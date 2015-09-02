package net.senmori.hunted.commands.debug;

import org.bukkit.ChatColor;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;
import net.senmori.hunted.util.Reference.RewardMessage;

public class DebugCommand extends Subcommand
{

	public DebugCommand()
	{
		this.name = "debug";
		this.needsPlayer = true;
		this.permission = Permissions.ADMIN_DEBUG;
	}

	@Override
	protected void perform() {
	    Hunted.getInstance().getArmorManager().generateArmor(getPlayer());
	    getPlayer().sendMessage(ChatColor.GREEN + RewardMessage.IRRITATING_MESSAGE);
	}
}
