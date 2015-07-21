package net.senmori.hunted.kit.potion;

import net.senmori.hunted.kit.enums.Potion.Category;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

public class HuntedPotion extends AbstractPotion
{
	public HuntedPotion(PotionType color, PotionEffect type, Category category)
    {
	    super(color, type, category);
    }

}
