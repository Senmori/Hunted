package net.senmori.hunted.loottable.function;

import net.senmori.hunted.loottable.condition.EntityPropertiesCondition;
import net.senmori.hunted.loottable.condition.LootCondition;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Smelts the item as it would be in a furnace. Used in combination with {@link EntityPropertiesCondition} to cook food from animals on death</br>
 * @author Senmori
 */
public class FurnaceSmeltFunction extends LootFunction {
	
	/**
	 * Smelts the item as it would be in a furnace. Used in combination with {@link EntityPropertiesCondition} to cook food from animals on death
	 */
	public FurnaceSmeltFunction() {
		super(LootFunctionType.FURNACE_SMELT);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", getType().getName());
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
