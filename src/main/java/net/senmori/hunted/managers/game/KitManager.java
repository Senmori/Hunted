package net.senmori.hunted.managers.game;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.armor.ArmorEnum;
import net.senmori.hunted.weapon.WeaponType;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitManager 
{
	private List<Material> items;
	
	public KitManager()
	{
		items = new ArrayList<Material>();
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
	public void generateKit(Player player)
	{
		generateArmor(player);
		generateWeapons(player);
		generatePotions(player);
	}
	
	public void generateArmor(Player player)
	{
		List<Material> armorPieces = new ArrayList<Material>();
		Random rand = new Random();
		
		for(int i = 0; i < rand.nextInt(4); i++)
		{
			Material randomPiece;
			do
			{
				int slot = rand.nextInt(ArmorEnum.values().length -1) + 1;
				randomPiece = ArmorEnum.values()[slot].getType();
				// if the list already contains a similar piece of armor, choose another

				//randomPiece = armor.get(rand.nextInt(armor.size()+1));
			}
			while(!armorPieces.contains(randomPiece) && randomPiece != null);
			armorPieces.add(randomPiece);
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
	
	public void generateWeapons(Player player)
	{
		Material weapon = null;
		
		weapon = WeaponType.values()[(int)Math.random()*(WeaponType.values().length - 1) +1].getType();
		//weapon = Hunted.getKitManager().weapons.get((int)(Math.random()*(WeaponType.values().length -1) +1));
		
		if(weapon.equals(Material.BOW))
		{
			player.getInventory().addItem(new ItemStack(Material.ARROW, (int) (Math.random() * (Hunted.maxArrowsPerReward -1) +1)));
		}
		player.getInventory().addItem(new ItemStack(weapon));
	}
	
	/*
	 * Generate potion(s) for a player
	 */
	public void generatePotions(Player player)
	{
		
	}
	
	/*
	 * Fill all lists
	 */
	private void fillLists()
	{
		
	}
	
	public List<Material> getItems()
	{
		return items;
	}
}
