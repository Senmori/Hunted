package net.senmori.hunted.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.MapConfiguration;
import net.senmori.hunted.util.LogHandler;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager  {
    private Hunted plugin;
    
    
	// settings
	public boolean debug;
	public int defaultCooldown; // in minutes, convert to milliseconds(n*60000)
	public int maxEffectLength;
	public int minEffectLength;
	public int maxEnchantLevel;
	public int enchantChance;
	public int maxAmplifierLevel;
	public int nearbyRadius;
	public int potionTierChance;
	public int smiteTeleportChance;
	public int ascentedItemChance;
	public int receiveEffectTwice;
	public int maxArrowsPerReward;
	public int maxPotsPerReward;
	public String activeWorld;
	public String activeMapConfiguration;
	
	// database config options
	public String dbUser;
	public String dbPassword;
	public String dbHost;
	public int dbPort;
	public String dbName;
	
	
	// map configuration holder
	private Map<String, MapConfiguration> mapConfigurations;
	
	public ConfigManager(Hunted plugin) {
	    this.plugin = plugin;
	    mapConfigurations = new HashMap<>();
	}
	
	public void init() {
		// create plugin directory if it doesn't exist
		if(!Hunted.getInstance().getDataFolder().exists()) {
			Hunted.getInstance().getDataFolder().mkdirs();
		}
		
		Hunted.pluginConfigFile = new File(Hunted.getInstance().getDataFolder(), "config.yml");
		if(!Hunted.pluginConfigFile.exists()) {
		    Hunted.pluginConfigFile.getParentFile().mkdirs();
		    Hunted.getInstance().saveDefaultConfig();
		}
		Hunted.config = YamlConfiguration.loadConfiguration(Hunted.pluginConfigFile);
		loadConfig();
		//loadStones();
		//loadLocations();
	}
	
	private void loadConfig() {
		// how would these two happen? If so, just delete and reload config .-.
		if(Hunted.getInstance().getConfig().getConfigurationSection("settings") == null) return;
		if(Hunted.getInstance().getConfig().getConfigurationSection("settings").getKeys(false).size() < 1) return;
		
		debug = plugin.getConfig().getBoolean("settings.debug", false);
		defaultCooldown = plugin.getConfig().getInt("settings.cooldown", 5);		
		maxEffectLength = plugin.getConfig().getInt("settings.max-effect-length", 12);
		maxEnchantLevel = plugin.getConfig().getInt("settings.max-enchant-level", 2);		
		enchantChance = plugin.getConfig().getInt("settings.enchant-chance", 10);		
		potionTierChance = plugin.getConfig().getInt("settings.potion-tier2-chance", 10);	
		nearbyRadius = plugin.getConfig().getInt("settings.radius", 50);	
		maxAmplifierLevel = plugin.getConfig().getInt("settings.max-amplifier-level", 2);	
		smiteTeleportChance = plugin.getConfig().getInt("settings.smite-teleport-chance", 10);	
		ascentedItemChance = plugin.getConfig().getInt("settings.ascented-chance", 16);	
		receiveEffectTwice = plugin.getConfig().getInt("settings.receive-effect-twice", 20);	
		maxArrowsPerReward = plugin.getConfig().getInt("settings.max-arrows", 20);	
		activeWorld = plugin.getConfig().getString("settings.world");
		activeMapConfiguration = plugin.getConfig().getString("settings.active-map-configuration");
		
		// database
		dbUser = plugin.getConfig().getString("database.username");
		dbPassword = plugin.getConfig().getString("database.password");
		dbHost = plugin.getConfig().getString("database.host");
		dbPort = plugin.getConfig().getInt("database.port");
		dbName = plugin.getConfig().getString("database.name");
		
		// if the active world isn't provided or doesn't exist use the first one found.
		if(Bukkit.getWorld(activeWorld) == null || activeWorld.isEmpty() || activeWorld.length() < 1) {
			LogHandler.warning(activeWorld + " does not exist!");
			activeWorld = Bukkit.getWorlds().get(0).getName();
			LogHandler.warning("World set to : " + activeWorld);
		}
		save();
	}

	public void save() {
		try {
			Hunted.config.save(Hunted.pluginConfigFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/* ###########################
	 *  Map Configuration section
	 * ###########################
	 */
	public MapConfiguration getActiveMapConfiguration() {
	    return mapConfigurations.get(activeMapConfiguration);
	}
	
	public void addMapConfiguration(String name, MapConfiguration config) {
	    mapConfigurations.put(name, config);
	}
	
	public MapConfiguration getMapConfiguration(String name) {
	    if(!mapConfigurations.containsKey(name)) {
	        return new MapConfiguration(name);
	    }
	    return mapConfigurations.get(name);
	}
	
	public Map<String, MapConfiguration> getMapConfigurations() {
	    return mapConfigurations;
	}
}