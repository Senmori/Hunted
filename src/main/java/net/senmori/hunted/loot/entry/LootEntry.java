package net.senmori.hunted.loot.entry;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.conditions.LootCondition;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Senmori on 4/1/2016.
 */
public abstract class LootEntry {

    protected int weight;
    protected int quality;
    protected List<LootCondition> conditions;


    protected LootEntry(int weight, int quality, List<LootCondition> conditions) {
        this.weight = weight;
        this.quality = quality;
        this.conditions = conditions;
    }

    public void addCondition(LootCondition condition) {
        if (conditions == null) { conditions = new ArrayList<>(); }
        conditions.add(condition);
    }

    public List<LootCondition> getConditions() { return this.conditions; }

    public abstract void addLoot(List<ItemStack> itemStacks);

    protected abstract void serialize(JsonObject json, JsonSerializationContext context);
}
