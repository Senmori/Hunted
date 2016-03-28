package net.senmori.hunted.loot.storage;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;

public class ResourceLocation {
	
	protected final String resourceDomain;
	protected final String resourcePath;
	
	protected ResourceLocation(String... resourceName) {
		this.resourceDomain = StringUtils.isEmpty(resourceName[0]) ? "minecraft" : resourceName[0].toLowerCase();
		this.resourcePath = resourceName[1];
		Validate.notNull(this.resourcePath);
	}
	
	public ResourceLocation(String resourceDomain, String resourcePath) {
		this(new String[] {resourceDomain, resourcePath});
	}
	
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
