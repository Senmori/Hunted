package net.senmori.hunted.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.storage.ResourceLocation;

import java.util.Random;

public interface LootCondition {
	

	boolean testCondition(LootContext context, Random rand);



	
	abstract class Serializer<T extends LootCondition> {
		private final ResourceLocation lootTableLocation;
		private final Class<T> conditionsClass;
		
		
		protected Serializer(ResourceLocation location, Class<T> clazz) {
			this.lootTableLocation = location;
			this.conditionsClass = clazz;
		}
		
		public ResourceLocation getLootTableLocation() {
			return this.lootTableLocation;
		}
		
		public Class<T> getConditionClass() {
			return conditionsClass;
		}
		
		public abstract void serialize(JsonObject json, T value, JsonSerializationContext context);
		public abstract T deserialize(JsonObject json, JsonDeserializationContext context);
	}
}
