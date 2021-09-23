
public class Spell {

	private String name;
	private int manaCost;
	boolean active = false;
	boolean grappleCast;
	int personalSpellCode;
	
	public Spell(String Name, int MPCost, boolean canGrappleCast, int code)
	{
		name = Name;
		manaCost = MPCost;
		grappleCast = canGrappleCast;
		personalSpellCode = code;
	}
	public String toString()
	{
		String spell = personalSpellCode + ": " + name;
		final int maxNameLength = 25;
		
		for (int i = name.length(); i < maxNameLength; i++)
		{
			spell += " ";
		}
		spell += "- " + manaCost + "MP";
		
		return spell;
		
		// replace false with [name.equals("Crushing Prison") || name.equals("Incendiary Cloud")] to revert
	}
	public String spellSelectionToString()
	{
		String spell = name;
		final int maxNameLength = 25;
		
		for (int i = name.length(); i < maxNameLength; i++)
		{
			spell += " ";
		}
		spell += "- " + manaCost + "MP";
		
		return spell;
		
		// replace false with [name.equals("Crushing Prison") || name.equals("Incendiary Cloud")] to revert
	}
	public String getName()
	{
		return name;
	}
	public int getCost()
	{
		return manaCost;
	}
	public void changeCost(int amount)
	{
		manaCost += amount;
		System.out.println(name + " now costs " + manaCost + "MP!");
	}
	public boolean getActive()
	{
		return active;
	}
	public boolean canGrappleCast()
	{
		return grappleCast;
	}
	public void setActive(boolean active)
	{
		this.active = active;
	}
	public static String[] getSpellDamageTypes()
	{
		String[] types = new String[7];
		
		types[0] = "fire";
		types[1] = "force";
		types[2] = "cold";
		types[3] = "electricity";
		types[4] = "sonic";
		types[5] = "entropy";
		types[6] = "light";
		
		return types;
	}
}
