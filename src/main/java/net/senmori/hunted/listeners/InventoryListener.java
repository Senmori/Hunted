package net.senmori.hunted.listeners;

import net.senmori.hunted.Hunted;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryListener implements Listener {

    private Hunted plugin;

    public InventoryListener(Hunted plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if(e.getWhoClicked() instanceof Player) {
        }
    }


    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if(e.getWhoClicked() instanceof Player) {
        }


    }
}
