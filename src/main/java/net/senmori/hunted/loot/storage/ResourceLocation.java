package net.senmori.hunted.loot.storage;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;

public class ResourceLocation {

	protected final String resourceWorld;
	protected final String resourceDomain;
	protected final String resourcePath;

	/**
	 * Generates a new ResourceLocation given an array of strings. <br>
	 * Index 0: resource domain<br>
	 * Index 1: resource Path<br>
	 * i.e. {"minecraft", "chests/jungle_temple"} <br>
	 * Do not include ".json"
	 * @param resourceName
     */
	protected ResourceLocation(String... resourceName) {
		this.resourceDomain = StringUtils.isEmpty(resourceName[0]) ? "minecraft" : resourceName[0].toLowerCase();
		this.resourcePath = resourceName[1];
		this.resourceWorld = Bukkit.getWorld("world").getName();
		Validate.notNull(this.resourcePath);
	}

	/**
	 * Generates a new ResourceLocation give the following parameters<br>
	 * * Do not include ".json"
	 * @param resourceDomain
	 * @param resourcePath
     */
	public ResourceLocation(String resourceDomain, String resourcePath) {
		this(new String[] {resourceDomain, resourcePath});
	}

	/**
	 * Generates a ResourceLocation from the given parameter. <br>
	 * Valid Parameter formatting: [domain]:[path] <br>
	 * @param resourceName
     */
	public ResourceLocation(String resourceName) {
		this(splitName(resourceName));
	}
	
	
	protected static String[] splitName(String toSplit) {
		String[] string = new String[] {"minecraft", toSplit};
		int i = toSplit.indexOf(58);
		
		if(i >= 0) {
			string[1] = toSplit.substring(i + 1, toSplit.length());
			
			if(i > 1) {
				string[0] = toSplit.substring(0,i);
			}
		}
		return string;
	}
	
	public String getResourcePath() {
		return this.resourcePath;
	}
	public String getResourceDomain() {
		return this.resourceDomain;
	}

	public String getResourceWorld() { return this.resourceWorld; }
	
	public String toString() {
		return this.resourceDomain + ":" + this.resourcePath;
	}
	
	public boolean equals(Object object) {
		if(this == object) {
			return true;
		} else if(!(object instanceof ResourceLocation)) {
			return false;
		} else {
			ResourceLocation resourceLocation = (ResourceLocation)object;
			return this.resourceDomain.equals(resourceLocation.resourceDomain) && this.resourcePath.equals(resourceLocation.resourcePath);
		}
	}
}
