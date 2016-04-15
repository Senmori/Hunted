package net.senmori.hunted.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.adapter.InheritanceAdapter;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class LootTable {

    private ResourceLocation location;
    private File file;
    private List<LootPool> pool = new ArrayList<>();

    public LootTable(List<LootPool> pool) {
        this.pool = pool;
    }

	public LootTable() {
        this.pool = new ArrayList<>();
    }

	/** Add another LootPool to this LootTable */
	public void addLootPool(LootPool pool) {
        this.pool.add(pool);
    }

    public List<LootPool> getLootPool() { return pool; }

    public void setResourceLocation(ResourceLocation location) {
        this.location = location;
        file = LootTableManager.getFile(location);
    }

    public static LootTable emptyLootTable() { return new LootTable(new ArrayList<>()); }


	public boolean save() {
        if (file != null && file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                String json = LootTableManager.gson.toJson(this);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.WARNING, "Couldn't create loot table \'" + location + "\' at file \'" + location.getResourcePath() + "\'");
                e.printStackTrace();
                return false;
            }
        } else {
            file = LootTableManager.getFile(location);
            try (FileWriter writer = new FileWriter(file)) {
                file.createNewFile();
                String json = LootTableManager.gson.toJson(this);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

	public static class Serializer extends InheritanceAdapter<LootTable> {

		public Serializer() {}

		public LootTable deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
			JsonObject jsonObject = JsonUtils.getJsonObject(json, "loot table");
            LootPool[] lootPool = JsonUtils.deserializeClass(jsonObject, "pools", new LootPool[0], context, LootPool[].class);
            return new LootTable(Arrays.asList(lootPool));
        }

		public JsonElement serialize(LootTable table, Type type, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
            jsonObject.add("pools", context.serialize(table.getLootPool()));
            return jsonObject;
		}
	}
}
