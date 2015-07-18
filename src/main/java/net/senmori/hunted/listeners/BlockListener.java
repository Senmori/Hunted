package net.senmori.hunted.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockListener implements Listener
{
	// called when a block is broken
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		if(!e.getBlock().getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		e.setCancelled(net.senmori.hunted.Hunted.getPlayerManager().canBreakBlocks(e.getPlayer()));
	}
	
	// called when a block is placed
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		if(!e.getBlock().getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		e.setCancelled(net.senmori.hunted.Hunted.getPlayerManager().canPlaceBlocks(e.getPlayer()));
		e.setBuild(net.senmori.hunted.Hunted.getPlayerManager().canPlaceBlocks(e.getPlayer()));
	}
	
	// called when a block's redstone level is changed
	@EventHandler
	public void onBlockRedstoneEvent(BlockRedstoneEvent e)
	{
		if(!e.getBlock().getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		e.setNewCurrent(e.getOldCurrent());
	}
	
	// called when a piston changes it's state
	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent e)
	{
		if(!e.getBlock().getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPistonRetract(BlockPistonRetractEvent e)
	{
		if(!e.getBlock().getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		e.setCancelled(true);
	}
	
	// called when a block is ignited, except by when a player places fire
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent e)
	{
		if(!e.getBlock().getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		e.setCancelled(true);
	}
}
