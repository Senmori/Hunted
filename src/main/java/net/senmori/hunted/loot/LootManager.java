package net.senmori.hunted.loot;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import net.senmori.hunted.loot.storage.ResourceLocation;

public class LootManager {

	private JavaPlugin plugin;
	private String defaultDomain;
	private Map<ResourceLocation, LootTable> resources;
	
	public LootManager(JavaPlugin plugin) {
		this.plugin = plugin;
		resources = new HashMap<>();
		defaultDomain = plugin.getName().toLowerCase().replace(' ', '_');
	}
	
	public void createLootTable(ResourceLocation resource, String fileName) {
		resources.put(resource, new LootTable(resource, fileName));
	}
	
	public void addResource(String worldName, String resourcePath, String fileName) {
		ResourceLocation loc = new ResourceLocation(worldName, null, resourcePath);
		resources.put(loc, new LootTable(loc, fileName));
	}
	
	public ResourceLocation getResource(String resourcePath) {
		for(ResourceLocation r : resources.keySet()) {
			if(r.getResourcePath().equals(resourcePath)) return r;
		}
		return null;
	}

}
