package net.senmori.hunted.lib.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Profile {

	private UUID uuid;
	private GameState gameState;
	protected Map<String, Integer> stats;
	
	//Scoreboard
	private Scoreboard board;
	private Objective obj;
	private Score kills;
	private Score gamemode;

	public Profile(Player player) {
		this.uuid = player.getUniqueId();
		stats = new HashMap<>();
		stats.put("kills", 0);
		stats.put("deaths", 0);
		stats.put("bowKills", 0);
		stats.put("chestsLooted", 0);
		stats.put("mobsKilled", 0);
		stats.put("damageDealt", 0);
		stats.put("damageTaken", 0);
		stats.put("stonesActivated", 0);
		stats.put("ascentedRewardsReceived", 0);
		stats.put("currentKillstreak", 0);
	}
	
	/*
	 * States
	 */
	public GameState getState() {
		return gameState != null ? gameState : GameState.OFFLINE;
	}

	public void setState(GameState state) {
		this.gameState = state;
	}

	/*
	 * #################### Statistic methods ####################
	 */
	
	public void updateStat(String statName, int amount) {
		if(stats.get(statName) != null) {
			int curr = stats.get(statName);
			stats.put(statName, curr += amount);
			return;
		}
		stats.put(statName, amount);
	}

	/*
	 * ###################### Generic Getters ######################
	 */

	public UUID getUuid() {
		return uuid;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(getUuid());
	}
	
	public Map<String, Integer> getStats() {
		return stats;
	}

}
