package net.senmori.hunted.stones;

import java.io.IOException;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.entity.Player;

public class InfoStone extends Stone
{
	
	private String[] info;
	public InfoStone(SerializedLocation loc)
	{
		super(loc);
	}
	@Override
	public void activate(Player player) 
	{
		player.sendMessage(getInfo());	
	}
	
	@Override
	public Type getType()
	{
		return Type.INFO;
	}
	
	public String[] getInfo()
	{
		for(int i = 0; i < info.length; i++)
		{
			String s = ChatColor.translateAlternateColorCodes('&', info[i]);
			info[i] = s;
		}
		return info;
	}
	
	/*
	 * Provide only path if the information is already present in the config
	 */
	public void addOrCreateInfo(String path, String newInfo)
	{
		List<String> oldList = Hunted.stoneConfig.getStringList("stone_info." + path);
		
		oldList.add(newInfo);
		
		Hunted.stoneConfig.set("stone_info." + path, oldList);
		try 
		{
			Hunted.stoneConfig.save(Hunted.stoneConfigFile);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
