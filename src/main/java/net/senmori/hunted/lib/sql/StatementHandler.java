package net.senmori.hunted.lib.sql;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class StatementHandler {

    private final Database database;
    private Connection connection = null;
    
    public StatementHandler(Database database) {
        this.database = database;
    }
    
    private synchronized List<Map<String, Object>> query(String query) {
        return null;
    }
    /* #########################
     * Player stats methods
     * #########################
     */
    public void updatePlayerStat(String column, String playerUUID, String value) {
        
    }
    
    public void updatePlayerStat(String column, String playerUUID, int value) {
        
    }
    
    public void updateKills(String playerUUID, int value) {
        
    }
    
    public void updateBowKills(String playerUUID, int value) {
        
    }
    
    public void updateDeaths(String playerUUID, int value) {
        
    }
    
    public void updateTimePlayed(String playerUUID, int time) {
        
    }
    
    public void updateChestsLooted(String playerUUID, int amount) {
        
    }
    
    public void updateMobsKilled(String playerUUID, int amount) {
        
    }
    
    public void updateDamageDealt(String playerUUID, int amount) {
        
    }
    
    public void updateDamageTaken(String playerUUID, int amount) {
        
    }
    
    public void updateStonesActivated(String playerUUID, int amount) {
        
    }
    
    public void updateAscentedRewardsReceived(String playerUUID, int amount) {
        
    }
    
    
    /* ##########################
     * Player pets 
     * ##########################
     */
    public void updatePet(String type, String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetCreeper(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetSkeleton(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetSpider(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetZombie(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetPigZombie(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetEnderman(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetPig(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetSheep(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetCow(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetChicken(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetMooshroomCow(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetOcelot(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetSquid(String playerUUID, boolean unlocked) {
        
    }
    
    public void updatePetName(String playerUUID, String name) {
        
    }
    
    
    /* ########################## 
     * Player Kits
     * ##########################
     */
    
    public void updateComponent(String playerUUID, String component, boolean unlocked) {
        
    }
    
    public void updateComponentIronArmor(String playerUUID, boolean unlocked) {
        
    }
    
    public void updateComponentChainArmor(String playerUUID, boolean unlocked) {
        
    }
    
    public void updateComponentLeatherArmor(String playerUUID, boolean unlocked) {
        
    }
    
    public void updateComponentWoodWeapon(String playerUUID, boolean unlocked) {
        
    }
    
    public void updateComponentStoneWeapon(String playerUUID, boolean unlocked) {
        
    }
    
    public void updateComponentIronWeapon(String playerUUID, boolean unlocked) {
        
    }
    
    /* ########################## 
     * Player Effects
     * ##########################
     */
    public void updateEffect(String type, String playerUUID, boolean unlocked) {
        
    }
    
    /* ########################## 
     * Player Kill Info
     * ##########################
     */
    public void updatePlayerKill(String aggressorUUID, String targetUUID, String killMethod, int distance, Timestamp timestamp) {
        
    }
    
    
    
    
    
    
    
    /* #################
     *  Private SQL
     * #################
     */
    private Connection getConnection() {
        return database.getConnection();
    }

}
