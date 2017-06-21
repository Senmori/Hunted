package net.senmori.hunted;

import co.aikar.commands.BukkitCommandManager;
import java.util.logging.Logger;
import net.senmori.hunted.util.LogHandler;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Hunted extends JavaPlugin {
    public static Logger logger;
    public static String name;
    // static variables
    private static Hunted instance;
    // plugin vars
    private PluginDescriptionFile pdf;
    // Command Manager (ACF)
    private BukkitCommandManager manager;


    public static Hunted getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        pdf = getDescription();
        name = pdf.getName();

        logger = getLogger();
        instance = this;

        // Config

        // setup commands
        manager = new BukkitCommandManager(this);
        LogHandler.info("Commands loaded!");

        // setup rewards
        LogHandler.info("Rewards loaded!");

        // Listeners
        LogHandler.info("Listeners enabled!");

        instance = this;
    }

    @Override
    public void onDisable() {

    }

    public BukkitCommandManager getCommandManager() {
        return manager;
    }
}
