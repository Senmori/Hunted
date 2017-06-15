package net.senmori.hunted.reward.rewards;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.lib.game.GameState;
import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.tasks.PlayerGlowTask;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference.RewardMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class NotifyReward extends Reward {
    private String name;
    private Set<UUID> active = new HashSet<UUID>();

    public NotifyReward(String name) {
        this.name = name;
    }

    @Override
    public void generateLoot(Player player) {
        // if number is 5[20% chance], notify all players of rewardee's location
        if((int) ( Math.random() * ( 5 - 1 ) + 1 ) >= 5) {
            notifyAll(player);
            return;
        }
        notifyWithin(player, Hunted.getInstance().getConfigManager().nearbyRadius);
    }

    /*
     * Notify all players IN_GAME of where the player who activated this guardian stone currently is
     */
    private void notifyAll(Player player) {

        for(UUID uuid : Hunted.getInstance().getPlayerManager().getPlayers().keySet()) {
            if(! Hunted.getInstance().getPlayerManager().getState(uuid).equals(GameState.IN_GAME)) {
                continue; // ignore everyone not playing
            }
            if(uuid.equals(player.getUniqueId().toString())) {
                if(! active.contains(player.getUniqueId())) {
                    new PlayerGlowTask(Hunted.getInstance(), player.getUniqueId(), Hunted.getInstance().getPotionManager().getDuration());
                }
                continue; // ignore player who actived this stone
            }
            if(Bukkit.getPlayer(uuid) != null) {
                ActionBar.sendMessage(Bukkit.getPlayer(uuid), ChatColor.AQUA + MessageFormat.format(RewardMessage.NOTIFY_ALL, player.getName()));
            }
        }
    }

    /* Notify player who activated Guardian Stone of how many players are nearby */
    private void notifyWithin(Player player, int radius) {
        List<String> players = new ArrayList<>();

        for(Player p : Bukkit.getServer().getWorld(player.getWorld().getName()).getPlayers()) {
            if(p.getLocation().distanceSquared(player.getLocation()) <= radius) {
                players.add(p.getUniqueId().toString());
            }
        }
        // send a random player name from the list to the player receiving this message

        ActionBar.sendMessage(player, ChatColor.AQUA + MessageFormat.format(RewardMessage.NOTIFY_WITHIN, players.size(), Hunted.getInstance().getConfigManager().nearbyRadius));
        players.clear(); // clear list just in case it gets stuck in memory
    }

    @Override
    public String getName() {
        return name;
    }
}
