package net.senmori.hunted.stones;

import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.entity.Player;

public class TeleportStone extends Stone
{
	
	
	public TeleportStone(SerializedLocation loc)
	{
		super(loc);
	}
	
	
	@Override
	public void activate(Player player) 
	{
		
	}
	
	@Override
	public Type getType()
	{
		return Type.TELEPORT;
	}
	
}
