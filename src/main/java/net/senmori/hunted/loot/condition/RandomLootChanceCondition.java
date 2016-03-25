package net.senmori.hunted.loot.condition;

import org.bukkit.enchantments.Enchantment;

import com.google.gson.JsonObject;

/**
 * Condition that tests a number between 0.0 - 1.0 is less than the specified {@link #chance}. </br>
 * This is affected by the {@link Enchantment#LOOT_BONUS_MOBS} level on the killing entity.
 */
public class RandomLootChanceCondition extends LootCondition  {
	
	/** The chance this condition will succeed */
	private double chance;
	/** Looting adjustment to the {@link #chance} */
	private double lootMultiplier;
	/**
	 * Condition that tests a number between 0.0 - 1.0 is less than the specified {@link #chance}. </br>
	 * This is affected by the {@link Enchantment#LOOT_BONUS_MOBS} level on the killing entity.
	 */
	public RandomLootChanceCondition() {
    }
	
	/**
	 * Sets the random chance, valid values are from 0.0 - 1.0
	 * @param chance - Base success rate
	 * @param lootingMultiplier - Looting adjustment to base success rate.</br><i>Formula: chance + looting_level * looting_mulitplier</i>
	 */
	public RandomLootChanceCondition setChances(double chance, double lootingMultiplier) {
		this.chance = chance;
		this.lootMultiplier = lootingMultiplier;
		return this;
	}

	@Override
    public JsonObject toJsonObject() {
		JsonObject condition = new JsonObject();
		condition.addProperty("condition", "random_chance_with_looting");
		condition.addProperty("chance", chance);
		condition.addProperty("looting_multiplier", lootMultiplier);
	    return condition;
    }

	@Override
    public LootConditionType getType() {
	    return LootConditionType.RANDOM_CHANCE_WITH_LOOTING;
    }

	@Override
    public LootCondition fromJsonObject(JsonObject condition) {
		chance = condition.get("chance").getAsDouble();
		lootMultiplier = condition.get("looting_multiplier").getAsDouble();
	    return null;
    }

}
