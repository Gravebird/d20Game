import java.util.Scanner;


public class Sewers extends Dungeon{

	private boolean complete = false;
	private boolean room2Clear = false;
	private boolean room3Clear = false;
	
	static boolean lootTaken = false;
	
	public void sewer(Player player, Magic Magic, Inventory inv)
	{
		boolean done = false;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("------------------------------------");
		System.out.println("You have entered the Sewers.");
		System.out.println("Suggested Level: 5 - 8");
		System.out.println("------------------------------------");
		
		while (!done && !complete)
		{
			System.out.println("You are standing in a tunnel. There is an OPENING up ahead, but that is the only \nthing "
					+ "visible");
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
			else if (command.equalsIgnoreCase("spellbook"))
			{
				Magic.getSpells(false);
			}
			else if (command.equalsIgnoreCase("inventory"))
			{
				inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(),
						player.getArmor());
			}
			else if (command.equalsIgnoreCase("look"))
			{
				System.out.println("You are standing in a tunnel. There is an OPENING up ahead, but that is the only thing "
						+ "visible");
			}
			else if (command.equalsIgnoreCase("item"))
			{
				Item.useItem(player, inv, Magic);
			}
			else if (command.equalsIgnoreCase("cast"))
			{
				useSpell(player, Magic);
			}
			else if (command.equalsIgnoreCase("stats"))
			{
				System.out.println(player.getStats());
			}
			else if (command.equalsIgnoreCase("equip"))
			{
				inv.equipItem(player, false);
			}
			else if (command.equalsIgnoreCase("go"))
			{
				System.out.print("Where would you like to go? ");
				command = scan.nextLine();
				
				if (command.equalsIgnoreCase("forward") || command.equalsIgnoreCase("opening"))
				{
					room2(player, Magic, inv);
				}
			}
		}
	}
	
	public void room2(Player player, Magic Magic, Inventory inv)
	{
		
		boolean done = false;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("You are standing in the middile of a room that some dire rats "
				+ "had been using for a nest.\nThere is junk all over the place. There is a DOOR "
				+ "on the other side of the room.");

		if (!room2Clear)
		{
			boolean escaped = true;
			int numRats = roll.d6() + roll.d6();
			System.out.println(numRats + " Dire Rats attack you!");
			for (int index = 0; index < numRats; index++)
			{
				escaped = Battle.Combat(player, Magic, inv, "Dire Rat", true);
				if (escaped)
					break;
			}
			if (escaped)
			{
				done = true;
			}
			else
			{
				room2Clear = true;
			}
		}
		
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
			else if (command.equalsIgnoreCase("spellbook"))
			{
				Magic.getSpells(false);
			}
			else if (command.equalsIgnoreCase("inventory"))
			{
				inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
			}
			else if (command.equalsIgnoreCase("look"))
			{
				System.out.println("You are standing in the middile of a room that some dire rats "
						+ "had been using for a nest.\nThere is junk all over the place. There is a DOOR "
						+ "on the other side of the room.");
			}
			else if (command.equalsIgnoreCase("item"))
			{
				Item.useItem(player, inv, Magic);
			}
			else if (command.equalsIgnoreCase("cast"))
			{
				useSpell(player, Magic);
			}
			else if (command.equalsIgnoreCase("stats"))
			{
				System.out.println(player.getStats());
			}
			else if (command.equalsIgnoreCase("equip"))
			{
				inv.equipItem(player, false);
			}
			else if (command.equalsIgnoreCase("go"))
			{
				System.out.println("Where would you like to go? ");
				command = scan.nextLine();
				
				if (command.equalsIgnoreCase("back"))
				{
					done = true;
				}
				else if (command.equalsIgnoreCase("forward") || command.equalsIgnoreCase("door"))
				{
					room3(player, Magic, inv);
				}
			}
		}
	}
	public void room3(Player player, Magic Magic, Inventory inv)
	{
		// Levels X - Y
		
		boolean done = false;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("You are standing in the center of a flooded room, from here you can hear "
				+ "growling coming from the room up AHEAD.");
		
		if (!room3Clear)
		{
			boolean escaped = true;
			int numCrocs = roll.d3() + 1;
			System.out.println(numCrocs + " Crocodiles attack " + player.getName() + "!");
			for (int index = 0; index < numCrocs; index++)
			{
				escaped = Battle.Combat(player, Magic, inv, "Crocodile", true);
				if (escaped)
					break;
			}
			if (escaped)
			{
				done = true;
			}
			else
			{
				room3Clear = true;
			}
		}
		
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
			else if (command.equalsIgnoreCase("spellbook"))
			{
				Magic.getSpells(false);
			}
			else if (command.equalsIgnoreCase("inventory"))
			{
				inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
			}
			else if (command.equalsIgnoreCase("look"))
			{
				System.out.println("You are standing in the center of a flooded room, from here you can hear "
						+ "growling coming from the room up AHEAD.");
			}
			else if (command.equalsIgnoreCase("item"))
			{
				Item.useItem(player, inv, Magic);
			}
			else if (command.equalsIgnoreCase("cast"))
			{
				useSpell(player, Magic);
			}
			else if (command.equalsIgnoreCase("stats"))
			{
				System.out.println(player.getStats());
			}
			else if (command.equalsIgnoreCase("equip"))
			{
				inv.equipItem(player, false);
			}
			else if (command.equalsIgnoreCase("go"))
			{
				System.out.print("Where will you go? ");
				command = scan.nextLine();
				
				if (command.equalsIgnoreCase("back"))
				{
					done = true;
				}
				else if (command.equalsIgnoreCase("forward") || command.equalsIgnoreCase("ahead"))
				{
					bossRoom(player, Magic, inv);
				}
			}
		}
	}
	public void bossRoom(Player player, Magic Magic, Inventory inv)
	{
		System.out.println("You have entered the Giant Crocodiles nest!");
		
		boolean escaped = true;
		
		escaped = Battle.Combat(player, Magic, inv, "Giant Crocodile", true);
		
		if (!escaped)
		{
			player.changeGold(roll.XdY(10, 100));
			player.addXP(roll.XdY(10, 100));
			
			if (!lootTaken)
			{
				inv.addItem("Crown of Thought", 8472);
				lootTaken = true;
			}
			complete = true;
		}
	}
}
