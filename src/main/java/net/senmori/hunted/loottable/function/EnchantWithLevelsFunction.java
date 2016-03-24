package net.senmori.hunted.loottable.function;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.senmori.hunted.loottable.condition.LootCondition;

/**
 * Enchants the associated item with the specified enchantment level, as if from an enchantment table</br>
 * @author Senmori
 */
public class EnchantWithLevelsFunction extends LootFunction {
	
	private boolean treasure;
	private int levels, minLevel, maxLevel;
	private boolean useLevels;
	/**
	 * Enchants the associated item with the specified enchantment level, as if from an enchantment table</br>
	 */
	public EnchantWithLevelsFunction() {
		super(LootFunctionType.ENCHANT_WITH_LEVELS);
		levels = 1;
		treasure = false;
		useLevels = false;
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
	
	/**
	 * Sets what level to enchant the itemstack with
	 * @param level
	 * @return
	 */
	public EnchantWithLevelsFunction setLevelExact(int level) {
		levels = level;
		return this;
	}
	
	/**
	 * Sets the minimum and maximum level this function will choose from when enchanting this item.</br>
	 * @param minLevel
	 * @param maxLevel
	 * @return
	 */
	public EnchantWithLevelsFunction setLevels(int minLevel, int maxLevel) {
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		useLevels = true;
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", getType().getName());
		function.addProperty("treasure", treasure);
		function.addProperty("levels", levels);
		if(useLevels) {
			JsonObject levelObject = new JsonObject();
			levelObject.addProperty("min", minLevel);
			levelObject.addProperty("max", maxLevel);
			function.add("levels", levelObject);
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

}
