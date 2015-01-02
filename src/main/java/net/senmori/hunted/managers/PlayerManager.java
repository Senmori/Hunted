package net.senmori.hunted.managers;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.lib.PlayerFile;

public class PlayerManager 
{
	
	private List<PlayerFile> playing;
	
	public PlayerManager()
	{
		playing = new ArrayList<PlayerFile>();
	}
	
	public boolean isPlaying(String uuid)
	{
		for(PlayerFile pf : playing)
		{
			if(pf.getUUID().equalsIgnoreCase(uuid))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean addPlayer(PlayerFile player)
	{
		for(PlayerFile pf : playing)
		{
			// if that player is already playing, why try to add them again?
			if(pf.getUUID().equalsIgnoreCase(player.getUUID()))
			{
				return false;
			}
		}
		playing.add(player);
		return true;
	}
	
	public boolean addPlayer(String uuid)
	{
		for(PlayerFile pf : playing)
		{
			if( pf.getUUID().equalsIgnoreCase(uuid) )
			{
				return false;
			}
		}
		playing.add(getPlayerFile(uuid));
		return true;
	}
	
	public boolean removePlayer(String uuid)
	{
		for(PlayerFile pf : playing)
		{
			if( pf.getUUID().equalsIgnoreCase(uuid))
			{
				playing.remove(pf);
				return true;
			}
		}
		return false;
	}
	
	public PlayerFile getPlayerFile(String uuid)
	{
		for(PlayerFile pf : playing)
		{
			if( pf.getUUID().equalsIgnoreCase(uuid))
			{
				return pf;
			}
		}
		return null;
	}
}
