package net.senmori.hunted.loot.storage;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;

public class ResourceLocation {
	
	private String resourceDomain;
	private String resourcePath;
	private String world;
	/**
	 * A new Resource Location for Loot Tables.
	 * @param domain - the namespace to use. 'null' defaults to "minecraft"
	 * @param path - the path to the resource, including trailing slash
	 */
	public ResourceLocation(String world, String domain, String path) {
		this.world = world == null ? Bukkit.getWorlds().get(0).getName() : world;
		this.resourceDomain = domain == null ? "minecraft" : domain;
		this.resourcePath = path;
		Validate.notNull(this.resourcePath);
	}
	
	public String getResourceDomain() {
		return resourceDomain;
	}
	
	public String getResourcePath() {
		return resourcePath;
	}
	
	public String getWorldName() {
		return world;
	}
	
	public String toString() {
		return resourceDomain + ":" + resourcePath;
	}
}
