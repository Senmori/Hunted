package net.senmori.hunted.reward;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.game.StoneManager;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.util.Reference.RewardMessage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NotifyReward extends Reward 
{
	private String name;
	public NotifyReward(String name) 
	{
		this.name = name;
		Hunted.getRewardManager().addReward(this);
	};
	
	@Override
	public void getLoot(Player player) 
	{
		// generate random number between 1 and 5
		int rNum = (int) (Math.random() * (5-1) + 1);
		
		// if number is 5[20% chance], notify all players of rewardee's location
		if(rNum % 5 == 0)
		{
			notifyAll(player);
			return;
		}
		notifyWithin(player);
	}
	
	private void notifyAll(Player player)
	{
		List<Player> players = Bukkit.getWorld(player.getWorld().getName()).getPlayers();
		
		for(Player p : players)
		{
			String name = null;
			for(GuardianStone g : StoneManager.guardianStoneList)
			{
				// reach distance for SMP is 5, use that as default
				if(g.getLocation().distanceSquared(player.getLocation()) <= 5)
				{
					// if guardian stone isn't named, return the location of the guardian stone
					if(name.isEmpty() || name == null)
					{
						// name = [x,y,z,world]
						name = "[" + g.getLocation().getBlockX() + "," + g.getLocation().getBlockY() + "," + g.getLocation().getBlockZ() + "," + g.getLocation().getWorld().getName() +  "]";
					}
					name = g.getName();
				}
			}
			p.sendMessage(ChatColor.AQUA + String.format(RewardMessage.NOTIFY_ALL, player.getName(), name));
		}
	}
	
	private void notifyWithin(Player player)
	{
		List<Player> players = new ArrayList<Player>();
		
		for(Player p : Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers())
		{
			if(p.getLocation().distanceSquared(player.getLocation()) <= Hunted.nearbyRadius)
			{
				players.add(p);
			}
		}
		player.sendMessage(ChatColor.AQUA + String.format(RewardMessage.NOTIFY_WITHIN, players.size(), Hunted.nearbyRadius));
		players.clear(); // clear list just in case it gets stuck in memory
	}
	
	@Override
	public String getName()
	{
		return name;
	}
}
