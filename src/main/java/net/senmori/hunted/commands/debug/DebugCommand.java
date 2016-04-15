package net.senmori.hunted.commands.debug;


import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.loot.LootTable;
import net.senmori.hunted.loot.LootTableManager;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DebugCommand extends Subcommand {

	public DebugCommand() {
		this.name = "debug";
		this.needsPlayer = true;
		this.permission = Permissions.ADMIN_DEBUG;
	}

	@Override
	protected void perform() {
		ResourceLocation debugTableLocation = new ResourceLocation("hunted:chests/debug");
		LootTable table = LootTableManager.getLootTable(debugTableLocation);

		getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.SMOOTH_BRICK));
	}
}
