package net.senmori.hunted.loottable.condition;

import com.google.gson.JsonObject;

import net.senmori.hunted.loottable.condition.enums.EntityPropertyEnum;

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
		super(LootConditionType.ENTITY_PROPERTIES);
		properties = new JsonObject();
		onFire = false;
    }
	
	public EntityPropertiesCondition setEntity(EntityPropertyEnum entity) {
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

	@Override
    public JsonObject toJsonObject() {
		JsonObject condition = new JsonObject();
		condition.addProperty("condition", getType().getName());
		condition.addProperty("entity", entity);
		condition.add("properties", properties);
	    return condition;
    }

}
