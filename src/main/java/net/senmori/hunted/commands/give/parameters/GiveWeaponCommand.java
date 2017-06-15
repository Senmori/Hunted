package net.senmori.hunted.commands.give.parameters;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;

public class GiveWeaponCommand extends Subcommand {

    public GiveWeaponCommand() {
        this.name = "weapon";
        this.description = "give weapon";
        this.permission = Permissions.ADMIN_EXEMPT;
        this.needsPlayer = true;
    }

    @Override
    protected void perform() {
        getPlayer().getInventory().addItem(Hunted.getInstance().getWeaponManager().generateWeapon());
    }

}
