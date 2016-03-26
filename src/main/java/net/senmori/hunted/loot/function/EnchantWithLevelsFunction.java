package net.senmori.hunted.loot.function;


import java.util.HashMap;
import java.util.Map;

import net.senmori.hunted.loot.condition.LootCondition;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Enchants the associated item with the specified enchantment level, as if from an enchantment table</br>
 * @author Senmori
 */
public class EnchantWithLevelsFunction extends LootFunction {
	
	private boolean treasure;
	private Map<String,Integer> levels;
	/**
	 * Enchants the associated item with the specified enchantment level, as if from an enchantment table</br>
	 */
	public EnchantWithLevelsFunction() {
		levels = new HashMap<>();
		treasure = false;
	}
	
	/* ############################
	 * ItemStack methods
	 * ############################
	 */
	@Override
    public ItemStack applyTo(ItemStack applyTo) {
	    return applyTo;
    }
	
	/* #########################
	 * Property methods
	 * #########################
	 */
	/**
	 * Sets what level to enchant the itemstack with
	 * @param level
	 * @return
	 */
	public EnchantWithLevelsFunction setLevels(int level) {
		levels.clear();
		levels.put("exact", level);
		return this;
	}
	
	/**
	 * Sets the minimum and maximum level this function will choose from when enchanting this item.</br>
	 * @param minLevel
	 * @param maxLevel
	 * @return
	 */
	public EnchantWithLevelsFunction setLevels(int min, int max) {
		levels.clear();
		levels.put("min", min);
		levels.put("max", max);
		return this;
	}
	
	/**
	 * Determines if treasure enchantments are possible.
	 * @param value
	 * @return
	 */
	public EnchantWithLevelsFunction setTreasurePossible(boolean value) {
		treasure = value;
		return this;
	}
	
	/* ##############################
	 * Load/Save methods
	 * ##############################
	 */
	
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "enchant_with_levels");
		function.addProperty("treasure", treasure);
		if(!useExactOnly()) {
			JsonObject levelObject = new JsonObject();
			levelObject.addProperty("min", levels.get("min"));
			levelObject.addProperty("max", levels.get("max"));
			function.add("levels", levelObject);
		} else {
			function.addProperty("levels", levels.get("exact"));
		}
		// add conditions
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
	    // check for conditions
	    if(element.get("conditions").isJsonArray()) { // we have conditions!
	    	loadConditions(element.get("conditions").getAsJsonArray());
	    }
	    return this;
    }
	
	/* #######################
	 * Getters
	 * #######################
	 */
	public boolean useExactOnly() {
		return levels.containsKey("exact");
	}
	
	@Override
    public LootFunctionType getType() {
	    return LootFunctionType.ENCHANT_WITH_LEVELS;
    }
}
