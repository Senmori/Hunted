package net.senmori.hunted.stones;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.util.Reference.RewardMessage;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class GuardianStone extends Stone {	
	private long lastActivated; // System time[in ms] in which stone was last activated
	private long cooldown; // Default cooldown [in minutes]
	
	public GuardianStone(SerializedLocation loc) {
		super(loc);
		this.cooldown = TimeUnit.MINUTES.toMillis(Hunted.getInstance().getConfigManager().defaultCooldown);
		// set stone to active
		this.lastActivated = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(cooldown);
	}
	
	/** Main method, call this to active this {@link GuardianStone} */
	@Override
	public void activate(Player player) {
		// stone isn't active, send message
		if(!isActive()) {
			int timeUntilActiveMin = (int) TimeUnit.MILLISECONDS.toMinutes(getElapsedTime() - cooldown);
			//player.sendMessage(ChatColor.YELLOW + String.format(RewardMessage.COLD_STONE, timeUntilActiveMin));
			player.sendMessage(ChatColor.YELLOW + MessageFormat.format(RewardMessage.COLD_STONE, timeUntilActiveMin));
			return;
		}
		Hunted.getInstance().getRewardManager().generateReward(player);
		this.lastActivated = System.currentTimeMillis();
	}
	
	/**
	 * Toggle redstone lamps to show whether or not this stone is active <br>
	 * - isActive() = lamps ON <br>
	 * - !isActive() = lamps OFF <br>
	 * @param block - The indicator block {@link Material#REDSTONE_LAMP_OFF} or {@link Material#REDSTONE_LAMP_ON} <br>
	 */
	public void toggleIndicators(Block block) {
		if(!block.getType().equals(Material.REDSTONE_LAMP_OFF) || !block.getType().equals(Material.REDSTONE_LAMP_ON)) return;
		for(BlockFace face : Hunted.getInstance().getStoneManager().getValidFaces()) {
		    if(block.getRelative(face).getType().equals(Material.REDSTONE_LAMP_OFF) || block.getRelative(face).getType().equals(Material.REDSTONE_LAMP_ON)) {
		        block.setType(this.isActive() ? Material.REDSTONE_LAMP_ON : Material.REDSTONE_LAMP_OFF);
		    }
		}
	}
	
	/**
	 * Set cooldown for guardian stone
	 * @param {@link #cooldown} - time in minutes
	 */
	public void setCooldown(int cooldown) {
		this.cooldown = TimeUnit.MINUTES.toMillis(cooldown);
	}
	
	/**
	 * Returns if the guardian stone is active or not
	 * @return 
	 */
	public boolean isActive() {
		return getElapsedTime() >= TimeUnit.MILLISECONDS.toMinutes(cooldown);
	}
	
	/**
	 * Returns how long ago this stone was activated(in minutes)
	 */
	public int getElapsedTime() {
		return (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - lastActivated);
	}
	
    public int getCooldown() {
        return (int) TimeUnit.MILLISECONDS.toMinutes(cooldown);
    }
	
	@Override
	public StoneType getType() {
		return StoneType.GUARDIAN;
	}

}
