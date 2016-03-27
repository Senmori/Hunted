package net.senmori.hunted.loot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.condition.LootConditionType;
import net.senmori.hunted.loot.entry.Entry;
import net.senmori.hunted.loot.entry.EntryType;
import net.senmori.hunted.loot.storage.ResourceLocation;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LootTable {
	
	private File lootTableFile;
	private String fileName;
	private JsonObject root;
	private List<Pool> pools;
	private ResourceLocation resourceLocation;
	/**
	 * Creates a new Loot Table file
	 * @param resourceLocation - see {@link ResourceLocation}
	 * @param fileName - the actual file name, ".json" not needed
	 */
	public LootTable(ResourceLocation resourceLocation, String fileName) {
		this.fileName = fileName;
		this.resourceLocation = resourceLocation;
		// <world>/data/loot_tables/<domain>/
		String domainRootPath = resourceLocation.getWorldName() + File.separator + "data" + File.separator + "loot_tables" + File.separator + resourceLocation.getResourceDomain() + File.separator;

		File domainDir = new File(domainRootPath);
		if(!domainDir.exists()) {
			domainDir.mkdirs();
		}
		// create subFolders in <domain>/ directory
			domainDir = new File(domainRootPath + resourceLocation.getResourcePath() + File.separator);
			if(!domainDir.exists()) {
				domainDir.mkdirs();
			}
		lootTableFile = new File(domainDir + File.separator + fileName + ".json");
		if(!lootTableFile.exists()) {
			try {
	            lootTableFile.createNewFile();
            } catch (IOException e) {
	            e.printStackTrace();
            }
		}
		root = new JsonObject();
		pools = new ArrayList<>();
	}
	
    public LootTable loadFromFile() {
    	List<Pool> currPools = new ArrayList<>();
    	try {
    		JsonParser parser = new JsonParser();
    		JsonElement element = parser.parse(new FileReader(lootTableFile));
    		root = element.getAsJsonObject();
    	} catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	// parse file here, recreate all entries/functions/conditions
		for(JsonElement ele : root.get("pools").getAsJsonArray()) {
			JsonObject pool = ele.getAsJsonObject();
			Pool curr = new Pool().fromJson(pool);
			currPools.add(curr);
		}
    	pools.addAll(currPools); // add them to loottable here to allow time for recursion
    	Bukkit.broadcastMessage("Loaded " + pools.size() + " pool(s)");
    	return this;
    }
	
    public void addPool(Pool pool) {
		pools.add(pool);
	}
     
    /* ##########################
     * Editing functions
     * ##########################
     */
    /**
     * Returns the first entry with the given name
     * @param entryName
     * @return
     */
    public Entry getEntry(String entryName) {
    	if(pools.size() == 1) {
    		for(Entry e : pools.get(0).getEntries()) {
    			if(e.getName().equals(entryName)) return e;
    		}
    	}
    	return null;
    }
    
    /**
     * Returns the first entry of the given {@link EntryType}
     * @param type
     * @return
     */
    public Entry getEntry(EntryType type) {
    	if(pools.size() == 1) {
    		for(Entry e : pools.get(0).getEntries()) {
    			if(e.getType().equals(type)) return e;
    		}
    	}
    	return null;
    }
    
    /**
     * Returns the first {@link LootCondition} of the given [@link LootConditionType}
     * @param type
     * @return
     */
    public LootCondition getCondition(LootConditionType type) {
    	if(pools.size() == 1) {
    		for(LootCondition lc : pools.get(0).getConditions()) {
    			if(lc.getType().equals(type)) return lc;
    		}
    	}
    	return null;
    }
    
    
    /* ##########################
     * Writing methods
     * ##########################
     */
    public void write() {
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String pretty = gson.toJson(toJsonObject());
        try {
	        FileWriter fw = new FileWriter(lootTableFile);
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(pretty);
	        bw.flush();
	        bw.close();
        } catch (IOException e) {
	        e.printStackTrace();
        }
    }
    
    public JsonObject toJsonObject() {
    	JsonArray rootArray = new JsonArray();
    	for(Pool p : pools) {
    		rootArray.add(p.toJsonObject());
    	}
    	root.add("pools", rootArray);
    	return root;
    }
   
    /* ##############
     * Getters
     * ##############
     */
    public List<Pool> getPools() {
    	return pools;
    }
    
    public File getFile() {
    	return lootTableFile;
    }
    
    /** This method returns a string formatted for use in /blockdata or /entitydata commands 
     *  @return domain:path/to/file
    */
    public String toString() {
    	return resourceLocation.getResourceDomain() + ":" + resourceLocation.getResourcePath() + File.separator + FilenameUtils.removeExtension(lootTableFile.getName());
    }
    
    public String getName() {
    	return fileName;
    }
}
