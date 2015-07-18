package net.senmori.hunted.weapon;

import org.bukkit.Material;

public enum WeaponType
{
	WOOD_SWORD(Material.WOOD_SWORD),
	STONE_SWORD(Material.STONE_SWORD),
	GOLD_SWORD(Material.GOLD_SWORD),
	IRON_SWORD(Material.IRON_SWORD),
	BOW(Material.BOW);
	//STONE_AXE(Material.STONE_AXE),
	//GOLD_AXE(Material.GOLD_AXE),
	//IRON_AXE(Material.IRON_AXE),
	//WOOD_SHOVEL(Material.WOOD_SPADE),
	//STONE_SHOVEL(Material.STONE_SPADE),
	//GOLD_SHOVEL(Material.GOLD_SPADE),
	//IRON_SHOVEL(Material.IRON_SPADE);
	
	private Material material;
	
	WeaponType(Material material) {
		this.material = material;
	}
	
	public Material getType() {
		return this.material;
	}
	
	public boolean isSword()
	{
		switch(this)
		{
			case WOOD_SWORD:
			case STONE_SWORD:
			case GOLD_SWORD:
			case IRON_SWORD:
				return true;
			default:
				return false;
		}
	}
	
	/*
	public boolean isSpade()
	{
		switch(this)
		{
			case WOOD_SHOVEL:
			case STONE_SHOVEL:
			case GOLD_SHOVEL:
			case IRON_SHOVEL:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isAxe()
	{
		switch(this)
		{
			case STONE_AXE:
			case GOLD_AXE:
			case IRON_AXE:
				return true;
			default:
				return false;
		}
	}
	*/
}
