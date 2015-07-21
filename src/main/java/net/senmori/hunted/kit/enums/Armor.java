package net.senmori.hunted.kit.enums;

import org.bukkit.Material;

/**
 * What armor type can be rewarded or generated in kits <br>
 * {@link material} - the type of material this armor is made from <br>
 * {@link slot} - the {@link ArmorSlot} this armor fits into <br>
 */
public enum Armor
{
	LEATHER_HELMET(Material.LEATHER_HELMET, ArmorSlot.HELMET),
	LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE, ArmorSlot.CHESTPLATE),
	LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS, ArmorSlot.LEGGINGS),
	LEATHER_BOOTS(Material.LEATHER_BOOTS, ArmorSlot.BOOTS),
	GOLD_HELMET(Material.GOLD_HELMET, ArmorSlot.HELMET),
	GOLD_CHESTPLATE(Material.GOLD_CHESTPLATE, ArmorSlot.CHESTPLATE),
	GOLD_LEGGINGS(Material.GOLD_LEGGINGS, ArmorSlot.LEGGINGS),
	GOLD_BOOTS(Material.GOLD_BOOTS, ArmorSlot.BOOTS),
	IRON_HELMET(Material.IRON_HELMET, ArmorSlot.HELMET),
	IRON_CHESTPLATE(Material.IRON_CHESTPLATE, ArmorSlot.CHESTPLATE),
	IRON_LEGGINGS(Material.IRON_LEGGINGS, ArmorSlot.LEGGINGS),
	IRON_BOOTS(Material.IRON_BOOTS, ArmorSlot.BOOTS),
	DIAMOND_HELMET(Material.DIAMOND_HELMET, ArmorSlot.HELMET),
	DIAMOND_CHESTPLATE(Material.DIAMOND_CHESTPLATE, ArmorSlot.CHESTPLATE),
	DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, ArmorSlot.LEGGINGS),
	DIAMOND_BOOTS(Material.DIAMOND_BOOTS, ArmorSlot.BOOTS);
	
	private Material material;
	private ArmorSlot slot;
	Armor(Material mat, ArmorSlot slot)
	{
		this.material = mat;
		this.slot = slot;
	}
	
	public Material getType()
	{
		return this.material;
	}
	
	public ArmorSlot getSlot()
	{
		return this.slot;
	}
	
	
	/**
	 * What armor slot this armor goes to
	 */
	public enum ArmorSlot
	{
		HELMET,
		CHESTPLATE,
		LEGGINGS,
		BOOTS;
	}
}
