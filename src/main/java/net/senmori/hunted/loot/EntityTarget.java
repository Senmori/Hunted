package net.senmori.hunted.loot;

/**
 * Created by Senmori on 4/12/2016.
 */
public enum EntityTarget {
    THIS("this"),
    KILLER("killer"),
    KILLER_PLAYER("killer_player");

    private final String targetType;

    EntityTarget(String type) {
        this.targetType = type;
    }

    public String getTargetType() { return this.targetType; }

    public static EntityTarget fromString(String name) {
        for (EntityTarget target : EntityTarget.values()) {
            if (target.getTargetType().equals(name.toLowerCase())) {
                return target;
            }
        }
        return null;
    }
}
