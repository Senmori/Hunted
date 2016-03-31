package net.senmori.hunted.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Senmori on 3/29/2016.
 */
public class LootFunctionManager {

    private static ConcurrentHashMap<ResourceLocation, LootFunction.Serializer<?>> nameToSerializerMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Class<? extends LootFunction>, LootFunction.Serializer<?>> classToSerializerMap = new ConcurrentHashMap<>();

    // load valid functions here
    static {

    }

    public LootFunctionManager() {}

    public static <T extends LootFunction> void registerFunction(LootFunction.Serializer<? extends T> function) {
        ResourceLocation resource = function.getFunctionName();
        Class functionClass = function.getFunctionClass();

        if(nameToSerializerMap.containsKey(resource)) {
            throw new IllegalArgumentException("Can\'t re-register item function name " + resource);
        } else if(classToSerializerMap.containsKey(functionClass)) {
            throw new IllegalArgumentException("Can\'t re-register item function class " + functionClass.getName());
        } else {
            nameToSerializerMap.put(resource, function);
            classToSerializerMap.put(functionClass, function);
        }
    }


    public static LootFunction.Serializer<?> getSerializerForName(ResourceLocation location) {
        LootFunction.Serializer serializer = (LootFunction.Serializer)nameToSerializerMap.get(location);

        if(serializer == null) {
            throw new IllegalArgumentException("Unknown loot item function " + location);
        } else {
            return serializer;
        }
    }

    public static <T extends LootFunction> LootFunction.Serializer<?> getSerializerFor(T functionClass) {
        LootFunction.Serializer serializer = (LootFunction.Serializer)classToSerializerMap.get(functionClass);

        if(serializer == null) {
            throw new IllegalArgumentException("Unknown loot item function " + functionClass);
        } else {
            return serializer;
        }
    }


    public static class Serializer implements JsonDeserializer<LootFunction>, JsonSerializer<LootFunction> {
        public Serializer() {}

        public LootFunction deserialize(JsonElement element, Type type, JsonDeserializationContext context) {
            JsonObject object = JsonUtils.getJsonObject(element, "function");
            ResourceLocation resource = new ResourceLocation(JsonUtils.getString(object, "function"));

            LootFunction.Serializer serializer;
            try {
                serializer = LootFunctionManager.getSerializerForName(resource);
            }catch (IllegalArgumentException e) {
                throw new JsonSyntaxException("Unknown function \'" + resource + "\'");
            }
            return serializer.deserialize(object, context, Arrays.asList((LootCondition[])JsonUtils.deserializeClass(object, "conditions", new LootCondition[0], context, LootCondition[].class)));
        }

        public JsonElement serialize(LootFunction function, Type type, JsonSerializationContext context) {
            LootFunction.Serializer serializer = LootFunctionManager.getSerializerFor(function);
            JsonObject object = new JsonObject();
            serializer.serialize(object, function, context);
            object.addProperty("function", serializer.getFunctionName().toString());
            if(function.getConditions() != null && function.getConditions().size() > 0) {
                object.add("conditions", context.serialize(function.getConditions()));
            }
            return object;
        }
    }
}
