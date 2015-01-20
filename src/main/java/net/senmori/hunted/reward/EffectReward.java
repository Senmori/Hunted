package net.senmori.hunted.reward;

import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.Reference.DebugMessage;
import net.senmori.hunted.util.Reference.RewardMessage;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectReward extends Reward 
{	
	private List<String> potionEffects;
	private String name;
	public EffectReward(String name) 
	{
		Hunted.getRewardManager().addReward(this);
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
		DebugMessage.sendMessage(player, String.format(DebugMessage.REWARD, player, type.toString()));
		
		// you're really unlucky...
		if(rNum % Hunted.receiveEffectTwice == 0)
		{
			PotionEffect p = new PotionEffect(PotionEffectType.getByName(potionEffects.get(rand.nextInt(potionEffects.size()))),getRandomPotionLength(3),rand.nextBoolean() ? 1 : 2);
			
			player.addPotionEffect(p);
			player.sendMessage(ChatColor.GOLD + String.format(RewardMessage.EFFECT_UNLUCKY, p.getType().getName().toLowerCase()));
			DebugMessage.sendMessage(player, String.format(DebugMessage.REWARD, player, p.getType().toString()));
		}
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	private int getRandomPotionLength(int min)
	{
		return (int) (Math.random() * (Hunted.maxEffectLength - min) + min);
	}
}
