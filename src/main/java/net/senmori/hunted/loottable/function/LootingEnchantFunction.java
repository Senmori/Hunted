package net.senmori.hunted.loottable.function;

import net.senmori.hunted.loottable.condition.LootCondition;

import org.bukkit.enchantments.Enchantment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Sets how many items extra you will receive per level of {@link Enchantment#LOOT_BONUS_MOBS}.</br>
 * Counts will be merged. Plan accordingly.</br>
 * @author Senmori
 */
public class LootingEnchantFunction extends LootFunction {
	
	private int count, min, max;
	private boolean useCount;
	/**
	 * Sets how many items extra you will receive per level of {@link Enchantment#LOOT_BONUS_MOBS}.</br>
	 * Counts will be merged. Plan accordingly.
	 */
	public LootingEnchantFunction() {
		super(LootFunctionType.LOOTING_ENCHANT);
		count = 1;
		useCount = false;
	}
	
	public LootingEnchantFunction setCount(int count) {
		this.count = count;
		return this;
	}
	
	public LootingEnchantFunction setCount(int min, int max) {
		this.min = min;
		this.max = max;
		useCount = true;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {
		JSONObject function = new JSONObject();
		function.put("function", getType().getName());
		if(useCount) {
			JSONObject counts = new JSONObject();
			counts.put("min", min);
			counts.put("max", max);
			function.put("count", counts);
		} else {
			function.put("count", count);
		}
		// add conditions
		if(conditions.size() > 0) {
			JSONArray conditionsArray = new JSONArray();
			for(LootCondition lc : conditions) {
				conditionsArray.add(lc.toJSONObject());
			}
			function.put("conditions", conditionsArray);
		}
		return function;
	}

}
