package net.senmori.hunted.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.adapter.InheritanceAdapter;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.entry.LootEntry;
import net.senmori.hunted.loot.entry.LootEntryEmpty;
import net.senmori.hunted.loot.entry.LootEntryItem;
import net.senmori.hunted.loot.entry.LootEntryTable;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LootPool {

	public static RandomValueRange noBonusRollsRange = new RandomValueRange(0.0f, 0.0f);
    protected List<LootEntry> entries = new ArrayList<>();
    protected List<LootCondition> conditions = new ArrayList<>();
    protected RandomValueRange rolls;
	protected RandomValueRange bonusRolls;

    /**
     * LootPool is a collection of {@link LootEntry} and {@link LootCondition}, which will generate a random set of loot.
     * @param entries - a list of {@link LootEntry} that will be used to generate a loot item.
     * @param poolConditions - a list of {@link LootCondition} that must pass for a loot item to be generated.
     * @param rolls - how many times this LootPool will attempt to generate a loot item
     * @param bonusRolls - how many <i>extra</i> times this LootPool will attempt to generate a loot item
     */
	public LootPool(List<LootEntry> entries, List<LootCondition> poolConditions, RandomValueRange rolls, RandomValueRange bonusRolls) {
		this.entries = entries;
		this.conditions = poolConditions;
		this.rolls = rolls;
		this.bonusRolls = bonusRolls;
	}

	public LootPool(float rolls) {
		this(null, null, new RandomValueRange(rolls), noBonusRollsRange);
	}

    public LootPool(List<LootEntry> entries, List<LootCondition> conditions, float rolls) {
		this(entries, conditions, new RandomValueRange(rolls), noBonusRollsRange);
	}

    public LootPool(List<LootEntry> entries, List<LootCondition> conditions, float rollMin, float rollMax) {
		this(entries, conditions, new RandomValueRange(rollMin, rollMax), noBonusRollsRange);
	}

    public LootPool(List<LootEntry> entries, List<LootCondition> conditions, float rollsMin, float rollsMax, float bonusRolls) {
		this(entries, conditions, new RandomValueRange(rollsMin, rollsMax), noBonusRollsRange);
	}

    public LootPool(List<LootEntry> entries, List<LootCondition> conditions, float rollsMin, float rollsMax, float bonusRollsMin, float bonusRollsMax) {
		this(entries, conditions, new RandomValueRange(rollsMin, rollsMax), noBonusRollsRange);
	}

    public void addLootEntry(LootEntry entry) {
        if (!entries.add(entry)) {
            Bukkit.broadcastMessage("Error adding entry!");
        }
    }

    public void addLootEntry(Material material, int weight, int quality) {
        addLootEntry(new LootEntryItem(material, weight, quality, null, null));
    }

    public void addLootEntry(ResourceLocation location, int weight, int quality) {
        addLootEntry(new LootEntryTable(location, weight, quality, null));
    }

    public void addEmptyLootEntry(int weight, int quality) {
        addLootEntry(new LootEntryEmpty(weight, quality, null));
    }

    public void addLootCondition(LootCondition condition) {
        if (!conditions.add(condition)) {
            Bukkit.broadcastMessage("Error adding condition!");
        }
    }

    /**
     * Returns a {@link RandomValueRange} set that can contain a minimum, and maximum value. <br>
     * If minimum < maximum, then the loot table will pick a random number between them, and will <br>
     * generate that many loot items, if all associated conditions pass.
     * @return
     */
    public RandomValueRange getRolls() {
        return rolls;
    }

    /**
     * Returns a {@link RandomValueRange} set that can contain a minimum and maximum value.<br>
     * If minimum < maximum, then the loot table will pick a random number between them, and will <br>
     * attempt to generate that many extra loot item(s), if all associated conditions pass.
     * @return
     */
    public RandomValueRange getBonusRolls() {
        return bonusRolls;
    }

	public List<LootEntry> getEntries() { return entries; }

	public List<LootCondition> getConditions() { return conditions; }


	public static class Serializer extends InheritanceAdapter<LootPool> {
		private int i = 0;
		public Serializer() {}

		@Override
		public LootPool deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "loot pool");
			LootEntry[] lootEntries = JsonUtils.deserializeClass(jsonObject, "entries", context, LootEntry[].class);
			LootCondition[] lootConditions = JsonUtils.deserializeClass(jsonObject, "conditions", new LootCondition[0], context, LootCondition[].class);
			RandomValueRange rolls = JsonUtils.deserializeClass(jsonObject, "rolls", context, RandomValueRange.class);
			RandomValueRange bonusRolls = JsonUtils.deserializeClass(jsonObject, "bonus_rolls", new RandomValueRange(0.0F, 0.0F), context, RandomValueRange.class);
			return new LootPool(Arrays.asList(lootEntries), Arrays.asList(lootConditions), rolls, bonusRolls);
		}

		@Override
		public JsonElement serialize(LootPool pool, Type type, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			json.add("rolls", context.serialize(pool.rolls));
			if (pool.bonusRolls != null && pool.bonusRolls.getMin() != 0.0F && pool.bonusRolls.getMax() != 0.0F) {
				json.add("bonus_rolls", context.serialize(pool.bonusRolls));
			}
			json.add("entries", context.serialize(pool.entries));
			if (pool.conditions != null && !pool.conditions.isEmpty()) {
				context.serialize(pool.conditions);
			}
			return json;
		}
	}

}
