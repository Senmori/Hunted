package net.senmori.hunted.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;

import java.util.List;

/**
 * Created by Senmori on 3/29/2016.
 */
public class LootEntryTable extends LootEntry {

    protected ResourceLocation table;

    public LootEntryTable(ResourceLocation table, int weight, int quality, List<LootCondition> conditions) {
        super(weight, quality, conditions);
        this.table = table;
    }

    public ResourceLocation getLootTable() { return this.table; }

    public void setLootTable(ResourceLocation location) { this.table = location; }

    @Override
    protected void serialize(JsonObject object, JsonSerializationContext context) {
        object.addProperty("name", this.table.toString());
    }


    public static LootEntryTable deserialize(JsonObject object, JsonDeserializationContext context, int weight, int quality, List<LootCondition> conditions) {
        ResourceLocation location = new ResourceLocation(JsonUtils.getString(object, "name"));
        return new LootEntryTable(location, weight, quality, conditions);
    }
}
