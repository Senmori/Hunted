package net.senmori.hunted.loottable.score;

import net.senmori.hunted.loottable.condition.EntityScoresCondition;

/**
 * Used in {@link EntityScoresCondition} as a helper class to organize scores
 * @author Senmori
 */
public class LootScore {
	
	private String scoreName;
	private int exactScore, min, max;
	private boolean useExactOnly = true;
	
	public LootScore(String scoreName, int exactScore) {
		this.scoreName = scoreName;
		this.exactScore = exactScore;
	}
	
	public LootScore(String scoreName, int min, int max) {
		this.scoreName = scoreName;
		this.min = min;
		this.max = max;
		useExactOnly = false;
	}
	
	public String getScoreName() {
		return scoreName;
	}
	
	public boolean useExactOnly() {
		return useExactOnly;
	}
	
	public int getExactScore() {
		return exactScore;
	}

	public int getScoreMin() {
		return min;
	}
	
	public int getScoreMax() {
		return max;
	}
}
