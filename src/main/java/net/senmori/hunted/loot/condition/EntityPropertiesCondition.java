package net.senmori.hunted.loot.condition;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonObject;

import net.senmori.hunted.loot.condition.properties.EntitySelector;

/**
 * Condition that checks certain properties of the {@link Entity} that has this loot table.
 */
public class EntityPropertiesCondition extends LootCondition {
	
	private String entity;
	private boolean onFire;
	private JsonObject properties;
	/**
	 * Condition that checks certain properties of the {@link Entity} that has this loot table.
	 */
	public EntityPropertiesCondition() {
		properties = new JsonObject();
		onFire = false;
    }
	
	/* ###################################
	 * ItemStack methods
	 * ###################################
	 */
	@Override
    public ItemStack applyTo(ItemStack applyTo) {
	
		
	    return applyTo;
    }
	
	
	
	/* ####################
	 * Property Methods
	 * ####################
	 */
	public EntityPropertiesCondition setEntity(EntitySelector entity) {
		this.entity = entity.getName();
		return this;
	}
	
	/** Generic property setter in case I can't update this as soon as more properties are made available */
	public EntityPropertiesCondition setProperty(String property, String value) {
		properties.addProperty(property, value);
		return this;
	}
	/**
	 * The condition will succeed if the {@link #entity} was killed while it was on fire.
	 * @param entity - The entity to check
	 * @param value - true/false. False by default
	 */
	public EntityPropertiesCondition setOnFire(boolean value) {
		this.onFire = value;
		properties.addProperty("on_fire", onFire);
		return this;
	}
	
	
	/* ######################################
	 * Load/Save Methods
	 * ######################################
	 */
	@Override
    public JsonObject toJsonObject() {
		JsonObject condition = new JsonObject();
		condition.addProperty("condition", "entity_properties");
		condition.addProperty("entity", entity);
		condition.add("properties", properties);
	    return condition;
    }

	@Override
    public LootCondition fromJsonObject(JsonObject condition) {
	    entity = condition.get("entity").getAsString();
	    onFire = condition.get("onFire").getAsBoolean();
	    return this;
    }

	@Override
    public LootConditionType getType() {
	    return LootConditionType.ENTITY_PROPERTIES;
    }
}
