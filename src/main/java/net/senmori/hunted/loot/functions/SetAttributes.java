package net.senmori.hunted.loot.functions;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.core.LootAttributeModifier;
import net.senmori.hunted.loot.core.LootContext;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import net.senmori.hunted.loot.utils.NBTUtils;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Senmori on 4/14/2016.
 */
public class SetAttributes extends LootFunction {

    private List<LootAttributeModifier> modifiers;

    public SetAttributes(List<LootAttributeModifier> modifiers, List<LootCondition> conditions) {
        super(conditions);
        this.modifiers = modifiers;
    }

    public SetAttributes(List<LootAttributeModifier> modifiers) {
        this(modifiers, null);
    }

    public void addModifier(LootAttributeModifier modifier) {
        if (modifiers.contains(modifier)) return;
        modifiers.add(modifier);
    }

    @Override
    public ItemStack apply(ItemStack itemstack, Random rand, LootContext context) {
        for (LootAttributeModifier modifier : modifiers) {
            UUID uuid = modifier.getUuid();
            if (uuid == null) {
                uuid = UUID.randomUUID();
            }
            AttributeModifier attrib = new AttributeModifier(uuid, modifier.getAttributeName(), modifier.getAmount().generateInt(new Random()), getOperation(modifier.getOperation()));
            itemstack = NBTUtils.addAttribute(attrib, itemstack, modifier.getEquipmentSlots());
        }
        return itemstack;
    }

    private AttributeModifier.Operation getOperation(int operation) {
        switch (operation) {
            case 0:
                return AttributeModifier.Operation.ADD_NUMBER;
            case 1:
                return AttributeModifier.Operation.ADD_SCALAR;
            case 2:
                return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
            default:
                throw new IllegalArgumentException("Unknown Attribute Modifier operation " + operation);
        }
    }


    public List<LootAttributeModifier> getModifiers() { return this.modifiers; }


    public static class Serializer extends LootFunction.Serializer<SetAttributes> {
        protected Serializer() { super(new ResourceLocation("set_attributes"), SetAttributes.class); }

        @Override
        public void serialize(JsonObject json, SetAttributes type, JsonSerializationContext context) {
            JsonArray array = new JsonArray();
            for (LootAttributeModifier mod : type.modifiers) {
                array.add(mod.serialize(context));
            }
            json.add("modifiers", array);
        }

        @Override
        public SetAttributes deserialize(JsonObject json, JsonDeserializationContext context, List<LootCondition> conditions) {
            JsonArray array = JsonUtils.getJsonArray(json, "modifiers");
            LootAttributeModifier[] modifiers = new LootAttributeModifier[array.size()];

            JsonElement element;
            int i = 0;
            for (Iterator iter = array.iterator(); iter.hasNext(); i++) {
                element = (JsonElement) iter.next();
                modifiers[i] = LootAttributeModifier.deserialize(JsonUtils.getJsonObject(element, "modifier"), context);
            }
            if (modifiers.length == 0) {
                throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
            } else {
                return new SetAttributes(Arrays.asList(modifiers), conditions);
            }
        }
    }
}
