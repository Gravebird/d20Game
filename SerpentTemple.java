import java.util.Scanner;


public class SerpentTemple extends Dungeon{
	
	boolean trapTriggered = false;
	boolean snakesInThePit = true;
	boolean room3Snakes = true;
	boolean room4Snakes = true;
	
	boolean complete = false;

	static boolean lootTaken = false;
	
	public void temple(Player player, Magic Magic, Inventory inv)
	{
		// Levels X - Y
		
		boolean done = false;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("------------------------------------");
		System.out.println("You have entered the Temple of Serpents.");
		System.out.println("Suggested Level: 7 - 11");
		System.out.println("------------------------------------");
		
		System.out.println("You are standing in the entrance to the temple." +
				"\nThere is a HALLWAY ahead of you.");
		
		while (!done && !complete)
		{
			System.out.println("What will you do?");
			String command = scan.nextLine();
			
			if (command.equalsIgnoreCase("help"))
			{
				help();
			}
			else if (command.equalsIgnoreCase("leave"))
			{
				done = true;
			}
			else if (command.equalsIgnoreCase("equip"))
			{
				inv.equipItem(player, false);
			}
			else if (command.equalsIgnoreCase("look"))
			{
				System.out.println("You are standing in the entrance to the temple." +
					"\nThere is a HALLWAY ahead of you.");
			}
			else if (command.equalsIgnoreCase("item"))
			{
				Item.useItem(player, inv, Magic);
			}
			else if (command.equalsIgnoreCase("inventory"))
			{
				inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
			}
			else if (command.equalsIgnoreCase("stats"))
			{
				System.out.println(player.getStats());
			}
			else if (command.equalsIgnoreCase("spellbook"))
			{
				Magic.getSpells(false);
			}
			else if (command.equalsIgnoreCase("cast"))
			{
				useSpell(player, Magic);
			}
			else if (command.equalsIgnoreCase("go"))
			{
				System.out.print("Where would you like to go? ");
				command = scan.nextLine();
				if (command.equalsIgnoreCase("hallway"))
				{
					room2(player, Magic, inv);
				}
			}
			command = "";
		}
	}
	
	public void room2(Player player, Magic Magic, Inventory inv)
	{
		
		boolean done = false;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("You are in the hallway of the temple." +
				"\nAhead of you there is an OPENING into a much larger room," +
				" though you can\nsee nothing more from here.");
		
		while (!done && !complete)
		{
			if (!trapTriggered)
			{
				System.out.println("The floor slides out from beneath you!");
				if (player.savingThrow(Player.Saves.Reflex, false, false) < 14)
				{
					System.out.println(player.getName() + " falls 40 feet into a pit!");
					Dice roll = new Dice();
					int totalDamage = roll.XdY(4, 6);
					player.meleeDamage(totalDamage);
					System.out.println(player.getName() + " took " + totalDamage + " damage!");
					snakePit(player, Magic, inv);
				}
				else
				{
					System.out.println(player.getName() + " jumps away from the trap!");
					player.addXP(roll.XdY(4, 100));
				}
				trapTriggered = true;
			}
			else
			{
				System.out.println("The hole in the floor is still present.");
			}
			System.out.println("What will you do?");
			String command = scan.nextLine();
			
			if (command.equalsIgnoreCase("help"))
			{
				help();
			}
			else if (command.equalsIgnoreCase("leave"))
			{
				done = true;
			}
			else if (command.equalsIgnoreCase("look"))
			{
				System.out.println("You are in the hallway of the temple." +
					"\nAhead of you there is an OPENING into a much larger room," +
					" though you can\nsee nothing more from here.");
			}
			else if (command.equalsIgnoreCase("item"))
			{
				Item.useItem(player, inv, Magic);
			}
			else if (command.equalsIgnoreCase("equip"))
			{
				inv.equipItem(player, false);
			}
			else if (command.equalsIgnoreCase("inventory"))
			{
				inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
			}
			else if (command.equalsIgnoreCase("stats"))
			{
				System.out.println(player.getStats());
			}
			else if (command.equalsIgnoreCase("spellbook"))
			{
				Magic.getSpells(false);
			}
			else if (command.equalsIgnoreCase("cast"))
			{
				useSpell(player, Magic);
			}
			else if (command.equalsIgnoreCase("go"))
			{
				System.out.println("Where would you like to go? ");
				command = scan.nextLine();
				if (command.equalsIgnoreCase("forward") || command.equalsIgnoreCase("opening"))
				{
					room3(player, Magic, inv);
				}
				else if (command.equalsIgnoreCase("pit") || command.equalsIgnoreCase("hole"))
				{
					System.out.println("You can't go down there.");
				}
			}
			command = "";
		}
	}
	
