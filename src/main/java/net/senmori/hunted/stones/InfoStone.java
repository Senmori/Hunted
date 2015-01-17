package net.senmori.hunted.stones;

import java.io.IOException;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.util.SerializedLocation;

import org.bukkit.entity.Player;

public class InfoStone extends Stone
{
	
	private List<String> info;
	public InfoStone(SerializedLocation loc)
	{
		super(loc);
	}
	@Override
	public void activate(Player player) 
	{
		player.sendMessage((String[]) getInfo().toArray());
	}
	
	@Override
	public Type getType()
	{
		return Type.INFO;
	}
	
	public List<String> getInfo()
	{
		for(String str : info)
		{
			String s = ChatColor.translateAlternateColorCodes('&', str);
			info.add(s);
		}
		return info;
	}
	
	/*
	 * Provide only path if the information is already present in the config
	 */
	public void addOrCreateInfo(String path, String newInfo)
	{
		List<String> newList = Hunted.stoneConfig.getStringList("stone_info." + path);
		
		newList.add(newInfo);
		
		Hunted.stoneConfig.set("stone_info." + path, newList);
		save();
	}
	
	public void addOrCreateInfo(String path, List<String> newInfoList)
	{
		List<String> oldInfoList = Hunted.stoneConfig.getStringList("stone_info." + path);
		
		oldInfoList.addAll(newInfoList);
		Hunted.stoneConfig.set("stone_info." + path, oldInfoList);
		save();
	}
	
	private void save()
	{
		try
		{
			Hunted.stoneConfig.save(Hunted.stoneConfigFile);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
