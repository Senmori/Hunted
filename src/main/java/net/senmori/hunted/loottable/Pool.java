package net.senmori.hunted.loottable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.senmori.hunted.loottable.condition.LootCondition;
import net.senmori.hunted.loottable.entry.Entry;
import net.senmori.hunted.loottable.entry.EntryType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Pool {
	
	private JSONObject pool;
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
		pool = new JSONObject();
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
	
	@SuppressWarnings("unchecked")
    public JSONObject toJSONObject() {
		// insert rolls
		if(!useRollsAbs) {
			JSONObject rollsList = new JSONObject();
			rollsList.put("min", rollsMin);
			rollsList.put("max", rollsMax);
			pool.put("rolls", rollsList);
		} else {
			pool.put("rolls", rollsAbs);
		}
		
		// bonus rolls
		if(useBonusRolls) {
			if(useBonusExact) {
				pool.put("bonus_rolls", bonusRollsAbs);
			} else {
				JSONObject bonusRolls = new JSONObject();
				bonusRolls.put("min", bonusRollsMin);
				bonusRolls.put("max", bonusRollsMax);
				pool.put("bonus_rolls", bonusRolls);
			}
		}
		
		// add entries
		if(entries.size() > 0) {
			// add entries
			JSONArray entriesArray = new JSONArray();
			for(Entry e : entries) {
				entriesArray.add(e.toJSONOBject());
			}
			pool.put("entries", entriesArray);
		}
		
		
		//add conditions, if present
		if(conditions.size() > 0) {
			JSONArray cArray = new JSONArray();
			for(LootCondition c : conditions) {
				cArray.add(c.toJSONObject());
			}
			pool.put("conditions", cArray);
		}
		return pool;
	}
}
