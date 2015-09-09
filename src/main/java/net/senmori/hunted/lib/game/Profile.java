package net.senmori.hunted.lib.game;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.senmori.hunted.Hunted;

import org.bukkit.entity.Player;

public class Profile {
    
    private String displayName;
    private String uuid;
    private int currentPlaySession;
    private long startSession;
    private GameState gameState;
    
    // current session stats
    private int kills;
    private int deaths;
    private int bowKills;
    private int chestsLooted;
    private int mobsKilled;
    private int damageDealt;
    private int damageTaken;
    private int stonesActivated;
    private int ascentedRewardsReceived;
    private int currentKillstreak;
    
    // session kills
    private Set<PlayerKill> sessionKills;
    
    public Profile(Player player) {
        sessionKills = new HashSet<>();
        this.displayName = player.getDisplayName();
        this.uuid = player.getUniqueId().toString();
        this.currentPlaySession = 0;
        
        this.kills = 0;
        this.deaths = 0;
        this.bowKills = 0;
        this.chestsLooted = 0;
        this.mobsKilled = 0;
        this.damageDealt = 0;
        this.damageTaken = 0;
        this.stonesActivated = 0;
        this.ascentedRewardsReceived = 0;
        this.currentKillstreak = 0;
        if(player.getWorld().getName().equals(Hunted.getInstance().getConfigManager().activeWorld) || Hunted.getInstance().getPlayerManager().isPlaying(uuid)) {
            setState(GameState.LOBBY);
        } else {
            setState(GameState.NOT_PLAYING);
        }
    }
    
    public GameState getState() {
        return gameState != null ? gameState : GameState.OFFLINE;
    }
    
    public void setState(GameState state) {
        this.gameState = state;
    }
    
    public void startSession() {
        startSession = System.nanoTime();
    }
    
    public void endSession() {
        currentPlaySession += (int) TimeUnit.MILLISECONDS.toSeconds(System.nanoTime() - startSession);
        // save to mysql async here
    }
    
    public void addKill(PlayerKill kill) {
        sessionKills.add(kill);
    }
    
    /* ####################
     *  Statistic methods
     * ####################
     */
    public void incrementSession() {
        currentPlaySession++;
    }
    
    public void incrementSession(int seconds) {
        currentPlaySession += seconds;
    }
    
    public void incrementKills() {
        kills++;
    }
    
    public void incrementKills(int amount) {
        kills += amount;
    }
    
    public void incrementDeaths() {
        deaths++;
    }
    
    public void incrementDeaths(int amount) {
        deaths += amount;
    }
    
    public void incrementBowKills() {
        bowKills++;
    }
    
    public void incrementBowKills(int amount) {
        bowKills += amount;
    }
    
    public void incrementChestsLooted() {
        chestsLooted++;
    }
    
    public void incrementChestsLooted(int amount) {
        chestsLooted += amount;
    }
    
    public void incrementMobsKilled() {
        mobsKilled++;
    }
    
    public void incrementMobsKilled(int amount) {
        mobsKilled += amount;
    }
    
    public void incrementDamageDealt(int amount) {
        damageDealt += amount;
    }
    
    public void incrementDamageTaken(int amount) {
        damageTaken += amount;
    }
    
    public void incrementStonesActivated() {
        stonesActivated++;
    }
    
    public void incrementStonesActivated(int amount) {
        stonesActivated += amount;
    }
    
    public void incrementAscentedRewardsReceived() {
        ascentedRewardsReceived++;
    }
    
    public void incrementAscentedRewardsReceived(int amount) {
        ascentedRewardsReceived += amount;
    }
    
    public void incrementKillstreak() {
        currentKillstreak++;
    }
    
    public void incrementKillstreak(int amount) {
        currentKillstreak += amount;
    }
    
    
    
    /* ######################
     *  Variable Getters
     * ######################
     */

    public String getDisplayName() {
        return displayName;
    }

    public String getUuid() {
        return uuid;
    }

    public int getCurrentPlaySession() {
        return currentPlaySession;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getBowKills() {
        return bowKills;
    }

    public int getChestsLooted() {
        return chestsLooted;
    }

    public int getMobsKilled() {
        return mobsKilled;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public int getStonesActivated() {
        return stonesActivated;
    }

    public int getAscentedRewardsReceived() {
        return ascentedRewardsReceived;
    }

    public int getCurrentKillstreak() {
        return currentKillstreak;
    }

    public Set<PlayerKill> getSessionKills() {
        return sessionKills;
    }
                    
}
