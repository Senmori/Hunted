package net.senmori.hunted.reward;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionType;

public class PotionReward extends Reward 
{	
	private List<PotionType> potions;
	private String name;
	
	public PotionReward(String name) 
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
}
