package net.senmori.hunted.managers.kit;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.kit.HuntedWeapon;
import net.senmori.hunted.kit.weapon.WeaponType;
import net.senmori.hunted.kit.weapon.bow.Bow.BowName;
import net.senmori.hunted.kit.weapon.bow.Bow.BowTitle;
import net.senmori.hunted.kit.weapon.enchantment.WeaponEnchant;
import net.senmori.hunted.kit.weapon.sword.SwordName;
import net.senmori.hunted.kit.weapon.sword.SwordTitle;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WeaponManager {
	private Hunted plugin;
    private Random rand;
	private List<WeaponType> weaponTypes;

	private List<SwordName> swordNames;
	private List<SwordTitle> swordTitles;

	private List<BowName> bowNames;
	private List<BowTitle> bowTitles;

	private List<WeaponEnchant> weaponEnchants;

	public WeaponManager(Hunted plugin) {
		this.plugin = plugin;
		weaponTypes = Arrays.asList(WeaponType.values());

		swordNames = Arrays.asList(SwordName.values());
		swordTitles = Arrays.asList(SwordTitle.values());

		bowNames = Arrays.asList(BowName.values());
		bowTitles = Arrays.asList(BowTitle.values());

		weaponEnchants = Arrays.asList(WeaponEnchant.values());
        rand = new Random();
	}

	/**
	 * Generate a random weapon that can be ascented
	 *
	 * @return weapon
	 */
	public ItemStack generateWeapon() {
		// n% chance of it being as ascented weapon
		boolean isAscented = rand.nextInt(plugin.getConfigManager().ascentedItemChance + 1) == plugin.getConfigManager().ascentedItemChance;
		WeaponType type = getRandomWeaponType(isAscented);
		// 20% chance of an item having a title
		String displayName = getRandomName(type, rand.nextInt(11) % 5 == 0, isAscented);
		HuntedWeapon weapon = new HuntedWeapon(displayName, type);
		// random durability
		weapon.setUses(getRandomDurability(type));
		// random number of enchantments
		int maxLevel = isAscented ? plugin.getConfigManager().maxEnchantLevel * 2 : plugin.getConfigManager().maxEnchantLevel;
		int minLevel = isAscented ? plugin.getConfigManager().maxEnchantLevel : 1;
		weapon.addEnchantment(getRandomEnchantments(type, rand.nextInt(4), maxLevel, minLevel));
		if (weapon.getEnchantments().keySet().size() < 1) {
			weapon.setDisplayName(null);
		}
		return weapon.toItemStack();
	}

	private WeaponType getRandomWeaponType(boolean ascented) {
		WeaponType type = weaponTypes.get(rand.nextInt(WeaponType.values().length + 1));
		if (ascented && !type.canBeAscented()) {
			while (!type.canBeAscented()) {
				type = weaponTypes.get(rand.nextInt(WeaponType.values().length + 1));
				if (type.canBeAscented()) {
					break;
				}
			}
		}
		return type;
	}

	private String getRandomName(WeaponType type, boolean useTitle, boolean isAscented) {
		String prefix = ChatColor.WHITE + "";
		String name = "";
		if (isAscented) {
			prefix = ChatColor.GOLD + "";
		}
		if (type.isBow()) {
			name += prefix + bowNames.get(rand.nextInt(bowNames.size())).getName();
			if (useTitle) {
				name += ", ";
				name += bowTitles.get((rand.nextInt(bowTitles.size()))).getName();
			}
			return name;
		}
		name += prefix + swordNames.get(rand.nextInt(swordNames.size())).getName();
		if (useTitle) {
			name += ", ";
			name += swordTitles.get(rand.nextInt(swordTitles.size())).getName();
		}
		return name;
	}

	/*
	 * Used by non-ascented weapons, has a max enchantment level of 2
	 */
	private Map<Enchantment, Integer> getRandomEnchantments(WeaponType type, int maxEnchants, int maxLevel, int minLevel) {
		// return empty map in case you cannot add null enchantments to
		// itemstacks
        Map<Enchantment, Integer> possible = new HashMap<>();
		if (maxEnchants < 1) {
			return possible;
		}
		
		for (WeaponEnchant we : weaponEnchants) {
			if (we.getAssociatedTypes().contains(type) && !possible.containsKey(we.getEnchant())) {
				int level = rand.nextInt(maxLevel - minLevel) + minLevel;
				possible.put(we.getEnchant(), level);
			}
		}
		// shuffle K,V in `possible`
		List<Enchantment> ench = new ArrayList<>();
		ench.addAll(possible.keySet());
		Collections.shuffle(ench);
		Map<Enchantment, Integer> enchants = new HashMap<>();
		for (int i = 0; i < maxEnchants; i++) {
			Enchantment current = ench.get(i);
			// handle if the map already has that enchantment in it
			if (enchants.containsKey(current)) {
				// max level is 2, so handle that
				if (enchants.get(current) >= maxLevel) {
					continue;
				}
				enchants.put(current, enchants.get(current) + 1);
				continue;
			}
			enchants.put(current, possible.get(current));
		}
		return enchants;
	}

	private int getRandomDurability(WeaponType type) {
		return rand.nextInt(type.getType().getMaxDurability() - 40) + 40;
	}
}
