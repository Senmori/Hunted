package net.senmori.hunted.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.functions.LootFunction;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Senmori on 4/1/2016.
 */
public class LootEntryItem extends LootEntry {

    private List<LootFunction> functions;
    private Material name;

    public LootEntryItem(Material material, int weight, int quality, List<LootFunction> functions, List<LootCondition> conditions) {
        super(weight, quality, conditions);
        if (functions == null) { this.functions = new ArrayList<>(); }
        this.functions = functions;
        this.name = material;
    }

    public void addFunction(LootFunction function) {
        if (functions == null) { functions = new ArrayList<>(); }
        functions.add(function);
    }

    @Override
    public void addLoot(List<ItemStack> itemstacks) {

    }

    public Material getMaterial() { return name; }

    public void setMaterial(Material newMaterial) { this.name = newMaterial; }

    public List<LootFunction> getFunctions() { return this.functions; }

    @Override
    protected void serialize(JsonObject json, JsonSerializationContext context) {
        json.addProperty("name", this.name.toString().toLowerCase());
        if (this.functions != null && !this.functions.isEmpty()) {
            json.add("functions", context.serialize(this.functions));
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
        return new LootEntryItem(stack.getType(), weight, quality, Arrays.asList(functions), conditions);
    }
}
