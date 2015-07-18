package net.senmori.hunted.managers.config;

import java.io.File;

import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.TeleportStone;
import net.senmori.hunted.util.FileUtil;
import net.senmori.hunted.util.LogHandler;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.configuration.file.YamlConfiguration;

public class StoneConfigManager 
{
	public static void init()
	{
		net.senmori.hunted.Hunted.stoneConfigFile = new File(net.senmori.hunted.Hunted.getInstance().getDataFolder(),"stones.yml");
		if(!net.senmori.hunted.Hunted.stoneConfigFile.exists())
		{
			net.senmori.hunted.Hunted.stoneConfigFile.getParentFile().mkdirs();
			FileUtil.copy(net.senmori.hunted.Hunted.getInstance().getResource("stones.yml"), net.senmori.hunted.Hunted.stoneConfigFile);
		}
		
		net.senmori.hunted.Hunted.stoneConfig = YamlConfiguration.loadConfiguration(net.senmori.hunted.Hunted.stoneConfigFile);
		LogHandler.info("Loaded stone config file!");
		
		loadConfig();
	}
	
	public static void loadConfig()
	{
		// if there are no stones, don't do the rest
		if(net.senmori.hunted.Hunted.stoneConfig.getKeys(false).size() < 1) return;
		for(String parent : net.senmori.hunted.Hunted.stoneConfig.getConfigurationSection("stones").getKeys(false))
		{
			if(parent.isEmpty()) return; // why is s empty! D:
			
			int x = net.senmori.hunted.Hunted.stoneConfig.getInt(parent + ".x");
			int y = net.senmori.hunted.Hunted.stoneConfig.getInt(parent + ".y");
			int z = net.senmori.hunted.Hunted.stoneConfig.getInt(parent + ".z");
			String world = net.senmori.hunted.Hunted.stoneConfig.getString(parent + ".world");
			String type = net.senmori.hunted.Hunted.stoneConfig.getString(parent + ".type");
			
			SerializedLocation loc = new SerializedLocation(x,y,z,world,parent);
			
			switch(type)
			{
				case "guardian":
						GuardianStone temp = new GuardianStone(loc);
						Integer cooldown = net.senmori.hunted.Hunted.stoneConfig.getInt(parent + ".cooldown");
						if(cooldown != null && cooldown > 0)
						{
							temp.setCustomCooldown(cooldown);
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
		
		
		// load spawn locations
		for(String name : net.senmori.hunted.Hunted.stoneConfig.getConfigurationSection("spawn_loc").getKeys(false))
		{
			String location = net.senmori.hunted.Hunted.stoneConfig.getString(name);
			String[] loc = location.split("|");
			int x = Integer.parseInt(loc[0]);
			int y = Integer.parseInt(loc[1]);
			int z = Integer.parseInt(loc[2]);
			
			SpawnManager.addSpawnLocation(new SerializedLocation(x, y, z, net.senmori.hunted.Hunted.activeWorld, name));
		}
		// respawn locations
		for(String name : net.senmori.hunted.Hunted.stoneConfig.getConfigurationSection("respawn_loc").getKeys(false))
		{
			String location = net.senmori.hunted.Hunted.stoneConfig.getString(name);
			String[] loc = location.split("|");
			int x = Integer.parseInt(loc[0]);
			int y = Integer.parseInt(loc[1]);
			int z = Integer.parseInt(loc[2]);
			
			SpawnManager.addSpawnLocation(new SerializedLocation(x, y, z, net.senmori.hunted.Hunted.activeWorld, name));
		}
	}
}
