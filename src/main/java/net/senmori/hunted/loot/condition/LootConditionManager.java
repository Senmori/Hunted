package net.senmori.hunted.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Senmori on 3/29/2016.
 */
public class LootConditionManager {

    private static ConcurrentHashMap<ResourceLocation, LootCondition.Serializer<?>> nameToSerializerMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Class<? extends LootCondition>, LootCondition.Serializer<?>> classToSerializerMap = new ConcurrentHashMap<>();

    //register valid LootConditions here.
    static {

    }

    public LootConditionManager() {}


    public static <T extends LootCondition> void registerCondition(LootCondition.Serializer<? extends T> condition) {
        ResourceLocation resource = condition.getLootTableLocation();
        Class clazz = condition.getConditionClass();

        if (nameToSerializerMap.containsKey(resource)) {
            throw new IllegalArgumentException("Can\'t re-register item condition name " + resource);
        } else if(classToSerializerMap.containsKey(clazz)) {
            throw new IllegalArgumentException("Can\'t re-register item condition class " + clazz);
        } else {
            nameToSerializerMap.put(resource,condition);
            classToSerializerMap.put(clazz, condition);
        }
    }


    public static boolean testAllConditions(List<LootCondition> conditions, Random rand, LootContext context) {
        if(conditions.size() < 1 || conditions == null) return true;

        int i = 0;
        for(LootCondition condition : conditions) {
            if(!condition.testCondition(context, rand)){
                return false;
            }
        }
        return true;
    }

    private static LootCondition.Serializer<?> getSerializerForName(ResourceLocation location ) {
        LootCondition.Serializer serializer = (LootCondition.Serializer) nameToSerializerMap.get(location);
        if(serializer == null) {
            throw new IllegalArgumentException("Unknown loot item condition \'" + location + "\'");
        } else {
            return serializer;
        }
    }

    private static <T extends LootCondition> LootCondition.Serializer<T> getSerializerFor(T conditionClass) {
        LootCondition.Serializer serializer = (LootCondition.Serializer)classToSerializerMap.get(conditionClass);
        if(serializer == null) {
            throw new IllegalArgumentException("Unknown loot item condition " + conditionClass);
        } else {
            return serializer;
        }
    }



    public static class Serializer implements JsonDeserializer<LootCondition>, JsonSerializer<LootCondition> {
        public Serializer() {}


        public LootCondition deserialize(JsonElement element, Type type, JsonDeserializationContext context) {
            JsonObject object = JsonUtils.getJsonObject(element, "condition");
            ResourceLocation resource = new ResourceLocation(JsonUtils.getString(object, "condition"));

            LootCondition.Serializer serializer;
            try {
                serializer = LootConditionManager.getSerializerForName(resource);
            } catch(IllegalArgumentException e) {
                throw new JsonSyntaxException("Unknown condition \'" + resource + "\'");
            }
            return serializer.deserialize(object, context);
        }

        public JsonElement serialize(LootCondition condition, Type type, JsonSerializationContext context) {
            LootCondition.Serializer serializer = LootConditionManager.getSerializerFor(condition);
            JsonObject object = new JsonObject();
            serializer.serialize(object, condition, context);
            object.addProperty("condition", serializer.getLootTableLocation().toString());
            return object;
        }
    }
}
