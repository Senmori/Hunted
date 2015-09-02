package net.senmori.hunted.managers.kit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.kit.potion.PotionTypeEnum;
import net.senmori.hunted.managers.ConfigManager;

import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PotionManager {
    private Hunted plugin;
	private List<PotionEffectType> potionEffectTypes;
	private List<PotionType> potionTypes;
	private Random rand;
	
	public PotionManager(Hunted plugin) {
	    this.plugin = plugin;
		potionEffectTypes = new ArrayList<>();
		potionTypes = new ArrayList<>();
		rand = new Random();
		load();
	}
	
	public void getPotion(Player player) {
	    if(rand.nextInt(11) >= 10) {
	        // splash potion
	        generateSplashPotion(player);
	        return;
	    }
	    // non-splash potion(with multiple effects?)
	    generatePotion(player);
	}

	/** Generate a random potion using {@link PotionEffectEnum } */
	public void generatePotion(Player player) {
		Potion potion = new Potion(getRandomPotionType());
		int numEffects = rand.nextInt(3) + 1; // generate between 1 and 3 effects to put onto this potion
		for(int i = 0; i < numEffects; i++) {
		    PotionEffect effect = new PotionEffect(getRandomPotionEffectType(), getDuration(), getAmplifier());
		    if(potion.getEffects().contains(effect)) {
		        effect = new PotionEffect(getRandomPotionEffectType(), getDuration(), getAmplifier());
		    }
		}
		player.getInventory().addItem(potion.toItemStack(1));
	}
	
	/** Generate a random splash potion */
	public void generateSplashPotion(Player player) {
	    Potion potion = new Potion(getRandomPotionType());
	    potion.setLevel(rand.nextInt(plugin.getConfigManager().potionTierChance + 1) == plugin.getConfigManager().potionTierChance ? 0 : potion.getType().getMaxLevel());
	    potion.setSplash(true);
	    potion.setHasExtendedDuration(rand.nextInt(plugin.getConfigManager().potionTierChance + 1) == plugin.getConfigManager().potionTierChance);
	    player.getInventory().addItem(potion.toItemStack(1));
	}
	
	/** Get a random duration between 0 and {@link ConfigManger#maxEffectLength} in ticks */
	private int getDuration() {
		return rand.nextInt(plugin.getConfigManager().maxEffectLength + 1)*20;
	}
	
	/** Get a random amplifier level between 0 and {@link ConfigManager#maxAmplifierLevel} */
	private int getAmplifier() {
		return rand.nextInt(plugin.getConfigManager().maxAmplifierLevel);
	}
	
	private PotionEffectType getRandomPotionEffectType() {
		Collections.shuffle(potionEffectTypes);
		return potionEffectTypes.get(0);
	}
	
	private PotionType getRandomPotionType() {
	    Collections.shuffle(potionTypes);
	    return potionTypes.get(0);
	}
	
	public List<PotionEffectType> getPotionEffectTypes() {
		return potionEffectTypes;
	}
	
	private void load() {
		for(PotionTypeEnum e : PotionTypeEnum.values()) {
			potionEffectTypes.add(e.getType());
		}
		
		potionTypes.add(PotionType.REGEN);
		potionTypes.add(PotionType.SPEED);
		potionTypes.add(PotionType.FIRE_RESISTANCE);
		potionTypes.add(PotionType.POISON);
		potionTypes.add(PotionType.INSTANT_HEAL);
		potionTypes.add(PotionType.STRENGTH);
		potionTypes.add(PotionType.JUMP);
		potionTypes.add(PotionType.NIGHT_VISION);
		potionTypes.add(PotionType.WATER_BREATHING);
		potionTypes.add(PotionType.WEAKNESS);
	}
}
