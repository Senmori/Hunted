package net.senmori.hunted.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.utils.JsonUtils;
import net.senmori.hunted.loot.utils.MathHelper;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;



public abstract class LootEntry {

	protected int weight;
	protected int quality;
	protected List<LootCondition> conditions;

	protected LootEntry(int weight, int quality, List<LootCondition> conditions) {
		this.weight = weight;
		this.quality = quality;
		this.conditions = conditions;
	}

	public int getEffectiveQuality(float luck) {
		return Math.max(MathHelper.floorFloat((float)weight + (float)quality * luck), 0);
	}


	public void addCondition(LootCondition condition) {
		conditions.add(condition);
	}

	protected abstract void serialize(JsonObject object, JsonSerializationContext context);

	public static class Serializer implements JsonSerializer<LootEntry>, JsonDeserializer<LootEntry> {
		public Serializer() {}

		public LootEntry deserialize(JsonElement element, Type type, JsonDeserializationContext context) {
			JsonObject object = JsonUtils.getJsonObject(element, "loot item");
			String s = JsonUtils.getString(object, "type");
			int w = JsonUtils.getInt(object, "weight", 1);
			int q = JsonUtils.getInt(object, "quality", 0);
			LootCondition[] conditions;
			if(object.has("conditions")) {
				conditions = JsonUtils.deserializeClass(object, "conditions", context, LootCondition[].class);
			} else {
				conditions = new LootCondition[0];
			}
			if(s.equals("item")) {
				return LootEntryItem.deserialize(object, context, w,q, Arrays.asList(conditions));
			} else if(s.equals("loot_table")) {
				return LootEntryTable.deserialize(object, context, w, q, Arrays.asList(conditions));
			} else if(s.equals("empty")) {
				return  LootEntryEmpty.deserialize(object, context, w, q, Arrays.asList(conditions));
			} else {
				throw new JsonSyntaxException("Unknown loot entry type \'" + s + "\'");
			}
		}

		public JsonElement serialize(LootEntry entry, Type type, JsonSerializationContext context) {
			JsonObject object = new JsonObject();
			object.addProperty("weight", entry.weight);
			object.addProperty("quality", entry.quality);
			if(entry.conditions.size() > 0) {
				object.add("conditons", context.serialize(entry.conditions));
			}

            if(entry instanceof LootEntryItem) {
                object.addProperty("type", "item");
            } else if(entry instanceof LootEntryTable) {
                object.addProperty("type", "loot_table");
            } else {
               if(!(entry instanceof LootEntryEmpty)) {
                   throw new IllegalArgumentException("Unknown loot entry type \'" + entry + "\'");
               }
                object.addProperty("type", "empty");
            }

            entry.serialize(object, context);
			return object;
		}
	}

}
