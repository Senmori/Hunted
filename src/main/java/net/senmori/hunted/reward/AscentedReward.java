package net.senmori.hunted.reward;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.util.LogHandler;

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
	public void generateLoot(Player player) 
	{	
		// give ascented item
		if(rand.nextInt(net.senmori.hunted.Hunted.ascentedItemChance+1) % net.senmori.hunted.Hunted.ascentedItemChance == 0)
		{
			ascentedItems.addAll(net.senmori.hunted.Hunted.lootConfig.getConfigurationSection("loot.ascented").getKeys(false));
			
			//String item = ascentedItems.get(rand.nextInt(ascentedItems.size()+1));
			
			return;
		}
		LogHandler.info("Generated an ascented reward!");
	}
	
	@Override
	public String getName()
	{
		return name;
	}
}
