package net.senmori.hunted.reward;

import net.senmori.hunted.managers.ConfigManager;

import org.bukkit.entity.Player;

public class EffectReward extends Reward 
{	
	private String name;
	public EffectReward(String name) 
	{
		this.name = name;
	}
	
	@Override
	public void generateLoot(Player player) 
	{
		
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	private int getRandomPotionLength(int min)
	{
		return (int) (Math.random() * (ConfigManager.maxEffectLength - min) + min);
	}
}
