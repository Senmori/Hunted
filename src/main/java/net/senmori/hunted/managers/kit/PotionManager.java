package net.senmori.hunted.managers.kit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.ConfigManager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
		load();
	}
	
	public ItemStack getPotion() {
	    return ((int)Math.random() * (10-1) + 1) >= 10 ? generateSplashPotion() : generatePotion();
	}

	/** Generate a random potion using {@link PotionEffectEnum } */
	public ItemStack generatePotion() {
		Potion potion = new Potion(getRandomPotionType());
		int numEffects = rand.nextInt(3) + 1; // generate between 1 and 3 effects to put onto this potion
		for(int i = 0; i < numEffects; i++) {
		    PotionEffect effect = new PotionEffect(getRandomPotionEffectType(), getDuration(), getAmplifier());
		    if(potion.getEffects().contains(effect)) {
		        effect = new PotionEffect(getRandomPotionEffectType(), getDuration(), getAmplifier());
		    }
		}
		return potion.toItemStack(1);
	}
	
	/** Generate a random splash potion */
	public ItemStack generateSplashPotion() {
	    Potion potion = new Potion(getRandomPotionType());
	    potion.setLevel(rand.nextInt(plugin.getConfigManager().potionTierChance + 1) == plugin.getConfigManager().potionTierChance ? 0 : potion.getType().getMaxLevel());
	    potion.setSplash(true);
	    potion.setHasExtendedDuration(rand.nextInt(plugin.getConfigManager().potionTierChance + 1) == plugin.getConfigManager().potionTierChance);
	    return potion.toItemStack(1);
	}
	
	/** Get a random duration between 0 and {@link ConfigManger#maxEffectLength} in ticks */
	public int getDuration() {
		return rand.nextInt(plugin.getConfigManager().maxEffectLength + 1)*20;
	}
	
	/** Get a random amplifier level between 0 and {@link ConfigManager#maxAmplifierLevel} */
	public int getAmplifier() {
		return rand.nextInt(plugin.getConfigManager().maxAmplifierLevel);
	}
	
	public PotionEffectType getRandomPotionEffectType() {
		Collections.shuffle(potionEffectTypes);
		return potionEffectTypes.get(0);
	}
	
	public PotionType getRandomPotionType() {
	    Collections.shuffle(potionTypes);
	    return potionTypes.get(0);
	}
	
	public List<PotionEffectType> getPotionEffectTypes() {
		return potionEffectTypes;
	}
	
	private void load() {
	    // potion effect types (for drinkable potions & effects)
		potionEffectTypes.add(PotionEffectType.SLOW);
		potionEffectTypes.add(PotionEffectType.INCREASE_DAMAGE);
		potionEffectTypes.add(PotionEffectType.HEAL);
		potionEffectTypes.add(PotionEffectType.HARM);
		potionEffectTypes.add(PotionEffectType.JUMP);
		potionEffectTypes.add(PotionEffectType.CONFUSION);
		potionEffectTypes.add(PotionEffectType.REGENERATION);
		potionEffectTypes.add(PotionEffectType.DAMAGE_RESISTANCE);
		potionEffectTypes.add(PotionEffectType.FIRE_RESISTANCE);
		potionEffectTypes.add(PotionEffectType.WATER_BREATHING);
		potionEffectTypes.add(PotionEffectType.INVISIBILITY);
		potionEffectTypes.add(PotionEffectType.BLINDNESS);
		potionEffectTypes.add(PotionEffectType.NIGHT_VISION);
		potionEffectTypes.add(PotionEffectType.HUNGER);
		potionEffectTypes.add(PotionEffectType.WEAKNESS);
		potionEffectTypes.add(PotionEffectType.POISON);
		potionEffectTypes.add(PotionEffectType.HEALTH_BOOST);
		potionEffectTypes.add(PotionEffectType.ABSORPTION);
		potionEffectTypes.add(PotionEffectType.SATURATION);
		potionEffectTypes.add(PotionEffectType.SPEED);
		
		// potion types (for splash potions)
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
