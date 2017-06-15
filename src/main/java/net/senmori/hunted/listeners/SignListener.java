package net.senmori.hunted.listeners;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.game.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

    private static final String ENTER_KEY = "Enter";
    private static final String EXIT_KEY = "Exit";

    private Hunted plugin;
    private PlayerManager playerManager;
    private String activeWorld;

    public SignListener(Hunted plugin) {
        this.plugin = plugin;
        this.playerManager = plugin.getPlayerManager();
        this.activeWorld = plugin.getConfigManager().activeWorld;
    }


    @EventHandler
    public void onSignCreate(SignChangeEvent e) {
        // enter hunted world sign


        // leave hunted world sign
    }
}
