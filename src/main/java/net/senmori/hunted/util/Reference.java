package net.senmori.hunted.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Reference 
{	
		public class Permissions
		{
			// Command permissions
			public static final String COMMAND_ADD  = "hunted.commands.add";
			public static final String COMMAND_DELETE = "hunted.commands.delete";
			public static final String COMMAND_EDIT = "hunted.commands.edit";
			public static final String COMMAND_LIST = "hunted.commands.list";
			public static final String COMMAND_EXEMPT = "hunted.commands.exempt";
			public static final String COMMAND_STUCK = "hunted.commands.stuck";
			public static final String COMMAND_STUCK_OTHER = "hunted.commands.stuck.other";
			
			// Interaction permissions
			public static final String ADMIN_BREAK = "hunted.admin.break";
			public static final String ADMIN_PLACE = "hunted.admin.place";
			public static final String PLAYER_INTERACT_STONE_GUARDIAN = "hunted.interact.stone.guardian";
			public static final String PLAYER_INTERACT_STONE_TELEPORT = "hunted.interact.stone.teleport";
			
			// Misc. permissions
			public static final String ADMIN_EXEMPT = "hunted.admin.exempt";
		}
	
		// All error messages stored here
		public class ErrorMessage
		{
			public static final String IMPORT_ERROR = "Error importing %s. Is it mispelled?";
			public static final String STONE_CREATION_ERROR = "You must be looking at a block in order to create a guardian stone!";
		}
		
		// All debug messages stored here
		public static class DebugMessage
		{
			private static ChatColor debugColor = ChatColor.GREEN;
			
			// Debug messages for when armor/weapons/potions are created when a player spawns into Hunted
			public static final String ARMOR_GEN = "Generated %s pieces of armor for %s.";
			public static final String WEAPON_GEN = "Generated %s weapons for %s.";
			public static final String POTION_GEN = "Generated %s potions for %s.";
			
			// Debug message for when a player receives a reward
			public static final String REWARD = "%s received %s.";
			
			
			public static void sendMessage(Player player, String message)
			{
				if(!net.senmori.hunted.Hunted.debug) return;
				player.sendMessage(debugColor + message);
				LogHandler.debug(message);
			}
		}
		
		public class RewardMessage
		{
			public static final String COLD_STONE = "This stone is cold. Come back in %s minute(s).";
			
			// Standard reward message when a player recieves a reward from a guardian stone
			public static final String STONE_REWARD = "You received %s!";
			
			// Standard message for when a player receives an effect reward
			public static final String EFFECT_REWARD = "Ouch! You've got some %s going on.";
			public static final String EFFECT_UNLUCKY = "Wow, you're really unlucky. Have some %s";
			
			// Standard smite message
			public static final String SMITE_MESSAGE = "Thou hast been smitten! Puny mortal.";
			
			// Standard message to notify the rewardee of how many players are within 'x' blocks of said rewardee
			public static final String NOTIFY_WITHIN = "There are %s players within %s blocks of you.";
			
			// Standard message to notify all players of rewardee's location
			public static final String NOTIFY_ALL = "%s is at %s!";
			
			// Standard message for irritating reward
			public static final String IRRITATING_MESSAGE = "Good luck fixing this...";
		}
		
		
		public class LootPath
		{
			public static final String ARMOR = "armor";
			public static final String WEAPON = "weapon";
			public static final String ITEM = "item";
			public static final String EFFECT = "effect";
			public static final String POTION = "potion";
			public static final String ENCHANTMENTS = "enchantment";
			public static final String ASCENTED = "ascented";
		}
		
		public class MONSTER_EGG_DATA
		{
			public static final byte CREEPER= 50;
			public static final byte SKELETON = 51;
			public static final byte SPIDER = 52;
			public static final byte ZOMBIE = 54;
			public static final byte SLIME = 55;
			public static final byte GHAST = 56;
			public static final byte ZOMBIE_PIGMAN = 57;
			public static final byte ENDERMAN = 58;
			public static final byte CAVE_SPIDER = 59;
			public static final byte SILVERFISH = 60;
			public static final byte BLAZE = 61;
			public static final byte MAGMA_CUBE = 62;
			public static final byte BAT = 65;
			public static final byte WITCH = 66;
			public static final byte ENDERMITE = 67;
			public static final byte GUARDIAN = 68;
			public static final byte PIG = 90;
			public static final byte SHEEP = 91;
			public static final byte COW = 92;
			public static final byte CHICKEN = 93;
			public static final byte SQUID = 94;
			public static final byte WOLF = 95;
			public static final byte MOOSHROOM = 96;
			public static final byte OCELOT = 98;
			public static final byte HORSE = 100;
			public static final byte RABBIT = 101;
			public static final byte VILLAGER = 120;
		}
}
