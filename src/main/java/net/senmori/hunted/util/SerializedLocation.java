package net.senmori.hunted.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SerializedLocation 
{
	private double x;
	private double y;
	private double z;
	private String world;
	private String name;
	
	public SerializedLocation(Location loc, String name)
	{
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.world = loc.getWorld().getName();
		this.name = name;
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
}
