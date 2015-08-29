package net.senmori.hunted.commands.list.parameters;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;

public class ListTeleportStones extends Subcommand {

    public ListTeleportStones() {
        this.name = "teleport";
        this.needsPlayer = true;
        this.description = "Lists all registered Teleport Stones for this map";
        this.permission = Permissions.COMMAND_LIST_TELEPORT;
    }

    @Override
    protected void perform() {

    }

}
