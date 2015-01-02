package net.senmori.hunted.managers;

import java.io.File;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.stones.AdminStone;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.InfoStone;
import net.senmori.hunted.stones.TeleportStone;
import net.senmori.hunted.util.FileUtil;
import net.senmori.hunted.util.LogHandler;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager 
{
	public static void setupConfig()
	{
		Hunted.pluginConfig = Hunted.getInstance().getConfig();
		
		Hunted.pluginConfigFile = new File(Hunted.getInstance().getDataFolder(), "config.yml");
		if(!Hunted.pluginConfigFile.exists())
		{
		    Hunted.pluginConfigFile.getParentFile().mkdirs();
		    FileUtil.copy(Hunted.getInstance().getResource("config.yml"), Hunted.pluginConfigFile);
		}
		
		Hunted.stoneConfigFile = new File(Hunted.getInstance().getDataFolder(), "stones.yml");
		if(!Hunted.stoneConfigFile.exists())
		{
			Hunted.stoneConfigFile.getParentFile().mkdirs();
			FileUtil.copy(Hunted.getInstance().getResource("stones.yml"), Hunted.stoneConfigFile);
		}
		
		Hunted.lootConfigFile = new File(Hunted.getInstance().getDataFolder(), "loot.yml");
		if(!Hunted.lootConfigFile.exists())
		{
			Hunted.lootConfigFile.getParentFile().mkdirs();
			FileUtil.copy(Hunted.getInstance().getResource("loot.yml"), Hunted.lootConfigFile);
		}
		
		Hunted.lootConfig = YamlConfiguration.loadConfiguration(Hunted.lootConfigFile);
		
		Hunted.stoneConfig = YamlConfiguration.loadConfiguration(Hunted.stoneConfigFile);
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
		
		Hunted.maxPotsPerReward = Hunted.getInstance().getConfig().getInt("max-pots-per-reward");
		LogHandler.debug("Max pots per reward: " + Hunted.maxPotsPerReward);
		
		Hunted.maxEffectLength = Hunted.getInstance().getConfig().getInt("max-effect-length");
		LogHandler.debug("Max effect length: " + Hunted.maxEffectLength);
		
		Hunted.nearbyRadius = Hunted.getInstance().getConfig().getInt("radius");
		LogHandler.debug("Radius to search: " + Hunted.nearbyRadius);
		
		Hunted.killStreakAmount = Hunted.getInstance().getConfig().getInt("killstreak-amount");
		LogHandler.debug("Killstreak amount: " + Hunted.killStreakAmount);
	}
	
	private static void loadStoneConfig()
	{
		String node = "guardian_stones";
		for(String s : Hunted.stoneConfig.getConfigurationSection(node).getKeys(false))
		{
			if(s.isEmpty()) return;
			
			int x = Hunted.stoneConfig.getInt(node + "." + s + ".x");
			int y = Hunted.stoneConfig.getInt(node + "." + s + ".y");
			int z = Hunted.stoneConfig.getInt(node + "." + s + ".z");
			String world = Hunted.stoneConfig.getString(node + "." + s + ".world");
			String type = Hunted.stoneConfig.getString(node + "." + s + ".type");
			
			SerializedLocation loc = new SerializedLocation(new Location(Bukkit.getWorld(world), (double)x, (double)y, (double)z), s);
			
			switch(type)
			{
				case "guardian":
						GuardianStone temp = new GuardianStone(loc);
						Integer cooldown = Hunted.stoneConfig.getInt(node + "." + s + ".cooldown");
						if(cooldown != null && cooldown > 0)
						{
							temp.setCustomCooldown(cooldown);
						}
					break;
				case "admin":
					new AdminStone(loc);
					break;
				case "info":
					new InfoStone(loc);
					break;
				case "teleport":
					new TeleportStone(loc);
					break;
				default:
					// Yell at people here
					break;
			}
		}
	}
}