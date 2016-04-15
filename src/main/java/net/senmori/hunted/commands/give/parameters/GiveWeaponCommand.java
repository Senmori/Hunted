package net.senmori.hunted.commands.give.parameters;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.inventory.ItemStack;

public class GiveWeaponCommand extends Subcommand {
	
	public GiveWeaponCommand() {
		this.name = "weapon";
		this.description = "give weapon";
		this.permission = Permissions.ADMIN_EXEMPT;
		this.needsPlayer = true;
	}
	
	@Override
	protected void perform() {
		ItemStack weapon = Hunted.getInstance().getWeaponManager().generateWeapon();
		getPlayer().getInventory().setItem(3,weapon);
		getPlayer().sendMessage("Generated " + weapon.getAmount() + " " + weapon.getType());
	}

}
