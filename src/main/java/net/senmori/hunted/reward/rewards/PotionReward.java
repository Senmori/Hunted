package net.senmori.hunted.reward.rewards;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.util.Reference.RewardMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;

public class PotionReward extends Reward {

	private String name;

	public PotionReward(String name) {
		this.name = name;
	}

	@Override
	public void generateLoot(Player player) {
		ItemStack potion = Hunted.getInstance().getPotionManager().getPotion();
		player.sendMessage(ChatColor.GREEN
		        + MessageFormat.format(RewardMessage.STONE_REWARD, potion.getItemMeta().getDisplayName()));
	}

	@Override
	public String getName() {
		return name;
	}
}
