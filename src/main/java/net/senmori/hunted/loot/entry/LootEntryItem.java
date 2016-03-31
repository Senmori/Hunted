package net.senmori.hunted.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.function.LootFunction;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Senmori on 3/29/2016.
 */
public class LootEntryItem extends LootEntry {

    private ItemStack itemstack;
    protected List<LootFunction> lootFunctions;

    public LootEntryItem(ItemStack stack, int weight, int quality, List<LootCondition> conditions, List<LootFunction> functions){
        super(weight, quality, conditions);
        this.lootFunctions = functions;
        this.itemstack = stack;
    }

    public LootEntryItem(Material material, int weight, int quality, List<LootCondition> conditions, List<LootFunction> functions) {
        super(weight, quality, conditions);
        this.lootFunctions = functions;
        this.itemstack = new ItemStack(material);
    }

    public ItemStack getItemstack() { return this.itemstack; }

    public void setItemStack(ItemStack newItemStack) { itemstack = newItemStack; }


    @Override
    protected void serialize(JsonObject object, JsonSerializationContext context) {
        if (this.lootFunctions != null && this.lootFunctions.size() > 0) {
            object.add("functions", context.serialize(this.lootFunctions));
        }

        Material material = this.itemstack.getType();
        if(material == null) {
            throw new IllegalArgumentException("Unknown material \'" + material + "\'");
        } else {
            object.addProperty("name", material.toString().toLowerCase());
        }
    }

    public static LootEntryItem deserialize(JsonObject object, JsonDeserializationContext context, int weight, int quality, List<LootCondition> conditions) {
        ItemStack stack = JsonUtils.getItem(object, "name");
        LootFunction[] functions;
        if(object.has("functions")) {
            functions = JsonUtils.deserializeClass(object, "functions", context, LootFunction[].class);
        } else {
            functions = new LootFunction[0];
        }
        return new LootEntryItem(stack, weight, quality, conditions, Arrays.asList(functions));
    }
}
