package net.senmori.hunted.commands.exempt;

import java.util.Arrays;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.LogHandler;
import net.senmori.hunted.util.Reference.Permissions;

public class ExemptCommand extends Subcommand
{
	public ExemptCommand()
    {
		this.name = "exempt";
		this.needsPlayer = true;
		this.description = "Used to exempt a player(s) from the Hunted setup process.";
		this.optionalArgs = Arrays.asList("[player]");
		this.permission = Permissions.COMMAND_EXEMPT;
    }
	@Override
    protected void perform()
    {
		getPlayer().sendMessage("test" + name);
    }
}
