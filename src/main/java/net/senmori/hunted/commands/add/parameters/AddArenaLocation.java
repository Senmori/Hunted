package net.senmori.hunted.commands.add.parameters;

import java.util.Arrays;

import org.bukkit.ChatColor;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.util.Reference.Permissions;

public class AddArenaLocation extends Subcommand {

	public AddArenaLocation() {
		this.name = "spawn";
		this.needsPlayer = true;
		this.description = "Adds a spawn location in the Hunted arena.";
		this.permission = Permissions.COMMAND_ADD;
		this.requiredArgs = Arrays.asList("name");
	}

	@Override
	protected void perform() {
		int count = Hunted.getInstance().getSpawnManager().getHuntedLocations().size() + 1;
		String name = args.length >= 2 ? args[1] : "HLoc-" + count;
		Hunted.getInstance().getSpawnManager().addHuntedLocation(new SerializedLocation(getPlayer().getLocation(), name));
		getPlayer().sendMessage(ChatColor.GREEN + "Successfully created new spawn location (" + name + ")");
	}

}
