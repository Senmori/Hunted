package net.senmori.hunted.reward;

import java.util.Random;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.Reference.RewardMessage;

import org.bukkit.entity.Player;

public class SmiteReward extends Reward
{
	private String name;
	public SmiteReward(String name) 
	{
		this.name = name;
		Hunted.getRewardManager().addReward(this);
	}
	
	@Override
	public void getLoot(Player player) 
	{
		player.getWorld().strikeLightning(player.getLocation());
		player.sendMessage(ChatColor.GOLD + RewardMessage.SMITE_MESSAGE);
		
		Random rand = new Random();
		int chance = Hunted.getInstance().getConfig().getInt("smite-teleport-chance");
		if(rand.nextInt(chance+1) % chance == 0)
		{
			Hunted.getRewardManager().getReward("teleport").getLoot(player);
		}
	}
	
	@Override
	public String getName()
	{
		return name;
	}

}
