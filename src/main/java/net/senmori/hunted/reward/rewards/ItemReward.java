package net.senmori.hunted.reward.rewards;

import java.text.MessageFormat;
import java.util.Random;
import net.senmori.hunted.kit.armor.Armor;
import net.senmori.hunted.kit.item.KitItem;
import net.senmori.hunted.kit.weapon.WeaponType;
import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemReward extends Reward {

    private String name;
    private int itemRewardSize = KitItem.values().length;
    private int armorRewardSize = Armor.values().length;
    private int weaponRewardSize = WeaponType.values().length;
    private Random random;

    private int total;

    public ItemReward(String name) {
        this.name = name;
        total = itemRewardSize + armorRewardSize + weaponRewardSize;
        random = new Random();
    }

    @Override
    public void generateLoot(Player player) {
        int index = random.nextInt(total + 1);
        if(index <= itemRewardSize) {
            index = random.nextInt(itemRewardSize + 1);
            KitItem curr = KitItem.values()[index >= itemRewardSize ? itemRewardSize : index];
            player.getInventory().addItem(new ItemStack(curr.getType(), curr.getMaxAmountReceivable()));
            ActionBar.sendMessage(player, ChatColor.GREEN + MessageFormat.format(Reference.RewardMessage.STONE_REWARD, curr.getType().toString().toLowerCase()));
            return;
        }
        index -= itemRewardSize;
        if(index <= armorRewardSize) {
            index = random.nextInt(armorRewardSize + 1);
            Armor armor = Armor.values()[index >= armorRewardSize ? armorRewardSize : index];
            player.getInventory().addItem(new ItemStack(armor.getType()));
            ActionBar.sendMessage(player, ChatColor.GREEN + MessageFormat.format(Reference.RewardMessage.STONE_REWARD, armor.getType().toString().toLowerCase()));
            return;
        }
        index -= armorRewardSize;
        index = random.nextInt(armorRewardSize + 1);
        WeaponType weapon = WeaponType.values()[index >= weaponRewardSize ? weaponRewardSize : index];
        player.getInventory().addItem(new ItemStack(weapon.getType()));
        ActionBar.sendMessage(player, ChatColor.GREEN + MessageFormat.format(Reference.RewardMessage.STONE_REWARD, weapon.getType().toString().toLowerCase()));
    }

    @Override
    public String getName() {
        return name;
    }
}
