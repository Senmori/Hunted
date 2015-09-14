package net.senmori.hunted.listeners;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.game.GameState;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.stones.Stone.StoneType;
import net.senmori.hunted.tasks.DelayedTntExplosion;
import net.senmori.hunted.tasks.LogOutTimer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.material.Attachable;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerListener implements Listener {
	private Hunted plugin;
	
	public PlayerListener(Hunted plugin) {
	    this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
	    // player is in the Hunted arena, not the store or lobby
	    if(plugin.getPlayerManager().getState(e.getPlayer().getUniqueId().toString()).equals(GameState.IN_GAME)) {
	        if(plugin.getConfigManager().activeWorld.equals(e.getPlayer().getWorld().getName())) {
	            if (!Hunted.getInstance().getStoneManager().isValidActivator(e.getClickedBlock().getType())) {
	                e.setCancelled(true);
	                e.setUseInteractedBlock(Result.DENY);
	                e.setUseItemInHand(Result.DENY);
	                return;
	            }
	    
	            Block block = null;
	            if (e.getClickedBlock() instanceof Attachable) {
	                Attachable attach = (Attachable) e.getClickedBlock();
	                block = e.getClickedBlock().getRelative(attach.getAttachedFace());
	            }
	    
	            // if stone is null, then what was the button attached to O.o
	            if (block != null) {
	                Stone s = Hunted.getInstance().getStoneManager().getStone(block.getLocation());
	                if (s == null || !plugin.getPlayerManager().canInteractWithStone(s, e.getPlayer())) {
	                    e.setCancelled(true);
	                    e.setUseInteractedBlock(Result.DENY);
	                    e.setUseItemInHand(Result.DENY);
	                    return;
	                }
	                if (s != null && s.getType().equals(StoneType.GUARDIAN)) {
	                    // if this is a guardian stone, toggle indicator blocks
	                    ((GuardianStone) s).toggleIndicators();
	                }
	                if(s != null) {
	                   s.activate(e.getPlayer()); 
	                }
	                return;
	            }
	            // cancel everything but interaction with specific items/blocks we want
	            e.setCancelled(true);
	            e.setUseInteractedBlock(Result.DENY);
	            e.setUseItemInHand(Result.DENY);
	            return;
	        }
	    }
	    
	    if(plugin.getPlayerManager().getState(e.getPlayer().getUniqueId().toString()).equals(GameState.IN_STORE)) {
	        return;
	    }
	    
	    if(plugin.getPlayerManager().getState(e.getPlayer().getUniqueId().toString()).equals(GameState.LOBBY)) {
	        
	    }
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
	    if(plugin.getPlayerManager().getState(e.getPlayer().getUniqueId().toString()).equals(GameState.IN_GAME)) {
	        if(plugin.getConfigManager().activeWorld.equals(e.getPlayer().getLocation().getWorld().getName())) {
	            if(e.getItemDrop().getItemStack().getType().equals(Material.TNT)) {
	                e.getItemDrop().setMetadata("tnt-explosion", new FixedMetadataValue(Hunted.getInstance(), true));
	                new DelayedTntExplosion(e.getItemDrop(), 1.3f, false, false);
	            } 
	        }
	    }
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        if(plugin.getPlayerManager().getState(e.getPlayer().getUniqueId().toString()).equals(GameState.IN_GAME)) {
            if(plugin.getConfigManager().activeWorld.equals(e.getPlayer().getLocation().getWorld().getName())) {
                if(e.getItem().hasMetadata("tnt-explosion")) {
                    e.setCancelled(true);
                }
            }
        }
	}

	// handle players logging in/out of hunted arena
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e) {
		plugin.getPlayerManager().trackPlayer(e.getPlayer());
		// if player is not in Hunted world, set GameState to NOT_PLAYING
		Player player = e.getPlayer();
		if(!player.getWorld().getName().equals(plugin.getConfigManager().activeWorld)) {
		    plugin.getPlayerManager().setState(player.getUniqueId().toString(), GameState.NOT_PLAYING);
		    return;
		}
		
		// if player was playing or in the store, change GameState to LOBBY and move them to lobby
		if(plugin.getPlayerManager().getState(player.getUniqueId().toString()).equals(GameState.IN_GAME) || plugin.getPlayerManager().getState(player.getUniqueId().toString()).equals(GameState.IN_STORE)) {
		    plugin.getPlayerManager().setState(player.getUniqueId().toString(), GameState.LOBBY);
		    player.teleport(plugin.getSpawnManager().getRandomLobbyLocation().getLocation());
		}
	}

	// handle players logging out
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
	    if(plugin.getPlayerManager().isPlaying(e.getPlayer().getUniqueId().toString())) {
	        new LogOutTimer(plugin, e.getPlayer().getUniqueId().toString(), 30);
	    }
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
	    if(plugin.getPlayerManager().isPlaying(e.getPlayer().getUniqueId().toString())) {
	        if(e.getPlayer().getWorld().getName().equals(plugin.getConfigManager().activeWorld)) {
	            if(plugin.getSpawnManager().getLobbyLocations().size() > 0) {
	               e.setRespawnLocation(plugin.getSpawnManager().getRandomLobbyLocation().getLocation());
	            } else {
	                e.setRespawnLocation(e.getRespawnLocation()); // because paranoia
	            }
	        }
	    }
	}

	// handle kicking of players
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
	    if(plugin.getPlayerManager().isPlaying(e.getPlayer().getUniqueId().toString())) {
	        new LogOutTimer(plugin, e.getPlayer().getUniqueId().toString(), 30);
	    }
	}

	// handle changing of worlds(tp most likely)
	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
	    if(plugin.getPlayerManager().isPlaying(e.getPlayer().getUniqueId().toString())) {
	        if(e.getFrom().getName().equals(plugin.getConfigManager().activeWorld)) {
	            plugin.getPlayerManager().leaveHuntedWorld(e.getPlayer());
	            return;
	        }
	        if(e.getPlayer().getWorld().getName().equals(plugin.getConfigManager().activeWorld)) {
	            plugin.getPlayerManager().enterHuntedWorld(e.getPlayer());
	            return;
	        }
	        plugin.getPlayerManager().setState(e.getPlayer().getUniqueId().toString(), GameState.NOT_PLAYING);
	    }
	}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {
	    if(plugin.getPlayerManager().isPlaying(e.getEntity().getUniqueId().toString())) {
	        if(e.getEntity().getWorld().getName().equals(plugin.getConfigManager().activeWorld)) {
	            e.getDrops().clear();
	            e.setDroppedExp(0);
	        } else {
	            plugin.getPlayerManager().setState(e.getEntity().getUniqueId().toString(), GameState.NOT_PLAYING);
	        }
	    }
	}
	
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent e) {
	}
	
	@EventHandler
	public void onPlayerHurtByEntity(EntityDamageByEntityEvent e) {
	    if(e.getEntity() instanceof Player) {
	        Player player = (Player) e.getEntity();
	        if(plugin.getPlayerManager().getState(player.getUniqueId().toString()).equals(GameState.IN_STORE) || plugin.getPlayerManager().getState(player.getUniqueId().toString()).equals(GameState.LOBBY)) {
	            e.setCancelled(true);
	            e.setDamage(0.0);
	        }
	    }
	}
	
	@EventHandler
	public void onPlayerHurt(EntityDamageByBlockEvent e) {
	    if(e.getCause().equals(DamageCause.VOID)) return;
	    if(e.getEntity() instanceof Player) {
	        Player player = (Player) e.getEntity();
	        if(plugin.getPlayerManager().getState(player.getUniqueId().toString()).equals(GameState.IN_STORE) || plugin.getPlayerManager().getState(player.getUniqueId().toString()).equals(GameState.LOBBY)) {
	            e.setCancelled(true);
	            e.setDamage(0.0);
	        }
	    }
	}
	
	// only freeze hunger when players are in the lobby or store
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
	    if(e.getEntity() instanceof Player) {
	        Player player = (Player) e.getEntity();
	        if(plugin.getPlayerManager().getState(player.getUniqueId().toString()).equals(GameState.IN_STORE)) {
	            e.setFoodLevel(e.getFoodLevel());
	            e.setCancelled(true);
	        }
	        if(plugin.getPlayerManager().getState(player.getUniqueId().toString()).equals(GameState.LOBBY)) {
	            e.setFoodLevel(20);
	        }
	    }
	}
}
