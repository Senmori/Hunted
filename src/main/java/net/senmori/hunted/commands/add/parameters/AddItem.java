package net.senmori.hunted.commands.add.parameters;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;

public class AddItem extends Subcommand
{

	public AddItem()
	{
		this.name = "item";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_ADD;
	}

	@Override
	protected void perform()
	{

	}

}
