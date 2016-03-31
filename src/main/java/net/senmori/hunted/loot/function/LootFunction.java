package net.senmori.hunted.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.storage.ResourceLocation;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public abstract class LootFunction {

    private List<LootCondition> conditions;

    protected LootFunction(List<LootCondition> conditions) {
        this.conditions = conditions;
    }

    public abstract void apply(ItemStack stack, Random rand, LootContext context);

    public List<LootCondition> getConditions() { return this.conditions; }

    public void addCondition(LootCondition condition){
        conditions.add(condition);
    }

    public abstract static class Serializer<T extends LootFunction> {
        private final ResourceLocation resource;
        private final Class<T> functionClass;

        protected Serializer(ResourceLocation resource, Class<T> clazz) {
            this.resource = resource;
            this.functionClass = clazz;
        }

        public ResourceLocation getFunctionName() { return this.resource; }

        public Class<T> getFunctionClass() { return this.functionClass; }

        public abstract void serialize(JsonObject object, T type, JsonSerializationContext context);

        public abstract T deserialize(JsonObject object, JsonDeserializationContext context, List<LootCondition> conditions);
    }

}
