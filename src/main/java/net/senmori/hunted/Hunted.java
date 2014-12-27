package net.senmori.hunted;

import java.util.logging.Logger;

import net.senmori.hunted.managers.CommandManager;
import net.senmori.hunted.managers.RewardManager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Hunted extends JavaPlugin
{
	//static variables
	private static Hunted instance;
	public static FileConfiguration pluginConfig; // plugin config
	public static FileConfiguration stoneConfig; // guardian stone config
	public static Logger log;
	private PluginDescriptionFile pdf;
	
	// plugin vars
	public static String name;
	
	// managers
	private CommandManager commandManager;
	private RewardManager rewardManager;
	
	// config options
	public static boolean debug;
	public static int defaultCooldown; // in minutes, convert to milliseconds(n*60000)
	public static int xpPerKill;
	public static int xpPerStone;
	
	
	
	public void onEnable()
	{	
		pdf = getDescription();
		name = pdf.getName();
		
		instance = this;
		log = Bukkit.getLogger();
		commandManager = new CommandManager(instance);
		commandManager.setCommandPrefix("H");
		
		rewardManager = new RewardManager(instance);
		instance = this;
	}
	
	
	public void onDisable()
	{
		
	}
	
	public static Hunted getInstance()
	{
		return instance;
	}
}
