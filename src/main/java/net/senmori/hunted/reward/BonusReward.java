package net.senmori.hunted.reward;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.Permissions.RewardMessage;

import org.bukkit.entity.Player;

public class BonusReward extends Reward
{
	
	private String name;
	public BonusReward(String name) 
	{
		this.name = name;
		Hunted.rewardManager.addReward(this);
	};
	
	@Override
	public void getLoot(Player player) 
	{
		Hunted.rewardManager.doSendBonusXp(player);
		int earnedXp = (int) (Hunted.xpPerStone + ( Hunted.rewardManager.giveBonusXP(player.getName()) ? (Hunted.xpPerStone * Hunted.rewardManager.getBonusXpModifier(player.getName())) : 0 ));
		player.sendMessage(ChatColor.AQUA + String.format(RewardMessage.STONE_REWARD, earnedXp + "extra experience"));
	}
	
	@Override
	public String getName()
	{
		return name;
	}
}
