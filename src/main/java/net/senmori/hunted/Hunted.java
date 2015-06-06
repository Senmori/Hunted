package net.senmori.hunted;

import java.io.File;
import java.util.logging.Logger;

import net.senmori.hunted.listeners.BlockListener;
import net.senmori.hunted.listeners.PlayerListener;
import net.senmori.hunted.managers.CommandManager;
import net.senmori.hunted.managers.ConfigManager;
import net.senmori.hunted.managers.game.PlayerManager;
import net.senmori.hunted.managers.game.RewardManager;
import net.senmori.hunted.reward.EffectReward;
import net.senmori.hunted.reward.IrritatingReward;
import net.senmori.hunted.reward.ItemReward;
import net.senmori.hunted.reward.NotifyReward;
import net.senmori.hunted.reward.PotionReward;
import net.senmori.hunted.reward.SmiteReward;
import net.senmori.hunted.reward.TeleportReward;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Hunted extends JavaPlugin
{
	// static variables
	private static Hunted instance;
	public static Logger log;

	// File variables
	public static File pluginConfigFile;
	public static File stoneConfigFile;
	public static File lootConfigFile;

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

	// config options
	public static boolean debug;
	public static int defaultCooldown; // in minutes, convert to
									   // milliseconds(n*60000)
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

		instance = this;
		log = Bukkit.getLogger();

		ConfigManager.setupConfig();
		
		// EventManager & listeners
		new BlockListener();
		new PlayerListener();

		// setup commands
		commandManager = new CommandManager(instance);
		commandManager.setCommandPrefix("H");

		// other managers
		rewardManager = new RewardManager();

		// Load rewards
		// rewardManager.addReward(new BonusReward("bonus"));
		rewardManager.addReward(new EffectReward("effect"));
		rewardManager.addReward(new ItemReward("item"));
		rewardManager.addReward(new NotifyReward("notify"));
		rewardManager.addReward(new PotionReward("potion"));
		rewardManager.addReward(new SmiteReward("smite"));
		rewardManager.addReward(new TeleportReward("teleport"));
		rewardManager.addReward(new IrritatingReward("irritating"));

		// player manager
		playerManager = new PlayerManager();

		instance = this;
	}

	public void onDisable()
	{

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
	
	public FileConfiguration getLootConfig()
	{
		if(lootConfig == null)
		{
			lootConfig = YamlConfiguration.loadConfiguration(lootConfigFile);
			return lootConfig;
		}
		return lootConfig;
	}
	
	public static FileConfiguration getStoneConfig()
	{
		if(stoneConfig == null)
		{
			stoneConfig = YamlConfiguration.loadConfiguration(stoneConfigFile);
			return stoneConfig;
		}
		return stoneConfig;
	}
	
	public static FileConfiguration getPluginConfig()
	{
		if(pluginConfig == null)
		{
			pluginConfig = YamlConfiguration.loadConfiguration(pluginConfigFile);
			return pluginConfig;
		}
		return pluginConfig;
	}
}
