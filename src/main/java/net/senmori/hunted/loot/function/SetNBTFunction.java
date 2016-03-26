package net.senmori.hunted.loot.function;


import net.senmori.hunted.loot.condition.LootCondition;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
	
	/* #####################
	 * ItemStack methods
	 * ######################
	 */
	@Override
    public ItemStack applyTo(ItemStack applyTo) {
	    return applyTo;
    }
	
	
	
	/* ###########################
	 * Property methods
	 * ###########################
	 */
	public String getTag(){
		return tag;
	}
	
	/* #########################
	 * Load/Save methods
	 * #########################
	 */

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
    public LootFunction fromJsonObject(JsonObject element) {
		tag = element.get("tag").getAsString();
	    // check for conditions
	    if(element.get("conditions").isJsonArray()) { // we have conditions!
	    	loadConditions(element.get("conditions").getAsJsonArray());
	    }
	    return this;
    }
	
	/* ###############################
	 * Getters
	 * ###############################
	 */
	@Override
    public LootFunctionType getType() {
	    return LootFunctionType.SET_NBT;
    }
}
