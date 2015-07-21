package net.senmori.hunted.kit.weapon;

import org.bukkit.Material;

public enum WeaponType
{
	WOOD_SWORD(Material.WOOD_SWORD, false),
	STONE_SWORD(Material.STONE_SWORD, false),
	GOLD_SWORD(Material.GOLD_SWORD, true),
	IRON_SWORD(Material.IRON_SWORD, true),
	DIAMOND_SWORD(Material.DIAMOND_SWORD,true),
	BOW(Material.BOW, true),
	STONE_AXE(Material.STONE_AXE, false),
	GOLD_AXE(Material.GOLD_AXE, true),
	IRON_AXE(Material.IRON_AXE, false),
	WOOD_SHOVEL(Material.WOOD_SPADE, false),
	STONE_SHOVEL(Material.STONE_SPADE, false),
	GOLD_SHOVEL(Material.GOLD_SPADE, true),
	IRON_SHOVEL(Material.IRON_SPADE, false);
	
	private Material material;
	private boolean canBeAscented;
	
	WeaponType(Material material, boolean canBeAscented) {
		this.material = material;
		this.canBeAscented = canBeAscented;
	}
	
	public Material getType() 
	{
		return this.material;
	}
	
	public boolean canBeAscented() 
	{
		return canBeAscented;
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
	
	public boolean isBow()
	{
		switch(this)
		{
			case BOW:
				return true;
			default:
				return false;
		}
	}
	

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
}
