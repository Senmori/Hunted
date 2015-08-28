package net.senmori.hunted.lib.sql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import net.senmori.hunted.lib.game.KillMethod;
import net.senmori.hunted.lib.game.PlayerKill;

public class QueryHandler {

    private final Database database;
    private Connection connection = null;
    
    public QueryHandler(Database database) {
        this.database = database;
    }
    
    /* ##########################
     *  Player stats methods
     * ##########################
     */
    public List<Map<String, Object>> getColumn(List<Map<String, Object>> results) {
        return results;
    }
    
    /** Get amount of player kills for a player */
    public int getKills(String playerUUID) {
        return 0;
    }
    
    /** Get amount of bow kills */
    public int getBowKills(String playerUUID) {
        return -1;
    }
    
    /** Get amount of deaths */
    public int getDeaths(String playerUUID) {
        return -1;
    }
    
    /** Get the amount of time played(in seconds) */
    public int getTimePlayed(String playerUUID) {
        return -1;
    }
    
    /** Get the amount of chests looted */
    public int getChestsLooted(String playerUUID) {
        return -1;
    }
    
    /** Get how many non-npc mobs this player has killed */
    public int getMobsKilled(String playerUUID) {
        return -1;
    }
    
    /** Get the amount of damage this player has dealt */
    public int getDamageDealt(String playerUUID) {
        return -1;
    }
    
    /** Get the amount of damage this player has taken */
    public int getDamageTaken(String playerUUID) {
        return -1;
    }
    
    /** Get how many guardian stones this player has activated */
    public int getStonesActivated(String playerUUID) {
        return -1;
    }
    
    /** Get how many ascented rewards this player has received */
    public int getAscentedRewardsReceived(String playerUUID) {
        return -1;
    }
    
    /** Get highest killstreak */
    public int getHighestKillstreak(String playerUUID) {
        return -1;
    }
    
    /* ##################################
     *  Player pets
     * ##################################
     */
    public boolean hasPet(String type, String playerUUID) {
        return false;
    }
    
    public boolean hasPetCreeper(String playerUUID) {
        return false;
    }
    
    public boolean hasPetSkeleton(String playerUUID) {
        return false;
    }
    
    public boolean hasPetSpider(String playerUUID) {
        return false;
    }
    
    public boolean hasPetZombie(String playerUUID) {
        return false;
    }
    
    public boolean hasPetPigZombie(String playerUUID) {
        return false;
    }
    
    public boolean hasPetEnderman(String playerUUID) {
        return false;
    }
    
    public boolean hasPetPig(String playerUUID) {
        return false;
    }
    
    public boolean hasPetSheep(String playerUUID) {
        return false;
    }
    
    public boolean hasPetCow(String playerUUID) {
        return false;
    }
    
    public boolean hasPetChicken(String playerUUID) {
        return false;
    }
    
    public boolean hasPetMooshroom(String playerUUID) {
        return false;
    }
    
    public boolean hasPetOcelot(String playerUUID) {
        return false;
    }
    
    public boolean hasPetSquid(String playerUUID) {
        return false;
    }
    
    public String getCurrentPetName(String playerUUID) {
        return null;
    }
    
    /* #################################
     *  Player Kits
     * #################################
     */
    public boolean hasComponent(String component, String playerUUID) {
        return false;
    }
    
    public boolean hasPotionComponent(String component, String playerUUID, int tier) {
        return false;
    }
    
    public int getPotionComponentLevel(String component, String playerUUID) {
        return 0;
    }
    
    public boolean hasLeatherArmorComponent(String playerUUID) {
        return false;
    }
    
    public boolean hasChainArmorComponent(String playerUUID) {
        return false;
    }
     
    public boolean hasIronArmorComponent(String playerUUID) {
        return false;
    }
    
    public boolean hasWoodWeaponComponent(String playerUUID) {
        return false;
    }
    
    public boolean hasStoneWeaponComponent(String playerUUID) {
        return false;
    }
    
    public boolean hasIronWeaponComponent(String playerUUID) {
        return false;
    }
    
    
    
    /* #################################
     *  Player Effects
     * #################################
     */
    public boolean hasEffect(String effect, String playerUUID) {
        return false;
    }
    
    /* #################################
     *  Player Kill Info
     * #################################
     */
        /* ##########################
         *  Player kill methods
         * ##########################
         */
    
    public PlayerKill getKill(String playerUUID) {
        return null;
    }
    /**
     * Get last kill according to timestamp
     * @param playerUUID
     * @return
     */
    public PlayerKill getLastKill(String playerUUID) {
        return null;
    }
    
    /**
     * Get last kill by {@link KillMethod}
     * @param method
     * @param playerUUID
     * @return
     */
    public PlayerKill getLastKillByMethod(String method, String playerUUID) {
        return null;
    }
    
    /**
     * Get the last kill that is within {@link deviation} of {@link maxDistance}
     * @param playerUUID
     * @param maxDistance
     * @param deviation
     * @return
     */
    public PlayerKill getLastKillByDistance(String playerUUID, int maxDistance, int deviation) {
        return null;
    }
    
    /**
     * Get all kills for a player
     * @param playerUUID
     * @return
     */
    public PlayerKill[] getAllPlayerKills(String playerUUID) {
        return null;
    }
    
        /* #####################################
         *  Killed by methods
         * #####################################
         */
    /**
     * Get the last death for this player by time
     * @param playerUUID
     * @return
     */
    public PlayerKill getLastKilledBy(String playerUUID) {
        return null;
    }
    
    /**
     * Get the last death for this player by {@link KillMethod}
     * @param method
     * @param playerUUID
     * @return
     */
    public PlayerKill getLastKilledByMethod(String method, String playerUUID) {
        return null;
    }
    
    /**
     * Get the last death for this player by distance. See {@link #getLastKillByDistance(String, int, int)}
     * @param playerUUID
     * @param maxDistance
     * @param deviation
     * @return
     */
    public PlayerKill getLastKilledByDistance(String playerUUID, int maxDistance, int deviation) {
        return null;
    }
    
    /**
     * Get all deaths for this player
     * @param playerUUID
     * @return
     */
    public PlayerKill[] getAllDeaths(String playerUUID) {
        return null;
    }
    
    
    
    
    
    
    
    /* ####################
     * Private SQL 
     * ###################
     */
    private Connection getConnection() {
        return database.getConnection();
    }
}
