package net.senmori.hunted.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class EventManager implements Listener
{
	private static List<Listener> listeners = new ArrayList<Listener>();
	

	protected <T extends EventManager> void registerEvent(Listener c, JavaPlugin instance)
	{
	    addListener(c, instance);
	    PluginManager pm = instance.getServer().getPluginManager();
		pm.registerEvents(c, instance);
	}
	
	private void addListener(Listener c, JavaPlugin instance)
	{
	    listeners.add(c);
	}
	
	public static List<Listener> getListeners()
	{
		return listeners;
	}
}
