package net.senmori.hunted.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.storage.ResourceLocation;

import java.util.Random;

/**
 * Created by Senmori on 4/13/2016.
 */
public interface LootCondition {

    boolean testCondition(Random rand, LootContext context);

    abstract class Serializer<T extends LootCondition> {
        private ResourceLocation name;
        private Class<T> conditionClass;

        protected Serializer(ResourceLocation location, Class<T> clazz) {
            this.name = location;
            this.conditionClass = clazz;
        }

        public ResourceLocation getName() { return this.name; }

        public Class<T> getConditionClass() { return this.conditionClass; }

        public abstract void serialize(JsonObject json, T type, JsonSerializationContext context);

        public abstract T deserialize(JsonObject json, JsonDeserializationContext context);
    }
}
