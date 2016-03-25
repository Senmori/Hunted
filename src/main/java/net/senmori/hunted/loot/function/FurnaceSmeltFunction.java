package net.senmori.hunted.loot.function;

import net.senmori.hunted.loot.condition.EntityPropertiesCondition;
import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.utils.LootUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Smelts the item as it would be in a furnace. Used in combination with {@link EntityPropertiesCondition} to cook food from animals on death</br>
 * @author Senmori
 */
public class FurnaceSmeltFunction extends LootFunction {
	
	/**
	 * Smelts the item as it would be in a furnace. Used in combination with {@link EntityPropertiesCondition} to cook food from animals on death
	 */
	public FurnaceSmeltFunction() {}


	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "furnace_smelt");
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
	    return LootFunctionType.FURNACE_SMELT;
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
