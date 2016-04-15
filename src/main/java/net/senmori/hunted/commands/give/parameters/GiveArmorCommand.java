package net.senmori.hunted.commands.give.parameters;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;

public class GiveArmorCommand extends Subcommand {
	
	public GiveArmorCommand() {
	    this.name = "armor";
	    this.description = "Give random armor";
		this.permission = Permissions.ADMIN_EXEMPT;
		this.needsPlayer = true;
    }

	@Override
	protected void perform() {
		Hunted.getInstance().getKitManager().generateArmor(getPlayer());
	}

}
