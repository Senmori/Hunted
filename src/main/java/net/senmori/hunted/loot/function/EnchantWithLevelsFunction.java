package net.senmori.hunted.loot.function;


import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.utils.LootUtil;

/**
 * Enchants the associated item with the specified enchantment level, as if from an enchantment table</br>
 * @author Senmori
 */
public class EnchantWithLevelsFunction extends LootFunction {
	
	private boolean treasure;
	private Map<String,Integer> levels;
	/**
	 * Enchants the associated item with the specified enchantment level, as if from an enchantment table</br>
	 */
	public EnchantWithLevelsFunction() {
		levels = new HashMap<>();
		treasure = false;
	}
	
	/**
	 * Determines if treasure enchantments are possible.
	 * @param value
	 * @return
	 */
	public EnchantWithLevelsFunction setTreasurePossible(boolean value) {
		treasure = value;
		return this;
	}
	
	/**
	 * Sets what level to enchant the itemstack with
	 * @param level
	 * @return
	 */
	public EnchantWithLevelsFunction setExactLevel(int level) {
		levels.clear();
		levels.put("exact", level);
		return this;
	}
	
	public boolean useExactOnly() {
		return levels.containsKey("exact");
	}
	
	/**
	 * Sets the minimum and maximum level this function will choose from when enchanting this item.</br>
	 * @param minLevel
	 * @param maxLevel
	 * @return
	 */
	public EnchantWithLevelsFunction setLevels(int min, int max) {
		levels.clear();
		levels.put("min", min);
		levels.put("max", max);
		return this;
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "enchant_with_levels");
		function.addProperty("treasure", treasure);
		if(!useExactOnly()) {
			JsonObject levelObject = new JsonObject();
			levelObject.addProperty("min", levels.get("min"));
			levelObject.addProperty("max", levels.get("max"));
			function.add("levels", levelObject);
		} else {
			function.addProperty("levels", levels.get("exact"));
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
	    return LootFunctionType.ENCHANT_WITH_LEVELS;
    }

	@Override
    public LootFunction fromJsonObject(JsonElement element) {
		JsonObject function = element.getAsJsonObject();
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
