package net.senmori.hunted.loot.condition;

import com.google.gson.JsonObject;



public abstract class LootCondition {
	
	protected LootCondition() {}
	
	public abstract JsonObject toJsonObject();
	public abstract LootCondition fromJsonObject(JsonObject condition);
	public abstract LootConditionType getType();
}
