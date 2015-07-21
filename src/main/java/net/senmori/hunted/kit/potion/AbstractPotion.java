package net.senmori.hunted.kit.potion;

import net.senmori.hunted.kit.enums.Potion.Category;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

public class AbstractPotion
{

	private PotionType color;
	private PotionEffect effect;
	private Category category;
	private boolean isSplash = false;
	private String displayName;
	
	
	public AbstractPotion(PotionType color, PotionEffect effect, Category category)
	{
		this.color = color;
		this.effect = effect;
		this.category = category;
	}
	
	/** Set the display name of the {@link ItemStack} */
	public void setDisplayName(String name)
	{
		this.displayName = name;
	}
	
	public String getDisplayName()
	{
		return displayName;
	}
	
	public void setEffect(PotionEffect effect)
	{
		this.effect = effect;
	}
	
	/**
	 * Set whether this potion should be a splash potion or not
	 * @param isSplash
	 */
	public void setSplash(boolean isSplash)
    {
	    this.isSplash = isSplash;
    }
	
	/** Make this a splash potion */
	public boolean isSplash()
	{
		return isSplash;
	}
	
	public ItemStack toItemStack()
	{
		ItemStack pt = new Potion(color).toItemStack(1);
		PotionMeta pm = (PotionMeta)pt.getItemMeta();
		
		pm.addCustomEffect(effect, true);
		pt.setItemMeta(pm);
		return pt;
	}
	
	public Category getCategory()
	{
		return category;
	}
}
