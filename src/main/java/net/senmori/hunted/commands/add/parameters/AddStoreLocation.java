package net.senmori.hunted.commands.add.parameters;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.util.Reference.Permissions;

public class AddStoreLocation extends Subcommand{

    public AddStoreLocation() {
        this.name = "store";
        this.needsPlayer = true;
        this.permission = Permissions.COMMAND_ADD_STORE;
        this.description = "Adds a Hunted store location to this map";
    }

    @Override
    protected void perform() {
        int count = Hunted.getInstance().getSpawnManager().getStoreLocations().size() +1;
        String locName = args.length >= 1 ? args[0] : "Store-" + count;
        Hunted.getInstance().getSpawnManager().addStoreLocation(new SerializedLocation(getPlayer().getLocation(), locName));
        getPlayer().sendMessage(ChatColor.GREEN + "Successfully added a new store location (" + locName + ")");
    }

}
