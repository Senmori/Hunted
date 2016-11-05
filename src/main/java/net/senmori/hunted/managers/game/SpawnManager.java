package net.senmori.hunted.managers.game;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.MapConfiguration;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.lib.game.LocationType;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpawnManager {
	/** Where players spawn when they die or enter the Hunted world */
	private List<SerializedLocation> lobbyLocations = new ArrayList<>();
	/** Where players spawn when they enter the Hunted arena */
	private List<SerializedLocation> huntedLocations = new ArrayList<>();
	/** Where players will spawn when they enter the Hunted store */
	private List<SerializedLocation> storeLocations = new ArrayList<>();
    
    private Hunted plugin = Hunted.getInstance();

    
	/* #####################################
	 * Add location methods
	 * #####################################
	 */

	/** Add a location to {@link #huntedLocations} */
	public boolean addArenaLocation(Location loc, String locName) {
        plugin.getConfigManager().getActiveMapConfiguration().saveArenaLocation(new SerializedLocation(loc, locName));
		return huntedLocations.add(new SerializedLocation(loc, locName));
	}

	/** Add a location to {@link #lobbyLocations} */
	public boolean addLobbyLocation(Location loc, String locName) {
        plugin.getConfigManager().getActiveMapConfiguration().saveLobbyLocation(new SerializedLocation(loc, locName));
		return lobbyLocations.add(new SerializedLocation(loc, locName));
	}

	/** Add a location to {@link #storeLocations} */
	public boolean addStoreLocation(Location loc, String name) {
        plugin.getConfigManager().getActiveMapConfiguration().saveStoreLocation(new SerializedLocation(loc, name));
		return storeLocations.add(new SerializedLocation(loc, name));
	}

	/*
	 * Remove location methods
	 */
	/**
	 * Generic remove a location, it will look through all lists. Don't use this unless absolutely necessary.
	 */
	public boolean removeLocation(Location loc) {
		MapConfiguration map = plugin.getConfigManager().getActiveMapConfiguration();
		SerializedLocation current = null;
		for (SerializedLocation sLoc : getSerializedLocation(loc).keySet()) {
			if (sLoc.getLocation().equals(loc)) {
				current = sLoc;
				break;
			}
		}

		if (current != null) {
			for (SerializedLocation hunted : getHuntedLocations()) {
				if (hunted.getLocation().equals(current.getLocation())) {
					map.removeHuntedLocation(current);
					return true;
				}
			}
			for (SerializedLocation lobby : getLobbyLocations()) {
				if (lobby.getLocation().equals(current.getLocation())) {
					map.removeLobbyLocation(current);
					return true;
				}
			}
			for (SerializedLocation store : getStoreLocations()) {
				if (store.getLocation().equals(current.getLocation())) {
					map.removeStoreLocation(current);
					return true;
				}
			}
		}
		return false;
	}

	public boolean removeLobbyLocation(Location loc) {
		if (lobbyLocations.isEmpty()) return true;
		for (SerializedLocation sl : lobbyLocations) {
			if (sl.getLocation().equals(loc)) return lobbyLocations.remove(sl);
		}
		return false;
	}

	public boolean removeHuntedLocation(Location loc) {
		if (huntedLocations.isEmpty()) return true;
		for (SerializedLocation sl : huntedLocations) {
			if (sl.getLocation().equals(loc)) return huntedLocations.remove(sl);
		}
		return false;
	}

	public boolean removeStoreLocation(Location loc) {
		if (storeLocations.isEmpty()) return true;
		for (SerializedLocation sl : storeLocations) {
			if (sl.getLocation().equals(loc)) return storeLocations.remove(sl);
		}
		return false;
	}

	/*
	 * Get location methods
	 */
	public String getLocationNameByLocation(Location loc) {
		for (SerializedLocation sl : getLocations()) {
			if (sl.getLocation().equals(loc)) return sl.getName();
		}
		return null;
	}

	/**
	 * Get all possible SerializedLocations for a given location Just in case someone put a location in two lists.
	 * @param loc
	 * @return
	 */
	public Map<SerializedLocation, LocationType> getSerializedLocation(Location loc) {
		Map<SerializedLocation, LocationType> location = new HashMap<>();
		// arena locations
		for (SerializedLocation sl : getHuntedLocations()) {
			if (sl.getLocation().equals(loc)) {
				location.put(sl, LocationType.ARENA);
				return location;
			}
		}
		// lobby
		for (SerializedLocation sl : getLobbyLocations()) {
			if (sl.getLocation().equals(loc)) {
				location.put(sl, LocationType.LOBBY);
				return location;
			}
		}
		// store
		for (SerializedLocation sl : getStoreLocations()) {
			if (sl.getLocation().equals(loc)) {
				location.put(sl, LocationType.STORE);
				return location;
			}
		}
		return location;
	}

	/** Get a location it's name */
	public SerializedLocation getLocationByName(String name) {
		for (SerializedLocation loc : getLocations()) {
			if (loc.getName().equalsIgnoreCase(name)) return loc;
		}
		return null;
	}

	/*
	 * Get random location methods
	 */
	/**
	 * Return a random serialized location from {@link #huntedLocations}
	 * @return random location
	 */
	public SerializedLocation getRandomHuntedLocation() {
		if (huntedLocations.isEmpty() || huntedLocations == null) return null;
		List<SerializedLocation> list = new ArrayList<>();
		list.addAll(huntedLocations);
		Collections.shuffle(list);
		return list.get(0);
	}

	/**
	 * Return a random serialized location from {@link #lobbyLocations}
	 *
	 * @return
	 */
	public SerializedLocation getRandomLobbyLocation() {
		if (lobbyLocations.isEmpty() || lobbyLocations == null) return null;
		List<SerializedLocation> list = new ArrayList<>();
		list.addAll(lobbyLocations);
		Collections.shuffle(list);
		return list.get(0);
	}

	/**
	 * Return a random serialized location from {@link #storeLocations}
	 *
	 * @return
	 */
	public SerializedLocation getRandomStoreLocation() {
		if (storeLocations.isEmpty() || storeLocations == null) return null;
		List<SerializedLocation> list = new ArrayList<>();
		list.addAll(storeLocations);
		Collections.shuffle(list);
		return list.get(0);
	}

	public List<SerializedLocation> getLocationsByType(LocationType type) {
        switch(type) {
            case ARENA:
                return getHuntedLocations();
            case LOBBY:
                return getLobbyLocations();
            case STORE:
                return getStoreLocations();
            default:
                return new ArrayList<SerializedLocation>();
        }
    }
    
	/*
	 * Generic getters
	 */
	/** Return all {@link #lobbyLocations} */
	public List<SerializedLocation> getLobbyLocations() {
		return lobbyLocations;
	}

	/** Return all {@link #huntedLocations} */
	public List<SerializedLocation> getHuntedLocations() {
		return huntedLocations;
	}

	public List<SerializedLocation> getStoreLocations() {
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
