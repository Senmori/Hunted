package net.senmori.hunted.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class EventManager 
{
	private static JavaPlugin plugin;
	private static List<Listener> listeners = new ArrayList<Listener>();
	

	protected <T extends EventManager> void registerEvent(Listener c, JavaPlugin instance)
	{
		plugin = instance;
	    addListener(c, instance);
	}
	
	public void registerAll()
	{
		for(Listener c : getListeners())
		{
			PluginManager pm = plugin.getServer().getPluginManager();
			pm.registerEvents(c, plugin);
		}
	}
	
	private void addListener(Listener c, JavaPlugin instance)
	{
	    listeners.add(c);
	}
	
	public List<Listener> getListeners()
	{
		return listeners;
	}
}
