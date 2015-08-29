package net.senmori.hunted.commands.add.parameters;

import org.bukkit.ChatColor;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.util.Reference.Permissions;

public class AddSpawn extends Subcommand {

	public AddSpawn() {
		this.name = "spawn";
		this.needsPlayer = true;
		this.description = "Adds a spawn location in the Hunted arena.";
		this.permission = Permissions.COMMAND_ADD;
	}

	@Override
	protected void perform() {
		int count = Hunted.getInstance().getSpawnManager().getHuntedLocations().size() + 1;
		String name = args.length >= 1? args[0] : "HLoc-" + count;
		SerializedLocation newSpawnLocation = new SerializedLocation(getPlayer().getLocation(), name);
		Hunted.getInstance().getSpawnManager().addHuntedLocation(newSpawnLocation);
		getPlayer().sendMessage(ChatColor.GREEN + "Successfully created new spawn location (" + name + ")");
	}

}
