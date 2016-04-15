package net.senmori.hunted.commands.give.parameters;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.inventory.ItemStack;

public class GivePotionCommand extends Subcommand {
	
	public GivePotionCommand() {
		this.name = "potion";
		this.description = "give potion";
		this.permission = Permissions.ADMIN_EXEMPT;
		this.needsPlayer = true;
	}
	
	@Override
	protected void perform() {
		ItemStack potion = Hunted.getInstance().getPotionManager().getPotion();
		getPlayer().getInventory().setItem(7, potion);
	}

}
