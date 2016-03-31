package net.senmori.hunted.commands.debug;


import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.loot.LootPool;
import net.senmori.hunted.loot.LootTable;
import net.senmori.hunted.loot.LootTableManager;
import net.senmori.hunted.loot.entry.LootEntry;
import net.senmori.hunted.loot.entry.LootEntryItem;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DebugCommand extends Subcommand {

	public DebugCommand() {
		this.name = "debug";
		this.needsPlayer = true;
		this.permission = Permissions.ADMIN_DEBUG;
	}

	@Override
	protected void perform() {
		ResourceLocation chestDebug = new ResourceLocation("hunted:chests/debug");
        Bukkit.broadcastMessage("Absolute File Path: " + LootTableManager.getResourceFilePath(chestDebug));
		LootTable debugChestTable = LootTableManager.createOrLoadLootTable(chestDebug);

		List<LootEntry> entries = new ArrayList<>();
		entries.add(new LootEntryItem(new ItemStack(Material.COBBLESTONE), 1, 0, null, null));
		entries.add(new LootEntryItem(Material.DIAMOND_BOOTS, 1, 0, null,null));

		LootPool defaultPool = new LootPool(entries, null, 3.0F);
		debugChestTable.addLootPool(defaultPool);

        Bukkit.broadcastMessage("Data input into Loot Table: " + chestDebug.toString());
        LootTableManager.save(chestDebug);
	}
}
