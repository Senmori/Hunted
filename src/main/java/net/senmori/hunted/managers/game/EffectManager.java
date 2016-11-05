package net.senmori.hunted.managers.game;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.tasks.PlayerGlowTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Handles timed effects that can be applied to a player
 */
public class EffectManager {
    
    private final Map<UUID, Integer> glowTasks = new HashMap<>();
    private final Map<UUID, Integer> activeTasks = new HashMap<>();
    
    private Hunted plugin;
    
    public EffectManager(JavaPlugin plugin) {
        this.plugin = (Hunted)plugin;
    }
    
    
    /*
    * ##################################
    *  PlayerGlowTask methods
    * ##################################
     */
    public void addGlow(Player player) {
        int length = (int)(Math.random() * (Hunted.getInstance().getConfigManager().maxEffectLength -1) + 1);
        addGlow(player, length);
    }
    
    public void addGlow(Player player, int duration) {
        player.setGlowing(false); //set false in case they logged in with the glowing effect
        if(activeTasks.containsKey(player.getUniqueId())) {
            duration = activeTasks.get(player.getUniqueId());
        }
        int taskId = new PlayerGlowTask(Hunted.getInstance(), player.getUniqueId(), duration).getTaskId();
        glowTasks.put(player.getUniqueId(), taskId);
        activeTasks.put(player.getUniqueId(), duration);
    }
    
    public void stopGlow(Player player) {
        stopGlow(player, false);
    }
    
    public void stopGlow(Player player, boolean forceRemove) {
        player.setGlowing(false);
        if(glowTasks.containsKey(player.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(glowTasks.get(player.getUniqueId()));
            glowTasks.remove(player.getUniqueId());
        }
        if(forceRemove) {
            activeTasks.remove(player.getUniqueId());
        }
    }
    
    public boolean hasGlowTask(Player player) {
        return activeTasks.containsKey(player.getUniqueId());
    }
    
    public void clearGlowTasks() {
        for(UUID uuid : glowTasks.keySet()) {
            Bukkit.getScheduler().cancelTask(glowTasks.get(uuid));
        }
        glowTasks.clear();
    }
}
