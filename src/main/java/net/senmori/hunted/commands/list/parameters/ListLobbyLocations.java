package net.senmori.hunted.commands.list.parameters;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;

public class ListLobbyLocations extends Subcommand {

    public ListLobbyLocations() {
        this.name = "lobby";
        this.needsPlayer = true;
        this.description = "Lists all registered lobby locations for this map";
        this.permission = Permissions.COMMAND_LIST_LOBBY;
    }

    @Override
    protected void perform() {

    }

}
