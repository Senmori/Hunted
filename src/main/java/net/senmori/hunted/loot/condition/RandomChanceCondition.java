package net.senmori.hunted.loot.condition;

import com.google.gson.JsonObject;



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
    public JsonObject toJsonObject() {
		JsonObject condition = new JsonObject();
		condition.addProperty("condition", "random_chance");
		condition.addProperty("chance", chance);
	    return condition;
    }

	@Override
    public LootConditionType getType() {
	    return LootConditionType.RANDOM_CHANCE;
    }

	@Override
    public LootCondition fromJsonObject(JsonObject condition) {
	    chance = condition.get("chance").getAsDouble();
		return this;
    }

}
