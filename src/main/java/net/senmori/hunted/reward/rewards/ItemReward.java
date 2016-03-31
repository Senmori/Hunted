package net.senmori.hunted.reward.rewards;

import net.senmori.hunted.kit.armor.Armor;
import net.senmori.hunted.kit.item.KitItem;
import net.senmori.hunted.kit.item.KitItem.Category;
import net.senmori.hunted.kit.weapon.WeaponType;
import net.senmori.hunted.reward.Reward;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ItemReward extends Reward {

	private String name;
	private Set<KitItem> itemRewards;
	private Set<Armor> armorRewards;
	private Set<WeaponType> weaponRewards;

	public ItemReward(String name) {
		this.name = name;

		itemRewards = new HashSet<>();
		for (KitItem kitItem : KitItem.values()) {
			if (kitItem.getCategory().equals(Category.UTILITY)) {
				continue; // ignore utility items for now
			}
			itemRewards.add(kitItem);
		}

		armorRewards = new HashSet<>();
		for (Armor a : Armor.values()) {
			armorRewards.add(a);
		}

		weaponRewards = new HashSet<>();
		for (WeaponType type : WeaponType.values()) {
			weaponRewards.add(type);
		}
	}

	@Override
	public void generateLoot(Player player) {
		// pick from ItemReward, a random weapon or a random piece of armor
	}

	@Override
	public String getName() {
		return name;
	}
}
