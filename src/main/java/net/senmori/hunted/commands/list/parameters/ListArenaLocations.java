package net.senmori.hunted.commands.list.parameters;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;

public class ListArenaLocations extends Subcommand {

    public ListArenaLocations() {
        this.name = "arena";
        this.needsPlayer = true;
        this.description = "Lists all registered arena locations for this map";
        this.permission = Permissions.COMMAND_LIST_ARENA;
    }

    @Override
    protected void perform() {

    }

}
