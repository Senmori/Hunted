package net.senmori.hunted.loottable.function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	public JSONObject toJSONObject() {
		JSONObject function = new JSONObject();
		function.put("function", getType().getName());
		function.put("tag", tag);
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
