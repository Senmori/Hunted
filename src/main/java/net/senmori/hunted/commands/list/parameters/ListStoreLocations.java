package net.senmori.hunted.commands.list.parameters;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;

public class ListStoreLocations extends Subcommand {

    public ListStoreLocations() {
        this.name = "store";
        this.needsPlayer = true;
        this.description = "Lists all registered store locations for this map";
        this.permission = Permissions.COMMAND_LIST_STORE;
    }

    @Override
    protected void perform() {

    }

}
