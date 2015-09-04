package net.senmori.hunted.reward.rewards;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.reward.Reward;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportReward extends Reward {
	private String name;
	public TeleportReward(String name) {
		this.name = name;
	}
	
	@Override
	public void generateLoot(Player player) {
	    boolean close = true;
		Location toLoc = Hunted.getInstance().getSpawnManager().getRandomHuntedLocation().getLocation();
		if(toLoc.distanceSquared(player.getLocation()) <= 50) {
		    while(close) {
		        toLoc = Hunted.getInstance().getSpawnManager().getRandomHuntedLocation().getLocation();
		        close = toLoc.distanceSquared(player.getLocation()) <= 50;
		        if(!close) break;
		    }
		}
		player.teleport(toLoc);
	}
	
	@Override
	public String getName() {
		return name;
	}

}
