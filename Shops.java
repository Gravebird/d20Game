import java.util.ArrayList;
import java.util.Scanner;

public class Shops {
	
	static Scanner scan = new Scanner(System.in);
	
	static int dayCounter = 0;
	static int enchantmentStart = -1;
	static Item enchantedItem = null;
	
	public static int getDayCounter()
	{
		return dayCounter;
	}

	public static void firstShop(Player player, Inventory inv)
	{
		boolean loop = true;
		String input;
		
		Item[] stock = new Item[21];
		
		stock[0] = new Item(4, "Quarterstaff");
		stock[1] = new Item(7, "Rapier");
		stock[2] = new Item(8, "Sword");
		stock[3] = new Item(8, "Mace");
		stock[4] = new Item(9, "Axe");
		stock[5] = new Item(9, "Warhammer");
		stock[6] = new Item(20, "Bandages");
		stock[7] = new Item(50, "Potion");
		stock[8] = new Item(110, "Oil of Recovery");
		stock[9] = new Item(120, "Mana Potion");
		stock[10] = new Item(185, "Potion of Str");
		stock[11] = new Item(190, "Potion of Int");
		stock[12] = new Item(200, "Blink Token");
		stock[13] = new Item(250, "Antidote");
		stock[14] = new Item(300, "Smoke Bomb");
		stock[15] = new Item(2304, "Acidic Quarterstaff");
		stock[16] = new Item(2307, "Shocking Rapier");
		stock[17] = new Item(2308, "Flame Sword");
		stock[18] = new Item(2309, "Freezing Axe");
		stock[19] = new Item(8309, "Power Axe");
		stock[20] = new Item(8309, "Screaming Warhammer");
		
		while (loop == true) {
			
			System.out.println("Shop stock:");
			for (int index = 0; index < stock.length; index++)
			{
				System.out.println(stock[index].shopStock());
			}
			System.out.println("Gold: " + player.getGold() + "GP");
			System.out.println("What will you buy? ");
			input = scan.nextLine();
			
			if (input.equalsIgnoreCase("help"))
			{
				System.out.println("sell          ----- sell something"
						+ "\n<item name>   ----- buy an item"
						+ "\nleave         ----- leave the shop");
			}
			else if (input.equalsIgnoreCase("leave"))
			{
				loop = false;
			}
			else if (input.equalsIgnoreCase("sell"))
			{
				while (loop)
				{
					inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
					System.out.print("What do you want to sell?");
					input = scan.nextLine();
					
					if (input.equalsIgnoreCase("help"))
					{
						System.out.println("-------------------------");
						System.out.println("buy           ----- "
								+ "go back to buying");
						System.out.println("<item name>   ----- sell an item");
						System.out.println("leave         ----- leave the "
								+ "shop");
						System.out.println("-------------------------");
					}
					else if (input.equalsIgnoreCase("leave") ||
							input.equalsIgnoreCase("buy"))
					{
						loop = false;
					}
					else
					{
						inv.sellItem(player, input);
					}
				}
				if (input.equalsIgnoreCase("buy"))
				{
					loop = true;
				}
			}
			else
			{
				int location = -1;
				for (int index = 0; index < stock.length; index++)
				{
					if (input.equalsIgnoreCase(stock[index].getName()))
					{
						location = index;
					}
				}
				if (location != -1)
				{
					if (player.getGold() >= stock[location].getPrice())
					{
						player.changeGold(-stock[location].getPrice());
						inv.addItem(stock[location].getName(), stock[
						            location].getPrice());
					}
					else
					{
						System.out.println("You don't have enough gold.");
					}
				}
				else
				{
					System.out.println("That item is not sold here.");
				}
			}
		}
	}
	
	/**
	 * <p>The only trainer that should ever be in the game. This should not
	 * have a second version.</p>
	 * 
	 * @param player The player to be passed to the trainer
	 */
	public static void trainer(Player player)
	{
		boolean loop = false;
		String input;
		
		int strPrice = 1000 * (2 * player.getStrTrain(0));
		int dexPrice = 1000 * (2 * player.getDexTrain(0));
		int conPrice = 1000 * (2 * player.getConTrain(0));
		int intPrice = 1000 * (2 * player.getIntTrain(0));
		int wisPrice = 1000 * (2 * player.getWisTrain(0));
		int chaPrice = 1000 * (2 * player.getChaTrain(0));
		
		if (player.fullHealth())
		{
			System.out.println("The trainer walks over to you from the center" +
					" of his dojo. \"What do you want?\", he says");
			loop = true;
		}
		else
		{
			System.out.println("The trainer looks you over. \"Come back "
					+ "when your healed, training won't do any good "
					+ "right now.\"");
		}
		
		while(loop)
		{
			System.out.println("----------------------------------");
			System.out.println("\tStrength\t- " + strPrice + "GP");
			System.out.println("\tDexterity\t- " + dexPrice + "GP");
			System.out.println("\tConstitution\t- " + conPrice + "GP");
			System.out.println("\tIntelligence\t- " + intPrice + "GP");
			System.out.println("\tWisdom\t\t- " + wisPrice + "GP");
			System.out.println("\tCharisma\t- " + chaPrice + "GP");
			System.out.println("Gold: " + player.getGold() + "GP");
			System.out.println("----------------------------------");
			System.out.print("What will you do? ");
			input = scan.nextLine();
			
			if (input.equalsIgnoreCase("help"))
			{
				System.out.println("[stat name]\t - Purchase training"
						+ "\nleave\t\t - leave the trainer");
			}
			else if (input.equalsIgnoreCase("Strength"))
			{
				if (player.getGold() >= strPrice)
				{
					player.changeGold(-strPrice);
					player.changeStat("Strength", 1);
					strPrice = 1000 * (2 * player.getStrTrain(1));
				}
				else
				{
					System.out.println("You don't have enough gold.");
				}
			}
			else if (input.equalsIgnoreCase("Dexterity"))
			{
				if (player.getGold() >= dexPrice)
				{
					player.changeGold(-dexPrice);
					player.changeStat("Dexterity", 1);
					dexPrice = 1000 * (2 * player.getDexTrain(1));
				}
				else
				{
					System.out.println("You don't have enough gold.");
				}
			}
			else if (input.equalsIgnoreCase("Constitution"))
			{
				if (player.getGold() >= conPrice)
				{
					player.changeGold(-conPrice);
					player.changeStat("Constitution", 1);
					conPrice = 1000 * (2 * player.getConTrain(1));
				}
				else
				{
					System.out.println("You don't have enough gold.");
				}
			}
			else if (input.equalsIgnoreCase("Intelligence"))
			{
				if (player.getGold() >= intPrice)
				{
					player.changeGold(-intPrice);
					player.changeStat("Intelligence", 1);
					intPrice = 1000 * (2 * player.getIntTrain(1));
				}
				else
				{
					System.out.println("You don't have enough gold.");
				}
			}
			else if (input.equalsIgnoreCase("Wisdom"))
			{
				if (player.getGold() >= wisPrice)
				{
					player.changeGold(-wisPrice);
					player.changeStat("Wisdom", 1);
					wisPrice = 1000 * (2 * player.getWisTrain(1));
				}
				else
				{
					System.out.println("You don't have enough gold.");
				}
			}
			else if (input.equalsIgnoreCase("Charisma"))
			{
				if (player.getGold() >= chaPrice)
				{
					player.changeGold(-chaPrice);
					player.changeStat("Charisma", 1);
					chaPrice = 1000 * (2 * player.getChaTrain(1));
				}
				else
				{
					System.out.println("You don't have enough gold.");
				}
			}
			else if (input.equalsIgnoreCase("leave"))
			{
				loop = false;
			}
			else
			{
				System.out.println("The trainer gives you a funny look."
						+ " \"I can't help you there.\"");
			}
			System.out.println(player.getStats());
		}
	}
	
