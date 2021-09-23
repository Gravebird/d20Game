import java.util.Scanner;

public class Game {
	
	public static boolean cheater = false;
	
	public static boolean castleAccess = false;
	public static boolean antidote = false;
	public static boolean ironSkin = false;
	
	static Dice roll = new Dice();

	public static void main(String[] args)
	{
		String input;
		boolean loop = true;
		boolean clock = true;
		Scanner scan = new Scanner(System.in);
		Magic Magic = new Magic();
		Inventory inv = new Inventory();
		
		Player player;
		
		System.out.print("What is your name? ");
		input = scan.nextLine();
		if (input.equalsIgnoreCase("quit"))
		{
			System.exit(0);
		}
		
		player = new Player(input);
		
		System.out.println("Str: " + player.getStr() + "\nDex: "
				+ player.getDex() + "\nCon: " + player.getCon()
				+ "\nInt: " + player.getInt() + "\nWis: "
				+ player.getWis() + "\nCha: " + player.getCha());
		do
		{
			System.out.println("Human           ----- Bonus feat");
			System.out.println("Elf             ----- +2 Dex, -2 Con, +2MP");
			System.out.println("Dwarf           ----- +2 Con, -2 Cha, +2 on saves against magic or poison");
			System.out.println("Gnome           ----- +2 Con, -2 Str, 5% spell failure from armor ignored");
			System.out.println("Halfling        ----- +2 Dex, -2 Str, +1 to AC");
			System.out.println("Half-Orc        ----- +2 Str, +2 Con, -2 Int");
			System.out.println("Arcania         ----- -2 Str, -2 Dex, -2 Con, +2 Int, 1 bonus spell");
			System.out.print("What is your race? ");
			input = scan.nextLine();
			if (input.equalsIgnoreCase("quit"))
			{
				System.exit(0);
			}
			else if (input.equalsIgnoreCase("human"))
				player.setRace(Player.Race.Human, Magic);
			else if (input.equalsIgnoreCase("elf"))
				player.setRace(Player.Race.Elf, Magic);
			else if (input.equalsIgnoreCase("dwarf"))
				player.setRace(Player.Race.Dwarf, Magic);
			else if (input.equalsIgnoreCase("gnome"))
				player.setRace(Player.Race.Gnome, Magic);
			else if (input.equalsIgnoreCase("halfling"))
				player.setRace(Player.Race.Halfling, Magic);
			else if (input.equalsIgnoreCase("half-orc"))
				player.setRace(Player.Race.HalfOrc, Magic);
			else if (input.equalsIgnoreCase("Arcania"))
				player.setRace(Player.Race.Arcania, Magic);
			else
			{
				System.out.println("Invalid Input");
				input = "";
			}
		}
		while (input.equals(""));
		System.out.print("Are you a fighter or a caster? ");
		do
		{
			input = scan.nextLine();
			if (input.equalsIgnoreCase("fighter")) {
				player.healthDie(10);
				loop = false;
			}
			else if (input.equalsIgnoreCase("caster")) {
				player.healthDie(6);
				System.out.print("Burning Hands\nMagic Missile"
						+ "\nRay of Frost\nWhat spell do you know? ");
				while (loop == true)
				{
					input = scan.nextLine();
					if (input.equalsIgnoreCase("Burning Hands")) {
						Magic.addSpell("Burning Hands", 1, false);
						loop = false;
					}
					else if (input.equalsIgnoreCase("Magic Missile")) {
						Magic.addSpell("Magic Missile", 1, false);
						loop = false;
					}
					else if (input.equalsIgnoreCase("Ray of Frost")) {
						Magic.addSpell("Ray of Frost", 1, false);
						loop = false;
					}
					else
						System.out.println("That is not an available spell.");
				}
				System.out.println("Spellbook: ");
				Magic.getSpells(false);
			}
			else if (input.equalsIgnoreCase("quit"))
			{
				System.exit(0);
			}
			else
				System.out.println("Only fighter or caster is allowed.");
		}
		while (loop == true);
		
		player.chooseFeat(Magic, true, false);
		
		loop = true;
		System.out.println(player.getStats());
		
		player.newQuest("none", "none", "none", 0, null);
		
		//Start of game
		System.out.println("You are standing in the streets of Cornwood.");
		
		while (loop == true)
		{
			System.out.print("What will you do? (type help to see a list of " 
					+ "options)");
			input = scan.nextLine();
			if (input.equalsIgnoreCase("help"))
			{
				System.out.println("go          ----- move to a different location.");
				System.out.println("item        ----- use an item from your inventory.");
				System.out.println("look        ----- look around your current area.");
				System.out.println("shop        ----- go to the shop if there is one nearby.");
				System.out.println("take        ----- takes an item, you could suffer consequences");
				System.out.println("                  for taking an owned item.");
				System.out.println("inventory   ----- presents your inventory to you.");
				System.out.println("spellbook   ----- presents your list of spells to you.");
				System.out.println("equip       ----- equips an item from your inventory.");
				System.out.println("unequip     ----- unequip an item.");
				System.out.println("stats       ----- presents your stat sheet to you.");
				System.out.println("quest       ----- shows you your current quest.");
				System.out.println("abandon     ----- gives up your current quest.");
				System.out.println("train       ----- level up.");
				System.out.println("feats       ----- see your feats.");
				System.out.println("qualities   ----- see your fighter qualities.");
				System.out.println("go-dungeon  ----- enter a dungeon.");
				System.out.println("go-arena    ----- fight in the arena for money.");
			}
			else if (input.equals("testingCheatS"))
			{
				if (!cheater)
					cheater = true;
				
				System.out.println("What will you do?");
				while (loop)
				{
					input = scan.nextLine();
					if (input.equals("moreMoney"))
					{
						System.out.print("How much? ");
						int num = scan.nextInt();
						player.changeGold(num);
						loop = false;
						scan.nextLine();
					}
					else if (input.equals("EXPofAges"))
					{
						System.out.print("How much?");
						int num = scan.nextInt();
						player.addXP(num);
						loop = false;
						scan.nextLine();
					}
					else if (input.equals("ENDGAME"))
					{
						System.out.println("The Dark Lord is waiting for you...");
						castleAccess = true;
						loop = false;
					}
					else if (input.equals("Major Creation"))
					{
						System.out.print("What item do you want? ");
						input = scan.nextLine();
						inv.addItem(input, 0);
						loop = false;
					}
					else if (input.equals("fight"))
					{
						System.out.print("What will you fight? ");
						input = scan.nextLine();
						Battle.Combat(player, Magic, inv, input, true);
						loop = false;
					}
					else if (input.equals("SET stats"))
					{
						loop = false;
						System.out.println("Enter your new stats: (Str) (Dex) (Con) (Int) (Wis) (Cha)");

						try
						{
							player.setStat("Str", scan.nextInt());
							System.out.println("\nStr: " + player.getStr());
							player.setStat("Dex", scan.nextInt());
							System.out.println("Dex: " + player.getDex());
							player.setStat("Con", scan.nextInt());
							System.out.println("Con: " + player.getCon());
							player.setStat("Int", scan.nextInt());
							System.out.println("Int: " + player.getInt());
							player.setStat("Wis", scan.nextInt());
							System.out.println("Wis: " + player.getWis());
							player.setStat("Cha", scan.nextInt());
							System.out.println("Cha: " + player.getCha() + "\n");
							scan.nextLine();
							
						}
						catch (java.util.InputMismatchException error)
						{
							System.out.println("Invalid input");
							System.out.println("Stats entered before the wrong input "
									+ " will still have been applied.");
						}
					}
					else if (input.equals("forbidden Knowledge"))
					{
						loop = false;
						System.out.println("What spell do you want to learn? ");
						System.out.println("Be careful, mistyping a spell will " +
								"cause you to have a spell \nthat does " +
								"nothing in your spellbook");
						input = scan.nextLine();
						System.out.print("What level spell is " + input + "? ");
						int spellLevel = scan.nextInt();
						Magic.addSpell(input, spellLevel, false);
						scan.nextLine();
					}
					else if (input.equals("shoppingSpree"))
					{
						loop = false;
						System.out.println("GOING TO MERCANTILE SHOP");
						Shops.mercantile(player, inv);
					}
				}
				loop = true;
			}
			else if (input.equalsIgnoreCase("quit"))
			{
				System.exit(0);
			}
			else if (input.equalsIgnoreCase("unequip"))
			{
				System.out.print("What will you unequip? (weappon/armor/accessory) ");
				String command = scan.nextLine();
				if (command.equalsIgnoreCase("weapon"))
				{
					player.setWeapon("unarmed", 0);
				}
				else if (command.equalsIgnoreCase("Armor"))
				{
					player.setArmor("none");
				}
				else if (command.equalsIgnoreCase("accessory"))
				{
					player.setAccessory("none");
				}
				else
				{
					System.out.println("Invalid Input");
				}
			}
			else if (input.equalsIgnoreCase("item"))
			{
				Item.useItem(player, inv, Magic);
			}
			else if (input.equalsIgnoreCase("qualities"))
			{
				System.out.println("\n" + player.getQualities() + "\n");
			}
			else if (input.equalsIgnoreCase("quest"))
			{
				System.out.println(player.getQuest());
			}
			else if (input.equalsIgnoreCase("abandon"))
			{
				System.out.print("Give up on \"" + player.getCurrentQuest().getName() + "\"? (yes/no) ");
				String choice = scan.nextLine();
				if (choice.equalsIgnoreCase("yes"))
				{
					System.out.println("\"" + player.getCurrentQuest().getName() + "\" was abandoned!");
					player.noQuest();
				}
				else
				{
					System.out.println("Did not give up on \"" + player.getCurrentQuest().getName() + "\".");
				}
			}
			else if (input.equalsIgnoreCase("inventory"))
			{
				inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
			}
			else if (input.equalsIgnoreCase("feats"))
			{
				System.out.println("\nFeats: " + player.getFeats() + "\n");
			}
			else if (input.equalsIgnoreCase("spellbook"))
			{
				Magic.getSpells(false);
			}
			else if (input.equalsIgnoreCase("stats"))
			{
				System.out.println(player.getStats());
			}
			else if (input.equalsIgnoreCase("look"))
			{
				System.out.println("You see the following places:\n");
				System.out.println("Shops/Services:");
				System.out.println("\t\tTavern");
				System.out.println("\t\tInn");
				System.out.println("\t\tShop");
				System.out.println("\t\tWizards hut");
				System.out.println("\t\tBlacksmith");
				System.out.println("\t\tDojo");
				System.out.println("\t\tChurch");
				System.out.println("\t\tEnchanter");
				System.out.println("\t\tPortal House");
				System.out.println();
				System.out.println("Hostile Locations:");
				System.out.println("\t\tAlley");
				System.out.println("\t\tForest");
				System.out.println("\t\tDeep Woods");
				System.out.println("\t\tCrypt");
				System.out.println("\t\tCastle");
				System.out.println("\t\tDungeon");
				System.out.println("\t\tArena");
				
				System.out.println();
			}
			else if (input.equalsIgnoreCase("equip"))
			{
				inv.equipItem(player, false);
			}
			else if (input.equalsIgnoreCase("train"))
			{
				player.levelUp(Magic);
			}
			else if (input.equalsIgnoreCase("shop"))
			{
				Shops.firstShop(player, inv);
			}
			else if (input.length() >= 4)
			{
				if (input.substring(0, 4).equals("take"))
			    {
				    System.out.println("There is nothing to take.");
			    }
			}
			else if (input.equalsIgnoreCase("go")) {
				System.out.print("Where would you like to go? ");
				input = scan.nextLine();
				if (input.equalsIgnoreCase("alley"))
				{
					if (player.getLevel() < 6)
					{
						Battle.Combat(player, Magic, inv, "Mugger", true);
					}
					else
					{
						int rng = roll.d100();
						if (rng > 75)
						{
							Battle.Combat(player, Magic, inv, "Ripper", true);
						}
						else
						{
							Battle.Combat(player, Magic, inv, "Mugger", true);
						}
					}
				}
				else if (input.equalsIgnoreCase("shop"))
				{
					Shops.firstShop(player, inv);
				}
				else if (input.equalsIgnoreCase("inn"))
				{
					Shops.firstInn(player, inv, clock);
					if (clock)
					{
						if (inv.hasItem("Wooden clock"))
						{
							clock = false;
						}
					}
				}
				else if (input.equalsIgnoreCase("Arena"))
				{
					Tournament.arena(player, Magic, inv, Shops.getDayCounter());
				}
				else if (input.equalsIgnoreCase("forest"))
				{
					Dice roll = new Dice();
					int rng = roll.d100();
					String enemyName;
					
					if (rng < 60)
					{
						enemyName = "Wolf";
					}
					else if (rng < 90)
					{
						enemyName = "Bear";
					}
					else
					{
						enemyName = "Witch";
					}
					
					Battle.Combat(player, Magic, inv, enemyName, true);
				}
				else if (input.equalsIgnoreCase("deep forest") || input.equalsIgnoreCase("deep woods"))
				{
					Dice roll = new Dice();
					int rng = roll.d100();
					String enemyName;
					int amount;
					
					if (rng < 19)
					{
						enemyName = "Wolf";
						amount = roll.XdY(2, 4);
					}
					else if (rng < 45)
					{
						enemyName = "Bear";
						amount = roll.d3();
					}
					else if (rng < 73)
					{
						enemyName = "Bison";
						amount = roll.dX(2);
					}
					else if (rng < 80)
					{
						enemyName = "Warlock";
						amount = 1;
					}
					else if (rng < 90)
					{
						enemyName = "Shadow Centaur";
						amount = 1;
					}
					else
					{
						enemyName = "Dire Bear";
						amount = 1;
					}
					
					if (amount > 1)
					{
						System.out.println(amount + " " + enemyName + "s attack "
								+ player.getName() + "!");
					}
					
					for (int index = 0; index < amount; index++)
					{
						Battle.Combat(player, Magic, inv, enemyName, true);
					}
				}
				else if (input.equalsIgnoreCase("dojo"))
				{
					Shops.trainer(player);
				}
				else if (input.equalsIgnoreCase("blacksmith"))
				{
					Shops.blacksmith(player, inv);
				}
				else if (input.equalsIgnoreCase("castle"))
				{
					if (castleAccess)
					{
						System.out.println("The guard is not guarding the "
								+ "door. What will you do? (enter/leave): ");
						input = scan.nextLine();
						if (input.equalsIgnoreCase("enter"))
						{
							System.out.println("You enter the throne room.");
							Battle.Combat(player, Magic, inv, "Dark Lord", true);
						}
					}
					else
					{
						Battle.Combat(player, Magic, inv, "Raging Guard", true);
					}
				}
				else if (input.equalsIgnoreCase("tavern"))
				{
					Tavern.enter(player, Magic, inv);
				}
				else if (input.equalsIgnoreCase("Wizards tent") || input.equalsIgnoreCase("hut"))
				{
					Shops.wizardTent(player, inv);
				}
				else if (input.equalsIgnoreCase("church"))
				{
					Shops.church(player, inv);
				}
				else if (input.equalsIgnoreCase("Crypt"))
				{
					Crypt dungeon= new Crypt();
					dungeon.crypt(player, Magic, inv);
				}
				else if (input.equalsIgnoreCase("enchanter"))
				{
					Shops.enchanter(player, inv);
				}
				else if (input.equalsIgnoreCase("Portal House"))
				{
					PortalHouse.enter(player, inv, Magic);
				}
				else if (input.equalsIgnoreCase("dungeon"))
				{
					String[] dungeonList = new String[3];
					boolean available = true;
					
					if (player.getLevel() == 1)
					{
						System.out.println("There are no dungeons available to you.");
						available = false;
					}
					else
					{
						if (player.getLevel() > 1)
						{
							dungeonList[0] = "Abandoned Keep      ----- Levels 2 to 4";
						}
						if (player.getLevel() > 4)
						{
							dungeonList[1] = "Sewers              ----- Levels 5 to 8";
						}
						if (player.getLevel() > 6)
						{
							dungeonList[2] = "Temple of Serpents  ----- Levels 7 to 11";
						}
					}
					
					if (available)
					{
						System.out.println("Dungeons:");
						for (int index = 0; index < dungeonList.length; index++)
						{
							if (dungeonList[index] != null)
							{
								System.out.println("\t" + dungeonList[index]);
							}
						}
						System.out.println("Which dungeon will you explore?");
						String choice = scan.nextLine();
						
						if (choice.equalsIgnoreCase("Abandoned Keep"))
						{
							if (player.getLevel() > 1)
							{
								Keep dungeon = new Keep();
								dungeon.keep(player, Magic, inv);
							}
						}
						else if (choice.equalsIgnoreCase("Sewers"))
						{
							if (player.getLevel() > 4)
							{
								Sewers dungeon = new Sewers();
								dungeon.sewer(player, Magic, inv);
							}
						}
						else if (choice.equalsIgnoreCase("Temple of Serpents"))
						{
							if (player.getLevel() > 6)
							{
								SerpentTemple dungeon = new SerpentTemple();
								dungeon.temple(player, Magic, inv);
							}
						}
					}
				}
			}
        }
	}
}
