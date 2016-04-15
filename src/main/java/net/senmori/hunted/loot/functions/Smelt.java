package net.senmori.hunted.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.storage.ResourceLocation;
import org.bukkit.Bukkit;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;
import java.util.Random;

/**
 * Created by Senmori on 4/14/2016.
 */
public class Smelt extends LootFunction {

    public Smelt(List<LootCondition> conditions) { super(conditions); }

    @Override
    public ItemStack apply(ItemStack itemstack, Random rand, LootContext context) {
        for (Recipe recipe : Bukkit.getRecipesFor(itemstack)) {
            if (recipe instanceof FurnaceRecipe) {
                return recipe.getResult();
            }
        }
        return itemstack;
    }


    public static class Serializer extends LootFunction.Serializer<Smelt> {
        protected Serializer() { super(new ResourceLocation("furnace_smelt"), Smelt.class); }

        @Override
        public void serialize(JsonObject json, Smelt type, JsonSerializationContext context) {

        }

        @Override
        public Smelt deserialize(JsonObject json, JsonDeserializationContext context, List<LootCondition> conditions) {
            return new Smelt(conditions);
        }
    }
}
