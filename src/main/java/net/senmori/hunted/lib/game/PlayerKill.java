package net.senmori.hunted.lib.game;

import java.sql.Timestamp;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class PlayerKill {
    
    private String killerUUID;
    private String killedUUID;
    private KillMethod killMethod;
    private int killDistance;
    private EntityType killerType;
    private EntityType killedType;
    private Timestamp timestamp;
    
    public PlayerKill(Entity killer, Entity killed, KillMethod killMethod, int distance) {
        this.killerUUID = killer.getUniqueId().toString();
        this.killedUUID = killed.getUniqueId().toString();
        this.killMethod = killMethod;
        this.killDistance = distance;
        this.killerType = killer.getType();
        this.killedType = killed.getType();
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
    
    public String getKillerUUID() {
        return killerUUID;
    }
    
    public String getKilledUUID() {
        return killedUUID;
    }
    
    public KillMethod getKillMethod() {
        return killMethod;
    }
    
    public int getKillDistance() {
        return killDistance;
    }
    
    public EntityType getKillerType() {
        return killerType;
    }
    
    public EntityType getKilledType() {
        return killedType;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
