package net.senmori.hunted.commands.add.parameters;

import java.util.Arrays;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;

public class AddMapConfiguration extends Subcommand {

    public AddMapConfiguration() {
        this.name = "map-configuration";
        this.aliases = Arrays.asList("mc", "mapc", "map-config");
        this.description = "Create a new map configuration";
        this.needsPlayer = true;
        this.permission = Permissions.COMMAND_ADD_MAP;
    }

    @Override
    protected void perform() {

    }

}
