import java.util.ArrayList;
import java.util.Scanner;


public class ShadowPlane 
{
	static Dice roll = new Dice();
	static int encounterRate = 50;
	static String location = "Portal";
	static Scanner scan = new Scanner(System.in);
	
	public static void run(Player player, Inventory inv, Magic mgk)
	{
		System.out.println("\nYou have entered the plane of shadow...\n");
		
		System.out.println("\nThe plane of shadow is not yet complete!");
		System.out.println("There will be far more to do once it is done.\n");
		
		
		boolean done = false;
		String input;
		
		while (!done)
		{
			System.out.print("What will you do? ");
			input = scan.nextLine();
			
			if (input.equalsIgnoreCase("help"))
			{
				System.out.println("look      ----- see the locations that you can go");
				System.out.println("go        ----- go to a new location");
				System.out.println("item      ----- use an item");
				System.out.println("inventory ----- see your inventory");
				System.out.println("spellbook ----- see your spellbook");
				System.out.println("equip     ----- equip an item");
				if (location.equals("Portal"))
				{
					System.out.println("leave     ----- go back to the material plane");
				}
				else
				{
					// Message about the wander command. REMOVE THIS WHEN THE PLANE IS COMPLETE
					System.out.println("\nNOTE: You can type \"wander\" to get an encounter without moving.");
					System.out.println("      This will not be an option once the shadow plane is complete.");
				}
			}
			else if (input.equalsIgnoreCase("item"))
			{
				Item.useItem(player, inv, mgk);
			}
			else if (input.equalsIgnoreCase("inventory"))
			{
				inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
			}
			else if (input.equalsIgnoreCase("spellbook"))
			{
				mgk.getSpells(false);
			}
			else if (input.equalsIgnoreCase("equip"))
			{
				inv.equipItem(player, false);
			}
			else if (input.equalsIgnoreCase("leave"))
			{
				if (location.equals("Portal"))
				{
					done = true;
				}
			}
			else if (input.equalsIgnoreCase("wander"))
			{
				// REMOVE THIS FEATURE WHEN THE PLANE IS COMPLETE
				System.out.println("\nWANDERING\n");
				encounter(player, inv, mgk);
			}
			else if (input.equalsIgnoreCase("look"))
			{
				System.out.println("You are currently at the " + location + ".");
				if (location.equals("Portal"))
				{
					System.out.println("You see the following locations:");
					System.out.println("\tValley\n\tForest\n\tCastle");
				}
				else if (location.equals("Valley"))
				{
					System.out.println("You see the following locations:");
					System.out.println("\tPortal\n\tForest\n\tCastle");
				}
				else if (location.equals("Forest"))
				{
					System.out.println("You see the following locations:");
					System.out.println("\tPortal\n\tValley\n\tCastle");
				}
				else if (location.equals("Castle"))
				{
					System.out.println("You see the following locations:");
					System.out.println("\tPortal\n\tValley\n\tForest");
				}
			}
			else if (input.equalsIgnoreCase("go"))
			{
				System.out.print("Where would you like to go? ");
				input = scan.nextLine();
				
				if (isValidLocation(input))
				{
					if (input.equalsIgnoreCase(location))
					{
						System.out.println("You are already at the " + location + "!");
					}
					else if (input.equalsIgnoreCase("Portal"))
					{
						location = "Portal";
						encounterRate = 50;
						System.out.println("You have arrived at the Portal.");
						encounter(player, inv, mgk);
					}
					else if (input.equalsIgnoreCase("Valley"))
					{
						location = "Valley";
						encounterRate = 60;
						System.out.println("You have arrived at the Valley.");
						encounter(player, inv, mgk);
					}
					else if (input.equalsIgnoreCase("Forest"))
					{
						location = "Forest";
						encounterRate = 75;
						System.out.println("You have arrived at the Forest.");
						encounter(player, inv, mgk);
					}
					else if (input.equalsIgnoreCase("Castle"))
					{
						location = "Castle";
						encounterRate = 40;
						System.out.println("You have arrived at the Castle.");
						encounter(player, inv, mgk);
					}
				}
				else
				{
					System.out.println("There is no such location.");
				}
			}
		}
	}
	
	private static boolean isValidLocation(String input)
	{
		if (input.equalsIgnoreCase("Portal")
				|| input.equalsIgnoreCase("Valley")
				|| input.equalsIgnoreCase("Forest")
				|| input.equalsIgnoreCase("Castle"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private static void encounter(Player player, Inventory inv, Magic mgk)
	{
		int rng = roll.d100();
		
		if (rng <= encounterRate)
		{
			String enemy = getAttackingCreature(player);
			if (enemy.equals("Dead Wizard") || enemy.equals("Lich"))
			{
				System.out.println("You see a robed corpse, long since dead.");
				boolean done = false;
				
				while (!done)
				{
					done = true;
					System.out.print("What will you do? (loot/leave) ");
					String input = scan.nextLine();
					if (input.equalsIgnoreCase("loot"))
					{
						if (enemy.equals("Lich"))
						{
							System.out.println("As you approach it, the corpse stands up and starts");
							System.out.println("cursing at you in a strange tongue!");
							Battle.Combat(player, mgk, inv, enemy, true);
						}
						else
						{
							if (roll.d100() > 40)
							{
								System.out.println("Most of the possessions are old and broken, but among");
								System.out.println("them lies the wizards spellbook!");
								inv.addItem("Wizards Spellbook", 8000);
							}
							else
							{
								System.out.println("Most of the possessions are old and broken, but among");
								System.out.println("them lies the wizards Staff!");
								inv.addItem("Staff of Storms", 21000);
							}
						}
					}
					else if (input.equalsIgnoreCase("leave"))
					{
						done = true;
					}
					else
					{
						System.out.println("Invalid Input");
						done = false;
					}
				}
			}
			else if (enemy.equals("Mercantile"))
			{
				Shops.mercantile(player, inv);
			}
			else
			{
				Battle.Combat(player, mgk, inv, enemy, true);
			}
		}
	}
	
	private static String getAttackingCreature(Player player)
	{
		int rng;
		ArrayList<String> encounters = new ArrayList<String>();
		
		encounters.add("Shadow Centaur");
		encounters.add("Shadow");
		
		if (location.equalsIgnoreCase("Portal"))
		{
			encounters.add("Warlock");
			encounters.add("Witch");
			encounters.add("Mercantile");
		}
		else if (location.equalsIgnoreCase("Valley"))
		{
			encounters.add("Dead Wizard");
			encounters.add("Lich");
			encounters.add("Dusk Beast");
		}
		else if (location.equalsIgnoreCase("Forest"))
		{
			encounters.add("Dusk Beast");
			encounters.add("Dark Lion");
		}
		else if (location.equalsIgnoreCase("Castle"))
		{
			encounters.add("Necromancer");
		}
		
		if (player.getAccessory().equals("Shadow Warding Charm") == false)
		{
			encounters.add("Greater Shadow");
			encounters.add("Nightcrawler");
			encounters.add("Nightwalker");
			encounters.add("Shadow Elemental");
		}
		
		rng = roll.dX(encounters.size());
		
		return encounters.get(rng - 1);
	}
}
