package net.senmori.hunted.tasks;

import net.senmori.hunted.Hunted;

import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedTntExplosion extends BukkitRunnable {

    private Hunted plugin;
    private float power;
    private boolean destroyBlocks;
    private Item item;
    
    public DelayedTntExplosion(Hunted plugin, Item item, float power, boolean destroyBlocks) {
        this.plugin = plugin;
        this.item = item;
        this.power = power;
        this.destroyBlocks = destroyBlocks;
    }
    
    @Override
    public void run() {
        item.getWorld().createExplosion(item.getLocation().getX(), item.getLocation().getY(), item.getLocation().getZ(), power, false, destroyBlocks);
        this.cancel();
    }

}
