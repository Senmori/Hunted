package net.senmori.hunted.listeners;

import net.senmori.hunted.Hunted;

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

public class BlockListener implements Listener {
	private Hunted plugin;

	public BlockListener(Hunted plugin) {
		this.plugin = plugin;
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