	public void snakePit(Player player, Magic Magic, Inventory inv)
	{
		// Levels X - Y
		
		boolean done = false;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("You are at the bottom of a pit.\nThere is a STAIRCASE leading out of this room.");
		if (snakesInThePit)
		
		while (!done && !complete)
		{
			if (snakesInThePit)
			{
				int numSnakes = roll.XdY(3, 4);
				for (int index = 0; index < numSnakes; index++)
				{
					Battle.Combat(player, Magic, inv, "Small Viper Snake", false);
				}
				snakesInThePit = false;
			}
			System.out.println("What will you do?");
			String command = scan.nextLine();
			
			if (command.equalsIgnoreCase("help"))
			{
				help();
			}
			else if (command.equalsIgnoreCase("leave"))
			{
				System.out.println("You cannot leave from here.");
			}
			else if (command.equalsIgnoreCase("look"))
			{
				System.out.println("You are at the bottom of a pit.\nThere is a STAIRCASE leading out of this room.");
			}
			else if (command.equalsIgnoreCase("item"))
			{
				Item.useItem(player, inv, Magic);
			}
			else if (command.equalsIgnoreCase("equip"))
			{
				inv.equipItem(player, false);
			}
			else if (command.equalsIgnoreCase("inventory"))
			{
				inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
			}
			else if (command.equalsIgnoreCase("stats"))
			{
				System.out.println(player.getStats());
			}
			else if (command.equalsIgnoreCase("spellbook"))
			{
				Magic.getSpells(false);
			}
			else if (command.equalsIgnoreCase("cast"))
			{
				useSpell(player, Magic);
			}
			else if (command.equalsIgnoreCase("go"))
			{
				System.out.print("Where would you like to go? ");
				command = scan.nextLine();
				if (command.equalsIgnoreCase("staircase"))
				{
					done = true;
					room3(player, Magic, inv);
				}
			}
			command = "";
		}
	}
	
