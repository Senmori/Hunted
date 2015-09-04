package net.senmori.hunted.commands.debug;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.kit.armor.ArmorSlot;
import net.senmori.hunted.util.Reference.Permissions;

import org.bukkit.inventory.ItemStack;

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
	    getPlayer().sendMessage("Generating armor");
	    ItemStack piece = Hunted.getInstance().getArmorManager().generatePiece(ArmorSlot.HELMET);
	    getPlayer().sendMessage(piece.getType().toString());
	    getPlayer().getInventory().addItem(piece);
	}
}
