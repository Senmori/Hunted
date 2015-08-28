package net.senmori.hunted.lib.game;

import java.sql.Timestamp;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class PlayerKill {
    
    private String aggressorUUID;
    private String targetUUID;
    private KillMethod killMethod;
    private int killDistance;
    private EntityType aggressorType;
    private EntityType targetType;
    private Timestamp timestamp;
    
    public PlayerKill(Entity aggressor, Entity target, KillMethod killMethod, int distance) {
        this.aggressorUUID = aggressor.getUniqueId().toString();
        this.targetUUID = target.getUniqueId().toString();
        this.killMethod = killMethod;
        this.killDistance = distance;
        this.aggressorType = aggressor.getType();
        this.targetType = target.getType();
        this.timestamp = new Timestamp(System.nanoTime());
    }
    
    public String getAggressorUUID() {
        return aggressorUUID;
    }
    
    public String getTargetUUID() {
        return targetUUID;
    }
    
    public KillMethod getKillMethod() {
        return killMethod;
    }
    
    public int getKillDistance() {
        return killDistance;
    }
    
    public EntityType getAggressorType() {
        return aggressorType;
    }
    
    public EntityType getTargetType() {
        return targetType;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
