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
		this.name = "respawn";
		this.needsPlayer = true;
		this.description = "Adds respawn locations to the Hunted arena.";
		this.permission = Permissions.COMMAND_ADD;
	}

	@Override
	protected void perform()
	{
		int count = SpawnManager.getRespawnLocations().size() +1;
		SerializedLocation newRespawnLocation = new SerializedLocation(getPlayer().getLocation(), "RLoc-" + count);
		SpawnManager.addRespawnLocation(newRespawnLocation);
		getPlayer().sendMessage(ChatColor.GREEN + "Successfully added a new respawn location (" + newRespawnLocation.getName() + ")");
	}

}
