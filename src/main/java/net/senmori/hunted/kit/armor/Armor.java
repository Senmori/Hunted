package net.senmori.hunted.kit.armor;

import org.bukkit.Material;

/**
 * What armor type can be rewarded or generated in kits <br>
 * {@link material} - the type of material this armor is made from <br>
 * {@link slot} - the {@link ArmorSlot} this armor fits into <br>
 */
public enum Armor {
	LEATHER_HELMET(Material.LEATHER_HELMET, ArmorSlot.HELMET, 0.1),
	LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE, ArmorSlot.CHESTPLATE, 0.1),
	LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS, ArmorSlot.LEGGINGS, 0.1),
	LEATHER_BOOTS(Material.LEATHER_BOOTS, ArmorSlot.BOOTS, 0.1),
	GOLD_HELMET(Material.GOLD_HELMET, ArmorSlot.HELMET, 0.1),
	GOLD_CHESTPLATE(Material.GOLD_CHESTPLATE, ArmorSlot.CHESTPLATE, 0.0),
	GOLD_LEGGINGS(Material.GOLD_LEGGINGS, ArmorSlot.LEGGINGS, 0.0),
	GOLD_BOOTS(Material.GOLD_BOOTS, ArmorSlot.BOOTS, 0.0),
	IRON_HELMET(Material.IRON_HELMET, ArmorSlot.HELMET, 0.1),
	IRON_CHESTPLATE(Material.IRON_CHESTPLATE, ArmorSlot.CHESTPLATE, 0.1),
	IRON_LEGGINGS(Material.IRON_LEGGINGS, ArmorSlot.LEGGINGS, 0.1),
	IRON_BOOTS(Material.IRON_BOOTS, ArmorSlot.BOOTS, 0.1),
	DIAMOND_HELMET(Material.DIAMOND_HELMET, ArmorSlot.HELMET, 0.025),
	DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, ArmorSlot.CHESTPLATE, 0.025),
	DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, ArmorSlot.LEGGINGS, 0.025),
	DIAMOND_BOOTS(Material.DIAMOND_BOOTS, ArmorSlot.BOOTS, 0.025);
	
	private Material material;
	private ArmorSlot slot;
	private double weight;
	Armor(Material mat, ArmorSlot slot, double weight) {
		this.material = mat;
		this.slot = slot;
		this.weight = weight;
	}
	
	public Material getType() {
		return this.material;
	}
	
	public ArmorSlot getSlot() {
		return this.slot;
	}
	
	public double getWeight() {
	    return weight;
	}
}
