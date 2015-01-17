package net.senmori.hunted.reward;

import java.util.Random;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.LogHandler;
import net.senmori.hunted.util.Permissions.RewardMessage;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class IrritatingReward extends Reward 
{
	private String name;
	public IrritatingReward(String name) 
	{
		this.name = name;
		Hunted.rewardManager.addReward(this);
	};
	
	/*
	 * Rearrange player's inventory, quite annoying...
	 */
	@Override
	public void getLoot(Player player) 
	{
		ItemStack[] inventory = player.getInventory().getContents();
		Random rand = new Random();
		
		for(int i = 0; i <= inventory.length; i++)
		{
			int oldSlot = i;
			int newSlot = rand.nextInt(inventory.length+1);
			
			PlayerInventory pInv = player.getInventory();
			// if oldSlot has an item, and new slot doesn't, move item to new slot
			if(pInv.getItem(oldSlot) != null && pInv.getItem(newSlot) == null)
			{
				pInv.setItem(newSlot, pInv.getItem(oldSlot));
				pInv.setItem(oldSlot, null);
			}
			else // otherwise, switch slots
			{
				ItemStack oldItem = pInv.getItem(oldSlot);
				ItemStack newItem = pInv.getItem(newSlot);
				
				pInv.setItem(oldSlot, newItem);
				pInv.setItem(newSlot, oldItem);
			}
		}
		player.sendMessage(ChatColor.GOLD + RewardMessage.IRRITATING_MESSAGE);
		LogHandler.debug(player.getDisplayName() + " receieved an irritating reward.");
	}
	
	@Override
	public String getName()
	{
		return name;
	}
}