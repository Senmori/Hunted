package net.senmori.hunted.stones;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.managers.game.StoneManager;
import net.senmori.hunted.util.Reference.RewardMessage;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class GuardianStone extends Stone
{	
	private long lastActivated; // System time[in ms] in which stone was last used
	private long defaultCooldown; // Default cooldown limit[in minutes], if custom cooldown isn't set
	private boolean useCustomCooldown = false;
	private long customCooldown;
	
	public GuardianStone(SerializedLocation loc)
	{
		super(loc);
		this.defaultCooldown = TimeUnit.MINUTES.toMillis(net.senmori.hunted.Hunted.defaultCooldown);
		// set stone to active
		this.lastActivated = System.nanoTime() - TimeUnit.MINUTES.toMillis(useCustomCooldown ? customCooldown : defaultCooldown);
	}
	
	@Override
	public void activate(Player player)
	{
		// stone isn't active, send message
		if(!isActive())
		{
			int timeUntilActiveMin = (int) TimeUnit.MILLISECONDS.toMinutes(getElapsedTime() - (useCustomCooldown ? customCooldown : defaultCooldown));
			player.sendMessage(ChatColor.YELLOW + String.format(RewardMessage.COLD_STONE, timeUntilActiveMin));
			return;
		}
		net.senmori.hunted.Hunted.getRewardManager().generateReward(player);
	}
	
	/**
	 * Toggle redstone lamps to show whether or not this stone is active
	 * - isActive() = lamps ON
	 * - !isActive() = lamps OFF
	 * @param block
	 */
	public void toggleIndicators(Block block)
	{
		for(BlockFace face : StoneManager.faces)
		{
			toggleIndicatorBlock(block.getRelative(face));
		}
	}
	
	private void toggleIndicatorBlock(Block block)
	{
		if(!block.getType().equals(Material.REDSTONE_LAMP_OFF) || !block.getType().equals(Material.REDSTONE_LAMP_ON)) return;
		
		block.setType(this.isActive() ? Material.REDSTONE_LAMP_ON : Material.REDSTONE_LAMP_OFF);
	}
	
	/**
	 * Set custom cooldown for guardian stone
	 * @param cooldown - time in minutes
	 */
	public void setCustomCooldown(int cooldown)
	{
		if(cooldown == defaultCooldown) return; // don't bother with custom cooldowns if they are the same as the default cooldown
		useCustomCooldown = true;
		customCooldown = TimeUnit.MINUTES.toMillis(cooldown);
	}
	
	/**
	 * Returns if the guardian stone is active or not
	 * @return 
	 */
	public boolean isActive()
	{
		return getElapsedTime() >= TimeUnit.MILLISECONDS.toMinutes(useCustomCooldown ? customCooldown : defaultCooldown);
	}
	
	/*
	 * Returns how long ago this stone was activated(in minutes)
	 */
	public long getElapsedTime()
	{
		return TimeUnit.MILLISECONDS.toMinutes(System.nanoTime() - lastActivated);
	}
	
	@Override
	public Type getType()
	{
		return Type.GUARDIAN;
	}
	
	public int getCooldown()
	{
		return useCustomCooldown ? (int)TimeUnit.MILLISECONDS.toMinutes(customCooldown) : (int)TimeUnit.MILLISECONDS.toMinutes(defaultCooldown);
	}
}
