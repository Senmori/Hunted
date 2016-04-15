package net.senmori.hunted.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.EntityTarget;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.properties.EntityProperty;
import net.senmori.hunted.loot.properties.EntityPropertyManager;
import net.senmori.hunted.loot.storage.ResourceLocation;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.Random;

/**
 * Created by Senmori on 4/13/2016.
 */
public class EntityHasProperty implements LootCondition {
    private List<EntityProperty> properties;
    private EntityTarget target;

    public EntityHasProperty(List<EntityProperty> properties, EntityTarget target) {
        this.properties = properties;
        this.target = target;
    }

    public void addProperty(EntityProperty property) { properties.add(property); }

    public void setTarget(EntityTarget target) { this.target = target; }

    public List<EntityProperty> getProperties() { return this.properties; }

    public EntityTarget getTarget() { return this.target; }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        Entity entity = context.getEntity(this.target);
        if (entity == null) {
            return false;
        } else {
            for (EntityProperty property : this.properties) {
                if (!property.testProperty(rand, entity)) return false;
            }
        }
        return true;
    }

    public static class Serializer extends LootCondition.Serializer<EntityHasProperty> {
        protected Serializer() { super(new ResourceLocation("entity_properties"), EntityHasProperty.class); }

        @Override
        public void serialize(JsonObject json, EntityHasProperty type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            for (EntityProperty property : type.getProperties()) {
                EntityProperty.Serializer serializer = EntityPropertyManager.getSerializerFor(property);
                jsonObject.add(serializer.getName().toString(), serializer.serialize(property, context));
            }
            json.add("properties", jsonObject);
            json.addProperty("entity", type.getTarget().getTargetType());
        }

        @Override
        public EntityHasProperty deserialize(JsonObject json, JsonDeserializationContext context) {
            return null;
        }
    }
}