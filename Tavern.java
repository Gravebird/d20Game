import java.util.Scanner;

public class Tavern {
	
	static Scanner scan = new Scanner(System.in);
	static int toddBeatenLast = -1;
	
	static boolean barkeepHasBeenTalkedTo = false;
	
	static Dice roll = new Dice();
	
	private static final int totalForestQuests = 5;

	public static void enter(Player player, Magic magic, Inventory inv)
	{
		System.out.println("\nYou have entered the tavern.\n");
		
		boolean done = false;
		
		if (roll.d100() <= 2)
		{
			Shops.mercantile(player, inv);
			System.out.println("The Mercantile teleports away.");
		}
		
		while (!done)
		{
			System.out.print("What will you do? ");
			String choice = scan.nextLine();
			
			if (choice.equalsIgnoreCase("help"))
			{
				System.out.println();
				System.out.println("barkeep  ----- talk to the tavern owner");
				System.out.println("look     ----- look around");
				System.out.println("[name]   ----- talk to one of the patrons");
				System.out.println("leave    ----- leave the tavern");
				System.out.println("buy      ----- buy a drink");
			}
			else if (choice.equalsIgnoreCase("barkeep"))
			{
				barkeep();
			}
			else if (choice.equalsIgnoreCase("look"))
			{
				look(player);
			}
			else if (choice.equalsIgnoreCase("leave"))
			{
				done = true;
			}
			else if (choice.equalsIgnoreCase("buy"))
			{
				Shops.tavern(player, inv);
			}
			else
			{
				if (choice.equalsIgnoreCase("Todd") && toddBeatenLast < Shops.dayCounter)
				{
					if (Player.beatenTodd)
					{
						System.out.println("\nTodd: You think you're so great don't you?");
						System.out.println("      Well I'll show you what's what!\n");
						Battle.Combat(player, magic, inv, "Todd", true);
						System.out.println("\nTodd: One day I will show you how a true");
						System.out.println("      warrior fights!\n");
						System.out.println("Todd left the tavern.\n");
						toddBeatenLast = Shops.dayCounter;
					}
					else
					{
						System.out.println("\nTodd: Hello there, I'm new to this town. I just");
						System.out.println("      started fighting in the arena, and I hope to");
						System.out.println("      become champion one day!\n");
					}
				}
				else if ((!Quest.muggerQuestComplete || (!Quest.ripperQuestComplete && Quest.muggerQuestComplete && player.getLevel() >= 6)) &&
						choice.equalsIgnoreCase("guard") || choice.equalsIgnoreCase("town guard"))
				{
					tavernGuard(player);
				}
				else if (choice.equalsIgnoreCase("Hunter") &&
						Quest.forestQuestStage < totalForestQuests && player.getLevel() > 3)
				{
					hunter(player, inv);
				}
				else if (choice.equalsIgnoreCase("Sorceror") && Battle.encounteredShadowCreature)
				{
					sorceror(player, inv);
				}
			}
		}
	}
	
	private static void barkeep()
	{
		String advice = "\nBarkeep: ";
		
		if (!barkeepHasBeenTalkedTo)
		{
			advice += "If you are looking for work you might try the church. I hear the\n"
				+    "         Paladin has some work that he will pay generously for.";
			barkeepHasBeenTalkedTo = true;
		}
		else
		{
			String possibleAdvice[] = new String[5];
			
			possibleAdvice[0] = "If you need to buy basic equipment, try the Shop. If you are\n"
					+           "         in the market for armor, try the Blacksmith. If you want powerful\n"
					+           "         magical equipment, try the Wizards Hut. Still not what you had in\n"
					+           "         mind? The church has a small selection of items that are especially\n"
					+           "         helpful when dealing with the undead.";
			
			possibleAdvice[1] = "There are several dungeons near this town. Surely you can find\n"
					+           "         some unique treasures if you complete the entire thing!";
			
			possibleAdvice[2] = "It's always useful to have a weapon on hand, even if you rely on\n"
					+           "         magic usually. You never know when you will run out of mana in\n"
					+           "         a tight spot.";
			
			possibleAdvice[3] = "It's important to stay alert when battling monsters! If you get\n"
					+           "         into a rhythm and stop paying attention you could easily wind\n"
					+           "         up dead simply because a dangerous monster appeared when you\n"
					+           "         thought you were safe.";
			
			possibleAdvice[4] = "When choosing a room at the inn, remember that a poor room will heal\n"
					+           "         you up to your level, a good room will heal you up to twice your\n"
					+           "         level, and the best room will heal you up to four times your level!\n"
					+           "         Regardless of how you rest, your mana will be restored to full.";
			
			int rng = roll.dX(possibleAdvice.length) - 1;
			
			advice += possibleAdvice[rng];
		}
		
		System.out.println(advice + "\n");
	}
	
