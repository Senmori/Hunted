package net.senmori.hunted.loottable.function;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.senmori.hunted.loottable.condition.LootCondition;

/**
 * Sets the item's data value.</br>
 * @author Senmori
 */
public class SetDataFunction extends LootFunction {
	
	private int data, min, max;
	private boolean useData;
	
	/**
	 * Sets the item's data value.
	 */
	public SetDataFunction() {
	    super(LootFunctionType.SET_DATA);
	    data = 0;
	    useData = false;
    }
	
	/**
	 * Set this item's data value exactly.
	 * @param data
	 * @return
	 */
	public SetDataFunction setDataValue(int data) {
		this.data = data;
		return this;
	}
	
	/**
	 * Choose a random data value between {@link #min} and {@link #max}</br>
	 * @param min
	 * @param max
	 * @return
	 */
	public SetDataFunction setDataValues(int min, int max) {
		this.min = min;
		this.max = max;
		useData = true;
		return this;
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", getType().getName());
		
		if(useData) {
			JsonObject dataValues = new JsonObject();
			dataValues.addProperty("min", min);
			dataValues.addProperty("max", max);
			function.add("data", dataValues);
		} else {
			function.addProperty("data", data);
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

}
