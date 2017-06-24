package net.senmori.hunted.lib;

public class ConfigOption {
    public static final ConfigOption NULL_OPTION = new ConfigOption(null, null);

    public static final ConfigOption COOLDOWN = new ConfigOption("cooldown", "5");
    // Effect
    public static final ConfigOption MAX_EFFECT_LENGTH = new ConfigOption("effect.max-length", "60");
    public static final ConfigOption MIN_EFFECT_LENGTH = new ConfigOption("effect.min-length", "10");
    public static final ConfigOption MAX_ENCHANTMENT_LEVEL = new ConfigOption("reward.max-enchant-level", "2");
    public static final ConfigOption CAN_RECEIVE_POTIONS = new ConfigOption("reward.can-reveive-potions", "false");
    public static final ConfigOption MAX_POTIONS = new ConfigOption("reward.max-potions", "2");
    public static final ConfigOption POTION_TIER_2_CHANCE = new ConfigOption("reward.potion-tier2-chance", "1");
    public static final ConfigOption MAX_AMPLIFIER_LEVEL = new ConfigOption("reward.max-amplifier-level", "1");
    public static final ConfigOption SMITE_TELEPORT_CHANCE = new ConfigOption("reward.smite-teleport-chance", "5");
    public static final ConfigOption SEARCH_RADIUS = new ConfigOption("reward.radius", "50");
    public static final ConfigOption ASCENTED_ITEM_CHANCE = new ConfigOption("reward.ascented-item-chance", "10");
    public static final ConfigOption RECEIVE_EFFECT_TWICE = new ConfigOption("reward.receive-effect-twice", "10");
    public static final ConfigOption MAX_ARROWS_REWARD = new ConfigOption("reward.max-arrows", "32");
    // Settings
    public static final ConfigOption HUNTED_WORLD = new ConfigOption("settings.world", "world");
    public static final ConfigOption ACTIVE_MAP_CONFIGURATION = new ConfigOption("settings.active-map-configuration", "");
    public static final ConfigOption SIGN_DEPTH = new ConfigOption("settings.sign-depth", "1");
    public static final ConfigOption PORTAL_SIGN_DEPTH = new ConfigOption("settings.portal-sign-depth", "1");
    // Entrance/Exit
    public static final ConfigOption ALLOW_SIGN_ENTRANCE = new ConfigOption("settings.entry.sign-entrance", "true");
    public static final ConfigOption ALLOW_SIGN_EXIT = new ConfigOption("settings.entry.sign-exit", "true");
    public static final ConfigOption ALLOW_PORTAL_ENTRANCE = new ConfigOption("settings.entry.portal-entrance", "true");
    public static final ConfigOption ALLOW_PORTAL_EXIT = new ConfigOption("settings.entry.portal-exit", "true");

    private final String path;
    private String value;
    public ConfigOption(String path, String defaultValue) {
        this.path = path;
        this.value = defaultValue;
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String newValue) {
        this.value = newValue;
    }

    public int getAsInt() {
        return Integer.parseInt(getValue());
    }

    public double getAsDouble() {
        return Double.parseDouble(getValue());
    }

    public long getAsLong() {
        return Long.parseLong(getValue());
    }

    public boolean equals(Object other) {
        if(!(other instanceof ConfigOption)) {
            return false;
        }
        ConfigOption that = (ConfigOption)other;
        if(this == ConfigOption.NULL_OPTION || that == ConfigOption.NULL_OPTION) {
            return false;
        }
        return this.getPath().equals(that.getPath());
    }
}
