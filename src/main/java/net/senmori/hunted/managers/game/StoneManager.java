package net.senmori.hunted.managers.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.stones.Stone.StoneType;
import net.senmori.hunted.stones.TeleportStone;

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
	
	/** Returns whether or not the provided {@link Material} is a valid activator for a {@link Stone} */
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
	
	/** Adds a new {@link Stone} */
	public static void register(Stone stone)
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
	
	/** Gets a {@link Stone} no matter the type by location*/
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
	
	/** Gets a {@link Stone} by name, no matter the location or type */
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
	
	/** Returns all registered {@link Stone} */
	public static List<Stone> getStones()
	{
		return masterStoneList;
	}
	
	/** Returns all registered {@link GuardianStone} */
	public static List<GuardianStone> getGuardianStones()
	{
		return guardianStoneList;
	}
	
	/** Returns all registered {@link TeleportStone} */
	public static List<TeleportStone> getTeleportStones()
	{
		return teleportStoneList;
	}
	
	public static void saveLocations()
	{
		for(Stone stone : getStones())
		{
			Hunted.getInstance().getConfig().set("stones." + stone.getName() + ".x", stone.getLocation().getBlockX());
			Hunted.getInstance().getConfig().set("stones." + stone.getName() + ".y", stone.getLocation().getBlockY());
			Hunted.getInstance().getConfig().set("stones." + stone.getName() + ".z", stone.getLocation().getBlockZ());
			Hunted.getInstance().getConfig().set("stones." + stone.getName() + ".type", stone.getType().toString());
			
			if(stone.getType().equals(StoneType.GUARDIAN))
			{
				Hunted.getInstance().getConfig().set("stones." + stone.getName() + ".cooldown", ((GuardianStone)stone).getCooldown());
			}
		}
	}
}
