package net.senmori.hunted.managers.kit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.kit.armor.Armor;
import net.senmori.hunted.kit.armor.ArmorEnchantment;
import net.senmori.hunted.kit.armor.ArmorSlot;
import net.senmori.hunted.util.LogHandler;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorManager {
    
    private Hunted plugin;
    
    private List<ArmorEnchantment> possibleEnchantments;
    private List<Armor> possibleArmor;
    
	public ArmorManager(Hunted plugin) {
	    this.plugin = plugin;
	    possibleEnchantments = new ArrayList<>();
	    possibleArmor = new ArrayList<>();
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
	    player.sendMessage("Generating " + numArmorPieces + " pieces of armor");
	    Collections.shuffle(possibleArmor);
	    for(int i = 0; i < numArmorPieces; i++) {
	        switch(possibleArmor.get(i)) {
    	        case HELMET:
    	            ItemStack helmet = generateHelmet();
    	            if(player.getInventory().getHelmet() != null || player.getInventory().getHelmet().getType().equals(Material.AIR)) {
    	                player.getInventory().addItem(helmet);
    	            } else {
    	              player.getInventory().setHelmet(helmet);  
    	            }
    	            player.sendMessage("Helmet: " + helmet.getType());
    	            continue;
    	        case CHESTPLATE:
    	            ItemStack chest = generateChestplate();
    	            if(player.getInventory().getChestplate() != null || player.getInventory().getHelmet().getType().equals(Material.AIR)) {
    	                player.getInventory().addItem(chest);
    	            } else {
    	              player.getInventory().setChestplate(chest);  
    	            }
    	            player.sendMessage("Chestplate: " + chest.getType());
    	            continue;
    	        case LEGGINGS:
    	            ItemStack legs = generateLeggings();
    	            if(player.getInventory().getLeggings() != null || player.getInventory().getHelmet().getType().equals(Material.AIR)) {
    	                player.getInventory().addItem(legs);
    	            } else {
    	                player.getInventory().setLeggings(legs);
    	            }
    	            player.sendMessage("Leggings: " + legs.getType());
    	            continue;
    	        case BOOTS:
    	            ItemStack boots = generateBoots();
    	            if(player.getInventory().getBoots() != null || player.getInventory().getBoots().getType().equals(Material.AIR)) {
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
	
	public ItemStack generatePiece(ArmorSlot slot) {
	    Armor armor = null;
	    do{
	        armor = possibleArmor.get((int)(Math.random() * (possibleArmor.size() - 1) + 1));
	    } while(!armor.getSlot().equals(slot));
	    Material type = armor.getType();
	    LogHandler.info("Armor Slot: " + armor.getSlot().toString());
	    LogHandler.info("Armor Type: " + type.toString());
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
       
       for(Armor arm : Armor.values()) {
           possibleArmor.add(arm);
       }
   }

}
