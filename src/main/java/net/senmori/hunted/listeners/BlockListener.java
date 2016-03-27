package net.senmori.hunted.listeners;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;

public class BlockListener implements Listener {
	private Hunted plugin;

	public BlockListener(Hunted plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onSignPlace(SignChangeEvent e) {
		e.setLine(0, ChatColor.RED + "[PotionSigns]");
		e.setLine(1, ChatColor.BLUE + "NIGHT_VISION");
		e.setLine(2, ChatColor.MAGIC + "" + Math.random() * (10-1) + 1);
		e.setLine(3, ChatColor.DARK_PURPLE + "I've used signs, yo");
	}
	
	// called when a block is broken
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (plugin.getPlayerManager().isPlaying(e.getPlayer().getUniqueId())) {
			if (e.getBlock().getWorld().getName().equals(plugin.getConfigManager().activeWorld)) {
				if(plugin.getPlayerManager().isExempt(e.getPlayer())) return;
				e.setCancelled(plugin.getPlayerManager().canBreakBlocks(e.getPlayer()));
			}
		}
	}

	// called when a block is placed
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		
		if (plugin.getPlayerManager().isPlaying(e.getPlayer().getUniqueId())) {
			if (e.getBlock().getWorld().getName().equals(plugin.getConfigManager().activeWorld)) {
				if(plugin.getPlayerManager().isExempt(e.getPlayer())) return;
				e.setCancelled(plugin.getPlayerManager().canPlaceBlocks(e.getPlayer()));
				e.setBuild(plugin.getPlayerManager().canPlaceBlocks(e.getPlayer()));
			}
		}
	}

	// called when a block's redstone level is changed
	@EventHandler
	public void onBlockRedstoneEvent(BlockRedstoneEvent e) {
		if (plugin.getConfigManager().activeWorld.equals(e.getBlock().getWorld().getName())) {
			e.setNewCurrent(e.getOldCurrent());
		}
	}

	// called when a piston changes it's state
	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent e) {
		if (!e.getBlock().getWorld().getName().equalsIgnoreCase(plugin.getConfigManager().activeWorld)) return;
		e.setCancelled(true);
	}

	@EventHandler
	public void onPistonRetract(BlockPistonRetractEvent e) {
		if (!e.getBlock().getWorld().getName().equalsIgnoreCase(plugin.getConfigManager().activeWorld)) return;
		e.setCancelled(true);
	}

	// called when a block is ignited, except by when a player places fire
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent e) {
		if (e.getBlock().getWorld().getName().equalsIgnoreCase(plugin.getConfigManager().activeWorld)) {
			if(e.getIgnitingEntity() instanceof Player) {
				Player player = (Player)e.getIgnitingEntity();
				if(!plugin.getPlayerManager().canPlaceBlocks(player)) {
					e.setCancelled(true);
					return;
				}
			}
			e.setCancelled(true);
		}
	}
}
