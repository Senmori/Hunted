package net.senmori.hunted.loot.function;


import java.util.HashMap;
import java.util.Map;

import net.senmori.hunted.loot.condition.LootCondition;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Sets the item's damage value(durability) for tools</br>
 * @author Senmori
 */
public class SetDamageFunction extends LootFunction {

	private Map<String,Double> damageValues;
	
	/**
	 * Sets the item's damage value(durability) for tools</br>
	 */
	public SetDamageFunction() {
	    damageValues = new HashMap<>();
	    damageValues.put("exact", 1.0);
    }
	
	/* ######################
	 * ItemStack methods
	 * ######################
	 */
	@Override
    public ItemStack applyTo(ItemStack applyTo) {
	    return applyTo;
    }
	
	/* ###############################
	 * Property methods 
	 * ###############################
	 */
	/**
	 * Set's the items damage fraction.</br>
	 * <i>Note: (1.0 is undamaged, 0.0 is zero durability left)</i>
	 * @param exact
	 * @return
	 */
	public SetDamageFunction setDamage(double exact) {
		damageValues.clear();
		damageValues.put("exact", exact);
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
		damageValues.clear();
		damageValues.put("min", min);
		damageValues.put("max", max);
		return this;
	}
	
	/* #########################
	 * Load/Save methods
	 * #########################
	 */
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "set_damage");
		
		if(!useExactOnly()) {
			JsonObject damages = new JsonObject();
			damages.addProperty("min", damageValues.get("min"));
			damages.addProperty("max", damageValues.get("max"));
			function.add("damage", damages);
		} else {
			function.addProperty("damage", damageValues.get("exact"));
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
		if(element.get("damage").getAsJsonPrimitive().isNumber()) {
			setDamage(element.get("damage").getAsDouble());
		} else {
			JsonObject damage = element.get("damage").getAsJsonObject();
			setDamage(damage.get("min").getAsDouble(), damage.get("max").getAsDouble());
		}
	    
	    // check for conditions
	    //loadConditions(element.get("conditions").getAsJsonArray());
		return this;
    }
	
	/* ####################
	 * Getters
	 * ####################
	 */
	public boolean useExactOnly() {
		return damageValues.containsKey("exact");
	}
	
	@Override
    public LootFunctionType getType() {
	    return LootFunctionType.SET_DAMAGE;
    }

}
