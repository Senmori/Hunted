package net.senmori.hunted.commands.edit;

import java.util.Arrays;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.LogHandler;
import net.senmori.hunted.util.Reference.Permissions;

public class EditCommand extends Subcommand {

	public EditCommand() 
	{
		this.name = "edit";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_EDIT;
		this.description = "Command used to edit a guardian stone and/or config options.";
		this.optionalArgs = Arrays.asList("[-c/config]","[-s/stone]");
	}

	@Override
	protected void perform() 
	{
		getPlayer().sendMessage("test" + name);
	}

}
