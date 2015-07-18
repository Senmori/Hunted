package net.senmori.hunted.armor;

import org.bukkit.Material;

public enum ArmorEnum
{
	LEATHER_HELMET(Material.LEATHER_HELMET),
	LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE),
	LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS),
	LEATHER_BOOTS(Material.LEATHER_BOOTS),
	GOLD_HELMET(Material.GOLD_HELMET),
	GOLD_CHESTPLATE(Material.GOLD_CHESTPLATE),
	GOLD_LEGGINGS(Material.GOLD_LEGGINGS),
	GOLD_BOOTS(Material.GOLD_BOOTS),
	IRON_HELMET(Material.IRON_HELMET),
	IRON_CHESTPLATE(Material.IRON_CHESTPLATE),
	IRON_LEGGINGS(Material.IRON_LEGGINGS),
	IRON_BOOTS(Material.IRON_BOOTS),
	DIAMOND_BOOTS(Material.DIAMOND_BOOTS);
	
	private Material material;
	ArmorEnum(Material mat)
	{
		this.material = mat;
	}
	
	public Material getType()
	{
		return this.material;
	}
}
