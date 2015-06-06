package net.senmori.hunted.listeners;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.EventManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockListener extends EventManager {

	public BlockListener() 
	{
		registerEvent(this, Hunted.getInstance());
	}
	
	// called when a block is broken
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		e.setCancelled(Hunted.getPlayerManager().canBreakBlocks(e.getPlayer()));
	}
	
	// called when a block is placed
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		e.setCancelled(Hunted.getPlayerManager().canPlaceBlocks(e.getPlayer()));
		e.setBuild(Hunted.getPlayerManager().canPlaceBlocks(e.getPlayer()));
	}
	
	// called when a block's redstone level is changed
	@EventHandler
	public void onBlockRedstoneEvent(BlockRedstoneEvent e)
	{
		if(!e.getBlock().getWorld().getName().equalsIgnoreCase(Hunted.activeWorld)) return;
		e.setNewCurrent(e.getOldCurrent());
	}
	
	// called when a piston changes it's state
	@EventHandler
	public void onPistonChangeState(BlockPistonEvent e)
	{
		if(!e.getBlock().getWorld().getName().equalsIgnoreCase(Hunted.activeWorld)) return;
		e.setCancelled(true);
	}
	
	// called when a block is ignited, except by when a player places fire
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent e)
	{
		if(!e.getBlock().getWorld().getName().equalsIgnoreCase(Hunted.activeWorld)) return;
		e.setCancelled(true);
	}
}
