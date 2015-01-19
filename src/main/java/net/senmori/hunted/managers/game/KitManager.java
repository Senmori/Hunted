package net.senmori.hunted.managers.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.Reference;
import net.senmori.hunted.util.Reference.LootPath;
import net.senmori.hunted.util.Reference.Message;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class KitManager 
{
	private List<Material> armor;
	private List<Material> weapons;
	private List<PotionType> potions;
	
	public KitManager()
	{
		armor = new ArrayList<Material>();
		weapons = new ArrayList<Material>();
		potions = new ArrayList<PotionType>();
		
		fillLists();
	}
	
	/**
	 * Generate a random kit for the given player
	 * Select up to 3 pieces of armor, not similar
	 * Select 1 bow and/or sword
	 * - if bow, select up to {@value Hunted#maxArrowsPerReward} arrows
	 * If potions can be received as a reward(or upon spawn into Hunted)
	 * - select up to {@value Hunted#maxPotsPerReward} potions
	 * @param player
	 */
	public void generateKitFor(Player player)
	{
		fillArmor(player);
		fillWeapons(player);
		fillPotions(player);
	}
	
	
	private void fillArmor(Player player)
	{
		List<Material> armorPieces = new ArrayList<Material>();
		Random rand = new Random();
		
		for(int i = 0; i < rand.nextInt(4); i++)
		{
			Material randomPiece;
			do 
			{
				randomPiece = armor.get(rand.nextInt(armor.size()+1));
			} 
			while(!armorPieces.contains(randomPiece) && randomPiece != null);
			armor.add(randomPiece);
		}
		
		ItemStack[] arm = {null,null,null,null};
		for(int i = 0; i < arm.length; i++)
		{
			for(Material m : armorPieces)
			{
				arm[i] = new ItemStack(m);
			}
		}
		
		player.getInventory().setArmorContents(arm);
	}
	
	private void fillWeapons(Player player)
	{
		List<Material> wep = new ArrayList<Material>();
		Random rand = new Random();
		for(int i = 0; i < rand.nextInt(3); i++)
		{
			Material rMat = weapons.get(rand.nextInt(weapons.size()+1));
			do
			{
				rMat = weapons.get(rand.nextInt(weapons.size()+1));
			}while(wep.contains(rMat));
			wep.add(rMat);
		}
		
		for(Material mat : wep)
		{
			player.getInventory().addItem(new ItemStack(mat));
		}
	}
	
	private void fillPotions(Player player)
	{
		Random rand = new Random();
		for(int i = 0; i < rand.nextInt(Hunted.maxPotsPerReward+1); i++)
		{
			player.getInventory().addItem(new Potion(potions.get(rand.nextInt(potions.size()+1)), rand.nextInt(Hunted.potionTierChance+1) % Hunted.potionTierChance == 0 ? 1 : 2).toItemStack(1));
		}
	}
	
	/*
	 * Fill all lists
	 */
	private void fillLists()
	{
		// fill armor
		for(String material : Hunted.lootConfig.getStringList(LootPath.ARMOR))
		{
			Material mat = Material.valueOf(material);
			Validate.notNull(mat, String.format(Message.IMPORT_ERROR, mat.toString().toLowerCase()));
			if(mat != null && !armor.contains(mat))
			{
				armor.add(mat);
			}
		}
		
		// fill weapons
		for(String material : Hunted.lootConfig.getStringList(LootPath.WEAPON))
		{
			Material mat = Material.valueOf(material);
			Validate.notNull(mat, String.format(Message.IMPORT_ERROR, mat.toString().toLowerCase()));
			if(mat != null && !weapons.contains(mat))
			{
				weapons.add(mat);
			}
		}
		
		// fill potions
		for(String type : Hunted.lootConfig.getStringList(LootPath.POTION))
		{
			PotionType pt = PotionType.valueOf(type);
			Validate.notNull(pt, String.format(Message.IMPORT_ERROR, pt.toString().toLowerCase()));
			if(pt != null && !potions.contains(pt))
			{
				potions.add(pt);
			}
		}
	}
}
