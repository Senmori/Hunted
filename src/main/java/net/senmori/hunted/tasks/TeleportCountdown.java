package net.senmori.hunted.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportCountdown extends BukkitRunnable
{
	private final Player player;
	private final Location loc;
	private int count;
	private final boolean showCountdown;
	
	/**
	 * Generates a new countdown for teleporting a player </br>
	 * See: {@link TeleportTask} </br>
	 * @param player - the player to teleport
	 * @param count - the seconds until the player is teleported
	 * @param loc - the location to teleport to when countdown is complete
	 */
	public TeleportCountdown(Player player, int count, Location loc, boolean showCountdown) {
		this.player = player;
		this.loc = loc;
		this.count = count;
		this.showCountdown = showCountdown;
	}
	
	public void executeTask() {
	}

	@Override
    public void run() {
	    if(showCountdown) {
	        player.sendMessage(count + "...");
	    }
	    count--;
	    if(count <= 0) {
	    	new TeleportTask(player, loc).run();
	    	this.cancel();
	    }
    }

}
