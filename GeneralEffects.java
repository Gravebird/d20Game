import java.util.Scanner;

public class GeneralEffects {

	static Scanner scan = new Scanner(System.in);
	static Dice roll = new Dice();
	
	public static void holyWater(Player player)
	{
		boolean done = false;
		String stat = "";
		while (!done)
		{
    		System.out.print("Which ability score should be healed? ");
    		stat = scan.nextLine();
    		if (stat.equalsIgnoreCase("Str") || stat.equalsIgnoreCase("Strength"))
    		{
    			stat = "Strength";
    			done = true;
    		}
    		else if (stat.equalsIgnoreCase("Dex") || stat.equalsIgnoreCase("Dexterity"))
    		{
    			stat = "Dexterity";
    			done = true;
    		}
    		else if (stat.equalsIgnoreCase("Con") || stat.equalsIgnoreCase("Constitution"))
    		{
    			stat = "Constitution";
    			done = true;
    		}
    		else if (stat.equalsIgnoreCase("Int") || stat.equalsIgnoreCase("Intelligence"))
    		{
    			stat = "Intelligence";
    			done = true;
    		}
    		else if (stat.equalsIgnoreCase("Wis") || stat.equalsIgnoreCase("Wisdom"))
    		{
    			stat = "Wisdom";
    			done = true;
    		}
    		else if (stat.equalsIgnoreCase("Cha") || stat.equalsIgnoreCase("Charisma"))
    		{
    			stat = "Charisma";
    			done = true;
    		}
    		else
    			System.out.println("Invalid Input");
		}
		int rng = roll.XdY(2, 3);
		player.cureAbilityDamage(stat, rng);
	}
	
	public static void lightningJavelin(Player player, Enemy enemy)
	{
		int damage = roll.XdY(5, 6);
		
		if (enemy.reflexSave() >= 14)
		{
			System.out.println("The " + enemy.getName() + " rolled away from the bolt!");
			damage = damage / 2;
		}
		enemy.damage(damage, player, "electricity");
	}
	
	public static void enemyCharge(Player player, Enemy enemy)
	{
		System.out.println("The " + enemy.getName() + " charges toward "
				+ player.getName() + "!");
		boolean loop = true;
		String input = null;
		
		while (loop)
		{
			System.out.print("What will you do? (stand ground/dodge/attack) ");
			input = scan.nextLine();
			if (input.equalsIgnoreCase("stand ground")
					|| input.equalsIgnoreCase("dodge")
					|| input.equalsIgnoreCase("attack"))
			{
				loop = false;
			}
			else
			{
				System.out.println("Invalid Input");
			}
		}
		
		if (input.equalsIgnoreCase("stand ground"))
		{
			System.out.println(player.getName() + " stands and waits for the impact!");
			if (player.grappleRoll() > enemy.grappleRoll() + 2)
			{
				System.out.println(player.getName() + " throws the " + enemy.getName() +
						" to the side!");
				int damage = player.unarmedAttack();
				enemy.meleeDamage(damage, Battle.unarmedAttack, player, false);
			}
			else
			{
				System.out.println("The " + enemy.getName() + " completely overpowers "
						+ player.getName() + "!");
				enemy.nextHitWillCrit();
				int damage = enemy.attack(player);
				player.meleeDamage(damage);
			}
		}
		else if (input.equalsIgnoreCase("dodge"))
		{
			System.out.println(player.getName() + " prepares to dodge at the last minute!");
			if (player.savingThrow(Player.Saves.Reflex, false, false) > enemy.grappleRoll() + 2)
			{
				System.out.println(player.getName() + " dodged the " + enemy.getName() + "!");
			}
			else
			{
				System.out.println("The " + enemy.getName() + " hit "
						+ player.getName() + "!");
				int damage = enemy.attack(player);
				System.out.println("The " + enemy.getName() + " did " + damage
						+ " damage to " + player.getName() + "!");
				player.meleeDamage(damage);
			}
		}
		else
		{
			System.out.println(player.getName() + " prepares to attack the "
					+ enemy.getName() + " just before impact!");
			if (player.attackRoll(enemy.getType()) >= enemy.getAC() - 2)
			{
				System.out.println(player.getName() + " hit the " + enemy.getName() + "!");
				player.getWeapon().damage(player, enemy, 0);
			}
			if (enemy.getHP() > 0)
			{
				System.out.println("The " + enemy.getName() + " hits " + player.getName() + "!");
				int damage = enemy.attack(player);
				System.out.println("The " + enemy.getName() + " did " + damage
						+ " damage to " + player.getName() + "!");
				player.meleeDamage(damage);
			}
			else
			{
				System.out.println("The " + enemy.getName() + " collapses just before "
						+ " hitting " + player.getName() + "!");
			}
		}
	}
	
