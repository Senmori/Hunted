package net.senmori.hunted.reward;

import java.util.List;
import java.util.Random;

import net.senmori.hunted.Hunted;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

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
		int rNum = rand.nextInt(20);
		
		PotionEffectType type = PotionEffectType.getByName(potionEffects.get(rand.nextInt(potionEffects.size())));
		int duration = rand.nextInt(Hunted.maxEffectLength);
		int amplifier = rand.nextBoolean() ? 1 : 2;
		
		PotionEffect pet = new PotionEffect(type, duration, amplifier);
		
		player.addPotionEffect(pet);
		
		// you're really unlucky...
		if(rNum % 20 == 0)
		{
			PotionEffect p = new PotionEffect(PotionEffectType.getByName(potionEffects.get(rand.nextInt(potionEffects.size()))),rand.nextInt(Hunted.maxEffectLength),rand.nextBoolean() ? 1 : 2);
			
			player.addPotionEffect(p);
		}
	}
	
	@Override
	public String getName()
	{
		return name;
	}
}
