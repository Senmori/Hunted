package net.senmori.hunted.loot.function;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.utils.LootUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class LootFunction {
	
	public List<LootCondition> conditions;
	protected LootFunction() {
		conditions = new ArrayList<>();
	}
	// apply this function to this itemstack
	public abstract ItemStack applyTo(ItemStack applyTo);
	
	
	public abstract JsonObject toJsonObject();
	public abstract LootFunction fromJsonObject(JsonObject element);
	public abstract LootFunctionType getType();
	
	
	
	
	public void addCondition(LootCondition lc) {
		conditions.add(lc);
	}
	
	public List<LootCondition> getConditions() {
		return conditions;
	}
	
	protected void loadConditions(JsonArray conditions) {
		for(JsonElement cond : conditions) {
			if(!cond.isJsonObject()) continue;
			String type = cond.getAsJsonObject().get("condition").getAsString();
			addCondition(LootUtil.getCondition(type).fromJsonObject(cond.getAsJsonObject()));
		}
	}
}
