package net.senmori.hunted.loot.core;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.adapter.InheritanceAdapter;
import net.senmori.hunted.loot.conditions.LootCondition;
import net.senmori.hunted.loot.conditions.LootConditionManager;
import net.senmori.hunted.loot.entry.LootEntry;
import net.senmori.hunted.loot.entry.LootEntryEmpty;
import net.senmori.hunted.loot.entry.LootEntryItem;
import net.senmori.hunted.loot.entry.LootEntryTable;
import net.senmori.hunted.loot.functions.SetCount;
import net.senmori.hunted.loot.functions.SetNBT;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import net.senmori.hunted.loot.utils.MathHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class LootPool {

	public static RandomValueRange noBonusRollsRange = new RandomValueRange(0.0f, 0.0f);
    protected List<LootEntry> entries = new ArrayList<>();
    protected List<LootCondition> conditions = new ArrayList<>();
    protected RandomValueRange rolls;
	protected RandomValueRange bonusRolls;
    protected String name;

    /**
     * LootPool is a collection of {@link LootEntry} and {@link LootCondition}, which will generate a random set of loot.
     * @param entries - a list of {@link LootEntry} that will be used to generate a loot item.
     * @param poolConditions - a list of {@link LootCondition} that must pass for a loot item to be generated.
     * @param rolls - how many times this LootPool will attempt to generate a loot item
     * @param bonusRolls - how many <i>extra</i> times this LootPool will attempt to generate a loot item
     */
    public LootPool(String name, List<LootEntry> entries, List<LootCondition> poolConditions, RandomValueRange rolls, RandomValueRange bonusRolls) {
        if (entries != null) { this.entries.addAll(entries); }
        if (poolConditions != null) { this.conditions.addAll(conditions); }
        this.rolls = rolls;
		this.bonusRolls = bonusRolls;
        this.name = name;
    }

    public LootPool(String name, float rolls) {
        new LootPool(name, null, null, new RandomValueRange(rolls), noBonusRollsRange);
    }

    public void generateLoot(Collection<ItemStack> stacks, Random rand, LootContext context) {
        if (LootConditionManager.testAllConditions(this.getConditions(), rand, context)) {
            int r = this.rolls.generateInt(rand) + MathHelper.floorFloat(this.bonusRolls.generateFloat(rand) * context.getLuck());

            for (int i = 0; i < r; i++) {
                this.createLootRoll(stacks, rand, context);
            }
        }
    }

    protected void createLootRoll(Collection<ItemStack> stacks, Random rand, LootContext context) {
        ArrayList list = new ArrayList<>();
        int i = 0;
        for (LootEntry e : this.entries) {
            if (LootConditionManager.testAllConditions(e.getConditions(), rand, context)) {
                int qual = e.getEffectiveQuality(context.getLuck());
                if (qual > 0) {
                    list.add(e);
                    i += qual;
                }
            }
        }

        if (i != 0 && !list.isEmpty()) {
            int num = rand.nextInt(i);
            Iterator iter = list.iterator();

            while (iter.hasNext()) {
                LootEntry entry = (LootEntry) iter.next();
                num -= entry.getEffectiveQuality(context.getLuck());
                if (num < 0) {
                    entry.addLoot(stacks, rand, context);
                    return;
                }
            }
        }
    }

    public void addLootEntry(LootEntry entry) {
        this.entries.add(entry);
    }

    public void addLootEntry(ItemStack stack, int weight, int quality) {
        LootEntryItem item = new LootEntryItem(stack.getType(), weight, quality, null, null);
        if (stack.getAmount() > 1) {
            item.addFunction(new SetCount(new RandomValueRange(stack.getAmount()), null));
        }

        if (CraftItemStack.asNMSCopy(stack).hasTag()) { // because this will override every other function anyways
            SetNBT function = new SetNBT(CraftItemStack.asNMSCopy(stack).getTag().toString(), null);
            item.addFunction(function);
        }
        addLootEntry(item);
    }
    public void addLootEntry(Material material, int weight, int quality) {
        addLootEntry(new LootEntryItem(material, weight, quality, null, null));
    }

    public void addLootTableEntry(ResourceLocation location, int weight, int quality) {
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
    public RandomValueRange getRolls() {return rolls == null ? RandomValueRange.emptyRange : rolls;}

    /**
     * Returns a {@link RandomValueRange} set that can contain a minimum and maximum value.<br>
     * If minimum < maximum, then the loot table will pick a random number between them, and will <br>
     * attempt to generate that many extra loot item(s), if all associated conditions pass.
     * @return
     */
    public RandomValueRange getBonusRolls() {
        return bonusRolls == null ? RandomValueRange.emptyRange : bonusRolls;
    }

	public List<LootEntry> getEntries() { return entries; }

	public List<LootCondition> getConditions() { return conditions; }

    public String getName() { return this.name; }


	public static class Serializer extends InheritanceAdapter<LootPool> {
		public Serializer() {}

		@Override
		public LootPool deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "loot pool");
            String name = JsonUtils.getString(jsonObject, "name");
            LootEntry[] lootEntries = JsonUtils.deserializeClass(jsonObject, "entries", context, LootEntry[].class);
			LootCondition[] lootConditions = JsonUtils.deserializeClass(jsonObject, "conditions", new LootCondition[0], context, LootCondition[].class);
			RandomValueRange rolls = JsonUtils.deserializeClass(jsonObject, "rolls", context, RandomValueRange.class);
			RandomValueRange bonusRolls = JsonUtils.deserializeClass(jsonObject, "bonus_rolls", new RandomValueRange(0.0F, 0.0F), context, RandomValueRange.class);
            return new LootPool(name, Arrays.asList(lootEntries), Arrays.asList(lootConditions), rolls, bonusRolls);
        }

		@Override
		public JsonElement serialize(LootPool pool, Type type, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
            json.add("name", new JsonPrimitive(pool.name));
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
