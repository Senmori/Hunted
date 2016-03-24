package net.senmori.hunted.loottable.function;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.senmori.hunted.loottable.attribute.LootAttribute;


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
	    super(LootFunctionType.SET_ATTRIBUTES);
	    modifiers = new ArrayList<>();
    }
	
	public SetAttributesFunction addModifier(LootAttribute mod) {
		modifiers.add(mod);
		return this;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", getType().getName());
		JsonArray modArray = new JsonArray();
		for(LootAttribute la : modifiers) {
			modArray.add(la.toJsonObject());
		}
		function.add("modifiers", modArray);
		return function;
	}

}
