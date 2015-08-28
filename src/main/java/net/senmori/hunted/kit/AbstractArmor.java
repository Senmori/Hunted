package net.senmori.hunted.kit;

import java.util.HashMap;
import java.util.Map;

import net.senmori.hunted.kit.armor.Armor;
import net.senmori.hunted.kit.armor.Armor.ArmorSlot;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AbstractArmor {
	private Material material;
	private ArmorSlot slot;
	private int durability;
	private String displayName;
	private Map<Enchantment, Integer> enchantments;
	
	
	public AbstractArmor(Material material, ArmorSlot slot) {
		this.material = material;
		this.slot = slot;
		enchantments = new HashMap<>();
	}
	
	/** Set the {@link Material} of this armor */
	public void setMaterial(Material material) {
	    this.material = material;
    }
	
	/** Set the durability of this armor */
	public void setDurability(int durability) {
	    this.durability = durability;
    }
	
	/** Set the display name of this armor */
	public void setDisplayName(String name) {
	    this.displayName = name;
    }
	
	/** Add an enchantment to this armor with a level of 1 */
	public void addEnchantment(Enchantment enchant) {
		enchantments.put(enchant, 1);
	}
	
	/** Add an enchantment to this armor with a supplied level */
	public void addEnchantment(Enchantment enchant, int level) {
		enchantments.put(enchant, level);
	}
	
	/** Add multiple enchantments to this armor */
	public void addEnchantment(Map<Enchantment,Integer> enchants) {
		enchantments.putAll(enchants);
	}
	
	/** Get the {@link Material} of this armor */
	public Material getType() {
		return this.material;
	}
	
	/** Get the {@link ArmorSlot} of this armor */
	public ArmorSlot getSlot() {
	    return this.slot;
	}
	
	/** Get the enchantments for this armor piece */
	public Map<Enchantment, Integer> getEnchantments() {
		return this.enchantments;
	}
	
	/** Get the display name of this armor */
	public String getDisplayName() {
		return this.displayName;
	}
	
	/** Get the durability of this armor */
	public int getDurability() {
		return this.durability;
	}
	
	/** Convert this armor to a useable {@link ItemStack} */
	public ItemStack toItemStack() {
		ItemStack armorPiece = new ItemStack(material);
		
		if(enchantments.keySet().size() > 0) {
			armorPiece.addEnchantments(enchantments);
		}
		ItemMeta armorMeta = armorPiece.getItemMeta();
		armorMeta.setDisplayName(displayName);
		armorPiece.setItemMeta(armorMeta);
		if(durability > 0) {
			armorPiece.setDurability((short)(material.getMaxDurability()-durability));
		}
		return armorPiece;
	}

}
