package net.senmori.hunted.loot.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Senmori on 4/23/2016.
 */
public class LootMenuManager {

    private Set<Menu> menuList = new HashSet<>();


    public LootMenuManager() {}


    public boolean addMenu(Menu menu) { return menuList.add(menu); }

    public boolean removeMenu(Menu menu) { return menuList.remove(menu); }


    public Set<Menu> getMenus() { return this.menuList; }


    public void onClick(InventoryClickEvent e) {
        for (Menu menu : menuList) {
            menu.onClick(e);
        }
    }

    public void onDrag(InventoryDragEvent e) {
        for (Menu menu : menuList) {
            menu.onDrag(e);
        }
    }
}
