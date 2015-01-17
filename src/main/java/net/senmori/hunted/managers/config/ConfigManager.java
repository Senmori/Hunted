package net.senmori.hunted.managers.config;

import java.io.File;
import java.util.List;

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
		
		Hunted.maxEffectLength = Hunted.getInstance().getConfig().getInt("max-effect-length");
		LogHandler.debug("Max effect length: " + Hunted.maxEffectLength);
		
		Hunted.maxEnchantLevel = Hunted.getInstance().getConfig().getInt("max-enchant-level");
		LogHandler.debug("Maximum enchant level: " + Hunted.maxEnchantLevel);
		
		Hunted.enchantChance = Hunted.getInstance().getConfig().getInt("enchant-chance");
		LogHandler.debug("Enchantment chance: " + Hunted.enchantChance);
		
		Hunted.potionTierChance = Hunted.getInstance().getConfig().getInt("potion-tier2-chance");
		LogHandler.debug("Potion Tier Chance: " + Hunted.potionTierChance);
		
		Hunted.nearbyRadius = Hunted.getInstance().getConfig().getInt("radius");
		LogHandler.debug("Radius to search: " + Hunted.nearbyRadius);
		
		Hunted.maxAmplifierLevel = Hunted.getInstance().getConfig().getInt("max-amplifier-level");
		LogHandler.debug("Maximum amplifier level: " + Hunted.maxAmplifierLevel);
		
		Hunted.smiteTeleportChance = Hunted.getInstance().getConfig().getInt("smite-teleport-chance");
		LogHandler.debug("Smite teleport chance: " + Hunted.smiteTeleportChance);
		
		Hunted.ascentedItemChance = Hunted.getInstance().getConfig().getInt("ascented-chance");
		LogHandler.debug("Ascented item chance: " + Hunted.ascentedItemChance);
		
		Hunted.receiveEffectTwice = Hunted.getInstance().getConfig().getInt("receive-effect-twice");
		LogHandler.debug("Receive effect twice: " + Hunted.receiveEffectTwice);
	}
	
	private static void loadStoneConfig()
	{
		String node = "stones";
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
					InfoStone is = new InfoStone(loc);
					String info = Hunted.stoneConfig.getString(node + "." + s + ".info");
					if(info != null)
					{
						List<String> infoList = Hunted.stoneConfig.getStringList("stone_info." + s);
						is.addOrCreateInfo("stone_info." + s, infoList);
					}
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