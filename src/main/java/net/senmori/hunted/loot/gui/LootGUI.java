package net.senmori.hunted.loot.gui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.senmori.hunted.loot.LootTable;
import net.senmori.hunted.loot.Pool;
import net.senmori.hunted.loot.entry.Entry;

public class LootGUI {
	
	private LootTable table;
	private int size;
	private String name;
	private Map<Integer, ItemStack> items;
	
	public LootGUI(LootTable table) {
		this.table = table;
		items = new HashMap<>();
		size = 0;
		name = table.getName();
	}
	
	
	public void createGUI(Player player) {
		player.sendMessage("1");
		int slot = 0;
		for(Pool p : table.getPools()) {
			size += p.getEntries().size();
				for(Entry e : p.getEntries()) {
					items.put(slot, e.getIcon());
					slot++;
				}
		}
		size = 54;
		
		Inventory tableInv = Bukkit.createInventory(null, size, name);
		for(Integer i : items.keySet()) {
			tableInv.setItem(i, items.get(i));
		}
		
		player.openInventory(tableInv);
	}

}
