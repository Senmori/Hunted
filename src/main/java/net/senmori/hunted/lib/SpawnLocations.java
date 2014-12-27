package net.senmori.hunted.lib;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;

import net.senmori.hunted.util.SerializedLocation;

public class SpawnLocations 
{
	// Default: respawnLocation = default world spawn, make sure to set this if you want it somewhere else
	static
	{
		respawnLocation = new SerializedLocation(Bukkit.getWorlds().get(0).getSpawnLocation(), "respawn");
	}
	
	private static SerializedLocation respawnLocation;
	private static HashMap<String,SerializedLocation> spawnLocations;
	
	public static void addSpawnLocation(SerializedLocation loc)
	{
		spawnLocations.put(loc.getName(), loc);
	}
	
	public static boolean removeSpawnLocation(SerializedLocation loc)
	{
		// HashMap is empty, can't remove what's not there
		if(spawnLocations.isEmpty()) return true;
		
		Iterator<String> iter = spawnLocations.keySet().iterator();
		while(iter.hasNext())
		{
			if(loc.compare(spawnLocations.get(iter.next())))
			{
				// the locations match, remove it
				iter.remove();
				return true;
			}
		}
		// that location didn't exist, therefore we can't remove it
		return false;
	}
	
	public static void setRespawnLocation(SerializedLocation loc)
	{
		respawnLocation = loc;
	}
	
	public static SerializedLocation getRespawnLoc()
	{
		return respawnLocation;
	}
	
	public static HashMap<String,SerializedLocation> getSpawnLocations()
	{
		return spawnLocations;
	}
}
