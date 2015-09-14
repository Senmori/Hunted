package net.senmori.hunted.managers.game;

import net.senmori.hunted.reward.Reward;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RewardManager {
	private List<Reward> rewards;
	
	public RewardManager() {
		rewards = new ArrayList<Reward>();
	}
	
	/*
	 * Loot generation process:
	 * - generate loot
	 * - give bonus xp as applicable
	 */
	public void generateReward(Player player) {
		Random rand = new Random();
		getRewards().get(rand.nextInt(getRewards().size())).generateLoot(player);
	}

	public List<Reward> getRewards() {
		return rewards;
	}
	
	public void addReward(Reward r) {
		rewards.add(r);
	}
	
	public Reward getReward(String name) {
		for(Reward r : getRewards()) {
			if(r.getName().equalsIgnoreCase(name)) {
				return r;
			}
		}
		return null;
	}
}
