package net.senmori.hunted.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.entry.Entry;
import net.senmori.hunted.loot.entry.EntryType;
import net.senmori.hunted.loot.utils.LootUtil;


public class Pool {
	
	private JsonObject pool;
	/** 
	 * Contains all the entries for this pool </br>
	 * - String is required for easy retrieval of an Entry
	 */
	private List<Entry> entries;
	private List<LootCondition> conditions;
	private Map<String, Integer> rolls;
	private Map<String, Integer> bonusRolls;
	
	public Pool() {
		pool = new JsonObject();
		entries = new ArrayList<>(); // ensure it remains in the same order we add stuff
		conditions = new ArrayList<>();
		rolls = new HashMap<>();
		bonusRolls = new HashMap<>();
		rolls.put("exact", 1);
	}
	
	public void addEntry(EntryType type, String name, int weight) {
		entries.add(new Entry(type, name, weight));
	}
	
	public void addEntry(Entry entry) {
		entries.add(entry);
	}
	
	public void setRolls(int amount) {
		rolls.clear();
		rolls.put("exact", amount);
	}
	
	public void setRolls(int min, int max) {
		rolls.clear();
		rolls.put("min", min);
		rolls.put("max", max);
	}
	
	public void setBonusRolls(int num) {
		bonusRolls.clear();
		bonusRolls.put("exact", num);
	}
	
	public void setBonusRolls(int min, int max) {
		bonusRolls.clear();
		bonusRolls.put("min", min);
		bonusRolls.put("max", max);
	}
	
	
    public JsonObject toJsonObject() {
		// insert rolls
		if(!rolls.containsKey("exact")) {
			JsonObject rollsList = new JsonObject();
			rollsList.addProperty("min", rolls.get("min"));
			rollsList.addProperty("max", rolls.get("max"));
			pool.add("rolls", rollsList);
		} else {
			pool.addProperty("rolls", rolls.get("exact"));
		}
		
		// bonus rolls
		if(!bonusRolls.containsKey("exact")) {
			JsonObject bRolls = new JsonObject();
			bRolls.addProperty("min", bonusRolls.get("min"));
			bRolls.addProperty("max", bonusRolls.get("max"));
			pool.add("bonus_rolls", bRolls);
		} else if(bonusRolls.containsKey("exact")) {
			pool.addProperty("rolls", bonusRolls.get("exact"));
		}
		
		// add entries
		if(entries.size() > 0) {
			// add entries
			JsonArray entriesArray = new JsonArray();
			for(Entry e : entries) {
				entriesArray.add(e.toJsonObject());
			}
			pool.add("entries", entriesArray);
		}
		
		//add conditions, if present
		if(conditions.size() > 0) {
			JsonArray cArray = new JsonArray();
			for(LootCondition c : conditions) {
				cArray.add(c.toJsonObject());
			}
			pool.add("conditions", cArray);
		}
		return pool;
	}
    
    public Pool fromJson(JsonObject pool) {
		// check for entries
		if(pool.get("entries").isJsonArray()) {
			for(JsonElement entryElement : pool.get("entries").getAsJsonArray()) {
				if(!entryElement.isJsonObject()) continue;
				JsonObject curr = entryElement.getAsJsonObject();
				entries.add(new Entry().fromJson(curr));
			}
		}
		
		// check for conditions
		if(pool.get("conditions").isJsonArray()) {
			for(JsonElement conditionElement : pool.get("conditions").getAsJsonArray()) {
				if(!conditionElement.isJsonObject()) continue;
				JsonObject cond = conditionElement.getAsJsonObject();
				conditions.add(LootUtil.getCondition(cond.get("condition").getAsString()).fromJsonObject(cond));
			}
		}
	return this;
    }
    
	public List<Entry> getEntries() {
		return entries;
	}
	
	public List<LootCondition> getConditions() {
		return conditions;
	}
}
