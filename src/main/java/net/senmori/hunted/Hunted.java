package net.senmori.hunted;

import me.dpohvar.powernbt.PowerNBT;
import me.dpohvar.powernbt.api.NBTManager;
import net.senmori.hunted.commands.add.AddCommand;
import net.senmori.hunted.commands.debug.DebugCommand;
import net.senmori.hunted.commands.delete.DeleteCommand;
import net.senmori.hunted.commands.edit.EditCommand;
import net.senmori.hunted.commands.give.HuntedGiveCommand;
import net.senmori.hunted.commands.list.ListCommand;
import net.senmori.hunted.commands.stuck.StuckCommand;
import net.senmori.hunted.lib.MapConfiguration;
import net.senmori.hunted.listeners.BlockListener;
import net.senmori.hunted.listeners.InventoryListener;
import net.senmori.hunted.listeners.PlayerListener;
import net.senmori.hunted.loot.menu.LootMenuManager;
import net.senmori.hunted.managers.CommandManager;
import net.senmori.hunted.managers.ConfigManager;
import net.senmori.hunted.managers.game.PlayerManager;
import net.senmori.hunted.managers.game.RewardManager;
import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.managers.game.StoneManager;
import net.senmori.hunted.managers.kit.ArmorManager;
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
import net.senmori.hunted.sql.Database;
import net.senmori.hunted.util.LogHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Hunted extends JavaPlugin {
	// static variables
	private static Hunted instance;
	public static Logger log;
	public NBTManager nbtManager = null;

	// plugin vars
	private PluginDescriptionFile pdf;
	public static String name;

	// game managers
	private RewardManager rewardManager;
	private StoneManager stoneManager;
	private SpawnManager spawnManager;
	private PlayerManager playerManager;
    private LootMenuManager lootMenuManager;
    // kit managers
	private KitManager kitManager;
	private WeaponManager weaponManager;
	private PotionManager potionManager;
	private ArmorManager armorManager;
	// misc. managers
	private ConfigManager configManager;
	private CommandManager commandManager;

	// Connection Pool
	private Database database; // SQL

	@Override
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
		database = new Database("HuntedDB.db", "Hunted", getInstance());

		// game managers
		playerManager = new PlayerManager(getInstance());
		stoneManager = new StoneManager();
		spawnManager = new SpawnManager();
		rewardManager = new RewardManager();
        lootMenuManager = new LootMenuManager();
        // kit managers
		kitManager = new KitManager(getInstance());
		weaponManager = new WeaponManager(getInstance());
		potionManager = new PotionManager(getInstance());
		armorManager = new ArmorManager(getInstance());
		// Misc. managers
		commandManager = new CommandManager(this);
		

		// setup commands
		commandManager.setCommandPrefix("ht");
		commandManager.registerCommand(new AddCommand());
		commandManager.registerCommand(new DebugCommand());
		commandManager.registerCommand(new DeleteCommand());
		commandManager.registerCommand(new EditCommand());
		commandManager.registerCommand(new ListCommand());
		commandManager.registerCommand(new StuckCommand());
		commandManager.registerCommand(new HuntedGiveCommand());
		LogHandler.info("Commands loaded!");

		// setup rewards

		rewardManager.addReward(new EffectReward("effect"));
		rewardManager.addReward(new ItemReward("item"));
		rewardManager.addReward(new NotifyReward("notify"));
		rewardManager.addReward(new PotionReward("potion"));
		rewardManager.addReward(new SmiteReward("smite"));
		rewardManager.addReward(new TeleportReward("teleport"));
		rewardManager.addReward(new IrritatingReward("irritating"));
		LogHandler.info("Rewards loaded!");

		// Listeners
		getServer().getPluginManager().registerEvents(new BlockListener(getInstance()), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(getInstance()), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(getInstance()), this);
        LogHandler.info("Listeners enabled!");
		
		// Power NBT
        if (Bukkit.getPluginManager().getPlugin("PowerNBT").isEnabled()) {
            nbtManager = PowerNBT.getApi();
		}
		instance = this;
	}

	@Override
	public void onDisable() {
		getConfigManager().save();
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

    public LootMenuManager getLootMenuManager() {
        return lootMenuManager;
    }

	public KitManager getKitManager() {
		return kitManager;
	}

	public ArmorManager getArmorManager() {
		return armorManager;
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
}
