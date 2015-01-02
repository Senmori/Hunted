package net.senmori.hunted.lib.item;

import java.util.List;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.PlayerFile;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LoadoutItem extends AbstractItem
{
	/* Loadout Items are the items used in the kit upgrade/unlock system */
	private String playerUUID;
	
	private String type;
	public LoadoutItem(String playerUUID, String type)
	{
		this.playerUUID = playerUUID;
		this.type = type;
	}
	
	@Override
	public String getType() 
	{
		return type;
	}

	@Override
	public int getUpgradeCost() 
	{
		return 0;
	}

	@Override
	public ItemStack toItemStack() 
	{
		return null;
	}

	@Override
	public void updatePlayerConfig() 
	{
		
	}

	@Override
	public int getInventorySlot() 
	{
		return 0;
	}

	@Override
	public int getSlotCost() 
	{
		return 0;
	}

	@Override
	public PlayerFile getPlayerFile() 
	{
		return Hunted.playerManager.getPlayerFile(playerUUID);
	}

	@Override
	public void onLeftClick() 
	{
		
	}

	@Override
	public void onRightClick() 
	{
		
	}

	@Override
	public void onHover() 
	{
		
	}

	@Override
	public String getCustomName() 
	{
		return null;
	}

	@Override
	public int getData() 
	{
		return 0;
	}

	@Override
	public List<Enchantment> getPossibleEnchantments() 
	{
		return null;
	}
	
}
