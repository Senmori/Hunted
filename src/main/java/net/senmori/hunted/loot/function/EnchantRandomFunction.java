package net.senmori.hunted.loot.function;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.loot.condition.LootCondition;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

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
		enchantments = new ArrayList<>();
	}
	
	/* ########################
	 * ItemStack methods
	 * ########################
	 */
	@Override
    public ItemStack applyTo(ItemStack applyTo) {
	    return applyTo;
    }
	
	/* #####################
	 * Property methods
	 * #####################
	 */
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
	
	/* ######################
	 * Load/Save methods
	 * ######################
	 */
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "enchant_randomly");
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

	@Override
    public LootFunction fromJsonObject(JsonObject element) {
	    JsonArray enchantments = element.getAsJsonArray("enchantments");
	    while(enchantments.iterator().hasNext()) {
	    	addEnchantment(enchantments.iterator().next().getAsString());
	    	if(!enchantments.iterator().hasNext()) break;
	    }
	    // check for conditions
	    //loadConditions(element.get("conditions").getAsJsonArray());
		return this;
    }
	
	/* #####################
	 * Getters
	 * #####################
	 */
	
	@Override
    public LootFunctionType getType() {
	    return LootFunctionType.ENCHANT_RANDOM;
    }
}
