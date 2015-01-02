package net.senmori.hunted.lib.item;

import java.util.List;

import net.senmori.hunted.lib.PlayerFile;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractItem implements IItem
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
	 * Gets the custom name of this item
	 */
	public abstract String getCustomName();
	
	/*
	 * Returns any extra data this block might have(wool, glass panes, etc)
	 */
	public abstract int getData();
	
	/*
	 * Return a valid list of enchantments
	 */
	public abstract List<Enchantment> getPossibleEnchantments();
}
