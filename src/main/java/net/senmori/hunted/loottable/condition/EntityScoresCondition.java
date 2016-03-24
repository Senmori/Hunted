package net.senmori.hunted.loottable.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.senmori.hunted.loottable.condition.enums.EntityPropertyEnum;
import net.senmori.hunted.loottable.score.LootScore;

import org.json.simple.JSONObject;

import com.google.gson.JsonObject;

/**
 * Condition that checks if an entity has a certain scoreboard value</br>
 * <i>WARNING</i>: You can use both setScoreExact(...) and setScore(...), although improper values could cause problems.</br>
 */
public class EntityScoresCondition extends LootCondition {
	
	private String entity;
	private List<LootScore> scores;
	private boolean useMinMax;
	
	/**
	 * Condition that checks if an entity has a certain scoreboard value</br>
	 * <i>WARNING</i>: You can use both setScoreExact(...) and setScore(...), although improper values could cause problems.</br>
	 */
	public EntityScoresCondition() {
		super(LootConditionType.ENTITY_SCORES);
		scores = new ArrayList<>();
		useMinMax = false;
    }
	
	/**
	 * Set the values of {@link #scoreExact}. </br>
	 * If set, this condition will check for <b><i>exactly</i></b> this score
	 * @param entity - see {@link EntityPropertyEnum}
	 * @param score - see {@link LootScore}
	 */
	public EntityScoresCondition addScore(EntityPropertyEnum entity, LootScore score) {
		this.entity = entity.getName();
		scores.add(score);
		return this;
	}
	
	
	@Override
    public JsonObject toJsonObject() {
		JsonObject condition = new JsonObject();
		condition.addProperty("condition", getType().getName());
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

}
