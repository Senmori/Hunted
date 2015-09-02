package net.senmori.hunted.managers.kit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    
	public ArmorManager(Hunted plugin) {
	    this.plugin = plugin;
	    possibleEnchantments = new ArrayList<>();
	    load();
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
    	            ItemStack helmet = generateHelmet();
    	            player.getInventory().setHelmet(helmet);
    	            player.sendMessage("Helmet: " + helmet.getType());
    	            Collections.shuffle(possibleArmor);
    	            continue;
    	        case CHESTPLATE:
    	            ItemStack chest = generateChestplate();
    	            player.getInventory().setChestplate(chest);
    	            player.sendMessage("Chestplate: " + chest.getType());
    	            Collections.shuffle(possibleArmor);
    	            continue;
    	        case LEGGINGS:
    	            ItemStack legs = generateLeggings();
    	            player.getInventory().setChestplate(legs);
    	            player.sendMessage("Leggings: " + legs.getType());
    	            Collections.shuffle(possibleArmor);
    	            continue;
    	        case BOOTS:
    	            ItemStack boots = generateBoots();
    	            player.getInventory().setBoots(boots);
    	            player.sendMessage("Boots: " + boots.getType());
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
           armorEnchant = possibleEnchantments.get((int)Math.random() * (possibleEnchantments.size() - 1) + 1);
           if(armorEnchant.getSlot().equals(slot)) break;
       }
       return armorEnchant.getEnchant();
   }
   
   private void load() {
       for(ArmorEnchantment e : ArmorEnchantment.values()) {
           possibleEnchantments.add(e);
       }
   }

}
