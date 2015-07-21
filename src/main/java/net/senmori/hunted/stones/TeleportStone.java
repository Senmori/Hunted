package net.senmori.hunted.stones;

import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.entity.Player;

public class TeleportStone extends Stone
{
	
	public TeleportStone(SerializedLocation loc)
	{
		super(loc);
	}
	
	/** Main method, call this to active this {@link TeleportStone} */
	@Override
	public void activate(Player player) 
	{
		player.teleport(SpawnManager.getRandomHuntedLocation().getLocation());
	}
	
	@Override
	public StoneType getType()
	{
		return StoneType.TELEPORT;
	}
	
}
