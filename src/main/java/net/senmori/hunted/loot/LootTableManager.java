package net.senmori.hunted.loot;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import net.senmori.hunted.loot.storage.ResourceLocation;

import com.google.common.base.Charsets;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class LootTableManager {
	
	private static final Gson GSON_INSTANCE = null;
	private final LoadingCache<ResourceLocation, LootTable> registeredLootTables = CacheBuilder.newBuilder().<ResourceLocation, LootTable>build(new LootTableManager.Loader());
	private final String baseFolder = "world/data/loot_tables/";
	
	public LootTable getLootTableFromLocation(ResourceLocation resource) {
		return (LootTable)this.registeredLootTables.getUnchecked(resource);
	}
	
	public void reloadLootTable() {
		this.registeredLootTables.invalidateAll();
		
		for(ResourceLocation resourceLocation : LootTableList.getAll()) {
			this.getLootTableFromLocation(resourceLocation);
		}
	}
	
	class Loader extends CacheLoader<ResourceLocation, LootTable> {
		
		private Loader() {};
		
		@Override
        public LootTable load(ResourceLocation location) throws Exception {
			if(location.getResourcePath().contains(".")) {
				Bukkit.getLogger().log(Level.WARNING, "Invalid loot table name \'" + location + "\' (can\'t contain periods)");
				return LootTable.EMPTY_LOOT_TABLE;
			} else {
				LootTable lootTable = this.loadLootTable(location);
				
				if(lootTable == null) {
					lootTable = this.loadBuiltInTable(location);
				}
				
				if(lootTable == null) {
					Bukkit.getLogger().log(Level.WARNING, "Couldn\'t find resource table " + location);
					return LootTable.EMPTY_LOOT_TABLE;
				}
				return lootTable;
			}
        }
		
		private LootTable loadLootTable(ResourceLocation resource) {
			File file = new File(new File(LootTableManager.this.baseFolder, resource.getResourceDomain() + File.separator), resource.getResourcePath() + ".json");
			
			if(file.exists()) {
				if(file.isFile()) {
					String s;
					
					try {
						s = Files.toString(file, Charsets.UTF_8);
					} catch(IOException e) {
						Bukkit.getLogger().log(Level.WARNING, "Couldn't load the loot table at " + resource + " from " + file, e);
						return LootTable.EMPTY_LOOT_TABLE;
					}
					
					try {
						return (LootTable)LootTableManager.GSON_INSTANCE.fromJson(s, LootTable.class);
					} catch(JsonParseException e) {
						Bukkit.getLogger().log(Level.SEVERE, "Couldn\'t load loot table " + resource + " from " + file, (Throwable)e);
						return LootTable.EMPTY_LOOT_TABLE;
					}
				} else {
					Bukkit.getLogger().log(Level.WARNING, "Expected to find loot table " + resource + "at " + file + " but it is a folde");
					return LootTable.EMPTY_LOOT_TABLE;
				}
			} else {
				return null;
			}
		}
		
		private LootTable loadBuiltInTable(ResourceLocation location) {
			URL url = LootTableManager.class.getResource(LootTableManager.this.baseFolder + location.getResourceDomain() + File.separator + location.getResourcePath() + ".json");
			
			if(url != null) {
				String s;
				
				try {
					s = Resources.toString(url, Charsets.UTF_8);
				} catch(IOException e) {
					Bukkit.getLogger().log(Level.WARNING, "Couldn\'t load loot table " + location + " from " + url, (Throwable)e);
					return LootTable.EMPTY_LOOT_TABLE;
				}
				
				try {
					return (LootTable)LootTableManager.GSON_INSTANCE.fromJson(s, LootTable.class);
				}catch(JsonParseException e) {
					Bukkit.getLogger().log(Level.WARNING, "Couldn't load loot table " + location + " from " + url);
					return LootTable.EMPTY_LOOT_TABLE;
				}
			} else {
				return null;
			}
        }
		
	}
}
