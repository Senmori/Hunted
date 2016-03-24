package net.senmori.hunted.loottable.condition;

import org.bukkit.enchantments.Enchantment;
import org.json.simple.JSONObject;

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
		super(LootConditionType.RANDOM_CHANCE_WITH_LOOTING);
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
    public JSONObject toJSONObject() {
		JSONObject condition = new JSONObject();
		condition.put("condition", getType().getName());
		condition.put("chance", chance);
		condition.put("looting_multiplier", lootMultiplier);
	    return condition;
    }

}
