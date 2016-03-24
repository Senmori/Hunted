package net.senmori.hunted.loottable.function;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.loottable.attribute.LootAttribute;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	public JSONObject toJSONObject() {
		JSONObject function = new JSONObject();
		function.put("function", getType().getName());
		JSONArray modArray = new JSONArray();
		for(LootAttribute la : modifiers) {
			modArray.add(la.toJSONObject());
		}
		function.put("modifiers", modArray);
		return function;
	}

}
