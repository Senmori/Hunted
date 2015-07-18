package net.senmori.hunted.managers.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.stones.TeleportStone;
import net.senmori.hunted.stones.Stone.Type;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class StoneManager 
{
	public static List<Stone> masterStoneList;
	
	public static List<GuardianStone> guardianStoneList;
	public static List<TeleportStone> teleportStoneList;
	
	public static List<BlockFace> faces;
	
	static
	{
		masterStoneList = new ArrayList<Stone>();
		guardianStoneList = new ArrayList<GuardianStone>();
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
		masterStoneList.add(stone);
		switch(stone.getType())
		{
			case GUARDIAN:
					guardianStoneList.add((GuardianStone) stone);
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
		for(Stone s : masterStoneList)
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
		for(Stone s : masterStoneList)
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
		return masterStoneList;
	}
	
	public static List<GuardianStone> getGuardianStones()
	{
		return guardianStoneList;
	}
	
	public static List<TeleportStone> getTeleportStones()
	{
		return teleportStoneList;
	}
	
	public static void saveLocations()
	{
		for(String name : net.senmori.hunted.Hunted.stoneConfig.getConfigurationSection("stones").getKeys(false))
		{
			for(Stone stone : getStones())
			{
				net.senmori.hunted.Hunted.stoneConfig.set("stones." + name + ".x", stone.getLocation().getBlockX());
				net.senmori.hunted.Hunted.stoneConfig.set("stones." + name + ".y", stone.getLocation().getBlockY());
				net.senmori.hunted.Hunted.stoneConfig.set("stones." + name + ".z", stone.getLocation().getBlockZ());
				net.senmori.hunted.Hunted.stoneConfig.set("stones." + name + ".type", stone.getType().toString());
				
				if(stone.getType().equals(Type.GUARDIAN))
				{
					net.senmori.hunted.Hunted.stoneConfig.set("stones." + name + ".cooldown", ((GuardianStone)stone).getCooldown());
				}
			}
		}
	}
}
