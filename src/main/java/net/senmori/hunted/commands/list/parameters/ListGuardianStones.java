package net.senmori.hunted.commands.list.parameters;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;

public class ListGuardianStones extends Subcommand {

    public ListGuardianStones() {
        this.name = "guardian";
        this.needsPlayer = true;
        this.description = "Lists all registered Guardian Stones for this map";
        this.permission = Permissions.COMMAND_LIST_GUARDIAN;
    }

    @Override
    protected void perform() {

    }

}
