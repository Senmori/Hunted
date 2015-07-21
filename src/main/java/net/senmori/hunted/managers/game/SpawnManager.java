package net.senmori.hunted.managers.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.Location;

public class SpawnManager 
{	
	/** Where players spawn when they die or enter the Hunted world */
	private static Set<SerializedLocation> lobbyLocations;
	/** Where players spawn when they enter the Hunted arena */
	private static Set<SerializedLocation> huntedLocations;
	
	// Default: respawnLocation = default world spawn, make sure to set this if you want it somewhere else
	static
	{
		huntedLocations = new HashSet<>();
		lobbyLocations = new HashSet<>();
	}
	
	/**
	 * Add a  location to {@link #huntedLocations} <br>
	 * @param loc - {@link SerializedLocation}
	 */
	public static void addHuntedLocation(SerializedLocation loc)
	{
		huntedLocations.add(loc);
	}
	
	/** Add a  location to {@link #huntedLocations} */
	public static void addHuntedLocation(Location loc, String locName)
	{
		huntedLocations.add(new SerializedLocation(loc, locName));
	}
	
	/** Add a location to {@link #lobbyLocations} */
	public static void addLobbyLocation(SerializedLocation loc)
	{
		lobbyLocations.add(loc);
	}
	
	/** Add a location to {@link #lobbyLocations} */
	public static void addLobbyLocation(Location loc, String locName)
	{
		lobbyLocations.add(new SerializedLocation(loc, locName));
	}
	
	/** Remove a location from {@link #lobbyLocations} */
	public static boolean removeLobbyLocation(SerializedLocation loc)
	{
		// HashMap is empty, can't remove what's not there
		if(lobbyLocations.isEmpty()) return true;
		for(SerializedLocation sl : lobbyLocations)
		{
			if(sl.getLocation().equals(loc.getLocation()))
			{
				return lobbyLocations.remove(sl);
			}
		}
		// that location didn't exist, therefore we can't remove it
		return false;
	}
	
	/** Remove a location from {@link #huntedLocations} */
	public static boolean removeHuntedLocation(SerializedLocation loc)
	{
		if(huntedLocations.isEmpty()) return true;
		for(SerializedLocation sl : huntedLocations)
		{
			if(sl.getLocation().equals(loc))
			{
				return huntedLocations.remove(sl);
			}
		}
		
		return false;
	}
	
	/** Get a location by name, from either {@link #lobbyLocations} or {@link #huntedLocations} */
	public SerializedLocation getLocationByName(String name)
	{
		for(SerializedLocation loc : getLocations())
		{
			if(loc.getName().equalsIgnoreCase(name))
			{
				return loc;
			}
		}
		return null;
	}
	
	/**
	 * Return a random serialized location from {@link #huntedLocations}
	 * @return
	 */
	public static SerializedLocation getRandomHuntedLocation()
	{
		List<SerializedLocation> list = new ArrayList<>();
		list.addAll(huntedLocations);
		Collections.shuffle(list);
		return list.get(0);
	}
	
	/**
	 * Return a random serialized location from {@link #lobbyLocations}
	 * @return
	 */
	public static SerializedLocation getRandomLobbyLocation()
	{
		List<SerializedLocation> list = new ArrayList<>();
		list.addAll(lobbyLocations);
		Collections.shuffle(list);
		return list.get(0);
	}
		
	/** Return all {@link #lobbyLocations} */
	public static Set<SerializedLocation> getLobbyLocations()
	{
		return lobbyLocations;
	}
	
	/** Return all {@link #huntedLocations} */
	public static Set<SerializedLocation> getHuntedLocations()
	{
		return huntedLocations;
	}
	
	/** Return all registered locations */
	public static Set<SerializedLocation> getLocations()
	{
		Set<SerializedLocation> locations = new HashSet<>();
		locations.addAll(lobbyLocations);
		locations.addAll(huntedLocations);
		return locations;
	}
	
	/** Save all registered locations */
    public static void saveLocations()
	{
		for(SerializedLocation loc : getHuntedLocations())
		{
			Hunted.getInstance().getConfig().set("hunted_loc." + loc.getName() + ".x", loc.getLocation().getBlockX());
			Hunted.getInstance().getConfig().set("hunted_loc." + loc.getName() + ".y", loc.getLocation().getBlockY());
			Hunted.getInstance().getConfig().set("hunted_loc." + loc.getName() + ".z", loc.getLocation().getBlockZ());
		}
		
		for(SerializedLocation loc : getLobbyLocations())
		{
			Hunted.getInstance().getConfig().set("lobby_loc." + loc.getName() + ".x", loc.getLocation().getBlockX());
			Hunted.getInstance().getConfig().set("lobby_loc." + loc.getName() + ".y", loc.getLocation().getBlockY());
			Hunted.getInstance().getConfig().set("lobby_loc." + loc.getName() + ".z", loc.getLocation().getBlockZ());
		}
	}
}
