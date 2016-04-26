package net.senmori.hunted.loot.menu.icon.icons;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.loot.core.LootTable;
import net.senmori.hunted.loot.menu.icon.Icon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Senmori on 4/24/2016.
 */
public class SaveTableIcon implements Icon {

    private LootTable table;
    private int slot;
    private ItemStack itemStack;

    public SaveTableIcon(int slot, LootTable table) {
        this.slot = slot;
        this.table = table;
        itemStack = new ItemStack(Material.EMPTY_MAP);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Save and Close (" + ChatColor.RESET + table.getResourceLocation().getResourcePath() + ChatColor.AQUA + ")");
        itemStack.setItemMeta(meta);
    }

    @Override
    public ItemStack getItemStack() { return this.itemStack; }

    public int getSlot() { return this.slot; }

    public LootTable getTable() { return this.table; }

    @Override
    public void onClick(Player player, int clickedSlot, ItemStack clickedItem, Inventory clickedInventory, ClickType type) {
        player.closeInventory();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (table.save()) {
                    player.sendMessage(ChatColor.AQUA + "Successfully updated \'" + ChatColor.RESET + table.getResourceLocation().getResourcePath() + ChatColor.AQUA + "\'!");
                }
            }
        }.runTaskLater(Hunted.getInstance(), 1L);
    }
}
