package net.senmori.hunted.managers;


import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.MapConfiguration;
import net.senmori.hunted.util.LogHandler;
import net.senmori.hunted.util.Reference;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
    // settings
    public boolean canReceivePotions;
    public boolean signEntrance;
    public boolean signExit;
    public boolean portalEntrance;
    public boolean portalExit;
    public int signDepth;
    public int portalSignDepth;
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

    private Hunted plugin;
    private File file;
    private FileConfiguration config;

    // map configuration holder
    private Map<String, MapConfiguration> mapConfigurations;

    public ConfigManager(Hunted plugin) {
        this.plugin = plugin;
        mapConfigurations = new HashMap<String, MapConfiguration>();
        init();
    }

    private void init() {
        // create plugin directory if it doesn't exist
        if(! Hunted.getInstance().getDataFolder().exists()) {
            Hunted.getInstance().getDataFolder().mkdirs();
        }

        file = new File(Hunted.getInstance().getDataFolder(), "config.yml");
        if(! file.exists()) {
            plugin.saveDefaultConfig();
        }

        config = YamlConfiguration.loadConfiguration(file);
        loadConfig();
    }

    private void loadConfig() {
        // how would these two happen? If so, just delete and reload config
        if(Hunted.getInstance().getConfig().getConfigurationSection("settings") == null) {
            throw new IllegalStateException("Config file was modified beyond repair. Please delete the file and restart the server");
        }
        if(Hunted.getInstance().getConfig().getConfigurationSection("settings").getKeys(false).size() < 1) {
            throw new IllegalStateException("Config settings were modified beyond repair. Please delete the file and restart the server");
        }

        defaultCooldown = plugin.getConfig().getInt("settings.cooldown", 5);
        maxEffectLength = plugin.getConfig().getInt("settings.max-effect-length", 12);
        minEffectLength = plugin.getConfig().getInt("settings.min-effect-length");
        maxEnchantLevel = plugin.getConfig().getInt("settings.max-enchant-level", 2);
        enchantChance = plugin.getConfig().getInt("settings.enchant-chance", 10);
        canReceivePotions = plugin.getConfig().getBoolean("settings.can-receive-potions");
        maxPotsPerReward = plugin.getConfig().getInt("max-potions", 2);
        potionTierChance = plugin.getConfig().getInt("settings.potion-tier2-chance", 10);
        nearbyRadius = plugin.getConfig().getInt("settings.radius", 50);
        maxAmplifierLevel = plugin.getConfig().getInt("settings.max-amplifier-level", 2);
        smiteTeleportChance = plugin.getConfig().getInt("settings.smite-teleport-chance", 10);
        ascentedItemChance = plugin.getConfig().getInt("settings.ascented-chance", 16);
        receiveEffectTwice = plugin.getConfig().getInt("settings.receive-effect-twice", 20);
        maxArrowsPerReward = plugin.getConfig().getInt("settings.max-arrows", 32);
        activeWorld = plugin.getConfig().getString("settings.world", "world");

        signEntrance = plugin.getConfig().getBoolean("settings.sign-entrance", true);
        signExit = plugin.getConfig().getBoolean("settings.sign-exit", true);
        portalEntrance = plugin.getConfig().getBoolean("settings.portal-entrance", true);
        portalExit = plugin.getConfig().getBoolean("settings.portal-exit", true);

        signDepth = plugin.getConfig().getInt("settings.sign-depth", 1);
        portalSignDepth = plugin.getConfig().getInt("settings.portal-sign-depth", 1);

        // if the active world isn't provided or doesn't exist use the first one found.
        if(Bukkit.getWorld(activeWorld) == null || activeWorld.isEmpty() || activeWorld.length() < 1) {
            LogHandler.warning(activeWorld + " does not exist!");
            activeWorld = Bukkit.getWorlds().get(0).getName();
            LogHandler.warning("World set to : " + activeWorld);
        }

        save();
    }

    public void loadActiveMapConfiguration() {
        activeMapConfiguration = plugin.getConfig().getString("settings.active-map-configuration");
        if(activeMapConfiguration == null || activeMapConfiguration.isEmpty()) {
            LogHandler.warning("There was an error importing map configurations! Please set a new map configuration.");
        }
        addMapConfiguration(activeMapConfiguration, new MapConfiguration(activeMapConfiguration));
        setActiveMapConfiguration(activeMapConfiguration);
    }

    public void save() {
        try {
            plugin.getConfig().save(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void addMapConfiguration(String name, MapConfiguration map) {
        if(mapConfigurations == null) {
            mapConfigurations = new LinkedHashMap<>();
        }
        mapConfigurations.put(name, map);
    }

    public MapConfiguration getMapConfiguration(String name) {
        if(! mapConfigurations.containsKey(name))
            return new MapConfiguration(name);
        return mapConfigurations.get(name);
    }

    public MapConfiguration getActiveMapConfiguration() {
        return mapConfigurations.get(activeMapConfiguration);
    }

    /*
     * ########################### Map Configuration section ###########################
     */
    public void setActiveMapConfiguration(String name) {
        if(mapConfigurations.containsKey(name) && mapConfigurations.get(name) != null) {
            // load from hashmap & save old map just in case
            activeMapConfiguration = name;
            getActiveMapConfiguration().load();
            return;
        }
        LogHandler.warning(MessageFormat.format(Reference.ErrorMessage.IMPORT_ERROR, name));
    }

    public Map<String, MapConfiguration> getMapConfigurations() {
        return mapConfigurations;
    }

    /*
     * File getters
     */
    public File getConfigFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }
}