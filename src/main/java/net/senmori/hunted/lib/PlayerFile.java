package net.senmori.hunted.lib;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.LogHandler;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerFile 
{
	private File playerFile;
	private String uuid;
	
	private int currentKills = 0;
	private int currentBowKills = 0;
	private int currentDeaths = 0;
	private int currentKillstreak = 0;
	
	public int upgradeSlotsAvailable = 3;
	
	private boolean fileExists;
	
	public PlayerFile(String playerUUID)
	{
		playerFile = new File("plugins/" + Hunted.name + "/playerdata/" + playerUUID + ".yml");
		if(playerFile.isFile()) fileExists = true;
		uuid = playerUUID;
		loadDefaults();
	}
	
	public String getUUID()
	{
		return uuid;
	}
	
	// Get player's current kills for this session
	public int getPlayerKills()
	{
		return currentKills;
	}
	
	// get player's current deaths for this session(hint: this shouldn't be more than 1..hopefully)
	public int getPlayerDeaths()
	{
		return currentDeaths;
	}
	
	// get player's killstreak for this session
	public int getKillStreak()
	{
		return currentKillstreak;
	}
	
	// get player's kills by bow
	public int getBowKills()
	{
		return currentBowKills;
	}
	
	/*
	 * Increment kills
	 */
	public void incrementKills()
	{
		incrementKills(1);
	}
	
	public void incrementKills(int by)
	{
		increment("kills", getPlayerKills() + by);
		currentKills += by;
		incrementKillStreak();
		save();
	}
	
	/*
	 * Increment bow kills
	 */
	public void incrementBowKills()
	{
		incrementBowKills(1);
	}
	
	public void incrementBowKills(int by)
	{
		increment("bow-kills", getBowKills() + by);
		incrementKills(by);
		currentBowKills =+ by;
		incrementKillStreak();
		save();
	}
	
	/*
	 * Increment deaths
	 */
	public void incrementDeaths()
	{
		incrementDeaths(1);
	}
	
	public void incrementDeaths(int by)
	{
		increment("kills", getPlayerDeaths() + by);
		currentDeaths += by;
		save();
	}
	

	/*
	 * Increment killstreak
	 */
	public void incrementKillStreak()
	{
		incrementKillStreak(1);
	}
	
	public void incrementKillStreak(int by)
	{
		currentKillstreak += by;
		if(currentKillstreak > getPlayerConfig().getInt("highest-killstreak"))
		{
			getPlayerConfig().set("highest-killstreak", currentKillstreak);
			save();
		}
		
	}
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(UUID.fromString(getUUID()));
	}
	
	/* ##############################
	 * 	Backend functions
	 * 	- All functions that directly interface between I/O and files go underneath this comment
	 * ##############################
	 */
	
	// Add an achievement to this player's file
	public void addAchievement(String achievement)
	{
		getPlayerConfig().set("achievements." + achievement, true);
		save();
	}
	
	// get a list of player's achievements 
	public List<String> getAchievements()
	{
		return getPlayerConfig().getStringList("achievements");
	}
	
	// Save current statistics after every death(i.e. when they respawn in lobby)
	public void saveStats()
	{
		increment("kills", getPlayerKills());
		increment("deaths", getPlayerDeaths());
		increment("highest-killstreak", getKillStreak());
		increment("bow-kills", getBowKills());
	}
	
	
	// Set a statistic that doesn't have a set method
	public void increment(String path, int amount)
	{
		getPlayerConfig().set(path, getPlayerConfig().getInt(path) + 1);
		save();
	}
	
	/*
	 * Only load defaults on file creation
	 */
	private void loadDefaults()
	{
		if(fileExists) return;
		getPlayerConfig().addDefault("kills", 0);
		getPlayerConfig().addDefault("bow-kills", 0);
		getPlayerConfig().addDefault("highest-killstreak", 0);
		getPlayerConfig().addDefault("deaths", 0);
		getPlayerConfig().createSection("achievements");
		
		// set defaults for which armor/weapons/potions/enchantments player has unlocked
		getPlayerConfig().addDefault("unlocked.armor", 0);
		getPlayerConfig().addDefault("unlocked.weapon", 0);
		getPlayerConfig().addDefault("unlocked.potion-tier-1", 0);
		getPlayerConfig().addDefault("Unlocked.potion-tier-2", 0);
		getPlayerConfig().addDefault("unlocked.ench.chestplate", 0);
		getPlayerConfig().addDefault("unlocked.ench.leggings", 0);
		getPlayerConfig().addDefault("unlocked.ench.boots", 0);
		getPlayerConfig().addDefault("unlocked.ench.sword", 0);
		getPlayerConfig().addDefault("unlocked.ench.bow", 0);
		
		// Set defaults for unlock/upgrade costs to the next level
		getPlayerConfig().addDefault("unlock-cost.armor", 1);
		getPlayerConfig().addDefault("unlock-cost.weapon", 1);
		getPlayerConfig().addDefault("unlock-cost.ench-level", 2);
		getPlayerConfig().addDefault("unlock-cost.potion-level", 2);
	}
	
	
	/*
	 * Get player's file from directory
	 */
	public File getFile()
	{
		return playerFile.isFile() ? playerFile : new File("plugins/Hunted/playerdata/" + uuid + ".yml");
	}
	
	/*
	 * Get file configuration
	 */
	public FileConfiguration getPlayerConfig()
	{
		return YamlConfiguration.loadConfiguration(getFile());
	}
	
	public void save()
	{
		try
		{
			getPlayerConfig().save(getFile());
		}
		catch (IOException e)
		{
			LogHandler.severe("Error saving file for player: " + Bukkit.getPlayer(UUID.fromString(uuid)));
			LogHandler.severe(Bukkit.getPlayer(UUID.fromString(uuid)) + " UUID: " + uuid);
			e.printStackTrace();
		}
	}
	
	
	/* ##############################
	 * 
	 * 	All functions relating to a player's kit/loadout go here 	
	 * 
	 * ##############################
	 */
	
	public Loadout getLoadout()
	{
		return null;
	}
	
	public Loadout getLoadout(String name)
	{
		return null;
	}
}
