package net.senmori.hunted.managers;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.reward.Reward;

import org.bukkit.entity.Player;

public class RewardManager 
{
	private Hunted plugin;
	
	private List<Class<? extends Reward>> rewardList;
	
	
	public RewardManager(Hunted instance)
	{
		this.plugin = instance;
		rewardList = new ArrayList<Class<? extends Reward>>();
	}
	
	/*
	 * used to generate loot
	 */
	public void generateReward(Player player)
	{
		
	}
	
	public void addReward(Reward reward)
	{
		
	}
}
