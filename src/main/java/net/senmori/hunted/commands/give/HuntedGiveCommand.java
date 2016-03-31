package net.senmori.hunted.commands.give;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.commands.give.parameters.GiveArmorCommand;
import net.senmori.hunted.commands.give.parameters.GivePotionCommand;
import net.senmori.hunted.commands.give.parameters.GiveWeaponCommand;
import net.senmori.hunted.util.Reference.Permissions;

import java.util.ArrayList;
import java.util.List;

public class HuntedGiveCommand extends Subcommand {
	
	private List<Subcommand> parameters;
	public HuntedGiveCommand() {
		this.name = "give";
		this.description = "give command";
		this.permission = Permissions.COMMAND_DEV_TOOLS;
		this.needsPlayer = false;
		
		parameters = new ArrayList<Subcommand>();
		parameters.add(new GiveArmorCommand());
		parameters.add(new GivePotionCommand());
		parameters.add(new GiveWeaponCommand());
	}
	
	@Override
    protected void perform() {
		List<String> argsList = new ArrayList<String>();
		if (args.length > 0) {
			String commandName = args[0].toLowerCase();

			for (int i = 1; i < args.length; i++) {
				argsList.add(args[i]);
			}
			for (Subcommand command : this.parameters) {
				if (command.getName().equalsIgnoreCase(commandName) || command.getAliases().contains(commandName)) {
					command.execute(sender, argsList.toArray(new String[argsList.size()]));
					return;
				}
			}
		} else {
			getPlayer().sendMessage(getUsageTemplate(true));
		}
		return;
	    
    }

}
