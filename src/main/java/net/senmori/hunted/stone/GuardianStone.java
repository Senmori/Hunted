package net.senmori.hunted.stone;

import java.util.concurrent.TimeUnit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class GuardianStone extends Stone {
    private final NamespacedKey name;
    private final long cooldown; // in seconds
    private long lastActivatedTime;

    public GuardianStone(NamespacedKey name, long cooldown) {
        this.name = name;
        this.cooldown = cooldown;
        this.lastActivatedTime = System.currentTimeMillis();
    }

    @Override
    public boolean isActive() {
        return TimeUnit.MILLISECONDS.toSeconds(lastActivatedTime) >= getCooldown();
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public long getElapsedTime() {
        return lastActivatedTime;
    }

    @Override
    public void activate(Player player) {
        if(!this.isActive()) {
            return;
        }
        this.lastActivatedTime = System.currentTimeMillis();
    }
}
