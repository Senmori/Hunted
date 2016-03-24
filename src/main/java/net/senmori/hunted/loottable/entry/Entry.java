package net.senmori.hunted.loottable.entry;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.loottable.condition.LootCondition;
import net.senmori.hunted.loottable.function.LootFunction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONOBject() {
		JSONObject entry = new JSONObject();
		entry.put("type", type.getType());
		if(!type.equals(EntryType.EMPTY)) {
			entry.put("name", name);
		}
		entry.put("weight", weight);
		if(useQuality) {
			entry.put("quality", quality);
		}
		
		if(conditions != null && conditions.size() > 0) {
			JSONArray conditionsArray = new JSONArray();
			for(LootCondition c : conditions) {
				conditionsArray.add(c.toJSONObject());
			}
			entry.put("conditions", conditionsArray);
		}
		
		if(functions != null && functions.size() > 0) {
			JSONArray functionsArray = new JSONArray();
			for(LootFunction f : functions) {
				functionsArray.add(f.toJSONObject());
			}
			entry.put("functions", functionsArray);
		}
		return entry;
	}
}
