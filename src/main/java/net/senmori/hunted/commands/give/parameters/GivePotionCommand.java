package net.senmori.hunted.commands.give.parameters;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.util.Reference.Permissions;

public class GivePotionCommand extends Subcommand {

    public GivePotionCommand() {
        this.name = "potion";
        this.description = "give potion";
        this.permission = Permissions.ADMIN_EXEMPT;
        this.needsPlayer = true;
    }

    @Override
    protected void perform() {
        getPlayer().getInventory().addItem(Hunted.getInstance().getPotionManager().getPotion());
    }

}
