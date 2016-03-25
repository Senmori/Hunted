package net.senmori.hunted.loot.score;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import net.senmori.hunted.loot.condition.EntityScoresCondition;

/**
 * Used in {@link EntityScoresCondition} as a helper class to organize scores
 * <i>Note:</i>You cannot set a minimum/maximum score, as well as an exact score.
 * @author Senmori
 */
public class LootScore {
	
	private String scoreName;
	private Map<String,Integer> scores;
	
	/**
	 * Used in {@link EntityScoresCondition} as a helper class to organize scores
	 * <i>Note:</i>You cannot set a minimum/maximum score, as well as an exact score.
	 * @author Senmori
	 */
	public LootScore() {
		scores = new HashMap<>();
	}
	
	/**
	 * Used in {@link EntityScoresCondition} as a helper class to organize scores
	 * <i>Note:</i>You cannot set a minimum/maximum score, as well as an exact score.
	 * @author Senmori
	 */
	public LootScore(String scoreName, int exactScore) {
		this.scoreName = scoreName;
		scores = new HashMap<>();
		scores.put("exact", exactScore);
	}
	
	/**
	 * Used in {@link EntityScoresCondition} as a helper class to organize scores
	 * <i>Note:</i>You cannot set a minimum/maximum score, as well as an exact score.
	 * @author Senmori
	 */
	public LootScore(String scoreName, int min, int max) {
		this.scoreName = scoreName;
		scores = new HashMap<>();
		scores.put("min", min);
		scores.put("max", max);
	}
	
	/** Set the minimum and maximum scores to check for. </br>
	 * This clears any other entries in this class.
	 * @param min
	 * @param max
	 * @return
	 */
	public LootScore setScore(int min, int max) {
		scores.clear();
		scores.put("min", min);
		scores.put("max", max);
		return this;
	}
	
	/**
	 * Set the exact score to check for </br>
	 * This clears any other entries in this class
	 * @param exact
	 * @return
	 */
	public LootScore setScore(int exact) {
		scores.clear();
		scores.put("exact", exact);
		return this;
	}
	
	public boolean useExactOnly() {
		return scores.containsKey("exact");
	}
	
	public String getScoreName() {
		return scoreName;
	}
	
	public int getExactScore() {
		return scores.get("exact");
	}

	public int getScoreMin() {
		return scores.get("min");
	}
	
	public int getScoreMax() {
		return scores.get("max");
	}
	
	public LootScore fromJsonObject(JsonObject scores) {
		scoreName = scores.get("name").getAsString();
		if(scores.get("exact") != null) {
			setScore(scores.get("exact").getAsInt());
		} else if(scores.get("min") != null && scores.get("max") != null) {
			setScore(scores.get("min").getAsInt(),scores.get("max").getAsInt());
		}
		return this;
	}
}
