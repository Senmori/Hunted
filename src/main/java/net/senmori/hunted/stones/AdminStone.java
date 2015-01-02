package net.senmori.hunted.stones;

import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.entity.Player;

public class AdminStone extends Stone
{

	public AdminStone(SerializedLocation loc)
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
		return Type.ADMIN;
	}
}
