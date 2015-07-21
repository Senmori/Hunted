package net.senmori.hunted.stones;

import net.senmori.hunted.managers.game.StoneManager;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Stone 
{
	/**
	 * The type of stones Hunted supports <br>
	 * {@link #GUARDIAN} - used for rewards, etc <br>
	 * {@link #TELEPORT} - used to teleport players to random locations <br>
	 *
	 */
	public enum StoneType
	{
		GUARDIAN, 	// normal guardian stones(rewards, etc)
		TELEPORT;	// Teleports players to a specified point
	}

	private SerializedLocation sLoc;
	
	Stone(SerializedLocation loc)
	{
		this.sLoc = loc;
		StoneManager.register(this);
	}
	public abstract void activate(Player player);
	
	public StoneType getType()
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
