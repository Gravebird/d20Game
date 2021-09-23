import java.util.ArrayList;
import java.util.Scanner;

public class Magic {
	
	private int damage;
	private int location;
	
	ArrayList<Spell> spellbook = new ArrayList<Spell>();
	static ArrayList<String> spellCodeValues = new ArrayList<String>();
	int spellCodeIndex = 1;
	Scanner scan = new Scanner(System.in);
	Dice roll = new Dice();
	
	
	public void addSpell(String Name, int cost, boolean canGrappleCast)
	{
		Spell spell = new Spell(Name, cost, canGrappleCast, (spellCodeValues.size() + 1));
		spellCodeValues.add(Name);
		spellbook.add(spell);
		System.out.println(Name + " was added to your spellbook!");
		spellCodeIndex++;
	}
	public String useSpell(String spell, int mana)
	{
		boolean match = false;
		location = 0;
		
		try
		{
			for (int index = 0; index < spellbook.size(); index++)
			{
				if (spell.equalsIgnoreCase(spellbook.get(index).getName()))
				{
					match = true;
					location = index;
				}
			}
			if (match == true && mana >= spellbook.get(location).getCost())
			{
				return spellbook.get(location).getName();
			}
			else if (mana < spellbook.get(location).getCost())
			{
				return "lack";
			}
			else
			{
				return "none";
			}
		}
		catch (java.lang.IndexOutOfBoundsException error)
		{
			return "none";
		}
	}
	public String getSpellFromCode(int input)
	{
		String spellName = "" + input;
		
		if (input <= spellCodeValues.size())
		{
			spellName = spellCodeValues.get(input - 1);
			System.out.println("\nCasting " + spellName + "\n");
		}
		
		return spellName;
	}
	public int getCost(String spell)
	{
		try
		{
			if (spell.equalsIgnoreCase(spellbook.get(location).getName()))
			{
				return spellbook.get(location).getCost();
			}
			else
			{
				return 0;
			}
		}
		catch (java.lang.IndexOutOfBoundsException error)
		{
			return 0;
		}
	}
	public int cast(String Name, int Level)
	{
		for (int index = 0; index < spellbook.size(); index++)
		{
			if (Name.equalsIgnoreCase(spellbook.get(index).toString()))
			{
				System.out.println("You cast "
						+ spellbook.get(index).toString());
			}
		}
		return damage;
	}
	public int getNumberOfSpellsKnown()
	{
		return spellbook.size();
	}
	public String[] getStillSpells()
	{
		String[] stillSpells = new String[4];
		stillSpells[0] = "Polymorph";
		stillSpells[1] = "Fireball";
		stillSpells[2] = "Cloak of Fire";
		stillSpells[3] = "Light Burst";
		
		return stillSpells;
	}
	public void getSpells(boolean grapple)
	{
		if (!grapple)
		{
			System.out.println("----------------------------------------");
			System.out.println("Spellbook:");
			for (int index = 0; index < spellbook.size(); index++)
			{
				System.out.println(spellbook.get(index));
			}
			System.out.println("----------------------------------------");
		}
		else
		{
			System.out.println("----------------------------------------");
			System.out.println("Spellbook:");
			for (int index = 0; index < spellbook.size(); index++)
			{
				if (spellbook.get(index).canGrappleCast() == true)
					System.out.print(spellbook.get(index));
			}
			System.out.println("----------------------------------------");
		}
	}
	public boolean hasSpell(String name)
	{
		boolean match = false;
		for (int index = 0; index < spellbook.size(); index++)
		{
			if (spellbook.get(index).getName().equalsIgnoreCase(name))
			{
				match = true;
			}
		}
		return match;
	}
	public Spell findSpell(String name)
	{
		boolean match = false;
		int location = 0;
		for (int index = 0; index < spellbook.size(); index++)
		{
			if (name.equalsIgnoreCase(spellbook.get(index).getName()))
			{
				match = true;
				location = index;
			}
		}
		if (match)
		{
			return spellbook.get(location);
		}
		else
		{
			System.out.println("Programmer error: " + name + " does not exist");
			return null;
		}
	}
	public void rngSpell(int num)
	{
		ArrayList<Spell> spellList = new ArrayList<Spell>();
		ArrayList<Spell> spellPool = new ArrayList<Spell>();
		
		//Create spells
		Spell spell = new Spell("Faerie Fire", 1, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Mage Armor", 1, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Cloak of Fire", 1, true, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Mend", 1, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("True Strike", 1, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Spark", 1, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Flash Freeze", 2, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Polymorph", 2, true, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Dimension Door", 2, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Shadow Slash", 2, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Cure", 2, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Haste", 2, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Heal", 3, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Confuse", 3, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Lightning Bolt", 3, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Bestow Curse", 3, true, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Hold Person", 3, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Hammer", 3, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Extended Mage Armor", 3, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Sonic Echo", 4, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Drain Life", 4, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Searing Light", 4, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Shadow Blast", 4, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Hold Monster", 5, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Slay Living", 6, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Sonic Boom", 6, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Light Burst", 6, true, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Avasculate", 7, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Drown", 7, false, spellCodeIndex);
		spellPool.add(spell);
		spell = new Spell("Sonic Hurricane", 8, false, spellCodeIndex);
		spellPool.add(spell);
		
		
		/*
		 * 57 spells in total
		 * 
		 * 27 of them are caster only spells
		 * 
		 * 30 of them can be obtained through the bonus spell feat or certain items
		 */
		
		//Generate the spells
		if (num > 1)
		{
			int value;
			boolean match = false;
			boolean loop = true;
			
			for (int index = 0; index < num; index++)
			{
				value = roll.dX(spellPool.size()) - 1;
				for (int location = 0; location < spellbook.size(); location++)
				{
					if (spellbook.get(location).getName().equals
							(spellPool.get(value).getName()))
					{
						match = true;
					}
				}
				for (int location = 0; location < spellList.size(); location++)
				{
					if (spellList.get(location).getName().equals
							(spellPool.get(value).getName()))
					{
						match = true;
					}
				}
				if (match)
				{
					match = false;
					index--;
				}
				else
				{
					spellList.add(spellPool.get(value));
				}
			}
			System.out.println("---------------------------------");
			for (int location = 0; location < spellList.size(); location++)
			{
				System.out.println(spellList.get(location).spellSelectionToString());
			}
			System.out.println("---------------------------------");
			while (loop)
			{
				System.out.print("Which spell do you want? ");
				String input = scan.nextLine();
				boolean choice = false;
				int matchLocation = 0;
				for (int index = 0; index < spellList.size(); index++)
				{
					if (input.equalsIgnoreCase(spellList.get(index).getName()))
					{
						choice = true;
						matchLocation = index;
					}
				}
				if (choice)
				{
					spellbook.add(spellList.get(matchLocation));
					System.out.println(spellList.get(matchLocation).getName()
							+ " was learned!");
					spellCodeValues.add(spellList.get(matchLocation).getName());
					loop = false;
				}
				else
				{
					System.out.println("That is not one of the choices.");
				}
			}
		}
		else if (num == 1)
		{
			int value;
			boolean done = false;
			
			while (!done)
			{
				done = true;
				value = roll.dX(spellPool.size() - 1);
				
				for (int i = 0; i < spellbook.size(); i++)
				{
					if (spellbook.get(i).getName().equals(spellPool.get(value).getName()))
					{
						done = false;
						break;
					}
				}
				
				if (done)
				{
					spellbook.add(spellPool.get(value));
					spellCodeValues.add(spellPool.get(value).getName());
					System.out.println(spellPool.get(value).getName() + " was learned!");
				}
			}
		}
		else
		{
			System.out.println("ERROR: Game invoked Magic.rngSpell(value), where value was " + num);
			System.out.println("       Cannot generate less than 1 spell!");
		}
		spellCodeIndex++;
	}
}
