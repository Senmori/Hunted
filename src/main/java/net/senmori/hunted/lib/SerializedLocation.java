package net.senmori.hunted.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SerializedLocation {
    private int x;
    private int y;
    private int z;
    private String world;
    private String name;

    public SerializedLocation(Location loc, String name) {
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();
        this.world = loc.getWorld().getName();
        this.name = name;
    }

    public SerializedLocation(int x, int y, int z, String world, String name) {
        this(new Location(Bukkit.getWorld(world), x, y, z), name);
    }

    public Location getLocation() {
        World w = Bukkit.getWorld(world);
        return w == null ? null : new Location(w, x, y, z);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object loc) {
        if(!( loc instanceof SerializedLocation ))
            return false;
        return this.getLocation().equals(( (SerializedLocation) loc ).getLocation());
    }

    @Override
    public String toString() {
        return this.world + "," + this.x + "," + this.y + "," + this.z;
    }
}
