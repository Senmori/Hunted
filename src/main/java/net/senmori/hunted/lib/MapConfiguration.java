package net.senmori.hunted.lib;

import java.io.File;
import java.io.IOException;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.stones.Stone.StoneType;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MapConfiguration {

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
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void saveStoneLocation(Stone stone, SerializedLocation loc) {
        saveStone(stone, loc);
    }
    
    public void saveStoneLocation(Stone stone, Location loc) {
        saveStone(stone, new SerializedLocation(loc, stone.getName()));
    }
    
    public void saveLobbyLocation(SerializedLocation loc) {
        saveLocation(loc, "lobby-loc");
    }
    
    public void saveHuntedLocation(SerializedLocation loc) {
        saveLocation(loc, "hunted-loc");
    }
    
    public void saveStoreLocation(SerializedLocation loc) {
        saveLocation(loc, "store-loc");
    }
    
    private void saveLocation(SerializedLocation loc, String parent) {
        getConfig().set(parent + loc.getName() + ".x", loc.getX());
        getConfig().set(parent + loc.getName() + ".y", loc.getY());
        getConfig().set(parent + loc.getName() + ".z", loc.getZ());
        save();
    }
    
    // Save name, location, cooldown(if different than global), and type
    private void saveStone(Stone stone, SerializedLocation loc) {
        String name = stone.getName() != null ? stone.getName() : loc.getName();
        getConfig().set("stone." + name + ".x", loc.getX());
        getConfig().set("stone." + name + ".y", loc.getY());
        getConfig().set("stone." + name + ".z", loc.getZ());
        getConfig().set("stone." + name + ".type", stone.getType());
        
        if(stone.getType().equals(StoneType.GUARDIAN)) {
            GuardianStone gStone = ((GuardianStone)stone);
            // store cooldown length if different than global cooldown
            if(gStone.getCooldown() != Hunted.getInstance().getConfigManager().defaultCooldown) {
               getConfig().set("stone." + name + ".cooldown", ((GuardianStone)stone).getCooldown()); 
            }
            // if stone is not active, store elapsed time
            if(!gStone.isActive()) {
                getConfig().set("stone." + name + ".elapsedTime", gStone.getElapsedTime());
            }
        }
    }
    
    
    /** Load this configuration from file into appropriate maps */
    private void loadFromFile() {
        
    }

    private void save() {
        try {
            config.save(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public File getFile() {
        if(file == null) {
            file = new File(plugin.getDataFolder() + File.separator + "configurations", name + ".yml");
        }
        return file;
    }
    
    public FileConfiguration getConfig() {
        if(config == null) {
            config = YamlConfiguration.loadConfiguration(getFile());
        }
        return config;
    }
    
    public String getName() {
        return name;
    }
}
