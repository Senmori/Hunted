package net.senmori.hunted.commands.edit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.commands.edit.parameters.EditActiveMap;
import net.senmori.hunted.commands.edit.parameters.EditConfig;
import net.senmori.hunted.util.Reference.Permissions;

public class EditCommand extends Subcommand {

	List<Subcommand> parameters;

	public EditCommand() {
		this.name = "edit";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_EDIT;
		this.description = "Command used to edit a guardian stone, config options, or the active map configuration";
		this.optionalArgs = Arrays.asList("config", "stone", "map");

		parameters = new ArrayList<>();

		parameters.add(new EditConfig());
		parameters.add(new EditActiveMap());
	}

	@Override
	protected void perform() {
		List<String> argsList = new ArrayList<>();
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
	}

}
