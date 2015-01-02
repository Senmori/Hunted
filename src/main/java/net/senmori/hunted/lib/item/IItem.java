package net.senmori.hunted.lib.item;

import net.senmori.hunted.lib.PlayerFile;

import org.bukkit.inventory.ItemStack;

public interface IItem 
{
	/*
	 * Get the type of material or potion type for this item
	 */
	public abstract String getType();
	
	/*
	 * Get how much it takes to upgrade/unlock this item
	 */
	public int getUpgradeCost();
	
	/*
	 * Converts this item into it's appropriate itemstack
	 */
	public ItemStack toItemStack();
	
	/*
	 * Updates the given player's config to show their
	 */
	public void updatePlayerConfig();
	
	/*
	 * Returns the assigned inventory slot for this item
	 */
	public int getInventorySlot();
	
	/*
	 * Return how many slots this item takes up in regards to loadouts
	 */
	public int getSlotCost();
	
	/*
	 * Gets the player file so we can easily get information on this item, from this player's config
	 */
	public PlayerFile getPlayerFile();
	
	public void onLeftClick();
	
	public void onRightClick();
	
	public void onHover();
}
