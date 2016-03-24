package net.senmori.hunted.loottable.function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.senmori.hunted.loottable.condition.LootCondition;

/**
 * Sets the stack size of the associated item</br>
 * @author Senmori
 */
public class SetCountFunction extends LootFunction {
	
	private int count, min, max;
	private boolean useCount;
	/**
	 * Sets the stack size of the associated item
	 */
	public SetCountFunction() {
	    super(LootFunctionType.SET_COUNT);
	    count = 1;
	    useCount = false;
    }
	
	/** Sets stack size exactly */
	public SetCountFunction setCount(int num) {
		this.count = num;
		return this;
	}
	
	/** Function will choose between {@link #min} and {@link #max} for the stack size of this item */
	public SetCountFunction setCount(int min, int max) {
		this.min = min;
		this.max = max;
		useCount = true;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public JSONObject toJSONObject() {
		JSONObject function = new JSONObject();
		function.put("function", getType().getName());
		
		if(useCount) {
			JSONObject counts = new JSONObject();
			counts.put("min", min);
			counts.put("max", max);
			function.put("count", counts);
		} else {
			function.put("count", count);
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
