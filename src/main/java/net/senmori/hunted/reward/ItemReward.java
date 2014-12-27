package net.senmori.hunted.reward;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ItemReward extends Reward 
{
	private enum Type
	{
		/*
		 * Armor rewards
		 */
		LEATHER_CHESTPLATE(Material.LEATHER_CHESTPLATE),
		LEATHER_LEGGINGS(Material.LEATHER_LEGGINGS),
		LEATHER_HELMET(Material.LEATHER_HELMET),
		LEATHER_BOOTS(Material.LEATHER_BOOTS),
		IRON_CHESTPLATE(Material.IRON_CHESTPLATE),
		IRON_LEGGINGS(Material.IRON_LEGGINGS),
		IRON_HELMET(Material.IRON_HELMET),
		IRON_BOOTS(Material.IRON_BOOTS),
		CHAIN_CHESTPLATE(Material.CHAINMAIL_CHESTPLATE),
		CHAIN_LEGGINGS(Material.CHAINMAIL_LEGGINGS),
		CHAIN_HELMET(Material.CHAINMAIL_HELMET),
		CHAIN_BOOTS(Material.CHAINMAIL_BOOTS),
		
		/*
		 * Weapon rewards
		 */
		STONE_SWORD(Material.STONE_SWORD),
		IRON_SWORD(Material.IRON_SWORD),
		GOLD_SWORD(Material.GOLD_SWORD),
		BOW(Material.BOW),
		
		/*
		 * Misc. items
		 */
		ARROW(Material.ARROW);
		
		private Material mat;
		Type(Material material)
		{
			this.mat = material;
		}
		
		public Material getMaterial()
		{
			return mat;
		}
	}
	
	@Override
	public void pickLoot(Player player) 
	{
		
	}

}
