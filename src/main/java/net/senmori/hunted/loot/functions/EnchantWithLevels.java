package net.senmori.hunted.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.core.LootContext;
import net.senmori.hunted.loot.core.RandomValueRange;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.EnchantmentHelper;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

/**
 * Created by Senmori on 4/13/2016.
 */
public class EnchantWithLevels extends LootFunction {

    private RandomValueRange randomLevel;
    private boolean treasure;

    public EnchantWithLevels(RandomValueRange randomLevel, boolean treasure, List<LootCondition> conditions) {
        super(conditions);
        this.randomLevel = randomLevel;
        this.treasure = treasure;
    }

    public EnchantWithLevels(RandomValueRange randomLevel, boolean treasure) {
        this(randomLevel, treasure, null);
    }

    public void setMin(int min) {
        int max = (int) randomLevel.getMax();
        randomLevel = new RandomValueRange(min, max);
    }

    public void setMax(int max) {
        int min = (int) randomLevel.getMin();
        randomLevel = new RandomValueRange(min, max);
    }

    public void setNewLevels(int min, int max) {
        randomLevel = new RandomValueRange(min, max);
    }

    public void setTreasureEnchantsAllowed(boolean allowed) { this.treasure = allowed; }

    @Override
    public ItemStack apply(ItemStack itemstack, Random rand, LootContext context) {
        return EnchantmentHelper.addRandomEnchant(itemstack, randomLevel.generateInt(rand), treasure);
    }

    public RandomValueRange getRandomLevelRange() { return this.randomLevel; }

    public boolean getTreasureEnchantmentsAllowed() { return this.treasure; }


    public static class Serializer extends LootFunction.Serializer<EnchantWithLevels> {
        protected Serializer() { super(new ResourceLocation("enchant_with_levels"), EnchantWithLevels.class); }

        @Override
        public void serialize(JsonObject json, EnchantWithLevels type, JsonSerializationContext context) {
            json.add("levels", context.serialize(type.randomLevel));
            json.addProperty("treasure", type.treasure);
        }

        @Override
        public EnchantWithLevels deserialize(JsonObject json, JsonDeserializationContext context, List<LootCondition> conditions) {
            RandomValueRange range = JsonUtils.deserializeClass(json, "levels", context, RandomValueRange.class);
            boolean treasure = JsonUtils.getBoolean(json, "treasure", false);
            return new EnchantWithLevels(range, treasure, conditions);
        }
    }
}
