package net.senmori.hunted.managers;

import java.util.ArrayList;
import java.util.List;

import net.senmori.hunted.achievements.Achievement;

public class AchievementManager 
{
	
	private static List<Achievement> achievements;
	
	static
	{
		achievements = new ArrayList<Achievement>();
	}
	
	public static Achievement getAchievement(String name)
	{
		for(Achievement a : achievements)
		{
			if(a.getName().equalsIgnoreCase(name))
			{
				return a;
			}
		}
		return null;
	}
	
	public static void addAchievement(Achievement ach)
	{
		achievements.add(ach);
	}
	
	public static void removeAchievement(Achievement ach)
	{
		achievements.remove(ach);
	}
	
	public static void removeAchievement(String name)
	{
		for(Achievement a : achievements)
		{
			if(a.getName().equalsIgnoreCase(name))
			{
				achievements.remove(a);
			}
		}
	}
}
