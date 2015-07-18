package net.senmori.hunted.weapon.ascented;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.senmori.hunted.abilities.Ability;
import net.senmori.hunted.weapon.AbstractWeapon;
import net.senmori.hunted.weapon.Weapon;
import net.senmori.hunted.weapon.WeaponType;

public abstract class AbstractAscented extends AbstractWeapon
{
	private String displayName;
	private WeaponType type;
	private HashMap<Enchantment, Integer> enchantments;
	private int usesLeft= 0;
	
	public AbstractAscented(String displayName, WeaponType type)
	{
		super(displayName,type);
		this.displayName = displayName;
		this.type = type;
		enchantments = new HashMap<>();
	}
	
	/*
	 * What to do when this item is equipped
	 */
	public abstract void onEquip();
	
	/*
	 * What to do when someone is attacked with this item
	 */
	public abstract void onAttack(Player attacker, Player target);
}
