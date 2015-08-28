package net.senmori.hunted.managers.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.SerializedLocation;

import org.bukkit.Location;

public class SpawnManager 
{	
	/** Where players spawn when they die or enter the Hunted world */
	private Set<SerializedLocation> lobbyLocations;
	/** Where players spawn when they enter the Hunted arena */
	private Set<SerializedLocation> huntedLocations;
	/** Where players will spawn when they enter the Hunted store */
	private Set<SerializedLocation> storeLocations;
	
	private Hunted plugin;
	
	// Default: respawnLocation = default world spawn, make sure to set this if you want it somewhere else
	public SpawnManager(Hunted plugin) {
	    this.plugin = plugin;
		huntedLocations = new HashSet<>();
		lobbyLocations = new HashSet<>();
		storeLocations = new HashSet<>();
	}
	
	/**
	 * Add a  location to {@link #huntedLocations} <br>
	 * @param loc - {@link SerializedLocation}
	 */
	public void addHuntedLocation(SerializedLocation loc)
	{
		huntedLocations.add(loc);
	}
	
	/** Add a  location to {@link #huntedLocations} */
	public void addHuntedLocation(Location loc, String locName)
	{
		huntedLocations.add(new SerializedLocation(loc, locName));
	}
	
	/** Add a location to {@link #lobbyLocations} */
	public void addLobbyLocation(SerializedLocation loc)
	{
		lobbyLocations.add(loc);
	}
	
	/** Add a location to {@link #lobbyLocations} */
	public void addLobbyLocation(Location loc, String locName)
	{
		lobbyLocations.add(new SerializedLocation(loc, locName));
	}
	
	/** Add a location to {@link #storeLocations} */
	public void addStoreLocation(SerializedLocation loc) {
	    storeLocations.add(loc);
	}
	
	/** Add a location to {@link #storeLocations} */
	public void addStoreLocation(Location loc, String name) {
	    storeLocations.add(new SerializedLocation(loc, name));
	}
	
	/** Remove a location from {@link #lobbyLocations} */
	public boolean removeLobbyLocation(SerializedLocation loc)
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
	public boolean removeHuntedLocation(SerializedLocation loc)
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
	
	public boolean removeStoreLocation(SerializedLocation loc) {
	    if(storeLocations.isEmpty()) return true;
	    for(SerializedLocation sl : storeLocations) {
	        if(sl.getLocation().equals(loc)) {
	            return storeLocations.remove(sl);
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
	public SerializedLocation getRandomHuntedLocation()
	{
		if(huntedLocations.isEmpty() || huntedLocations == null) return null;
		List<SerializedLocation> list = new ArrayList<>();
		list.addAll(huntedLocations);
		Collections.shuffle(list);
		return list.get(0);
	}
	
	/**
	 * Return a random serialized location from {@link #lobbyLocations}
	 * @return
	 */
	public SerializedLocation getRandomLobbyLocation()
	{
		if(lobbyLocations.isEmpty() || lobbyLocations == null) return null;
		List<SerializedLocation> list = new ArrayList<>();
		list.addAll(lobbyLocations);
		Collections.shuffle(list);
		return list.get(0);
	}
	
	/**
	 * Return a random serialized location from {@link #storeLocations}
	 * @return
	 */
	public SerializedLocation getRandomStoreLocation() {
	    if(storeLocations.isEmpty() || storeLocations == null) return null;
	    List<SerializedLocation> list = new ArrayList<>();
	    list.addAll(storeLocations);
	    Collections.shuffle(list);
	    return list.get(0);
	}
		
	/** Return all {@link #lobbyLocations} */
	public Set<SerializedLocation> getLobbyLocations() {
		return lobbyLocations;
	}
	
	/** Return all {@link #huntedLocations} */
	public Set<SerializedLocation> getHuntedLocations() {
		return huntedLocations;
	}
	
	public Set<SerializedLocation> getStoreLocations() {
	    return storeLocations;
	}
	
	
	/** Return all registered locations */
	public Set<SerializedLocation> getLocations() {
		Set<SerializedLocation> locations = new HashSet<>();
		locations.addAll(lobbyLocations);
		locations.addAll(huntedLocations);
		locations.addAll(storeLocations);
		return locations;
	}
}
