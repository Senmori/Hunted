package net.senmori.hunted.commands.debug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.enchantments.Enchantment;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.managers.game.NameGenerator;
import net.senmori.hunted.util.Reference.Permissions;
import net.senmori.hunted.weapon.HuntedWeapon;
import net.senmori.hunted.weapon.WeaponEnchantEnum;
import net.senmori.hunted.weapon.WeaponType;

public class DebugCommand extends Subcommand
{

	public DebugCommand()
	{
		this.name = "debug";
		this.needsPlayer = true;
		this.permission = Permissions.COMMAND_ADD;
	}

	@Override
	protected void perform()
	{
		NameGenerator gen = new NameGenerator();
		WeaponType type = WeaponType.values()[(int)(Math.random()*(WeaponType.values().length + 1) + 1)];
		gen.generateName(type);
		if((int) (Math.random()*((10 - 1) + 1)) == 10)
		{
			gen.generateTitle(type);
		}
		HuntedWeapon weapon = new HuntedWeapon(gen.toString(),type);
		weapon.setUses((int)(Math.random()*(weapon.getType().getType().getMaxDurability()-40)+40));
		weapon.addEnchantment(getRandEnchant(weapon.getType()));
		getPlayer().getInventory().addItem(weapon.toItemStack());
	}
	
	private Enchantment getRandEnchant(WeaponType type)
	{
		List<Enchantment> possible = new ArrayList<>();
		
		for(WeaponEnchantEnum e : WeaponEnchantEnum.values())
		{
			if(e.getAssociatedTypes().contains(type))
			{
				possible.add(e.getEnchant());
			}
		}
		Collections.shuffle(possible);
		return possible.get(0);
	}
}
