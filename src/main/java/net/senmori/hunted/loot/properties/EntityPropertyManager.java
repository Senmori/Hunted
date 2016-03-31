package net.senmori.hunted.loot.properties;

import net.senmori.hunted.loot.storage.ResourceLocation;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Senmori on 3/29/2016.
 */
public class EntityPropertyManager {

    private static ConcurrentHashMap<ResourceLocation, EntityProperty.Serializer<?>> nameToSerializerMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Class<? extends EntityProperty>, EntityProperty.Serializer<?>> classToSerializerMap = new ConcurrentHashMap<>();

    // load entity properties here
    static {

    }

    public EntityPropertyManager() {}

    public static <T extends EntityProperty> void registerProperty(EntityProperty.Serializer<? extends T> property) {
        ResourceLocation location = property.getName();
        Class propertyClass = property.getClass();

        if(nameToSerializerMap.containsKey(location)) {
            throw new IllegalArgumentException("Can\'t re-register entity property name " + location);
        } else if(classToSerializerMap.containsKey(propertyClass)) {
            throw new IllegalArgumentException("Can\'t re-register entity property class " + propertyClass.getName());
        } else {
            nameToSerializerMap.put(location, property);
            classToSerializerMap.put(propertyClass, property);
        }
    }

    public static EntityProperty.Serializer<?> getSerializerForName(ResourceLocation location) {
        EntityProperty.Serializer serializer = (EntityProperty.Serializer) nameToSerializerMap.get(location);

        if(serializer == null) {
            throw new IllegalArgumentException("Unknown loot entity property \'" + location + "\'");
        } else {
            return serializer;
        }
    }

    public static <T extends EntityProperty> EntityProperty.Serializer<?> getSerializerFor(T propertyClass) {
        EntityProperty.Serializer serializer = (EntityProperty.Serializer)classToSerializerMap.get(propertyClass);
        if(serializer == null) {
            throw new IllegalArgumentException("Unknown loot entity property " + propertyClass);
        } else {
            return serializer;
        }
    }
}
