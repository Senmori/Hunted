package net.senmori.hunted.commands.delete;

import java.util.Arrays;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;


public class DeleteCommand extends Subcommand
{
	public DeleteCommand()
	{
		this.name = "delete";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_DELETE;
		this.description = "Command to delete guardian/teleport stones.";
		this.optionalArgs = Arrays.asList("name");
	}

	@Override
    protected void perform()
    {
		
    }

}
