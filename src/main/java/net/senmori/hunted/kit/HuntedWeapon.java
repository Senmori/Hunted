package net.senmori.hunted.kit;

import java.util.HashMap;
import java.util.Map;
import net.senmori.hunted.kit.weapon.WeaponType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HuntedWeapon {
    protected String displayName;
    protected Map<Enchantment, Integer> enchantments;
    protected WeaponType type;
    protected int usesLeft;

    public HuntedWeapon(String displayName, WeaponType type) {
        this.displayName = displayName;
        this.type = type;
        enchantments = new HashMap<>();
    }

    public void addEnchantment(Enchantment enchant) {
        addEnchantment(enchant, 1);
    }

    public void addEnchantment(Enchantment enchant, int level) {
        enchantments.put(enchant, level);
    }

    public void addEnchantment(Map<Enchantment, Integer> enchants) {
        enchantments.putAll(enchants);
    }

    public ItemStack toItemStack() {
        ItemStack weapon = new ItemStack(type.getType());
        // add damage attribute so it can compete with swords
        if(enchantments.keySet().size() > 0) {
            weapon.addEnchantments(enchantments);
        }
        ItemMeta meta = weapon.getItemMeta();
        meta.setDisplayName(displayName);
        weapon.setItemMeta(meta);
        if(usesLeft > 0) {
            weapon.setDurability((short) ( type.getType().getMaxDurability() - usesLeft ));
        }
        return weapon;
    }

    public WeaponType getType() {
        return type;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public int getUses() {
        return usesLeft;
    }

    public void setUses(int uses) {
        this.usesLeft = uses;
    }
}
