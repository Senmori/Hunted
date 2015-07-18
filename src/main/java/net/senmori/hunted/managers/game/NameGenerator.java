package net.senmori.hunted.managers.game;

import net.senmori.hunted.weapon.WeaponType;
import net.senmori.hunted.weapon.bow.BowEnum.Bow;
import net.senmori.hunted.weapon.bow.BowEnum.BowTitle;
import net.senmori.hunted.weapon.sword.SwordEnum.SwordTitle;
import net.senmori.hunted.weapon.sword.SwordEnum.Swords;

public class NameGenerator
{
	private StringBuilder builder;
	
	public NameGenerator()
	{
		builder = new StringBuilder();
	}
	
	public NameGenerator generateName(WeaponType material)
	{
		if(material.equals(WeaponType.BOW))
		{
			generateBowName();
			return this;
		}
		generateSwordName();
		return this;
	}
	
	public NameGenerator generateTitle(WeaponType material)
	{
		if(material.equals(WeaponType.BOW))
		{
			generateBowTitle();
			return this;
		}
		generateSwordTitle();
		return this;
	}
	
	public NameGenerator generateBowName()
	{
		builder.append(Bow.values()[(int)(Math.random()*(Bow.values().length - 1) + 1)].getName());
		return this;
	}
	
	public NameGenerator generateSwordName()
	{
		builder.append(Swords.values()[(int)(Math.random()*(Swords.values().length - 1) + 1)].getName());
		return this;
	}
	
	public NameGenerator generateBowTitle()
	{
		builder.append(", ");
		builder.append(BowTitle.values()[(int)(Math.random()*(BowTitle.values().length - 1) + 1)].getName());
		return this;
	}
	
	public NameGenerator generateSwordTitle()
	{
		builder.append(", ");
		builder.append(SwordTitle.values()[(int)(Math.random()*(SwordTitle.values().length - 1) + 1)].getName());
		return this;
	}
	
	public String toString()
	{
		return builder.toString();
	}
}
