package net.senmori.hunted.commands.edit;

import net.senmori.hunted.commands.Subcommand;

public class EditStone extends Subcommand{

    public EditStone() {
        this.name = "stone";
        this.permission = "commands.edit.stone";
        this.needsPlayer = true;
        this.description = "Edit guardian stone options";
    }

    @Override
    protected void perform() {
        
    }

}
