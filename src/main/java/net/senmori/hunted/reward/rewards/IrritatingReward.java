package net.senmori.hunted.reward.rewards;


import java.util.Random;
import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.util.Reference.RewardMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class IrritatingReward extends Reward {
    private String name;
    private Random random;

    public IrritatingReward(String name) {
        this.name = name;
        random = new Random();
    }

    /*
     * Rearrange player's inventory, quite annoying...
     */
    @Override
    public void generateLoot(Player player) {
        ItemStack[] inventory = player.getInventory().getContents();

        for(int i = 0; i <= inventory.length; i++) {
            int newSlot = random.nextInt(inventory.length);
            if(i == newSlot) {
                newSlot = random.nextInt(inventory.length);
            }

            PlayerInventory pInv = player.getInventory();
            // if oldSlot has an item, and new slot doesn't, move item to new slot
            if(pInv.getItem(i) != null && pInv.getItem(newSlot) == null) {
                pInv.setItem(newSlot, pInv.getItem(i));
                pInv.setItem(i, null);
            } else { // otherwise, switch slots
                ItemStack oldItem = pInv.getItem(i);
                ItemStack newItem = pInv.getItem(newSlot);

                pInv.setItem(i, newItem);
                pInv.setItem(newSlot, oldItem);
            }
        }
        player.sendMessage(ChatColor.GOLD + RewardMessage.IRRITATING_MESSAGE);
    }

    @Override
    public String getName() {
        return name;
    }
}
