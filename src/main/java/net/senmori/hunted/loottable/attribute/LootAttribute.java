package net.senmori.hunted.loottable.attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;

import com.google.gson.JsonObject;

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
	private LootAttributeSlot mainSlot;
	private boolean useRandAmounts;
	private double min, max;
	
	public LootAttribute(AttributeModifier modifier, LootAttributeSlot slot) {
		this.modifier = modifier;
		mainSlot = slot;
		useRandAmounts = false;
	}
	
	/** Set the slot the item must be in for the modifier to take effect */
	public LootAttribute setSlot(LootAttributeSlot slot) {
		mainSlot = slot;
		return this;
	}
	
	public LootAttribute setAmount(double amount) {
		modifier = new AttributeModifier(modifier.getName(), amount, modifier.getOperation());
		return this;
	}
	
	public LootAttribute setRandomAmount(double min, double max) {
		this.min = min;
		this.max = max;
		useRandAmounts = true;
		return this;
	}
	
	public JsonObject toJsonObject() {
		JsonObject attribute = new JsonObject();
		attribute.addProperty("name", modifier.getName());
		attribute.addProperty("attribute", modifier.getName());
		int operation = modifier.getOperation().equals(Operation.ADD_NUMBER)? 0 : modifier.getOperation().equals(Operation.ADD_SCALAR) ? 1 : 2;
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
		attribute.addProperty("slot", mainSlot.toString().toLowerCase());
		return attribute;
	}
}
