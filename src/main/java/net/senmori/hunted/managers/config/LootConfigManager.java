package net.senmori.hunted.managers.config;

import java.io.File;

import net.senmori.hunted.util.FileUtil;
import net.senmori.hunted.util.LogHandler;

import org.bukkit.configuration.file.YamlConfiguration;

public class LootConfigManager
{
	public static void init()
	{
		net.senmori.hunted.Hunted.lootConfigFile = new File(net.senmori.hunted.Hunted.getInstance().getDataFolder(),"loot.yml");
		if(!net.senmori.hunted.Hunted.lootConfigFile.exists())
		{
			net.senmori.hunted.Hunted.lootConfigFile.getParentFile().mkdirs();
			FileUtil.copy(net.senmori.hunted.Hunted.getInstance().getResource("loot.yml"), net.senmori.hunted.Hunted.lootConfigFile);
		}
		
		net.senmori.hunted.Hunted.lootConfig = YamlConfiguration.loadConfiguration(net.senmori.hunted.Hunted.lootConfigFile);
		LogHandler.info("Loaded loot config file!");
		loadConfig();
	}
	
	private static void loadConfig()
	{
		
	}
}
