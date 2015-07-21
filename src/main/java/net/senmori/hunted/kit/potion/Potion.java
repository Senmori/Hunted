package net.senmori.hunted.kit.potion;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Potion
{
	/**
	 * How to classify PotionEffects <br>
	 * {@link #BENEFICIAL} :<br>
	 * - Beneficial effects effect how much damage you apply or receive(potentially) in a <i>positive</i> manner <br>
	 * {@link #HARMFUL} :<br>
	 * - Harmful effects effect how much damage you apply for receive(potentially) in a <i>negative</i> manner <br>
	 * {@link #OTHER} :<br>
	 * - Other effects are effects that do not meet the criteria for the first two classifcations <br>
	 *
	 */
	public enum PotionEffectEnum
	{
		SPEED(PotionEffectType.SPEED, Potion.Category.OTHER, PotionType.SPEED, false, 120),
		SLOW(PotionEffectType.SLOW, Potion.Category.OTHER, PotionType.SLOWNESS, true, 30),
		INCREASE_DAMAGE(PotionEffectType.INCREASE_DAMAGE, Potion.Category.BENEFICIAL, PotionType.STRENGTH, false, 30),
		HEAL(PotionEffectType.HEAL, Potion.Category.BENEFICIAL, PotionType.INSTANT_HEAL, true, 0),
		HARM(PotionEffectType.HARM, Potion.Category.HARMFUL, PotionType.INSTANT_DAMAGE, true, 0),
		JUMP(PotionEffectType.JUMP, Potion.Category.OTHER, PotionType.JUMP, false, 30),
		CONFUSION(PotionEffectType.CONFUSION, Potion.Category.HARMFUL, null, false, 10),
		REGENERATION(PotionEffectType.REGENERATION, Potion.Category.BENEFICIAL, PotionType.REGEN, false, 15),
		DAMAGE_RESISTANCE(PotionEffectType.DAMAGE_RESISTANCE, Potion.Category.BENEFICIAL,null, false, 20),
		FIRE_RESISTANCE(PotionEffectType.FIRE_RESISTANCE, Potion.Category.BENEFICIAL, PotionType.FIRE_RESISTANCE, false, 90),
		WATER_BREATHING(PotionEffectType.JUMP, Potion.Category.OTHER, PotionType.WATER_BREATHING, false, 60),
		INVISIBILITY(PotionEffectType.INVISIBILITY, Potion.Category.BENEFICIAL, PotionType.INVISIBILITY, false, 45),
		BLINDNESS(PotionEffectType.BLINDNESS, Potion.Category.HARMFUL, null, false, 15),
		NIGHT_VISION(PotionEffectType.NIGHT_VISION, Potion.Category.OTHER, PotionType.NIGHT_VISION, false, 60),
		HUNGER(PotionEffectType.HUNGER, Potion.Category.HARMFUL, null, true, 30),
		WEAKNESS(PotionEffectType.WEAKNESS, Potion.Category.HARMFUL, PotionType.WEAKNESS, true, 25),
		POISON(PotionEffectType.POISON, Potion.Category.HARMFUL, PotionType.POISON, true, 30),
		HEALTH_BOOST(PotionEffectType.HEALTH_BOOST, Potion.Category.BENEFICIAL, null, false, 0),
		ABSORPTION(PotionEffectType.ABSORPTION, Potion.Category.BENEFICIAL, null, false, 6),
		SATURATION(PotionEffectType.SATURATION, Potion.Category.OTHER, null, false, 10);
		
		private PotionEffectType pet;
		private Category Category;
		private PotionType type;
		private boolean onlySplash;
		private int maxLength;
		PotionEffectEnum(PotionEffectType pet, Category Category, PotionType type, boolean onlySplash, int maxLength)
		{
			this.pet = pet;
			this.Category = Category;
			this.onlySplash = onlySplash;
			this.maxLength = maxLength;
		}
		
		public PotionEffectType getEffectType()
		{
			return pet;
		}
		
		public Category getCategory()
		{
			return Category;
		}
		
		public PotionType getType()
		{
			return type;
		}
		
		public boolean canOnlyBeSplash()
		{
			return onlySplash;
		}
		
		public int getMaxLength()
		{
			return maxLength;
		}
	}
	
	/**
	 * Category is used to categorize {@link PotionEffect} <br>
	 * {@link #BENEFICIAL} :<br>
	 * - makes the player have an advantage over other players(not including health or hunger) <br>
	 * {@link #HARMFUL} :<br>
	 * - makes the player have a disadvantage against another player(not including health or hunger) <br>
	 * {@link OTHER} :<br>
	 * - anything that doesn't meet the first two criteria
	 *
	 */
	public enum Category
	{
		BENEFICIAL,
		HARMFUL,
		OTHER;
	}
}

