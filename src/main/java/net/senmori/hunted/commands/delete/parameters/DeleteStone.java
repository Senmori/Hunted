package net.senmori.hunted.commands.delete.parameters;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.stones.Stone;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference.ErrorMessage;
import net.senmori.hunted.util.Reference.Permissions;
import net.senmori.hunted.util.Reference.SuccessMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Set;
import org.bukkit.event.block.Action;

public class DeleteStone extends Subcommand {

	public DeleteStone() {
		this.name = "stone";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_DELETE_STONE;
		this.optionalArgs = Arrays.asList("name");
	}

	@Override
	protected void perform() {
		// player specified a stone name to look for
		if (args.length >= 2) {
			String toRemove = args[1];
			Stone remove = Hunted.getInstance().getStoneManager().getStone(toRemove);
			if (remove == null) {
				ActionBar.sendMessage(getPlayer(), ChatColor.RED + ErrorMessage.STONE_DELETE_ERROR);
				return;
			}
			Hunted.getInstance().getStoneManager().removeStone(remove);
			// getPlayer().sendMessage(ChatColor.GREEN +
			// SuccessMessage.STONE_REMOVED.replace("%stone", toRemove));
			ActionBar.sendMessage(getPlayer(), ChatColor.GREEN + MessageFormat.format(SuccessMessage.STONE_REMOVED, toRemove));
			return;
		}

		Block targetBlock = getPlayer().getTargetBlock((Set<Material>) null, 5);
		if (targetBlock == null) {
			ActionBar.sendMessage(getPlayer(), ChatColor.RED + ErrorMessage.STONE_DELETE_ERROR);
			return;
		}

		Stone targetStone = Hunted.getInstance().getStoneManager().getStone(targetBlock.getLocation());
		if (targetStone == null) {
			ActionBar.sendMessage(getPlayer(), ChatColor.RED + ErrorMessage.STONE_DELETE_ERROR);
			return;
		}
		Hunted.getInstance().getStoneManager().removeStone(targetStone);
		ActionBar.sendMessage(getPlayer(), ChatColor.GREEN + MessageFormat.format(SuccessMessage.STONE_REMOVED, targetStone.getName()));
	}

}
