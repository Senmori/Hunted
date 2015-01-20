package net.senmori.hunted.managers.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.senmori.hunted.stones.AdminStone;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.InfoStone;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.stones.TeleportStone;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class StoneManager 
{
	public static List<Stone> stoneList;
	
	public static List<GuardianStone> guardianStoneList;
	public static List<AdminStone> adminStoneList;
	public static List<InfoStone> infoStoneList;
	public static List<TeleportStone> teleportStoneList;
	
	public static List<BlockFace> faces;
	
	static
	{
		stoneList = new ArrayList<Stone>();
		guardianStoneList = new ArrayList<GuardianStone>();
		adminStoneList = new ArrayList<AdminStone>();
		infoStoneList = new ArrayList<InfoStone>();
		teleportStoneList = new ArrayList<TeleportStone>();
		faces = Arrays.asList(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH);
	}
	
	/*
	 * Returns whether or not a material can activate a stone
	 */
	public static boolean isValidActivator(Material material)
	{
		switch(material)
		{
			case STONE_BUTTON:
			case WOOD_BUTTON:
			case WALL_SIGN:
				return true;
			default:
				return false;
		}
	}
	
	public static void add(Stone stone)
	{
		if(stone == null || stone.getType() == null) return;
		stoneList.add(stone);
		switch(stone.getType())
		{
			case GUARDIAN:
					guardianStoneList.add((GuardianStone) stone);
					return;
			case ADMIN:
					adminStoneList.add((AdminStone)stone);
					return;
			case INFO:
					infoStoneList.add((InfoStone)stone);
					return;
			case TELEPORT:
					teleportStoneList.add((TeleportStone)stone);
					return;
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
		return guardianStoneList;
	}
	
	public static List<AdminStone> getAdminStones()
	{
		return adminStoneList;
	}
	
	public static List<InfoStone> getInfoStones()
	{
		return infoStoneList;
	}
	
	public static List<TeleportStone> getTeleportStones()
	{
		return teleportStoneList;
	}
}
