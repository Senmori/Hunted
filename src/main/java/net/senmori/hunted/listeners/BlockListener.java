package net.senmori.hunted.listeners;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.EventManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener extends EventManager {

	public BlockListener() 
	{
		registerEvent(this, Hunted.getInstance());
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		e.setCancelled(Hunted.getPlayerManager().canBreakBlocks(e.getPlayer()));
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		e.setCancelled(Hunted.getPlayerManager().canPlaceBlocks(e.getPlayer()));
		e.setBuild(Hunted.getPlayerManager().canPlaceBlocks(e.getPlayer()));
	}

}
