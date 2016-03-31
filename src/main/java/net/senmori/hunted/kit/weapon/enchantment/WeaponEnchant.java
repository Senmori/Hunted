package net.senmori.hunted.kit.weapon.enchantment;

import net.senmori.hunted.kit.weapon.WeaponType;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;
import java.util.List;

public enum WeaponEnchant {
	ARROW_DAMAGE(Enchantment.ARROW_DAMAGE, Arrays.asList(WeaponType.BOW)),
	ARROW_FIRE(Enchantment.ARROW_FIRE, Arrays.asList(WeaponType.BOW)),
	ARROW_KNOCKBACK(Enchantment.ARROW_KNOCKBACK, Arrays.asList(WeaponType.BOW)),
	ARROW_INFINTE(Enchantment.ARROW_INFINITE, Arrays.asList(WeaponType.BOW)),
	DAMAGE_ALL(Enchantment.DAMAGE_ALL, Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD,
	        WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD)),
	DAMAGE_UNDEAD(Enchantment.DAMAGE_UNDEAD, Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD,
	        WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD)),
	DAMAGE_ARTHROPODS(Enchantment.DAMAGE_ARTHROPODS, Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD,
	        WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD)),
	DURABILITY(Enchantment.DURABILITY, Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD,
	        WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD)),
	FIRE_ASPECT(Enchantment.FIRE_ASPECT, Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD,
	        WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD)),
	KNOCKBACK(Enchantment.KNOCKBACK, Arrays.asList(WeaponType.WOOD_SWORD, WeaponType.STONE_SWORD,
	        WeaponType.IRON_SWORD, WeaponType.GOLD_SWORD));

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
