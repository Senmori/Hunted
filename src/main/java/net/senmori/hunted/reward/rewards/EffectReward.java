package net.senmori.hunted.reward.rewards;

import net.senmori.hunted.reward.Reward;

import org.bukkit.entity.Player;

public class EffectReward extends Reward {	
	private String name;
	public EffectReward(String name) {
		this.name = name;
	}
	
	@Override
	public void generateLoot(Player player) {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}
