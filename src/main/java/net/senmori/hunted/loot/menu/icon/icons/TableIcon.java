package net.senmori.hunted.loot.menu.icon.icons;

import net.senmori.hunted.loot.entry.LootEntryTable;
import net.senmori.hunted.loot.menu.icon.Icon;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Senmori on 4/24/2016.
 */
public class TableIcon implements Icon {

    protected LootEntryTable entry;
    protected ItemStack stack;
    protected int slot;

    /**
     * This {@link Icon} is used to display a link to the related {@link LootEntryTable}.<br>
     * Clicking this {@link Icon} will <b>NOT</b> open the related {@link LootEntryTable}.<br>
     * You cannot add/remove conditions. Only the entry itself.
     *
     * @param slot
     * @param table
     */
    public TableIcon(int slot, LootEntryTable table) {
        this.slot = slot;
        this.entry = table;
    }

    @Override
    public ItemStack getItemStack() { return this.stack; }

    public int getSlot() { return this.slot; }

    public LootEntryTable getEntry() { return this.entry; }


    @Override
    public void onClick(Player player, int clickedSlot, ItemStack clickedItem, Inventory clickedInventory, ClickType type) {

    }
}
