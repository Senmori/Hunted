package net.senmori.hunted.commands.delete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.commands.delete.parameters.DeleteLocation;
import net.senmori.hunted.commands.delete.parameters.DeleteStone;
import net.senmori.hunted.util.Reference.Permissions;

public class DeleteCommand extends Subcommand {

	List<Subcommand> parameters;

	public DeleteCommand() {
		this.name = "delete";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_DELETE;
		this.description = "Command to delete guardian/teleport stones and/or locations";
		this.optionalArgs = Arrays.asList("stone", "location");

		parameters = new ArrayList<>();
		parameters.add(new DeleteStone());
		parameters.add(new DeleteLocation());
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
