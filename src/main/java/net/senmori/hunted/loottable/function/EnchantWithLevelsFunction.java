package net.senmori.hunted.loottable.function;


import net.senmori.hunted.loottable.condition.LootCondition;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	public JSONObject toJSONObject() {
		JSONObject function = new JSONObject();
		function.put("function", getType().getName());
		function.put("treasure", treasure);
		function.put("levels", levels);
		if(useLevels) {
			JSONObject levelObject = new JSONObject();
			levelObject.put("min", minLevel);
			levelObject.put("max", maxLevel);
			function.put("levels", levelObject);
		}
		// add conditions
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
