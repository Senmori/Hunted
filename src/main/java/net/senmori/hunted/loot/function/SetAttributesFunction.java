package net.senmori.hunted.loot.function;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.loot.attribute.LootAttribute;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 * Add attribute modifiers to an item</br>
 * @author Senmori
 */
public class SetAttributesFunction extends LootFunction {
	
	private List<LootAttribute> modifiers;
	/**
	 * Add attribute modifiers to an item
	 */
	public SetAttributesFunction() {
	    modifiers = new ArrayList<>();
    }
	
	/* #########################
	 * ItemStack methods
	 * #########################
	 */
	@Override
    public ItemStack applyTo(ItemStack applyTo) {
	    // TODO Auto-generated method stub
	    return null;
    }
	
	
	
	/* #########################
	 * Property methods
	 * #########################
	 */
	
	public SetAttributesFunction addModifier(LootAttribute mod) {
		modifiers.add(mod);
		return this;
	}
	
	
	
	
	/* ############################
	 * Load/Save methods
	 * ############################
	 */
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "set_attributes");
		JsonArray modArray = new JsonArray();
		for(LootAttribute la : modifiers) {
			modArray.add(la.toJsonObject());
		}
		function.add("modifiers", modArray);
		return function;
	}

	@Override
    public LootFunction fromJsonObject(JsonObject element) {
		// iterate through modifiers
		if(element.get("modifiers").isJsonArray()) {
			JsonArray modifiers = element.get("modifiers").getAsJsonArray();
			while(modifiers.iterator().hasNext()) {
				JsonObject curr = modifiers.iterator().next().getAsJsonObject();
				addModifier(new LootAttribute().fromJsonObject(curr));
				if(!modifiers.iterator().hasNext()) break;
			}
		}
		
	    // check for conditions
	    if(element.get("conditions").isJsonArray()) { // we have conditions!
	    	loadConditions(element.get("conditions").getAsJsonArray());
	    }
	    return this;
    }
	
	/* #################
	 * Getters
	 * #################
	 */
	@Override
    public LootFunctionType getType() {
	    return LootFunctionType.SET_ATTRIBUTES;
    }

}
