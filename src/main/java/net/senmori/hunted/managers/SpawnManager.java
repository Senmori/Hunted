package net.senmori.hunted.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.Bukkit;

public class SpawnManager 
{
	// Default: respawnLocation = default world spawn, make sure to set this if you want it somewhere else
	static
	{
		respawnLocation = new SerializedLocation(Bukkit.getWorlds().get(0).getSpawnLocation(), "respawn");
		spawnLocations = new ArrayList<SerializedLocation>();
	}
	
	private static SerializedLocation respawnLocation;
	private static List<SerializedLocation> spawnLocations;
	
	public static void addSpawnLocation(SerializedLocation loc)
	{
		spawnLocations.add(loc);
	}
	
	public static boolean removeSpawnLocation(SerializedLocation loc)
	{
		// HashMap is empty, can't remove what's not there
		if(spawnLocations.isEmpty()) return true;
		for(SerializedLocation sl : spawnLocations)
		{
			if(sl.getLocation().equals(loc.getLocation()))
			{
				spawnLocations.remove(sl);
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
	
	public static List<SerializedLocation> getSpawnLocations()
	{
		return spawnLocations;
	}
	
	public List<SerializedLocation> getLocationByName(String name)
	{
		List<SerializedLocation> temp = new ArrayList<SerializedLocation>();
		for(SerializedLocation loc : getSpawnLocations())
		{
			if(loc.getName().equalsIgnoreCase(name))
			{
				temp.add(loc);
			}
		}
		return temp;
	}
	
	public SerializedLocation getRandomSpawnLocation()
	{
		Random rand = new Random();
		return spawnLocations.get(rand.nextInt(spawnLocations.size()));
	}
}
