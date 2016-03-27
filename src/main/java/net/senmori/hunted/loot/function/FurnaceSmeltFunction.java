package net.senmori.hunted.loot.function;

import net.senmori.hunted.loot.condition.EntityPropertiesCondition;
import net.senmori.hunted.loot.condition.LootCondition;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Smelts the item as it would be in a furnace. Used in combination with {@link EntityPropertiesCondition} to cook food from animals on death</br>
 * @author Senmori
 */
public class FurnaceSmeltFunction extends LootFunction {
	
	/**
	 * Smelts the item as it would be in a furnace. Used in combination with {@link EntityPropertiesCondition} to cook food from animals on death
	 */
	public FurnaceSmeltFunction() {}
	
	/* #####################
	 * ItemStack methods
	 * #####################
	 */
	
	@Override
    public ItemStack applyTo(ItemStack applyTo) {
		Material smeltResult = null;
		for(Recipe recipe : Bukkit.getRecipesFor(applyTo)) {
			if(recipe instanceof FurnaceRecipe) {
				smeltResult = ((FurnaceRecipe)recipe).getResult().getType();
				break;
			}
		}
		applyTo.setType(smeltResult != null ? smeltResult : applyTo.getType());
	    return applyTo;
    }
	
	
	
	
	/* ########################
	 * Load/Save methods
	 * ########################
	 */
	@Override
	public JsonObject toJsonObject() {
		JsonObject function = new JsonObject();
		function.addProperty("function", "furnace_smelt");
		// add conditions
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
	    // check for conditions
	    //loadConditions(element.get("conditions").getAsJsonArray());
	    return this;
    }
	
	/* ####################
	 * Getters
	 * ####################
	 */

	@Override
    public LootFunctionType getType() {
	    return LootFunctionType.FURNACE_SMELT;
    }
}
