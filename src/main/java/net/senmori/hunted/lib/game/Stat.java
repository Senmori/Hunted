package net.senmori.hunted.lib.game;

/**
 * Represents the different kinds of Statistics this plugin tracks
 */
public enum Stat {
    /**
     * Represents how many player killed another mob/player
     */
    KILLS,
    /**
     * Represents how many times this player has died
     */
    DEATHS,
    
    /**
     * Represents how many times this player has killed a mob/player with a bow
     */
    BOW_KILLS,
    
    /**
     * Represents how many times a player has opened a chest with loot
     */
    CHESTS_LOOTED,
    
    /**
     * Represents how many mobs this player has killed
     */
    MOBS_KILLED,
    
    /**
     * Reprsents how much damage a player has dealt
     */
    DAMAGE_DEALT,
    
    /**
     * Represents how much damage a player has received
     */
    DAMAGE_TAKEN,
    
    /**
     * Represents how many guardian/teleport stones a player has activated
     */
    STONES_ACTIVATED,
    
    /**
     * Represents how many ascented items a player has received
     */
    ASCENTED_REWARDS_RECEIVED,
    /**
     * Represents how many kills this player has killed without dieing
     */
    CURRENT_KILLSTREAK,
    ;
}
