package net.senmori.hunted.loot.function;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.senmori.hunted.loot.attribute.LootAttribute;
import net.senmori.hunted.loot.utils.LootUtil;


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
	
	public SetAttributesFunction addModifier(LootAttribute mod) {
		modifiers.add(mod);
		return this;
	}
	
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
    public LootFunctionType getType() {
	    return LootFunctionType.SET_ATTRIBUTES;
    }

	@Override
    public LootFunction fromJsonObject(JsonElement element) {
		JsonObject function = element.getAsJsonObject();
		// iterate through modifiers
		if(function.get("modifiers").isJsonArray()) {
			JsonArray modifiers = function.get("modifiers").getAsJsonArray();
			while(modifiers.iterator().hasNext()) {
				JsonObject curr = modifiers.iterator().next().getAsJsonObject();
				addModifier(new LootAttribute().fromJsonObject(curr));
				if(!modifiers.iterator().hasNext()) break;
			}
		}
		
	    // check for conditions
	    if(function.get("conditions").isJsonArray()) { // we have conditions!
	    	JsonArray conditions = function.get("conditions").getAsJsonArray();
	    	while(conditions.iterator().hasNext()) {
	    		JsonObject next = conditions.iterator().next().getAsJsonObject();
	    		addCondition(LootUtil.getCondition(next.get("type").getAsString())); // get the correct instance of LootFunction
	    		if(!conditions.iterator().hasNext()) break;
	    	}
	    }
	    return this;
    }

}
