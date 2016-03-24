package net.senmori.hunted.commands.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.commands.list.parameters.ListArenaLocations;
import net.senmori.hunted.commands.list.parameters.ListGuardianStones;
import net.senmori.hunted.commands.list.parameters.ListLobbyLocations;
import net.senmori.hunted.commands.list.parameters.ListStoreLocations;
import net.senmori.hunted.commands.list.parameters.ListTeleportStones;
import net.senmori.hunted.util.Reference.Permissions;

public class ListCommand extends Subcommand {

	private List<Subcommand> parameters;

	public ListCommand() {
		this.name = "list";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_LIST;
		this.description = "Used to list various things about this plugin.";
		this.optionalArgs = Arrays.asList("guardian", "teleport", "arena", "lobby", "store");

		parameters = new ArrayList<>();
		parameters.add(new ListGuardianStones());
		parameters.add(new ListTeleportStones());
		parameters.add(new ListArenaLocations());
		parameters.add(new ListStoreLocations());
		parameters.add(new ListLobbyLocations());

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
