package net.senmori.hunted.commands.list.parameters;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.lib.game.LocationType;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;

public class ListGuardianStones extends Subcommand {
    
    private static final String NUM_LOC = "There are {0} guardian stone locations for this map.";
    
	public ListGuardianStones() {
		this.name = "guardian";
		this.needsPlayer = true;
		this.description = "Lists all registered Guardian Stones for this map";
		this.permission = Permissions.COMMAND_LIST_GUARDIAN;
	}

	@Override
	protected void perform() {
        List<Stone> locations = Hunted.getInstance().getStoneManager().getStones();
        int numLocations = locations.size();
        getPlayer().sendMessage(ChatColor.DARK_PURPLE + MessageFormat.format(NUM_LOC, (ChatColor.GOLD + String.valueOf(numLocations))));
        getPlayer().sendMessage(ChatColor.GOLD + Strings.repeat('-', NUM_LOC.length()));
        String message = "{0}" + ChatColor.GOLD+ ": [" + ChatColor.RED + "{1}" + ChatColor.RED + ", " + ChatColor.RESET +"{2}" + ChatColor.RED +", " + ChatColor.RESET + "{3} + " + ChatColor.GOLD + "] - " + ChatColor.RESET + "{4}";
        
        int index = 1;
        for(Stone stone : locations) {
            if(stone.getType() != Stone.StoneType.GUARDIAN) continue;
            ChatColor color = (index % 2 == 0 ? ChatColor.GRAY : ChatColor.WHITE);
            String cIndex = color + (String.valueOf(index));
            String x = color + (String.valueOf(stone.getLocation().getX()));
            String y = color + (String.valueOf(stone.getLocation().getY()));
            String z = color + (String.valueOf(stone.getLocation().getZ()));
            String name = color + stone.getName();
            getPlayer().sendMessage(color + MessageFormat.format(message, cIndex, x, y, z, name));
        }
        getPlayer().sendMessage(ChatColor.GOLD + Strings.repeat('-', NUM_LOC.length()));
	}

}
