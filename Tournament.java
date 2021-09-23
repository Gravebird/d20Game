import java.util.Scanner;

public class Tournament {

	private static int winnings;
	private static int nextFight = 0;
	
	
	public static void arena(Player player, Magic mgk, Inventory inv, int dayCounter)
	{
		Scanner scan = new Scanner(System.in);
		
		int amount = 0;
		
		System.out.println("\nYou approach the arena\n");
		
		if (dayCounter >= nextFight)
		{
			if (player.getHP() == player.getHPTotal())
			{
				String enemyName;
				String input;
				
				if (nextFight == 0)
				{
					enemyName = "Todd";
					winnings = 100;
					amount = 1;
				}
				else if (nextFight == 5)
				{
					enemyName = "Wolf";
					winnings = 200;
					amount = 1;
				}
				else if (nextFight == 10)
				{
					enemyName = "Zombie";
					winnings = 300;
					amount = 4;
				}
				else if (nextFight == 15)
				{
					enemyName = "Bear";
					winnings = 400;
					amount = 2;
				}
				else if (nextFight == 20)
				{
					enemyName = "Large Viper Snake";
					winnings = 600;
					amount = 3;
				}
				else if (nextFight == 25)
				{
					enemyName = "Gladiator";
					winnings = 750;
					amount = 2;
				}
				else if (nextFight == 30)
				{
					enemyName = "Todd";
					winnings = 800;
					amount = 1;
				}
				else if (nextFight == 35)
				{
					enemyName = "Skeleton";
					winnings = 200;
					amount = 10;
				}
				else if (nextFight == 40)
				{
					enemyName = "Mummy";
					winnings = 900;
					amount = 1;
				}
				else if (nextFight == 45)
				{
					enemyName = "Dire Rat";
					winnings = 600;
					amount = 6;
				}
				else if (nextFight == 50)
				{
					enemyName = "Dire Bear";
					winnings = 1000;
					amount = 1;
				}
				else if (nextFight == 55)
				{
					enemyName = "Warlock";
					winnings = 1000;
					amount = 1;
				}
				else if (nextFight == 60)
				{
					enemyName = "Gladiator";
					winnings = 600;
					amount = 10;
				}
				else if (nextFight == 65)
				{
					enemyName = "Bison";
					winnings = 800;
					amount = 2;
				}
				else if (nextFight == 70)
				{
					enemyName = "Todd";
					winnings = 1500;
					amount = 1;
				}
				else if (nextFight == 75)
				{
					enemyName = "Chraal";
					winnings = 1600;
					amount = 1;
				}
				else if (nextFight == 80)
				{
					enemyName = "Troll";
					winnings = 1650;
					amount = 2;
				}
				else if (nextFight == 85)
				{
					enemyName = "Champion";
					winnings = 2000;
					amount = 1;
				}
				else if (nextFight == 90)
				{
					enemyName = "Dark Lord";
					winnings = 3000;
					amount = 1;
				}
				else
				{
					enemyName = "ERROR: No more enemies";
					winnings = 0;
					amount = 1;
				}
				
				
				System.out.print("Guard: Welcome to the arena! Are you here for a match?(yes/no) ");
				input = scan.nextLine();
				
				if (input.equalsIgnoreCase("yes"))
				{
					System.out.print("Guard: Excellent! Your opponent will be ");
					if (enemyName.equals("Todd"))
						System.out.println(enemyName);
					else if (enemyName.equals("Champion") || enemyName.equals("Dark Lord"))
						System.out.println("the " + enemyName);
					else if (amount == 1)
						System.out.println("a " + enemyName);
					else
						System.out.println(amount + " " + enemyName + "s");
					System.out.println("       and the winnings you can earn will be " + winnings + "GP");
					System.out.println("       not to mention any loot that it happens to drop");
					System.out.print("       Are you still ready to fight?(yes/no) ");
					input = scan.nextLine();
					
					if (input.equalsIgnoreCase("yes"))
					{
						for (int index = 0; index < amount; index++)
						{
							Battle.Combat(player, mgk, inv, enemyName, false);
						}
						
						System.out.println("Guard: Amazing! Here is your reward");
						player.changeGold(winnings);
						nextFight += 5;
					}
					else
					{
						System.out.println("Guard: Well that's too bad.");
					}
				}
				else
				{
					System.out.println("Guard: Well that's too bad.");
				}
			}
			else
			{
				System.out.println("Guard: It wouldn't be very fun for the people to");
				System.out.println("       watch a dying man fight in the arena. Go ");
				System.out.println("       rest until your healed and we'll talk.");
			}
		}
		else
		{
			System.out.println("Guard: We have no fights scheduled today, the");
			System.out.println("       next fight will be in " + (nextFight - dayCounter) + " days.");
		}
	}
}
