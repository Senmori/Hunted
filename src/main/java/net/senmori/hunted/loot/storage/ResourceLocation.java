package net.senmori.hunted.loot.storage;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.loot.LootTable;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;

public class ResourceLocation {
	
	private String resourceDomain;
	private String resourcePath;
	private String world;
	private List<LootTable> associatedTables;
	/**
	 * A new Resource Location for Loot Tables.
	 * @param domain - the namespace to use. 'null' defaults to "minecraft"
	 * @param path - the path to the resource, including trailing slash
	 */
	public ResourceLocation(String world, String domain, String path) {
		associatedTables = new ArrayList<>();
		this.world = world == null ? Bukkit.getWorlds().get(0).getName() : world;
		this.resourceDomain = domain == null ? "minecraft" : domain;
		this.resourcePath = path;
		Validate.notNull(this.resourcePath);
	}
	
	public void addLootTable(LootTable table) {
		associatedTables.add(table);
	}
	
	public LootTable getLootTable(String name) {
		for(LootTable t : associatedTables) {
			if(t.getName().equalsIgnoreCase(name)) return t;
		}
		return null;
	}
	
	/** Get the namespace of this location. For Spigot plugins, this is the plugin's name in lowercase, and no spaces. */
	public String getResourceDomain() {
		return resourceDomain;
	}
	
	/** Get the path, after the namespace folder, to the lowest level of directory for this ResourceLocation */
	public String getResourcePath() {
		return resourcePath;
	}
	
	/** Get the world this ResourceLocation is associated with */
	public String getWorldName() {
		return world;
	}
	
	/** Get a String ready to be input into commands in-game. (i.e. namespace:path/to/file) */
	public String toString() {
		return resourceDomain + ":" + resourcePath;
	}
	
	public List<LootTable> getTables() {
		return associatedTables;
	}
}
