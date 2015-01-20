package net.senmori.hunted.managers.game;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.util.Reference.Permissions;

public class PlayerManager {

	public PlayerManager() {}
	
	/*
	 * Setup the player's inventory, gamemode, effects, and spawn point when they die/spawn into the world
	 */
	public void setupPlayer(Player player)
	{
		if(!player.getWorld().getName().equals(Hunted.activeWorld)) return;
		if(isExemptFromSetup(player)) return;
		player.getInventory().clear();
		player.setGameMode(GameMode.ADVENTURE);
		player.getActivePotionEffects().clear();
		player.teleport(SpawnManager.getRandomRespawnLocation().getLocation());
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
		case ADMIN:
			return player.hasPermission(Permissions.PLAYER_INTERACT_STONE_ADMIN);
		case INFO:
			return player.hasPermission(Permissions.PLAYER_INTERACT_STONE_INFO);
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

}
