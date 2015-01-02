package net.senmori.hunted.reward;

import net.senmori.hunted.Hunted;

import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class PotionReward extends Reward 
{	
	public enum Type
	{
		SPEED(PotionType.SPEED),
		NIGHTVISION(PotionType.NIGHT_VISION),
		JUMP(PotionType.JUMP),
		WATER_BREATHING(PotionType.WATER_BREATHING),
		FIRE_RESIST(PotionType.FIRE_RESISTANCE),
		INIVISBILITY(PotionType.INVISIBILITY),
		WEAKNESS(PotionType.WEAKNESS),
		POISON(PotionType.POISON),
		REGEN(PotionType.REGEN);
		
		private PotionType type;
		private Type(PotionType type)
		{
			this.type = type;
		}

		public PotionType getType()
		{
			return type;
		}
	}
	
	private String name;
	public PotionReward(String name) 
	{
		this.name = name;
		Hunted.rewardManager.addReward(this);
	};
	
	@Override
	public void getLoot(Player player) 
	{
		// pick random potion from enum & generate data
		Type type = Type.values()[(int) Math.random() * Type.values().length];
		PotionType potType = type.getType();
		int level = (int) (Math.random() * (2-1) + 1);

		Potion pot = new Potion(potType, level);
		pot.setSplash(true);
		// only extend potion duration if it's level 2
		pot.setHasExtendedDuration(level == 2 ? true : false);
		
		// random amount between 1 and 4
		player.getInventory().addItem(pot.toItemStack((int) (Math.random() * (Hunted.maxPotsPerReward-1) + 1)));
	}
	
	@Override
	public String getName()
	{
		return name;
	}
}
