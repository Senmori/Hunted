package net.senmori.hunted.commands.add.parameters;

import java.util.Set;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.Stone.StoneType;
import net.senmori.hunted.stones.TeleportStone;
import net.senmori.hunted.util.Reference.ErrorMessage;
import net.senmori.hunted.util.Reference.Permissions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class AddStone extends Subcommand {
	public AddStone() {
		this.name = "stone";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_ADD;
	}
	
	@Override
    protected void perform() {
		Block targetBlock = getPlayer().getTargetBlock((Set<Material>)null, 5);
		String stoneName = args.length == 2 ? args[1] : "";
		int count = 0;
		for(String i : args) {
			getPlayer().sendMessage("Arg " + count++ +  ": " + i);
		}
		// player isn't looking at a valid block
		if(targetBlock == null)
		{
			getPlayer().sendMessage(ChatColor.YELLOW + ErrorMessage.STONE_CREATION_ERROR);
			return;
			
		}
		switch(this.args[0])
		{
			case "guardian":
			case "-g":
				createNewStone(StoneType.GUARDIAN, targetBlock.getLocation(), stoneName);
				break;
			case "teleport":
			case "-t":
				createNewStone(StoneType.TELEPORT, targetBlock.getLocation(), stoneName);
				break;
			default:
				break;
			
		}
    }
	
	private void createNewStone(StoneType type, Location loc, String name) {
		switch(type) {
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
}
