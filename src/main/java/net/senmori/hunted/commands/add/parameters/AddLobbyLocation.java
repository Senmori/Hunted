package net.senmori.hunted.commands.add.parameters;


import java.text.MessageFormat;
import java.util.Arrays;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.ChatColor;

public class AddLobbyLocation extends Subcommand {

    public AddLobbyLocation() {
        this.name = "lobby";
        this.needsPlayer = true;
        this.description = "Adds respawn locations to the Hunted arena.";
        this.permission = Permissions.COMMAND_ADD_LOBBY;
        this.requiredArgs = Arrays.asList("name");
    }

    @Override
    protected void perform() {
        int count = Hunted.getInstance().getSpawnManager().getLobbyLocations().size() + 1;
        String locName = args.length >= 2 ? args[1] : "LobbyLoc-" + count;
        Hunted.getInstance().getSpawnManager().addLobbyLocation(getPlayer().getLocation(), locName);
        ActionBar.sendMessage(getPlayer(), ChatColor.GREEN + MessageFormat.format(Reference.SuccessMessage.LOBBY_LOCATION_CREATED, args[1]));
    }

}
