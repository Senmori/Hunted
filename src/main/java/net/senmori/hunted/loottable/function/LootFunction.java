package net.senmori.hunted.loottable.function;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.loottable.condition.LootCondition;

import com.google.gson.JsonObject;

public abstract class LootFunction {
	
	private LootFunctionType type;
	public List<LootCondition> conditions;
	protected LootFunction(LootFunctionType type) {
		this.type = type;
		conditions = new ArrayList<>();
	}

	public abstract JsonObject toJsonObject();
	
	public void addCondition(LootCondition lc) {
		conditions.add(lc);
	}
	
	public List<LootCondition> getConditions() {
		return conditions;
	}
	
	public LootFunctionType getType() {
		return type;
	}
}
