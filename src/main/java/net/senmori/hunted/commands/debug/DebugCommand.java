package net.senmori.hunted.commands.debug;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;
import org.bukkit.attribute.Attribute;

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
