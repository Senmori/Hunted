package net.senmori.hunted.loot.condition;

import java.util.List;
import java.util.Random;

import org.bukkit.entity.Entity;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.properties.EntityProperty;
import net.senmori.hunted.loot.storage.ResourceLocation;

public class EntityHasProperty implements LootCondition {

	private List<EntityProperty> entityProperties;
	private final LootContext.EntityTarget target;
	
	public EntityHasProperty(List<EntityProperty> properties, LootContext.EntityTarget target) {
		this.entityProperties = properties;
		this.target = target;
	}
	
	
	public boolean testCondition(Random rand, LootContext context) {
		Entity entity = context.getEntity(this.target);
		
		if(entity == null) {
			return false;
		} else {
			for(EntityProperty property : entityProperties) {
				if(!property.testProperty(rand, entity)) {
					return false;
				}
			}
			return true;
		}
	}
	
	
	public static class Serializer extends LootCondition.Serializer<EntityHasProperty> {

		protected Serializer() {
	        super(new ResourceLocation("entity_properties"), EntityHasProperty.class);
        }

		@Override
        public void serialize(JsonObject json, EntityHasProperty value, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			
			for(EntityProperty property : value.entityProperties) {
				EntityProperty.Serializer<EntityProperty> serializer = null;
				jsonObject.add(serializer.getName().toString(), serializer.serialize(property, context));
			}
			json.add("properties", jsonObject);
			json.add("entity", context.serialize(value.target));
        }

		@Override
        public EntityHasProperty deserialize(JsonObject json, JsonDeserializationContext context) {
			
			return null;
        }
		
		
		
	}
}