	/*
	 * <p>The inn, there may be a second version of this without the clock.</p>
	 */
	public static void firstInn(Player player, Inventory inv, boolean clock)
	{
		boolean loop = true;
		String input;
		System.out.println("The innkeeper smiles at you when you "
				+ "enter. \"Can I help you?\", she asks.");
		while (loop == true)
		{
			System.out.println();
			if (player.isDiseased())
			{
				System.out.println("**********");
				System.out.println("DISEASED");
				if (player.hasMummyRot())
				{
					System.out.println("\tMummy Rot");
				}
				System.out.println("**********");
			}
			if (player.isAbilityDamaged())
			{
				System.out.println("**********");
				System.out.println("ABILITY DAMAGED");
				System.out.println(player.getPoisonedStats());
				System.out.println("**********");
			}
			if (player.isDrunk())
			{
				System.out.println("**********");
				System.out.println("DRUNK");
				System.out.println("**********");
			}
			System.out.println("\t---Day " + dayCounter + "---");
			System.out.println("Prices:\n\tPoor quality room: "
					+ "2G\n\tGood quality room: 3G\n\tBest quality "
					+ "room: 5G\nHP: " + player.getHP() + "/" 
					+ player.getHPTotal()+ "\nGold: " 
					+ player.getGold() + "GP");
			System.out.println("What room do you want? ");
			input = scan.nextLine();
			
			if (input.equalsIgnoreCase("help"))
			{
				System.out.println("Poor      ----- buy the poor "
						+ "room\nGood      ----- buy the good "
						+ "room\nBest      ----- buy the best "
						+ "room\nLeave     ----- leave the inn");
			}
			else if (input.equalsIgnoreCase("poor"))
			{
				if (player.getGold() >= 2)
				{
					player.changeGold(-2);
					player.poorRest();
					dayCounter++;
					if (player.hasMummyRot())
					{
						player.mummyRotDamage();
					}
					if (player.hasMageArmor)
					{
						player.reverseMageArmor(true);
					}
					if (Game.antidote)
					{
						Game.antidote = false;
					}
					if (Game.ironSkin)
					{
						Game.ironSkin = false;
					}
				}
				else
					System.out.println("You don't have enough "
							+ "money.");
			}
			else if (input.equalsIgnoreCase("good"))
			{
				if (player.getGold() >= 3)
				{
					player.changeGold(-3);
					player.goodRest();
					dayCounter++;
					if (player.hasMummyRot())
					{
						player.mummyRotDamage();
					}
					if (player.hasMageArmor)
					{
						player.reverseMageArmor(true);
					}
					if (Game.antidote)
					{
						Game.antidote = false;
					}
					if (Game.ironSkin)
					{
						Game.ironSkin = false;
					}
				}
				else
				{
					System.out.println("You don't have enough "
							+ "money.");
				}
			}
			else if (input.equalsIgnoreCase("best"))
			{
				if (player.getGold() >= 5)
				{
					player.changeGold(-5);
					player.bestRest();
					dayCounter++;
					if (player.hasMummyRot())
					{
						player.mummyRotDamage();
					}
					if (player.hasMageArmor)
					{
						player.reverseMageArmor(true);
					}
					if (Game.antidote)
					{
						Game.antidote = false;
					}
					if (Game.ironSkin)
					{
						Game.ironSkin = false;
					}
				}
				else
				{
					System.out.println("You don't have enough "
							+ "money.");
				}
			}
			else if (input.equalsIgnoreCase("leave"))
			{
				loop = false;
			}
			else if (input.equalsIgnoreCase("look"))
			{
				if (clock == true)
				System.out.println("There isn't much of note in "
						+ "the inn, except a wooden clock on the "
						+ "innkeepers desk.");
				else
				{
					System.out.println("The desk looks much more "
							+ "plain without the clock.");
				}
			}
			else if (input.equalsIgnoreCase("take"))
			{
				System.out.println("take what? ");
				input = scan.nextLine();
				
				if (input.equalsIgnoreCase("clock") && clock == 
						true)
				{
					System.out.println("The innkeeper doesn't "
							+ "seem to notice for some reason.");
					inv.addItem("Wooden clock", 1);
					clock = false;
					Game.castleAccess = true;
				}
				else if (clock == false)
				{
					System.out.println("There is no clock on the "
							+ "desk.");
				}
			}
			
		}
		loop = true;
	}
	
	public static int getDay()
	{
		return dayCounter;
	}
	
