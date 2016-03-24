package net.senmori.hunted.loottable.condition;

import org.json.simple.JSONObject;

/**
 * Condition that test a random number 0.0-1.0 is less than {@link #chance}. </br>
 */
public class RandomChanceCondition extends LootCondition  {
	
	/** Base success rate of this condition */
	private double chance;
	
	/**
	 * Condition that test a random number 0.0-1.0 is less than {@link #chance}. </br>
	 */
	public RandomChanceCondition() {
		super(LootConditionType.RANDOM_CHANCE);
    }
	
	/**
	 * Sets the base success rate of this condition succeeding</br>
	 * Valid values are from 0.0 - 1.0
	 * @param chance - Base success rate
	 */
	public RandomChanceCondition setChance(double chance) {
		this.chance = chance;
		return this;
	}
	
	@Override
    public JSONObject toJSONObject() {
		JSONObject condition = new JSONObject();
		condition.put("condition", getType().getName());
		condition.put("chance", chance);
	    return condition;
    }

}
