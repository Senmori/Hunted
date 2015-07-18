package net.senmori.hunted.commands.add.parameters;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.util.SerializedLocation;
import net.senmori.hunted.util.Reference.Permissions;

public class AddSpawn extends Subcommand
{

	public AddSpawn()
	{
		this.name = "spawn";
		this.needsPlayer = true;
		this.description = "Adds a spawn location in the Hunted arena.";
		this.permission = Permissions.COMMAND_ADD;
	}

	@Override
	protected void perform()
	{
		int count = SpawnManager.getSpawnLocations().size() + 1;
		SerializedLocation newSpawnLocation = new SerializedLocation(getPlayer().getLocation(), "SpawnLoc-" + count);
		SpawnManager.addSpawnLocation(newSpawnLocation);
		getPlayer().sendMessage(ChatColor.GREEN + "Successfully created new spawn location (" + newSpawnLocation.getName() + ")" + count);
	}

}
