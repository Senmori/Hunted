package net.senmori.hunted.commands.add.parameters;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.util.Reference.Permissions;

public class AddRespawn extends Subcommand
{

	public AddRespawn()
	{
		this.name = "lobby";
		this.needsPlayer = true;
		this.description = "Adds respawn locations to the Hunted arena.";
		this.permission = Permissions.COMMAND_ADD;
	}

	@Override
	protected void perform() {
	    int count = Hunted.getInstance().getSpawnManager().getLobbyLocations().size() +1;
	    String locName = "RLoc-";
		if(args.length >= 1) {
		    locName = args[0];
		} else {
		    locName += count;
		}
		Hunted.getInstance().getSpawnManager().addLobbyLocation(new SerializedLocation(getPlayer().getLocation(), locName));
		getPlayer().sendMessage(ChatColor.GREEN + "Successfully added a new lobby location (" + locName + ")");
	}

}
