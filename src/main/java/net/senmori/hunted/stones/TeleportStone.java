package net.senmori.hunted.stones;

import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.entity.Player;

public class TeleportStone extends Stone
{
	private String name;
	
	public TeleportStone(SerializedLocation loc)
	{
		super(loc);
	}
	
	
	@Override
	public void activate(Player player) 
	{
		player.teleport(SpawnManager.getRandomSpawnLocation().getLocation());
	}
	
	@Override
	public Type getType()
	{
		return Type.TELEPORT;
	}
	
}
