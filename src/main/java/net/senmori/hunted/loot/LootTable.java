package net.senmori.hunted.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.adapter.InheritanceAdapter;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LootTable {

	private List<LootPool> pools;
	
	public LootTable(List<LootPool> pools) {
		this.pools = pools;
	}

	public LootTable() {
		this.pools = new ArrayList<>();
	}

	/** Add another LootPool to this LootTable */
	public void addLootPool(LootPool pool) {
		if (pools == null) pools = new ArrayList<>();
		pools.add(pool);
	}

	public List<LootPool> getPools() { return pools; }

	public static LootTable emptyLootTable() { return new LootTable(new ArrayList<LootPool>()); }


	public boolean save() {
		return LootTableManager.save(this);
	}

	public static class Serializer extends InheritanceAdapter<LootTable> {

		public Serializer() {}

		public LootTable deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
			Bukkit.broadcastMessage("Deserializing loot table");
			JsonObject jsonObject = JsonUtils.getJsonObject(json, "loot table");
			LootPool[] lootPools = JsonUtils.deserializeClass(jsonObject, "pools", new LootPool[0], context, LootPool[].class);
			return new LootTable(Arrays.asList(lootPools));
		}

		public JsonElement serialize(LootTable table, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.add("pools", context.serialize(table.getPools()));
			return jsonObject;
		}
	}
}
