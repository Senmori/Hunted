package net.senmori.hunted.loot.attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class LootAttribute {
	
	// static attributes for easy reference
	private static AttributeModifier maxHealthModifier = new AttributeModifier("generic.maxHealth", 20.0, Operation.ADD_NUMBER);
	private static AttributeModifier movementSpeedModifier = new AttributeModifier("generic.movementSpeed", 0.7, Operation.MULTIPLY_SCALAR_1);
	private static AttributeModifier armorModifier = new AttributeModifier("generic.armor", 0.0, Operation.ADD_NUMBER);
	private static AttributeModifier attackDamageModifier = new AttributeModifier("generic.attackDamage", 2.0, Operation.ADD_NUMBER);
	private static AttributeModifier genericLuckModifier = new AttributeModifier("generic.luck", 0.0, Operation.ADD_NUMBER);
	
	public static LootAttribute maxHealthAttribute = new LootAttribute(maxHealthModifier, LootAttributeSlot.MAINHAND);
	public static LootAttribute movementSpeedAttribute = new LootAttribute(movementSpeedModifier, LootAttributeSlot.FEET);
	public static LootAttribute armorAttribute = new LootAttribute(armorModifier, LootAttributeSlot.CHEST);
	public static LootAttribute attackDamageAttribute = new LootAttribute(attackDamageModifier, LootAttributeSlot.MAINHAND);
	public static LootAttribute genericLuckAttribute = new LootAttribute(maxHealthModifier, LootAttributeSlot.MAINHAND);
	
	
	private AttributeModifier modifier;
	//private LootAttributeSlot mainSlot;
	private List<LootAttributeSlot> possibleSlots;
	private boolean useRandAmounts;
	private double min, max;
	private String operation;
	
	public LootAttribute() {
		useRandAmounts = false;
		possibleSlots = new ArrayList<>();
	}
	
	public LootAttribute(AttributeModifier modifier, LootAttributeSlot slot) {
		this.modifier = modifier;
		//mainSlot = slot;
		useRandAmounts = false;
		possibleSlots = new ArrayList<>();
		possibleSlots.add(slot);
		operation = modifier.getOperation().equals(Operation.ADD_NUMBER)? "addition" : modifier.getOperation().equals(Operation.ADD_SCALAR) ? "multiply_base" : "multiply_total";
	}
	
	/** Set the slot the item must be in for the modifier to take effect */
	public LootAttribute mainSlot(LootAttributeSlot slot) {
		List<LootAttributeSlot> temp = possibleSlots;
		possibleSlots.clear();
		possibleSlots.set(0, slot);
		possibleSlots.addAll(temp);
		return this;
	}
	
	public void addSlot(LootAttributeSlot slot) {
		if(possibleSlots.contains(slot)) return;
		possibleSlots.add(slot);
	}
	
	public LootAttribute amount(double amount) {
		modifier = new AttributeModifier(modifier.getName(), amount, modifier.getOperation());
		return this;
	}
	
	public LootAttribute randomAmount(double min, double max) {
		this.min = min;
		this.max = max;
		useRandAmounts = true;
		return this;
	}
	
	public LootAttribute operation(Operation operation) {
		modifier = new AttributeModifier(modifier.getName(), modifier.getAmount(), operation);
		this.operation = modifier.getOperation().equals(Operation.ADD_NUMBER)? "addition" : modifier.getOperation().equals(Operation.ADD_SCALAR) ? "multiply_base" : "multiply_total";
		return this;
	}
	
	public JsonObject toJsonObject() {
		boolean slots = possibleSlots.size() > 1;
		JsonObject attribute = new JsonObject();
		attribute.addProperty("name", modifier.getName());
		attribute.addProperty("attribute", modifier.getName());
		attribute.addProperty("operation", operation);
		
		// add random amounts if applicable
		if(useRandAmounts) {
			JsonObject amounts = new JsonObject();
			amounts.addProperty("min", min);
			amounts.addProperty("max", max);
			attribute.add("amount", amounts);
		} else {
			attribute.addProperty("amount", modifier.getAmount());
		}
		attribute.addProperty("id", modifier.getUniqueId().toString());
		// set slots
		attribute.addProperty("slot", possibleSlots.get(0).toString().toLowerCase());
		if(slots) {
			JsonArray slotArray = new JsonArray();
			int index = 1;
			for(LootAttributeSlot s : possibleSlots) {
				slotArray.add(new JsonPrimitive(possibleSlots.get(index).toString().toLowerCase()));
				index++;
			}
			attribute.add("slot", slotArray);
		}
		return attribute;
	}
	
	@SuppressWarnings("unused")
    public LootAttribute fromJsonObject(JsonObject attribute) {
		AttributeModifier modifier;
		
		String name = attribute.get("name").getAsString();
		String attr = attribute.get("attribute").getAsString();
		String operation = attribute.get("operation").getAsString();
		Double amount = attribute.get("amount").getAsDouble();
		String id = null;
		if(attribute.get("id") != null) {
			id = attribute.get("id").getAsString();
		}
		if(attribute.get("slot").isJsonPrimitive()) { // only one slot selected for this attribute
			String slot = attribute.get("slot").getAsString();
			possibleSlots.add(LootAttributeSlot.valueOf(slot.toUpperCase()));
		} else if(attribute.get("slot").isJsonArray()) {
			JsonArray slots = attribute.get("slot").getAsJsonArray();
			while(slots.iterator().hasNext()) {
				if(!slots.iterator().hasNext()) break;
				String slot = slots.iterator().next().getAsString();
				possibleSlots.add(LootAttributeSlot.valueOf(slot.toUpperCase()));
			}
		}
		Operation op;
		switch(operation) {
			case "addition":
				op = Operation.ADD_NUMBER;
				break;
			case "multiply_base":
				op = Operation.ADD_SCALAR;
				break;
			case "multiply_total":
				op = Operation.MULTIPLY_SCALAR_1;
				break;
			default:
					op = Operation.ADD_NUMBER;
		}
		if(id != null) {
			modifier = new AttributeModifier(UUID.fromString(id), name, amount, op);
		} else {
			modifier = new AttributeModifier(name, amount, op);
		}
		return this;
	}
}
