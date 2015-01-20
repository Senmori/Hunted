package net.senmori.hunted.managers.game;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.util.Reference.Permissions;

public class PlayerManager {

	public PlayerManager() 
	{
		
	}
	
	public void setupPlayer(Player player)
	{
		player.getInventory().clear();
		player.setGameMode(GameMode.ADVENTURE);
		player.getActivePotionEffects().clear();
		player.teleport(SpawnManager.getRandomRespawnLocation().getLocation());
	}
	
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
	
	public boolean canPlaceBlocks(Player player)
	{
		return player.hasPermission(Permissions.ADMIN_PLACE);
	}
	
	public boolean canBreakBlocks(Player player)
	{
		return player.hasPermission(Permissions.ADMIN_BREAK);
	}

}
