package net.senmori.hunted.loottable.condition;

import org.json.simple.JSONObject;

/**
 * Condition that checks if the entity who triggered this condition is a player
 */
public class KilledByPlayerCondition extends LootCondition  {

	private boolean inverse;
	
	/**
	 * Condition that checks if the entity who triggered this condition is a player
	 */
	public KilledByPlayerCondition() {
		super(LootConditionType.KILLED_BY_PLAYER);
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
    public JSONObject toJSONObject() {
		JSONObject condition = new JSONObject();
		condition.put("condition", getType().getName());
		condition.put("inverse", inverse);
	    return condition;
    }

}
