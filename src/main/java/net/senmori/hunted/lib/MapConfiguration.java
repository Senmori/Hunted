package net.senmori.hunted.lib;

import java.io.File;
import java.io.IOException;
import net.senmori.hunted.Hunted;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
        if(! file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }
    /*
     * Getters
     */
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

    private void save() {
        try {
            config.save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
