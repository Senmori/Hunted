package net.senmori.hunted.util;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.game.StoneManager;
import net.senmori.hunted.stones.Stone;

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
		
		
		// only change name if no name is given by default
		if(name.isEmpty() || name.length() < 1)
		{
			// is it a guardian stone?
			for(Stone s : StoneManager.getStones())
			{
				if(s.getLocation().equals(loc))
				{
					name = s.getType().toString().substring(1).toLowerCase();
					name += "-" + Hunted.getStoneConfig().getConfigurationSection("stones").getKeys(false).size()+1;
				}
			}
		}
		this.name = name;
	}
	
	public SerializedLocation(Location loc)
	{
		this(loc, "");
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
