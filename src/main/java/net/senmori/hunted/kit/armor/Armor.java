package net.senmori.hunted.kit.armor;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

/**
 * What armor type can be rewarded or generated in kits <br>
 * {@link Material} - the type of material this armor is made from <br>
 * {@link EquipmentSlot} - the {@link EquipmentSlot} this armor fits into <br>
 */
public enum Armor {
    LEATHER_HELMET(Material.LEATHER_HELMET, EquipmentSlot.HEAD),
    LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE, EquipmentSlot.CHEST),
    LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS, EquipmentSlot.LEGS),
    LEATHER_BOOTS(Material.LEATHER_BOOTS, EquipmentSlot.FEET),
    GOLD_HELMET(Material.GOLD_HELMET, EquipmentSlot.HEAD),
    GOLD_CHESTPLATE(Material.GOLD_CHESTPLATE, EquipmentSlot.CHEST),
    GOLD_LEGGINGS(Material.GOLD_LEGGINGS, EquipmentSlot.LEGS),
    GOLD_BOOTS(Material.GOLD_BOOTS, EquipmentSlot.FEET),
    IRON_HELMET(Material.IRON_HELMET, EquipmentSlot.HEAD),
    IRON_CHESTPLATE(Material.IRON_CHESTPLATE, EquipmentSlot.CHEST),
    IRON_LEGGINGS(Material.IRON_LEGGINGS, EquipmentSlot.LEGS),
    IRON_BOOTS(Material.IRON_BOOTS, EquipmentSlot.FEET),
    DIAMOND_HELMET(Material.DIAMOND_HELMET, EquipmentSlot.HEAD),
    DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, EquipmentSlot.CHEST),
    DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, EquipmentSlot.LEGS),
    DIAMOND_BOOTS(Material.DIAMOND_BOOTS, EquipmentSlot.FEET);

    private Material material;
    private EquipmentSlot slot;

    Armor(Material mat, EquipmentSlot slot) {
        this.material = mat;
        this.slot = slot;
    }

    public Material getType() {
        return this.material;
    }

    public EquipmentSlot getSlot() {
        return this.slot;
    }
}
