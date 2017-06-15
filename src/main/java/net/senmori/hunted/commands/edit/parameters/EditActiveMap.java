package net.senmori.hunted.commands.edit.parameters;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.LogHandler;
import net.senmori.hunted.util.Reference.ErrorMessage;
import net.senmori.hunted.util.Reference.Permissions;
import net.senmori.hunted.util.Reference.SuccessMessage;
import org.bukkit.ChatColor;

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

        if(! file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                LogHandler.warning(MessageFormat.format(ErrorMessage.IMPORT_ERROR, fileName));
                e.printStackTrace();
                return;
            }
            ActionBar.sendMessage(getPlayer(), ChatColor.GREEN + MessageFormat.format(SuccessMessage.MAP_CONFIG_CREATED, fileName));
        }

        // change config setting, save file
        Hunted.getInstance().getConfigManager().activeMapConfiguration = fileName;
        Hunted.getInstance().getConfig().set("settings.active-map-configuration", fileName);
        // load new map configuration
        Hunted.getInstance().getConfigManager().getActiveMapConfiguration().load();
        ActionBar.sendMessage(getPlayer(), ChatColor.GREEN + MessageFormat.format(SuccessMessage.MAP_CONFIGURATION_CHANGED, fileName));
    }

}
