package net.senmori.hunted.tasks;

import java.util.UUID;

import net.senmori.hunted.Hunted;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LogOutTimer extends BukkitRunnable {
    
    private String uuid;
    private Hunted plugin;
    int count;
    public LogOutTimer(Hunted plugin, String uuid, int seconds) {
        this.plugin = plugin;
        this.uuid = uuid;
        count = seconds;
    }

    @Override
    public void run() {
       --count;
       if(Bukkit.getPlayer(UUID.fromString(uuid)) != null) {
           Player player = Bukkit.getPlayer(UUID.fromString(uuid));
           if(player.isOnline()) {
               this.cancel();
           }
       }
       if(count <= 0) {
           if(Bukkit.getPlayer(UUID.fromString(uuid)) != null) {
              if(Bukkit.getPlayer(UUID.fromString(uuid)).isOnline()) {
                   this.cancel();
              } else {
                  plugin.getPlayerManager().untrackPlayer(uuid);
                  this.cancel();
              }
           }
       }
        
    }

}
