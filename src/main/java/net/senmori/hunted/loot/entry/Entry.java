package net.senmori.hunted.loot.entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dpohvar.powernbt.api.NBTCompound;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.function.LootFunction;
import net.senmori.hunted.loot.function.SetCountFunction;
import net.senmori.hunted.loot.function.SetNBTFunction;
import net.senmori.hunted.loot.utils.LootUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Entry {
	
	private List<LootCondition> conditions;
	private List<LootFunction> functions;
	private EntryType type;
	private String name;
	private Map<String,Integer> modifiers;
	
	// used when displaying this entry in an inventory
	private Material defaultIcon;
	
	public Entry() {
		conditions = new ArrayList<>();
		functions = new ArrayList<>();
		modifiers = new HashMap<>();
	}
	
	public Entry(EntryType type, String name, int weight) {
		conditions = new ArrayList<>();
		functions = new ArrayList<>();
		modifiers = new HashMap<>();
		this.type = type;
		this.name = name;
		modifiers.put("weight", weight);
		assignDefaultIcon();
	}
	
	public Entry(ItemStack item, int weight) {
		this(EntryType.ITEM, item.getType().toString().toLowerCase(), weight);
		if(item.getAmount() > 1) {
			addFunction(new SetCountFunction().setCount(item.getAmount()));
		}
		NBTCompound tag = Hunted.getInstance().nbtManager.read(item);
		addFunction(new SetNBTFunction().setTag(tag.toString()));
		assignDefaultIcon();
	}
	
	public Entry(EntryType type, int weight) {
		conditions = new ArrayList<>();
		functions = new ArrayList<>();
		this.type = type;
		modifiers.put("weight", weight);
		assignDefaultIcon();
	}
	
	/* ##############################
	 *  ItemStack methods
	 * ##############################
	 */
	public ItemStack getIcon() {
		ItemStack icon = new ItemStack(defaultIcon);
		for(LootCondition  lc : getConditions()) {
			icon = lc.applyTo(icon);
		}
		
		for(LootFunction f : getFunctions()) {
			icon = f.applyTo(icon);
		}
		return icon;
	}
	
	private void assignDefaultIcon(){
		if(this.type.equals(EntryType.LOOT_TABLE)) {
			defaultIcon = Material.MAP;
			return;
		}
		if(this.type.equals(EntryType.EMPTY)) {
			defaultIcon = Material.STAINED_GLASS_PANE;
			return;
		}
		defaultIcon = Material.valueOf(name.toUpperCase());
	}
	/* ############################
	 *  Property methods
	 * ############################
	 */
	
	public Entry setType(EntryType type) {
		this.type = type;
		assignDefaultIcon();
		return this;
	}
	
	public Entry setName(String name) {
		this.name = name;
		assignDefaultIcon();
		return this;
	}
	
	public Entry setWeight(int weight) {
		modifiers.put("weight", weight);
		return this;
	}
	
	public Entry setQuality(int quality) {
		modifiers.put("quality", quality);
		return this;
	}
	
	public void addCondition(LootCondition condition) {
		conditions.add(condition);
	}
	
	public void addFunction(LootFunction function) {
		if(type.equals(EntryType.EMPTY)) return;
		functions.add(function);
	}
	
	/* ######################
	 *  To/From Json Methods
	 * ######################
	 */
	public JsonObject toJsonObject() {
		JsonObject entry = new JsonObject();
		entry.addProperty("type", type.getType());
		if(!type.equals(EntryType.EMPTY)) {
			entry.addProperty("name", name);
		}
		entry.addProperty("weight", modifiers.get("weight"));
		if(useQuality()) {
			entry.addProperty("quality", modifiers.get("quality"));
		}
		
		if(conditions != null && conditions.size() > 0) {
			JsonArray conditionsArray = new JsonArray();
			for(LootCondition c : conditions) {
				conditionsArray.add(c.toJsonObject());
			}
			entry.add("conditions", conditionsArray);
		}
		
		if(functions != null && functions.size() > 0) {
			JsonArray functionsArray = new JsonArray();
			for(LootFunction f : functions) {
				functionsArray.add(f.toJsonObject());
			}
			entry.add("functions", functionsArray);
		}
		return entry;
	}
	
	public Entry fromJson(JsonObject element) {
		type = EntryType.valueOf(element.get("type").getAsString().toUpperCase());
		name = element.get("name").getAsString();
		modifiers.put("weight", element.get("weight").getAsInt());
		if(element.get("quality") != null) {
			modifiers.put("quality", element.get("quality").getAsInt());
		}
		assignDefaultIcon();
/*		// check functions
		if(element.get("functions") != null) {
			for(JsonElement funcElement : element.get("functions").getAsJsonArray()) {
				JsonObject function = funcElement.getAsJsonObject();
				addFunction(LootUtil.getFunction(function.get("function").getAsString()).fromJsonObject(function));
			}
		}
		if(element.get("conditions") != null) {
			for(JsonElement conditionElement : element.get("conditions").getAsJsonArray()) {
				JsonObject cond = conditionElement.getAsJsonObject();
				addCondition(LootUtil.getCondition(cond.get("condition").getAsString()).fromJsonObject(cond));
			}	
		}*/
	return this;
	}
	
	/* ####################
	 * Getters
	 * ####################
	 */
	public EntryType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWeight() {
		return modifiers.get("weight");
	}
	
	public boolean useQuality() {
		return modifiers.containsKey("quality");
	}
	
	public int getQuality() {
		return modifiers.get("quality");
	}
	
	public List<LootCondition> getConditions() {
		return conditions;
	}
	
	public List<LootFunction> getFunctions() {
		return functions;
	}
}
