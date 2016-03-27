package net.senmori.hunted.listeners;

import me.dpohvar.powernbt.api.NBTCompound;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.game.GameState;
import net.senmori.hunted.loot.utils.LootUtil;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.stones.Stone.StoneType;
import net.senmori.hunted.tasks.TntExplosion;
import net.senmori.hunted.util.Reference.Permissions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.Attachable;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerListener implements Listener {
	private Hunted plugin;

	public PlayerListener(Hunted plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerUseSpawnEgg(CreatureSpawnEvent e) {
		if(e.getSpawnReason().equals(SpawnReason.SPAWNER_EGG)) {
			// set NoAI so it's easier to test
			NBTCompound tag = Hunted.getInstance().nbtManager.read(e.getEntity());
			tag.put("NoAI", 1);
			Hunted.getInstance().nbtManager.write(e.getEntity(), tag);
			// reset LootTables each time so I don't have to keep placing/breaking blocks
			if(LootUtil.hasLootTable(e.getEntity())) {
				LootUtil.clearLootTable(e.getEntity());
			}
			LootUtil.setLootTable(e.getEntity(), "hunted:chests/debug");
		}
	}
		
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(LootUtil.isValidBlock(e.getClickedBlock()) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			LootUtil.setLootTable(e.getClickedBlock(), "hunted:chests/debug");
		}
		// player is playing, store or lobby haven't been implemented yet
		if (plugin.getPlayerManager().getState(e.getPlayer().getUniqueId()).equals(GameState.IN_GAME)) {
			if (plugin.getConfigManager().activeWorld.equals(e.getPlayer().getWorld().getName())) {
				if(e.getPlayer().hasPermission(Permissions.ADMIN_EXEMPT)) return;
				if(e.getPlayer().hasPermission(Permissions.COMMAND_DEV_TOOLS)) return; // ignore dev testing
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
					if (s != null) {
						s.activate(e.getPlayer());
					}
					return;
				}
				// cancel everything but interaction with specific items/blocks
				// we want
				e.setCancelled(true);
				e.setUseInteractedBlock(Result.DENY);
				e.setUseItemInHand(Result.DENY);
				return;
			}
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if (plugin.getPlayerManager().getState(e.getPlayer().getUniqueId()).equals(GameState.IN_GAME)) {
			if (plugin.getConfigManager().activeWorld.equals(e.getPlayer().getLocation().getWorld().getName())) {
				if (e.getItemDrop().getItemStack().getType().equals(Material.TNT)) {
					e.getItemDrop().setMetadata("tnt-explosion", new FixedMetadataValue(Hunted.getInstance(), true));
					new TntExplosion(e.getItemDrop(), 3.0f, false, false).runTaskLater(Hunted.getInstance(), 20 * 4);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
		if (plugin.getPlayerManager().getState(e.getPlayer().getUniqueId()).equals(GameState.IN_GAME)) {
			if (plugin.getConfigManager().activeWorld.equals(e.getPlayer().getLocation().getWorld().getName())) {
				if (e.getItem().hasMetadata("tnt-explosion")) {
					e.setCancelled(true);
				}
			}
		}
	}

	// handle players logging in/out of hunted arena
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		// if player is not in Hunted world, set GameState to NOT_PLAYING
		plugin.getPlayerManager().trackPlayer(e.getPlayer().getUniqueId());
	}

	// handle players logging out
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if (plugin.getPlayerManager().isPlaying(e.getPlayer().getUniqueId())) {
			plugin.getPlayerManager().untrackPlayer(e.getPlayer().getUniqueId());
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		if (plugin.getPlayerManager().isPlaying(e.getPlayer().getUniqueId())) {
			if (e.getPlayer().getWorld().getName().equals(plugin.getConfigManager().activeWorld)) {
				if (plugin.getSpawnManager().getLobbyLocations().size() > 0) {
					e.setRespawnLocation(plugin.getSpawnManager().getRandomLobbyLocation().getLocation());
				} else {
					e.setRespawnLocation(e.getRespawnLocation()); // because
					                                              // paranoia
				}
			}
		}
	}

	// handle kicking of players
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		if (plugin.getPlayerManager().isPlaying(e.getPlayer().getUniqueId())) {
			//new LogOutTimer(plugin, e.getPlayer().getUniqueId().toString(), 30);
		}
	}

	// handle changing of worlds(tp most likely)
	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
		if (plugin.getPlayerManager().isPlaying(e.getPlayer().getUniqueId())) {
			if (e.getFrom().getName().equals(plugin.getConfigManager().activeWorld)) {
				plugin.getPlayerManager().leaveWorld(e.getPlayer());
				return;
			}
			if (e.getPlayer().getWorld().getName().equals(plugin.getConfigManager().activeWorld)) {
				plugin.getPlayerManager().enterWorld(e.getPlayer());
				return;
			}
			plugin.getPlayerManager().setState(e.getPlayer().getUniqueId(), GameState.NOT_PLAYING);
		}
	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {
		if (plugin.getPlayerManager().isPlaying(e.getEntity().getUniqueId())) {
			if (e.getEntity().getWorld().getName().equals(plugin.getConfigManager().activeWorld)) {
				e.getDrops().clear();
				e.setDroppedExp(0);
			} else {
				plugin.getPlayerManager().setState(e.getEntity().getUniqueId(), GameState.NOT_PLAYING);
			}
		}
	}

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent e) {
		Player killer = null;
		if(e.getEntity().getKiller() instanceof Player) {
			killer = e.getEntity().getKiller();
		}
		
		if(killer != null) {
			plugin.getPlayerManager().getPlayer(killer.getUniqueId()).updateStat("kills", 1);
		}
	}

	@EventHandler
	public void onPlayerHurtByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (plugin.getPlayerManager().getState(player.getUniqueId()).equals(GameState.IN_STORE)
			        || plugin.getPlayerManager().getState(player.getUniqueId()).equals(GameState.LOBBY)) {
				e.setCancelled(true);
				e.setDamage(0.0);
			}
		}
	}

	@EventHandler
	public void onPlayerHurt(EntityDamageByBlockEvent e) {
		if (e.getCause().equals(DamageCause.VOID)) return;
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (plugin.getPlayerManager().getState(player.getUniqueId()).equals(GameState.IN_STORE)
					|| plugin.getPlayerManager().getState(player.getUniqueId()).equals(GameState.LOBBY)) {
				e.setCancelled(true);
				e.setDamage(0.0);
			}
		}
	}

	// only freeze hunger when players are in the lobby or store
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (plugin.getPlayerManager().getState(player.getUniqueId()).equals(GameState.IN_STORE)) {
				e.setFoodLevel(e.getFoodLevel());
				e.setCancelled(true);
			}
			if (plugin.getPlayerManager().getState(player.getUniqueId()).equals(GameState.LOBBY)) {
				e.setFoodLevel(20);
			}
		}
	}
}
