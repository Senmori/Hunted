package net.senmori.hunted.loottable.entry;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.loottable.condition.LootCondition;
import net.senmori.hunted.loottable.function.LootFunction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Entry {
	
	private List<LootCondition> conditions;
	private List<LootFunction> functions;
	private EntryType type;
	private String name;
	private int weight;
	private int quality;
	private boolean useQuality = false;
	
	
	public Entry(EntryType type, String name, int weight) {
		conditions = new ArrayList<>();
		functions = new ArrayList<>();
		this.type = type;
		this.name = name;
		this.weight = weight;
	}
	
	public Entry(EntryType type, int weight) {
		conditions = new ArrayList<>();
		functions = new ArrayList<>();
		this.type = type;
		this.weight = weight;
	}
	
	public Entry setType(EntryType type) {
		this.type = type;
		return this;
	}
	
	public Entry setName(String name) {
		this.name = name;
		return this;
	}
	
	public Entry setWeight(int weight) {
		this.weight = weight;
		return this;
	}
	
	public Entry setQuality(int quality) {
		this.quality = quality;
		useQuality = true;
		return this;
	}
	
	public void addCondition(LootCondition condition) {
		conditions.add(condition);
	}
	
	public void addFunction(LootFunction function) {
		functions.add(function);
	}
	
	public EntryType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public List<LootCondition> getConditions() {
		return conditions;
	}
	
	public List<LootFunction> getFunctions() {
		return functions;
	}
	
	public JsonObject toJsonObject() {
		JsonObject entry = new JsonObject();
		entry.addProperty("type", type.getType());
		if(!type.equals(EntryType.EMPTY)) {
			entry.addProperty("name", name);
		}
		entry.addProperty("weight", weight);
		if(useQuality) {
			entry.addProperty("quality", quality);
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
}
