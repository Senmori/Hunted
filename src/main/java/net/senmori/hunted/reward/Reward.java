package net.senmori.hunted.reward;

import org.bukkit.entity.Player;

public abstract class Reward 
{
	public abstract void getLoot(Player player);
	
	public String getName()
	{
		return null;
	}
}
