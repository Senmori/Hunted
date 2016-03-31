package net.senmori.hunted.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.LootContext.EntityTarget;
import net.senmori.hunted.loot.properties.EntityProperty;
import net.senmori.hunted.loot.properties.EntityPropertyManager;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class EntityHasProperty implements LootCondition {

	private List<EntityProperty> entityProperties;
	private EntityTarget target;
	
	public EntityHasProperty(List<EntityProperty> properties, LootContext.EntityTarget target) {
		this.entityProperties = properties;
		this.target = target;
	}

	@Override
	public boolean testCondition(LootContext context, Random rand) {
		return true;
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

	public List<EntityProperty> getProperties() { return this.entityProperties; }

	public static class Serializer extends LootCondition.Serializer<EntityHasProperty> {

		protected Serializer() {
	        super(new ResourceLocation("entity_properties"), EntityHasProperty.class);
        }

		@Override
        public void serialize(JsonObject json, EntityHasProperty value, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			
			for(EntityProperty property : value.entityProperties) {
				EntityProperty.Serializer serializer = EntityPropertyManager.getSerializerFor(property);
				jsonObject.add(serializer.getName().toString(), serializer.serialize(property, context));
			}
			json.add("properties", jsonObject);
			json.add("entity", context.serialize(value.target));
        }

		@Override
        public EntityHasProperty deserialize(JsonObject json, JsonDeserializationContext context) {
			Set set = JsonUtils.getJsonObject(json, "properties").entrySet();
			List<EntityProperty> properties = new ArrayList<>();
			Iterator i = set.iterator();
			Entry entry;
			while(i.hasNext()) {
				entry = (Entry)i.next();
				ResourceLocation location = new ResourceLocation((String)entry.getKey());
				properties.add(EntityPropertyManager.getSerializerForName(location).deserialize((JsonElement)entry.getValue(), context));
			}
			return new EntityHasProperty(properties, JsonUtils.deserializeClass(json, "entity", context, EntityTarget.class));
        }
	}
}
