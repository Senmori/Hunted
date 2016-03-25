package net.senmori.hunted.loot.function;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.utils.LootUtil;

/**
 * Adds NBT data to an item</br>
 * @author Senmori
 */
public class SetNBTFunction extends LootFunction {
	
	private String tag;
	
	/**
	 * Adds NBT data to an item</br>
	 * I will eventually add helper methods to make things easier.
	 */
	public SetNBTFunction() {
	    tag = "{}";
    }
	
	public SetNBTFunction setTag(String tag) {
		this.tag = tag;
		return this;
	}
	
	public String getTag(){
		return tag;
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "set_nbt");
		function.addProperty("tag", tag);
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
	    return LootFunctionType.SET_NBT;
    }

	@Override
    public LootFunction fromJsonObject(JsonElement element) {
		JsonObject function = element.getAsJsonObject();
		tag = function.get("tag").getAsString();
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
