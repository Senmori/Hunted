package net.senmori.hunted.managers.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.stones.TeleportStone;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class StoneManager 
{
    private List<Stone> masterStoneList;
	
	private List<GuardianStone> guardianStoneList;
	private List<TeleportStone> teleportStoneList;
	
	private List<BlockFace> faces;
	
	public StoneManager() {
		masterStoneList = new ArrayList<Stone>();
		guardianStoneList = new ArrayList<GuardianStone>();
		teleportStoneList = new ArrayList<TeleportStone>();
		faces = Arrays.asList(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH);
	}
	
	/** Returns whether or not the provided {@link Material} is a valid activator for a {@link Stone} */
	public boolean isValidActivator(Material material)
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
	public void register(Stone stone)
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
	public Stone getStone(Location loc)
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
	public Stone getStone(String name)
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
	
	/** Returns all valid blockfaces for indicator blocks */
	public List<BlockFace> getValidFaces() {
	    return faces;
	}
	
	/** Returns all registered {@link Stone} */
	public List<Stone> getStones()
	{
		return masterStoneList;
	}
	
	/** Returns all registered {@link GuardianStone} */
	public List<GuardianStone> getGuardianStones()
	{
		return guardianStoneList;
	}
	
	/** Returns all registered {@link TeleportStone} */
	public List<TeleportStone> getTeleportStones()
	{
		return teleportStoneList;
	}
}
