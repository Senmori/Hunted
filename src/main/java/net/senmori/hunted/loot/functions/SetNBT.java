package net.senmori.hunted.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.server.v1_9_R1.MojangsonParseException;
import net.minecraft.server.v1_9_R1.MojangsonParser;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.core.LootContext;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

/**
 * Created by Senmori on 4/14/2016.
 */
public class SetNBT extends LootFunction {

    private String tag;
    private NBTTagCompound root;

    public SetNBT(String tag, List<LootCondition> conditions) {
        super(conditions);
        setNBTTag(tag);
    }

    public SetNBT(String tag) {
        this(tag, null);
    }

    public void setNBTTag(String tag) {
        this.tag = tag;
        try {
            root = MojangsonParser.parse(tag);
        } catch (MojangsonParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ItemStack apply(ItemStack itemstack, Random rand, LootContext context) {
        net.minecraft.server.v1_9_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemstack);
        nmsStack.setTag(root);
        return CraftItemStack.asBukkitCopy(nmsStack);
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
