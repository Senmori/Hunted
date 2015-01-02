package net.senmori.hunted.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_8_R1.PossibleFishingResult;
import net.senmori.hunted.Hunted;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;


public class Loadout 
{
	// Who this loadout is for
	private PlayerFile player;
		
	// Armor content
	private Material helmet = null;
	private Material chestplate = null;
	private Material leggings = null;
	private Material boots = null;
	
	// Weapon(s)
	private Material sword = null;
	private Material bow = null;
	
	// Potion loadout
	private List<PotionType> potions;
	
	private Random rand;
	
	/*
	 * All positions are the same in regards to material
	 * 0 = leather
	 * 1 = iron
	 * 2 = gold
	 * 3 = chainmail
	 */	
		// List of unlocked armor
	private HashMap<Boolean,List<Material>> possibleArmor;
		// List of unlocked weapons
	private HashMap<Boolean,Material> possibleWeapons;
		// List of unlocked potions
	private HashMap<Boolean,PotionType> possiblePotions;
	private List<Material> possibleItems;
	
	
	// Static lists & vars
	private static int maxBitPositionsArmor = 4;
	private static int maxBitPositionsPotions = 12;
		
	/*
	 * Master list of possible kit options
	 */
		// Master list of all possible armor
	private static List<List<Material>>	masterArmorList;
	private static List<Material> masterLeatherArmor;
	private static List<Material> masterIronArmor;
	private static List<Material> masterGoldArmor;
	private static List<Material> masterChainArmor;
		// Master list of all possible weapons
	private static List<Material> masterWeaponlist;
		// Master list of all possible potions
	private static List<PotionType> masterPotionList;
	
	
	static
	{
		masterArmorList = new ArrayList<List<Material>>();
		masterLeatherArmor = new ArrayList<Material>();
		masterIronArmor = new ArrayList<Material>();
		masterGoldArmor = new ArrayList<Material>();
		masterChainArmor = new ArrayList<Material>();
		masterWeaponlist = new ArrayList<Material>();
		masterPotionList = new ArrayList<PotionType>();
		
		// Fill master lists
		for(String node : Hunted.lootConfig.getConfigurationSection("loot.item.armor").getKeys(false))
		{
			// get material types for each armor
			for(String mat : Hunted.lootConfig.getStringList("loot.item.armor." + node))
			{
				// combine type (mat) with the piece of armor (node)
				// s = "leather" + "_" + "helmet"
				String s = mat + "_" + node;
				switch(node)
				{
					case "leather":
							masterLeatherArmor.add(Material.valueOf(s));
							break;
					case "iron":
							masterIronArmor.add(Material.valueOf(s));
							break;
					case "gold":
							masterGoldArmor.add(Material.valueOf(s));
							break;
					case "chainmail":
							masterChainArmor.add(Material.valueOf(s));
							break;
				}
			}
		} 
		// load master armor list
			masterArmorList.add(masterLeatherArmor);
			masterArmorList.add(masterIronArmor);
			masterArmorList.add(masterGoldArmor);
			masterArmorList.add(masterChainArmor);
		// end armor loading
			for(String s : Hunted.lootConfig.getStringList("loot.item.weapon"))
			{
				masterWeaponlist.add(Material.valueOf(s));
			}
		// end weapon loading
			for(String type : Hunted.lootConfig.getStringList("loot.potion"))
			{
				masterPotionList.add(PotionType.valueOf(type));
			}
		// end potion loading
	}
	
	
	
	public Loadout(PlayerFile player)
	{
		potions = new ArrayList<PotionType>();
		possibleArmor = new HashMap<Boolean,List<Material>>();
		possibleWeapons = new HashMap<Boolean,Material>();
		possiblePotions = new HashMap<Boolean,PotionType>();
		possibleItems = new ArrayList<Material>();
		
		rand = new Random();
		
		// load player specific variables here
			// load available armors
		int unlockedArmor = player.getPlayerConfig().getInt("unlocked.armor");
		int unlockedWeapon = player.getPlayerConfig().getInt("unlocked.weapon");
		int unlockedPotion = player.getPlayerConfig().getInt("unlocked.potion");
		
		// load unlocked armor & weapons
		for(int i = 0; i < maxBitPositionsArmor; i++)
		{
				// null checks
			possibleArmor.put(isBit(unlockedArmor, i), masterArmorList.get(i) != null ? masterArmorList.get(i) : null);
			possibleWeapons.put(isBit(unlockedWeapon, i), masterWeaponlist.get(i) != null ? masterWeaponlist.get(i) : null);
		}
		
		// load unlocked potions
		for(int x = 0; x < maxBitPositionsPotions; x++)
		{
				// more null checks
			possiblePotions.put(isBit(unlockedPotion,x), masterPotionList.get(x) != null ? masterPotionList.get(x) : null);
		}
		
		// load possible items
		List<String> itemTypes = Hunted.lootConfig.getStringList("loot.item.item");
		for(String type : itemTypes)
		{
			possibleItems.add(Material.valueOf(type) != null ? Material.valueOf(type) : null);
		}
	}
	
	public Loadout getLoadout()
	{
		return this;
	}
	
	public HashMap<Boolean,List<Material>> getUnlockedArmor()
	{
		return possibleArmor;
	}
	
	public HashMap<Boolean,Material> getUnlockedWeapons()
	{
		return possibleWeapons;
	}
	
	public List<Material> getPossibleItems()
	{
		return possibleItems;
	}
	
	public HashMap<Boolean,PotionType> getUnlockedPotions()
	{
		return possiblePotions;
	}
	
	// Bitshift function, for stuff
	// shift 'n', 'k' positions
	public static boolean isBit(int n, int k)
	{
		return ((n >> k) & 1) == 1;
	}
}
