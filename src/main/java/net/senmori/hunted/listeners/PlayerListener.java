package net.senmori.hunted.listeners;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.managers.EventManager;
import net.senmori.hunted.managers.game.StoneManager;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.stones.Stone.Type;

import org.bukkit.block.Block;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.material.Attachable;

public class PlayerListener extends EventManager {

	public PlayerListener() {
		registerEvent(this, Hunted.getInstance());
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(Hunted.activeWorld)) return;
		// ignore everything but buttons and wall sign
		if(!StoneManager.isValidActivator(e.getClickedBlock().getType()))
		{
			e.setCancelled(true);
			e.setUseInteractedBlock(Result.DENY);
			e.setUseItemInHand(Result.DENY);
			return;
		}
		
		Block stone = null;
		if( e.getClickedBlock() instanceof Attachable)
		{
			Attachable attach = (Attachable) e.getClickedBlock();
			stone = e.getClickedBlock().getRelative(attach.getAttachedFace());
		}
		
		// if stone is null, then what was the button attached to O.o
		if(stone != null)
		{
			Stone s = StoneManager.getStone(stone.getLocation());
			if(!Hunted.getPlayerManager().canInteractWithStone(s, e.getPlayer())) return;
			if(s.getType().equals(Type.GUARDIAN))
			{
				// if this is a guardian stone, light up nearby redstone_lamps on/off
				GuardianStone gStone = (GuardianStone)s;
				gStone.toggleIndicators(stone);
			}
			s.activate(e.getPlayer());
		}
	}
	
	// handle players logging in/out of hunted arena
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e)
	{
		if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(Hunted.activeWorld)) return;
		Hunted.getPlayerManager().setupPlayer(e.getPlayer());
	}
	
	// handle players logging out
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e)
	{
		if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(Hunted.activeWorld)) return;
		Hunted.getPlayerManager().setupPlayer(e.getPlayer());
	}
	
	// handle kicking of players
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e)
	{
		if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(Hunted.activeWorld)) return;
		Hunted.getPlayerManager().setupPlayer(e.getPlayer());
	}
	
	// handle changing of worlds(tp most likely)
	@EventHandler
	public void onPlayerBan(PlayerChangedWorldEvent e)
	{
		if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(Hunted.activeWorld)) return;
		Hunted.getPlayerManager().setupPlayer(e.getPlayer());
	}
}
