package net.senmori.hunted.loot.function;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.utils.LootUtil;

/**
 * Sets the stack size of the associated item</br>
 * @author Senmori
 */
public class SetCountFunction extends LootFunction {
	
	private Map<String,Integer> counts;
	/**
	 * Sets the stack size of the associated item
	 */
	public SetCountFunction() {
	    counts = new HashMap<>();
    }
	
	/** Sets stack size exactly */
	public SetCountFunction setCount(int exact) {
		counts.clear();
		counts.put("exact", exact);
		return this;
	}
	
	/** Function will choose between {@link #min} and {@link #max} for the stack size of this item */
	public SetCountFunction setCount(int min, int max) {
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
		function.addProperty("function", "set_count");
		
		if(!useExactOnly()) {
			JsonObject nums = new JsonObject();
			nums.addProperty("min", counts.get("min"));
			nums.addProperty("max", counts.get("max"));
			function.add("count", nums);
		} else {
			function.addProperty("count", counts.get("exact"));
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
	    return LootFunctionType.SET_COUNT;
    }

	@Override
    public LootFunction fromJsonObject(JsonElement element) {
		JsonObject function = element.getAsJsonObject();
		if(function.get("count").isJsonObject()) {
			setCount(function.get("count").getAsInt(), function.get("count").getAsInt());
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
