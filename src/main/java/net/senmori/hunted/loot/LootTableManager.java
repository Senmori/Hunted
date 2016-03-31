package net.senmori.hunted.loot;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.senmori.hunted.loot.LootContext.EntityTarget;
import net.senmori.hunted.loot.condition.LootCondition;
import net.senmori.hunted.loot.condition.LootConditionManager;
import net.senmori.hunted.loot.entry.LootEntry;
import net.senmori.hunted.loot.function.LootFunction;
import net.senmori.hunted.loot.function.LootFunctionManager;
import net.senmori.hunted.loot.storage.ResourceLocation;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class LootTableManager {

    /* Classes to include:(so I don't forget
        RandomValueRange.class, RandomValueRange.Serializer
        LootPool.class LootPool.Serializer
        LootTable.class  LootTable.Serializer
        LootEntry.class  LootEntry.Serializer
        LootCondition  LootConditionManager.Serializer
        LootFunction   LootFunctionManager.Serializer
        LootContext.EntityTarget.class  LootContext.EntityTarget.Serializer
     */
    private static final GsonBuilder builder = new GsonBuilder()
                                                 .registerTypeAdapter(RandomValueRange.class, new RandomValueRange.Serializer())
                                                 .registerTypeAdapter(LootPool.class, new LootPool.Serializer())
                                                 .registerTypeAdapter(LootTable.class, new LootTable.Serializer())
                                                 .registerTypeHierarchyAdapter(LootEntry.class, new LootEntry.Serializer())
                                                 .registerTypeHierarchyAdapter(LootCondition.class, new LootConditionManager.Serializer())
                                                 .registerTypeHierarchyAdapter(LootFunction.class, new LootFunctionManager.Serializer())
                                                 .registerTypeHierarchyAdapter(EntityTarget.class, new EntityTarget.Serializer());
	private static final Gson GSON_INSTANCE = builder.setPrettyPrinting().create();
    /* ConcurrentHashMap because it's thread safe, supposedly */
	private static ConcurrentHashMap<ResourceLocation, LootTable> registeredLootTables = new ConcurrentHashMap<>();
    // Thread-safe Set; We shouldn't be accessing this a lot but better safe than sorry.
    private static Set<ResourceLocation> lootTables = Collections.synchronizedSet(new HashSet<ResourceLocation>());
    private static final String worldFolder = Bukkit.getWorldContainer().getAbsolutePath().replace('.', ' ').trim(); // remove the trailing '.', because it messes things up in Windows.
	private static final String baseFolder = worldFolder + "world" + File.separator + "data" + File.separator + "loot_tables" + File.separator;

    private LootTableManager() {}

    /**
     * Get a {@link ResourceLocation} that matches the given path.<br>
     *     Do not include the domain. (i.e. "minecraft")
     * @param resourcePath - the path to the loot table, without file extendsions.
     * @return
     */
    public static ResourceLocation getResourceLocation(String resourcePath) {
        for(ResourceLocation location : registeredLootTables.keySet()) {
            if(location.getResourcePath().equals(resourcePath)) {
                return location;
            }
        }
        return null;
    }

    /**
     * Get a {@link ResourceLocation} that matches the given domain and path.
     * @param resourceDomain - the namespace of the plugin(i.e. "minecraft")
     * @param resourcePath - the path to the loot table, without file extensions.
     * @return
     */
    public static ResourceLocation getResourceLocation(String resourceDomain, String resourcePath) {
        for(ResourceLocation loc : registeredLootTables.keySet()) {
            if(loc.getResourceDomain().equals(resourceDomain) && loc.getResourcePath().equals(resourcePath)) {
                return loc;
            }
        }
        return null;
    }

    /**
     * Get a {@link LootTable} from the given {@link ResourceLocation}
     * @param resource
     * @return
     */
    public static LootTable getLootTable(ResourceLocation resource) { return registeredLootTables.get(resource); }

	private void reloadLootTables() {
		registeredLootTables.clear();

        synchronized (lootTables) {
            Iterator i = lootTables.iterator();
            while(i.hasNext()) {
                ResourceLocation location = (ResourceLocation)i.next();
                registeredLootTables.put(location, getLootTable(location));
            }
        }
	}

    /**
     * Add a {@link ResourceLocation} which will generate the .json file. <br>
     * If a .json file is not already created, it will create one and return it. <br>
     * If the {@link LootTable} is already loaded, it will return that. <br>
     * @param resourceLocation
     */
    public static LootTable createOrLoadLootTable(ResourceLocation resourceLocation) {
        if(!lootTables.contains(resourceLocation)) {
            lootTables.add(resourceLocation);
        }

       if(registeredLootTables.containsKey(resourceLocation)) {
           return registeredLootTables.get(resourceLocation);
       } else {
           File file = new File(getResourceFilePath(resourceLocation));
           if(file.exists()) {
               // load file
               try {
                   LootTable table = load(resourceLocation);
                   registeredLootTables.put(resourceLocation, table);
                   return table;
               }catch(IOException e) {
                   Bukkit.getLogger().log(Level.WARNING, "Couldn\'t load loot table " + resourceLocation);
                   return LootTable.getEmptyLootTable();
               }

           } else {
               // no LootTable file exists, create new file, and return empty LootTable
                LootTable newTable = LootTable.getEmptyLootTable();
                write(newTable, resourceLocation);
                registeredLootTables.put(resourceLocation, newTable);
                return newTable;

           }
       }
    }

    /**
     * Write to file the {@link LootTable} assicated with the given {@link ResourceLocation}
     * @param location
     */
    public static void save(ResourceLocation location) {
        LootTable table = registeredLootTables.get(location);
        registeredLootTables.put(location, table); // update registeredLootTables
        write(table, location);
    }

    // write table data to file
    private static void write(LootTable table, ResourceLocation location) {
        File fullFilePath = new File(getResourceFilePath(location));
        File filePathNoFile = new File(getResourceFilePath(location).replaceFirst("^\\\\(.+\\\\)*(.+)\\.(.+)$", " ").trim()); // remove file from path to make parent directories
        Bukkit.broadcastMessage("File Path No File: " + filePathNoFile);
        try(Writer writer = new FileWriter(filePathNoFile)) {
            if(!filePathNoFile.getParentFile().exists()) {
                filePathNoFile.mkdirs();
            }
            if(!fullFilePath.exists() && fullFilePath.isFile()) {
                fullFilePath.mkdirs();
                fullFilePath.createNewFile();
            }
            String s = GSON_INSTANCE.toJson(table);
            Bukkit.broadcastMessage(s);
            writer.write(s);
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().log(Level.WARNING, "Couldn\'t create loot table \'" + location + "\' at file \'" + fullFilePath + "\'");
        }
    }

    public static String getResourceFilePath(ResourceLocation location) {
        return LootTableManager.baseFolder + location.getResourceDomain() + File.separator + location.getResourcePath() + ".json";
    }

    public static LootTable load(ResourceLocation location) throws IOException, JsonSyntaxException {
        if(location.getResourcePath().contains(".")) {
            Bukkit.getLogger().log(Level.WARNING, "Invalid loot table name \'" + location + "\' (can\'t contain periods)");
            return LootTable.getEmptyLootTable();
        } else {
            LootTable lootTable = loadLootTable(location);

            if(lootTable == null) {
                lootTable = loadBuiltInTable(location);
            }

            if(lootTable == null) {
                Bukkit.getLogger().log(Level.WARNING, "Couldn\'t find resource table " + location);
                return LootTable.getEmptyLootTable();
            }
            return lootTable;
        }
    }

    /* Get a loot table by a ResourceLocation */
    private static LootTable loadLootTable(ResourceLocation resource) {
        File file = new File(getResourceFilePath(resource));

        if(file.exists()) {
            if(file.isFile()) {
                String s;

                try {
                    s = Files.toString(file, Charsets.UTF_8);
                } catch(IOException e) {
                    Bukkit.getLogger().log(Level.WARNING, "Couldn\'t load the loot table at " + resource + " from " + file, e);
                    return LootTable.getEmptyLootTable();
                }

                try {
                    return LootTableManager.GSON_INSTANCE.fromJson(s, LootTable.class);
                } catch(JsonParseException e) {
                    Bukkit.getLogger().log(Level.SEVERE, "Couldn\'t load loot table " + resource + " from " + file, e);
                    return LootTable.getEmptyLootTable();
                }
            } else {
                Bukkit.getLogger().log(Level.WARNING, "Expected to find loot table " + resource + "at " + file + " but it is a folder");
                return LootTable.getEmptyLootTable();
            }
        } else {
            try {
                file.createNewFile();
                return LootTable.getEmptyLootTable();
            }catch(IOException e) {
                return LootTable.getEmptyLootTable();
            }
        }
    }

    /* Get the built-in Minecraft resource tables and see if that given ResourceLocation is there
        NOT used for anything other than default loot tables.
     */
    private static LootTable loadBuiltInTable(ResourceLocation location) {
        URL url = Bukkit.class.getResource("/assets/" + location.getResourceDomain() + "/loot_tables/" + location.getResourcePath() + ".json");
        if(url != null) {
            String s;

            try {
                s = Resources.toString(url, Charsets.UTF_8);
            } catch(IOException e) {
                Bukkit.getLogger().log(Level.WARNING, "Couldn\'t load loot table " + location + " from " + url, e);
                return LootTable.getEmptyLootTable();
            }

            try {
                return LootTableManager.GSON_INSTANCE.fromJson(s, LootTable.class);
            }catch(JsonParseException e) {
                Bukkit.getLogger().log(Level.WARNING, "Couldn't load loot table " + location + " from " + url);
                return LootTable.getEmptyLootTable();
            }
        } else {
            return null;
        }
    }
}
