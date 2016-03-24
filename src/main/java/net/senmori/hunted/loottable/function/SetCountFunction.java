package net.senmori.hunted.loottable.function;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
	
	@Override
    public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", getType().getName());
		
		if(useCount) {
			JsonObject counts = new JsonObject();
			counts.addProperty("min", min);
			counts.addProperty("max", max);
			function.add("count", counts);
		} else {
			function.addProperty("count", count);
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
