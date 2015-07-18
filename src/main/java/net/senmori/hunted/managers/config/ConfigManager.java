package net.senmori.hunted.managers.config;

import java.io.File;
import java.io.IOException;

import net.senmori.hunted.util.LogHandler;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager 
{
	public static void init()
	{
		// create plugin directory if it doesn't exist
		if(!net.senmori.hunted.Hunted.getInstance().getDataFolder().exists())
		{
			net.senmori.hunted.Hunted.getInstance().getDataFolder().mkdirs();
		}
		
		net.senmori.hunted.Hunted.pluginConfigFile = new File(net.senmori.hunted.Hunted.getInstance().getDataFolder(), "config.yml");
		if(!net.senmori.hunted.Hunted.pluginConfigFile.exists())
		{
		    net.senmori.hunted.Hunted.pluginConfigFile.getParentFile().mkdirs();
		    net.senmori.hunted.Hunted.getInstance().saveDefaultConfig();
		}
		net.senmori.hunted.Hunted.pluginConfig = YamlConfiguration.loadConfiguration(net.senmori.hunted.Hunted.pluginConfigFile);
		loadConfig();
	}
	
	private static void loadConfig()
	{
		net.senmori.hunted.Hunted.debug = net.senmori.hunted.Hunted.getInstance().getConfig().getBoolean("debug");
		LogHandler.info("Debug: " + net.senmori.hunted.Hunted.debug);
		
		net.senmori.hunted.Hunted.defaultCooldown = net.senmori.hunted.Hunted.getInstance().getConfig().getInt("cooldown");
		LogHandler.info("Default cooldown: " + net.senmori.hunted.Hunted.defaultCooldown);
		
		net.senmori.hunted.Hunted.maxEffectLength = net.senmori.hunted.Hunted.getInstance().getConfig().getInt("max-effect-length");
		LogHandler.info("Max effect length: " + net.senmori.hunted.Hunted.maxEffectLength);
		
		net.senmori.hunted.Hunted.maxEnchantLevel = net.senmori.hunted.Hunted.getInstance().getConfig().getInt("max-enchant-level");
		LogHandler.info("Maximum enchant level: " + net.senmori.hunted.Hunted.maxEnchantLevel);
		
		net.senmori.hunted.Hunted.enchantChance = net.senmori.hunted.Hunted.getInstance().getConfig().getInt("enchant-chance");
		LogHandler.info("Enchantment chance: " + net.senmori.hunted.Hunted.enchantChance);
		
		net.senmori.hunted.Hunted.potionTierChance = net.senmori.hunted.Hunted.getInstance().getConfig().getInt("potion-tier2-chance");
		LogHandler.info("Potion Tier Chance: " + net.senmori.hunted.Hunted.potionTierChance);
		
		net.senmori.hunted.Hunted.nearbyRadius = net.senmori.hunted.Hunted.getInstance().getConfig().getInt("radius");
		LogHandler.info("Radius to search: " + net.senmori.hunted.Hunted.nearbyRadius);
		
		net.senmori.hunted.Hunted.maxAmplifierLevel = net.senmori.hunted.Hunted.getInstance().getConfig().getInt("max-amplifier-level");
		LogHandler.info("Maximum amplifier level: " + net.senmori.hunted.Hunted.maxAmplifierLevel);
		
		net.senmori.hunted.Hunted.smiteTeleportChance = net.senmori.hunted.Hunted.getInstance().getConfig().getInt("smite-teleport-chance");
		LogHandler.info("Smite teleport chance: " + net.senmori.hunted.Hunted.smiteTeleportChance);
		
		net.senmori.hunted.Hunted.ascentedItemChance = net.senmori.hunted.Hunted.getInstance().getConfig().getInt("ascented-chance");
		LogHandler.info("Ascented item chance: " + net.senmori.hunted.Hunted.ascentedItemChance);
		
		net.senmori.hunted.Hunted.receiveEffectTwice = net.senmori.hunted.Hunted.getInstance().getConfig().getInt("receive-effect-twice");
		LogHandler.info("Receive effect twice: " + net.senmori.hunted.Hunted.receiveEffectTwice);
		
		net.senmori.hunted.Hunted.maxArrowsPerReward = net.senmori.hunted.Hunted.getInstance().getConfig().getInt("max-arrows");
		LogHandler.info("Max Arrows: " + net.senmori.hunted.Hunted.maxArrowsPerReward);
		
		net.senmori.hunted.Hunted.activeWorld = net.senmori.hunted.Hunted.getInstance().getConfig().getString("world");
		// if the active world isn't provided use the first one.
		if(net.senmori.hunted.Hunted.activeWorld.isEmpty() || net.senmori.hunted.Hunted.activeWorld.length() < 1)
		{
			net.senmori.hunted.Hunted.activeWorld = Bukkit.getWorlds().get(0).toString();
			LogHandler.warning("Hunted world not set in config! World set to: " + net.senmori.hunted.Hunted.activeWorld);
		}
		LogHandler.info("Active world: " + net.senmori.hunted.Hunted.activeWorld);
		
		try
		{
			net.senmori.hunted.Hunted.pluginConfig.save(net.senmori.hunted.Hunted.pluginConfigFile);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}