	public static void alcohol(Player player, int potency)
	{
		/*
		 * 1: Strength
		 * 2: Constitution
		 * 3: Charisma
		 */
		int benefit = roll.dX(3);
		
		int benefitAmount = roll.dX(potency);
		if (player.isAlcoholic() && roll.d100() > 50)
		{
			benefitAmount++;
		}
		
		/*
		 * 1: Dexterity
		 * 2: Intelligence
		 * 3: Wisdom
		 */
		int suffer = roll.dX(3);
		
		int sufferAmount = -roll.dX(potency);
		if (player.isAlcoholic() && roll.d100() > 50)
		{
			sufferAmount--;
		}
		if (player.hasDrunkenMaster())
		{
			sufferAmount++;
			if (sufferAmount >= 0)
			{
				sufferAmount = -1;
			}
		}
		
		final Player.Stat Bstat;
		final Player.Stat Sstat;
		
		String benefitMessage;
		String sufferMessage;
		
		if (benefit == 1)
		{
			Bstat = Player.Stat.Str;
			benefitMessage = "Strength";
		}
		else if (benefit == 2)
		{
			Bstat = Player.Stat.Con;
			benefitMessage = "Constitution";
		}
		else
		{
			Bstat = Player.Stat.Cha;
			benefitMessage = "Charisma";
		}
		
		if (suffer == 1)
		{
			Sstat = Player.Stat.Dex;
			sufferMessage = "Dexterity";
		}
		else if (suffer == 2)
		{
			Sstat = Player.Stat.Int;
			sufferMessage = "Intelligence";
		}
		else
		{
			Sstat = Player.Stat.Wis;
			sufferMessage = "Wisdom";
		}
		
		System.out.println(benefitMessage + " increased by " + benefitAmount + "!");
		player.addAlcoholBonus(Bstat, benefitAmount);
		System.out.println(sufferMessage + " decreased by " + Math.abs(sufferAmount) + "!");
		player.addAlcoholBonus(Sstat, sufferAmount);
	}
	
	public static void statIncreaseByOne(Player player)
	{
		System.out.println("\nStr\nDex\nCon\nInt\nWis\nCha\n");
		boolean loop = true;
		
		while (loop)
		{
			loop = false;
			String input = scan.nextLine();
			if (input.equalsIgnoreCase("Str") || input.equalsIgnoreCase("Strength"))
			{
				player.changeStat("Strength", 1);
			}
			else if (input.equalsIgnoreCase("Dex") || input.equalsIgnoreCase("Dexterity"))
			{
				player.changeStat("Dexterity", 1);
			}
			else if (input.equalsIgnoreCase("Con") || input.equalsIgnoreCase("Constitution"))
			{
				player.changeStat("Constitution", 1);
			}
			else if (input.equalsIgnoreCase("Int") || input.equalsIgnoreCase("Intelligence"))
			{
				player.changeStat("Intelligence", 1);
			}
			else if (input.equalsIgnoreCase("Wis") || input.equalsIgnoreCase("Wisdom"))
			{
				player.changeStat("Wisdom", 1);
			}
			else if (input.equalsIgnoreCase("Cha") || input.equalsIgnoreCase("Charisma"))
			{
				player.changeStat("Charisma", 1);
			}
			else
			{
				System.out.println("Invalid Input");
				loop = true;
			}
		}
	}
	
	public static int rodOfLightning()
	{
		return roll.XdY(10, 6);
	}
}
