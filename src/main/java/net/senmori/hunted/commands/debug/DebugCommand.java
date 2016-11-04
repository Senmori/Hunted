package net.senmori.hunted.commands.debug;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.tasks.PlayerGlowTask;
import net.senmori.hunted.util.Reference.Permissions;

public class DebugCommand extends Subcommand {
    
	public DebugCommand() {
		this.name = "debug";
		this.needsPlayer = true;
		this.permission = Permissions.ADMIN_DEBUG;
	}
    
	@Override
	protected void perform() {
        new PlayerGlowTask(Hunted.getInstance(), getPlayer().getUniqueId(), (int)(Math.random() * (Hunted.getInstance().getConfigManager().maxEffectLength - 1) + 1));
    }
}
