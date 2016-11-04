package net.senmori.hunted.commands.add.parameters;


import java.text.MessageFormat;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.MapConfiguration;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.ChatColor;

import java.util.Arrays;

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
			ActionBar.sendMessage(getPlayer(), ChatColor.GREEN + MessageFormat.format(Reference.SuccessMessage.MAP_CONFIG_CREATED, args[1]));
		}
	}

}
