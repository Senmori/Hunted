package net.senmori.hunted.commands.edit.parameters;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference.ErrorMessage;
import net.senmori.hunted.util.Reference.Permissions;
import net.senmori.hunted.util.Reference.SuccessMessage;
import org.bukkit.ChatColor;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;

public class EditActiveMap extends Subcommand {

	public EditActiveMap() {
		this.name = "map";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_EDIT_MAP;
		this.requiredArgs = Arrays.asList("name");
	}

	@Override
	protected void perform() {
		String fileName = args[0];
		File file = new File(Hunted.getInstance().getDataFolder() + File.separator + "configurations", fileName + ".yml");
		
		if (!file.exists()) {
			ActionBar.sendMessage(getPlayer(), ChatColor.RED + ErrorMessage.NO_FILE_FOUND_ERROR);
			return;
		}

		// change config setting, save file
		Hunted.getInstance().getConfigManager().activeMapConfiguration = fileName;
		Hunted.getInstance().getConfig().set("settings.active-map-configuration", fileName);
		// load new map configuration
		Hunted.getInstance().getConfigManager().getActiveMapConfiguration().load();
		ActionBar.sendMessage(getPlayer(), ChatColor.GREEN + MessageFormat.format(SuccessMessage.MAP_CONFIGURATION_CHANGED, fileName));
	}

}
