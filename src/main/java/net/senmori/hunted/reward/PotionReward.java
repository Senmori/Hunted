package net.senmori.hunted.reward;

import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class PotionReward extends Reward 
{	
	private enum Type
	{
		SPEED(PotionType.SPEED,60),
		NIGHTVISION(PotionType.NIGHT_VISION,180),
		JUMP(PotionType.JUMP,30),
		WATER_BREATHING(PotionType.WATER_BREATHING,60),
		FIRE_RESIST(PotionType.FIRE_RESISTANCE,60),
		INIVISBILITY(PotionType.INVISIBILITY,60),
		WEAKNESS(PotionType.WEAKNESS,75),
		POISON(PotionType.POISON,20),
		REGEN(PotionType.REGEN,20);
		
		private PotionType type;
		private int maxDuration; // in seconds
		Type(PotionType type, int duration)
		{
			this.type = type;
			this.maxDuration = duration;
		}
		
		public int getMaxDuration()
		{
			return maxDuration;
		}
		
		public PotionType getType()
		{
			return type;
		}
	}

	@Override
	public void pickLoot(Player player) 
	{
		Type type = Type.values()[(int) Math.random() * Type.values().length];
		PotionType potType = type.getType();
		int level = (int) (Math.random() * (2-1) + 1);
		Potion pot = new Potion(potType, level);
		pot.setSplash(true);
		// only extend potion duration if it's level 2
		pot.setHasExtendedDuration(level == 1 ? true : false);
		
		// random amount between 1 and 4
		player.getInventory().addItem(pot.toItemStack((int) (Math.random() * (4-1) + 1)));
	}

}
