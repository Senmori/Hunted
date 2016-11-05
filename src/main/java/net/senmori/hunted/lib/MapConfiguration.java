package net.senmori.hunted.lib;

import com.google.common.io.PatternFilenameFilter;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.stones.Stone.StoneType;
import net.senmori.hunted.stones.TeleportStone;
import net.senmori.hunted.util.LogHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MapConfiguration {
    private final static String MAP_LOCATIONS_KEY = "spawn-loc";
    private final static String LOBBY_LOCATIONS_KEY = "lobby-loc";
    private final static String STORE_LOCATIONS_KEY = "store-loc";

	private Hunted plugin;
	private String name;

	private File file;
	private FileConfiguration config;

	public MapConfiguration(String name) {
		plugin = Hunted.getInstance();
		this.name = name;
		file = new File(plugin.getDataFolder() + File.separator + "configurations", name + ".yml");

		// if file doesn't exist, create it
		// check for parent dir existence as well
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
    
	public void saveStoneLocation(Stone stone, Location loc) {
		saveStone(stone, new SerializedLocation(loc, stone.getName()));
		save();
	}

	public void saveLobbyLocation(SerializedLocation loc) {
		saveLocation(loc, LOBBY_LOCATIONS_KEY);
		save();
	}

	public void saveArenaLocation(SerializedLocation loc) {
		saveLocation(loc, MAP_LOCATIONS_KEY);
		save();
	}

	public void saveStoreLocation(SerializedLocation loc) {
		saveLocation(loc, STORE_LOCATIONS_KEY);
		save();
	}

	private void saveLocation(SerializedLocation loc, String parent) {
		getConfig().set(parent + "." +  loc.getName() + ".x", loc.getX());
		getConfig().set(parent + "." +  loc.getName() + ".y", loc.getY());
		getConfig().set(parent + "." +  loc.getName() + ".z", loc.getZ());
		save();
	}

	// Save name, location, cooldown(if different than global), and type
	private void saveStone(Stone stone, SerializedLocation loc) {
		String name = loc.getName();
		getConfig().set("stone." + name + ".x", loc.getX());
		getConfig().set("stone." + name + ".y", loc.getY());
		getConfig().set("stone." + name + ".z", loc.getZ());
		getConfig().set("stone." + name + ".type", stone.getType());

		if (stone.getType().equals(StoneType.GUARDIAN)) {
			GuardianStone gStone = (GuardianStone) stone;
			// store cooldown length if different than global cooldown
			if (gStone.getCooldown() != Hunted.getInstance().getConfigManager().defaultCooldown) {
				getConfig().set("stone." + name + ".cooldown", ((GuardianStone) stone).getCooldown());
			}
			// if stone is not active, store elapsed time
			if (!gStone.isActive()) {
				getConfig().set("stone." + name + ".elapsedTime", gStone.getElapsedTime());
			}
		}
		save();
	}

	/*
	 * Remove Location methods
	 */
	private void removeLocation(SerializedLocation loc, String parent) {
		getConfig().set(parent + loc.getName(), null);
		save();
	}

	public void removeHuntedLocation(SerializedLocation loc) {
		removeLocation(loc, MAP_LOCATIONS_KEY);
		save();
	}

	public void removeStoreLocation(SerializedLocation loc) {
		removeLocation(loc, STORE_LOCATIONS_KEY);
		save();
	}

	public void removeLobbyLocation(SerializedLocation loc) {
		removeLocation(loc, LOBBY_LOCATIONS_KEY);
		save();
	}

	public void removeStone(Stone stone) {
		removeLocation(new SerializedLocation(stone.getLocation(), stone.getName()), "stone");
		save();
	}

	/** Load this configuration from file into appropriate maps */
	public void load() {
		LogHandler.info("Loading new map configuration (" + name + ")");
		// clear locations in current maps
		plugin.getSpawnManager().getHuntedLocations().clear();
		plugin.getSpawnManager().getLobbyLocations().clear();
		plugin.getSpawnManager().getStoreLocations().clear();
		plugin.getStoneManager().getStones().clear();
		LogHandler.info("Cleared old map configuration!");
		World world = Bukkit.getWorld(plugin.getConfigManager().activeWorld);
		// load hunted locations
        if(config.getConfigurationSection(MAP_LOCATIONS_KEY) != null && !config.getConfigurationSection(MAP_LOCATIONS_KEY).getKeys(true).isEmpty()) {
            for (String name : config.getConfigurationSection(MAP_LOCATIONS_KEY).getKeys(true)) {
                int x = config.getInt("spawn-loc." + name + ".x");
                int y = config.getInt("spawn-loc." + name + ".y");
                int z = config.getInt("spawn-loc." + name + ".z");
        
                Location loc = new Location(world, x, y, z);
                plugin.getSpawnManager().addArenaLocation(loc, name);
            }
        }

		// load lobby locations
        if(config.getConfigurationSection(LOBBY_LOCATIONS_KEY) != null && !config.getConfigurationSection(LOBBY_LOCATIONS_KEY).getKeys(true).isEmpty()) {
            for (String name : config.getConfigurationSection(LOBBY_LOCATIONS_KEY).getKeys(true)) {
                int x = config.getInt("lobby-loc." + name + ".x");
                int y = config.getInt("lobby-loc." + name + ".y");
                int z = config.getInt("lobby-loc." + name + ".z");
        
                Location loc = new Location(world, x, y, z);
                plugin.getSpawnManager().addLobbyLocation(loc, name);
            }
        }

		// load store locations
        if(config.getConfigurationSection(STORE_LOCATIONS_KEY) != null && !config.getConfigurationSection(STORE_LOCATIONS_KEY).getKeys(true).isEmpty()) {
            for (String name : config.getConfigurationSection(STORE_LOCATIONS_KEY).getKeys(true)) {
                int x = config.getInt("store-loc." + name + ".x");
                int y = config.getInt("store-loc." + name + ".y");
                int z = config.getInt("store-loc." + name + ".z");
        
                Location loc = new Location(world, x, y, z);
                plugin.getSpawnManager().addStoreLocation(loc, name);
            }
        }
        
		// load stone locations
        if(config.getConfigurationSection("stone") != null && !config.getConfigurationSection("stone").getKeys(true).isEmpty()) {
            for (String name : config.getConfigurationSection("stone").getKeys(true)) {
                int x = config.getInt("stone." + name + ".x");
                int y = config.getInt("stone." + name + ".y");
                int z = config.getInt("stone." + name + ".z");
                String type = config.getString("stone." + name + ".type");
                int cooldown = 0;
                int lastActivated = 0;
        
                Location loc = new Location(world, x, y, z);
        
                // if it's a guardian stone
                if (type.equalsIgnoreCase("guardian")) {
                    if (!(config.getInt("stone." + name + ".cooldown", 0) <= 0)) {
                        // stone has a custom cooldown
                        cooldown = config.getInt("stone." + name + ".cooldown");
                    }
                    if (!(config.getInt("stone." + name + ".elapsedTime", 0) <= 0)) {
                        // set the stone's elapsed time
                        lastActivated = config.getInt("stone." + name + ".elapsedTime");
                    }
            
                    GuardianStone gs = new GuardianStone(new SerializedLocation(loc, name));
                    if (cooldown > 0) {
                        gs.setCooldown(cooldown);
                    }
                    if (lastActivated > 0) {
                        // lastActivated will be how long ago this stone was activated
                        gs.setLastActivated(gs.getCooldown() - TimeUnit.MINUTES.toMillis(lastActivated));
                    }
                    continue;
                }
                new TeleportStone(new SerializedLocation(loc, name));
            }
        }
		save();
	}

	/*
	 * Getters
	 */
	public File getFile() {
		if (file == null) {
			file = new File(plugin.getDataFolder() + File.separator + "configurations", name + ".yml");
		}
		return file;
	}

	public FileConfiguration getConfig() {
		if (config == null) {
			config = YamlConfiguration.loadConfiguration(getFile());
		}
		return config;
	}

	public String getName() {
		return name;
	}

	private void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
