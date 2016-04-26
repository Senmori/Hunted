package net.senmori.hunted.loot.utils;

import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import net.minecraft.server.v1_9_R1.Item;
import net.minecraft.server.v1_9_R1.MinecraftKey;
import net.senmori.hunted.Hunted;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class LootUtils {

	private static List<Material> validBlocks;
	private static NBTManager manager;
	private static final String lootTableKey = "LootTable";
	private static final String lootTableSeed = "LootTableSeed";
	private static final String deathLootTableKey = "DeathLootTable";
	private static final String deathLootSeed = "DeathLootTableSeed";
	static {
		validBlocks = new ArrayList<>();
		manager = Hunted.getInstance().nbtManager;
		validBlocks.add(Material.CHEST);
		validBlocks.add(Material.DROPPER);
		validBlocks.add(Material.DISPENSER);
		validBlocks.add(Material.HOPPER);
		validBlocks.add(Material.TRAPPED_CHEST);
	}
	
	private LootUtils() {}
	
	/* ##############################
	 * Loot Table functions for blocks & entities
	 * ##############################
	 */
	public static boolean hasLootTable(Entity entity) {
		if(entity.getType().equals(EntityType.MINECART_CHEST) || entity.getType().equals(EntityType.MINECART_HOPPER)) {
			return manager.read(entity).containsKey(lootTableKey) || manager.read(entity).containsKey(lootTableSeed);
		}
		return manager.read(entity).containsKey(deathLootTableKey) || manager.read(entity).containsKey(deathLootSeed);
	}
	
	public static boolean hasLootTable(Block block) {
		if (!isValidBlock(block)) return false;
		return manager.read(block).containsKey(lootTableKey) || manager.read(block).containsKey(lootTableSeed);
	}
	
	public static boolean clearLootTable(Entity entity) {
		NBTCompound tag = manager.read(entity);
		if(entity.getType().equals(EntityType.MINECART_CHEST) || entity.getType().equals(EntityType.MINECART_HOPPER)) {
			tag.remove(lootTableKey);
			tag.remove(lootTableSeed);
		} else {
			tag.remove(deathLootTableKey);
			tag.remove(deathLootSeed);
		}
		manager.write(entity, tag);
		return !manager.read(entity).containsKey(deathLootTableKey) && !manager.read(entity).containsKey(deathLootSeed);
	}
	
	public static boolean clearLootTable(Block block) {
		if (!isValidBlock(block)) return true;
		NBTCompound tag = manager.read(block);
		tag.remove(lootTableKey);
		tag.remove(lootTableSeed);
		manager.write(block, tag);
		return !manager.read(block).containsKey(lootTableKey) && !manager.read(block).containsKey(lootTableSeed);
	}
	
	public static boolean setLootTable(Entity entity, String table) {
		NBTCompound tag = manager.read(entity);
		if(entity.getType().equals(EntityType.MINECART_CHEST) || entity.getType().equals(EntityType.MINECART_HOPPER)) {
			tag.put(lootTableKey, table);
		} else {
			tag.put(deathLootTableKey, table);
		}
		manager.write(entity, tag);
		return manager.read(entity).containsKey(deathLootTableKey);
	}
	
	public static boolean setLootTable(Block block, String table) {
		if (!isValidBlock(block)) return false;
		NBTCompound tag = manager.read(block);
		tag.put(lootTableKey, table);
		manager.write(block, tag);
		return manager.read(block).containsKey(lootTableKey);
	}
	
	
	public static boolean isValidBlock(Block block) {
		if(block == null) return false;
		return validBlocks.contains(block.getType());
	}


    public static String getNameForItem(net.minecraft.server.v1_9_R1.ItemStack stack) {
        for (MinecraftKey key : Item.REGISTRY.keySet()) {
            if (Item.REGISTRY.get(key).getName().equals(stack.getItem().getName())) {
                return key.toString();
            }
        }
        return stack.getName();
    }
}
