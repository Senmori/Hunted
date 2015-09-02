package net.senmori.hunted.commands.add.parameters;

import java.util.Arrays;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.util.Reference.Permissions;

public class AddLobbyLocation extends Subcommand
{

	public AddLobbyLocation()
	{
		this.name = "lobby";
		this.needsPlayer = true;
		this.description = "Adds respawn locations to the Hunted arena.";
		this.permission = Permissions.COMMAND_ADD;
		this.requiredArgs = Arrays.asList("name");
	}

	@Override
	protected void perform() {
	    int count = Hunted.getInstance().getSpawnManager().getLobbyLocations().size() +1;
	    String locName = args.length >= 2 ? args[1] :  "RLoc-" + count;
		Hunted.getInstance().getSpawnManager().addLobbyLocation(new SerializedLocation(getPlayer().getLocation(), locName));
		getPlayer().sendMessage(ChatColor.GREEN + "Successfully added a new lobby location (" + locName + ")");
	}

}
