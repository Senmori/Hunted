package net.senmori.hunted.loot.function;

import java.util.HashMap;
import java.util.Map;

import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.utils.LootUtil;

import org.bukkit.enchantments.Enchantment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Sets how many items extra you will receive per level of {@link Enchantment#LOOT_BONUS_MOBS}.</br>
 * Counts will be merged. Plan accordingly.</br>
 * @author Senmori
 */
public class LootingEnchantFunction extends LootFunction {
	
	private Map<String,Integer> counts;
	/**
	 * Sets how many items extra you will receive per level of {@link Enchantment#LOOT_BONUS_MOBS}.</br>
	 * Counts will be merged. Plan accordingly.
	 */
	public LootingEnchantFunction() {
		counts = new HashMap<>();
	}
	
	public LootingEnchantFunction setCount(int count) {
		counts.clear();
		counts.put("exact", count);
		return this;
	}
	
	public LootingEnchantFunction setCount(int min, int max) {
		counts.clear();
		counts.put("min", min);
		counts.put("max", max);
		return this;
	}
	
	public boolean useExactOnly() {
		return counts.containsKey("exact");
	}
	
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "looting_enchant");
		if(!useExactOnly()) {
			JsonObject counts = new JsonObject();
			counts.add("min", counts.get("min"));
			counts.add("max", counts.get("max"));
			function.add("count", counts);
		} else {
			function.addProperty("count", counts.get("exact"));
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

	@Override
    public LootFunctionType getType() {
	    return LootFunctionType.LOOTING_ENCHANT;
    }

	@Override
    public LootFunction fromJsonObject(JsonElement element) {
		JsonObject function = element.getAsJsonObject();
		if(function.get("count").isJsonObject()) {
			JsonObject counts = function.get("count").getAsJsonObject();
			setCount(counts.get("min").getAsInt(), counts.get("max").getAsInt());
		} else if(function.get("count").getAsJsonPrimitive().isNumber()) {
			setCount(function.get("count").getAsInt());
		}
		
	    // check for conditions
	    if(function.get("conditions").isJsonArray()) { // we have conditions!
	    	JsonArray conditions = function.get("conditions").getAsJsonArray();
	    	while(conditions.iterator().hasNext()) {
	    		JsonObject next = conditions.iterator().next().getAsJsonObject();
	    		addCondition(LootUtil.getCondition(next.get("type").getAsString())); // get the correct instance of LootFunction
	    		if(!conditions.iterator().hasNext()) break;
	    	}
	    }
	    return this;
    }

}
