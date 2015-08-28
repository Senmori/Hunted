package net.senmori.hunted.lib;

import java.io.File;
import java.io.IOException;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.stones.Stone;

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
        file = new File(plugin.getDataFolder() + File.pathSeparator + "configurations", name);
        
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
        config = YamlConfiguration.loadConfiguration(file);
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
        config.set(parent + loc.getName() + ".x", loc.getX());
        config.set(parent + loc.getName() + ".y", loc.getY());
        config.set(parent + loc.getName() + ".z", loc.getZ());
        save();
    }
    
    private void saveStone(Stone stone, SerializedLocation loc) {
        
    }
    
    
    /** Load this configuration from file into appropriate maps */
    private void loadFromFile() {
        // l
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
        return file;
    }
    
    public FileConfiguration getConfig() {
        return config;
    }
    
    public String getName() {
        return name;
    }
}
