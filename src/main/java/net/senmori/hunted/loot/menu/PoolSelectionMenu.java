package net.senmori.hunted.loot.menu;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.loot.core.LootPool;
import net.senmori.hunted.loot.core.LootTable;
import net.senmori.hunted.loot.menu.icon.icons.PoolIcon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Senmori on 4/23/2016.
 * This menu is displayed when selecting which {@link LootPool} to display.
 */
public class PoolSelectionMenu extends Menu {

    protected static List<Integer> reservedSlots = Arrays.asList(0, 1);
    protected int size;
    protected String title;

    protected LootTable table;

    protected Inventory inventory;
    protected Map<Integer, PoolIcon> poolIconMap = new HashMap<>();

    /**
     * This menu is displayed when selecting which {@link LootPool} to display.
     *
     * @param table
     */
    public PoolSelectionMenu(LootTable table) {
        Hunted.getInstance().getLootMenuManager().addMenu(this);
        this.table = table;
        this.title = ChatColor.BLACK + "" + ChatColor.BOLD + "Loot Table: " + ChatColor.RESET + table.getResourceLocation().getResourcePath();
        // figure out most appropriate size
        size = setSize(size);
        loadIcons();
    }

    public void setTable(LootTable table) { this.table = table; }

    public LootTable getTable() { return this.table; }

    public int getSize() { return this.size; }

    public Inventory getInventory() { return this.inventory; }

    public Map<Integer, PoolIcon> getIconMap() { return this.poolIconMap; }

    @Override
    public void show(Player player) {
        if (Hunted.getInstance().getLootMenuManager().removeMenu(this)) {
            Hunted.getInstance().getLootMenuManager().addMenu(this);
        }
        player.closeInventory();
        inventory = Bukkit.createInventory(null, size, title);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer, PoolIcon> entry : poolIconMap.entrySet()) {
                    inventory.setItem(entry.getKey(), entry.getValue().getItemStack());
                }
                this.cancel();
            }
        }.runTaskLater(Hunted.getInstance(), 1L);
        player.openInventory(inventory);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (!e.getClickedInventory().equals(e.getView().getTopInventory())) { // cancel all clicks that aren't in this inventory to prevent weirdness
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            return;
        }
        if (e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem() == null)
            return; // ignore all clicks that aren't on an item

        Player player = (Player) e.getWhoClicked();
        // player selects a pool
        if (poolIconMap.get(e.getRawSlot()) != null) {
            PoolIcon icon = poolIconMap.get(e.getRawSlot());
            icon.onClick(e);
            LootPoolMenu menu = new LootPoolMenu(icon.getLootPool(), this);
            menu.show(player);
        }
    }

    @Override
    public void onDrag(InventoryDragEvent event) {
        event.setResult(Event.Result.DENY);
        event.setCancelled(true);
    }

    private int setSize(int size) {
        if (size < 9) return 9;
        if (size >= 9 && size < 18) return 18;
        if (size >= 18 && size < 27) return 27;
        if (size > 27 && size < 36) return 36;
        if (size >= 36 && size < 45) return 45;
        return 54;
    }


    private void loadIcons() {
        int slot = 0;
        for (LootPool pool : table.getLootPools()) {
            PoolIcon icon = new PoolIcon(slot, pool);
            poolIconMap.put(icon.getSlot(), icon);
        }
    }
}
