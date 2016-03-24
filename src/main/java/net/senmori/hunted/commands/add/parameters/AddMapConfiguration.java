package net.senmori.hunted.commands.add.parameters;

import java.util.Arrays;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.MapConfiguration;
import net.senmori.hunted.util.Reference.Permissions;

public class AddMapConfiguration extends Subcommand {

	public AddMapConfiguration() {
		this.name = "map-configuration";
		this.aliases = Arrays.asList("mc", "mapc", "map-config");
		this.description = "Create a new map configuration";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_ADD_MAP;
		this.requiredArgs = Arrays.asList("name");
	}

	@Override
	protected void perform() {
		if (args.length >= 2) {
			new MapConfiguration(args[1]);
			getPlayer()
			        .sendMessage(ChatColor.GREEN + "Successfully created the new map configuration " + args[1] + ".");
		}
	}

}
