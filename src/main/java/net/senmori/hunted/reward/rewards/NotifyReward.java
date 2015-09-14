package net.senmori.hunted.reward.rewards;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.game.GameState;
import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.util.Reference.RewardMessage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NotifyReward extends Reward {
	private String name;
	public NotifyReward(String name) {
		this.name = name;
	}
	
	@Override
	public void generateLoot(Player player) {
		// if number is 5[20% chance], notify all players of rewardee's location
		if((int) (Math.random() *(5-1) + 1)>= 5) {
			notifyAll(player);
			return;
		}
		notifyWithin(player);
	}
	
	/* Notify all players IN_GAME of where the player who activated this guardian stone currently is */
	private void notifyAll(Player player) {
		
		for(String uuid : Hunted.getInstance().getPlayerManager().getPlayers().keySet()) {
		    if(!Hunted.getInstance().getPlayerManager().getState(uuid).equals(GameState.IN_GAME)) continue; // ignore everyone not playing
			if(uuid.equalsIgnoreCase(player.getUniqueId().toString())) continue; // ignore player who actived this stone
			String name = null;
			for(GuardianStone g : Hunted.getInstance().getStoneManager().getGuardianStones()) {
				// reach distance for SMP is 5, use that as default
				if(g.getLocation().distanceSquared(player.getLocation()) <= 5) {
					// if guardian stone isn't named, return the location of the guardian stone
					if(name.isEmpty() || name == null) {
						// name = [x,y,z,world]
						name = "[" + g.getLocation().getBlockX() + "," + g.getLocation().getBlockY() + "," + g.getLocation().getBlockZ() + "," + g.getLocation().getWorld().getName() +  "]";
						continue;
					}
					name = g.getName();
				}
			}
			if(Bukkit.getPlayer(UUID.fromString(uuid)) != null) {
			   Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(ChatColor.AQUA + MessageFormat.format(RewardMessage.NOTIFY_ALL, player.getName(), name)); 
			}
			//Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(ChatColor.AQUA + String.format(RewardMessage.NOTIFY_ALL, player.getName(), name));
		}
	}
	
	/* Notify player who activated Guardian Stone of how many players are nearby */
	private void notifyWithin(Player player) {
		List<String> players = new ArrayList<>();
		
		for(Player p : Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers()) {
			if(p.getLocation().distanceSquared(player.getLocation()) <= Hunted.getInstance().getConfigManager().nearbyRadius) {
				players.add(p.getUniqueId().toString());
			}
		}
		//player.sendMessage(ChatColor.AQUA + String.format(RewardMessage.NOTIFY_WITHIN, players.size(), Hunted.getInstance().getConfigManager().nearbyRadius));
		player.sendMessage(ChatColor.AQUA + MessageFormat.format(RewardMessage.NOTIFY_WITHIN, players.size(), Hunted.getInstance().getConfigManager().nearbyRadius));
		players.clear(); // clear list just in case it gets stuck in memory
	}
	
	@Override
	public String getName() {
		return name;
	}
}
