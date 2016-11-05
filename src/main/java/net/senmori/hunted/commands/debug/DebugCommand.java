package net.senmori.hunted.commands.debug;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.MapConfiguration;
import net.senmori.hunted.lib.game.GameState;
import net.senmori.hunted.tasks.PlayerGlowTask;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class DebugCommand extends Subcommand {
    
	public DebugCommand() {
		this.name = "debug";
		this.needsPlayer = true;
		this.permission = Permissions.ADMIN_DEBUG;
	}
    
	@Override
	protected void perform() {
       Hunted.getInstance().getPlayerManager().setState(getPlayer().getUniqueId(), GameState.IN_GAME);
        getPlayer().sendMessage("GAME STATE: " + Hunted.getInstance().getPlayerManager().getState(player.getUniqueId()));
    }

}
