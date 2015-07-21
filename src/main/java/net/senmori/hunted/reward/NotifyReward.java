package net.senmori.hunted.reward;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.ConfigManager;
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
	}
	
	@Override
	public void generateLoot(Player player) 
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
		
		for(String uuid : Hunted.getPlayerManager().getPlayers().keySet())
		{
			if(uuid.equalsIgnoreCase(player.getUniqueId().toString())) continue; // ignore player who actived this stone
			String name = null;
			for(GuardianStone g : StoneManager.getGuardianStones())
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
			Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(ChatColor.AQUA + String.format(RewardMessage.NOTIFY_ALL, player.getName(), name));
		}
	}
	
	private void notifyWithin(Player player)
	{
		List<String> players = new ArrayList<>();
		
		for(Player p : Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers())
		{
			if(p.getLocation().distanceSquared(player.getLocation()) <= ConfigManager.nearbyRadius)
			{
				players.add(p.getUniqueId().toString());
			}
		}
		player.sendMessage(ChatColor.AQUA + String.format(RewardMessage.NOTIFY_WITHIN, players.size(), ConfigManager.nearbyRadius));
		players.clear(); // clear list just in case it gets stuck in memory
	}
	
	@Override
	public String getName()
	{
		return name;
	}
}