	private static void look(Player player)
	{
		String[] list = new String[4];
		if (Player.beatenTodd && toddBeatenLast < Shops.dayCounter)
		{
			list[0] = "Todd sits at a table, glaring at you.";
		}
		else if (!Player.beatenTodd)
		{
			list[0] = "Todd sits at a table, looking anxious.";
		}
		if (!Quest.muggerQuestComplete
				|| (!Quest.ripperQuestComplete && Quest.muggerQuestComplete && player.getLevel() >= 6))
		{
			list[1] = "A town guard is speaking to the bartender.";
		}
		if (player.getLevel() > 3 && Quest.forestQuestStage < totalForestQuests)
		{
			list[2] = "A hunter stands near the door, watching you closely.";
		}
		if (Battle.encounteredShadowCreature)
		{
			list[3] = "A sorceror is questioning some of the patrons.";
		}
		
		int amount = 0;
		
		for (int i = 0; i < list.length; i++)
		{
			if (list[i] != null)
			{
				amount++;
			}
		}
		
		String adj;
		String noun;
		if (amount != 1)
		{
			adj = "are";
			noun = "people";
		}
		else
		{
			adj = "is";
			noun = "person";
		}
		
		System.out.println("\nThere " + adj + " " + amount + " " + noun + " in the tavern.\n");
		
		int personNumber = 1;
		
		for (int i = 0; i < list.length; i++)
		{
			if (list[i] != null)
			{
				System.out.println(personNumber + ": " + list[i]);
				personNumber++;
			}
		}
		System.out.println("\nThe barkeep is polishing mugs behind the counter");
		System.out.println();
	}
	
	/**
	 * The guard in the tavern. Gives the mugger and ripper quests.
	 * 
	 * @param player - The player
	 */
	private static void tavernGuard(Player player)
	{
		if (!Quest.muggerQuestComplete && player.getQuestID().equals("none"))
		{
			System.out.println("\nSuggested Level: 1");
			System.out.println("\nGuard: Hello there. You seem like the capable sort, would");
			System.out.println("       you help me with a problem? This town is in serious");
			System.out.println("       trouble, there seems to be an infinite number of muggers");
			System.out.println("       in that alley over there. If you could slay 50 of them");
			System.out.print("       I would happily reward you. (yes/no)");
			boolean loop = true;
			while (loop)
			{
				String input = scan.nextLine();
				if (input.equalsIgnoreCase("yes"))
				{
					String details = "The guard in the tavern wants me to kill 50 muggers to"
							+ "\nhelp ease the towns crime problem. The muggers can be found"
							+ "\nin the alley just outside the tavern.";
					
					player.newQuest("A serious problem", details, "Mugger", 50, "Tavern Guard");
					loop = false;
				}
				else if (input.equalsIgnoreCase("no"))
				{
					loop = false;
				}
				else
					System.out.println("Invalid Input");
			}
		}
		else if (player.getQuestID().equals("Mugger") && player.questIsComplete())
		{
			System.out.println("\nGuard: There is still no end to them, but at least some of them");
			System.out.println("       cannot hurt people anymore.\n");
			player.addXP(1000);
			player.changeGold(500);
			player.noQuest();
			Quest.muggerQuestComplete = true;
		}
		else if (!Quest.ripperQuestComplete && Quest.muggerQuestComplete && player.getLevel() >= 6 && player.getQuestID().equals("none"))
		{
			System.out.println("\nSuggested Level: 7");
			System.out.println("\nGuard: Some new threat has emerged within the city. Creatures ");
			System.out.println("       called \"Rippers\" have begun stalking the alleys, and ");
			System.out.println("       killing any unsuspecting citizens that happen by. Can you");
			System.out.print("       slay 10 of them for me? I will happily reward you. (yes\no)");
			boolean loop = true;
			while (loop)
			{
				String input = scan.nextLine();
				if (input.equalsIgnoreCase("yes"))
				{
					String details = "The guard in the tavern wants me to kill 10 Rippers."
							+ "\nThese creatures have recently started appearing in the"
							+ "\nalleys around town.";
					
					player.newQuest("Ripper is in Town", details, "Ripper", 10, "Tavern Guard");
					loop = false;
				}
				else if (input.equalsIgnoreCase("no"))
				{
					loop = false;
				}
				else
					System.out.println("Invalid Input");
			}
		}
		else if (player.getQuestID().equals("Ripper") && player.questIsComplete())
		{
			System.out.println("\nGuard: Unfortunately there seem to be too many of these");
			System.out.println("       creatures to count, but at least you have thinned");
			System.out.println("       their numbers for now.");
			player.addXP(2500);
			player.changeGold(1000);
			player.noQuest();
			Quest.ripperQuestComplete = true;
		}
		else if (player.getCurrentQuest().getGiver().equals("Tavern Guard") && player.questIsComplete() == false)
		{
			System.out.println("\nGuard: You still haven't finished dealing with my problem. If");
			System.out.println("       you want the reward come back when its done.\n");
		}
		else if (player.getQuestID().equals("none") == false)
		{
			System.out.println("Guard: You are already doing something for someone else. Come");
			System.out.println("       back when you're done with that and we'll talk.");
		}
	}
	
