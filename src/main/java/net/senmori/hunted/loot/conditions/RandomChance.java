package net.senmori.hunted.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.core.LootContext;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;

import java.util.Random;

/**
 * Created by Senmori on 4/13/2016.
 */
public class RandomChance implements LootCondition {
    private float chance;

    public RandomChance(float chance) { this.chance = chance; }

    public void setChance(float chance) { this.chance = chance; }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        return rand.nextFloat() < this.chance;
    }

    public float getChance() { return this.chance; }


    public static class Serializer extends LootCondition.Serializer<RandomChance> {
        protected Serializer() { super(new ResourceLocation("random_chance"), RandomChance.class); }

        @Override
        public void serialize(JsonObject json, RandomChance type, JsonSerializationContext context) {
            json.addProperty("chance", type.getChance());
        }

        @Override
        public RandomChance deserialize(JsonObject json, JsonDeserializationContext context) {
            return new RandomChance(JsonUtils.getFloat(json, "chance"));
        }
    }
}
