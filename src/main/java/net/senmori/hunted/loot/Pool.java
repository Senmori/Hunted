package net.senmori.hunted.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.entry.Entry;
import net.senmori.hunted.loot.entry.EntryType;


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
	
	public List<Entry> getEntries() {
		return entries;
	}
	
	public List<LootCondition> getConditions() {
		return conditions;
	}
	
	public void addEntry(EntryType type, String name, int weight) {
		entries.add(new Entry(type, name, weight));
	}
	
	public void addEntry(Entry entry) {
		entries.add(entry);
	}
	
	public void setRolls(int amount) {
		rolls.put("exact", amount);
	}
	
	public void setRolls(int min, int max) {
		rolls.put("min", min);
		rolls.put("max", max);
	}
	
	public void setBonusRolls(int num) {
		bonusRolls.put("exact", num);
	}
	
	public void setBonusRolls(int min, int max) {
		bonusRolls.put("min", min);
		bonusRolls.put("max", max);
	}
	
	
    public JsonObject toJsonObject() {
		// insert rolls
		if(rolls.containsKey("min") && rolls.containsKey("max")) {
			JsonObject rollsList = new JsonObject();
			rollsList.addProperty("min", rolls.get("min"));
			rollsList.addProperty("max", rolls.get("max"));
			pool.add("rolls", rollsList);
		} else {
			pool.addProperty("rolls", rolls.get("exact"));
		}
		
		// bonus rolls
		if(bonusRolls.containsKey("min") && bonusRolls.containsKey("max")) {
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
}
