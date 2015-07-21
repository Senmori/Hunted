package net.senmori.hunted.kit.enums;

import org.bukkit.Material;

public class Item
{
	/**
	 * ItemRewards are what a player can receive that are either food, perform a special function or something else entirely <BR>
	 * {@link #type} - what type of material this item is created from <br>
	 * {@link #category} - what category this item reward fits into <br>
	 * {@link #maxReceivable} - how many of this item can be received at one time <br>
	 *
	 */
	public enum ItemReward
	{
		APPLE(Material.APPLE, Category.FOOD, Material.APPLE.getMaxStackSize()),
		BREAD(Material.BREAD, Category.FOOD, Material.BREAD.getMaxStackSize()),
		CAKE(Material.CAKE, Category.FOOD, Material.CAKE.getMaxStackSize()),
		COOKED_CHICKEN(Material.COOKED_CHICKEN, Category.FOOD, Material.COOKED_CHICKEN.getMaxStackSize()),
		COOKED_FISH(Material.COOKED_FISH, Category.FOOD, Material.COOKED_FISH.getMaxStackSize()),
		COOKED_MUTTON(Material.COOKED_MUTTON, Category.FOOD, Material.COOKED_MUTTON.getMaxStackSize()),
		COOKED_PORKCHOP(Material.GRILLED_PORK, Category.FOOD, Material.GRILLED_PORK.getMaxStackSize()),
		COOKED_RABBIT(Material.COOKED_RABBIT, Category.FOOD, Material.COOKED_RABBIT.getMaxStackSize()),
		GOLDEN_APPLE(Material.GOLDEN_APPLE, Category.FOOD, Material.GOLDEN_APPLE.getMaxStackSize()),
		GOLDEN_CARROT(Material.GOLDEN_CARROT, Category.FOOD, Material.GOLDEN_CARROT.getMaxStackSize()),
		MUSHROOM_STEW(Material.MUSHROOM_SOUP, Category.FOOD, 6),
		PUMPKIN_PIE(Material.PUMPKIN_PIE, Category.FOOD, 3),
		RABBIT_STEW(Material.RABBIT_STEW, Category.FOOD, 2),
		ROTTEN_FLESH(Material.ROTTEN_FLESH, Category.FOOD, Material.ROTTEN_FLESH.getMaxStackSize()),
		STEAK(Material.COOKED_BEEF, Category.FOOD, Material.COOKED_BEEF.getMaxStackSize()),
		BAKED_POTATO(Material.BAKED_POTATO, Category.FOOD, Material.BAKED_POTATO.getMaxStackSize()),
		WARP_STAR(Material.NETHER_STAR, Category.UTILITY, 1),
		MAP(Material.MAP, Category.UTILITY, 1),
		FIREWORK(Material.FIREWORK, Category.UTILITY, 1),
		COMPASS(Material.COMPASS, Category.UTILITY, 1),
		ENDERPEARL(Material.ENDER_PEARL, Category.OTHER, Material.ENDER_PEARL.getMaxStackSize());
		
		/** What material this item is made out of */
		private Material type;
		/** What category this item fits into */
		private Category category;
		/** How many of this item can be received at once */
		private int maxReceivable;
		ItemReward(Material material, Category category, int maxReceivable)
		{
			this.type = material;
			this.category = category;
			this.maxReceivable = maxReceivable;
		}
		
		/** Get the {@link Material} of  this ItemReward */
		public Material getType()
		{
			return this.type;
		}
		
		/** Get what {@link Category} this item fits into */
		public Category getCategory()
		{
			return this.category;
		}
		
		/** Get how many of this item can be received at once */
		public int getMaxAmountReceivable()
		{
			return this.maxReceivable;
		}
	}
	
	
	/**
	 * Categorize {@link ItemReward} by what the item does <br>
	 * {@link #FOOD} - food item <br>
	 * {@link UTILITY} - performs a function other than what it normally does(compass, etc.) <br>
	 * {@link OTHER} - does not match criteria for first two categories <br>
	 */
	public enum Category
	{
		FOOD,
		UTILITY,
		OTHER;
	}
}
