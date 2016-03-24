package net.senmori.hunted.loottable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.senmori.hunted.loottable.condition.LootCondition;
import net.senmori.hunted.loottable.entry.Entry;
import net.senmori.hunted.loottable.entry.EntryType;


public class Pool {
	
	private JsonObject pool;
	/** 
	 * Contains all the entries for this pool </br>
	 * - String is required for easy retrieval of an Entry
	 */
	private List<Entry> entries;
	private List<LootCondition> conditions;
	private int rollsAbs, rollsMin, rollsMax;
	private int bonusRollsAbs, bonusRollsMin, bonusRollsMax;
	private boolean useBonusRolls, useBonusExact, useRollsAbs;
	
	public Pool() {
		pool = new JsonObject();
		entries = new ArrayList<>(); // ensure it remains in the same order we add stuff
		conditions = new ArrayList<>();
		rollsAbs = 1;
		useRollsAbs = true;
		useBonusRolls = false;
		useBonusExact = true;
		
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
		rollsAbs = amount;
	}
	
	public void setRolls(int min, int max) {
		rollsMin = min;
		rollsMax = max;
		useRollsAbs = false;
	}
	
	public void setBonusRolls(int num) {
		bonusRollsAbs = num;
		useBonusRolls = true;
	}
	
	public void setBonusRolls(int min, int max) {
		bonusRollsMin = min;
		bonusRollsMax = max;
		useBonusExact = false;
		useBonusRolls = true;
	}
	
	
    public JsonObject toJsonObject() {
		// insert rolls
		if(!useRollsAbs) {
			JsonObject rollsList = new JsonObject();
			rollsList.addProperty("min", rollsMin);
			rollsList.addProperty("max", rollsMax);
			pool.add("rolls", rollsList);
		} else {
			pool.addProperty("rolls", rollsAbs);
		}
		
		// bonus rolls
		if(useBonusRolls) {
			if(useBonusExact) {
				pool.addProperty("bonus_rolls", bonusRollsAbs);
			} else {
				JsonObject bonusRolls = new JsonObject();
				bonusRolls.addProperty("min", bonusRollsMin);
				bonusRolls.addProperty("max", bonusRollsMax);
				pool.add("bonus_rolls", bonusRolls);
			}
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
