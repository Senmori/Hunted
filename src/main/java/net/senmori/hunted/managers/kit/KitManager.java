package net.senmori.hunted.managers.kit;

import net.senmori.hunted.Hunted;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitManager {
	private Hunted plugin;

	public KitManager(Hunted plugin) {
		this.plugin = plugin;
	}

	/**
	 * Generate a random kit for the given player <br>
	 * Select up to 3 pieces of armor, not similar <br>
	 * Select 1 bow and/or sword <br>
	 * - if bow, select up to {@value Hunted#maxArrowsPerReward} arrows <br>
	 * If potions can be received as a reward(or upon spawn into Hunted) <br>
	 * - select up to {@value Hunted#maxPotsPerReward} potions <br>
	 *
	 * @param player
	 */
	public void generateKit(Player player) {
		generateArmor(player);
		generateWeapon(player);
		generatePotions(player);
	}

	/*
	 * Generate a random set of armor for a player
	 */
	public void generateArmor(Player player) {
		plugin.getArmorManager().generateArmor(player);
	}

	/*
	 * Generate a random weapon for a player
	 */
	public void generateWeapon(Player player) {
		ItemStack weapon = plugin.getWeaponManager().generateWeapon();
		if (weapon.getType().equals(Material.BOW)) {
			player.getInventory().addItem(new ItemStack(Material.ARROW, (int) (Math.random() * (plugin.getConfigManager().maxArrowsPerReward - 1) + 1)));
		}
		player.getInventory().addItem(weapon);
	}

	/*
	 * Generate potion(s) for a player
	 */
	public void generatePotions(Player player) {
		ItemStack potion = plugin.getPotionManager().getPotion();
		player.getInventory().addItem(potion);
	}
}
