package net.senmori.hunted.lib;

import org.bukkit.Material;



public class KitSelector 
{
	// Armor slots (in inventory)
	private static int helmetSlot = 11;
	private static int chestplateSlot = helmetSlot + 9;
	private static int leggingSlot = chestplateSlot + 9;
	private static int bootSlot = leggingSlot + 9;
	
	// Weapon slots (in inventory)
	private static int bowSlot = leggingSlot -1;
	private static int swordSlot = leggingSlot + 1;
	
	// Potion Slots (in inventory)
	private static int[] potionSlots = {helmetSlot + 3,		helmetSlot + 4,		helmetSlot + 5,
										chestplateSlot + 3, chestplateSlot + 4, chestplateSlot + 5,
										leggingSlot + 3, 	leggingSlot + 4, 	leggingSlot + 5,
										bootSlot + 3, 		bootSlot + 4, 		bootSlot + 5};

	// Enchantment option highest slot option (in inventory)
	private static int enchantHighestSlot = 8;
	
	
	/*
	 * Opens the default Kit selection/upgrade chest
	 */
	public static void openSelectorChest()
	{
		
	}	
}
