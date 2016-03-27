package net.senmori.hunted.commands.debug;


import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.loot.LootTable;
import net.senmori.hunted.loot.gui.LootGUI;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.util.Reference.Permissions;

public class DebugCommand extends Subcommand {

	public DebugCommand() {
		this.name = "debug";
		this.needsPlayer = true;
		this.permission = Permissions.ADMIN_DEBUG;
	}

	@Override
	protected void perform() {
		
		
	}
}
