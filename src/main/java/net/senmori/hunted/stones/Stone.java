package net.senmori.hunted.stones;

import net.senmori.hunted.managers.game.StoneManager;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Stone 
{
	public enum Type
	{
		GUARDIAN, 	// normal guardian stones(rewards, etc)
		TELEPORT;	// Teleports players to a specified point
	}

	private SerializedLocation sLoc;
	
	Stone(SerializedLocation loc)
	{
		this.sLoc = loc;
		StoneManager.add(this);
	}
	public abstract void activate(Player player);
	
	public Type getType()
	{
		return null;
	}
	
	public Location getLocation()
	{
		return sLoc.getLocation();
	}
	
	public String getName()
	{
		return sLoc.getName();
	}
}
