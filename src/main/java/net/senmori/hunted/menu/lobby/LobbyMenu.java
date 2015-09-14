package net.senmori.hunted.menu.lobby;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.senmori.hunted.menu.Menu;


public class LobbyMenu extends Menu {


    public LobbyMenu(String title, int size) {
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
