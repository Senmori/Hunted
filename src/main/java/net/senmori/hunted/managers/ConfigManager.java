package net.senmori.hunted.managers;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.util.FileUtil;
import net.senmori.hunted.util.LogHandler;

public class ConfigManager 
{
	private static FileConfiguration stoneConfig;
	public static void setupConfig()
	{
		Hunted.pluginConfig = Hunted.getInstance().getConfig();
		
		File pluginConfigFile = new File(Hunted.getInstance().getDataFolder(), "config.yml");
		if(!pluginConfigFile.exists())
		{
		    pluginConfigFile.getParentFile().mkdirs();
		    FileUtil.copy(Hunted.getInstance().getResource("config.yml"), pluginConfigFile);
		}
		
		File stoneConfigFile = new File(Hunted.getInstance().getDataFolder(), "stones.yml");
		if(!stoneConfigFile.exists())
		{
			stoneConfigFile.getParentFile().mkdirs();
			FileUtil.copy(Hunted.getInstance().getResource("stones.yml"), stoneConfigFile);
		}
		
		stoneConfig = YamlConfiguration.loadConfiguration(stoneConfigFile);
		loadStoneConfig();
		
		Hunted.pluginConfig = Hunted.getInstance().getConfig();
		loadConfig();
	}
	
	private static void loadConfig()
	{
		Hunted.debug = Hunted.getInstance().getConfig().getBoolean("debug");
		LogHandler.debug("Debug: " + Hunted.debug);
		
		Hunted.defaultCooldown = Hunted.getInstance().getConfig().getInt("cooldown");
		LogHandler.debug("Default cooldown: " + Hunted.defaultCooldown);
		
		Hunted.xpPerKill = Hunted.getInstance().getConfig().getInt("xp-per-kill");
		LogHandler.debug("Xp per kill: " + Hunted.xpPerKill);
		
		Hunted.xpPerStone = Hunted.getInstance().getConfig().getInt("xp-per-use");
		LogHandler.debug("Xp per use: " + Hunted.xpPerStone);
		
	}
	
	private static void loadStoneConfig()
	{
		String node = "guardian_stones";
		for(String s : stoneConfig.getConfigurationSection(node).getKeys(false))
		{
			if(s.isEmpty()) return;
			
			int x = stoneConfig.getInt(node + "." + s + ".x");
			int y = stoneConfig.getInt(node + "." + s + ".y");
			int z = stoneConfig.getInt(node + "." + s + ".z");
			String world = stoneConfig.getString(node + "." + s + ".world");
			Integer cooldown = stoneConfig.getInt(node + "." + s + ".cooldown");
			
			Location newLoc = new Location(Bukkit.getWorld(world), (double)x, (double)y, (double)z);
			GuardianStone temp = new GuardianStone(newLoc, s);
			if(cooldown != null)
			{
				temp.setCustomCooldown(cooldown);
			}
		}
	}
}
