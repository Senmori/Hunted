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
		boolean near = true;
		Location toLoc = Hunted.getInstance().getSpawnManager().getRandomHuntedLocation().getLocation();
		if (toLoc.distanceSquared(player.getLocation()) <= 50) {
			while (near) {
				toLoc = Hunted.getInstance().getSpawnManager().getRandomHuntedLocation().getLocation();
				near = toLoc.distanceSquared(player.getLocation()) <= 50;
				if (!near) {
					break;
				}
			}
		}
		player.teleport(toLoc);
	}

	@Override
	public String getName() {
		return name;
	}

}
