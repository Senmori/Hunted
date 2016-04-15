package net.senmori.hunted.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

/**
 * Created by Senmori on 4/14/2016.
 */
public class SetNBT extends LootFunction {

    private String tag;

    public SetNBT(String tag, List<LootCondition> conditions) {
        super(conditions);
        this.tag = tag;
    }


    @Override
    public ItemStack apply(ItemStack itemstack, Random rand, LootContext context) {
        return null;
    }


    public static class Serializer extends LootFunction.Serializer<SetNBT> {
        protected Serializer() { super(new ResourceLocation("set_nbt"), SetNBT.class); }

        @Override
        public void serialize(JsonObject json, SetNBT type, JsonSerializationContext context) {
            json.addProperty("tag", type.tag);
        }

        @Override
        public SetNBT deserialize(JsonObject json, JsonDeserializationContext context, List<LootCondition> conditions) {
            return new SetNBT(JsonUtils.getString(json, "tag"), conditions);
        }
    }
}
