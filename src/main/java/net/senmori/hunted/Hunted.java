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
import net.senmori.hunted.lib.MapConfiguration;
import net.senmori.hunted.lib.sql.Database;
import net.senmori.hunted.lib.sql.QueryHandler;
import net.senmori.hunted.lib.sql.StatementHandler;
import net.senmori.hunted.listeners.BlockListener;
import net.senmori.hunted.listeners.PlayerListener;
import net.senmori.hunted.managers.CommandManager;
import net.senmori.hunted.managers.ConfigManager;
import net.senmori.hunted.managers.game.PlayerManager;
import net.senmori.hunted.managers.game.RewardManager;
import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.managers.game.StoneManager;
import net.senmori.hunted.managers.kit.KitManager;
import net.senmori.hunted.managers.kit.PotionManager;
import net.senmori.hunted.managers.kit.WeaponManager;
import net.senmori.hunted.reward.rewards.EffectReward;
import net.senmori.hunted.reward.rewards.IrritatingReward;
import net.senmori.hunted.reward.rewards.ItemReward;
import net.senmori.hunted.reward.rewards.NotifyReward;
import net.senmori.hunted.reward.rewards.PotionReward;
import net.senmori.hunted.reward.rewards.SmiteReward;
import net.senmori.hunted.reward.rewards.TeleportReward;
import net.senmori.hunted.util.LogHandler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.zaxxer.hikari.HikariDataSource;

public class Hunted extends JavaPlugin {
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

	// game managers
	private RewardManager rewardManager;
	private StoneManager stoneManager;
	private SpawnManager spawnManager;	
	private PlayerManager playerManager;
	// kit managers
	private KitManager kitManager;
	private WeaponManager weaponManager;
	private PotionManager potionManager;
	// misc. managers
	private ConfigManager configManager;
	private CommandManager commandManager;
	
	// Connection Pool
	private HikariDataSource hikari;
	private Database database; // SQL
	
	
	// SQL Objects
	private StatementHandler statementHandler;
	private QueryHandler queryHandler;

	public void onEnable() {
		pdf = getDescription();
		name = pdf.getName();
		
		log = getLogger();
		instance = this;
		
		// Config
        configManager = new ConfigManager(getInstance());
        configManager.init();
        configManager.addMapConfiguration("test", new MapConfiguration("test"));
        
		// SQL
        database = new Database(getConfigManager().dbUser, getConfigManager().dbPassword, getConfigManager().dbName, getConfigManager().dbHost, Integer.toString(getConfigManager().dbPort), getInstance());
        statementHandler = new StatementHandler(getSQLDatabase());
        queryHandler = new QueryHandler(getSQLDatabase());
        
        
		// Connection Pool
		hikari = new HikariDataSource();
		hikari.setMaximumPoolSize(10);
		hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		hikari.addDataSourceProperty("serverName", getConfigManager().dbHost);
		hikari.addDataSourceProperty("port", getConfigManager().dbPort);
		hikari.addDataSourceProperty("databaseName", getConfigManager().dbName);
		hikari.addDataSourceProperty("user", getConfigManager().dbUser);
		hikari.addDataSourceProperty("password", getConfigManager().dbPassword);
		
		
		
		// managers
		playerManager = new PlayerManager(getInstance());
		kitManager = new KitManager(getInstance());
		weaponManager = new WeaponManager(getInstance());
		potionManager = new PotionManager(getInstance());
	    stoneManager = new StoneManager();
	    spawnManager = new SpawnManager(getInstance());
		

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
		LogHandler.info(rewardManager.getRewards().size() + " rewards added!");
		

		
		//Listeners
		getServer().getPluginManager().registerEvents(new BlockListener(getInstance()), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(getInstance()), this);
		LogHandler.info("Listeners enabled!");
		
		instance = this;
	}

	public void onDisable() {
	    getConfigManager().save();
	    
	    if(hikari != null) {
	        hikari.close();
	    }
	}

	public static Hunted getInstance() {
		return instance;
	}
	
	public ConfigManager getConfigManager() {
	    return configManager;
	}

	public RewardManager getRewardManager() {
		return rewardManager;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}
	
	public KitManager getKitManager() {
		return kitManager;
	}
	
	public WeaponManager getWeaponManager() {
		return weaponManager;
	}
	
	public PotionManager getPotionManager() {
		return potionManager;
	}
	
	public StoneManager getStoneManager() {
	    return stoneManager;
	}
	
	public SpawnManager getSpawnManager() {
	    return spawnManager;
	}
	
	public Database getSQLDatabase() {
	    return database;
	}
	
	public StatementHandler getStatementHandler() {
	    return statementHandler;
	}
	
	public QueryHandler getQueryHandler() {
	    return queryHandler;
	}
	
	public HikariDataSource getHikari() {
	    return hikari;
	}
}
