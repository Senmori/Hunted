package net.senmori.hunted.managers.kit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.kit.potion.PotionTypeEnum;
import net.senmori.hunted.managers.ConfigManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
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
	

	/** Generate a random potion using {@link PotionEffectEnum } */
	public void getPotion(Player player) {
		// get potion effects
		List<PotionEffectType> effects = new ArrayList<>();
		// get between 1 and 3 effects (for non-splash potions)
		for(int i = 0; i < rand.nextInt(3) + 1; i++) {
			effects.add(getRandomEffectType());
		}
		
		ItemStack potionStack = new ItemStack(Material.POTION);
		PotionMeta meta = (PotionMeta)potionStack.getItemMeta();
		
		for(PotionEffectType eff : effects) {
			PotionEffect effect = new PotionEffect(eff, getDuration(), getAmplifier());
			meta.addCustomEffect(effect, true);
		}
		meta.setMainEffect(effects.get(rand.nextInt(effects.size())));
		potionStack.setItemMeta(meta);
		player.getInventory().addItem(potionStack);
	}
	
	/** Generate a random splash potion */
	public void getSplashPotion(Player player) {
	    Collections.shuffle(potionTypes);
	    PotionType mainType = getRandomType();
	    Potion potion = new Potion(mainType);
	    potion.setSplash(true);
	    potion.setHasExtendedDuration( (rand.nextInt(plugin.getConfigManager().potionTierChance) % plugin.getConfigManager().potionTierChance == 0) );
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
	
	private PotionEffectType getRandomEffectType() {
		Collections.shuffle(potionEffectTypes);
		return potionEffectTypes.get(0);
	}
	
	private PotionType getRandomType() {
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
