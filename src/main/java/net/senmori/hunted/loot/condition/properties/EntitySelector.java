package net.senmori.hunted.loot.condition.properties;

public enum EntitySelector {
	
	/** The {@link Entity} that was looted */
	THIS("this"),
	/** The {@link Entity} that damage/killed this {@link Entity} */
	KILLER("killer"),
	/** The {@link Player} that killed this {@link Entity} */
	KILLER_PLAYER("killer_player");
	
	private String name;
	EntitySelector(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
