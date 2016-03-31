package net.senmori.hunted.listeners;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class InventoryListener implements Listener {

	private Hunted plugin;

	public InventoryListener(Hunted plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		Player player = null;
		if (e.getWhoClicked() instanceof Player) {
			player = (Player) e.getWhoClicked();
		}

		if (player != null) {
			if (plugin.getPlayerManager().getState(player.getUniqueId()).equals(GameState.LOBBY)) {

			}
		}
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		Player player = null;
		if (e.getWhoClicked() instanceof Player) {
			player = (Player) e.getWhoClicked();
		}

		if (player != null) {
			if (plugin.getPlayerManager().getState(player.getUniqueId()).equals(GameState.LOBBY)) {

			}
		}
	}

	@EventHandler
	public void onInventoryPickupItem(InventoryPickupItemEvent e) {

	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Player player = null;
		if (e.getPlayer() instanceof Player) {
			player = (Player) e.getPlayer();
		}

		if (player != null) {
			if (plugin.getPlayerManager().getState(player.getUniqueId()).equals(GameState.LOBBY)) {

			}
		}
	}

}
