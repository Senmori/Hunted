package net.senmori.hunted.reward;

import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.Permissions.RewardMessage;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectReward extends Reward 
{	
	private List<String> potionEffects;
	private String name;
	public EffectReward(String name) 
	{
		Hunted.rewardManager.addReward(this);
		potionEffects = Hunted.lootConfig.getStringList("loot.effect");
	};
	
	@Override
	public void getLoot(Player player) 
	{
		Random rand = new Random();
		int rNum = rand.nextInt(Hunted.receiveEffectTwice+1);
		
		PotionEffectType type = PotionEffectType.getByName(potionEffects.get(rand.nextInt(potionEffects.size())));
		int duration = rand.nextInt(Hunted.maxEffectLength);
		int amplifier = rand.nextBoolean() ? 1 : 2;
		
		PotionEffect pet = new PotionEffect(type, duration, amplifier);
		
		player.addPotionEffect(pet);
		player.sendMessage(ChatColor.GOLD + String.format(RewardMessage.EFFECT_REWARD, type.getName().toLowerCase()));
		
		// you're really unlucky...
		if(rNum % Hunted.receiveEffectTwice == 0)
		{
			PotionEffect p = new PotionEffect(PotionEffectType.getByName(potionEffects.get(rand.nextInt(potionEffects.size()))),Hunted.getRewardManager().getRandomPotionLength(),rand.nextBoolean() ? 1 : 2);
			
			player.addPotionEffect(p);
			player.sendMessage(ChatColor.GOLD + String.format(RewardMessage.EFFECT_UNLUCKY, p.getType().getName().toLowerCase()));
		}
	}
	
	@Override
	public String getName()
	{
		return name;
	}
}
