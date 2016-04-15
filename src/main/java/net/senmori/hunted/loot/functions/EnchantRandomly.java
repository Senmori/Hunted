package net.senmori.hunted.loot.functions;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import net.senmori.hunted.loot.utils.MathHelper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Senmori on 4/13/2016.
 */
public class EnchantRandomly extends LootFunction {

    private List<Enchantment> enchantments;

    public EnchantRandomly(List<Enchantment> enchantments, List<LootCondition> conditions) {
        super(conditions);
        this.enchantments = enchantments;
    }

    public void addEnchantment(Enchantment enchant) {
        if (enchantments.contains(enchant)) return;
        enchantments.add(enchant);
    }

    public List<Enchantment> getEnchantments() { return this.enchantments; }

    @Override
    public ItemStack apply(ItemStack itemstack, Random rand, LootContext context) {
        Enchantment enchant = null;
        if (enchantments != null && !enchantments.isEmpty()) {
            enchant = this.enchantments.get(rand.nextInt(this.enchantments.size()));
        } else {
            // look through default Bukkit enchantments
            List list = Lists.newArrayList();

            Enchantment[] possible = Enchantment.values();
            boolean continueLoop = true;
            do {
                int i = rand.nextInt(possible.length);
                if (possible[i].canEnchantItem(itemstack) && !itemstack.getType().equals(Material.ENCHANTED_BOOK)) {
                    enchant = possible[i];
                }
            } while (continueLoop);
        }

        if (enchant == null) return itemstack; // this should never happen
        if (itemstack.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemstack.getItemMeta();
            int level = MathHelper.getRandomIntegerInRange(rand, enchant.getStartLevel(), enchant.getMaxLevel());
            if (meta.hasStoredEnchant(enchant)) {
                int oldEnchantLevel = meta.getStoredEnchantLevel(enchant);
                meta.addStoredEnchant(enchant, level + oldEnchantLevel, false);
                itemstack.setItemMeta(meta);
                return itemstack;
            } else {
                meta.addStoredEnchant(enchant, level, false);
                itemstack.setItemMeta(meta);
                return itemstack;
            }
        } else {
            ItemMeta meta = itemstack.getItemMeta();
            meta.addEnchant(enchant, MathHelper.getRandomIntegerInRange(rand, enchant.getStartLevel(), enchant.getMaxLevel()), false);
            itemstack.setItemMeta(meta);
            return itemstack;
        }
    }


    public static class Serializer extends LootFunction.Serializer<EnchantRandomly> {
        protected Serializer() { super(new ResourceLocation("enchant_randomly"), EnchantRandomly.class); }

        @Override
        public void serialize(JsonObject json, EnchantRandomly type, JsonSerializationContext context) {
            if (type.enchantments != null && !type.enchantments.isEmpty()) {
                JsonArray array = new JsonArray();
                Iterator iter = type.enchantments.iterator();

                while (iter.hasNext()) {
                    Enchantment enchant = (Enchantment) iter.next();
                    ResourceLocation loc = new ResourceLocation(enchant.getName());
                    if (loc == null) {
                        throw new IllegalArgumentException("Don't know how to serialize enchantment " + enchant);
                    }
                    array.add(new JsonPrimitive(loc.toString()));
                }
                json.add("enchantments", array);
            }
        }

        @Override
        public EnchantRandomly deserialize(JsonObject json, JsonDeserializationContext context, List<LootCondition> conditions) {
            List list = null;
            if (json.has("enchantments")) {
                list = Lists.newArrayList();
                Iterator iter = JsonUtils.getJsonArray(json, "enchantments").iterator();

                while (iter.hasNext()) {
                    JsonElement element = (JsonElement) iter.next();
                    String s = JsonUtils.getString(element, "enchantment");
                    Enchantment enchant = Enchantment.getByName(s);
                    if (enchant == null) {
                        throw new JsonSyntaxException("Unknown enchantment \'" + s + "\'");
                    }
                    list.add(enchant);
                }
            }
            return new EnchantRandomly(list, conditions);
        }
    }

}