package net.senmori.hunted.stones;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public abstract class Stone 
{
	private static List<Stone> stones;
	static
	{
		stones = new ArrayList<Stone>();
	}
	
	Stone()
	{
		stones.add(this);
	}
	public abstract void activate(Player player);
}
