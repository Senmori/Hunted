package net.senmori.hunted.commands.add.parameters;

import java.text.MessageFormat;
import java.util.ArrayList;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class AddArenaLocation extends Subcommand {

	public AddArenaLocation() {
		this.name = "spawn";
		this.needsPlayer = true;
		this.description = "Adds a spawn location in the Hunted arena.";
		this.permission = Permissions.COMMAND_ADD_SPAWN;
		this.optionalArgs = Arrays.asList("name");
	}

	@Override
	protected void perform() {
		int count = Hunted.getInstance().getSpawnManager().getHuntedLocations().size() + 1;
		String name = args.length >= 2 ? args[1] : "ArenaLoc-" + count;
		Hunted.getInstance().getSpawnManager().addArenaLocation(getPlayer().getLocation(), name);
		ActionBar.sendMessage(getPlayer(), ChatColor.GREEN + MessageFormat.format(Reference.SuccessMessage.SPAWN_LOCATION_CREATED, name));
	}

}
