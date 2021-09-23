import java.util.Scanner;

public class PortalHouse {

	public static boolean shadowPortal = false;
	static Scanner scan = new Scanner(System.in);
	static int amount = 0;
	static String portals[] = new String[1];
	
	public static void enter(Player player, Inventory inv, Magic mgk)
	{
		amount = 0;
		boolean done = false;
		
		System.out.println("\nYou have entered the House of Portals");
		
		
		if (shadowPortal)
		{
			portals[0] = "Shadow Portal";
		}
		
		for (int i = 0; i < portals.length; i++)
		{
			if (portals[i] != null)
			{
				amount++;
			}
		}
		
		look();
		
		while (!done)
		{
			System.out.print("What will you do? ");
			String input = scan.nextLine();
			
			if (input.equalsIgnoreCase("help"))
			{
				help();
			}
			else if (input.equalsIgnoreCase("look"))
			{
				look();
			}
			else if (input.equalsIgnoreCase("leave"))
			{
				done = true;
			}
			else
			{
				/* Player has entered the name of a portal */
				if ((input.equalsIgnoreCase("Shadow Portal") || input.equalsIgnoreCase("Shadow"))
						&& shadowPortal)
				{
					ShadowPlane.run(player, inv, mgk);
					System.out.println("\nYou have entered the House of Portals.\n");
				}
				else
				{
					System.out.println("That is not an available portal.");
				}
			}
		}
	}
	
	public static void look()
	{
		if (amount == 0)
		{
			System.out.println("There are no open portals.");
		}
		else
		{
			String adj = "are";
			if (amount == 1)
			{
				adj = "is";
			}
			System.out.print("There " + adj + " " + amount + " portal");
			if (amount > 1)
			{
				System.out.print("s");
			}
			System.out.println(" open.\nPortals:\n");
			
			for (int index = 0; index < amount; index++)
			{
				System.out.println(portals[index]);
			}
		}
	}
	
	public static void help()
	{
		System.out.println("[portal name]   ----- use a portal");
		System.out.println("look            ----- look around");
		System.out.println("leave           ----- leave the portal house");
	}
}
