package net.senmori.hunted.managers.game;

import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerManager {
	public enum State
	{
		IN_GAME,
		IN_STORE,
		OFFLINE;
	}
	private HashMap<String,State> activePlayers;
	
	public PlayerManager() 
	{
		activePlayers = new HashMap<String,State>();
	}
	
	/*
	 * Setup the player's inventory, gamemode, effects, and spawn point when they die/spawn into the world
	 */
	public void setupPlayer(Player player)
	{
		if(!player.getWorld().getName().equals(net.senmori.hunted.Hunted.activeWorld)) return;
		if(isExemptFromSetup(player)) return;
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setGameMode(GameMode.ADVENTURE);
		player.getActivePotionEffects().clear();
		player.teleport(SpawnManager.getRandomRespawnLocation().getLocation());
		//add player to list
		activePlayers.put(player.getUniqueId().toString(), State.IN_GAME);
	}
	
	/*
	 * Remove player from game.
	 */
	public void removePlayer(Player player)
	{
		if(!player.getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		if(isExemptFromSetup(player)) return;
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setGameMode(GameMode.SURVIVAL);
		player.getActivePotionEffects().clear();
		activePlayers.remove(player.getUniqueId().toString());
		
	}
	
	/*
	 * Returns whether or not this player can interact with the given stone
	 */
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
	
	/*
	 * Returns whether or not this player can place blocks in hunted
	 */
	public boolean canPlaceBlocks(Player player)
	{
		return player.hasPermission(Permissions.ADMIN_PLACE);
	}
	
	/*
	 * Returns whether or not this player can break blocks in hunted
	 */
	public boolean canBreakBlocks(Player player)
	{
		return player.hasPermission(Permissions.ADMIN_BREAK);
	}
	/*
	 * Returns whether or not the player is exempt from the .setupPlayer() function
	 */
	public boolean isExemptFromSetup(Player player)
	{
		return player.hasPermission(Permissions.ADMIN_EXEMPT);
	}
	
	public HashMap<String,State> getPlayers()
	{
		return activePlayers;
	}

}
