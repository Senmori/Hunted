package net.senmori.hunted.loot.utils;

import java.util.ArrayList;
import java.util.List;

import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.loot.condition.EntityPropertiesCondition;
import net.senmori.hunted.loot.condition.EntityScoresCondition;
import net.senmori.hunted.loot.condition.KilledByPlayerCondition;
import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.condition.RandomChanceCondition;
import net.senmori.hunted.loot.condition.RandomLootChanceCondition;
import net.senmori.hunted.loot.function.EnchantRandomFunction;
import net.senmori.hunted.loot.function.EnchantWithLevelsFunction;
import net.senmori.hunted.loot.function.FurnaceSmeltFunction;
import net.senmori.hunted.loot.function.LootFunction;
import net.senmori.hunted.loot.function.LootingEnchantFunction;
import net.senmori.hunted.loot.function.SetAttributesFunction;
import net.senmori.hunted.loot.function.SetCountFunction;
import net.senmori.hunted.loot.function.SetDamageFunction;
import net.senmori.hunted.loot.function.SetDataFunction;
import net.senmori.hunted.loot.function.SetNBTFunction;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class LootUtil {

	private static List<Material> validBlocks;
	private static NBTManager manager;
	private static final String lootTableKey = "LootTable";
	private static final String lootTableSeed = "LootTableSeed";
	private static final String deathLootTableKey = "DeathLootTable";
	private static final String deathLootSeed = "DeathLootTableSeed";
	static {
		validBlocks = new ArrayList<>();
		validBlocks.add(Material.CHEST);
		validBlocks.add(Material.DROPPER);
		validBlocks.add(Material.DISPENSER);
		validBlocks.add(Material.HOPPER);
		validBlocks.add(Material.TRAPPED_CHEST);
		manager = Hunted.getInstance().nbtManager;
	}
	
	private LootUtil() {}
	
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
		if(!validBlocks.contains(block.getType())) return false;
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
		if(!validBlocks.contains(block.getType())) return true;
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
		if(!validBlocks.contains(block.getType())) return false;
		NBTCompound tag = manager.read(block);
		tag.put(lootTableKey, table);
		manager.write(block, tag);
		return manager.read(block).containsKey(lootTableKey);
	}
	
	
	public static boolean isValidBlock(Block block) {
		if(block == null) return false;
		return validBlocks.contains(block.getType());
	}
	
	
	
	/* ###########################
	 * Return correct condition based on condition type
	 * ###########################
	 */
	public static LootCondition getCondition(String type) {
		switch(type) {
			case "entity_properties":
				return new EntityPropertiesCondition();
			case "entity_scores":
				return new EntityScoresCondition();
			case "killed_by_player":
				return new KilledByPlayerCondition();
			case "random_chance":
				return new RandomChanceCondition();
			case "random_chance_with_looting":
				return new RandomLootChanceCondition();
				default:
					return null;
		}
	}
	
	/* ############################
	 * Return correct function based on function type
	 * ############################
	 */
	public static LootFunction getFunction(String type) {
		switch(type) {
			case "enchant_randomly":
				return new EnchantRandomFunction();
			case "enchant_with_levels":
				return new EnchantWithLevelsFunction();
			case "furnace_smelt":
				return new FurnaceSmeltFunction();
			case "looting_enchant":
				return new LootingEnchantFunction();
			case "set_attributes":
				return new SetAttributesFunction();
			case "set_count":
				return new SetCountFunction();
			case "set_damage":
				return new SetDamageFunction();
			case "set_data":
				return new SetDataFunction();
			case "set_nbt":
				return new SetNBTFunction();
			default:
				return null;
		}
	}
}
