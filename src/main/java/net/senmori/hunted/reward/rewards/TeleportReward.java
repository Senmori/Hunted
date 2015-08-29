package net.senmori.hunted.reward.rewards;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.game.SpawnManager;
import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.util.LogHandler;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportReward extends Reward
{
	private String name;
	public TeleportReward(String name) 
	{
		this.name = name;
	};
	
	@Override
	public void generateLoot(Player player) 
	{
		Location toLoc = Hunted.getInstance().getSpawnManager().getRandomHuntedLocation().getLocation();
		do 
		{
			toLoc = Hunted.getInstance().getSpawnManager().getRandomHuntedLocation().getLocation();
			LogHandler.debug("Teleport location is less than 50 blocks away. Choosing new location!");
		}
		while(toLoc.distanceSquared(player.getLocation()) <= 50);
		
		player.teleport(toLoc);
	}
	
	@Override
	public String getName()
	{
		return name;
	}

}