package net.senmori.hunted.commands.add;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.commands.add.parameters.AddRespawn;
import net.senmori.hunted.commands.add.parameters.AddSpawn;
import net.senmori.hunted.commands.add.parameters.AddStone;
import net.senmori.hunted.util.Reference.Permissions;

public class AddCommand extends Subcommand {

	private List<Subcommand> parameters;
	public AddCommand() 
	{
		
		this.name = "add";
		this.needsPlayer = true;
		this.description = "Command to add guardian stones and/or items";
		this.permission = Permissions.COMMAND_ADD;
		this.optionalArgs = Arrays.asList("stone|lobby|spawn");
		
		parameters = new ArrayList<Subcommand>();
		// add arguments
		parameters.add(new AddRespawn());
		parameters.add(new AddSpawn());
		parameters.add(new AddStone());
	}
	

	@Override
	protected void perform() 
	{
		List<String> argsList = new ArrayList<String>();
		if(args.length > 0)
		{
			String commandName = args[0].toLowerCase();
			
            for (int i = 1; i < args.length; i++)
            {
	            argsList.add(args[i]);
            }
			for(Subcommand command : this.parameters)
			{
				if(command.getName().equalsIgnoreCase(commandName) || command.getAliases().contains(commandName))
				{
					command.execute(sender, argsList.toArray(new String[argsList.size()]));
					return;
				}
			}
		}
		else
		{
			getPlayer().sendMessage(getUsageTemplate(true));
		}
		return;
	}
	
	
	@Override
	public String getUsageTemplate(boolean displayHelp)
	{
		StringBuilder ret = new StringBuilder();
		
		ret.append(name + " ");
		
		for(String s : requiredArgs)
		{
			ret.append(String.format("<%s> ", s));
		}
		
		for(String s : optionalArgs)
		{
			ret.append(String.format("[%s] ", s));
		}
		
		if(displayHelp)
		{
			ret.append(" - " + description);
		}
		return ret.toString();
	}
}
