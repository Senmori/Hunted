package net.senmori.hunted.loot.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * Created by Senmori on 4/23/2016.
 */
public abstract class Menu implements IMenu {
    public abstract void show(Player player);

    public abstract void onClick(InventoryClickEvent event);

    public abstract void onDrag(InventoryDragEvent event);
}
