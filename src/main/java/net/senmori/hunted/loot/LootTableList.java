package net.senmori.hunted.loot;

import java.util.Set;

import com.google.common.collect.Sets;

import net.senmori.hunted.loot.storage.ResourceLocation;

public class LootTableList {

	private static Set<ResourceLocation> lootTables = Sets.<ResourceLocation>newHashSet();
	
	
	public static ResourceLocation register(ResourceLocation id) {
		if(lootTables.add(id)) {
			return id;
		} else {
			throw new IllegalArgumentException(id + " is already a registered loot table!");
		}
	}
	
	public static Set<ResourceLocation> getAll() {
		return lootTables;
	}
}
