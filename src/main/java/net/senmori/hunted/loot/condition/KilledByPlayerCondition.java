package net.senmori.hunted.loot.condition;

import com.google.gson.JsonObject;



/**
 * Condition that checks if the entity who triggered this condition is a player
 */
public class KilledByPlayerCondition extends LootCondition  {

	private boolean inverse;
	
	/**
	 * Condition that checks if the entity who triggered this condition is a player
	 */
	public KilledByPlayerCondition() {
		inverse = false;
    }
	
	/**
	 * Set's inverse to 'true', so this condition will only succeed if the killer is <b>NOT</b> a player
	 * @param value
	 */
	public KilledByPlayerCondition setInverse(boolean value) {
		inverse = value;
		return this;
	}

	@Override
    public JsonObject toJsonObject() {
		JsonObject condition = new JsonObject();
		condition.addProperty("condition", "killed_by_player");
		condition.addProperty("inverse", inverse);
	    return condition;
    }

	@Override
    public LootConditionType getType() {
	    return LootConditionType.KILLED_BY_PLAYER;
    }

	@Override
    public LootCondition fromJsonObject(JsonObject condition) {
		inverse = condition.get("inverse").getAsBoolean();
	    return this;
    }

}
