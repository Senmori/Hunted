package net.senmori.hunted;

import net.senmori.hunted.commands.add.AddCommand;
import net.senmori.hunted.commands.debug.DebugCommand;
import net.senmori.hunted.commands.delete.DeleteCommand;
import net.senmori.hunted.commands.edit.EditCommand;
import net.senmori.hunted.commands.exempt.ExemptCommand;
import net.senmori.hunted.commands.list.ListCommand;
import net.senmori.hunted.commands.stuck.StuckCommand;
import net.senmori.hunted.listeners.BlockListener;
import net.senmori.hunted.listeners.PlayerListener;
import net.senmori.hunted.managers.CommandManager;
import net.senmori.hunted.managers.config.ConfigManager;
import net.senmori.hunted.managers.config.LootConfigManager;
import net.senmori.hunted.managers.config.StoneConfigManager;
import net.senmori.hunted.managers.game.*;
import net.senmori.hunted.reward.*;
import net.senmori.hunted.util.LogHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Hunted extends JavaPlugin
{
	// static variables
	private static Hunted instance;
	public static Logger log;

	// File variables
	public static File pluginConfigFile;
	public static File lootConfigFile;
	public static File stoneConfigFile;

	// config variables
	public static FileConfiguration pluginConfig; // plugin config
	public static FileConfiguration stoneConfig; // guardian stone config
	public static FileConfiguration lootConfig;  // loot table config

	// plugin vars
	private PluginDescriptionFile pdf;
	public static String name;

	// managers
	private CommandManager commandManager;
	private static RewardManager rewardManager;
	private static PlayerManager playerManager;
	private static KitManager kitManager;

	// config options
	public static boolean debug;
	public static int defaultCooldown; // in minutes, convert to milliseconds(n*60000)
	public static int maxEffectLength;
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

	public void onEnable()
	{
		pdf = getDescription();
		name = pdf.getName();
		
		log = getLogger();
		instance = this;
		

		// Configs
		ConfigManager.init();
		LootConfigManager.init();
		StoneConfigManager.init();
		
		// player manager
		playerManager = new PlayerManager();
		LogHandler.info("Player Manager enabled!");
		
		// kit manager
		kitManager = new KitManager();
		LogHandler.info("Kit Manager enabled");
		
		
		//Listeners
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		LogHandler.info("Listeners enabled!");
		
		// setup commands
		LogHandler.info("Setting up commands...");
		commandManager = new CommandManager(this);
		commandManager.setCommandPrefix("ht");
		commandManager.registerCommand(new AddCommand());
		commandManager.registerCommand(new DebugCommand());
		commandManager.registerCommand(new DeleteCommand());
		commandManager.registerCommand(new EditCommand());
		commandManager.registerCommand(new ExemptCommand());
		commandManager.registerCommand(new ListCommand());
		commandManager.registerCommand(new StuckCommand());
		LogHandler.info("Commands loaded!");
		
		// setup rewards
		rewardManager = new RewardManager();
		rewardManager.addReward(new EffectReward("effect"));
		rewardManager.addReward(new ItemReward("item"));
		rewardManager.addReward(new NotifyReward("notify"));
		rewardManager.addReward(new PotionReward("potion"));
		rewardManager.addReward(new SmiteReward("smite"));
		rewardManager.addReward(new TeleportReward("teleport"));
		rewardManager.addReward(new IrritatingReward("irritating"));
		LogHandler.info("Rewards added!");
		
		instance = this;
	}

	public void onDisable()
	{
		SpawnManager.saveLocations();
		StoneManager.saveLocations();
		// save config files
		try
        {
	        Hunted.pluginConfig.save(Hunted.pluginConfigFile);
	        Hunted.stoneConfig.save(Hunted.stoneConfigFile);
	        Hunted.lootConfig.save(Hunted.lootConfigFile);
        } catch (IOException e)
        {
	        e.printStackTrace();
        }
	}

	public static Hunted getInstance()
	{
		return instance;
	}

	public static RewardManager getRewardManager()
	{
		return rewardManager;
	}

	public static PlayerManager getPlayerManager()
	{
		return playerManager;
	}
	
	public static KitManager getKitManager()
	{
		return kitManager;
	}
}
