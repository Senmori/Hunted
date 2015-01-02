package net.senmori.hunted.lib.item;

import net.senmori.hunted.lib.PlayerFile;

import org.bukkit.inventory.ItemStack;

public abstract class AbstractPotion implements IItem
{
	public abstract String getType();

	public abstract int getUpgradeCost();

	public abstract ItemStack toItemStack();
	
	public abstract void updatePlayerConfig();
	
	public abstract int getInventorySlot();
	
	public abstract int getSlotCost();
	
	public abstract PlayerFile getPlayerFile();
	
	public abstract void onLeftClick();
	
	public abstract void onRightClick();
	
	public abstract void onHover();
	
	/*
	 * Get the duration of time, in ticks, this potion is active
	 */
	public abstract int getDuration();
	
	/*
	 * Get the amplifier(level) of this potion
	 */
	public abstract int getAmplifier();
}
