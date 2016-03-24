package net.senmori.hunted.managers.kit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.kit.armor.Armor;
import net.senmori.hunted.kit.armor.ArmorEnchantment;
import net.senmori.hunted.kit.armor.ArmorSlot;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorManager {

	private Hunted plugin;

	private List<ArmorEnchantment> possibleEnchantments;
	private List<Armor> possibleArmor;
	private List<ArmorSlot> possibleSlots;

	public ArmorManager(Hunted plugin) {
		this.plugin = plugin;
		possibleEnchantments = new ArrayList<>();
		possibleArmor = new ArrayList<>();
		possibleSlots = new ArrayList<>();
		load();
	}

	/** Main method for generating random armor */
	public void generateArmor(Player player) {
		List<ArmorSlot> possibleArmor = new ArrayList<>();
		possibleArmor.addAll(possibleSlots);
		int numArmorPieces = (int) (Math.random() * (3 - 1) + 1); // generate up
		                                                          // to 3
		                                                          // pieces of
		                                                          // armor,
		                                                          // minimum
		                                                          // of 1
		// TODO: temporary message
		player.sendMessage("Generating " + numArmorPieces + " pieces of armor");
		Collections.shuffle(possibleArmor);
		for (int i = 0; i < numArmorPieces; i++) {
			ArmorSlot current = possibleArmor.get(i);
			// add armor piece to inventory if player already has the same type
			// of armor equipped
			switch (current) {
				case HELMET:
					ItemStack helmet = generateHelmet();
					if (player.getInventory().getHelmet() != null) {
						player.getInventory().addItem(helmet);
					} else {
						player.getInventory().setHelmet(helmet);
					}
					player.sendMessage("Helmet: " + helmet.getType());
					continue;
				case CHESTPLATE:
					ItemStack chest = generateChestplate();
					if (player.getInventory().getChestplate() != null) {
						player.getInventory().addItem(chest);
					} else {
						player.getInventory().setChestplate(chest);
					}
					player.sendMessage("Chestplate: " + chest.getType());
					continue;
				case LEGGINGS:
					ItemStack legs = generateLeggings();
					if (player.getInventory().getLeggings() != null) {
						player.getInventory().addItem(legs);
					} else {
						player.getInventory().setLeggings(legs);
					}
					player.sendMessage("Leggings: " + legs.getType());
					continue;
				case BOOTS:
					ItemStack boots = generateBoots();
					if (player.getInventory().getBoots() != null) {
						player.getInventory().addItem(boots);
					} else {
						player.getInventory().setBoots(boots);
					}
					player.getInventory().setBoots(boots);
					player.sendMessage("Boots: " + boots.getType());
					continue;
				default:
					continue;

			}
		}
	}

	public ItemStack generateHelmet() {
		return generatePiece(ArmorSlot.HELMET);
	}

	public ItemStack generateChestplate() {
		return generatePiece(ArmorSlot.CHESTPLATE);
	}

	public ItemStack generateLeggings() {
		return generatePiece(ArmorSlot.LEGGINGS);
	}

	public ItemStack generateBoots() {
		return generatePiece(ArmorSlot.BOOTS);
	}

	private ItemStack generatePiece(ArmorSlot slot) {
		Random rand = new Random();
		Armor armor = possibleArmor.get(rand.nextInt(possibleArmor.size()));
		if (!armor.getSlot().equals(slot)) {
			while (!armor.getSlot().equals(slot)) {
				armor = possibleArmor.get(rand.nextInt(possibleArmor.size()));
				if (armor.getSlot().equals(slot)) {
					break;
				}
			}
		}
		Material type = armor.getType();
		ItemStack armorPiece = new ItemStack(type);
		armorPiece.setDurability(getRandomDurability(armorPiece));
		// 10% chance to have random enchantment on armor upon generation
		if ((int) (Math.random() * (10 - 1) + 1) >= 10) {
			armorPiece.addEnchantment(getRandomEnchant(armor.getSlot()),rand.nextInt(plugin.getConfigManager().maxEnchantLevel + 1));
		}
		return armorPiece;
	}

	private short getRandomDurability(ItemStack stack) {
		return (short) (Math.random() * (stack.getType().getMaxDurability() - 40) + 40);
	}

	private Enchantment getRandomEnchant(ArmorSlot slot) {
		ArmorEnchantment armorEnchant = null;
		Random rand = new Random();
		// return enchantment if it matches given slot, or it can be applied to
		// any piece of armor
		while (!armorEnchant.getSlot().equals(slot) || !armorEnchant.getSlot().equals(ArmorSlot.ALL)) {
			armorEnchant = possibleEnchantments.get(rand.nextInt(possibleEnchantments.size() + 1));
			if (armorEnchant.getSlot().equals(slot) || armorEnchant.getSlot().equals(ArmorSlot.ALL)) {
				break;
			}
		}
		return armorEnchant.getEnchant();
	}

	/*
	 * Generic getters
	 */
	public List<ArmorEnchantment> getPossibleEnchantments() {
		return possibleEnchantments;
	}

	public List<Armor> getPossibleArmor() {
		return possibleArmor;
	}

	public List<ArmorSlot> getPossibleSlots() {
		return possibleSlots;
	}

	private void load() {
		for (ArmorEnchantment e : ArmorEnchantment.values()) {
			possibleEnchantments.add(e);
		}

		for (Armor arm : Armor.values()) {
			possibleArmor.add(arm);
		}

		for (ArmorSlot as : ArmorSlot.values()) {
			possibleSlots.add(as);
		}
	}

}
