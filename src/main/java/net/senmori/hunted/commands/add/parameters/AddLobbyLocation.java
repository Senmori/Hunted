package net.senmori.hunted.commands.add.parameters;

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
	}

	@Override
	protected void perform() {
	    int count = Hunted.getInstance().getSpawnManager().getLobbyLocations().size() +1;
	    String locName = args.length >= 1 ? args[0] :  "RLoc-" + count;
		Hunted.getInstance().getSpawnManager().addLobbyLocation(new SerializedLocation(getPlayer().getLocation(), locName));
		getPlayer().sendMessage(ChatColor.GREEN + "Successfully added a new lobby location (" + locName + ")");
	}

}
