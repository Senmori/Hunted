package net.senmori.hunted.weapon;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class AbstractWeapon implements Weapon
{
	protected String displayName;
	protected Map<Enchantment, Integer> enchantments;
	protected WeaponType type;
	protected int usesLeft;
	
	
	public AbstractWeapon(String displayName, WeaponType type)
	{
		this.displayName = displayName;
		this.type = type;
		enchantments = new HashMap<>();
	}
	
	public void setDisplayName(String name)
	{
		this.displayName = name;
	}
	
	public void addEnchantment(Enchantment enchant)
	{
		enchantments.put(enchant, 1);
	}
	
	public void addEnchantment(Enchantment enchant, int level)
	{
		enchantments.put(enchant, level);
	}
	
	public void addEnchantment(Map<Enchantment, Integer> enchants)
	{
		enchantments.putAll(enchants);
	}
	
	public void setType(WeaponType type)
	{
		this.type = type;
	}
	
	public void setUses(int uses)
	{
		this.usesLeft = uses;
	}
	
	public ItemStack toItemStack()
	{
		ItemStack weapon = new ItemStack(type.getType());
		// add damage attribute so it can compete with swords
		if(enchantments.keySet().size() > 0)
		{
			weapon.addEnchantments(enchantments);
		}
		ItemMeta meta = weapon.getItemMeta();
		meta.setDisplayName(displayName);
		weapon.setItemMeta(meta);
		if(usesLeft > 0)
		{
			weapon.setDurability((short)(type.getType().getMaxDurability()-usesLeft));
		}
		
		return weapon;
	}
	
	public WeaponType getType()
	{
		return type;
	}
	
	public abstract void onEquip();
	public abstract void onAttackEntity(Player aggressor, Player target);
}