	/**
	 * <p>The identifier for the town. This will be the only version.
	 */
	public static void wizardTent(Player player, Inventory inv)
	{
		boolean loop = true;
		final int identifyCost = 100;
		
		while (loop)
		{
			boolean notEnough = false;
			inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
			System.out.println("What needs to be identified?\nCost: " + identifyCost + "GP");
			String item = scan.nextLine();
			
			if (inv.hasItem(item) == false)
			{
				if (item.equalsIgnoreCase("help") == false)
				{
					if (item.equalsIgnoreCase("leave") == false)
					{
						if (item.equalsIgnoreCase("buy") == false)
						{
							item = "none";
						}
					}
				}
			}
			
			
			if (item.equalsIgnoreCase("help"))
			{
				System.out.println("---------------------------------------------");
				System.out.println("<item name>   - identify the item for " + identifyCost + "GP");
				System.out.println("buy           - buy something from the wizard");
				System.out.println("leave         - leave the wizard tent");
				System.out.println("---------------------------------------------");
			}
			else if (item.equalsIgnoreCase("leave"))
			{
				loop = false;
			}
			else if (item.equalsIgnoreCase("buy"))
			{
				Item[] stock = new Item[23];
				stock[0] = new Item(250, "Lightning Javelin");
				stock[1] = new Item(400, "Greater Potion");
				stock[2] = new Item(500, "Greater Mana Potion");
				stock[3] = new Item(800, "Major Mana Potion");
				stock[4] = new Item(2500, "Brawlers Gloves");
				stock[5] = new Item(1000, "Bracers of Armor +1");
				stock[6] = new Item(4000, "Bracers of Armor +2");
				stock[7] = new Item(1000, "Cloak of Resistance +1");
				stock[8] = new Item(4000, "Cloak of Resistance +2");
				stock[9] = new Item(2000, "Ring of Protection +1");
				stock[10] = new Item(8000, "Ring of Protection +2");
				stock[11] = new Item(4000, "Gauntlets of Strength +2");
				stock[12] = new Item(16000, "Gauntlets of Strength +4");
				stock[13] = new Item(4000, "Gloves of Dexterity +2");
				stock[14] = new Item(16000, "Gloves of Dexterity +4");
				stock[15] = new Item(4000, "Amulet of Health +2");
				stock[16] = new Item(16000, "Amulet of Health +4");
				stock[17] = new Item(4000, "Headband of Intellect +2");
				stock[18] = new Item(16000, "Headband of Intellect +4");
				stock[19] = new Item(4000, "Periapt of Wisdom +2");
				stock[20] = new Item(16000, "Periapt of Wisdom +4");
				stock[21] = new Item(4000, "Cloak of Charisma +2");
				stock[22] = new Item(16000, "Cloak of Charisma +4");
				
				
				System.out.println("---------------------------------------");
				for (int index = 0; index < stock.length; index++)
				{
					System.out.println(stock[index].shopStock());
				}
				System.out.println("---------------------------------------");
				
				boolean shopLoop = true;
				
				while (shopLoop)
				{
					System.out.println("Gold: " + player.getGold() + "GP");
					System.out.print("What will you buy? ");
					String command = scan.nextLine();
					
					if (command.equalsIgnoreCase("help"))
					{
						System.out.println("-----------------------------------------");
						System.out.println("<item name>         - buy an item");
						System.out.println("identify            - identify something");
						System.out.println("leave               - leave the wizard tent");
						System.out.println("-----------------------------------------");
					}
					else if (command.equalsIgnoreCase("identify"))
					{
						shopLoop = false;
					}
					else if (command.equalsIgnoreCase("leave"))
					{
						shopLoop = false;
						loop = false;
					}
					else
					{
						int location = -1;
						
						for (int index = 0; index < stock.length; index++)
						{
							if (command.equalsIgnoreCase(stock[index].getName()))
							{
								location = index;
							}
						}
						if (location == -1)
						{
							System.out.println("The wizard doesn't sell that.");
						}
						else if (player.getGold() >= stock[location].getPrice())
						{
							player.changeGold(-stock[location].getPrice());
							inv.addItem(stock[location].getName(), stock[location].getPrice());
						}
						else
						{
							System.out.println("You don't have enough gold.");
						}
					}
				}
			}
			else if (item.equalsIgnoreCase("Amulet of Hate"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("Increases damage output form all sources by 2.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Crown of Thought"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("Increases total MP by 4.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Flame Sword"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A Sword that deals 1-6 bonus fire damage.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Power Axe"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("An Axe that deals double your Strength modifer instead of 1.5X");
				}
				else
					notEnough = true;
			}
			else if (item.substring(0, item.length() - 3).equalsIgnoreCase("Ring of Protection"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A ring that grants a +" + item.substring(item.length() -1, item.length()) + 
							" bonus to AC (even touch and flat-footed)");
				}
				else
					notEnough = true;
			}
			else if (item.substring(0, item.length() - 3).equalsIgnoreCase("Bracers of Armor"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A pair of bracers that grant a +" + item.substring(item.length() - 1, item.length())
							+ " bonus to AC (but not touch AC)");
				}
				else
					notEnough = true;
			}
			else if (item.substring(0, item.length() - 3).equalsIgnoreCase("Cloak of Resistance"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("An enchanted cloak that gives the wearer a +" + item.substring(item.length() - 1, item.length())
							+ " bonus to all saving throws");
				}
				else
					notEnough = true;
			}
			else if (item.substring(0, item.length() - 3).equalsIgnoreCase("Gauntlets of Strength"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("Magical bracers that increase the strength of the "
							+ "wearer by " + item.substring(item.length() - 1, item.length())
							+ " while worn");
				}
				else
					notEnough = true;
			}
			else if (item.substring(0, item.length() - 3).equalsIgnoreCase("Gloves of Dexterity"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("Magical gloves that increase the dexterity of the "
							+ "wearer by " + item.substring(item.length() - 1, item.length())
							+ " while worn");
				}
				else
					notEnough = true;
			}
			else if (item.substring(0, item.length() - 3).equalsIgnoreCase("Amulet of Health"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A magical amulet that increases the constitution of the "
							+ "wearer by " + item.substring(item.length() - 1, item.length())
							+ " while worn");
				}
				else
					notEnough = true;
			}
			else if (item.substring(0, item.length() - 3).equalsIgnoreCase("Headband of Intellect"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A magical headband that increases the intelligence of the "
							+ "wearer by " + item.substring(item.length() - 1, item.length())
							+ " while worn");
				}
				else
					notEnough = true;
			}
			else if (item.substring(0, item.length() - 3).equalsIgnoreCase("Periapt of Wisdom"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A magical periapt that increases the wisdom of the "
							+ "wearer by " + item.substring(item.length() - 1, item.length())
							+ " while worn");
				}
				else
					notEnough = true;
			}
			else if (item.substring(0, item.length() - 3).equalsIgnoreCase("Cloak of Charisma"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A magical cloak that increases the charisma of the "
							+ "wearer by " + item.substring(item.length() - 1, item.length())
							+ " while worn");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Necklace of Purity"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A potent magical necklace that negates any sort of" +
							"poison damage that the wearer would take.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("potion"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A magical potion that restores 1d8 + your level HP");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("greater potion"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A potent magical potion that restores 3d8 + twice your level HP");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("mana potion"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A magical potion that restores 1d3 + 2 MP");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("greater mana potion"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A potent magical potion that restores 1d4 + 4 MP");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("major mana potion"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A powerful potion that restores 2d3 + 6 MP");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Undead Bane Mace"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("\nUndead Bane Mace: A mace that has +2 to hit and a bonus 2d6 + 2 damage when fighting undead\n");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Animal Bane Warhammer"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("\nAnimal Bane Warhammer: A Warhammer that has +2 to hit and a bonus 2d6 + 2 damage");
					System.out.println("                       when fighting animals!");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Disrupting Warhammer"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("\nDisrupting Warhammer: A potent undead slaying weapon, any undead that is hit must make a DC 14 will "
							+ "save or be destroyed.\n");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Full Potion"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("\nFull Potion: A magical potion that restores all HP of the drinker.\n");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Revitalising Potion"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("\nRevitalising Potion: A potent magical potion that restores the drinker as though they rested for a week.\n");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Freezing Axe"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A axe that deals a bonus 1-6 cold damage.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Shocking Rapier"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A rapier that deals a bonus 1-6 electricity damage.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("holy water"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("Restores 2d3 points to a single ability score upon use.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Lightning Javelin"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("Deals 5d6 electricity damage, with a save for half damage");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Blink Token"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("Teleports you 10 feet away, allowing you to instantly escape a grapple");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Acidic Quarterstaff"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A quarterstaff that deals a bonus 1-6 acid damage");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Screaming Warhammer"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A warhammer that deals a bonus 2-8 sonic damage");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Ghost Touch Mace"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A mace that can hit incorporeal targets as if they were " +
							"\ncompletely physical");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Soulstealing Rapier"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A rapier that drains 2d6 life from the enemy per hit.\n"
							+ "You receive all life lost this way.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Brawlers Gloves"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("Thick gloves that add 1d6 damage to the wearers unarmed damage while worn.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Shadow Warding Charm"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A magical charm that wards off the most dangerous of shadow creatures.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Book of burning"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A book, enchanted to shoot a ball of fire (2d6 damage) whenever it is opened!");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Rod of Lightning"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A rod that shoots lightning when used (10d6 damage)");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Sword of Agrinon"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A sword which has a 2% chance to kill any mortal creature");
					System.out.println("in one hit.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Ring of Frost Resistance"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("While wearing this ring, you will take 10 less damage from");
					System.out.println("cold attacks");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Full plate of Absorbtion"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("This armor reduces all non-physical damage by 10.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Antidote"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("Gives you +4 against poison effects until you sleep.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Breastplate of Spellcasting"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("This armor is designed to be easy to cast spells in.");
					System.out.println("There is no chance of failing a spell because of this armor.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Lucky Amulet"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("This amulet earns you more loot from battle.");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("Staff of Storms"))
			{
				if (player.getGold() >= identifyCost)
				{
					player.changeGold(-identifyCost);
					System.out.println("A rare magical quarterstaff that deals an extra"
							+ "\nd4 of damage for every 3 caster levels you possess."
							+ "\nIt also gives you an additional 2 mana while equipped");
				}
				else
					notEnough = true;
			}
			else if (item.equalsIgnoreCase("none"))
			{
				System.out.println("You don't have that item.");
			}
			else
			{
				System.out.println("That is not a magical item.");
			}
			if (notEnough)
			{
				System.out.println("You don't have enough gold.");
				notEnough = false;
			}
		}
	}
	
	public static void church(Player player, Inventory inv)
	{
		System.out.println("You have entered the church.");
		boolean done = false;
		boolean shopping = false;
		
		while (!done)
		{
			System.out.print("What will you do? ");
			String command = scan.nextLine();
			if (command.equals("help"))
			{
				System.out.println("disease      ----- cure a disease");
				System.out.println("look         ----- look around");
				System.out.println("paladin      ----- speak to the paladin");
				System.out.println("buy          ----- buy something");
				System.out.println("leave        ----- leave the church");
			}
			else if (command.equals("look"))
			{
				System.out.println("This church is devoted to the god of healing and the sun, Pelor.\n"
						+ "There are many clerics as well as a few paladins among the many people inside.");
			}
			else if (command.equals("paladin"))
			{
				if (Quest.churchQuestStage == 0 && player.getQuestID().equals("none"))
				{
					System.out.println("\nSuggested Level: 3");
					System.out.println("\nPaladin: Hello. You look like someone who can solve a problem.");
					System.out.println("         I have a just such a problem you see, the crypt just");
					System.out.println("         outside town is infested with undead. If you can go in");
					System.out.println("         there and get rid of the 5 skeletons or zombies, I will");
					System.out.print("         richly reward you. (yes/no) ");
					boolean loop = true;
					while (loop)
					{
						String choice = scan.nextLine();
						if (choice.equalsIgnoreCase("yes"))
						{
							String details = "The paladin in the church "
									+ "wants me to kill 5 skeletons or zombies.\n"
									+ "They can be found in the crypt or the abandoned keep.\n"
									+ "I have been "
									+ "promised a great reward.";
							player.newQuest("A holy request",
									details, "Skeleton", 5, "Paladin");
							loop = false;
						}
						else if (choice.equalsIgnoreCase("no"))
							loop = false;
						else
						{
							System.out.println("Invalid input");
						}
					}
				}
				else if (player.getQuestID().equals("Skeleton") && player.questIsComplete())
				{
					System.out.println("\nPaladin: Wonderful job, son. Maybe we can do this just yet.\n");
					player.addXP(1200);
					player.changeGold(600);
					player.noQuest();
					Quest.churchQuestStage++;
				}
				else if (Quest.churchQuestStage == 1 && player.getQuestID().equals("none"))
				{
					System.out.println("\nSuggested Level: 4");
					System.out.println("\nPaladin: I have another task for you. There is an owlbear skeleton");
					System.out.println("         in the abandoned keep. If you can end its torment, I will");
					System.out.print("         reward you even better than last time. (yes/no) ");
					boolean loop = true;
					while (loop)
					{
						String choice = scan.nextLine();
						if (choice.equalsIgnoreCase("yes"))
						{
							String details = "The paladin wants me to kill "
									+ "the Owlbear Skeleton in the abandoned keep\n"
									+ ", the dungeon just outside town. I have been\n"
									+ "promised a great reward.";
							player.newQuest("A holy request - part 2", details, "Owlbear Skeleton", 1, "Paladin");
							loop = false;
						}
						else if (choice.equalsIgnoreCase("no"))
							loop = false;
						else
							System.out.println("Invalid Input");
					}
				}
				else if (player.getQuestID().equals("Owlbear Skeleton") && player.questIsComplete())
				{
					System.out.println("\nPaladin: I'm glad you came back safely, and to hear that the");
					System.out.println("         abomination is dead. Here, you've earned this.\n");
					player.addXP(1500);
					player.changeGold(800);
					player.noQuest();
					Quest.churchQuestStage++;
				}
				else if (Quest.churchQuestStage == 2 && player.getQuestID().equals("none"))
				{
					System.out.println("\nSuggested Level: 5");
					System.out.println("\nPaladin: Ah, there you are. I feel like I can rely on you to solve this towns");
					System.out.println("         problems. Well it seems that there is a dire rat infestation in the");
					System.out.print("         sewers. Would you be willing to clear it out? (yes/no)");
					boolean loop = true;
					while (loop)
					{
						String choice = scan.nextLine();
						if (choice.equalsIgnoreCase("yes"))
						{
							String details = "The paladin wants me to kill 8 dire rats from the sewers near town."
									+ "\nThese rats have poison so I should be careful.";
							player.newQuest("Cleaning up", details, "Dire Rat", 8, "Paladin");
							loop = false;
						}
						else if (choice.equalsIgnoreCase("no"))
						{
							loop = false;
						}
						else
							System.out.println("Invalid Input");
					}
				}
				else if (player.getQuestID().equals("Dire Rat") && player.questIsComplete())
				{
					System.out.println("\nPaladin: Those rats will bother this town no longer, and you have earned this.\n");
					player.addXP(1800);
					player.changeGold(1000);
					player.noQuest();
					Quest.churchQuestStage++;
				}
				else if (Quest.churchQuestStage == 3 && player.getQuestID().equals("none"))
				{
					System.out.println("\nSuggested Level: 7");
					System.out.println("\nPaladin: It would seem that the rats were not the only problem in our sewers.");
					System.out.println("         There is a giant crocodile down there, and it has been birthing");
					System.out.println("         more crocodiles. If you could kill some of the smaller crocodiles");
					System.out.print("         you would sure be doing this town a favor. (yes/no)");
					boolean loop = true;
					while (loop)
					{
						String choice = scan.nextLine();
						if (choice.equalsIgnoreCase("yes"))
						{
							String details = "The paladin wants me to kill 3 crocodiles in the sewers near town."
									+ "\nThe crocodiles climb on top of their opponent and crush them with their"
									+ "\nweight, so I should prepare for this possibility.";
							player.newQuest("The Giant Crocodile - part 1", details, "Crocodile", 3, "Paladin");
							loop = false;
						}
						else if (choice.equalsIgnoreCase("no"))
						{
							loop = false;
						}
						else
							System.out.println("Invalid Input");
					}
				}
				else if (player.getQuestID().equals("Crocodile") && player.questIsComplete())
				{
					System.out.println("\nPaladin: Now the Giant Crocodile is all that is left to make");
					System.out.println("         the sewer system safe once again. Great job.\n");
					player.addXP(2100);
					player.changeGold(1200);
					player.noQuest();
					Quest.churchQuestStage++;
				}
				else if (Quest.churchQuestStage == 4 && player.getQuestID().equals("none"))
				{
					System.out.println("\nSuggested Level: 10");
					System.out.println("\nPaladin: It's time we got rid of that Giant Crocodile. The longer");
					System.out.println("         we wait, the more crocodiles it will spawn. Of course you");
					System.out.print("         will be rewarded once again. (yes/no)");
					boolean loop = true;
					while (loop)
					{
						String choice = scan.nextLine();
						if (choice.equalsIgnoreCase("yes"))
						{
							String details = "The paladin wants me to kill the Giant Crocodile in the sewers"
									+ "\nnear town. The Giant Crocodile has a huge jaw and can crush bones"
									+ "\nwith each bite, so I should be prepared to fight it.";
							player.newQuest("The Giant Crocodile - part 2", details, "Giant Crocodile", 1, "Paladin");
							loop = false;
						}
						else if (choice.equalsIgnoreCase("no"))
						{
							loop = false;
						}
						else
							System.out.println("Invalid Input");
					}
				}
				else if (player.getQuestID().equals("Giant Crocodile") && player.questIsComplete())
				{
					System.out.println("\nPaladin: The town truly owes you, for now the sewers are safe");
					System.out.println("         once again. Take this potion, you deserve it.\n");
					inv.addItem("Potion of Enlightenment", 10000);
					player.addXP(2400);
					player.changeGold(1400);
					player.noQuest();
					Quest.churchQuestStage++;
				}
				else if (Quest.churchQuestStage == 5 && player.getQuestID().equals("none"))
				{
					System.out.println("\nPaladin: I'm sorry, I don't have anything more for you.");
					System.out.println("         Try the tavern if you're still looking for work.\n");
				}
				else if (player.getCurrentQuest().getGiver().equals("Paladin") && player.questIsComplete() == false)
				{
					System.out.println("\nPaladin: You haven't finished the last task I assigned.");
					System.out.println("         Come back when you're done.\n");
				}
				else if (player.getQuestID().equals("none") == false)
				{
					// This should be the last thing in this if/else branch
					if (Quest.churchQuestStage == 5)
					{
						System.out.println("\nPaladin: I'm sorry, I don't have anything more for you.");
						System.out.println("         Try the tavern if you're still looking for work.\n");
					}
					else
					{
						System.out.println("\nPaladin: Oh, you already have a quest, come talk to me when its done.\n");
					}
				}
			}
			else if (command.equals("leave"))
			{
				done = true;
			}
			else if (command.equals("buy"))
			{
				shopping = true;
				while (shopping)
				{
					Item[] wares = new Item[4];
					wares[0] = new Item("Undead Bane Mace", 1, 2308);
					wares[1] = new Item("Holy Water", 1, 296);
					wares[2] = new Item("Ghost Touch Mace", 1, 4308);
					wares[3] = new Item("Disrupting Warhammer", 1, 8312);
					
					for (int index = 0; index < wares.length; index++)
					{
						System.out.println(wares[index].shopStock());
					}
					System.out.println("Gold: " + player.getGold() + "GP");
					System.out.print("What will you buy? (type leave to exit) ");
					String input = scan.nextLine();
					boolean match = false;
					
					for (int index = 0; index < wares.length; index++)
					{
						if (input.equalsIgnoreCase(wares[index].getName()))
						{
							if (player.getGold() >= wares[index].getPrice())
							{
								player.changeGold(-wares[index].getPrice());
								inv.addItem(wares[index].getName(), wares[index].getPrice());
							}
							else
								System.out.println("You don't have enough gold.");
							match = true;
						}
						else if (input.equalsIgnoreCase("leave"))
							shopping = false;
					}
					if (!match)
						System.out.println("They do not sell " + input + " here.");
				}
			}
			else if (command.equals("disease"))
			{
				boolean match = false;
				if (player.isDiseased())
				{
					String[] list = new String[1];
					if (player.hasMummyRot())
					{
						list[0] = "Mummy Rot";
					}
					
					System.out.println("----------------");
					for (int index = 0; index < list.length; index++)
					{
						if (list[index] != null)
						{
							System.out.println(list[index]);
						}
					}
					System.out.println("----------------");
					System.out.print("Which disease needs curing? ");
					command = scan.nextLine();
					for (int index = 0; index < list.length; index++)
					{
						if (command.equalsIgnoreCase(list[index]))
						{
							match = true;
						}
					}
					if (match)
					{
						final int price;
						
						if (command.equalsIgnoreCase("Mummy Rot"))
						{
							price = 300;
							boolean complete = false;
							while (!complete)
							{
								System.out.println("GP: " + player.getGold());
								System.out.print("The process will cost " + price + " GP. (yes/no) ");
								command = scan.nextLine();
								if (command.equalsIgnoreCase("Yes"))
								{
									if (player.getGold() >= price)
									{
										player.changeGold(-price);
										Dice roll = new Dice();
										int casterLevelCheck = roll.d20() + 11;
										if (casterLevelCheck >= 20)
										{
											System.out.println("The disease has been cured!");
											player.setMummyRot(false);
											complete = true;
										}
										else
										{
											System.out.println("The disease resisted the magical healing!");
										}
									}
									else
									{
										System.out.println("You do not have enough money.");
										complete = true;
									}
								}
							}
						}
					}
					else
					{
						System.out.println("You don't have that disease.");
					}
				}
				else
				{
					System.out.println("You are suffering from no disease.");
				}
			}
		}
	}
	
	public static void blacksmith(Player player, Inventory inv)
	{
		boolean loop = true;
		String type = "";
		String input;
		//Trying to improve the way the shop works.
		Item[] lightStock = new Item[4];
		Item[] medStock = new Item[2];
		Item[] heavyStock = new Item[4];
		
		lightStock[0] = new Item(5, "Padded");
		lightStock[1] = new Item(10, "Leather");
		lightStock[2] = new Item(25, "Studded Leather");
		lightStock[3] = new Item(100, "Chain Shirt");
		medStock[0] = new Item(150, "Chainmail");
		medStock[1] = new Item(200, "Breastplate");
		heavyStock[0] = new Item(200, "Splint mail");
		heavyStock[1] = new Item(250, "Banded mail");
		heavyStock[2] = new Item(600, "Half-plate");
		heavyStock[3] = new Item(1500, "Full plate");
		
		while (loop == true) {
			
			System.out.println("Light Armor:");
			for (int index = 0; index < lightStock.length; index++)
			{
				System.out.println(lightStock[index].shopStock());
			}
			System.out.println("Medium Armor:");
			for (int index = 0; index < medStock.length; index++)
			{
				System.out.println(medStock[index].shopStock());
			}
			System.out.println("Heavy Armor:");
			for (int index = 0; index < heavyStock.length; index++)
			{
				System.out.println(heavyStock[index].shopStock());
			}
			System.out.println("Gold: " + player.getGold() + "GP");
			System.out.println("What will you buy? ");
			input = scan.nextLine();
			
			if (input.equalsIgnoreCase("help"))
			{
				System.out.println("<item name>   ----- buy an item"
						+ "\nleave         ----- leave the blacksmith");
			}
			else if (input.equalsIgnoreCase("leave"))
			{
				loop = false;
			}
			else
			{
				int location = -1;
				for (int index = 0; index < lightStock.length; index++)
				{
					if (input.equalsIgnoreCase(lightStock[index].getName()))
					{
						location = index;
						type = "light";
					}
				}
				for (int index = 0; index < medStock.length; index++)
				{
					if (input.equalsIgnoreCase(medStock[index].getName()))
					{
						location = index;
						type = "medium";
					}
				}
				for (int index = 0; index < heavyStock.length; index++)
				{
					if (input.equalsIgnoreCase(heavyStock[index].getName()))
					{
						location = index;
						type = "heavy";
					}
				}
				if (location != -1)
				{
					if (type.equals("light"))
					{
						if (player.getGold() >= lightStock[location].getPrice())
						{
							player.changeGold(-lightStock[location].getPrice());
							inv.addItem(lightStock[location].getName(), lightStock[
							            location].getPrice());
						}
					}
					else if (type.equals("medium"))
					{
						if (player.getGold() >= medStock[location].getPrice())
						{
							player.changeGold(-medStock[location].getPrice());
							inv.addItem(medStock[location].getName(), medStock[
							            location].getPrice());
						}
					}
					else if (type.equals("heavy"))
					{
						if (player.getGold() >= heavyStock[location].getPrice())
						{
							player.changeGold(-heavyStock[location].getPrice());
							inv.addItem(heavyStock[location].getName(), heavyStock[
							            location].getPrice());
						}
					}
					else
					{
						System.out.println("You don't have enough gold.");
					}
				}
				else
				{
					System.out.println("That item is not sold here.");
				}
			}
		}
	}
	
	public static void enchanter(Player player, Inventory inv)
	{
		
		boolean loop = true;
		
		int totalCost = 0;
		
		if (enchantmentStart < dayCounter)
		{
			if (enchantedItem == null)
			{
				while (loop)
				{
					int cost = 0;
					
					boolean loop2 = true;
					String choice = "";
					
					while (loop2)
					{
						System.out.println("Weapon: " + player.getWeapon().getFullName());
						System.out.println("Armor: " + player.getArmor().getName());
						System.out.println("Gold: " + player.getGold() + "GP");
						
						System.out.print("What do you want enchanted? (weapon/armor) ");
						choice = scan.nextLine();
						
						if (choice.equalsIgnoreCase("weapon"))
						{
							loop2 = false;
							if (player.getWeapon().getEnhancement() == 0)
							{
								totalCost += 2000;
								cost = totalCost;
							}
							else
							{
								int oldPrice = (int)Math.pow(player.getWeapon().getEnhancement(), 2) * 2000;
								totalCost = (int)Math.pow((player.getWeapon().getEnhancement() + 1), 2) * 2000;
								
								cost = totalCost - oldPrice;
							}
							totalCost += player.getWeapon().getBasePrice();
						}
						else if (choice.equalsIgnoreCase("armor"))
						{
							loop2 = false;
							if (player.getArmor().getEnhancement() == 0)
							{
								totalCost += 1000;
								cost = totalCost;
							}
							else
							{
								int oldPrice = (int)Math.pow(player.getArmor().getEnhancement(), 2) * 1000;
								totalCost = (int)Math.pow((player.getArmor().getEnhancement() + 1), 2) * 1000;
								
								cost = totalCost - oldPrice;
							}
							totalCost += player.getArmor().getBasePrice();
						}
						else
						{
							System.out.println("Invalid input");
						}
					}
					
					choice = choice.toLowerCase();
					
					System.out.print("To enchant your " + choice + " it will cost " + cost + "GP (yes/leave) ");
					String input = scan.nextLine();
					if (input.equalsIgnoreCase("help"))
					{
						System.out.println("yes       ----- enchant the " + choice);
						System.out.println("leave     ----- leave the store");
					}
					else if (input.equalsIgnoreCase("yes"))
					{
						String enchantedItemName;
						if (choice.equals("armor"))
						{
							enchantedItemName = player.getArmor().getName();
						}
						else
						{
							enchantedItemName = player.getWeapon().getName();
						}
						
						if (player.getGold() >= cost && enchantedItemName.equals("none") == false && enchantedItemName.equals("unarmed") == false)
						{
							player.changeGold(-cost);
							String itemName;
							if (choice.equals("armor"))
							{
								Armor armor = player.getArmor();
								player.setArmor("none");
								inv.findAndRemove(armor.getName());
								if (armor.getEnhancement() == 0)
								{
									itemName = armor.getName() + " +1";
								}
								else
								{
									int index = armor.getName().indexOf('+');
									int num = Integer.parseInt("" + armor.getName().charAt(index + 1));
									num++;
									itemName = armor.getName().substring(0, armor.getName().length() - 1) + num;
								}
							}
							else
							{
								Weapon weapon = player.getWeapon();
								player.setWeapon("unarmed", 0);
								inv.findAndRemove(weapon.getFullName());
								if (weapon.getEnhancement() == 0)
								{
									itemName = weapon.getName() + " +1";
								}
								else
								{
									itemName = weapon.getName() + " +" + (weapon.getEnhancement() + 1);
								}
							}
							
							enchantedItem = new Item(itemName, 1, totalCost);
							loop = false;
							System.out.println("Enchanter: Ok, I'll have the item for a day. Come back tomorrow and it will be" +
											   "\n           done.");
							enchantmentStart = dayCounter;
						}
						else if (choice.equals("armor") && player.getArmor().getName().equals("none"))
						{
							System.out.println("You cannot enchant your clothes.");
							loop = false;
						}
						else if (choice.equals("weapon") && player.getWeapon().getName().equals("unarmed"))
						{
							System.out.println("You cannot enchant your hands.");
							loop = false;
						}
						else
						{
							System.out.println("You don't have enough gold.");
							loop = false;
						}
					}
					else if (input.equalsIgnoreCase("leave"))
					{
						loop = false;
					}
				}
			}
			else
			{
				System.out.println("Here you go.");
				System.out.println(enchantedItem.getName() + " received!");
				inv.addItem(enchantedItem);
				enchantedItem = null;
			}
		}
		else
		{
			System.out.println("Come back tomorrow and I'll have it.");
		}
	}
	
	public static void tavern(Player player, Inventory inv)
	{
		boolean loop = true;
		String input;
		
		Item[] stock = new Item[5];
		
		stock[0] = new Item(1, "Mead");
		stock[1] = new Item(4, "Wine");
		stock[2] = new Item(10, "Dwarven Rum");
		stock[3] = new Item(22, "Orc Killer");
		stock[4] = new Item(40, "Dwarf Killer");
		
		while (loop == true) {
			
			System.out.println("Tavern stock:");
			for (int index = 0; index < stock.length; index++)
			{
				System.out.println(stock[index].shopStock());
			}
			System.out.println("Gold: " + player.getGold() + "GP");
			System.out.println("What will you buy? ");
			input = scan.nextLine();
			
			if (input.equalsIgnoreCase("help"))
			{
				System.out.println("sell          ----- sell something"
						+ "\n<item name>   ----- buy an item"
						+ "\nleave         ----- leave the shop");
			}
			else if (input.equalsIgnoreCase("leave"))
			{
				loop = false;
			}
			else
			{
				int location = -1;
				for (int index = 0; index < stock.length; index++)
				{
					if (input.equalsIgnoreCase(stock[index].getName()))
					{
						location = index;
					}
				}
				if (location != -1)
				{
					if (player.getGold() >= stock[location].getPrice())
					{
						player.changeGold(-stock[location].getPrice());
						inv.addItem(stock[location].getName(), stock[
						            location].getPrice());
					}
					else
					{
						System.out.println("You don't have enough gold.");
					}
				}
				else
				{
					System.out.println("That item is not sold here.");
				}
			}
		}
	}
	
	public static void mercantile(Player player, Inventory inv)
	{
		boolean loop = true;
		
		System.out.println("\nA Mercantile has set up shop, and is beckoning you to him.");
		System.out.println("\nMercantile: You there! You look like the sort that has lots");
		System.out.println("            of gold! I'm the type who has stuff worth gold!");
		System.out.print("            How about we see what we can do about this? (yes/no) ");
		
		String input;
		
		while (loop)
		{
			input = scan.nextLine();
			
			if (input.equalsIgnoreCase("yes"))
			{
				Dice roll = new Dice();
				final int amountToGenerate = roll.XdY(3, 4);
				
				System.out.println("\nMercantile: Excellent, now let's work on lightening those pockets");
				System.out.println("            of yours...\n");
				
				ArrayList<Item> possibleStock = new ArrayList<Item>();
				possibleStock.add(new Item(296, "Holy Water"));
				possibleStock.add(new Item(945, "Full Potion"));
				possibleStock.add(new Item(1750, "Potion of Haste"));
				possibleStock.add(new Item(1800, "Potion of Ironskin"));
				possibleStock.add(new Item(1400, "Revitalising Potion"));
				possibleStock.add(new Item(2309, "Animal Bane Warhammer"));
				possibleStock.add(new Item(4350, "Breastplate of Spellcasting"));
				possibleStock.add(new Item(4500, "Ring of Frost Resistance"));
				possibleStock.add(new Item(5000, "Spell Scroll"));
				possibleStock.add(new Item(6000, "Lucky Amulet"));
				possibleStock.add(new Item(6500, "Rod of Lightning"));
				possibleStock.add(new Item(8308, "Sword of Agrinon"));
				possibleStock.add(new Item(8550, "Full plate of Absorbtion"));
				possibleStock.add(new Item(16412, "Soulstealing Rapier"));
				
				// Adding accessories of stat increasing
				// Formula: Value = bonus * bonus * 1000
				
				int bonus = roll.dX(3) * 2;
				possibleStock.add(new Item((bonus * bonus * 1000), "Gauntlets of Strength +" + bonus));
				bonus = roll.dX(3) * 2;
				possibleStock.add(new Item((bonus * bonus * 1000), "Gloves of Dexterity +" + bonus));
				bonus = roll.dX(3) * 2;
				possibleStock.add(new Item((bonus * bonus * 1000), "Amulet of Health +" + bonus));
				bonus = roll.dX(3) * 2;
				possibleStock.add(new Item((bonus * bonus * 1000), "Headband of Intellect +" + bonus));
				bonus = roll.dX(3) * 2;
				possibleStock.add(new Item((bonus * bonus * 1000), "Periapt of Wisdom +" + bonus));
				bonus = roll.dX(3) * 2;
				possibleStock.add(new Item((bonus * bonus * 1000), "Cloak of Charisma +" + bonus));
				
				bonus = roll.dX(5);
				possibleStock.add(new Item((bonus * bonus * 2000), "Ring of Protection +" + bonus));
				
				bonus = roll.dX(5);
				possibleStock.add(new Item((bonus * bonus * 1000), "Cloak of Resistance +" + bonus));
				
				
				Item stock[] = new Item[amountToGenerate];
				
				for (int i = 0; i < amountToGenerate; i++)
				{
					int location = roll.dX(possibleStock.size()) - 1;
					stock[i] = possibleStock.get(location);
					possibleStock.remove(location);
				}
				
				// sort the shop stock
				
				int minimum;
				
				for (int i = 0; i < stock.length - 1; i++)
				{
					minimum = i;
					
					for (int j = i + 1; j < stock.length; j++)
					{
						if (stock[j].getPrice() < stock[minimum].getPrice())
						{
							minimum = j;
						}
					}
					Item temp = stock[i];
					stock[i] = stock[minimum];
					stock[minimum] = temp;
				}
				
				// Stock is now sorted
				
				System.out.println("---------------------------------------");
				for (int index = 0; index < stock.length; index++)
				{
					System.out.println(stock[index].shopStock());
				}
				System.out.println("---------------------------------------");
				
				boolean shopLoop = true;
				
				while (shopLoop)
				{
					System.out.println("Gold: " + player.getGold() + "GP");
					System.out.print("What will you buy? ");
					String command = scan.nextLine();
					
					if (command.equalsIgnoreCase("help"))
					{
						System.out.println("-----------------------------------------");
						System.out.println("<item name>         - buy an item");
						System.out.println("leave               - leave the Mercantile's shop");
						System.out.println("-----------------------------------------");
					}
					else if (command.equalsIgnoreCase("leave"))
					{
						shopLoop = false;
						loop = false;
					}
					else
					{
						int location = -1;
						
						for (int index = 0; index < stock.length; index++)
						{
							if (command.equalsIgnoreCase(stock[index].getName()))
							{
								location = index;
							}
						}
						if (location == -1)
						{
							System.out.println("The Mercantile doesn't sell that.");
						}
						else if (player.getGold() >= stock[location].getPrice())
						{
							player.changeGold(-stock[location].getPrice());
							inv.addItem(stock[location].getName(), stock[location].getPrice());
						}
						else
						{
							System.out.println("You don't have enough gold.");
						}
					}
				}
			}
			else if (input.equalsIgnoreCase("no"))
			{
				loop = false;
				System.out.println("\nMercantile: Hmmm... I must have been wrong about you.\n");
			}
			else
			{
				System.out.println("Invalid Input");
			}
		}
	}
}
