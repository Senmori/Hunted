package net.senmori.hunted.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;

import java.util.Random;

/**
 * Created by Senmori on 4/13/2016.
 */
public class RandomChanceWithLooting implements LootCondition {

    private float chance;
    private float lootingMultiplier;

    public RandomChanceWithLooting(float chance, float lootingMultiplier) {
        this.chance = chance;
        this.lootingMultiplier = lootingMultiplier;
    }

    public void setChance(float newChance) { this.chance = newChance; }

    public void setLootingMultiplier(float multiplier) { this.lootingMultiplier = multiplier; }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        int i = 0;
        if (context.getKiller() instanceof LivingEntity) {
            i = ((LivingEntity) context.getKiller()).getEquipment().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
        }
        return rand.nextFloat() < this.chance * (float) i * this.lootingMultiplier;
    }

    public float getChance() { return this.chance; }

    public float getLootingMultiplier() { return this.lootingMultiplier; }

    public static class Serializer extends LootCondition.Serializer<RandomChanceWithLooting> {
        protected Serializer() { super(new ResourceLocation("random_chance_with_looting"), RandomChanceWithLooting.class); }

        @Override
        public void serialize(JsonObject json, RandomChanceWithLooting type, JsonSerializationContext context) {
            json.addProperty("chance", type.getChance());
            json.addProperty("looting_multiplier", type.getLootingMultiplier());
        }

        @Override
        public RandomChanceWithLooting deserialize(JsonObject json, JsonDeserializationContext context) {
            return new RandomChanceWithLooting(JsonUtils.getFloat(json, "chance"), JsonUtils.getFloat(json, "looting_multiplier"));
        }
    }
}
