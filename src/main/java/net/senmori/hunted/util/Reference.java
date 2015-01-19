package net.senmori.hunted.util;

import net.senmori.hunted.Hunted;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Reference 
{	
		public class Permissions
		{
			// Command permissions
			public static final String ADMIN_CREATE = "hunted.admin.create";
			public static final String ADMIN_DELETE = "hunted.admin.delete";
			public static final String ADMIN_EDIT = "hunted.admin.edit";
			
			// Misc. permissions
			public static final String ADMIN_DEBUG = "hunted.debug.receive";
		}
	
		// All error messages stored here
		public class Message
		{
			public static final String COLD_STONE = "This stone is cold. Come back in {0} {1}.";
			public static final String IMPORT_ERROR = "Error importing {0}. Is it mispelled?";
		}
		
		// All debug messages stored here
		public static class DebugMessage
		{
			private static ChatColor debugColor = ChatColor.GREEN;
			
			// Debug messages for when armor/weapons/potions are created when a player spawns into Hunted
			public static final String ARMOR_GEN = "Generated {0} pieces of armor for {1}.";
			public static final String WEAPON_GEN = "Generated {0} weapons for {1}.";
			public static final String POTION_GEN = "Generated {0} potions for {1}.";
			
			// Debug message for when a player receives a reward
			public static final String REWARD = "{0} received {1}.";
			
			
			public static void sendMessage(Player player, String message)
			{
				if(!Hunted.debug || !player.hasPermission(Permissions.ADMIN_DEBUG)) return;
				player.sendMessage(debugColor + message);
				LogHandler.debug(message);
			}
		}
		
		public class RewardMessage
		{
			// Standard reward message when a player recieves a reward from a guardian stone
			public static final String STONE_REWARD = "You received {0}!";
			
			// Standard message for when a player receives an effect reward
			public static final String EFFECT_REWARD = "Ouch! You've got some {0} going on.";
			public static final String EFFECT_UNLUCKY = "Wow, you're really unlucky. Have some {0}";
			
			// Standard smite message
			public static final String SMITE_MESSAGE = "Thou hast been smitten! Puny mortal.";
			
			// Standard message to notify the rewardee of how many players are within 'x' blocks of said rewardee
			public static final String NOTIFY_WITHIN = "There are {0} players within {1} blocks of you.";
			
			// Standard message to notify all players of rewardee's location
			public static final String NOTIFY_ALL = "{0} is at {1}!";
			
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
}
