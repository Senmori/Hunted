package net.senmori.hunted.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.entry.LootEntry;
import net.senmori.hunted.loot.utils.JsonUtils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class LootPool {

	private List<LootEntry> poolEntries;
	private List<LootCondition> poolConditions;
	private RandomValueRange rolls;
	private RandomValueRange bonusRolls;

    /**
     * LootPool is a collection of {@link LootEntry} and {@link LootCondition}, which will generate a random set of loot.
     * @param entries - a list of {@link LootEntry} that will be used to generate a loot item.
     * @param poolConditions - a list of {@link LootCondition} that must pass for a loot item to be generated.
     * @param rolls - how many times this LootPool will attempt to generate a loot item
     * @param bonusRolls - how many <i>extra</i> times this LootPool will attempt to generate a loot item
     */
	public LootPool(List<LootEntry> entries, List<LootCondition> poolConditions, RandomValueRange rolls, RandomValueRange bonusRolls) {
		this.poolEntries = entries;
		this.poolConditions = poolConditions;
		this.rolls = rolls;
		this.bonusRolls = bonusRolls;
	}

    public LootPool(List<LootEntry> entries, List<LootCondition> conditions, float rolls) {
        this(entries, conditions, new RandomValueRange(rolls), new RandomValueRange(0.0F, 0.0F));
    }

    public LootPool(List<LootEntry> entries, List<LootCondition> conditions, float rollMin, float rollMax) {
        this(entries,conditions, new RandomValueRange(rollMin, rollMax), new RandomValueRange(0.0F, 0.0F));
    }

    public LootPool(List<LootEntry> entries, List<LootCondition> conditions, float rollsMin, float rollsMax, float bonusRolls) {
        this(entries, conditions, new RandomValueRange(rollsMin, rollsMax), new RandomValueRange(bonusRolls));
    }

    public LootPool(List<LootEntry> entries, List<LootCondition> conditions, float rollsMin, float rollsMax, float bonusRollsMin, float bonusRollsMax) {
        this(entries, conditions, new RandomValueRange(rollsMin, rollsMax), new RandomValueRange(bonusRollsMin, bonusRollsMax));
    }

    public void addLootEntry(LootEntry entry) {
        poolEntries.add(entry);
    }

    public void addLootCondition(LootCondition condition) {
        poolConditions.add(condition);
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

    public List<LootEntry> getEntries() { return poolEntries; }

    public List<LootCondition> getConditions() { return poolConditions; }


	public static class Serializer implements JsonDeserializer<LootPool>, JsonSerializer<LootPool> {
		public Serializer() {}

		public LootPool deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
			JsonObject jsonObject = JsonUtils.getJsonObject(json, "loot pool");
			LootEntry[] aLootEntry = JsonUtils.deserializeClass(jsonObject, "entries", context, LootEntry[].class);
			LootCondition[] aLootCondition = JsonUtils.deserializeClass(jsonObject, "conditions", new LootCondition[0], context, LootCondition[].class);
			RandomValueRange rolls = JsonUtils.deserializeClass(jsonObject, "rolls", context, RandomValueRange.class);
			RandomValueRange bonusRolls = JsonUtils.deserializeClass(jsonObject, "bonus_rolls", new RandomValueRange(0.0F, 0.0F), context, RandomValueRange.class);
			return new LootPool(Arrays.asList(aLootEntry), Arrays.asList(aLootCondition), rolls, bonusRolls);
		}

		public JsonElement serialize(LootPool pool, Type type, JsonSerializationContext context) {
			JsonObject object = new JsonObject();
			object.add("entries", context.serialize(pool.poolEntries));
			object.add("rolls", context.serialize(pool.rolls));
			if(pool.bonusRolls.getMin() != 0.0F && pool.bonusRolls.getMax() != 0.0F) {
				object.add("bonus_rolls", context.serialize(pool.bonusRolls));
			}
			if(pool.poolConditions != null && !pool.poolConditions.isEmpty()) {
				object.add("conditions", context.serialize(pool.poolConditions));
			}
			return object;
		}
	}
}
