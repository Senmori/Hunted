package net.senmori.hunted.kit.potion;

import org.bukkit.potion.PotionEffectType;

public enum PotionTypeEnum {
    
    SLOW(PotionEffectType.SLOW),
    INCREASE_DAMAGE(PotionEffectType.INCREASE_DAMAGE),
    HEAL(PotionEffectType.HEAL),
    HARM(PotionEffectType.HARM),
    JUMP(PotionEffectType.JUMP),
    CONFUSION(PotionEffectType.CONFUSION),
    REGENERATION(PotionEffectType.REGENERATION),
    DAMAGE_RESISTANCE(PotionEffectType.DAMAGE_RESISTANCE),
    FIRE_RESISTANCE(PotionEffectType.FIRE_RESISTANCE),
    WATER_BREATHING(PotionEffectType.WATER_BREATHING),
    INVISIBILITY(PotionEffectType.INVISIBILITY),
    BLINDNESS(PotionEffectType.BLINDNESS),
    NIGHT_VISION(PotionEffectType.NIGHT_VISION),
    HUNGER(PotionEffectType.HUNGER),
    WEAKNESS(PotionEffectType.WEAKNESS),
    POISON(PotionEffectType.POISON),
    HEALTH_BOOST(PotionEffectType.HEALTH_BOOST),
    ABSORPTION(PotionEffectType.ABSORPTION),
    SATURATION(PotionEffectType.SATURATION),
    SPEED(PotionEffectType.SPEED);
   
    
    private PotionEffectType type;
    PotionTypeEnum(PotionEffectType type) {
        this.type = type;
    }
    
    public PotionEffectType getType() {
        return type;
    }
}
