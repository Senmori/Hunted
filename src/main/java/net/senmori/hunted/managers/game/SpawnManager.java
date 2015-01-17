package net.senmori.hunted.managers.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.util.SerializedLocation;

public class SpawnManager 
{
	// Default: respawnLocation = default world spawn, make sure to set this if you want it somewhere else
	static
	{
		respawnLocations = new ArrayList<SerializedLocation>();
		spawnLocations = new ArrayList<SerializedLocation>();
	}
	
	private static List<SerializedLocation> respawnLocations;
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
	
	public static void addRespawnLocation(SerializedLocation loc)
	{
		respawnLocations.add(loc);
	}
	
	public static List<SerializedLocation> getRespawnLocations()
	{
		return respawnLocations;
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
	
	public static SerializedLocation getRandomSpawnLocation()
	{
		Random rand = new Random();
		return spawnLocations.get(rand.nextInt(spawnLocations.size()));
	}
	
	public static SerializedLocation getRandomRespawnLocation()
	{
		Random rand = new Random();
		return respawnLocations.get(rand.nextInt(respawnLocations.size()));
	}
}
