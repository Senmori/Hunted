package net.senmori.hunted.managers.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.util.SerializedLocation;

public class SpawnManager 
{	
	// respawnLocations = where players spawn when they die, or are entering the Hunted world
	private static List<SerializedLocation> respawnLocations;
	// spawnLocations = where players spawn when they enter the Hunted arena
	private static List<SerializedLocation> spawnLocations;
	
	// Default: respawnLocation = default world spawn, make sure to set this if you want it somewhere else
	static
	{
		respawnLocations = new ArrayList<SerializedLocation>();
		spawnLocations = new ArrayList<SerializedLocation>();
	}
	
	/*
	 * Add spawn location(Hunted arena)
	 */
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
				return spawnLocations.remove(sl);
			}
		}
		// that location didn't exist, therefore we can't remove it
		return false;
	}
	
	/*
	 * Add respawn location(Hunted spawn area)
	 */
	public static void addRespawnLocation(SerializedLocation loc)
	{
		respawnLocations.add(loc);
	}
	
	public static boolean removeRespawnLocation(SerializedLocation loc)
	{
		if(respawnLocations.isEmpty()) return true;
		for(SerializedLocation sl : respawnLocations)
		{
			if(sl.getLocation().equals(loc))
			{
				return respawnLocations.remove(sl);
			}
		}
		
		return false;
	}
	
	public SerializedLocation getLocationByName(String name)
	{
		for(SerializedLocation loc : getSpawnLocations())
		{
			if(loc.getName().equalsIgnoreCase(name))
			{
				return loc;
			}
		}
		return null;
	}
	
	public static SerializedLocation getRandomSpawnLocation()
	{
		Random rand = new Random();
		return spawnLocations.get(rand.nextInt(spawnLocations.size()+1));
	}
	
	public static SerializedLocation getRandomRespawnLocation()
	{
		Random rand = new Random();
		return respawnLocations.get(rand.nextInt(respawnLocations.size()+1));
	}
		
	public static List<SerializedLocation> getRespawnLocations()
	{
		return respawnLocations;
	}
	
	public static List<SerializedLocation> getSpawnLocations()
	{
		return spawnLocations;
	}
}
