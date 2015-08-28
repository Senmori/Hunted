package net.senmori.hunted.stones;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.managers.game.SpawnManager;

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
		player.teleport(Hunted.getInstance().getSpawnManager().getRandomHuntedLocation().getLocation());
	}
	
	@Override
	public StoneType getType()
	{
		return StoneType.TELEPORT;
	}
	
}
