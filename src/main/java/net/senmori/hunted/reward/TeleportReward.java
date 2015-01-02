package net.senmori.hunted.reward;

import net.senmori.hunted.Hunted;

import org.bukkit.entity.Player;

public class TeleportReward extends Reward
{
	private String name;
	public TeleportReward(String name) 
	{
		Hunted.rewardManager.addReward(this);
	};
	
	@Override
	public void getLoot(Player player) 
	{
		
	}
	
	@Override
	public String getName()
	{
		return name;
	}

}
