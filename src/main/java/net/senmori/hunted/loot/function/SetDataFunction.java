package net.senmori.hunted.loot.function;


import java.util.HashMap;
import java.util.Map;

import net.senmori.hunted.loot.condition.LootCondition;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Sets the item's data value.</br>
 * @author Senmori
 */
public class SetDataFunction extends LootFunction {
	
	private Map<String,Integer> dataValues;
	
	/**
	 * Sets the item's data value.
	 */
	public SetDataFunction() {
	    dataValues = new HashMap<>();
    }
	
	/* ####################
	 * ItemStack methods
	 * ####################
	 */
	@Override
    public ItemStack applyTo(ItemStack applyTo) {
	    return applyTo;
    }
	
	/* ########################
	 * Property methods
	 * ########################
	 */
	/**
	 * Set this item's data value exactly.
	 * @param data
	 * @return
	 */
	public SetDataFunction setDataValue(int exact) {
		dataValues.clear();
		dataValues.put("exact", exact);
		return this;
	}
	
	/**
	 * Choose a random data value between {@link #min} and {@link #max}</br>
	 * @param min
	 * @param max
	 * @return
	 */
	public SetDataFunction setDataValue(int min, int max) {
		dataValues.clear();
		dataValues.put("min", min);
		dataValues.put("max", max);
		return this;
	}
	
	
	/* ###########################
	 * Load/Save methods
	 * ###########################
	 */
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "set_data");
		
		if(!useExactOnly()) {
			JsonObject datas = new JsonObject();
			datas.addProperty("min", dataValues.get("min"));
			datas.addProperty("max", dataValues.get("max"));
			function.add("data", datas);
		} else {
			function.addProperty("data", dataValues.get("exact"));
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
		if(element.get("data").isJsonPrimitive()) {
			setDataValue(element.get("data").getAsInt(), element.get("data").getAsInt());
		} else if(element.get("data").getAsJsonPrimitive().isNumber()) {
			setDataValue(element.get("data").getAsInt());
		}
		
	    // check for conditions
	    if(element.get("conditions").isJsonArray()) { // we have conditions!
	    	loadConditions(element.get("conditions").getAsJsonArray());
	    }
	    return this;
    }
	/* #####################
	 * Getters
	 * #####################
	 */
	
	public boolean useExactOnly() {
		return dataValues.containsKey("exact");
	}
	
	@Override
    public LootFunctionType getType() {
	    return LootFunctionType.SET_DATA;
    }



}
