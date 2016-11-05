package net.senmori.hunted.stones;

import java.util.Random;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.SerializedLocation;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeleportStone extends Stone {

    private Random random;
    
	public TeleportStone(SerializedLocation loc) {
		super(loc);
        random = new Random();
	}

	/** Main method, call this to active this {@link TeleportStone} */
	@Override
	public void activate(Player player) {
        if(random.nextInt(Hunted.getInstance().getConfigManager().smiteTeleportChance) == Hunted.getInstance().getConfigManager().smiteTeleportChance) {
            ActionBar.sendMessage(player, ChatColor.GOLD + Reference.RewardMessage.SMITE_MESSAGE);
            player.getWorld().strikeLightning(player.getLocation());
        }
		player.teleport(Hunted.getInstance().getSpawnManager().getRandomHuntedLocation().getLocation());
	}

	@Override
	public StoneType getType() {
		return StoneType.TELEPORT;
	}

}
