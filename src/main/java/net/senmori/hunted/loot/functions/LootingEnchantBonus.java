package net.senmori.hunted.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.core.LootContext;
import net.senmori.hunted.loot.core.RandomValueRange;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

/**
 * Created by Senmori on 4/14/2016.
 */
public class LootingEnchantBonus extends LootFunction {

    private RandomValueRange count;

    public LootingEnchantBonus(RandomValueRange count, List<LootCondition> conditions) {
        super(conditions);
        this.count = count;
    }

    public LootingEnchantBonus(RandomValueRange count) {
        this(count, null);
    }

    public void setMin(int min) {
        int max = (int) count.getMax();
        count = new RandomValueRange(min, max);
    }

    public void setMax(int max) {
        int min = (int) count.getMin();
        count = new RandomValueRange(min, max);
    }

    public void setRange(int min, int max) {
        count = new RandomValueRange(min, max);
    }

    @Override
    public ItemStack apply(ItemStack itemstack, Random rand, LootContext context) {
        Entity entity = context.getKiller();
        int i = 0;
        if (entity instanceof LivingEntity) {
            i = ((LivingEntity) context.getKiller()).getEquipment().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
        }
        if (i == 0) { return itemstack; }
        float f = (float) i * this.count.generateFloat(rand);
        itemstack.setAmount(itemstack.getAmount() + Math.round(f));
        return itemstack;
    }

    public RandomValueRange getCount() { return this.count; }


    public static class Serializer extends LootFunction.Serializer<LootingEnchantBonus> {
        protected Serializer() { super(new ResourceLocation("looting_enchant"), LootingEnchantBonus.class); }

        @Override
        public void serialize(JsonObject json, LootingEnchantBonus type, JsonSerializationContext context) {
            json.add("count", context.serialize(type.count));
        }

        @Override
        public LootingEnchantBonus deserialize(JsonObject json, JsonDeserializationContext context, List<LootCondition> conditions) {
            return new LootingEnchantBonus(JsonUtils.deserializeClass(json, "count", context, RandomValueRange.class), conditions);
        }
    }
}
