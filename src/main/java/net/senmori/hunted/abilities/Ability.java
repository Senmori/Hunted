package net.senmori.hunted.abilities;

public abstract class Ability
{
	private String name;
	
	Ability(String name) {
		this.name = name;
	}
	
	
	public abstract void perform();
	
	public String getName()
	{
		return name;
	}
}
