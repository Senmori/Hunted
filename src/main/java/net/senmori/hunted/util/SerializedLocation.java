package net.senmori.hunted.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SerializedLocation 
{
	private int x;
	private int y;
	private int z;
	private String world;
	private String name;
	
	public SerializedLocation(Location loc, String name)
	{
		this.x = loc.getBlockX();
		this.y = loc.getBlockY();
		this.z = loc.getBlockZ();
		this.world = loc.getWorld().getName();
		this.name = name;
	}
	
	public SerializedLocation(Location loc)
	{
		this(loc,"");
	}
	
	public Location getLocation()
	{
		World w = Bukkit.getWorld(world);
		if(w == null)
		{
			return null;
		}
		return new Location(w,x,y,z);
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean compare(SerializedLocation loc)
	{
		return this.getLocation().equals(loc.getLocation());
	}
	
	public String toString()
	{
		return this.world + "," + this.x + "," + this.y + "," + this.z;
	}
}
