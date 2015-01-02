package net.senmori.hunted.util;

public class Permissions 
{
	// Plugin permissions
	public static final String ADMIN_CREATE = "hunted.admin.create";
	public static final String ADMIN_DELETE = "hunted.admin.delete";
	public static final String ADMIN_EDIT = "hunted.admin.edit";
	
	
	// All error messages stored here
	public class Message
	{
		public static final String COLD_STONE = "This stone is cold. Come back in %m %s.";
	}
	
	
	public class RewardMessage
	{
		/*
		 * Standard symbols to use
		 * - %x - rewardee
		 * - %i - item reward
		 * - %n - replace w/ numbers
		 * - %g - guardian stone closest to %r's location
		 */
		// Standard reward message when a player recieves a reward from a guardian stone
		public static final String STONE_REWARD = "You received %x!";
		
		// Standard smite message
		public static final String SMITE_MESSAGE = "Thou hast been smitten! Puny mortal.";
		
		// Standard message to notify the rewardee of how many players are within 'x' blocks of said rewardee
		public static final String NOTIFY_WITHIN = "There are %x players within %n blocks of you.";
		
		// Standard message to notify all players of rewardee's location
		public static final String NOTIFY_ALL = "%r is at %g!";
		
		// Standard message of how much XP the player earned per kill or stone use
		public static final String XP_EARNED = "+%n XP";
		
		// Standard message of their current killstreak, if applicable(new high killstreak, record killstreak, etc..)
		public static final String KILLSTREAK_MSG = "Current killstreak: %n";
		
		// Standard message for irritating reward
		public static final String IRRITATING_MESSAGE = "Good luck fixing this...";
	}
}
