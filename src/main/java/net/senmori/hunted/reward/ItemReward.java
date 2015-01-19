package net.senmori.hunted.reward;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.Reference.Message;
import net.senmori.hunted.util.Reference.RewardMessage;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemReward extends Reward 
{
	private List<Material> loot;
	
	private String name;
	public ItemReward(String name) 
	{
		this.name = name;
		Hunted.rewardManager.addReward(this);
		loot = new ArrayList<Material>();
		
		// load armor
		for(String mat : Hunted.lootConfig.getStringList("loot.item.armor"))
		{
			for(String piece : Hunted.lootConfig.getStringList("loot.item.armor"))
			{
				Material material = Material.valueOf(mat + "_" + piece);
				Validate.notNull(material, String.format(Message.IMPORT_ERROR, material.toString().toLowerCase()));
				if(material != null)
				{
					loot.add(material);
				}
			}
		}
		
		// load weapons
		for(String type : Hunted.lootConfig.getStringList("loot.item.weapon"))
		{
			Material material = Material.valueOf(type);
			Validate.notNull(material, String.format(Message.IMPORT_ERROR, material.toString().toLowerCase()));
			if(material != null)
			{
				loot.add(material);
			}
		}
		
		// load misc. items
		for(String item : Hunted.lootConfig.getStringList("loot.item.item"))
		{
			Material material = Material.valueOf(item);
			Validate.notNull(material, String.format(Message.IMPORT_ERROR, material.toString().toLowerCase()));
			if(material != null)
			{
				loot.add(material);
			}
		}
		
	};
	
	@Override
	public void getLoot(Player player) 
	{
		Random rand = new Random();
		Material mat = loot.get(rand.nextInt(loot.size()));
		ItemStack is = new ItemStack(mat);
		
		// get enchantment for item if applicable
		if(is.getEnchantments().size() > 0 && (rand.nextInt(Hunted.enchantChance+1) % Hunted.enchantChance == 0))
		{
			Enchantment e = null;
			for(Enchantment enchant : is.getEnchantments().keySet())
			{
				// if random number % chance == 0, apply enchant
				if(rand.nextInt(Hunted.enchantChance+1) % Hunted.enchantChance == 0)
				{
					e = enchant;
				}
			}
			is.addEnchantment(e, rand.nextInt(Hunted.enchantChance+1) % Hunted.enchantChance == 0 ? 1 : 2);
		}
		player.getInventory().addItem(is);
		player.sendMessage(ChatColor.GOLD + String.format(RewardMessage.STONE_REWARD, is.getItemMeta().getDisplayName()));
	}

	
	@Override
	public String getName()
	{
		return name;
	}
}