	/**
	 * <p>The hunter in the tavern, gives the wolf, bear, and witch quest that the
	 * raging guard used to give.</p>
	 * 
	 * @param player - the player's character
	 */
	private static void hunter(Player player, Inventory inv)
	{
		boolean loop = true;
		String input;
		
		if (Quest.forestQuestStage == 0 && player.getQuestID().equals("none"))
		{
			System.out.println("\nSuggested Level: 4");
			System.out.println("\nHunter: Hmm, you look like you can hold your own.");
			System.out.println("        How would you like to do some hunting for me?");
			System.out.println("        There are a bunch of wolves in the forest that need slaying,");
			System.out.print("        if you kill them for me I will reward you. (yes/no) ");
			
			while (loop)
			{
				input = scan.nextLine();
				if (input.equalsIgnoreCase("yes"))
				{
					System.out.println("Hunter: I knew you would be able to help, thanks. Just");
					System.out.println("        come back when you're done.");
					
					String details = "The hunter at the tavern wants me to kill 6 wolves in the"
							+ "\nforest just outside town. Wolves are not the only thing in the"
							+ "\nforest so I should buy a smoke bomb just in case";
					player.newQuest("Wolves to the Slaughter", details, "Wolf", 6, "Hunter");
					loop = false;
				}
				else if (input.equalsIgnoreCase("no"))
				{
					System.out.println("\nHunter: Looks like I misjudged you.\n");
					loop = false;
				}
				else
				{
					System.out.println("Invalid Input");
				}
			}
		}
		else if (player.getQuestID().equals("Wolf") && player.questIsComplete())
		{
			System.out.println("\nHunter: Thanks, now I've filled my quota for today.\n");
			player.addXP(1000);
			player.changeGold(400);
			player.noQuest();
			Quest.forestQuestStage++;
		}
		else if (Quest.forestQuestStage == 1 && player.getQuestID().equals("none"))
		{
			System.out.println("\nSuggested Level: 6");
			System.out.println("\nHunter: Want to try your hand at some harder prey?");
			System.out.println("        I have to kill 4 bears this time, if you do");
			System.out.print("        it for me I can pay you again. (yes/no) ");
			
			while (loop)
			{
				input = scan.nextLine();
				if (input.equalsIgnoreCase("yes"))
				{
					System.out.println("\nHunter: Perfect, just let me know when you're done.\n");
					
					String details = "The hunter in the tavern wants me to kill 4 bears so he can"
							+ "\nlaze around all day again. I have been promised a reward again,"
							+ "\nhowever.";
					
					player.newQuest("This is how we do it in Russia", details, "Bear", 4, "Hunter");
					loop = false;
				}
				else if (input.equalsIgnoreCase("no"))
				{
					System.out.println("\nHunter: Too scared huh? Fine I guess I'll do it myself.\n");
					loop = false;
				}
				else
				{
					System.out.println("Invalid Input");
				}
			}
		}
		else if (player.getQuestID().equals("Bear") && player.questIsComplete())
		{
			System.out.println("\nHunter: Thank you once again, my friend. I think we");
			System.out.println("        have a long and profitable future ahead of us.\n");
			player.addXP(1500);
			player.changeGold(600);
			player.noQuest();
			Quest.forestQuestStage++;
		}
		else if (Quest.forestQuestStage == 2 && player.getQuestID().equals("none"))
		{
			System.out.println("\nSuggested Level: 8");
			System.out.println("\nHunter: The prey this time is a little trickier. I need a witch");
			System.out.println("        to be slain, as one of them has been attacking the farms");
			System.out.print("        lately. Be careful, the witch is deadly. (yes/no) ");
			
			while (loop)
			{
				input = scan.nextLine();
				if (input.equalsIgnoreCase("yes"))
				{
					System.out.println("\nHunter: You can find a witch in the forest, be careful.\n");
					
					String details = "The hunter in the tavern wants me to kill a witch in the forest,"
							+ "\nwitches have taken many lives so I should be careful.";
					
					player.newQuest("She's such a Witch", details, "Witch", 1, "Hunter");
					loop = false;
				}
				else if (input.equalsIgnoreCase("no"))
				{
					System.out.println("\nHunter: You've come this far only to back out now?");
					System.out.println("        I'm disappointed...\n");
					loop = false;
				}
				else
				{
					System.out.println("Invalid Input");
				}
			}
		}
		else if (player.getQuestID().equals("Witch") && player.questIsComplete())
		{
			System.out.println("\nHunter: You saved me a mess of trouble this time,");
			System.out.println("        thank you so much.\n");
			player.addXP(2000);
			player.changeGold(800);
			player.noQuest();
			Quest.forestQuestStage++;
		}
		else if (Quest.forestQuestStage == 3 && player.getQuestID().equals("none"))
		{
			System.out.println("\nSuggested Level: 10");
			System.out.println("\nHunter: Hello again, how would you feel about doing");
			System.out.println("        something else for me? It will not be easy,");
			System.out.println("        I need you to go to the deep woods and kill 3");
			System.out.println("        Bison for me. Be careful, the deep woods is a");
			System.out.print("        very deadly place. (yes/no) ");
			
			while (loop)
			{
				input = scan.nextLine();
				if (input.equalsIgnoreCase("yes"))
				{
					System.out.println("\nHunter: Thanks, you don't know how grateful I am.\n");
					
					String details = "The hunter in the tavern wants me to kill 3 Bison, which"
							+ "\ncan be found in the deep woods. The deep woods is a dangerous"
							+ "\nplace so I should prepare.";
					
					player.newQuest("The Mighty Bison", details, "Bison", 3, "Hunter");
					loop = false;
				}
				else if (input.equalsIgnoreCase("no"))
				{
					System.out.println("\nHunter: Damn, I was hoping you could do this, I'm no");
					System.out.println("        match for the deep woods.\n");
					loop = false;
				}
				else
				{
					System.out.println("Invalid Input");
				}
			}
		}
		else if (player.getQuestID().equals("Bison") && player.questIsComplete())
		{
			System.out.println("\nHunter: Ah, thanks. You saved me the trouble of having");
			System.out.println("        to risk my own skin in the deep woods. That place");
			System.out.println("        is terrifying!\n");
			player.addXP(2500);
			player.changeGold(1000);
			player.noQuest();
			Quest.forestQuestStage++;
		}
		else if (Quest.forestQuestStage == 4 && player.getQuestID().equals("none"))
		{
			System.out.println("\nSuggested Level: 13");
			System.out.println("\nHunter: I have a serious problem. My client thinks I");
			System.out.println("        am the best hunter since I have filled all these");
			System.out.println("        requests, thanks to you. The truth is, I shouldn't");
			System.out.println("        have stopped hunting in the forest, the deep woods");
			System.out.println("        will be the death of me! Anyway, I need to kill a");
			System.out.print("        Dire Bear in the deep woods. Can you help me out? (yes/no) ");
			
			while (loop)
			{
				input = scan.nextLine();
				if (input.equalsIgnoreCase("yes"))
				{
					System.out.println("\nHunter: Thank you so much. After this I am going to");
					System.out.println("        pick up a nobler professon, like farming maybe...\n");
					
					String details = "The hunter in the tavern wants me to kill a Dire Bear"
							+ "\nin the deep woods. A Dire Bear is an extremely dangerous"
							+ "\nanimal, so I should be ready to fight it.";
					
					player.newQuest("You slay me", details, "Dire Bear", 1, "Hunter");
					loop = false;
				}
				else if (input.equalsIgnoreCase("no"))
				{
					System.out.println("\nHunter: Damn, I am so going to die...\n");
					loop = false;
				}
				else
				{
					System.out.println("Invalid Input");
				}
			}
		}
		else if (player.getQuestID().equals("Dire Bear") && player.questIsComplete())
		{
			System.out.println("\nHunter: Thank you, I would have no doubt been killed if");
			System.out.println("        I tried to slay a Dire Bear, you are one of a kind.");
			System.out.println("        Here, this is the book I was given for accepting this");
			System.out.println("        prey, you deserve it more than I.\n");
			inv.addItem("Tome of Knowledge", 20000);
			player.addXP(4000);
			player.changeGold(2000);
			player.noQuest();
			Quest.forestQuestStage++;
		}
		else if (player.getCurrentQuest().getGiver().equals("Hunter") && player.questIsComplete() == false)
		{
			System.out.println("\nHunter: What are you doing? You haven't finished what I");
			System.out.println("        asked so I'm not paying you yet!\n");
		}
		else if (player.getQuestID().equals("none") == false)
		{
			System.out.println("\nHunter: You are doing something for someone else already you dolt!\n");
		}
	}
	
