import java.util.Scanner;


public class Crypt extends Dungeon{
	
	boolean wandering = true;
	boolean done = false;

	public void crypt(Player player, Magic Magic, Inventory inv)
	{
		int increment = 0;
		
		System.out.println("------------------------------------");
		System.out.println("You have entered the Crypt.");
		System.out.println("Don't stay too long...");
		System.out.println("------------------------------------");
		
		while (wandering)
		{
			done = false;
			
			encounter(player, Magic, inv, increment);
			
			Scanner scan = new Scanner(System.in);
			
			while (!done)
			{
				System.out.println("You are wandering around in the crypt");
				System.out.println("What will you do?");
				String command = scan.nextLine();
				
				if (command.equalsIgnoreCase("help"))
				{
					System.out.println("wander    ----- wander around the crypt");
					System.out.println("look      ----- look around");
					System.out.println("cast      ----- cast a spell (noncombat spells only");
					System.out.println("item      ----- use an item");
					System.out.println("stats     ----- see your stats");
					System.out.println("equip     ----- equip an item");
					System.out.println("inventory ----- see your inventory");
					System.out.println("spellbook ----- see your spellbook");
					System.out.println("leave     ----- leave the crypt");
				}
				else if (command.equalsIgnoreCase("leave"))
				{
					wandering = false;
					done = true;
				}
				else if (command.equalsIgnoreCase("look"))
				{
					System.out.println("You are in a room in the crypt, the air is cold and still, \nand "
							+ "the stench of death is prominent.");
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
				else if (command.equalsIgnoreCase("wander"))
				{
					done = true;
				}
			}
			if (increment < 80)
				increment += 10;
		}
	}
	
	private void encounter(Player player, Magic Magic, Inventory inv, int increment)
	{
		String enemyName = "none";
		int amount = 0;
		int rng = roll.d100() + increment;
		if (rng < 40)
		{
			enemyName = "Skeleton";
			amount = roll.d3();
		}
		else if (rng < 80)
		{
			enemyName = "Zombie";
			amount = roll.d3();
		}
		else if (rng < 90)
		{
			System.out.println(player.getName() + " has found a treasure chest!");
			int trap = roll.d100();
			boolean trapPresent;
			if (trap >= 50)
				trapPresent = true;
			else
				trapPresent = false;
			int trapDC = roll.XdY(5, 6);
			int trapCheck = roll.d20() + ((player.getWis() - 10) / 2);
			if (trapCheck >= trapDC && trapPresent)
			{
				System.out.println("You see a trap on the chest!");
			}
			boolean complete = false;
			while (!complete)
			{
				System.out.print("What will you do? ");
				String input = scan.nextLine();
				if (input.equalsIgnoreCase("help"))
				{
					System.out.println("open      ----- open the chest");
					System.out.println("leave     ----- leave the chest behind");
				}
				else if (input.equalsIgnoreCase("leave"))
				{
					complete = true;
				}
				else if (input.equalsIgnoreCase("open"))
				{
					complete = true;
					System.out.println(player.getName() + " opens the chest!");
					
					if (trapPresent)
					{
						rng = roll.d100();
						if (rng <= 100)
						{
							System.out.println("Poisonous darts shoot from the walls!");
							rng = roll.d8();
							int poisonType = roll.d3();
							String poisonStat;
							if (poisonType == 1)
								poisonStat = "Strength";
							else if (poisonType == 2)
								poisonStat = "Dexterity";
							else
								poisonStat = "Constitution";
							
							boolean wasPoisoned = false;
							
							int poisonDC = roll.XdY(4, 6);
							for (int index = 0; index < rng; index++)
							{
								if (roll.d20() + 18 >= player.getAC())
								{
									System.out.println("The dart hit " + player.getName() + "!");
									int damage = roll.d4() + 1;
									player.meleeDamage(damage);
									System.out.println("Did " + damage + " damage to " + player.getName() + "!");
									if (player.savingThrow(Player.Saves.Fortitude, false, true) < poisonDC)
									{
										System.out.println(player.getName() + " has been poisoned!");
										player.poison(poisonStat, 1, 3, true);
										wasPoisoned = true;
									}
								}
								else
									System.out.println("The dart missed " + player.getName() + "!");
							}
							if (wasPoisoned)
							{
								if (player.savingThrow(Player.Saves.Fortitude, false, true) < poisonDC)
								{
									player.poison(poisonStat, 1, 3, true);
								}
							}
							System.out.println("HP: " + player.getHP() + "/" + player.getHPTotal());
						}
					}
					
					rng = roll.dX(46);
					Item loot;
					if (rng == 1)
						loot = new Item("Potion", 1, 50);
					else if (rng == 2)
						loot = new Item("Mana Potion", 1, 120);
					else if (rng == 3)
						loot = new Item("Greater Mana Potion", 1, 500);
					else if (rng == 4)
						loot = new Item("Greater Potion", 1, 400);
					else if (rng == 5)
						loot = new Item("Major Mana Potion", 1, 800);
					else if (rng == 6)
						loot = new Item("Full Potion", 1, 945);
					else if (rng == 7)
						loot = new Item("Revitalising Potion", 1, 1800);
					else if (rng == 8)
						loot = new Item("Lightning Javelin", 1, 350);
					else if (rng == 9)
						loot = new Item("Oil of Recovery", 1, 110);
					else if (rng == 10)
						loot = new Item("Potion of Str", 1, 185);
					else if (rng == 11)
						loot = new Item("Potion of Int", 1, 190);
					else if (rng == 12)
						loot = new Item("Flame Sword", 1, 2308);
					else if (rng == 13)
						loot = new Item("Power Axe", 1, 8309);
					else if (rng == 14)
						loot = new Item("Smoke Bomb", 1, 300);
					else if (rng == 15)
						loot = new Item("Quarterstaff +1", 1, 2304);
					else if (rng == 16)
						loot = new Item("Ring of Protection +1", 1, 2000);
					else if (rng == 17)
						loot = new Item("Ring of Protection +2", 1, 8000);
					else if (rng == 18)
						loot = new Item("Ring of Protection +3", 1, 18000);
					else if (rng == 19)
						loot = new Item("Ring of Protection +4", 1, 32000);
					else if (rng == 20)
						loot = new Item("Ring of Protection +5", 1, 50000);
					else if (rng == 21)
						loot = new Item("Bracers of Armor +1", 1, 1000);
					else if (rng == 22)
						loot = new Item("Bracers of Armor +2", 1, 4000);
					else if (rng == 23)
						loot = new Item("Cloak of Resistance +1", 1, 1000);
					else if (rng == 24)
						loot = new Item("Cloak of Resistance +2", 1, 4000);
					else if (rng == 25)
					{
						int bonus = roll.d3() * 2;
						loot = new Item("Gauntlets of Strength +" + bonus, 1, ((int)Math.pow(bonus, 2) / 2) * 1000);
					}
					else if (rng == 26)
					{
						int bonus = roll.d3() * 2;
						loot = new Item("Amulet of Health +" + bonus, 1, ((int)Math.pow(bonus, 2) / 2) * 1000);
					}
					else if (rng == 27)
					{
						int bonus = roll.d3() * 2;
						loot = new Item("Gloves of Dexterity +" + bonus, 1, ((int)Math.pow(bonus, 2) / 2) * 1000);
					}
					else if (rng == 28)
					{
						int bonus = roll.d3() * 2;
						loot = new Item("Headband of Intellect +" + bonus, 1, ((int)Math.pow(bonus, 2) / 2) * 1000);
					}
					else if (rng == 29)
					{
						int bonus = roll.d3() * 2;
						loot = new Item("Periapt of Wisdom +" + bonus, 1, ((int)Math.pow(bonus, 2) / 2) * 1000);
					}
					else if (rng == 30)
					{
						int bonus = roll.d3() * 2;
						loot = new Item("Cloak of Charisma +" + bonus, 1, ((int)Math.pow(bonus, 2) / 2) * 1000);
					}
					else if (rng == 31)
					{
						int bonus = roll.dX(5);
						loot = new Item("Padded +" + bonus, 1, (int)Math.pow(bonus, 2) * 1000 + 5);
					}
					else if (rng == 32)
					{
						int bonus = roll.dX(5);
						loot = new Item("Leather +" + bonus, 1, (int)Math.pow(bonus, 2) * 1000 + 10);
					}
					else if (rng == 33)
					{
						int bonus = roll.dX(5);
						loot = new Item("Studded Leather +" + bonus, 1, (int)Math.pow(bonus, 2) * 1000 + 25);
					}
					else if (rng == 34)
					{
						int bonus = roll.dX(5);
						loot = new Item("Chain Shirt +" + bonus, 1, (int)Math.pow(bonus, 2) * 1000 + 100);
					}
					else if (rng == 35)
					{
						int bonus = roll.dX(5);
						loot = new Item("Chainmail +" + bonus, 1, (int)Math.pow(bonus, 2) * 1000 + 150);
					}
					else if (rng == 36)
					{
						int bonus = roll.dX(5);
						loot = new Item("Breastplate +" + bonus, 1, (int)Math.pow(bonus, 2) * 1000 + 200);
					}
					else if (rng == 37)
					{
						int bonus = roll.dX(5);
						loot = new Item("Splint mail +" + bonus, 1, (int)Math.pow(bonus, 2) * 1000 + 200);
					}
					else if (rng == 38)
					{
						int bonus = roll.dX(5);
						loot = new Item("Banded mail +" + bonus, 1, (int)Math.pow(bonus, 2) * 1000 + 250);
					}
					else if (rng == 39)
					{
						int bonus = roll.dX(5);
						loot = new Item("Half-plate +" + bonus, 1, (int)Math.pow(bonus, 2) * 1000 + 600);
					}
					else if (rng == 40)
					{
						int bonus = roll.dX(5);
						loot = new Item("Full plate +" + bonus, 1, (int)Math.pow(bonus, 2) * 1000 + 1500);
					}
					else if (rng == 41)
						loot = new Item("Undead Bane Mace", 1, 2308);
					else if (rng == 42)
						loot = new Item("Freezing Axe", 1, 2309);
					else if (rng == 43)
						loot = new Item("Shocking Rapier", 1, 2306);
					else if (rng == 44)
						loot = new Item("Acidic Quarterstaff",1 , 2304);
					else if (rng == 45)
						loot = new Item("Screaming Warhammer",1 , 8309);
					else
						loot = new Item("Disrupting Warhammer", 1, 8309);
					
					int goldAmount = roll.XdY(10, 100);
					player.changeGold(goldAmount);
					inv.addItem(loot);
					System.out.println(loot.getName() + " was found!");
				}
			}
		}
		else if (rng < 100)
		{
			enemyName = "Mohrg";
			amount = 1;
		}
		else if (rng < 110)
		{
			enemyName = "Mummy";
			amount = 1;
		}
		else if (rng < 120)
		{
			enemyName = "Boneclaw";
			amount = roll.dX(2);
		}
		else if (rng < 130)
		{
			enemyName = "Necromancer";
			amount = 1;
		}
		else if (rng < 140)
		{
			enemyName = "Shadow";
			amount = roll.d4();
		}
		else if (rng < 150)
		{
			enemyName = "Greater Shadow";
			amount = 1;
		}
		else if (rng < 160)
		{
			enemyName = "Necronaut";
			amount = 1;
		}
		else
		{
			enemyName = "Gravecrawler";
			amount = 1;
		}
		
		if (enemyName.equals("none") == false)
		{
			if (amount > 1)
				System.out.println(amount + " " + enemyName + "s approach " + player.getName() + "!");
			for (int index = 0; index < amount; index++)
			{
				boolean escaped = Battle.Combat(player, Magic, inv, enemyName, true);
				if (escaped)
				{
					wandering = false;
					done = true;
					
					break;
				}
			}
		}
	}
}
