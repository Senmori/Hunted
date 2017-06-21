package net.senmori.hunted.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import net.senmori.hunted.Hunted;
import org.bukkit.entity.Player;

@CommandAlias("debug")
@CommandPermission("hunted.debug")
public class DebugCommand extends BaseCommand {

    private Hunted plugin;
    public DebugCommand(Hunted plugin) {
        this.plugin = plugin;
    }


    @Default
    public void debugCommand(@Optional Player player) {

    }
}
