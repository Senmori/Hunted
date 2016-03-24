package net.senmori.hunted.loottable.function;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.senmori.hunted.loottable.condition.LootCondition;

/**
 * Sets the item's damage value(durability) for tools</br>
 * @author Senmori
 */
public class SetDamageFunction extends LootFunction {

	private double damage, min, max;
	private boolean useDamage;
	
	/**
	 * Sets the item's damage value(durability) for tools</br>
	 */
	public SetDamageFunction() {
	    super(LootFunctionType.SET_DAMAGE);
	    damage = 1.0f; // undamaged by default?
	    useDamage = false;
    }
	
	/**
	 * Set's the items damage fraction.</br>
	 * <i>Note: (1.0 is undamaged, 0.0 is zero durability left)</i>
	 * @param damage
	 * @return
	 */
	public SetDamageFunction setDamage(double damage) {
		this.damage = damage;
		return this;
	}
	
	/**
	 * Specifies a random damage fraction between {@link #min} and {@link #max}</br>
	 * <i>Note: (1.0 is undamaged, 0.0 is zero durability left)</i>
	 * @param min
	 * @param max
	 * @return
	 */
	public SetDamageFunction setDamage(double min, double max) {
		this.min = min;
		this.max = max;
		useDamage = true;
		return this;
	}

	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", getType().getName());
		
		if(useDamage) {
			JsonObject damages = new JsonObject();
			damages.addProperty("min", min);
			damages.addProperty("max", max);
			function.add("damage", damages);
		} else {
			function.addProperty("damage", damage);
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
