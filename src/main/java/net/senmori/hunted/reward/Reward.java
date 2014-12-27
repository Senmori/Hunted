package net.senmori.hunted.reward;

import org.bukkit.entity.Player;

public abstract class Reward 
{
	public enum Type
	{
		/*
		 * Reward Type breakdown:
		 * - ITEM : armor, weapon, junk, etc.
		 * - POTION : give rewardee a potion(throwable)
		 * - EFFECT: apply a potion effect(good/bad) to a player
		 * - NOTIFY : notify rewardee of all players within 'x' blocks or notify all players where rewardee is
		 * - TELEPORT: teleport rewardee to random spawn location
		 * - SMITE : strike rewardee w/ lightning
		 * - BONUS : give extra xp 
		 */
		ITEM(ItemReward.class),
		POTION(PotionReward.class),
		EFFECT(EffectReward.class),
		NOTIFY(EffectReward.class),
		TELEPORT(EffectReward.class),
		SMITE(SmiteReward.class),
		IRRITATING(IrritatingReward.class),
		BONUS(BonusReward.class);
		
		private Class<? extends Reward> c;
		Type(Class<? extends Reward> c)
		{
			this.c = c;
		}
		
		public Class<? extends Reward> getRewardClass()
		{
			return c;
		}
	}
	
	public void pickLoot(Player player) {};
}
