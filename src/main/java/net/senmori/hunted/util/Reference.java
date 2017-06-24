package net.senmori.hunted.util;

public final class Reference {
    public class Permissions {
        // Command permissions

        // Add command
        public static final String COMMAND_ADD = "hunted.commands.add";
        // Add command parameter permissions
        public static final String COMMAND_ADD_LOBBY = "hunted.commands.add.respawn";
        public static final String COMMAND_ADD_SPAWN = "hunted.commands.add.spawn";
        public static final String COMMAND_ADD_STONE = "hunted.commands.add.stone";
        public static final String COMMAND_ADD_STORE = "hunted.commands.add.store";
        public static final String COMMAND_ADD_MAP = "hunted.commands.add.map.configuration";

        // Delete command
        public static final String COMMAND_DELETE = "hunted.commands.delete";
        // Delete command parameters
        public static final String COMMAND_DELETE_STONE = "hunted.commands.delete.stone";
        public static final String COMMAND_DELETE_LOCATION = "hunted.commands.delete.location";

        public static final String COMMAND_EDIT = "hunted.commands.edit";
        // Edit command parameters
        public static final String COMMAND_EDIT_CONFIG = "hunted.commands.edit.config";
        public static final String COMMAND_EDIT_STONE = "hunted.commands.edit.stone";
        public static final String COMMAND_EDIT_MAP = "hunted.commands.edit.map";

        public static final String COMMAND_LIST = "hunted.commands.list";
        // list parameter permissions
        public static final String COMMAND_LIST_GUARDIAN = "hunted.commands.list.guardian";
        public static final String COMMAND_LIST_TELEPORT = "hunted.commands.list.teleport";
        public static final String COMMAND_LIST_SPAWN = "hunted.commands.list.arena";
        public static final String COMMAND_LIST_LOBBY = "hunted.commands.list.lobby";
        public static final String COMMAND_LIST_STORE = "hunted.commands.list.store";

        // Stuck command
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

    public class SuccessMessage {
        public static final String MAP_CONFIGURATION_CHANGED = "Successfully changed map configuration to {0}";
        public static final String STONE_REMOVED = "Removed {0}!";
        public static final String LOCATION_REMOVED = "Removed {0}!";
        public static final String STONE_CREATED = "Succesfully added new Guardian Stone ( {0} )!";
        public static final String LOBBY_LOCATION_CREATED = "Successfully added a new lobby location ( {0} ) )";
        public static final String MAP_CONFIG_CREATED = "Successfully created the new map configuration ( {0} )";
        public static final String STORE_LOCATION_CREATED = "Successfully created the new store location ( {0} )";
        public static final String SPAWN_LOCATION_CREATED = "Successfully create a new spawn location ( {0} )";
    }

    // All error messages stored here
    public class ErrorMessage {
        public static final String IMPORT_ERROR = "Error importing {0}. Is it misspelled?";

        public static final String STONE_CREATION_ERROR = "Error creating a Stone. Are you looking at the block you want to make a Stone?";
        public static final String STONE_DELETE_ERROR = "Error deleting a Stone. Are you looking at it?";

        public static final String NO_STORE_LOCATION_ERROR = "No store locations have been saved!";
        public static final String NO_HUNTED_LOCATION_ERROR = "No Hunted locations have been saved!";
        public static final String NO_LOBBY_LOCATION_ERROR = "No lobby locations have been saved!";

        public static final String LOCATION_DELETE_ERROR = "Location is not valid. Please try again.";

        public static final String NO_COMMAND_PERMISSION = "You do not have permission to run this command!";

        public static final String NO_FILE_FOUND_ERROR = "{0} does not exist!";

        public static final String WRONG_WORLD_ERROR = "You are not in the correct world for this command!";

        public static final String COMMAND_COOLDOWN = "You can run this command in {0} {1} {2} seconds";
    }

    public class RewardMessage {
        /*
         * Returns if the appropriate {@link net.senmori.hunted.stone.Stone} is active or not
         */
        public static final String COLD_STONE = "This stone is cold. Come back in {0} minute(s).";

        /*
         * Standard reward message when a player receives a reward from a {@link net.senmori.hunted.stone.GuardianStone}
         */
        public static final String STONE_REWARD = "You received {0}!";

        /*
         * Standard message for when a player receives an effect reward
         */
        public static final String EFFECT_REWARD = "Ouch! You've got some {0} going on.";
        public static final String EFFECT_UNLUCKY = "Wow, you're really unlucky. Have some {0}";

        /*
         * Standard smite message
         */
        public static final String SMITE_MESSAGE = "Thou hast been smitten! Puny mortal.";

        /*
         * Standard message to notify the rewardee of how many players are within an 'x' block radius
         */
        public static final String NOTIFY_WITHIN = "There are {0} players within {1} blocks of you.";

        /*
         * Standard message to notify all players(except rewardee) of the rewardee's location
         */
        public static final String NOTIFY_ALL = "{0} is so excited they are glowing!";

        /*
         * Standard message for when rewardee receives an irritating reward
         */
        public static final String IRRITATING_MESSAGE = "Good luck fixing this...";
    }
}
