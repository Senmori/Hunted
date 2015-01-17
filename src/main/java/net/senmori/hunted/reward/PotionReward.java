package net.senmori.hunted.reward;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.Permissions.RewardMessage;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class PotionReward extends Reward 
{	
	private List<PotionType> potions;
	private String name;
	
	public PotionReward(String name) 
	{
		this.name = name;
		potions = new ArrayList<PotionType>();
		
		for(String type : Hunted.lootConfig.getStringList("loot.item.potion"))
		{
			PotionType pt = PotionType.valueOf(type);
			Validate.notNull(pt, "Error importing potion type " + pt + ". Is it misspelled?");
			if(pt != null)
			{
				potions.add(pt);
			}
		}
	}
	
	@Override
	public void getLoot(Player player) 
	{
		// pick random potion from enum & generate data
		Random rand = new Random();
		int level = rand.nextInt(Hunted.potionTierChance+1) % Hunted.potionTierChance == 0 ? 1 : 2;
		
		PotionType pt = potions.get(rand.nextInt(potions.size()));
		Potion pot = new Potion(pt, level);
		// only extend potion duration if it's level 2
		pot.setHasExtendedDuration(level > 1 ? true : false);
		
		// random amount between 1 and 4
		player.getInventory().addItem(pot.toItemStack(1));
		player.sendMessage(ChatColor.GOLD + String.format(RewardMessage.STONE_REWARD, pot.toItemStack(1).getItemMeta().getDisplayName()));
	}
	
	@Override
	public String getName()
	{
		return name;
	}
}
