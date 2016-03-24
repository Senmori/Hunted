package net.senmori.hunted.loottable.function;

import net.senmori.hunted.loottable.condition.LootCondition;

import org.bukkit.enchantments.Enchantment;
import org.json.simple.JSONArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
	
	
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", getType().getName());
		if(useCount) {
			JsonObject counts = new JsonObject();
			counts.addProperty("min", min);
			counts.addProperty("max", max);
			function.add("count", counts);
		} else {
			function.addProperty("count", count);
		}
		// add conditions
		if(conditions.size() > 0) {
			JsonArray conditionsArray = new JsonArray();
			for(LootCondition lc : conditions) {
				conditionsArray.add(lc.toJsonObject());
			}
			function.add("conditions", conditionsArray);
		}
		return function;
	}

}
