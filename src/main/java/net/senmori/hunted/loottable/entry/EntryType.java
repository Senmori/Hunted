package net.senmori.hunted.loottable.entry;

public enum EntryType {
	
	ITEM("item"),
	LOOT_TABLE("loot_table"),
	EMPTY("empty");
	
	private String name;
	EntryType(String name) {
		this.name = name;
	}
	
	public String getType() {
		return name;
	}
	
}
