package net.senmori.hunted.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.senmori.hunted.loot.utils.JsonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LootTable {

	private List<LootPool> pools;
	
	public LootTable(List<LootPool> pools) {
		this.pools = pools;
	}

	/** Add another LootPool to this LootTable */
	public void addLootPool(LootPool pool) { pools.add(pool); }

	public List<LootPool> getPools() { return pools; }


	public static LootTable getEmptyLootTable() { return new LootTable(new ArrayList<LootPool>()); }
	public static class Serializer implements JsonDeserializer<LootTable>, JsonSerializer<LootTable> {

		public Serializer() {}

		public LootTable deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
			JsonObject jsonObject = JsonUtils.getJsonObject(json, "loot table");
			LootPool[] lootPools = JsonUtils.deserializeClass(jsonObject, "pools", new LootPool[0], context, LootPool[].class);
			return new LootTable(Arrays.asList(lootPools));
		}

		public JsonElement serialize(LootTable table, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.add("pools", context.serialize(table.pools));
			return jsonObject;
		}
	}
}
