package net.senmori.hunted.commands.stuck;

import java.util.Arrays;

import org.bukkit.Location;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.util.Reference.Permissions;

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
		// handle players stuck in the actual Hunted arena
		//if(Hunted.getPlayerManager().getPlayers().get(getPlayer().getUniqueId().toString()).equals(State.IN_GAME))
		//{
			boolean sameLocation = true;
			Location newLocation = null;
			while(sameLocation)
			{
				newLocation = SpawnManager.getRandomSpawnLocation().getLocation();
				sameLocation = newLocation.distanceSquared(getPlayer().getLocation()) <= 25 ? true : false;
			}
			getPlayer().teleport(newLocation);
		//}
		
	}

}
