package net.senmori.hunted.loot.condition;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonObject;



public abstract class LootCondition {
	
	protected LootCondition() {}
	
	// load/save to file methods
	public abstract JsonObject toJsonObject();
	public abstract LootCondition fromJsonObject(JsonObject condition);
	public abstract LootConditionType getType();
	
	// apply this condition to an item, or see if the condition should apply
	public abstract ItemStack applyTo(ItemStack applyTo);
}
