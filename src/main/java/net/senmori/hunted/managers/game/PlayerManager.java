package net.senmori.hunted.managers.game;



import java.util.Collection;
import java.util.HashMap;

import net.senmori.hunted.managers.ConfigManager;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.util.Reference.Permissions;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerManager {
	/**
	 * Stores the state of players <br>
	 * {@link #LOBBY} - if the player is in the Hunted lobby area <br>
	 * {@link #IN_GAME} - if the player is in the Hunted arena <br>
	 * {@link #IN_STORE} - if the player is in the Hunted store <br>
	 * {@link #OFFLINE} - if the player is offline but still stored for whatever reason <br>
	 */
	public enum State
	{
		LOBBY,
		IN_GAME,
		IN_STORE,
		OFFLINE;
	}
	
	private HashMap<String,State> activePlayers;
	
	private HashMap<String, Collection<PotionEffect>> inStorePlayers;
	
	public PlayerManager() 
	{
		activePlayers = new HashMap<>();
		inStorePlayers = new HashMap<>();
	}
	
	/** Setup the player for entrance in the Hunted arena */
	public void setupPlayer(Player player)
	{
		if(!player.getWorld().getName().equals(ConfigManager.activeWorld)) return;
		if(isExemptFromSetup(player)) return;
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setGameMode(GameMode.ADVENTURE);
		player.getActivePotionEffects().clear();
		player.teleport(SpawnManager.getRandomLobbyLocation().getLocation());
		//add player to list
		activePlayers.put(player.getUniqueId().toString(), State.LOBBY);
	}
	
	/** Remove player from {@link PlayerManager#activePlayers} */
	public void removePlayer(Player player)
	{
		getState(player);
		if(!player.getWorld().getName().equalsIgnoreCase(ConfigManager.activeWorld)) return;
		if(isExemptFromSetup(player)) return;
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setGameMode(GameMode.SURVIVAL);
		player.getActivePotionEffects().clear();
		activePlayers.remove(player.getUniqueId().toString());
		inStorePlayers.remove(player.getUniqueId().toString());
		
	}
	
	/** Returns if this player can interact with guardian or teleport Stones */
	public boolean canInteractWithStone(Stone s, Player player)
	{
		switch(s.getType())
		{
		case GUARDIAN:
			return player.hasPermission(Permissions.PLAYER_INTERACT_STONE_GUARDIAN);
		case TELEPORT:
			return player.hasPermission(Permissions.PLAYER_INTERACT_STONE_TELEPORT);
		default:
			return false;
		}
	}
	
	/** Returns whether or not thisp layer can place blocks */
	public boolean canPlaceBlocks(Player player)
	{
		return player.hasPermission(Permissions.ADMIN_PLACE);
	}
	
	/** Returns whether or not this player can break blocks */
	public boolean canBreakBlocks(Player player)
	{
		return player.hasPermission(Permissions.ADMIN_BREAK);
	}
	
	/** Returns whether or not this player is exempt from normal setup of players */
	public boolean isExemptFromSetup(Player player)
	{
		return player.hasPermission(Permissions.ADMIN_EXEMPT);
	}
	
	/** Returns the {@link State} of the player */
	public State getState(Player player)
	{
		return activePlayers.get(player.getUniqueId().toString());
	}
	
	/** Returns a map of all players in the Hunted world */
	public HashMap<String,State> getPlayers()
	{
		return activePlayers;
	}
	
	

}
