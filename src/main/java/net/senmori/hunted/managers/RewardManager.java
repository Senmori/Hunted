package net.senmori.hunted.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Permissions.RewardMessage;

import org.bukkit.entity.Player;

public class RewardManager 
{
	private List<Reward> rewards;
	private HashMap<String,Double> bonusXpList;
	
	public RewardManager()
	{
		bonusXpList = new HashMap<String,Double>();
		rewards = new ArrayList<Reward>();
	}
	
	/*
	 * Loot generation process:
	 * - generate loot
	 * - give bonus xp as applicable
	 */
	public void generateReward(Player player)
	{
		// generate loot
		Random rand = new Random();
		getRewards().get(rand.nextInt(getRewards().size())).getLoot(player);
		doSendBonusXp(player);
	}
	
	/*
	 * Returns if player get's bonus xp(not to be confused with bonus xp from guardian stones)
	 */
	public boolean giveBonusXP(String player)
	{
		return bonusXpList.containsKey(player);
	}
	
	public double getBonusXpModifier(String player)
	{
		return bonusXpList.get(player);
	}
	
	public void doSendBonusXp(Player player)
	{
		// Xp-per-stone + (Xp-per-stone * modifier) = totalXp
		int earnedXp = (int) (Hunted.xpPerStone + ( giveBonusXP(player.getName()) ? (Hunted.xpPerStone * getBonusXpModifier(player.getName())) : 0 ));
		// send update to player with how much xp they earned
		ActionBar.sendUpdate(player, String.format(RewardMessage.XP_EARNED, earnedXp));
		player.setExp(player.getExp() + earnedXp);
	}
	
	public List<Reward> getRewards()
	{
		return rewards;
	}
	
	public void addReward(Reward r)
	{
		rewards.add(r);
	}
	
	public Reward getReward(String className)
	{
		for(Reward r : getRewards())
		{
			if(r.getClass().getSimpleName().contains(className))
			{
				return r;
			}
		}
		return null;
	}
}
