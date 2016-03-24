package net.senmori.hunted.loottable.function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {
		JSONObject function = new JSONObject();
		function.put("function", getType().getName());
		
		if(useData) {
			JSONObject dataValues = new JSONObject();
			dataValues.put("min", min);
			dataValues.put("max", max);
			function.put("data", dataValues);
		} else {
			function.put("data", data);
		}
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
