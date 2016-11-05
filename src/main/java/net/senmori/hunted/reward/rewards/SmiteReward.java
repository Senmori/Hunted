package net.senmori.hunted.reward.rewards;


import net.senmori.hunted.Hunted;
import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference.RewardMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;

public class SmiteReward extends Reward {
	private String name;

	public SmiteReward(String name) {
		this.name = name;
	}

	@Override
	public void generateLoot(Player player) {
		player.getWorld().strikeLightning(player.getLocation());
		ActionBar.sendMessage(player, ChatColor.GOLD + RewardMessage.SMITE_MESSAGE);

		Random rand = new Random();
		int chance = Hunted.getInstance().getConfigManager().smiteTeleportChance;
		if (rand.nextInt(chance + 1) % chance == 0) {
			Hunted.getInstance().getRewardManager().getReward("teleport").generateLoot(player);
		}
	}

	@Override
	public String getName() {
		return name;
	}

}
