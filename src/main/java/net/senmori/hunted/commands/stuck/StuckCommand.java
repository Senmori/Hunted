package net.senmori.hunted.commands.stuck;

import java.util.Arrays;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.util.Reference.Permissions;

import org.bukkit.Bukkit;

public class StuckCommand extends Subcommand
{

	public StuckCommand()
	{
		this.name = "stuck";
		this.needsPlayer = true;
		this.description = "Command used if a player is stuck.";
		this.permission = Permissions.COMMAND_STUCK;
		this.optionalArgs = Arrays.asList("[player]");
	}

	@Override
	protected void perform()
	{
		if(args.length > 0)
		{
			if(Bukkit.getPlayer(args[0]) != null)
			{
				Bukkit.getPlayer(args[0]).teleport(SpawnManager.getRandomHuntedLocation().getLocation());
				return;
			}
		}
		getPlayer().teleport(SpawnManager.getRandomHuntedLocation().getLocation());
	}

}
