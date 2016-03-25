package net.senmori.hunted.loot.function;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.loot.condition.LootCondition;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class LootFunction {
	
	public List<LootCondition> conditions;
	protected LootFunction() {
		conditions = new ArrayList<>();
	}

	public abstract JsonObject toJsonObject();
	public abstract LootFunction fromJsonObject(JsonElement element);
	public abstract LootFunctionType getType();
	
	public void addCondition(LootCondition lc) {
		conditions.add(lc);
	}
	
	public List<LootCondition> getConditions() {
		return conditions;
	}
}
