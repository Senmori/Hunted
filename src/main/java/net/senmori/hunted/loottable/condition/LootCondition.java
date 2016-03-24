package net.senmori.hunted.loottable.condition;

import com.google.gson.JsonObject;



public abstract class LootCondition {
	
	private LootConditionType type;
	protected LootCondition(LootConditionType type) {
		this.type = type;
	}
	
	
	public abstract JsonObject toJsonObject();
	
	public LootConditionType getType() {
		return type;
	}
}
