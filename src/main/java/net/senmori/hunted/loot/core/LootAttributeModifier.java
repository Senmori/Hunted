package net.senmori.hunted.loot.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Senmori on 4/14/2016.
 */
public class LootAttributeModifier {

    protected String modifierName;
    protected String attributeName;
    protected int operation;
    protected RandomValueRange amount;
    protected UUID uuid;
    protected Set<EquipmentSlot> equipmentSlots;

    public LootAttributeModifier(String modifierName, String attributeName, int operation, RandomValueRange randomAmount, UUID uuid, Set<EquipmentSlot> validEquipmentSlots) {
        this.modifierName = modifierName;
        this.attributeName = attributeName;
        this.operation = operation;
        this.amount = randomAmount;
        this.uuid = uuid;
        this.equipmentSlots = validEquipmentSlots;
    }

    public LootAttributeModifier setAmount(RandomValueRange amount) {
        this.amount = amount;
        return this;
    }

    public String getModifierName() { return this.modifierName; }

    public String getAttributeName() { return this.attributeName; }

    public int getOperation() { return this.operation; }

    public RandomValueRange getAmount() { return this.amount; }

    public UUID getUuid() { return this.uuid; }

    public Set<EquipmentSlot> getEquipmentSlots() { return this.equipmentSlots; }


    public JsonObject serialize(JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.modifierName);
        json.addProperty("attribute", this.attributeName);
        json.addProperty("operation", getOperationFromNum(this.operation));
        json.add("amount", context.serialize(this.amount));
        if (this.uuid != null) {
            json.addProperty("uuid", this.uuid.toString());
        }

        if (this.equipmentSlots.size() > 0) {
            JsonArray array = new JsonArray();
            for (EquipmentSlot slot : equipmentSlots) {
                array.add(new JsonPrimitive(slot.name()));
            }
            json.add("slot", array);
        }
        return json;
    }

    public static LootAttributeModifier deserialize(JsonObject json, JsonDeserializationContext context) {
        String modifierName = JsonUtils.getString(json, "name");
        String attributeName = JsonUtils.getString(json, "attribute");
        int operation = LootAttributeModifier.getOperationFromString(JsonUtils.getString(json, "operation"));
        RandomValueRange randomAmount = JsonUtils.deserializeClass(json, "amount", context, RandomValueRange.class);
        UUID uuid = null;
        EquipmentSlot[] slots;
        Set<EquipmentSlot> validSlots = new HashSet<>();
        if (JsonUtils.isString(json, "slot")) {
            slots = new EquipmentSlot[]{EquipmentSlot.valueOf(JsonUtils.getString(json, "slot"))};
        } else {
            if (!(JsonUtils.isJsonArray(json, "slot"))) {
                throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either a string or array of strings");
            }

            JsonArray array = JsonUtils.getJsonArray(json, "slot");
            slots = new EquipmentSlot[array.size()];
            JsonElement element;
            int i = 0;
            for (Iterator iter = array.iterator(); iter.hasNext(); i++) {
                element = (JsonElement) iter.next();
                slots[i] = EquipmentSlot.valueOf(JsonUtils.getString(element, "slot"));
            }
            if (slots.length == 0) {
                throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry");
            }
            for (EquipmentSlot s : slots) {
                validSlots.add(s);
            }
        }
        if (json.has("id")) {
            String id = JsonUtils.getString(json, "id");
            try {
                uuid = UUID.fromString(id);
            } catch (IllegalArgumentException e) {
                throw new JsonSyntaxException("Invalid attribute modifier id \'" + id + "\' (must be UUID format, with dashes)");
            }
        }
        return new LootAttributeModifier(modifierName, attributeName, operation, randomAmount, uuid, validSlots);
    }

    private static String getOperationFromNum(int operation) {
        switch (operation) {
            case 0:
                return "addition";
            case 1:
                return "mutliply_base";
            case 2:
                return "multiply_total";
            default:
                throw new IllegalArgumentException("Unknown attribute modifier operation " + operation);
        }
    }

    private static int getOperationFromString(String operation) {
        switch (operation.toLowerCase()) {
            case "addition":
                return 0;
            case "multiply_base":
                return 1;
            case "multiply_total":
                return 2;
            default:
                throw new JsonSyntaxException("Unknown attribute modifier operation " + operation);
        }
    }
}
