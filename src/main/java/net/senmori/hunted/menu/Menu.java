package net.senmori.hunted.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class Menu implements InventoryHolder {

	private int size;
	private Inventory inventory;

	public Menu(String title, int size) {
		this.size = size;
		inventory = Bukkit.createInventory(null, this.size, title);
	}

	/* On click, process stuff */
	public abstract void onClick(Player player, ItemStack is);

	/* Used to create the GUI that the player will see */
	public abstract void create();

	/* Show the inventory to this player */
	public void show(Player player) {
		player.openInventory(inventory);
	}
	
	public void addItem(ItemStack item) {
		inventory.addItem(item);
	}

	/* Get item's lore so we can tell what to do with it, if it has encoded lore */
	protected String getItemLore(ItemStack is) {
		return null;
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	public int getSize() {
		return size;
	}
}
