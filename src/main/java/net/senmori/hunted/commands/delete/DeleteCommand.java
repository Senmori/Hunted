package net.senmori.hunted.commands.delete;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MonsterEggs;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.LogHandler;
import net.senmori.hunted.util.Reference.Permissions;


public class DeleteCommand extends Subcommand
{
	public DeleteCommand()
	{
		this.name = "delete";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_DELETE;
		this.description = "Command to delete guardian stones.";
		this.optionalArgs = Arrays.asList("-s [name/ID]");
	}

	@Override
    protected void perform()
    {
		getPlayer().sendMessage("test" + name);
    }

}
