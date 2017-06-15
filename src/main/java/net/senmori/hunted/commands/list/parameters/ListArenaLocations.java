package net.senmori.hunted.commands.list.parameters;

import java.text.MessageFormat;
import java.util.List;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.lib.game.LocationType;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;

public class ListArenaLocations extends Subcommand {

    private static final String NUM_LOC = "There are {0} {1} spawn locations for this map.";


    public ListArenaLocations() {
        this.name = "spawn";
        this.needsPlayer = true;
        this.description = "Lists all registered spawn locations for this map";
        this.permission = Permissions.COMMAND_LIST_SPAWN;
    }

    @Override
    protected void perform() {
        List<SerializedLocation> locations = Hunted.getInstance().getSpawnManager().getLocationsByType(LocationType.ARENA);
        int numLocations = locations.size();
        getPlayer().sendMessage(ChatColor.DARK_PURPLE + MessageFormat.format(NUM_LOC, ( ChatColor.GOLD + String.valueOf(numLocations) ), ChatColor.DARK_PURPLE));
        getPlayer().sendMessage(ChatColor.GOLD + Strings.repeat('-', NUM_LOC.length()));
        String message = "{0}" + ChatColor.GOLD + ": [" + ChatColor.RED + "{1}" + ChatColor.RED + ", " + ChatColor.RESET + "{2}" + ChatColor.RED + ", " + ChatColor.RESET + "{3} + " + ChatColor.GOLD + "] - " + ChatColor.RESET + "{4}";

        int index = 1;
        for(SerializedLocation loc : locations) {
            ChatColor color = ( index % 2 == 0 ? ChatColor.GRAY : ChatColor.WHITE );
            String cIndex = color + ( String.valueOf(index) );
            String x = color + ( String.valueOf(loc.getX()) );
            String y = color + ( String.valueOf(loc.getY()) );
            String z = color + ( String.valueOf(loc.getZ()) );
            String name = color + loc.getName();
            getPlayer().sendMessage(color + MessageFormat.format(message, cIndex, x, y, z, name));
            index++;
        }
        getPlayer().sendMessage(ChatColor.GOLD + Strings.repeat('-', NUM_LOC.length()));
    }

}
