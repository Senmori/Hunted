package net.senmori.hunted.achievements;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.AchievementManager;
import net.senmori.hunted.managers.PlayerManager;

import org.bukkit.ChatColor;

public abstract class Achievement 
{
	/*
	 * Achievements are broken down into these criteria:
	 * - name -> Name of the achievement
	 * - message -> the message that is sent to players when getting the achievement
	 * - reward -> the amount of XP to reward the player
	 * - color -> what color the message will be
	 * - criteria -> determined in doesMeetCriteria
	 * - update -> update's player's file, and send's all applicable messages
	 */
	public abstract String getName();
	public abstract String getMessage();
	public abstract int getReward();
	
	public abstract boolean doesMeetCriteria(String player);
	
	public abstract void update(String player);
	
	public ChatColor getColor()
	{
		return ChatColor.LIGHT_PURPLE;
	}
	
	public PlayerManager getPlayerManager()
	{
		return Hunted.playerManager;
	}
	
	public AchievementManager getManager()
	{
		return Hunted.achManager;
	}
}