	public void room3(Player player, Magic Magic, Inventory inv)
	{
		// Levels X - Y
		
		boolean done = false;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("This room is wide and open, it seems like a worship chamber of some sort." +
				"\nThere are symbols drawn upon the walls in blood, but you can't read them." +
				"\nA DOOR is visible on the opposite side of the room, as well as a STAIRCASE going downwards.");
		
		while (!done && !complete)
		{
			if (room3Snakes)
			{
				boolean escaped = false;
				Dice roll = new Dice();
				int numSnakes = roll.XdY(2, 3);
				for (int index = 0; index < numSnakes; index++)
				{
					escaped = Battle.Combat(player, Magic, inv, "Large Viper Snake", true);
					if (escaped)
						break;
				}
				if (escaped)
					done = true;
				else
					room3Snakes = false;
			}
			System.out.println("What will you do?");
			String command = scan.nextLine();
			
			if (command.equalsIgnoreCase("help"))
			{
				help();
			}
			else if (command.equalsIgnoreCase("leave"))
			{
				done = true;
			}
			else if (command.equalsIgnoreCase("look"))
			{
				System.out.println("This room is wide and open, it seems like a worship chamber of some sort." +
					"\nThere are symbols drawn upon the walls in blood, but you can't read them." +
					"\nA DOOR is visible on the opposite side of the room, as well as a STAIRCASE going downwards.");
			}
			else if (command.equalsIgnoreCase("item"))
			{
				Item.useItem(player, inv, Magic);
			}
			else if (command.equalsIgnoreCase("equip"))
			{
				inv.equipItem(player, false);
			}
			else if (command.equalsIgnoreCase("inventory"))
			{
				inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
			}
			else if (command.equalsIgnoreCase("stats"))
			{
				System.out.println(player.getStats());
			}
			else if (command.equalsIgnoreCase("spellbook"))
			{
				Magic.getSpells(false);
			}
			else if (command.equalsIgnoreCase("cast"))
			{
				useSpell(player, Magic);
			}
			else if (command.equalsIgnoreCase("go"))
			{
				System.out.print("Where would you like to go? ");
				command = scan.nextLine();
				if (command.equalsIgnoreCase("staircase"))
				{
					snakePit(player, Magic, inv);
				}
				else if (command.equalsIgnoreCase("door"))
				{
					room4(player, Magic, inv);
				}
			}
			command = "";
		}
	}
	
	public void room4(Player player, Magic Magic, Inventory inv)
	{
		// Levels X - Y
		
		boolean done = false;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("You are now in what appears to be some sort of "
				+ "sacrificial chamber.\nOn the other side of the room there is "
				+ "a large DOOR.");
		
		while (!done && !complete)
		{
			if (room4Snakes)
			{
				boolean escaped = false;
				Dice roll = new Dice();
				int numSnakes = roll.d3();
				for (int index = 0; index < numSnakes; index++)
				{
					escaped = Battle.Combat(player, Magic, inv, "Huge Viper Snake", true);
					if (escaped)
						break;
				}
				if (escaped)
					done = true;
				else
					room4Snakes = false;
			}
			System.out.println("What will you do?");
			String command = scan.nextLine();
			
			if (command.equalsIgnoreCase("help"))
			{
				help();
			}
			else if (command.equalsIgnoreCase("leave"))
			{
				done = true;
			}
			else if (command.equalsIgnoreCase("look"))
			{
				System.out.println("You are now in what appears to be some sort of "
					+ "sacrificial chamber.\nOn the other side of the room there is "
					+ "a large DOOR.");
			}
			else if (command.equalsIgnoreCase("item"))
			{
				Item.useItem(player, inv, Magic);
			}
			else if (command.equalsIgnoreCase("equip"))
			{
				inv.equipItem(player, false);
			}
			else if (command.equalsIgnoreCase("inventory"))
			{
				inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
			}
			else if (command.equalsIgnoreCase("stats"))
			{
				System.out.println(player.getStats());
			}
			else if (command.equalsIgnoreCase("spellbook"))
			{
				Magic.getSpells(false);
			}
			else if (command.equalsIgnoreCase("cast"))
			{
				useSpell(player, Magic);
			}
			else if (command.equalsIgnoreCase("go"))
			{
				System.out.print("Where would you like to go? ");
				command = scan.nextLine();
				if (command.equalsIgnoreCase("door"))
				{
					finalRoom(player, Magic, inv);
				}
			}
			command = "";
		}
	}
	
	public void finalRoom(Player player, Magic Magic, Inventory inv)
	{
        System.out.println("You have entered the Altar chamber!");
		
		boolean escaped = true;
		
		escaped = Battle.Combat(player, Magic, inv, "Yuan-Ti Abomination", true);
		
		if (!escaped)
		{
			player.changeGold(roll.XdY(15, 100));
			player.addXP(roll.XdY(15, 100));
			
			if (!lootTaken)
			{
				inv.addItem("Necklace of Purity", 19473);
				lootTaken = true;
			}
			complete = true;
		}
	}
}
