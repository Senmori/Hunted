package net.senmori.hunted.reward;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.Hunted;

import org.bukkit.entity.Player;

public class AscentedReward extends Reward 
{
	private List<String> ascentedItems;
	private String name;
	private Random rand;
	public AscentedReward(String name)
	{
		this.name = name;
		ascentedItems = new ArrayList<String>();
		rand = new Random();
	}
	
	@Override
	public void getLoot(Player player) 
	{	
		// give ascented item
		if(rand.nextInt(Hunted.ascentedItemChance+1) % Hunted.ascentedItemChance == 0)
		{
			ascentedItems.addAll(Hunted.lootConfig.getConfigurationSection("loot.ascented").getKeys(false));
			
			String item = ascentedItems.get(rand.nextInt(ascentedItems.size()+1));
			
			return;
		}
		Hunted.getRewardManager().generateReward(player);
	}
	
	@Override
	public String getName()
	{
		return name;
	}
}
