package net.senmori.hunted.loot.condition;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonObject;



/**
 * Condition that checks if the entity who triggered this condition is a player
 */
public class KilledByPlayerCondition extends LootCondition  {

	private boolean inverse;
	
	/**
	 * Condition that checks if the entity who triggered this condition is a player
	 */
	public KilledByPlayerCondition() {
		inverse = false;
    }
	

	@Override
    public ItemStack applyTo(ItemStack applyTo) {
	    return applyTo;
    }

	
	/**
	 * Set's inverse to 'true', so this condition will only succeed if the killer is <b>NOT</b> a player
	 * @param value
	 */
	public KilledByPlayerCondition setInverse(boolean value) {
		inverse = value;
		return this;
	}

	/* ##################
	 * Load/Save Methods
	 * ##################
	 */
	@Override
    public JsonObject toJsonObject() {
		JsonObject condition = new JsonObject();
		condition.addProperty("condition", "killed_by_player");
		condition.addProperty("inverse", inverse);
	    return condition;
    }

	@Override
    public LootCondition fromJsonObject(JsonObject condition) {
		inverse = condition.get("inverse").getAsBoolean();
	    return this;
    }

	@Override
    public LootConditionType getType() {
	    return LootConditionType.KILLED_BY_PLAYER;
    }

}
