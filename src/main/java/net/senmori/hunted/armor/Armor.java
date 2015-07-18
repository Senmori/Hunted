package net.senmori.hunted.armor;

import java.util.List;

import org.bukkit.Material;

public interface Armor
{
	/*
	 * Set the material of this armor
	 */
	public void setMaterial(Material material);
	
	/*
	 * Set the durability of this piece of armor
	 */
	public void setDurability(int durability);
	
	/*
	 * Set the display name of this armor
	 */
	public void setDisplayName(String name);
	
	/*
	 * Set the item's lore
	 */
	public void setLore(List<String> lore);
	
	/*
	 * What to do when this armor is equipped
	 */
	public void onEquip(boolean timed);
	
	
}
