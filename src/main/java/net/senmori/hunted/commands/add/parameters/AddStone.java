package net.senmori.hunted.commands.add.parameters;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.TeleportStone;
import net.senmori.hunted.stones.Stone.Type;
import net.senmori.hunted.util.SerializedLocation;
import net.senmori.hunted.util.Reference.DebugMessage;
import net.senmori.hunted.util.Reference.ErrorMessage;
import net.senmori.hunted.util.Reference.Permissions;

public class AddStone extends Subcommand
{
	public AddStone()
	{
		this.name = "stone";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_ADD;
	}
	
	@Override
    protected void perform()
    {
		Block targetBlock = getPlayer().getTargetBlock((Set<Material>)null, 5);
		
		// player isn't looking at a valid block
		if(targetBlock == null)
		{
			DebugMessage.sendMessage(getPlayer(), ErrorMessage.STONE_CREATION_ERROR);
			
		}
		switch(this.args[0])
		{
			case "guardian":
			case "-g":
				createNewStone(Type.GUARDIAN,targetBlock.getLocation());
				break;
			case "teleport":
			case "-t":
				createNewStone(Type.TELEPORT,targetBlock.getLocation());
				break;
			default:
				break;
			
		}
    }
	
	private void createNewStone(Type type, Location loc, String name)
	{
		switch(type)
		{
			case GUARDIAN:
				new GuardianStone(new SerializedLocation(loc,args[1]));
				break;
			case TELEPORT:
				new TeleportStone(new SerializedLocation(loc, args[1]));
				break;
			default:
				break;
		}
	}
	private void createNewStone(Type type, Location loc)
	{
		createNewStone(type, loc, "");
	}
}
