package net.senmori.hunted.managers.kit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
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

    public ItemStack getPotion() {
        int chance = Hunted.getInstance().getConfigManager().potionTierChance;
        int randNum = rand.nextInt(chance + 1);

        if(randNum >= chance) {
            return generatePotion(Material.SPLASH_POTION);
        }
        return generatePotion(Material.POTION);
    }

    /**
     * Generate a random potion using {@link PotionEffectType }
     */
    private ItemStack generatePotion(Material material) {
        ItemStack item = new ItemStack(material);
        PotionMeta pMeta = (PotionMeta) item.getItemMeta();
        int numEffects = rand.nextInt(3) + 1; // generate between 1 and 3 effects to put onto this potion
        for(int i = 0; i < numEffects; i++) {
            PotionEffect effect = new PotionEffect(getRandomPotionEffectType(), getDuration(), getAmplifier());
            if(pMeta.hasCustomEffects() && pMeta.getCustomEffects().contains(effect)) {
                effect = new PotionEffect(getRandomPotionEffectType(), getDuration(), getAmplifier());
            }
            pMeta.addCustomEffect(effect, false);
        }
        item.setItemMeta(pMeta);
        return item;
    }

    /**
     * Get a random duration between 0 and {@link ConfigManager#maxEffectLength} in ticks
     */
    public int getDuration() {
        return rand.nextInt(plugin.getConfigManager().maxEffectLength + 1) * 20;
    }

    /**
     * Get a random amplifier level between 0 and {@link ConfigManager#maxAmplifierLevel}
     */
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
        potionEffectTypes.add(PotionEffectType.ABSORPTION);
        potionEffectTypes.add(PotionEffectType.BLINDNESS);
        potionEffectTypes.add(PotionEffectType.CONFUSION);
        potionEffectTypes.add(PotionEffectType.DAMAGE_RESISTANCE);
        potionEffectTypes.add(PotionEffectType.FIRE_RESISTANCE);
        potionEffectTypes.add(PotionEffectType.HARM);
        potionEffectTypes.add(PotionEffectType.HEAL);
        potionEffectTypes.add(PotionEffectType.HEALTH_BOOST);
        potionEffectTypes.add(PotionEffectType.HUNGER);
        potionEffectTypes.add(PotionEffectType.INCREASE_DAMAGE);
        potionEffectTypes.add(PotionEffectType.INVISIBILITY);
        potionEffectTypes.add(PotionEffectType.JUMP);
        potionEffectTypes.add(PotionEffectType.LEVITATION);
        potionEffectTypes.add(PotionEffectType.LUCK);
        potionEffectTypes.add(PotionEffectType.NIGHT_VISION);
        potionEffectTypes.add(PotionEffectType.POISON);
        potionEffectTypes.add(PotionEffectType.REGENERATION);
        potionEffectTypes.add(PotionEffectType.SATURATION);
        potionEffectTypes.add(PotionEffectType.SLOW);
        potionEffectTypes.add(PotionEffectType.SPEED);
        potionEffectTypes.add(PotionEffectType.WATER_BREATHING);
        potionEffectTypes.add(PotionEffectType.WEAKNESS);
    }
}
