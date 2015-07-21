package net.senmori.hunted.kit.weapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;

public enum WeaponEnchant
{
	ARROW_DAMAGE(Enchantment.ARROW_DAMAGE, new ArrayList<WeaponType>(Arrays.asList(WeaponType.BOW))),
	ARROW_FIRE(Enchantment.ARROW_FIRE, new ArrayList<WeaponType>(Arrays.asList(WeaponType.BOW))),
	ARROW_KNOCKBACK(Enchantment.ARROW_KNOCKBACK, new ArrayList<WeaponType>(Arrays.asList(WeaponType.BOW))),
	ARROW_INFINTE(Enchantment.ARROW_INFINITE, new ArrayList<WeaponType>(Arrays.asList(WeaponType.BOW))),
	DAMAGE_ALL(Enchantment.DAMAGE_ALL, new ArrayList<WeaponType>(Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD, WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD))),
	DAMAGE_UNDEAD(Enchantment.DAMAGE_UNDEAD, new ArrayList<WeaponType>(Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD, WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD))),
	DAMAGE_ARTHROPODS(Enchantment.DAMAGE_ARTHROPODS, new ArrayList<WeaponType>(Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD, WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD))),
	DURABILITY(Enchantment.DURABILITY, new ArrayList<WeaponType>(Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD, WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD))),
	FIRE_ASPECT(Enchantment.FIRE_ASPECT, new ArrayList<WeaponType>(Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD, WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD))),
	KNOCKBACK(Enchantment.KNOCKBACK, new ArrayList<WeaponType>(Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD, WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD)));
	
	private Enchantment enchant;
	private List<WeaponType> associated;
	
	WeaponEnchant(Enchantment enchant, List<WeaponType> type) {
		this.enchant = enchant;
		this.associated = type;
	}
	
	public Enchantment getEnchant() {
		return this.enchant;
	}
	
	public List<WeaponType> getAssociatedTypes() {
		return this.associated;
	}
}
