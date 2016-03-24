package net.senmori.hunted.loottable.condition.enums;

public enum EntityPropertyEnum {
	
	/** The {@link Entity} that was looted */
	THIS("this"),
	/** The {@link Entity} that damage/killed this {@link Entity} */
	KILLER("killer"),
	/** The {@link Player} that killed this {@link Entity} */
	KILLER_PLAYER("killer_player");
	
	private String name;
	EntityPropertyEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
