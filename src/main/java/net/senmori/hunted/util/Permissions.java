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
}
