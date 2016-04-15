package net.senmori.hunted.loot.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Senmori on 4/12/2016.
 */
public abstract class InheritanceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    public abstract JsonElement serialize(T t, Type type, JsonSerializationContext jsonSerializationContext);

    @Override
    public abstract T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException;


}
