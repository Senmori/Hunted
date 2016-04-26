package net.senmori.hunted.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.core.LootContext;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Senmori on 4/1/2016.
 */
public class LootEntryEmpty extends LootEntry {

    public LootEntryEmpty(int weight, int quality, List<LootCondition> conditions) {
        super(weight, quality, conditions);
    }

    public LootEntryEmpty(int weight, int quality) {
        this(weight, quality, null);
    }


    @Override
    public void addLoot(Collection<ItemStack> itemStacks, Random rand, LootContext context) {
    }

    @Override
    protected void serialize(JsonObject json, JsonSerializationContext context) {
    }

    public static LootEntryEmpty deserialize(JsonObject object, JsonDeserializationContext context, int weight, int quality, List<LootCondition> conditions) {
        return new LootEntryEmpty(weight, quality, conditions);
    }
}
