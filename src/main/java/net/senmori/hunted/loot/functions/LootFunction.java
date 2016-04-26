package net.senmori.hunted.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.core.LootContext;
import net.senmori.hunted.loot.storage.ResourceLocation;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

/**
 * Created by Senmori on 4/13/2016.
 */
public abstract class LootFunction {
    private List<LootCondition> conditions;

    protected LootFunction(List<LootCondition> conditions) { this.conditions = conditions; }

    public void addCondition(LootCondition condition) { this.conditions.add(condition); }

    public abstract ItemStack apply(ItemStack itemstack, Random rand, LootContext context);

    public List<LootCondition> getConditions() { return this.conditions; }


    public abstract static class Serializer<T extends LootFunction> {
        private ResourceLocation lootTableLocation;
        private Class<T> functionClass;

        protected Serializer(ResourceLocation location, Class<T> clazz) {
            this.lootTableLocation = location;
            this.functionClass = clazz;
        }

        public ResourceLocation getName() { return this.lootTableLocation; }

        public Class<T> getFunctionClass() { return this.functionClass; }

        public abstract void serialize(JsonObject json, T type, JsonSerializationContext context);

        public abstract T deserialize(JsonObject json, JsonDeserializationContext context, List<LootCondition> conditions);

    }
}
