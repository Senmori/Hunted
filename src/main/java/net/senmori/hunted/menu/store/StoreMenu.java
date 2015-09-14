package net.senmori.hunted.menu.store;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.senmori.hunted.menu.Menu;

public class StoreMenu extends Menu {

    public StoreMenu(String title, int size) {
        super(title, size);
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public void onClick(Player player, ItemStack is) {

    }

    @Override
    protected void create() {

    }

}
