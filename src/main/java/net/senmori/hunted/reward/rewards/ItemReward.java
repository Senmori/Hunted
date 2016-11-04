package net.senmori.hunted.reward.rewards;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import net.senmori.hunted.Hunted;
import net.senmori.hunted.kit.armor.Armor;
import net.senmori.hunted.kit.item.KitItem;
import net.senmori.hunted.kit.item.KitItem.Category;
import net.senmori.hunted.kit.weapon.WeaponType;
import net.senmori.hunted.reward.Reward;
import net.senmori.hunted.util.ActionBar;
import net.senmori.hunted.util.Reference;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.inventory.ItemStack;

public class ItemReward extends Reward {

	private String name;
	private List<KitItem> itemRewards;
	private List<Armor> armorRewards;
	private List<WeaponType> weaponRewards;

	public ItemReward(String name) {
		this.name = name;

		itemRewards = new ArrayList<>();
		for (KitItem kitItem : KitItem.values()) {
			if (kitItem.getCategory().equals(Category.UTILITY)) {
				continue; // ignore utility items for now
			}
			itemRewards.add(kitItem);
		}

		armorRewards = new ArrayList<>();
		for (Armor a : Armor.values()) {
			armorRewards.add(a);
		}

		weaponRewards = new ArrayList<>();
		for (WeaponType type : WeaponType.values()) {
			weaponRewards.add(type);
		}
	}

	@Override
	public void generateLoot(Player player) {
		int size = itemRewards.size() + armorRewards.size() + weaponRewards.size();
        int index = (int)(Math.random() * (size - 1) + 1);
        boolean running = true;
        if(size <= itemRewards.size()) {
            size = itemRewards.size();
            index = (int)(Math.random() * (size - 1) + 1);
            KitItem curr = itemRewards.get(index >= itemRewards.size() ? itemRewards.size() : index);
            player.getInventory().addItem(new ItemStack(curr.getType(), curr.getMaxAmountReceivable()));
            ActionBar.sendMessage(player, ChatColor.AQUA + MessageFormat.format(Reference.RewardMessage.STONE_REWARD, curr.getType().toString().toLowerCase()));
        }
        size -= itemRewards.size();
	}

	@Override
	public String getName() {
		return name;
	}
}
