package net.senmori.hunted;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import net.senmori.hunted.commands.add.AddCommand;
import net.senmori.hunted.commands.debug.DebugCommand;
import net.senmori.hunted.commands.delete.DeleteCommand;
import net.senmori.hunted.commands.edit.EditCommand;
import net.senmori.hunted.commands.list.ListCommand;
import net.senmori.hunted.commands.stuck.StuckCommand;
import net.senmori.hunted.listeners.BlockListener;
import net.senmori.hunted.listeners.PlayerListener;
import net.senmori.hunted.managers.CommandManager;
import net.senmori.hunted.managers.ConfigManager;
import net.senmori.hunted.managers.game.KitManager;
import net.senmori.hunted.managers.game.PlayerManager;
import net.senmori.hunted.managers.game.RewardManager;
import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.managers.game.StoneManager;
import net.senmori.hunted.managers.game.WeaponManager;
import net.senmori.hunted.reward.EffectReward;
import net.senmori.hunted.reward.IrritatingReward;
import net.senmori.hunted.reward.ItemReward;
import net.senmori.hunted.reward.NotifyReward;
import net.senmori.hunted.reward.PotionReward;
import net.senmori.hunted.reward.SmiteReward;
import net.senmori.hunted.reward.TeleportReward;
import net.senmori.hunted.util.LogHandler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Hunted extends JavaPlugin
{
	// static variables
	private static Hunted instance;
	public static Logger log;

	// File variables
	public static File pluginConfigFile;

	// config
	public static FileConfiguration config; // plugin config

	// plugin vars
	private PluginDescriptionFile pdf;
	public static String name;

	// managers
	private CommandManager commandManager;
	private static RewardManager rewardManager;
	private static PlayerManager playerManager;
	private static KitManager kitManager;
	private static WeaponManager weaponManager;

	public void onEnable()
	{
		pdf = getDescription();
		name = pdf.getName();
		
		log = getLogger();
		instance = this;
		

		// Configs
		ConfigManager.init();
		
		// managers
		playerManager = new PlayerManager();
		kitManager = new KitManager();
		weaponManager = new WeaponManager();
		
		
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
	        Hunted.config.save(Hunted.pluginConfigFile);
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
	
	public static WeaponManager getWeaponManager()
	{
		return weaponManager;
	}
}
