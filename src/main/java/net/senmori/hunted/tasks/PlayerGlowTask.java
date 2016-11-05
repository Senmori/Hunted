package net.senmori.hunted.tasks;

import static com.sun.jmx.snmp.EnumRowStatus.active;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_10_R1.ChatComponentText;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerGlowTask extends BukkitRunnable {
    
    private final UUID uuid;
    private final Hunted plugin;
    private int duration;
    private Player player;
    private String glowMessage = ChatColor.GREEN + "Glow effect will end in ";
    
    public PlayerGlowTask(Hunted plugin, UUID playerUUID, int duration) {
        this.plugin = plugin;
        this.uuid = playerUUID;
        this.duration = duration;
        this.runTaskTimer(plugin, 0L, 20L);
    }
    
    @Override
    public void run() {
        player = Bukkit.getPlayer(uuid);
        if(player == null) {
            this.cancel();
        }
        player.setGlowing(true);
        --duration;
        ActionBar.sendMessage(player, glowMessage + ChatColor.RED + duration);
        if(duration <= 0) {
            player.setGlowing(false);
            this.cancel();
        }
    }
}
