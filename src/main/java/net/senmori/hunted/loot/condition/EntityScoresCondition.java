package net.senmori.hunted.loot.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.senmori.hunted.loot.condition.properties.EntitySelector;
import net.senmori.hunted.loot.score.LootScore;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Condition that checks if an entity has a certain scoreboard value</br>
 * <i>WARNING</i>: You can use both setScoreExact(...) and setScore(...), although improper values could cause problems.</br>
 */
public class EntityScoresCondition extends LootCondition {
	
	private String entity;
	private List<LootScore> scores;
	
	public EntityScoresCondition() {
		scores = new ArrayList<>();
	}
	
	/**
	 * Condition that checks if an entity has a certain scoreboard value</br>
	 */
	public EntityScoresCondition(EntitySelector entity) {
		scores = new ArrayList<>();
		this.entity = entity.getName();
    }
	
	/* ##################
	 * ItemStack methods
	 * ##################
	 */
	@Override
    public ItemStack applyTo(ItemStack applyTo) {
	    return applyTo;
    }


	
	
	/* #################
	 * Property Methods
	 * #################
	 */
	/**
	 * Add a {@link LootScore} to this condition
	 * All scores submitted must pass for this condition to pass.
	 * @param entity - see {@link EntitySelector}
	 * @param score - see {@link LootScore}
	 */
	public EntityScoresCondition addScore(LootScore score) {
		scores.add(score);
		return this;
	}
	
	
	/* ############################
	 * Load/Save Methods
	 * ############################
	 */
	@Override
    public JsonObject toJsonObject() {
		JsonObject condition = new JsonObject();
		condition.addProperty("condition", "entity_scores");
		condition.addProperty("entity", entity);
		JsonObject rootScore = new JsonObject();
		for(LootScore sc : scores) {
			// if it's using an exact score, only input what's needed
			if(sc.useExactOnly()) {
				rootScore.addProperty(sc.getScoreName(), sc.getExactScore());
			} else {
				JsonObject rand = new JsonObject();
				rand.addProperty("min", sc.getScoreMin());
				rand.addProperty("max", sc.getScoreMax());
				rootScore.add(sc.getScoreName(), rand);
			}
		}
		condition.add("scores", rootScore);
	    return condition;
    }

	@Override
    public LootCondition fromJsonObject(JsonObject condition) {
		entity = condition.get("entity").getAsString();
		for(Map.Entry<String, JsonElement> entry : condition.get("scores").getAsJsonObject().entrySet()) {
			if(entry.getValue().getAsJsonPrimitive().isNumber()) { // exact score
				addScore(new LootScore(entry.getKey(), entry.getValue().getAsInt()));
			} else if(entry.getValue().isJsonObject()) { // this is minimum & maximum score
				JsonObject value = entry.getValue().getAsJsonObject();
				addScore(new LootScore(entry.getKey(), value.get("min").getAsInt(), value.get("max").getAsInt()));
			}
		}
		return this;
    }

	@Override
    public LootConditionType getType() {
	    return LootConditionType.ENTITY_SCORES;
    }
}
