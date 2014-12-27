package net.senmori.hunted.stones;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.Permissions.ErrorMessage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GuardianStone extends Stone
{
	private int x;
	private int y;
	private int z;
	private String world;
	private String name;
	
	private long lastActivated; // System time[in ms] in which stone was last used
	private int defaultCooldown; // Default cooldown limit[in minutes], if custom cooldown isn't set
	private boolean useCustomCooldown = false;
	private int customCooldown;
	
	private boolean isAdminStone = false;
	
	// static variables
	private static List<GuardianStone> guardianStones;
	
	public GuardianStone(Location loc, String name)
	{
		super();
		this.x = loc.getBlockX();
		this.y = loc.getBlockY();
		this.z = loc.getBlockZ();
		this.world = loc.getWorld().toString();
		this.name = name;
		// set stone to active
		this.lastActivated = System.nanoTime() - (useCustomCooldown ? customCooldown : defaultCooldown);
		this.defaultCooldown = Hunted.defaultCooldown;
		
		guardianStones.add(this);
	}
	
	@Override
	public void activate(Player player)
	{
		// stone isn't active, send message
		if(!isActive())
		{
			int timeUntilActive = getTimePassed();
			player.sendMessage(ChatColor.YELLOW + String.format(ErrorMessage.COLD_STONE, timeUntilActive, timeUntilActive > 0 ? "minute(s)" : "second(s)"));
		}
	}
	
	/**
	 * Set custom cooldown for guardian stone
	 * @param cooldown - time in minutes
	 */
	public void setCustomCooldown(int cooldown)
	{
		useCustomCooldown = true;
		customCooldown = cooldown >= 0 ? cooldown : 0;
	}
	
	/**
	 * Converts this guardian stone to an admin stone, thus changing what it does
	 */
	public void convertToAdminStone()
	{
		this.isAdminStone = true;
	}
	/**
	 * Returns if the guardian stone is active or not
	 * @return 
	 */
	public boolean isActive()
	{
		if(useCustomCooldown) return getTimePassed() >= this.customCooldown;
		return getTimePassed() >= this.defaultCooldown;
	}
	
	/*
	 * Returns how long ago this stone was activated(in minutes)
	 */
	public int getTimePassed()
	{
		return (int) TimeUnit.MILLISECONDS.toMinutes(System.nanoTime() - lastActivated);
	}
	
	public String getName()
	{
		return name;
	}
	
	public Location getLocation()
	{
		return new Location(Bukkit.getWorld(this.world), (double)x, (double)y, (double)z);
	}
}
