package net.senmori.hunted.loottable.condition;

import net.senmori.hunted.loottable.condition.enums.EntityPropertyEnum;

import org.json.simple.JSONObject;

/**
 * Condition that checks certain properties of the {@link Entity} that has this loot table.
 */
public class EntityPropertiesCondition extends LootCondition {
	
	private String entity;
	private boolean onFire;
	private JSONObject properties;
	/**
	 * Condition that checks certain properties of the {@link Entity} that has this loot table.
	 */
	public EntityPropertiesCondition() {
		super(LootConditionType.ENTITY_PROPERTIES);
		properties = new JSONObject();
		onFire = false;
    }
	
	public EntityPropertiesCondition setEntity(EntityPropertyEnum entity) {
		this.entity = entity.getName();
		return this;
	}
	/** Generic property setter in case I can't update this as soon as more properties are made available */
	public EntityPropertiesCondition setProperty(String property, Object value) {
		properties.put(property, value);
		return this;
	}
	/**
	 * The condition will succeed if the {@link #entity} was killed while it was on fire.
	 * @param entity - The entity to check
	 * @param value - true/false. False by default
	 */
	public EntityPropertiesCondition setOnFire(boolean value) {
		this.onFire = value;
		properties.put("on_fire", onFire);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
    public JSONObject toJSONObject() {
		JSONObject condition = new JSONObject();
		condition.put("condition", getType().getName());
		condition.put("entity", entity);
		condition.put("properties", properties);
	    return condition;
    }

}