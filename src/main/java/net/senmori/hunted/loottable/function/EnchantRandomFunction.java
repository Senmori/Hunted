package net.senmori.hunted.loottable.function;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.loottable.condition.LootCondition;

import org.bukkit.enchantments.Enchantment;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Enchants the item associated with this function with a random enchantment from {@link #enchantments}, at a random level</br>
 * For multiple random enchantments, you will need to use several of this function. </br>
 * If you get mulitple of the same enchantment, they will not merge. They will simply be listed on after the other. </br>
 * @author Senmori
 */
public class EnchantRandomFunction extends LootFunction {
	
	/** List of enchantments this function will choose from. See {@link Enchantment} */
	private List<String> enchantments;

	/**
	 * Enchants the item associated with this function with a random enchantment from {@link #enchantments}, at a random level</br>
	 * For multiple random enchantments, you will need to use several of this function. </br>
	 * If you get mulitple of the same enchantment, they will not merge. They will simply be listed on after the other. </br>

	 */
	public EnchantRandomFunction() {
		super(LootFunctionType.ENCHANT_RANDOM);
		enchantments = new ArrayList<>();
	}
	
	public EnchantRandomFunction addEnchantment(String enchantName) {
		enchantments.add(enchantName);
		return this;
	}
	
	public EnchantRandomFunction addEnchantment(Enchantment enchant) {
		enchantments.add(enchant.getName());
		return this;
	}
	
	public EnchantRandomFunction addEnchantment(List<Enchantment> enchants) {
		for(Enchantment e : enchants) {
			enchantments.add(e.getName());
		}
		return this;
	}
	
	
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", getType().getName());
		JsonArray enchants = new JsonArray();
		for(String s : enchantments) {
			enchants.add(new JsonPrimitive(s));
		}
		function.add("enchantments", enchants);
		// add conditions if present
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
