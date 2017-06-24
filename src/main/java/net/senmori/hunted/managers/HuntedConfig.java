package net.senmori.hunted.managers;

import java.io.File;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.ConfigOption;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class HuntedConfig {
    private final File configFile;
    private final FileConfiguration config;
    private Hunted plugin;

    public HuntedConfig(Hunted plugin, String fileName) {
        this.plugin = plugin;

        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        this.configFile = new File(plugin.getDataFolder(), fileName);
        if(this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            plugin.saveDefaultConfig();
        }

        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public ConfigOption getConfigOption(ConfigOption option) {
        option.setValue(getConfig().getString(option.getPath()));
        return option;
    }
}
