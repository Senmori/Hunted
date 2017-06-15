package net.senmori.hunted.commands.add.parameters;


import java.text.MessageFormat;
import java.util.Arrays;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.ChatColor;

public class AddStoreLocation extends Subcommand {

    public AddStoreLocation() {
        this.name = "store";
        this.needsPlayer = true;
        this.permission = Permissions.COMMAND_ADD_STORE;
        this.description = "Adds a Hunted store location to this map";
        this.requiredArgs = Arrays.asList("name");
    }

    @Override
    protected void perform() {
        int count = Hunted.getInstance().getSpawnManager().getStoreLocations().size() + 1;
        String locName = args.length >= 2 ? args[1] : "Store-" + count;
        Hunted.getInstance().getSpawnManager().addStoreLocation(getPlayer().getLocation(), locName);
        ActionBar.sendMessage(getPlayer(), ChatColor.GREEN + MessageFormat.format(Reference.SuccessMessage.STORE_LOCATION_CREATED, locName));
    }

}
