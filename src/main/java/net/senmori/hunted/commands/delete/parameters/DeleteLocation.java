package net.senmori.hunted.commands.delete.parameters;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.ErrorMessage;
import net.senmori.hunted.util.Reference.Permissions;
import net.senmori.hunted.util.Reference.SuccessMessage;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class DeleteLocation extends Subcommand {

    public DeleteLocation() {
        this.name = "location";
        this.needsPlayer = true;
        this.permission = Permissions.COMMAND_DELETE_LOCATION;
    }

    @Override
    protected void perform() {
        Location remove = getPlayer().getLocation();
        String locationName = Hunted.getInstance().getSpawnManager().getLocationNameByLocation(remove);
        boolean removed = Hunted.getInstance().getSpawnManager().removeLocation(remove);
        if(!removed) {
            getPlayer().sendMessage(ChatColor.RED + ErrorMessage.LOCATION_DELETE_ERROR);
            return;
        }
        if(locationName == null) {
            getPlayer().sendMessage(ChatColor.GREEN + SuccessMessage.LOCATION_REMOVED.replace(" %loc", ""));
        }
        getPlayer().sendMessage(ChatColor.GREEN + SuccessMessage.LOCATION_REMOVED.replace("%loc", locationName));
    }

}
