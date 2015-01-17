package net.senmori.hunted.managers.game;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.stones.AdminStone;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.InfoStone;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.stones.TeleportStone;

import org.bukkit.Location;

public class StoneManager 
{
	public static List<Stone> stoneList;
	
	public static List<GuardianStone> gStoneList;
	public static List<AdminStone> aStoneList;
	public static List<InfoStone> iStoneList;
	public static List<TeleportStone> tStoneList;
	
	static
	{
		stoneList = new ArrayList<Stone>();
		gStoneList = new ArrayList<GuardianStone>();
		aStoneList = new ArrayList<AdminStone>();
		iStoneList = new ArrayList<InfoStone>();
		tStoneList = new ArrayList<TeleportStone>();
	}
	
	public static void add(Stone stone)
	{
		if(stone == null || stone.getType() == null) return;
		stoneList.add(stone);
		switch(stone.getType())
		{
			case GUARDIAN:
					gStoneList.add((GuardianStone) stone);
			case ADMIN:
					aStoneList.add((AdminStone)stone);
			case INFO:
					iStoneList.add((InfoStone)stone);
			case TELEPORT:
					tStoneList.add((TeleportStone)stone);
			default:
					return;
		}
	}
	
	public static Stone getStone(Location loc)
	{
		for(Stone s : stoneList)
		{
			if(s.getLocation().equals(loc))
			{
				return s;
			}
		}
		return null;
	}
	
	public static Stone getStone(String name)
	{
		for(Stone s : stoneList)
		{
			if(s.getName().equalsIgnoreCase(name))
			{
				return s;
			}
		}
		return null;
	}
	
	public static List<Stone> getStones()
	{
		return stoneList;
	}
	
	public static List<GuardianStone> getGuardianStones()
	{
		return gStoneList;
	}
	
	public static List<AdminStone> getAdminStones()
	{
		return aStoneList;
	}
	
	public static List<InfoStone> getInfoStones()
	{
		return iStoneList;
	}
	
	public static List<TeleportStone> getTeleportStones()
	{
		return tStoneList;
	}
}
