package net.senmori.hunted.reward;

import java.util.Random;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.Permissions.RewardMessage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SmiteReward extends Reward
{
	private String name;
	public SmiteReward(String name) 
	{
		this.name = name;
		Hunted.rewardManager.addReward(this);
	};
	
	@Override
	public void getLoot(Player player) 
	{
		Bukkit.getServer().getWorld(player.getWorld().getName()).strikeLightning(player.getLocation());
		player.sendMessage(ChatColor.GOLD + RewardMessage.SMITE_MESSAGE);
		
		Random rand = new Random();
		if(rand.nextInt(6) % 5 == 0)
		{
			for(int i = 0; i < Hunted.rewardManager.getRewards().size(); i++)
			{
				if(Hunted.rewardManager.getRewards().get(i).getClass().getSimpleName().equalsIgnoreCase("TeleportReward"))
				{
					Hunted.rewardManager.getRewards().get(i).getLoot(player);
				}
			}
		}
	}
	
	@Override
	public String getName()
	{
		return name;
	}

}
