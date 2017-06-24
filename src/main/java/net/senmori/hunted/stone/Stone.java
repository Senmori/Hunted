package net.senmori.hunted.stone;

import org.bukkit.entity.Player;

/**
 * Represents any kind of Stone that can be activated.
 */
public abstract class Stone {

    /**
     * Check if this Stone is active.
     * @return true if active
     */
    public abstract boolean isActive();

    /**
     * Returns the complete cooldown of this Stone. <br>
     * This is how many seconds are between activations of this Stone.
     * @return number of seconds between activations
     */
    public abstract long getCooldown();

    /**
     * How long ago this stone was last activated.<br>
     * This value is in MS. Convert to seconds using TimeUnit.<br>
     * This value can be larger than the cooldown.
     * @return seconds since last activation
     */
    public abstract long getElapsedTime();

    /**
     * Activates this Stone, if and only if it is active.
     * @param player the player who is trying to activate this stone
     */
    public abstract void activate(Player player);
}
