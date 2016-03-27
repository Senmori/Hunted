package net.senmori.hunted.loot.function;

import java.util.HashMap;
import java.util.Map;

import net.senmori.hunted.loot.condition.LootCondition;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
	
	/* ###########################
	 * ItemStack methods
	 * # ##########################
	 */
	
	@Override
    public ItemStack applyTo(ItemStack applyTo) {
	    return applyTo;
    }
	
	
	/* ##########################
	 * Property methods
	 * ##########################
	 */
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
	
	/* ###################
	 * Load/Save methods
	 * ###################
	 */
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
    public LootFunction fromJsonObject(JsonObject element) {
		if(element.get("count").isJsonPrimitive()) {
			setCount(element.get("count").getAsInt());
		}else {
			JsonObject count = element.get("count").getAsJsonObject();
			setCount(count.get("min").getAsInt(), count.get("max").getAsInt());
		}
		
	    // check for conditions
	    //loadConditions(element.get("conditions").getAsJsonArray());
	    return this;
    }
	
	
	/* ##################
	 * Getters
	 * ###################
	 */
	public boolean useExactOnly() {
		return counts.containsKey("exact");
	}
	
	@Override
    public LootFunctionType getType() {
	    return LootFunctionType.SET_COUNT;
    }

}
