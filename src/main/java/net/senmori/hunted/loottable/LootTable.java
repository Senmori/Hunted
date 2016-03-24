package net.senmori.hunted.loottable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class LootTable {
	
	private File lootTableFile;
	private JsonObject root;
	private List<Pool> pools;
	
	public LootTable(String worldName, String namespace, String file) {
		World world = Bukkit.getWorld(worldName);
		if( world == null) {
			world = Bukkit.getWorlds().get(0);
		}
		String path = world.getWorldFolder().getPath() + File.separator + "data" + File.separator + "loot_tables" + File.separator + namespace + File.separator;
		File lootTableDir = new File(path);
		if(!lootTableDir.exists()) {
			lootTableDir.mkdirs();
		}
		lootTableFile = new File(path + file);
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
	
    public void addPool(Pool pool) {
		pools.add(pool);
	}
    
    public void write() {
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String pretty = gson.toJson(toJson());
    	Bukkit.broadcastMessage(pretty);
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
    
    public List<Pool> getPools() {
    	return pools;
    }

    public JsonObject toJson() {
    	JsonArray rootArray = new JsonArray();
    	for(Pool p : pools) {
    		rootArray.add(p.toJsonObject());
    	}
    	root.add("pools", rootArray);
    	return root;
    }
    
    public File getFile() {
    	return lootTableFile;
    }
}
