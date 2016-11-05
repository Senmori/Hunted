package net.senmori.hunted.kit.armor;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.EquipmentSlot;

public enum ArmorEnchantment {

	PROTECTION(EnchantmentTarget.ARMOR, Enchantment.PROTECTION_ENVIRONMENTAL),
	FIRE_PROTECTION(EnchantmentTarget.ARMOR, Enchantment.PROTECTION_FIRE),
	FEATHER_FALLING(EnchantmentTarget.ARMOR_FEET, Enchantment.PROTECTION_FALL),
	BLAST_PROTECTION(EnchantmentTarget.ARMOR, Enchantment.PROTECTION_EXPLOSIONS),
	PROJECTILE_PROTECTION(EnchantmentTarget.ARMOR, Enchantment.PROTECTION_PROJECTILE),
	RESPIRATION(EnchantmentTarget.ARMOR_HEAD, Enchantment.OXYGEN),
	THORNS(EnchantmentTarget.ARMOR, Enchantment.THORNS),
	DEPTH_STRIDER(EnchantmentTarget.ARMOR_FEET, Enchantment.DEPTH_STRIDER),
	UNBREAKING(EnchantmentTarget.ALL, Enchantment.DURABILITY);

	private EnchantmentTarget slot;
	private Enchantment enchant;

	ArmorEnchantment(EnchantmentTarget slot, Enchantment enchant) {
		this.slot = slot;
		this.enchant = enchant;
	}

	public EnchantmentTarget getSlot() {
		return slot;
	}

	public Enchantment getEnchant() {
		return enchant;
	}
    
    
    /**
     * Checks if this ArmorEnchant can enchant a specific EquipmentSlot
     *
     * @param slot
     * @return
     */
	public boolean canEnchant(EquipmentSlot slot) {
        switch(this) {
            case PROTECTION:
            case FIRE_PROTECTION:
            case BLAST_PROTECTION:
            case PROJECTILE_PROTECTION:
            case THORNS:
            case UNBREAKING:
                switch(slot) {
                    case HAND:
                    case OFF_HAND:
                        return false;
                    default:
                        return true;
                }
            case DEPTH_STRIDER:
            case FEATHER_FALLING:
                switch(slot) {
                    case FEET:
                        return true;
                    default:
                        return false;
                }
            
            case RESPIRATION:
                switch(slot) {
                    case HEAD:
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }
}
