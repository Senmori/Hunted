package net.senmori.hunted.managers;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.senmori.hunted.commands.Subcommand;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager implements CommandExecutor {
	private JavaPlugin plugin;
	private String commandPrefix;
	private List<Subcommand> commands = new ArrayList<Subcommand>();

	private static List<CommandManager> managers = new ArrayList<CommandManager>();

	public CommandManager(JavaPlugin plugin) {
		this.plugin = plugin;
		addCommandManager(this);

	}

	public void registerCommand(Subcommand command) {
		commands.add(command);
	}

	public void setCommandPrefix(String commandPrefix) {
		this.commandPrefix = commandPrefix;
		plugin.getCommand(commandPrefix).setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> argsList = new ArrayList<String>();

		if (args.length > 0) {
			String commandName = args[0].toLowerCase();

			for (int i = 1; i < args.length; i++) {
				argsList.add(args[i]);
			}

			for (Subcommand command : commands) {
				if (command.getName().equals(commandName) || command.getAliases().contains(commandName)) {
					command.execute(sender, argsList.toArray(new String[argsList.size()]));
					return true;
				}
			}
		} else {
			// display list of subcommand names, along with their descriptions
			StringBuilder sb = new StringBuilder();
			sb.append(ChatColor.GREEN + "---------------- " + ChatColor.YELLOW + "Hunted comands" + ChatColor.GREEN
			        + " ----------------");
			for (Subcommand sub : commands) {
				sb.append("\n");
				sb.append(ChatColor.GREEN + sub.getName() + ChatColor.YELLOW + " - " + ChatColor.YELLOW
				        + sub.getDescription());
			}
			sb.append(ChatColor.GREEN + "----------------------------------------------");
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.sendMessage(sb.toString());
				return true;
			}
			Bukkit.dispatchCommand(sender, "help " + commandPrefix);
		}

		return true;
	}

	public String getCommandPrefix() {
		return commandPrefix;
	}

	public List<Subcommand> getCommands() {
		return commands;
	}

	public static CommandManager getCommandManager(String prefix) {
		for (CommandManager m : managers) {
			if (m.commandPrefix.equals(prefix)) return m;
		}

		return null;
	}

	public static void addCommandManager(CommandManager m) {
		managers.add(m);
	}

	public static void removeCommandManager(CommandManager m) {
		managers.remove(m);
	}
}
