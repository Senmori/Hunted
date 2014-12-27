package net.senmori.hunted.reward;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IrritatingReward extends Reward 
{
	@Override
	public void pickLoot(Player player) 
	{
		ItemStack[] inventory = player.getInventory().getContents();
		
		// rearrange player inventory, *evil laugh*
		for(int i = 0; i < inventory.length; i++)
		{
			
		}
	}

}
