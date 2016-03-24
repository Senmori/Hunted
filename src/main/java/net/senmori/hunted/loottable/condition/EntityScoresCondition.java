package net.senmori.hunted.loottable.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.senmori.hunted.loottable.condition.enums.EntityPropertyEnum;
import net.senmori.hunted.loottable.score.LootScore;

import org.json.simple.JSONObject;

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
	
	
	@SuppressWarnings("unchecked")
	@Override
    public JSONObject toJSONObject() {
		JSONObject condition = new JSONObject();
		condition.put("condition", getType().getName());
		JSONObject rootScore = new JSONObject();
		for(LootScore sc : scores) {
			// if it's using an exact score, only input what's needed
			if(sc.useExactOnly()) {
				rootScore.put(sc.getScoreName(), sc.getExactScore());
			} else {
				JSONObject rand = new JSONObject();
				rand.put("min", sc.getScoreMin());
				rand.put("max", sc.getScoreMax());
				rootScore.put(sc.getScoreName(), rand);
			}
		}
		condition.put("scores", rootScore);
	    return condition;
    }

}
