package net.senmori.hunted.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.RandomValueRange;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

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
        Enchantment enchant = null;
        Enchantment[] possible = Enchantment.values();

        do {
            int i = rand.nextInt(possible.length);
            if (possible[i].canEnchantItem(itemstack)) {
                if ((possible[i].equals(Enchantment.FROST_WALKER) || possible[i].equals(Enchantment.MENDING)) && this.treasure) {
                    enchant = possible[i];
                    break;
                } else {
                    enchant = possible[i];
                    break;
                }
            }
        } while (enchant == null);
        if (itemstack.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemstack.getItemMeta();
            int oldLevel = meta.getStoredEnchantLevel(enchant);
            meta.addStoredEnchant(enchant, (this.randomLevel.generateInt(rand) + oldLevel), false);
            itemstack.setItemMeta(meta);
            return itemstack;
        } else {
            ItemMeta meta = itemstack.getItemMeta();
            meta.addEnchant(enchant, randomLevel.generateInt(rand), false);
            itemstack.setItemMeta(meta);
            return itemstack;
        }
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
