package net.senmori.hunted.weapon;

import org.bukkit.entity.Player;

public class HuntedWeapon extends AbstractWeapon
{
	public HuntedWeapon(String displayName, WeaponType type)
	{
		super(displayName,type);
	}

	@Override
    public void onEquip() {}

	@Override
    public void onAttackEntity(Player aggressor, Player target) {}
	
}
