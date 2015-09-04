package net.senmori.hunted.tasks;

import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedTntExplosion extends BukkitRunnable {

    private float power;
    private boolean destroyBlocks;
    private boolean createFire;
    private Item item;
    
    public DelayedTntExplosion(Item item, float power, boolean destroyBlocks, boolean createFire) {
        this.item = item;
        this.power = power;
        this.destroyBlocks = destroyBlocks;
        this.createFire = createFire;
    }
    
    @Override
    public void run() {
        item.getWorld().createExplosion(item.getLocation().getX(), item.getLocation().getY(), item.getLocation().getZ(), power, createFire, destroyBlocks);
        this.cancel();
    }

}
