package net.senmori.hunted.managers.game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.lib.game.GameState;
import net.senmori.hunted.lib.game.Profile;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference.ErrorMessage;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerManager {

    private Hunted plugin;
    private Map<UUID, Profile> activePlayers;
    private Map<UUID, SerializedLocation> lastKnownLocation;

    public PlayerManager(JavaPlugin plugin) {
        this.plugin = (Hunted) plugin;
        activePlayers = new HashMap<UUID, Profile>();
        lastKnownLocation = new HashMap<UUID, SerializedLocation>();
    }

	/*
     * ################### Tracking methods ###################
	 */

    /**
     * Setup the player for entrance into the Hunted world
     *
     * @param uuid uuid of the player
     */
    public void trackPlayer(UUID uuid) {
        activePlayers.put(uuid, new Profile(Bukkit.getPlayer(uuid)));
        setState(uuid, GameState.LOBBY);
    }

    /**
     * Remove the player from being track
     *
     * @param uuid
     */
    public void untrackPlayer(UUID uuid) {
        activePlayers.remove(uuid);
    }

	/*
	 * ###################### Arena Methods ######################
	 */

    /**
     * Teleport player to Hunted arena. Give them armor, weapons, and anything else for when a player *enters* the arena
     * here
     *
     * @param player
     */
    public void enterArena(Player player) {
        if(plugin.getSpawnManager().getHuntedLocations().isEmpty() || plugin.getSpawnManager().getHuntedLocations() == null) {
            player.sendMessage(ChatColor.RED + ErrorMessage.NO_HUNTED_LOCATION_ERROR);
            return;
        }
        setState(player.getUniqueId(), GameState.IN_GAME);
        activePlayers.get(player.getUniqueId()).updateInventory();

        // clear inventory and potion effects
        player.getInventory().clear();
        player.getActivePotionEffects().clear();
        player.setGameMode(GameMode.ADVENTURE);
        // generate armor & weapon
        Hunted.getInstance().getKitManager().generateKit(player);
        // teleport to random location in arena
        player.teleport(plugin.getSpawnManager().getRandomHuntedLocation().getLocation());
        ActionBar.sendMessage(player, ChatColor.GREEN + "Good luck!");
    }

    /**
     * Teleport the player out of the Hunted arena
     *
     * @param player
     */
    public void leaveArena(Player player) {
        setState(player.getUniqueId(), GameState.LOBBY);
        player.getInventory().clear();
        activePlayers.get(player.getUniqueId()).restoreInventory();
        player.teleport(plugin.getSpawnManager().getRandomLobbyLocation().getLocation());
    }

	/*
	 * ######################## Hunted Store methods ########################
	 */

    /**
     * Teleport player to hunted store
     */
    public void enterStore(Player player) {
        if(plugin.getSpawnManager().getStoreLocations() == null || plugin.getSpawnManager().getStoreLocations().isEmpty()) {
            player.sendMessage(ChatColor.RED + ErrorMessage.NO_STORE_LOCATION_ERROR);

            return;
        }
        player.teleport(plugin.getSpawnManager().getRandomStoreLocation().getLocation());
        setState(player.getUniqueId(), GameState.IN_STORE);
    }

    /**
     * Teleport player out of hunted store to location depending on {@link GameState}
     */
    public void leaveStore(Player player) {
        // TODO: eventually have a GUI that opens and the player can choose to go to the lobby or back to the arena
        player.teleport(plugin.getSpawnManager().getRandomHuntedLocation().getLocation());
    }

	/*
	 * ######################### Enter/Exit world methods #########################
	 */

    /**
     * Called when a player enters the Hunted world
     *
     * @param player
     */
    public void enterWorld(Player player) {
        lastKnownLocation.put(player.getUniqueId(), new SerializedLocation(player.getLocation(), player.getDisplayName()));
        trackPlayer(player.getUniqueId());
    }

    /**
     * Teleport the player out of the Hunted world Restore inventory, potion effects, and teleport to where they
     * teleported in from
     *
     * @param player
     */
    public void leaveWorld(Player player) {
        if(isPlaying(player.getUniqueId())) {
            player.getInventory().clear();
            player.getActivePotionEffects().clear();

            // restore inventory
            getPlayer(player.getUniqueId()).restoreInventory();
            player.teleport(lastKnownLocation.get(player.getUniqueId()).getLocation(), TeleportCause.PLUGIN);
            untrackPlayer(player.getUniqueId());
        }
        setState(player.getUniqueId(), GameState.NOT_PLAYING);
        untrackPlayer(player.getUniqueId());
    }

	/*
	 * ####################### Permission methods #######################
	 */

    /**
     * Returns if this player can interact with guardian or teleport Stones
     */
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

    /**
     * Returns whether or not thisp layer can place blocks
     */
    public boolean canPlaceBlocks(Player player) {
        return player.hasPermission(Permissions.ADMIN_PLACE);
    }

    /**
     * Returns whether or not this player can break blocks
     */
    public boolean canBreakBlocks(Player player) {
        return player.hasPermission(Permissions.ADMIN_BREAK);
    }

    /**
     * Returns whether or not this player is exempt from normal setup of players
     */
    public boolean isExempt(Player player) {
        return player.hasPermission(Permissions.ADMIN_EXEMPT);
    }

    /*
     * ######################### Misc. #########################
     */
    public boolean isPlaying(UUID uuid) {
        switch(getState(uuid)) {
            case IN_GAME:
            case IN_STORE:
                return true;
            case LOBBY:
            case NOT_PLAYING:
            case OFFLINE:
            default:
                return false;
        }
    }

    /**
     * Returns the {@link GameState} of the player
     */
    public GameState getState(UUID uuid) {
        if(! activePlayers.containsKey(uuid)) {
            activePlayers.put(uuid, new Profile(Bukkit.getPlayer(uuid)));
            return activePlayers.get(uuid).getState();
        }
        return activePlayers.get(uuid).getState();
    }

    public void setState(UUID uuid, GameState state) {
        if(! activePlayers.containsKey(uuid)) {
            activePlayers.put(uuid, new Profile(Bukkit.getPlayer(uuid)));
        }
        activePlayers.get(uuid).setState(state);
    }

    /**
     * Returns where the player teleported from </br> Eventually I want to hook into essentials' homes </br>
     *
     * @param uuid - of the player
     *
     * @return SerializedLocation of where the player was at
     */
    public SerializedLocation getLastKnownLocation(UUID uuid) {
        return lastKnownLocation.get(uuid);
    }

    /*
     * #################### Getters ####################
     */
    public Map<UUID, Profile> getPlayers() {
        return activePlayers;
    }

    public Profile getPlayer(UUID uuid) {
        return activePlayers.get(uuid) != null ? activePlayers.get(uuid) : new Profile(Bukkit.getPlayer(uuid));
    }
}
