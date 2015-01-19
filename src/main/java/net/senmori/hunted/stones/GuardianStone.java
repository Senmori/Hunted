package net.senmori.hunted.stones;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.Reference.Message;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.entity.Player;

public class GuardianStone extends Stone
{	
	private long lastActivated; // System time[in ms] in which stone was last used
	private int defaultCooldown; // Default cooldown limit[in minutes], if custom cooldown isn't set
	private boolean useCustomCooldown = false;
	private int customCooldown;
		
	
	public GuardianStone(SerializedLocation loc)
	{
		super(loc);
		this.defaultCooldown = Hunted.defaultCooldown;
		// set stone to active
		this.lastActivated = System.nanoTime() - (useCustomCooldown ? customCooldown : defaultCooldown);
	}
	
	@Override
	public void activate(Player player)
	{
		// stone isn't active, send message
		if(!isActive())
		{
			int timeUntilActiveMin = (int) TimeUnit.MILLISECONDS.toMinutes((useCustomCooldown ? customCooldown : defaultCooldown) - getTimePassed());
			int timeUntilActiveSec = (int) TimeUnit.MILLISECONDS.toSeconds(System.nanoTime() - lastActivated);
			player.sendMessage(ChatColor.YELLOW + String.format(Message.COLD_STONE, timeUntilActiveMin + "m ", timeUntilActiveSec + "s"));
			return;
		}
		Hunted.getRewardManager().generateReward(player);
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
	 * Returns if the guardian stone is active or not
	 * @return 
	 */
	public boolean isActive()
	{
		return getTimePassed() >= TimeUnit.MILLISECONDS.toMinutes(useCustomCooldown ? customCooldown : defaultCooldown);
	}
	
	/*
	 * Returns how long ago this stone was activated(in minutes)
	 */
	public long getTimePassed()
	{
		return TimeUnit.MILLISECONDS.toMinutes(System.nanoTime() - lastActivated);
	}
	
	@Override
	public Type getType()
	{
		return Type.GUARDIAN;
	}
}
