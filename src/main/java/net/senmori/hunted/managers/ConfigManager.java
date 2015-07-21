package net.senmori.hunted.managers;

import java.io.File;
import java.io.IOException;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.TeleportStone;
import net.senmori.hunted.util.LogHandler;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;




public class ConfigManager 
{
	// settings
	public static boolean debug;
	public static int defaultCooldown; // in minutes, convert to milliseconds(n*60000)
	public static int maxEffectLength;
	public static int minEffectLength;
	public static int maxEnchantLevel;
	public static int enchantChance;
	public static int maxAmplifierLevel;
	public static int nearbyRadius;
	public static int potionTierChance;
	public static int smiteTeleportChance;
	public static int ascentedItemChance;
	public static int receiveEffectTwice;
	public static int maxArrowsPerReward;
	public static int maxPotsPerReward;
	public static String activeWorld;
	
	
	public static void init()
	{
		// create plugin directory if it doesn't exist
		if(!Hunted.getInstance().getDataFolder().exists())
		{
			Hunted.getInstance().getDataFolder().mkdirs();
		}
		
		Hunted.pluginConfigFile = new File(Hunted.getInstance().getDataFolder(), "config.yml");
		if(!Hunted.pluginConfigFile.exists())
		{
		    Hunted.pluginConfigFile.getParentFile().mkdirs();
		    Hunted.getInstance().saveDefaultConfig();
		}
		Hunted.config = YamlConfiguration.loadConfiguration(Hunted.pluginConfigFile);
		loadConfig();
		loadStones();
		loadLocations();
	}
	
	private static void loadConfig()
	{
		// how would these two happen? If so, just delete and reload config .-.
		if(Hunted.getInstance().getConfig().getConfigurationSection("settings") == null) return;
		if(Hunted.getInstance().getConfig().getConfigurationSection("settings").getKeys(false).size() < 1) return;
		
		debug = Hunted.getInstance().getConfig().getBoolean("settings.debug");
		defaultCooldown = Hunted.getInstance().getConfig().getInt("settings.cooldown");		
		maxEffectLength = Hunted.getInstance().getConfig().getInt("settings.max-effect-length");
		minEffectLength = Hunted.getInstance().getConfig().getInt("settings.min-effect-length");
		maxEnchantLevel = Hunted.getInstance().getConfig().getInt("settings.max-enchant-level");		
		enchantChance = Hunted.getInstance().getConfig().getInt("settings.enchant-chance");		
		potionTierChance = Hunted.getInstance().getConfig().getInt("settings.potion-tier2-chance");	
		nearbyRadius = Hunted.getInstance().getConfig().getInt("settings.radius");	
		maxAmplifierLevel = Hunted.getInstance().getConfig().getInt("settings.max-amplifier-level");	
		smiteTeleportChance = Hunted.getInstance().getConfig().getInt("settings.smite-teleport-chance");	
		ascentedItemChance = Hunted.getInstance().getConfig().getInt("settings.ascented-chance");	
		receiveEffectTwice = Hunted.getInstance().getConfig().getInt("settings.receive-effect-twice");	
		maxArrowsPerReward = Hunted.getInstance().getConfig().getInt("settings.max-arrows");	
		activeWorld = Hunted.getInstance().getConfig().getString("settings.world");
		
		// if the active world isn't provided or doesn't exist use the first one found.
		if(activeWorld.isEmpty() || activeWorld.length() < 1 || Bukkit.getWorld(activeWorld) == null)
		{
			LogHandler.warning(activeWorld + " does not exist!");
			activeWorld = Bukkit.getWorlds().get(0).toString();
			LogHandler.warning("Hunted world set to : " + activeWorld);
		}
		save();
	}
	
	/** Load all {@link Stone} from config */
	private static void loadStones()
	{
		// if there are no stones, don't do the rest or it's null
		if(Hunted.getInstance().getConfig().getConfigurationSection("stones") == null) return;
		if(Hunted.getInstance().getConfig().getConfigurationSection("stones").getKeys(false).size() < 1) return;
		
		for(String parent : Hunted.config.getConfigurationSection("stones").getKeys(false))
		{
			if(parent.isEmpty()) continue; // why is this empty! D:
			
			int x = Hunted.getInstance().getConfig().getInt("stones." + parent + ".x");
			int y = Hunted.getInstance().getConfig().getInt("stones." + parent + ".y");
			int z = Hunted.getInstance().getConfig().getInt("stones." + parent + ".z");
			String world = Hunted.getInstance().getConfig().getString("stones." + parent + ".world");
			String type = Hunted.getInstance().getConfig().getString("stones." + parent + ".type");
			
			SerializedLocation loc = new SerializedLocation(x,y,z,world,parent);
			
			switch(type)
			{
				case "guardian":
						GuardianStone temp = new GuardianStone(loc);
						temp.setCooldown(Hunted.config.getInt("stones." + parent + ".cooldown"));
					break;
				case "teleport":
					new TeleportStone(loc);
					break;
				default:
					// Yell at people here
					break;
			}
		}
		save();
	}
	
	/** Load {@link SpawnManager#huntedLocations} and {@link SpawnManager#lobbyLocations} */
	private static void loadLocations()
    {
		if (Hunted.getInstance().getConfig().getConfigurationSection("lobby-loc") != null)
        {
	        if (Hunted.getInstance().getConfig().getConfigurationSection("lobby-loc").getKeys(false).size() > 0)
            {
	            for (String name : Hunted.getInstance().getConfig().getConfigurationSection("lobby-loc").getKeys(false))
	            {
		            int x = Hunted.getInstance().getConfig().getInt("lobby-loc." + name + ".x");
		            int y = Hunted.getInstance().getConfig().getInt("lobby-loc." + name + ".y");
		            int z = Hunted.getInstance().getConfig().getInt("lobby-loc." + name + ".z");

		            SpawnManager.addLobbyLocation(new SerializedLocation(x, y, z, ConfigManager.activeWorld, name));
	            }
            }
        }
		
		if (Hunted.getInstance().getConfig().getConfigurationSection("hunted-loc") != null)
        {
	        if (Hunted.getInstance().getConfig().getConfigurationSection("hunted-loc").getKeys(false).size() > 0)
            {
	            // hunted locations
	            for (String name : Hunted.getInstance().getConfig().getConfigurationSection("hunted-loc").getKeys(false))
	            {
		            int x = Hunted.getInstance().getConfig().getInt("hunted-loc." + name + ".x");
		            int y = Hunted.getInstance().getConfig().getInt("hunted-loc." + name + ".y");
		            int z = Hunted.getInstance().getConfig().getInt("hunted-loc." + name + ".z");

		            SpawnManager.addHuntedLocation(new SerializedLocation(x, y, z, ConfigManager.activeWorld, name));
	            }
            }
        }
		save();
    }

	public static void save()
	{
		try
		{
			Hunted.config.save(Hunted.pluginConfigFile);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}