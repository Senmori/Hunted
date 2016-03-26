package net.senmori.hunted.commands.debug;


import java.util.Set;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.loot.utils.LootUtil;
import net.senmori.hunted.util.Reference.Permissions;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class DebugCommand extends Subcommand {

	public DebugCommand() {
		this.name = "debug";
		this.needsPlayer = true;
		this.permission = Permissions.ADMIN_DEBUG;
	}

	@Override
	protected void perform() {
		
		Block target = getPlayer().getTargetBlock((Set<Material>)null, 5);
		
		if(target != null && LootUtil.isValidBlock(target)) {
			if(LootUtil.hasLootTable(target)) LootUtil.clearLootTable(target);
			LootUtil.setLootTable(target, "hunted:chests/debug");
		}
	}
}
