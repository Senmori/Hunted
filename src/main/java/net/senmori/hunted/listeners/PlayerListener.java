package net.senmori.hunted.listeners;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.managers.game.StoneManager;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.stones.Stone.Type;
import net.senmori.hunted.lib.ActionBarAPI;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.material.Attachable;

public class PlayerListener implements Listener
{
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		// ignore everything but buttons and wall sign
		if (!StoneManager.isValidActivator(e.getClickedBlock().getType()))
		{
			e.setCancelled(true);
			e.setUseInteractedBlock(Result.DENY);
			e.setUseItemInHand(Result.DENY);
			return;
		}

		Block block = null;
		if (e.getClickedBlock() instanceof Attachable)
		{
			Attachable attach = (Attachable) e.getClickedBlock();
			block = e.getClickedBlock().getRelative(attach.getAttachedFace());
		}

		// if stone is null, then what was the button attached to O.o
		if (block != null)
		{
			Stone s = StoneManager.getStone(block.getLocation());
			if (!net.senmori.hunted.Hunted.getPlayerManager().canInteractWithStone(s, e.getPlayer())) return;
			if (s.getType().equals(Type.GUARDIAN))
			{
				// if this is a guardian stone, light up nearby redstone_lamps
				// on/off
				((GuardianStone) s).toggleIndicators(block);
			}
			s.activate(e.getPlayer());
		}
	}

	// handle players logging in/out of hunted arena
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e)
	{
		if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		if(net.senmori.hunted.Hunted.getPlayerManager().isExemptFromSetup(e.getPlayer())) return;
		net.senmori.hunted.Hunted.getPlayerManager().setupPlayer(e.getPlayer());
	}

	// handle players logging out
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e)
	{
		if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		if(net.senmori.hunted.Hunted.getPlayerManager().isExemptFromSetup(e.getPlayer())) return;
		net.senmori.hunted.Hunted.getPlayerManager().setupPlayer(e.getPlayer());
	}

	// handle kicking of players
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e)
	{
		if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		net.senmori.hunted.Hunted.getPlayerManager().setupPlayer(e.getPlayer());
	}

	// handle changing of worlds(tp most likely)
	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent e)
	{
		if (!e.getFrom().toString().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		if(net.senmori.hunted.Hunted.getPlayerManager().isExemptFromSetup(e.getPlayer())) return;
		net.senmori.hunted.Hunted.getPlayerManager().setupPlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e)
	{
		// ignore all death events that aren't in the world 'Hunted' is active in
		if(!e.getEntity().getWorld().getName().equalsIgnoreCase(net.senmori.hunted.Hunted.activeWorld)) return;
		if(net.senmori.hunted.Hunted.getPlayerManager().isExemptFromSetup(e.getEntity())) return;
		
		e.getDrops().clear();
		// eventually add random drop of 1-3 items here...
		e.setDroppedExp(0);
	}
	
	@EventHandler
	public void onPlayerKillEntity(EntityDeathEvent e)
	{
		if(e.getEntity().getKiller() instanceof Player)
		{
			ActionBarAPI.send(e.getEntity().getKiller(), ChatColor.DARK_PURPLE + "\n+20 exp");
		}
	}
}
