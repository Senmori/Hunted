package net.senmori.hunted.commands.list;

import java.util.Arrays;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;

public class ListCommand extends Subcommand {

	public ListCommand() 
	{
		this.name = "list";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_LIST;
		this.description = "Used to list various things about this plugin.";
		this.optionalArgs = Arrays.asList("-s [name/ID]", "-c [config]", "-k [loot]");
	}

	@Override
	protected void perform() 
	{
		
	}

}
