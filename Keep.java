
public class Keep extends Dungeon{

	static boolean lootTaken = false;
	
	public void keep(Player player, Magic Magic, Inventory inv)
	{
		// Levels 2 - 4
		
		boolean done = false;
		boolean room1 = false;
		boolean room2 = false;
		boolean skeletons = true;
		boolean zombies = true;
		
		System.out.println("------------------------------------");
		System.out.println("You have entered the Abandoned Keep.");
		System.out.println("Suggested Level: 2 - 4");
		System.out.println("------------------------------------");
		System.out.println("\nThere are two DOORs in this room, one of which is unlocked.");
		
		while (!done)
		{
			System.out.println("What will you do?");
			String command = scan.nextLine();
			
			if (command.equalsIgnoreCase("help"))
			{
				help();
				System.out.println("leave     ----- leave the dungeon");
			}
			else if (command.equalsIgnoreCase("inventory"))
			{
				inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
			}
			else if (command.equalsIgnoreCase("spellbook"))
			{
				Magic.getSpells(false);
			}
			else if (command.equalsIgnoreCase("leave"))
			{
				done = true;
			}
			else if (command.equalsIgnoreCase("look"))
			{
				System.out.println("There are two doors in this room, one of which is unlocked.");
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
			else if (command.equalsIgnoreCase("take"))
			{
				System.out.print("Take what?");
				command = scan.nextLine();
				if (command.equalsIgnoreCase("rock") || command.equalsIgnoreCase("stone"))
				{
					inv.addItem("rock", 0);
				}
				else
				{
					System.out.println("You cannot find any.");
				}
			}
			else if (command.equalsIgnoreCase("go"))
			{
				System.out.print("Where would you like to go? ");
				String input = scan.nextLine();
				
				if (input.equalsIgnoreCase("door"))
				{
					System.out.print("Which door (unlocked/locked)? ");
					input = scan.nextLine();
					
					if (input.equalsIgnoreCase("unlocked"))
					{
						room1 = true;
						if (skeletons)
						{
							boolean escaped = true;
							int numSkeletons = roll.d4();
							System.out.println("You are standing in a hallway. There are only two exits, the entrance and forward.");
							String skeleton = "skeleton";
							String is = "is";
							if (numSkeletons > 1)
							{
								skeleton += "s";
								is = "are";
							}
							System.out.println("There " + is + " " + numSkeletons + " " + skeleton + " in this room!");
							for (int index = 0; index < numSkeletons; index++)
							{
								escaped = Battle.Combat(player, Magic, inv, "Skeleton", true);
								if (escaped)
									break;
							}
							if (escaped)
							{
								room1 = false;
							}
							else
							{
								skeletons = false;
							}
						}
						while (room1)
						{
							System.out.println("You are standing in a hallway. There are rocks scattered across the room,\nThere are only two exits, the ENTRANCE and FORWARD.");
							System.out.println("What will you do?");
							String command1 = scan.nextLine();
							
							if (command1.equalsIgnoreCase("help"))
							{
								help();
							}
							else if (command1.equalsIgnoreCase("inventory"))
							{
								inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(),
										player.getArmor());
							}
							else if (command1.equalsIgnoreCase("spellbook"))
							{
								Magic.getSpells(false);
							}
							else if (command1.equalsIgnoreCase("look"))
							{
								System.out.println("You are standing in a hallway. There are rocks scattered across the room,\nThere are only two exits, the ENTRANCE and FORWARD.");
							}
							else if (command1.equalsIgnoreCase("item"))
							{
								Item.useItem(player, inv, Magic);
							}
							else if (command1.equalsIgnoreCase("cast"))
							{
								useSpell(player, Magic);
							}
							else if (command1.equalsIgnoreCase("stats"))
							{
								System.out.println(player.getStats());
							}
							else if (command1.equalsIgnoreCase("equip"))
							{
								inv.equipItem(player, false);
							}
							else if (command1.equalsIgnoreCase("take"))
							{
								System.out.print("Take what?");
								command1 = scan.nextLine();
								if (command1.equalsIgnoreCase("rock") || command1.equalsIgnoreCase("stone"))
								{
									inv.addItem("rock", 0);
								}
								else
								{
									System.out.println("You cannot find any.");
								}
							}
							else if (command1.equalsIgnoreCase("leave"))
							{
								room1 = false;
							}
							else if (command1.equalsIgnoreCase("go"))
							{
								System.out.print("Where would you like to go? ");
								command1 = scan.nextLine();
								if (command1.equalsIgnoreCase("entrance") || command1.equalsIgnoreCase("back"))
								{
									room1 = false;
									System.out.println("There are two doors in this room, one of which is unlocked.");
								}
								else if (command1.equalsIgnoreCase("forward"))
								{
									room2 = true;
									if (zombies)
									{
										boolean escaped = true;
										int numZombies = roll.dX(2) + 1;
										System.out.println("There is a KEY on the floor in the "
												+ "center of this room."
														+ "\nThe only way out is BACK");
										System.out.println("There are " + numZombies + " zombies in this room!");
										for (int index = 0; index < numZombies; index++)
										{
											escaped = Battle.Combat(player, Magic, inv, "Zombie", true);
											if (escaped)
												break;
										}
										if (escaped)
										{
											room2 = false;
										}
										else
										{
											zombies = false;
										}
									}
									while (room2)
									{
										System.out.println("What will you do?");
										String command2 = scan.nextLine();
										
										if (command2.equalsIgnoreCase("help"))
										{
											help();
											System.out.println("take      ----- take an item");
										}
										else if (command2.equalsIgnoreCase("inventory"))
										{
											inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(),
													player.getArmor());
										}
										else if (command2.equalsIgnoreCase("spellbook"))
										{
											Magic.getSpells(false);
										}
										else if (command2.equalsIgnoreCase("look"))
										{
											if (inv.hasItem("Key"))
											{
												System.out.println("There is nothing of interest in this room.");
											}
											else
											{
												System.out.println("There is a KEY on the floor in the center of this room."
														+ "\nThe only way out is BACK");
											}
										}
										else if (command2.equalsIgnoreCase("take"))
										{
											System.out.println("Take what?");
											command2 = scan.nextLine();
											
											if (command2.equalsIgnoreCase("Key")
													&& inv.hasItem("Key") == false)
											{
												inv.addItem("Key", 0);
											}
											else
											{
												System.out.println("That item does not exist.");
											}
										}
										else if (command2.equalsIgnoreCase("item"))
										{
											Item.useItem(player, inv, Magic);
										}
										else if (command2.equalsIgnoreCase("cast"))
										{
											useSpell(player, Magic);
										}
										else if (command2.equalsIgnoreCase("stats"))
										{
											System.out.println(player.getStats());
										}
										else if (command2.equalsIgnoreCase("equip"))
										{
											inv.equipItem(player, false);
										}
										else if (command2.equalsIgnoreCase("take"))
										{
											System.out.print("Take what?");
											command2 = scan.nextLine();
											if (command2.equalsIgnoreCase("rock") || command2.equalsIgnoreCase("stone"))
											{
												inv.addItem("rock", 0);
											}
											else
											{
												System.out.println("You cannot find any.");
											}
										}
										else if (command2.equalsIgnoreCase("leave"))
										{
											room2 = false;
										}
										else if (command2.equalsIgnoreCase("go"))
										{
											System.out.print("Where would you like to go? ");
											command2 = scan.nextLine();
											if (command2.equalsIgnoreCase("hall") || command2.equalsIgnoreCase("hallway")
													|| command2.equalsIgnoreCase("back"))
											{
												room2 = false;
											}
										}
									}
								}
								else
								{
									System.out.println("You can't go there.");
								}
							}
						}
					}
					else if (input.equalsIgnoreCase("locked"))
					{
						if (inv.hasItem("Key"))
						{
							boolean escaped = true;
							System.out.println("Door has been unlocked.");
							inv.questRemoval("Key", 1);
							escaped = Battle.Combat(player, Magic, inv, "Owlbear Skeleton", true);
							
							if (!escaped)
							{
								player.changeGold(roll.XdY(5, 100));
								player.addXP(roll.XdY(5, 100));
								
								if (!lootTaken)
								{
									inv.addItem("Amulet of Hate", 3874);
									lootTaken = true;
								}
								done = true;
							}
							
						}
						else
						{
							System.out.println("The door is locked.");
						}
					}
					else
					{
						System.out.println("You can't go there.");
					}
				}
			}
		}
	}
}
