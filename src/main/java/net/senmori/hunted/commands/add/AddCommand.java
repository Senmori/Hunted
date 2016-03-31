package net.senmori.hunted.commands.add;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.commands.add.parameters.AddArenaLocation;
import net.senmori.hunted.commands.add.parameters.AddLobbyLocation;
import net.senmori.hunted.commands.add.parameters.AddMapConfiguration;
import net.senmori.hunted.commands.add.parameters.AddStone;
import net.senmori.hunted.commands.add.parameters.AddStoreLocation;
import net.senmori.hunted.util.Reference.Permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddCommand extends Subcommand {

	private List<Subcommand> parameters;

	public AddCommand() {

		this.name = "add";
		this.needsPlayer = true;
		this.description = "Command to add stones or specified locations to Hunted";
		this.permission = Permissions.COMMAND_ADD;
		this.optionalArgs = Arrays.asList("stone", "lobby", "spawn", "store");

		parameters = new ArrayList<Subcommand>();
		// add arguments
		parameters.add(new AddLobbyLocation());
		parameters.add(new AddArenaLocation());
		parameters.add(new AddStone());
		parameters.add(new AddStoreLocation());
		parameters.add(new AddMapConfiguration());
	}

	@Override
	protected void perform() {
		List<String> argsList = new ArrayList<String>();
		if (args.length > 0) {
			String commandName = args[0].toLowerCase();

			for (int i = 1; i < args.length; i++) {
				argsList.add(args[i]);
			}
			for (Subcommand command : this.parameters) {
				if (command.getName().equalsIgnoreCase(commandName) || command.getAliases().contains(commandName)) {
					command.execute(sender, argsList.toArray(new String[argsList.size()]));
					return;
				}
			}
		} else {
			getPlayer().sendMessage(getUsageTemplate(true));
		}
		return;
	}
}
