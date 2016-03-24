package net.senmori.hunted.loottable.condition;

public enum LootConditionType {
	
	RANDOM_CHANCE("random_chance"),
	RANDOM_CHANCE_WITH_LOOTING("random_chance_with_looting"),
	KILLED_BY_PLAYER("killed_by_player"),
	ENTITY_PROPERTIES("entity_properties"),
	ENTITY_SCORES("entity_scores");
	
	private String name;
	LootConditionType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
