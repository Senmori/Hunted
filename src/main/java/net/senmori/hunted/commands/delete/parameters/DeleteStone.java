package net.senmori.hunted.commands.delete.parameters;

import java.util.Set;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.util.Reference.ErrorMessage;
import net.senmori.hunted.util.Reference.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class DeleteStone extends Subcommand{

    public DeleteStone() {
        this.name = "stone";
        this.needsPlayer = true;
        this.permission = Permissions.COMMAND_DELETE_STONE;
    }

    @Override
    protected void perform() {
        Block targetBlock = getPlayer().getTargetBlock((Set<Material>)null, 5);
        if(targetBlock == null) {
            getPlayer().sendMessage(ChatColor.RED + ErrorMessage.STONE_DELETE_ERROR);
            return;
        }
        
        Stone targetStone = Hunted.getInstance().getStoneManager().getStone(targetBlock.getLocation()); 
        
        Hunted.getInstance().getStoneManager().removeStone(targetStone);
    }

}
