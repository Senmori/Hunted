package net.senmori.hunted.commands.stuck;

import java.util.Arrays;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.lib.game.GameState;
import net.senmori.hunted.util.Reference.ErrorMessage;
import net.senmori.hunted.util.Reference.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StuckCommand extends Subcommand {

	public StuckCommand() {
		this.name = "stuck";
		this.needsPlayer = true;
		this.description = "Command used if a player is stuck.";
		this.permission = Permissions.COMMAND_STUCK;
		this.optionalArgs = Arrays.asList("player");
	}

	@Override
	protected void perform() {
		if(args.length > 0) {
			if (getPlayer().hasPermission(Permissions.COMMAND_STUCK_OTHER)) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player player = Bukkit.getPlayer(args[0]);
                    teleport(player, Hunted.getInstance().getPlayerManager().getState(player.getUniqueId().toString()));
                    return;
                }
            } else { // no permission
                getPlayer().sendMessage(ChatColor.RED + ErrorMessage.NO_COMMAND_PERMISSION);
                return;
            }
		}
		teleport(getPlayer(), Hunted.getInstance().getPlayerManager().getState(getPlayer().getUniqueId().toString()));
	}
	
	
	private void teleport(Player player, GameState state) {
	   switch(state) {
           case IN_GAME:
               player.teleport(Hunted.getInstance().getSpawnManager().getRandomHuntedLocation().getLocation());
               return;
           case IN_STORE:
               player.teleport(Hunted.getInstance().getSpawnManager().getRandomStoreLocation().getLocation());
               return;
           default:
               player.teleport(Hunted.getInstance().getSpawnManager().getRandomLobbyLocation().getLocation());
           
       }
	}

}
