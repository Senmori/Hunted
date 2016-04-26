package net.senmori.hunted.loot.menu;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.loot.core.LootPool;
import net.senmori.hunted.loot.entry.LootEntry;
import net.senmori.hunted.loot.entry.LootEntryItem;
import net.senmori.hunted.loot.entry.LootEntryTable;
import net.senmori.hunted.loot.menu.icon.Icon;
import net.senmori.hunted.loot.menu.icon.icons.BonusRollsIcon;
import net.senmori.hunted.loot.menu.icon.icons.DeleteIcon;
import net.senmori.hunted.loot.menu.icon.icons.EntryIcon;
import net.senmori.hunted.loot.menu.icon.icons.ParentIcon;
import net.senmori.hunted.loot.menu.icon.icons.RollsIcon;
import net.senmori.hunted.loot.menu.icon.icons.SaveTableIcon;
import net.senmori.hunted.loot.menu.icon.icons.TableIcon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Senmori on 4/24/2016.
 */
public class LootPoolMenu extends Menu {

    private PoolSelectionMenu parent;
    private LootPool pool;
    private int size;
    private String title;

    private Map<Integer, Icon> iconMap = new HashMap<>();
    private Inventory inventory;

    public LootPoolMenu(LootPool pool, PoolSelectionMenu parent) {
        Hunted.getInstance().getLootMenuManager().addMenu(this);
        this.parent = parent;
        this.pool = pool;
        this.size = setSize(pool.getEntries().size());
        this.title = ChatColor.BLACK + "" + ChatColor.BOLD + "Pool: " + ChatColor.RED + "" + ChatColor.ITALIC + pool.getName();
        loadIcons();

    }

    public LootPool getLootPool() { return this.pool; }

    public int getSize() { return this.size; }

    public String getTitle() { return this.title; }

    public PoolSelectionMenu getParent() { return this.parent; }

    @Override
    public void show(Player player) {
        player.closeInventory();
        inventory = Bukkit.createInventory(null, size, title);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer, Icon> entry : iconMap.entrySet()) {
                    inventory.setItem(entry.getKey(), entry.getValue().getItemStack());
                }
            }
        }.runTaskLater(Hunted.getInstance(), 1L);
        player.openInventory(inventory);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (iconMap.get(e.getRawSlot()) != null) {
            Player player = (Player) e.getWhoClicked();
            iconMap.get(e.getRawSlot()).onClick(player, e.getRawSlot(), e.getCurrentItem(), e.getClickedInventory(), e.getClick());
        }
    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setResult(Event.Result.DENY);
        event.setCancelled(true);
    }


    private void loadIcons() {
        iconMap.put(size - 5, new DeleteIcon(size - 5, pool, this));
        iconMap.put(0, new RollsIcon(0, pool));
        iconMap.put(1, new BonusRollsIcon(1, pool));
        int slot = 2;
        for (LootEntry entry : pool.getEntries()) {
            if (iconMap.containsKey(slot)) {
                do {
                    slot++;
                } while (iconMap.containsKey(slot));
            }
            if (entry instanceof LootEntryItem) {
                EntryIcon icon = new EntryIcon(slot, (LootEntryItem) entry);
                iconMap.put(icon.getSlot(), icon);
                slot++;
                continue;
            }
            if (entry instanceof LootEntryTable) {
                TableIcon icon = new TableIcon(slot, (LootEntryTable) entry);
                iconMap.put(icon.getSlot(), icon);
                slot++;
            }
        }
        iconMap.put(size - 9, new ParentIcon(size - 9, parent));
        iconMap.put(size - 1, new SaveTableIcon(size - 1, parent.getTable()));
    }

    private int setSize(int size) {
        if (size <= 18) return 27;
        if (size > 27 && size < 36) return 36;
        if (size >= 36 && size < 45) return 45;
        return 54;
    }
}
