package net.senmori.hunted;

import java.io.File;
import java.util.logging.Logger;

import net.minecraft.server.v1_8_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R1.PacketPlayOutUpdateEntityNBT;
import net.senmori.hunted.managers.AchievementManager;
import net.senmori.hunted.managers.CommandManager;
import net.senmori.hunted.managers.EventManager;
import net.senmori.hunted.managers.PlayerManager;
import net.senmori.hunted.managers.RewardManager;
import net.senmori.hunted.reward.BonusReward;
import net.senmori.hunted.reward.EffectReward;
import net.senmori.hunted.reward.IrritatingReward;
import net.senmori.hunted.reward.ItemReward;
import net.senmori.hunted.reward.NotifyReward;
import net.senmori.hunted.reward.PotionReward;
import net.senmori.hunted.reward.SmiteReward;
import net.senmori.hunted.reward.TeleportReward;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Hunted extends JavaPlugin
{
	//static variables
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
	public static RewardManager rewardManager;
	public static PlayerManager playerManager;
	public static AchievementManager achManager;
	
	// config options
	public static boolean debug;
	public static int defaultCooldown; // in minutes, convert to milliseconds(n*60000)
	public static int xpPerKill;
	public static int xpPerStone;
	public static int maxPotsPerReward;
	public static int nearbyRadius;
	public static int maxEffectLength;
	public static int killStreakAmount;
	
	
	public void onEnable()
	{	
		pdf = getDescription();
		name = pdf.getName();
		
		instance = this;
		log = Bukkit.getLogger();
		// setup commands
		commandManager = new CommandManager(instance);
		commandManager.setCommandPrefix("H");
		
		// other managers
		rewardManager = new RewardManager();
		playerManager = new PlayerManager();
		
		// Load rewards
		rewardManager.addReward(new BonusReward("bonus"));
		rewardManager.addReward(new EffectReward("effect"));
		rewardManager.addReward(new IrritatingReward("irritating"));
		rewardManager.addReward(new ItemReward("item"));
		rewardManager.addReward(new NotifyReward("notify"));
		rewardManager.addReward(new PotionReward("potion"));
		rewardManager.addReward(new SmiteReward("smite"));
		rewardManager.addReward(new TeleportReward("teleport"));
		
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
