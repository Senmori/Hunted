package net.senmori.hunted.lib.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Profile {

	private UUID uuid;
	private GameState gameState;
	protected Map<Stat, Integer> stats;
	private PlayerInventory lastInventory;
	
	//Scoreboard
	private Scoreboard board;
	private Objective obj;
	private Score kills;
	private Score gamemode;

	public Profile(Player player) {
		this.uuid = player.getUniqueId();
		stats = new HashMap<Stat, Integer>();
		stats.put(Stat.KILLS, 0);
		stats.put(Stat.DEATHS, 0);
		stats.put(Stat.BOW_KILLS, 0);
		stats.put(Stat.CHESTS_LOOTED, 0);
		stats.put(Stat.MOBS_KILLED, 0);
		stats.put(Stat.DAMAGE_DEALT, 0);
		stats.put(Stat.DAMAGE_TAKEN, 0);
		stats.put(Stat.STONES_ACTIVATED, 0);
		stats.put(Stat.ASCENTED_REWARDS_RECEIVED, 0);
		stats.put(Stat.CURRENT_KILLSTREAK, 0);
		this.lastInventory = player.getInventory();
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
	
	public void updateStat(Stat statName, int amount) {
		if(stats.get(statName) != null) {
			int curr = stats.get(statName);
			stats.put(statName, curr += amount);
			return;
		}
		stats.put(statName, amount);
	}
	
	
	/*
	 * ###################### Inventory #############################
	 */
	public PlayerInventory getLastInventory() {
		return this.lastInventory;
	}
	
	public void restoreInventory() {
		Player player = Bukkit.getPlayer(getUUID());
		
		player.getInventory().setArmorContents(this.lastInventory.getArmorContents());
		player.getInventory().setExtraContents(this.lastInventory.getExtraContents());
		
		for(int i = 0; i < this.lastInventory.getSize(); i++) {
			if(this.lastInventory.getItem(i) != null) {
				player.getInventory().setItem(i, this.lastInventory.getItem(i));
			}
		}
		this.lastInventory.clear();
	}

	/*
	 * ###################### Generic Getters ######################
	 */

	public UUID getUUID() {
		return uuid;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(getUUID());
	}
	
	public Map<Stat, Integer> getStats() {
		return stats;
	}

}
