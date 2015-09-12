package net.senmori.hunted.managers.game;



import java.util.HashMap;
import java.util.Map;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.lib.game.GameState;
import net.senmori.hunted.lib.game.Profile;
import net.senmori.hunted.lib.libs.ActionBarAPI;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.util.Reference.ErrorMessage;
import net.senmori.hunted.util.Reference.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {

	private Hunted plugin;
    private Map<String, Profile> activePlayers;
    private Map<String, ItemStack[]> playerInventories;
	private Map<String, SerializedLocation> lastKnownLocation;
	
	public PlayerManager(Hunted plugin) {
	    this.plugin = plugin;
		activePlayers = new HashMap<>();
		playerInventories = new HashMap<>();
		lastKnownLocation = new HashMap<>();
	}
	
	/* ###################
	 *  Tracking methods
	 * ###################
	 */
	
	/** Setup the player for entrance in the Hunted arena */
	public void trackPlayer(Player player) {
	    activePlayers.put(player.getUniqueId().toString(), new Profile(player));
	    if(isPlaying(player.getUniqueId().toString())) {
	        storeInventory(player);
	        player.getInventory().clear();
	        player.getActivePotionEffects().clear();
	    }
	}
	
	/** Remove player from being tracked */
   public void untrackPlayer(String uuid) {
        activePlayers.remove(uuid);
        playerInventories.remove(uuid);
        lastKnownLocation.remove(uuid);
    }
	
   /* ######################
    *  Arena Methods
    * ###################### 
    */
	/** 
	 * Teleport player to Hunted arena.
	 * Give them armor, weapons, and anything else for when a player *enters* the arena here
	 * @param player
	 */
	public void enterArena(Player player) {
	    if(plugin.getSpawnManager().getHuntedLocations().isEmpty() || plugin.getSpawnManager().getHuntedLocations() == null) {
            player.sendMessage(ChatColor.RED + ErrorMessage.NO_HUNTED_LOCATION_ERROR);
            return;
        }
	    setState(player.getUniqueId().toString(), GameState.IN_GAME);
	    // clear inventory and potion effects
	    player.getInventory().clear();
	    player.getActivePotionEffects().clear();
	    player.setGameMode(GameMode.ADVENTURE);
	    // generate armor & weapon
	    Hunted.getInstance().getKitManager().generateKit(player);
	    // teleport to random location in arena
	    player.teleport(plugin.getSpawnManager().getRandomHuntedLocation().getLocation());
	    ActionBarAPI.send(player, ChatColor.GREEN + "Good luck!");
	}
	
	/**
	 * Teleport the player out of the Hunted arena 
	 * @param player
	 */
	public void leaveArena(Player player) {
	    setState(player.getUniqueId().toString(), GameState.LOBBY);
	    player.teleport(plugin.getSpawnManager().getRandomLobbyLocation().getLocation());
	}
	
	/* ########################
	 *  Hunted Store methods
	 * ########################
	 */
	/** Teleport player to hunted store */
	public void enterHuntedStore(Player player) {
	    if(!plugin.getSpawnManager().getStoreLocations().isEmpty() || plugin.getSpawnManager().getStoreLocations() == null) {
	        player.sendMessage(ChatColor.RED + ErrorMessage.NO_STORE_LOCATION_ERROR);
	        return;
	    }
	    player.teleport(plugin.getSpawnManager().getRandomStoreLocation().getLocation());
	    setState(player.getUniqueId().toString(), GameState.IN_STORE);
	}
	
	/** Teleport player out of hunted store to location depending on {@link GameState} */
	public void leaveHuntedStore(Player player) {
	    // TODO: eventually have a GUI that opens and the player can choose to go to the lobby or back to the arena
	    player.teleport(plugin.getSpawnManager().getRandomHuntedLocation().getLocation());
	}
	
	
	/* #########################
	 *  Enter/Exit world methods
	 * #########################
	 */
	/**
	 * Called when a player enters the Hunted world
	 * @param player
	 */
	public void enterHuntedWorld(Player player) {
	    lastKnownLocation.put(player.getUniqueId().toString(), new SerializedLocation(player.getLocation(), player.getDisplayName()));
	    trackPlayer(player);
	}
	
	
	/**
	 * Teleport the player out of the Hunted world
	 * Restore inventory, potion effects, and teleport to where they teleported in from
	 * @param player
	 */
	public void leaveHuntedWorld(Player player) {
	    if(isPlaying(player.getUniqueId().toString())) {
	        player.getInventory().clear();
	        player.getActivePotionEffects().clear();
	        
	        // restore inventory
            for(ItemStack is : playerInventories.get(player.getUniqueId().toString())) {
                player.getInventory().addItem(is);
            }
	        player.teleport(lastKnownLocation.get(player.getUniqueId()).getLocation(), TeleportCause.PLUGIN);
            untrackPlayer(player.getUniqueId().toString());
	    }
	    setState(player.getUniqueId().toString(), GameState.NOT_PLAYING);
	}
	
	 /* #######################
     *  Permission methods
     *  #######################
     */
	
	/** Returns if this player can interact with guardian or teleport Stones */
	public boolean canInteractWithStone(Stone s, Player player) {
		switch(s.getType()) {
		case GUARDIAN:
			return player.hasPermission(Permissions.PLAYER_INTERACT_STONE_GUARDIAN);
		case TELEPORT:
			return player.hasPermission(Permissions.PLAYER_INTERACT_STONE_TELEPORT);
		default:
			return false;
		}
	}
	
	/** Returns whether or not thisp layer can place blocks */
	public boolean canPlaceBlocks(Player player) {
		return player.hasPermission(Permissions.ADMIN_PLACE);
	}
	
	/** Returns whether or not this player can break blocks */
	public boolean canBreakBlocks(Player player) {
		return player.hasPermission(Permissions.ADMIN_BREAK);
	}
	
	/** Returns whether or not this player is exempt from normal setup of players */
	public boolean isExempt(Player player) {
		return player.hasPermission(Permissions.ADMIN_EXEMPT);
	}
	
	/* #########################
	 *  Misc.
	 * #########################
	 */
	public boolean isPlaying(String uuid) {
	    switch(getState(uuid)) {
    	    case IN_GAME:
    	    case IN_STORE:
    	    case LOBBY:
    	        return true;
    	    case NOT_PLAYING:
    	    case OFFLINE:
    	        return false;
	        default:
	            return false;
	    }
	}
	/** Returns the {@link GameState} of the player */
	public GameState getState(String uuid) {
		return activePlayers.get(uuid).getState();
	}
	
	public void setState(String uuid, GameState state) {
	    activePlayers.get(uuid).setState(state);
	}
	
	/** Returns where the player teleported from </br>
	 *  Eventually I want to hook into essentials' homes </br>
	 * @param uuid - of the player
	 * @return SerializedLocation of where the player was at
	 */
	public SerializedLocation getLastKnownLocation(String uuid) {
	    return lastKnownLocation.get(uuid);
	}

	private void storeInventory(Player player) {
	    playerInventories.put(player.getUniqueId().toString(), player.getInventory().getContents());
	}
	
	/* ####################
	 *  Getters
	 * ####################
	 */
    public Map<String, Profile> getPlayers() {
        return activePlayers;
    } 
}
