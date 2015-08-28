package net.senmori.hunted.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportTask extends BukkitRunnable
{
	private final Player player;
	private final Location to;
	
	/**
	 * Teleports a player to a specified location </br>
	 * @param player - the player to teleport
	 * @param to - the new location
	 */
	public TeleportTask(Player player, Location to) {
		this.player = player;
		this.to = to;
	}

	@Override
    public void run()
    {
	   this.player.teleport(to);
	   this.cancel();
    }

}
