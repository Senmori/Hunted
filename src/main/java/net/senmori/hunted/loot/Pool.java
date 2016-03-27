package net.senmori.hunted.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;

import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.entry.Entry;
import net.senmori.hunted.loot.entry.EntryType;
import net.senmori.hunted.loot.function.LootFunction;
import net.senmori.hunted.loot.utils.LootUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


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
	
	/* #######################
	 * Property methods
	 * #######################
	 */
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
	
	public void addCondition(LootCondition condition) {
		conditions.add(condition);
	}
	
	
	/* #######################
	 * Load/Save methods
	 * #######################
	 */
	
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
		// check for rolls
    	if(pool.get("rolls").isJsonPrimitive()) {
    		rolls.clear();
    		setRolls(pool.get("rolls").getAsInt());
    	} else {
    		rolls.clear();
    		setRolls(pool.get("rolls").getAsJsonObject().get("min").getAsInt(), pool.get("rolls").getAsJsonObject().get("max").getAsInt());
    	}
    	// bonus rolls (these are optional, so null checking is required)
    	if(pool.get("bonus_rolls") != null) {
    		if(pool.get("bonus_rolls").isJsonPrimitive()) {
    			setBonusRolls(pool.get("bonus_rolls").getAsInt());
    		} else {
    			setBonusRolls(pool.get("bonus_rolls").getAsJsonObject().get("min").getAsInt(), pool.get("bonus_rolls").getAsJsonObject().get("max").getAsInt());
    		}
    	}
    	if(pool.get("entries") != null) {// should never be empty, but weirder things have happened
			Bukkit.broadcastMessage("    "); // debugging purposes only
    		JsonArray entries = pool.get("entries").getAsJsonArray();
    		int num = 0;
	    	for(JsonElement entryElement : entries) {
	    		// Entry
	    		JsonObject currentEntry = entryElement.getAsJsonObject();
	    		Entry cEntry = new Entry().fromJson(currentEntry);
	    		Bukkit.broadcastMessage("Entry (" + num + "): {Type: " + cEntry.getType().toString() + " Name: " + cEntry.getName() + " Weight: " + cEntry.getWeight() + "}");
	    			// check currentEntry for functions
	    			if(currentEntry.get("functions") != null) {
	    				JsonArray functions = currentEntry.get("functions").getAsJsonArray();
	    				for(JsonElement funcElement : functions) {
	    					JsonObject currentFunction = funcElement.getAsJsonObject();
	    					LootFunction currFunction = LootUtil.getFunction(currentFunction.get("function").getAsString());
	    					Bukkit.broadcastMessage("Function (" + num + "): Type: " + currFunction.getType());
	    						
	    						// check function for conditions, because we all love recursion
	    						if(currentFunction.get("conditions") != null) {
	    							JsonArray conditions = currentFunction.get("conditions").getAsJsonArray();
	    							for(JsonElement cond : conditions) {
	    								JsonObject currCondition = cond.getAsJsonObject();
	    								Bukkit.broadcastMessage("Function Condition (" + num + ") Type: " + currCondition.get("condition").getAsString());
	    								currFunction.addCondition(LootUtil.getCondition(currCondition.get("condition").getAsString()));
	    							}
	    						}
	    					cEntry.addFunction(currFunction);
	    				} 
	    			} // end function section
	    			
	    			// check currentEntry for conditions, fml.
	    			if(currentEntry.get("conditions") != null) {
	    				JsonArray entryConditions = currentEntry.get("conditions").getAsJsonArray();
	    				for(JsonElement cond : entryConditions) {
	    					JsonObject currentCondition = cond.getAsJsonObject();
	    					Bukkit.broadcastMessage("Entry Condition (" + num + "): Type: " + currentCondition.get("condition").getAsString());
	    					cEntry.addCondition(LootUtil.getCondition(currentCondition.get("condition").getAsString()));
	    				}
	    			}
    			addEntry(cEntry);
    			Bukkit.broadcastMessage("Entry #" + num + ": Conditions: " + cEntry.getConditions().size() + " | Functions: " + cEntry.getFunctions().size());
    			Bukkit.broadcastMessage("Entry #" + num + " loaded!");
    			Bukkit.broadcastMessage("Pool entries: " + getEntries().size());
    			num++;
    			Bukkit.broadcastMessage("    ");// debugging purposes only
			} // end entries loop
    	}
		Bukkit.broadcastMessage("Loaded all entries");
    	if(pool.get("conditions") != null) {
	    	for(JsonElement conditionElement : pool.get("conditions").getAsJsonArray()) {
				JsonObject cond = conditionElement.getAsJsonObject();
				conditions.add(LootUtil.getCondition(cond.get("condition").getAsString()).fromJsonObject(cond));
			}	
    	} 
    	Bukkit.broadcastMessage("Pool loaded " + entries.size() + " entries, and " + conditions.size() + " conditions");
	return this;
    }
    
    
    /* #################
     * Getters
     * #################
     */
	public List<Entry> getEntries() {
		return entries;
	}
	
	public List<LootCondition> getConditions() {
		return conditions;
	}
}
