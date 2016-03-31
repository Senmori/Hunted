package net.senmori.hunted.loot.properties;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.storage.ResourceLocation;
import org.bukkit.entity.Entity;

import java.util.Random;

public interface EntityProperty {
	
	boolean testProperty(Random rand, Entity entity);
	
	
	abstract class Serializer<T extends EntityProperty> {
		private final ResourceLocation name;
		private final Class<T> clazz;
		
		protected Serializer(ResourceLocation name, Class<T> clazz) {
			this.name = name;
			this.clazz = clazz;
		}
		
		public ResourceLocation getName() {
			return this.name;
		}
		
		public Class<T> getPropertyClass() {
			return this.clazz;
		}
		
		public abstract JsonElement serialize(T property, JsonSerializationContext context);
		public abstract T deserialize(JsonElement element, JsonDeserializationContext context);
	}
	
}
