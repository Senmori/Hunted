package net.senmori.hunted.managers.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.menu.Menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class MenuManager implements Listener {

    private Hunted plugin;
    private Map<Object, Menu> menus;
    
    public MenuManager(Hunted plugin) {
        this.plugin = plugin;
        menus = new HashMap<>();
        
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    public void addMenu(Object key, Menu menu) {
        menus.put(key, menu);
    }
    
    public Menu getMenu(Object key) {
        return menus.get(key);
    }
    
    public void removeMenu(Object key) {
        menus.remove(key);
    }
    
    public Collection<Menu> getMenus() {
        return menus.values();
    }
    
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        for(Menu menu : getMenus()) {
            if(menu.getInventory().getName().equals(inv.getName())) {
                e.setCancelled(true);
                menu.onClick(player, e.getCurrentItem());
            }
        }
    }
    
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        Inventory inv = e.getWhoClicked().getOpenInventory().getTopInventory();
        for(Menu menu : getMenus()) {
            if(menu.getInventory().getName().equals(inv.getName())) {
                e.setCancelled(true);
            }
        }
    }
}
