package net.senmori.hunted.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.core.LootContext;
import net.senmori.hunted.loot.core.RandomValueRange;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

/**
 * Created by Senmori on 4/14/2016.
 */
public class SetCount extends LootFunction {
    private RandomValueRange range;

    public SetCount(RandomValueRange range, List<LootCondition> conditions) {
        super(conditions);
        this.range = range;
    }

    public SetCount(RandomValueRange range) {
        this(range, null);
    }

    public void setMin(int min) {
        int max = (int) range.getMax();
        range = new RandomValueRange(min, max);
    }

    public void setMax(int max) {
        int min = (int) range.getMin();
        range = new RandomValueRange(min, max);
    }

    public void setRange(int min, int max) {
        range = new RandomValueRange(min, max);
    }

    @Override
    public ItemStack apply(ItemStack itemstack, Random rand, LootContext context) {
        itemstack.setAmount(itemstack.getAmount() + range.generateInt(rand));
        return itemstack;
    }

    public RandomValueRange getRange() { return this.range; }

    public static class Serializer extends LootFunction.Serializer<SetCount> {
        protected Serializer() { super(new ResourceLocation("set_count"), SetCount.class); }

        @Override
        public void serialize(JsonObject json, SetCount type, JsonSerializationContext context) {
            json.add("count", context.serialize(type.range));
        }

        @Override
        public SetCount deserialize(JsonObject json, JsonDeserializationContext context, List<LootCondition> conditions) {
            return new SetCount(JsonUtils.deserializeClass(json, "count", context, RandomValueRange.class), conditions);
        }
    }
}
