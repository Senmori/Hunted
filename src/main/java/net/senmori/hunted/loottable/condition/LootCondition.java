package net.senmori.hunted.loottable.condition;

import org.json.simple.JSONObject;

public abstract class LootCondition {
	
	private LootConditionType type;
	protected LootCondition(LootConditionType type) {
		this.type = type;
	}
	
	
	public abstract JSONObject toJSONObject();
	
	public LootConditionType getType() {
		return type;
	}
}
