package net.senmori.hunted.loottable.function;

import net.senmori.hunted.loottable.condition.EntityPropertiesCondition;
import net.senmori.hunted.loottable.condition.LootCondition;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	public JSONObject toJSONObject() {
		JSONObject function = new JSONObject();
		function.put("function", getType().getName());
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
