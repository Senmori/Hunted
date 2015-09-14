package net.senmori.hunted.reward.rewards;

import java.text.MessageFormat;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.util.Reference.RewardMessage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectReward extends Reward {	
	private String name;
	public EffectReward(String name) {
		this.name = name;
	}
	
	@Override
	public void generateLoot(Player player) {
		PotionEffectType type = Hunted.getInstance().getPotionManager().getRandomPotionEffectType();
		int duration = Hunted.getInstance().getPotionManager().getDuration();
		int amplifier = Hunted.getInstance().getPotionManager().getAmplifier();
		
		player.addPotionEffect(new PotionEffect(type, duration, amplifier));
		player.sendMessage(ChatColor.GREEN + MessageFormat.format(RewardMessage.EFFECT_REWARD, type.getName()));
		
		// 10% chance of receiving a second effect
		if( ((int)(Math.random() * (10 -1 ) + 1)) >= 10) {
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
