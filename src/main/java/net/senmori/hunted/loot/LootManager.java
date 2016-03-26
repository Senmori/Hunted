package net.senmori.hunted.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import net.senmori.hunted.loot.storage.ResourceLocation;

public class LootManager {

	private JavaPlugin plugin;
	private String defaultDomain;
	private List<ResourceLocation> resources;
	
	public LootManager(JavaPlugin plugin) {
		this.plugin = plugin;
		resources = new ArrayList<>();
		defaultDomain = plugin.getName().toLowerCase().replace(' ', '_');
	}
	
	/** Add a new {@link ResourceLocation} and {@link LootTable} */
	public void addResource(ResourceLocation resource, String fileName) {
		resource.addLootTable(new LootTable(resource, fileName));
		resources.add(resource);
	}
	
	/** Add a new {@link ResourceLocation} and {@link LootTable} */
	public void addResource(String worldName, String resourcePath, String fileName) {
		ResourceLocation loc = new ResourceLocation(worldName, null, resourcePath);
		loc.addLootTable(new LootTable(loc,fileName));
		resources.add(loc);
	}
	
	/** Get's the LootTable with the given name. 
	 * @param fileName - the file to look for.
	 * @return
	 */
	public LootTable getLootTable(String fileName) {
		for(ResourceLocation r : resources) {
			for(LootTable t : r.getTables()) {
				if(t.getName().equalsIgnoreCase(fileName)) return t;
			}
		}
		return null;
	}
	
	/**
	 * Get a {@link ResourceLocation} that matches the given path
	 * @param resourcePath
	 * @return
	 */
	public ResourceLocation getResource(String resourcePath) {
		for(ResourceLocation r : resources) {
			if(r.getResourcePath().equals(resourcePath)) return r;
		}
		return null;
	}

}
