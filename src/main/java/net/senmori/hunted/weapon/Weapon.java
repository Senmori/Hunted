package net.senmori.hunted.weapon;

import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public interface Weapon
{
	/*
	 * Sets this weapon's display name
	 */
	public void setDisplayName(String display);
	
	/*
	 * Add enchantment to weapon, default to level 1
	 */
	public void addEnchantment(Enchantment enchant);
	
	/*
	 * Add enchantment to weapon
	 */
	public void addEnchantment(Enchantment enchant, int level);
	
	/*
	 * Add a map of enchantments and their levels
	 */
	public void addEnchantment(Map<Enchantment,Integer> enchant);
	
	/*
	 * Set what type of weapon this is
	 */
	public void setType(WeaponType type);
	
	/*
	 * Set how many uses this weapon has left
	 */
	public void setUses(int damage);
	
	/*
	 * Transforms the current weapon into an itemstack 
	 */
	public ItemStack toItemStack();
}
