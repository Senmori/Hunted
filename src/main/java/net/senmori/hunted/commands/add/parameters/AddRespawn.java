package net.senmori.hunted.commands.add.parameters;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.util.SerializedLocation;
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
	protected void perform()
	{
		String locName = args[args.length];
		int count = SpawnManager.getLobbyLocations().size() +1;
		SerializedLocation newLobbyLocation = new SerializedLocation(getPlayer().getLocation(), "RLoc-" + count);
		SpawnManager.addLobbyLocation(newLobbyLocation);
		getPlayer().sendMessage(ChatColor.GREEN + "Successfully added a new lobby location (" + newLobbyLocation.getName() + ")");
	}

}
