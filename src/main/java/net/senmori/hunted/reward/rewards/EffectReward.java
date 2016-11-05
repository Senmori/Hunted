package net.senmori.hunted.reward.rewards;

import java.util.Random;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.util.Reference.RewardMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.MessageFormat;

public class EffectReward extends Reward {
	private String name;
    private Random random;
    
    
	public EffectReward(String name) {
		this.name = name;
        random = new Random();
	}

	@Override
	public void generateLoot(Player player) {
		PotionEffectType type = Hunted.getInstance().getPotionManager().getRandomPotionEffectType();
		int duration = Hunted.getInstance().getPotionManager().getDuration();
		int amplifier = Hunted.getInstance().getPotionManager().getAmplifier();

		player.addPotionEffect(new PotionEffect(type, duration, amplifier));
		player.sendMessage(ChatColor.GREEN + MessageFormat.format(RewardMessage.EFFECT_REWARD, type.getName()));

		// n% chance of receiving a second effect
		if (random.nextInt(Hunted.getInstance().getConfigManager().receiveEffectTwice + 1) >= Hunted.getInstance().getConfigManager().receiveEffectTwice) {
			type = Hunted.getInstance().getPotionManager().getRandomPotionEffectType();
			duration = Hunted.getInstance().getPotionManager().getDuration();
			amplifier = Hunted.getInstance().getPotionManager().getAmplifier();
			player.addPotionEffect(new PotionEffect(type, duration, amplifier));
			player.sendMessage(ChatColor.BLUE + MessageFormat.format(RewardMessage.EFFECT_UNLUCKY, type));
		}
	}

	@Override
	public String getName() {
		return name;
	}
}
