package net.senmori.hunted.stones;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.SerializedLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Stone {
    private SerializedLocation sLoc;

    Stone(SerializedLocation loc) {
        this.sLoc = loc;
        Hunted.getInstance().getStoneManager().register(this);
    }

    public abstract void activate(Player player);

    public StoneType getType() {
        return null;
    }

    public Location getLocation() {
        return sLoc.getLocation();
    }

    public String getName() {
        return sLoc.getName();
    }

    /**
     * The type of stones Hunted supports <br>
     * {@link #GUARDIAN} - used for rewards, etc <br>
     * {@link #TELEPORT} - used to teleport players to random locations <br>
     */
    public enum StoneType {
        GUARDIAN, // normal guardian stones(rewards, etc)
        TELEPORT // Teleports players to a specified point
    }
}
