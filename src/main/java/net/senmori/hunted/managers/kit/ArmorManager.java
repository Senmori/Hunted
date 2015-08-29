package net.senmori.hunted.managers.kit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.kit.armor.Armor;
import net.senmori.hunted.kit.armor.ArmorEnchantment;
import net.senmori.hunted.kit.armor.ArmorSlot;
import net.senmori.hunted.kit.weapon.WeaponType;

public class ArmorManager {
    
    private Hunted plugin;
    
    private List<Enchantment> possibleEnchantments;
    
	public ArmorManager(Hunted plugin) {
	    this.plugin = plugin;
	    possibleEnchantments = new ArrayList<>();
	}
	
	/** Main method for generating random armor */
	public void generateArmor(Player player) {
	    List<ArmorSlot> possibleArmor = new ArrayList<>();
	    possibleArmor.add(ArmorSlot.HELMET);
	    possibleArmor.add(ArmorSlot.CHESTPLATE);
	    possibleArmor.add(ArmorSlot.LEGGINGS);
	    possibleArmor.add(ArmorSlot.BOOTS);
	    int numArmorPieces = (int) (Math.random() * (3 - 1) + 1);
	    Collections.shuffle(possibleArmor);
	    for(int i = 0; i < numArmorPieces; i++) {
	        switch(possibleArmor.get(i)) {
    	        case HELMET:
    	            player.getInventory().setHelmet(generateHelmet());
    	            Collections.shuffle(possibleArmor);
    	            continue;
    	        case CHESTPLATE:
    	            player.getInventory().setChestplate(generateChestplate());
    	            Collections.shuffle(possibleArmor);
    	            continue;
    	        case LEGGINGS:
    	            player.getInventory().setChestplate(generateLeggings());
    	            Collections.shuffle(possibleArmor);
    	            continue;
    	        case BOOTS:
    	            player.getInventory().setBoots(generateBoots());
    	            Collections.shuffle(possibleArmor);
    	            continue;
	            default:
	                Collections.shuffle(possibleArmor);
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
	    Material type = null;
	    Armor armor = null;
	    while(!armor.getSlot().equals(slot)) {
	        armor = Armor.values()[(int)(Math.random() * (Armor.values().length) + 1)];
	        if(armor.getSlot().equals(slot)) break;
	    }
	    type = armor.getType();
	    ItemStack armorPiece = new ItemStack(type);
	    armorPiece.setDurability(getRandomDurability(armorPiece));
	    // 10% chance to have random enchantment on armor upon generation
	    if( ((int)(Math.random() * (10 - 1) + 1)) >= 10) {
	        armorPiece.addEnchantment(getRandomEnchant(armor.getSlot()), (int)(Math.random() * (plugin.getConfigManager().maxEnchantLevel + 1)));
	    }
	    return armorPiece;
	}
	
   private short getRandomDurability(ItemStack stack) {
        return (short)(Math.random()*(stack.getType().getMaxDurability() - 40) + 40);
    }
   
   private Enchantment getRandomEnchant(ArmorSlot slot) {
       ArmorEnchantment armorEnchant = null;
       while(!armorEnchant.getSlot().equals(slot)) {
           armorEnchant = ArmorEnchantment.values()[(int)(Math.random() * (ArmorEnchantment.values().length) + 1)];
           if(armorEnchant.getSlot().equals(slot)) break;
       }
       return armorEnchant.getEnchant();
   }

}
