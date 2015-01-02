package net.senmori.hunted.reward;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.senmori.hunted.Hunted;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ItemReward extends Reward 
{
	// the possible materials to choose from (leather, iron, chain...)
	private List<String> materials;
	
	private Set<String> weaponLoot; // stone, iron, gold sword
	private Set<String> itemLoot;   // arrow, anything miscellaneous
	private Set<String> armorLoot;  // helmet, chest, legs, boots
	
	private HashMap<String,Material> armorCache;
	
	private String name;
	public ItemReward(String name) 
	{
		this.name = name;
		Hunted.rewardManager.addReward(this);
		
		materials = Hunted.lootConfig.getStringList("loot.item.armor.material");
		
		armorLoot = Hunted.lootConfig.getConfigurationSection("loot.item.armor").getKeys(false);
		weaponLoot = Hunted.lootConfig.getConfigurationSection("loot.item.weapon").getKeys(false);
	};
	
	@Override
	public void getLoot(Player player) 
	{
		
	}

	
	@Override
	public String getName()
	{
		return name;
	}
}