	private static void sorceror(Player player, Inventory inv)
	{
		if (Quest.shadowQuestStage == 0 && player.getQuestID().equals("none"))
		{
			System.out.println("\nSuggested Level: 10\n");
			System.out.println("\nSorceror: Oh hello there! Did you happen to see some sort of ");
			System.out.println("          shadow creatures in the deep woods? I'm researching");
			System.out.println("          these creatures. I believe they come from another");
			System.out.print("          world. Would you be interested in helping me out? (yes/no) ");
			
			boolean loop = true;
			
			while (loop)
			{
				String input = scan.nextLine();
				
				if (input.equalsIgnoreCase("yes"))
				{
					System.out.println("\nSorceror: You will? Fantastic! I will open a portal to where");
					System.out.println("          I believe these creatures come from, some kind of");
					System.out.println("          shadow world. Go to the house of portals when you are");
					System.out.println("          ready. Also I recommend you wear this Shadow Warding Charm,");
					System.out.println("          so the most dangerous of the monsters leave you alone.\n");
					
					inv.addItem("Shadow Warding Charm", 1000);
					
					String details = "The sorceror in the tavern asked me to go to the shadow world" +
									 "\nand find out about these \"Shadow Creatures\"" +
									 "\nI can find the portal in the house of portals.";
					
					player.newQuest("New Frontiers", details, "Shadow", 5, "Sorceror");
					PortalHouse.shadowPortal = true;
					loop = false;
				}
				else if (input.equalsIgnoreCase("no"))
				{
					System.out.println("\nSorceror: Alright. I'll ask other people then.");
				}
				else
				{
					System.out.println("Invalid Input");
				}
			}
		}
		else if (player.getQuestID().equals("Shadow") && player.questIsComplete())
		{
			System.out.println("Sorceror: You came back alive! How fantastic!");
			System.out.println("          So you encountered some of these strange creatures?");
			System.out.println("          You must tell me all about it!");
			player.addXP(2500);
			player.changeGold(1250);
			player.noQuest();
			Quest.shadowQuestStage++;
		}
		else if (Quest.shadowQuestStage == 1)
		{
			System.out.println("Sorceror: I'm sorry, but I don't have anything else");
			System.out.println("          for you to do yet.");
		}
		else if (player.getCurrentQuest().getGiver().equals("Sorceror") && player.questIsComplete() == false)
		{
			System.out.println("\nSorceror: You still haven't done what I asked...\n");
		}
		else if (player.getQuestID().equals("none") == false)
		{
			System.out.println("\nSorceror: Oh, I see that you are already on a quest. Come back later.\n");
		}
	}
}
