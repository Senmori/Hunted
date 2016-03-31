package net.senmori.hunted.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.condition.LootCondition;

import java.util.List;

/**
 * Created by Senmori on 3/29/2016.
 */
public class LootEntryEmpty extends LootEntry {

    public LootEntryEmpty(int weight, int quality, List<LootCondition> conditions) {
        super(weight, quality, conditions);
    }


    @Override
    protected void serialize(JsonObject object, JsonSerializationContext context) {
    }

    public static LootEntryEmpty deserialize(JsonObject object, JsonDeserializationContext context, int weight, int quality, List<LootCondition> conditions) {
        return new LootEntryEmpty(weight, quality, conditions);
    }
}
