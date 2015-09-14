package net.senmori.hunted.kit.item;

import org.bukkit.Material;

public enum KitItem {
	/**
	 * ItemRewards are what a player can receive that are either food, perform a special function or something else entirely <BR>
	 * {@link #type} - what type of material this item is created from <br>
	 * {@link #category} - what category this item reward fits into <br>
	 * {@link #maxReceivable} - how many of this item can be received at one time <br>
	 *
	 */
		APPLE(Material.APPLE, Category.FOOD, 16),
		BREAD(Material.BREAD, Category.FOOD, 16),
		CAKE(Material.CAKE, Category.FOOD, 1),
		COOKED_CHICKEN(Material.COOKED_CHICKEN, Category.FOOD, 16),
		COOKED_FISH(Material.COOKED_FISH, Category.FOOD, 16),
		COOKED_MUTTON(Material.COOKED_MUTTON, Category.FOOD, 16),
		COOKED_PORKCHOP(Material.GRILLED_PORK, Category.FOOD, 16),
		COOKED_RABBIT(Material.COOKED_RABBIT, Category.FOOD, 16),
		GOLDEN_APPLE(Material.GOLDEN_APPLE, Category.FOOD, 2),
		GOLDEN_CARROT(Material.GOLDEN_CARROT, Category.FOOD, 24),
		MUSHROOM_STEW(Material.MUSHROOM_SOUP, Category.FOOD, 4),
		PUMPKIN_PIE(Material.PUMPKIN_PIE, Category.FOOD, 3),
		RABBIT_STEW(Material.RABBIT_STEW, Category.FOOD, 2),
		ROTTEN_FLESH(Material.ROTTEN_FLESH, Category.FOOD, 16),
		STEAK(Material.COOKED_BEEF, Category.FOOD, 16),
		BAKED_POTATO(Material.BAKED_POTATO, Category.FOOD, 24),
		WARP_STAR(Material.NETHER_STAR, Category.UTILITY, 1),
		MAP(Material.MAP, Category.UTILITY, 1),
		FIREWORK(Material.FIREWORK, Category.UTILITY, 1),
		COMPASS(Material.COMPASS, Category.UTILITY, 1),
		ENDERPEARL(Material.ENDER_PEARL, Category.OTHER, 6);
		
		/** What material this item is made out of */
		private Material type;
		/** What category this item fits into */
		private Category category;
		/** How many of this item can be received at once */
		private int maxReceivable;
		
		KitItem(Material material, Category category, int maxReceivable) {
			this.type = material;
			this.category = category;
			this.maxReceivable = maxReceivable;
		}
		
		/** Get the {@link Material} of  this ItemReward */
		public Material getType() {
			return this.type;
		}
		
		/** Get what {@link Category} this item fits into */
		public Category getCategory() {
			return this.category;
		}
		
		/** Get how many of this item can be received at once */
		public int getMaxAmountReceivable() {
			return this.maxReceivable;
		}
	
	
	/**
	 * Categorize {@link ItemReward} by what the item does <br>
	 * {@link #FOOD} - food item <br>
	 * {@link UTILITY} - performs a function other than what it normally does(compass, etc.) <br>
	 * {@link OTHER} - does not match criteria for first two categories <br>
	 */
	public enum Category {
		FOOD,
		UTILITY,
		OTHER;
	}
}
