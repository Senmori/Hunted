package net.senmori.hunted.loot.function;


import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.utils.LootUtil;

/**
 * Sets the item's damage value(durability) for tools</br>
 * @author Senmori
 */
public class SetDamageFunction extends LootFunction {

	private Map<String,Double> damageValues;
	
	/**
	 * Sets the item's damage value(durability) for tools</br>
	 */
	public SetDamageFunction() {
	    damageValues = new HashMap<>();
	    damageValues.put("exact", 1.0);
    }
	
	/**
	 * Set's the items damage fraction.</br>
	 * <i>Note: (1.0 is undamaged, 0.0 is zero durability left)</i>
	 * @param exact
	 * @return
	 */
	public SetDamageFunction setDamage(double exact) {
		damageValues.clear();
		damageValues.put("exact", exact);
		return this;
	}
	
	/**
	 * Specifies a random damage fraction between {@link #min} and {@link #max}</br>
	 * <i>Note: (1.0 is undamaged, 0.0 is zero durability left)</i>
	 * @param min
	 * @param max
	 * @return
	 */
	public SetDamageFunction setDamage(double min, double max) {
		damageValues.clear();
		damageValues.put("min", min);
		damageValues.put("max", max);
		return this;
	}
	
	public boolean useExactOnly() {
		return damageValues.containsKey("exact");
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "set_damage");
		
		if(!useExactOnly()) {
			JsonObject damages = new JsonObject();
			damages.addProperty("min", damageValues.get("min"));
			damages.addProperty("max", damageValues.get("max"));
			function.add("damage", damages);
		} else {
			function.addProperty("damage", damageValues.get("exact"));
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
	    return LootFunctionType.SET_DAMAGE;
    }

	@Override
    public LootFunction fromJsonObject(JsonElement element) {
		JsonObject function = element.getAsJsonObject();
	    if(function.get("damage").isJsonObject()) {
	    	setDamage(function.get("damage").getAsDouble(), function.get("damage").getAsDouble());
	    } else if(function.get("damage").getAsJsonPrimitive().isNumber()) {
	    	setDamage(function.get("damage").getAsDouble());
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
