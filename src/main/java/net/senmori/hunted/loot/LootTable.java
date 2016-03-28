package net.senmori.hunted.loot;

import java.util.List;
import java.util.Random;

import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

public class LootTable {
	
	public static final LootTable EMPTY_LOOT_TABLE = new LootTable(new LootPool[0]);
	private LootPool[] pools;
	
	public LootTable(LootPool[] pools) {
		this.pools = pools;
	}
	
	public List<ItemStack> generateLootForPools(Random rand) {
		List<ItemStack> list = Lists.<ItemStack>newArrayList();
		
		return list;
	}

}
