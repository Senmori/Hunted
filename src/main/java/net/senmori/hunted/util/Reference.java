package net.senmori.hunted.util;

import net.senmori.hunted.stones.GuardianStone;
import net.senmori.hunted.stones.Stone;

public class Reference {	
		public class Permissions {
			// Command permissions
			public static final String COMMAND_ADD  = "hunted.commands.add";
			// Add command parameter permissions
			public static final String COMMAND_ADD_RESPAWN = "hunted.commands.add.respawn";
			public static final String COMMAND_ADD_SPAWN = "hunted.commands.add.spawn";
			public static final String COMMAND_ADD_STONE = "hunted.commands.add.stone";
			
			public static final String COMMAND_DELETE = "hunted.commands.delete";
			
			public static final String COMMAND_EDIT = "hunted.commands.edit";
			// Edit command parameters
			public static final String COMMAND_EDIT_CONFIG = "hunted.commands.edit.config";
			public static final String COMMAND_EDIT_STONE = "hunted.commands.edit.stone";
			
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
			public static final String ADMIN_DEBUG = "hunted.admin.debug";
		}
	
		// All error messages stored here
		public class ErrorMessage {
			public static final String IMPORT_ERROR = "Error importing %s. Is it mispelled?";

			public static final String STONE_CREATION_ERROR = "You must be looking at a block in order to create a guardian stone!";
			
			public static final String NO_STORE_LOCATION_ERROR = "No store locations have been saved!";
			public static final String NO_HUNTED_LOCATION_ERROR = "No Hunted locations have been saved!";
			public static final String NO_LOBBY_LOCATION_ERROR = "No lobby locations have been saved!";
			
			public static final String NO_COMMAND_PERMISSION = "You do not have permission to run this command!";
		}
		
		/**
		 * %t : time </br>
		 * %item : item received </br>
		 * %effect : effect received </br>
		 * %radius : radius for notify_within reward </br>
		 * %player : player who activated the stone </br>
		 * %loc : location(or name of stone) where the notify_all reward will display </br>
		 * %n : amount of players within %radius blocks of %player </br>
		 */
		public class RewardMessage {
			/** Returns if the appropriate {@link Stone} is active or not */
			public static final String COLD_STONE = "This stone is cold. Come back in %t minute(s).";
			
			/** Standard reward message whena player receives a reward from a {@link GuardianStone} */
			public static final String STONE_REWARD = "You received %item!";
			
			/** Standard message for when a player receives an effect reward */
			public static final String EFFECT_REWARD = "Ouch! You've got some %effect going on.";
			public static final String EFFECT_UNLUCKY = "Wow, you're really unlucky. Have some %effect";
			
			/** Standard smite message */
			public static final String SMITE_MESSAGE = "Thou hast been smitten! Puny mortal.";
			

			/** Standard message to notify the rewardee of how many players are within an 'x' block radius */
			public static final String NOTIFY_WITHIN = "There are %n players within %radius blocks of you.";
			
			/** Standard message to notify all players(except rewardee) of the rewardee's location */
			public static final String NOTIFY_ALL = "%player is at %loc!";
			
			/** Standard message for when rewardee receives an irritating reward */
			public static final String IRRITATING_MESSAGE = "Good luck fixing this...";
		}
		
		/** Data that is stored for whenever a custom mob egg is used/spawned */
		public class MonsterEggData {
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
