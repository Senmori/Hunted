package net.senmori.hunted.loottable.function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {
		JSONObject function = new JSONObject();
		function.put("function", getType().getName());
		
		if(useDamage) {
			JSONObject damages = new JSONObject();
			damages.put("min", min);
			damages.put("max", max);
			function.put("damage", damages);
		} else {
			function.put("damage", damage);
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
