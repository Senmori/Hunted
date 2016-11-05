package net.senmori.hunted.reward.rewards;


import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.util.Reference.RewardMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

public class IrritatingReward extends Reward {
	private String name;
    private Random random;
    
	public IrritatingReward(String name) {
		this.name = name;
        random = new Random();
	}

	/*
	 * Rearrange player's inventory, quite annoying...
	 */
	@Override
	public void generateLoot(Player player) {
		ItemStack[] inventory = player.getInventory().getContents();

		for (int i = 0; i <= inventory.length; i++) {
			int oldSlot = i;
			int newSlot = random.nextInt(inventory.length);
            if(oldSlot == newSlot) {
                newSlot = random.nextInt(inventory.length);
            }
            
			PlayerInventory pInv = player.getInventory();
			// if oldSlot has an item, and new slot doesn't, move item to new slot
			if (pInv.getItem(oldSlot) != null && pInv.getItem(newSlot) == null) {
				pInv.setItem(newSlot, pInv.getItem(oldSlot));
				pInv.setItem(oldSlot, null);
			} else { // otherwise, switch slots
				ItemStack oldItem = pInv.getItem(oldSlot);
				ItemStack newItem = pInv.getItem(newSlot);

				pInv.setItem(oldSlot, newItem);
				pInv.setItem(newSlot, oldItem);
			}
		}
		player.sendMessage(ChatColor.GOLD + RewardMessage.IRRITATING_MESSAGE);
	}

	@Override
	public String getName() {
		return name;
	}
}
