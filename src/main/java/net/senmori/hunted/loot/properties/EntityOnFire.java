package net.senmori.hunted.loot.properties;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.entity.Entity;

import java.util.Random;

/**
 * Created by Senmori on 4/12/2016.
 */
public class EntityOnFire implements EntityProperty {

    private boolean onFire;

    public EntityOnFire(boolean onFire) { this.onFire = onFire; }

    public void setOnFire(boolean value) { this.onFire = value; }

    @Override
    public boolean testProperty(Random rand, Entity entity) {
        return onFire ? entity.getFireTicks() > 0 : entity.getFireTicks() <= 0;
    }

    public boolean getOnFire() { return this.onFire; }

    public static class Serializer extends EntityProperty.Serializer<EntityOnFire> {
        protected Serializer() { super(new ResourceLocation("on_fire"), EntityOnFire.class); }

        @Override
        public JsonElement serialize(EntityOnFire type, JsonSerializationContext context) {
            return new JsonPrimitive(type.onFire);
        }

        @Override
        public EntityOnFire deserialize(JsonElement json, JsonDeserializationContext context) {
            return new EntityOnFire(JsonUtils.getBoolean(json, "on_fire"));
        }
    }
}
