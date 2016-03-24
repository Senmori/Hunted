package net.senmori.hunted.loottable.function;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.senmori.hunted.loottable.condition.LootCondition;

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
	    super(LootFunctionType.SET_NBT);
	    tag = "";
    }
	
	public SetNBTFunction setTag(String tag) {
		this.tag = tag;
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", getType().getName());
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

}
