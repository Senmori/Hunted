package net.senmori.hunted.achievements;


public class KillStreakAchievment extends Achievement
{
	/*
	 * Achievement for when a player gets a killstreak
	 */
	
	@Override
	public String getName() 
	{
		return "killstreak";
	}

	@Override
	public String getMessage() 
	{
		return "[!] You have achieved a killstreak of %a [!]";
	}
	
	
	@Override
	public boolean doesMeetCriteria(String player) 
	{
		return getPlayerManager().getPlayerFile(player).getPlayerKills() % 10 == 0;
	}

	@Override
	public void update(String player) 
	{
		
	}

	@Override
	public int getReward() 
	{
		return 20;
	}
}
