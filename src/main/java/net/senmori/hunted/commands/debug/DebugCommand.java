package net.senmori.hunted.commands.debug;


import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.loot.core.LootTable;
import net.senmori.hunted.loot.core.LootTableManager;
import net.senmori.hunted.loot.menu.PoolSelectionMenu;
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
        LootTable table = LootTableManager.getLootTable(new ResourceLocation("hunted:chests/debug"));
        PoolSelectionMenu menu = new PoolSelectionMenu(table);
        menu.show(getPlayer());
    }
}
