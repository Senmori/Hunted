package net.senmori.hunted.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.core.LootContext;
import net.senmori.hunted.loot.core.LootTable;
import net.senmori.hunted.loot.core.LootTableManager;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Senmori on 4/1/2016.
 */
public class LootEntryTable extends LootEntry {

    protected ResourceLocation lootTableLocation;

    public LootEntryTable(ResourceLocation location, int weight, int quality, List<LootCondition> conditions) {
        super(weight, quality, conditions);
        this.lootTableLocation = location;
    }

    public LootEntryTable(ResourceLocation location, int weight, int quality) {
        this(location, weight, quality, null);
    }

    public ResourceLocation getLootTableLocation() { return this.lootTableLocation; }

    @Override
    public void addLoot(Collection<ItemStack> itemStacks, Random rand, LootContext context) {
        LootTable table = LootTableManager.getLootTable(lootTableLocation);
        List coll = table.generateLootForPools(rand, context);
        itemStacks.addAll(coll);
    }


    @Override
    protected void serialize(JsonObject json, JsonSerializationContext context) {
        json.addProperty("name", this.lootTableLocation.toString());
    }


    public static LootEntryTable deserialize(JsonObject jsonObject, JsonDeserializationContext context, int weight, int quality, List<LootCondition> conditions) {
        ResourceLocation rLocation = new ResourceLocation(JsonUtils.getString(jsonObject, "name"));
        return new LootEntryTable(rLocation, weight, quality, conditions);
    }
}
