package net.senmori.hunted.commands.list.parameters;

import java.text.MessageFormat;
import java.util.Set;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.lib.game.LocationType;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;

public class ListLobbyLocations extends Subcommand {
    
    private static final String NUM_LOC = "There are {0} lobby locations for this map.";
    
	public ListLobbyLocations() {
		this.name = "lobby";
		this.needsPlayer = true;
		this.description = "Lists all registered lobby locations for this map";
		this.permission = Permissions.COMMAND_LIST_LOBBY;
	}

	@Override
	protected void perform() {
        Set<SerializedLocation> locations = Hunted.getInstance().getSpawnManager().getLocationsByType(LocationType.LOBBY);
        int numLocations = locations.size();
        getPlayer().sendMessage(ChatColor.DARK_PURPLE + MessageFormat.format(NUM_LOC, (ChatColor.GOLD + String.valueOf(numLocations))));
        getPlayer().sendMessage(ChatColor.GOLD + Strings.repeat('-', NUM_LOC.length()));
        String message = "{0}" + ChatColor.GOLD+ ": [" + ChatColor.RED + "{1}" + ChatColor.RED + ", " + ChatColor.RESET +"{2}" + ChatColor.RED +", " + ChatColor.RESET + "{3} + " + ChatColor.GOLD + "] - " + ChatColor.RESET + "{4}";
        
        int index = 1;
        for(SerializedLocation loc : locations) {
            ChatColor color = (index % 2 == 0 ? ChatColor.GRAY : ChatColor.WHITE);
            String cIndex = color + (String.valueOf(index));
            String x = color + (String.valueOf(loc.getX()));
            String y = color + (String.valueOf(loc.getY()));
            String z = color + (String.valueOf(loc.getZ()));
            String name = color + loc.getName();
            getPlayer().sendMessage(color + MessageFormat.format(message, cIndex, x, y, z, name));
        }
        getPlayer().sendMessage(ChatColor.GOLD + Strings.repeat('-', NUM_LOC.length()));
	}

}
