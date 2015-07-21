package net.senmori.hunted.commands.debug;

import java.util.Random;

import net.senmori.hunted.commands.Subcommand;
import net.senmori.hunted.kit.potion.HuntedPotion;
import net.senmori.hunted.kit.potion.Potion.PotionEffectEnum;
import net.senmori.hunted.util.LogHandler;
import net.senmori.hunted.util.Reference.Permissions;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

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
		Random rand = new Random();
		LogHandler.info("########");
		LogHandler.info("Creating potion..." );

		PotionEffectEnum potEnum = PotionEffectEnum.values()[rand.nextInt(PotionEffectEnum.values().length+1)];
		int duration = rand.nextInt(potEnum.getMaxLength())*20;
		int amplifier = rand.nextInt(3);
		boolean isSplash = potEnum.canOnlyBeSplash() ? true : rand.nextInt(10) % 10 == 0 ? true : false;
		
		LogHandler.info("Duration: " + duration + ", Amplifier: "+ amplifier);
		PotionType color = potEnum.getType() == null ? PotionType.STRENGTH : potEnum.getType();
		HuntedPotion potion = new HuntedPotion(color, new PotionEffect(potEnum.getEffectType(), duration, amplifier), potEnum.getCategory());
		
		potion.setDisplayName("Potion of Everlasting Annoying");

		getPlayer().getInventory().addItem(potion.toItemStack());
	}
}
