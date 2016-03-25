package net.senmori.hunted.loot.function;


import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.utils.LootUtil;

/**
 * Sets the item's data value.</br>
 * @author Senmori
 */
public class SetDataFunction extends LootFunction {
	
	private Map<String,Integer> dataValues;
	
	/**
	 * Sets the item's data value.
	 */
	public SetDataFunction() {
	    dataValues = new HashMap<>();
    }
	
	/**
	 * Set this item's data value exactly.
	 * @param data
	 * @return
	 */
	public SetDataFunction setDataValue(int exact) {
		dataValues.clear();
		dataValues.put("exact", exact);
		return this;
	}
	
	/**
	 * Choose a random data value between {@link #min} and {@link #max}</br>
	 * @param min
	 * @param max
	 * @return
	 */
	public SetDataFunction setDataValue(int min, int max) {
		dataValues.clear();
		dataValues.put("min", min);
		dataValues.put("max", max);
		return this;
	}
	
	public boolean useExactOnly() {
		return dataValues.containsKey("exact");
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "set_data");
		
		if(!useExactOnly()) {
			JsonObject datas = new JsonObject();
			datas.addProperty("min", dataValues.get("min"));
			datas.addProperty("max", dataValues.get("max"));
			function.add("data", datas);
		} else {
			function.addProperty("data", dataValues.get("exact"));
		}
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
	    return LootFunctionType.SET_DATA;
    }

	@Override
    public LootFunction fromJsonObject(JsonElement element) {
		JsonObject function = element.getAsJsonObject();
		if(function.get("data").isJsonObject()) {
			setDataValue(function.get("data").getAsInt(), function.get("data").getAsInt());
		} else if(function.get("data").getAsJsonPrimitive().isNumber()) {
			setDataValue(function.get("data").getAsInt());
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
