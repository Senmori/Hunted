package net.senmori.hunted.kit.armor;

import org.bukkit.enchantments.Enchantment;

public enum ArmorEnchantment {

	PROTECTION(ArmorSlot.ALL, Enchantment.PROTECTION_ENVIRONMENTAL),
	FIRE_PROTECTION(ArmorSlot.ALL, Enchantment.PROTECTION_FIRE),
	FEATHER_FALLING(ArmorSlot.BOOTS, Enchantment.PROTECTION_FALL),
	BLAST_PROTECTION(ArmorSlot.ALL, Enchantment.PROTECTION_EXPLOSIONS),
	PROJECTILE_PROTECTION(ArmorSlot.ALL, Enchantment.PROTECTION_PROJECTILE),
	RESPIRATION(ArmorSlot.HELMET, Enchantment.OXYGEN),
	THORNS(ArmorSlot.ALL, Enchantment.THORNS),
	DEPTH_STRIDER(ArmorSlot.BOOTS, Enchantment.DEPTH_STRIDER),
	UNBREAKING(ArmorSlot.ALL, Enchantment.DURABILITY);

	private ArmorSlot slot;
	private Enchantment enchant;

	ArmorEnchantment(ArmorSlot slot, Enchantment enchant) {
		this.slot = slot;
		this.enchant = enchant;
	}

	public ArmorSlot getSlot() {
		return slot;
	}

	public Enchantment getEnchant() {
		return enchant;
	}
}
