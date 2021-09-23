import java.util.Scanner;


public class Battle {
	
	static Weapon forceAttack = new Weapon("ERROR", 0);
	static Weapon unarmedAttack = new Weapon("unarmed", 0);
	
	static boolean rage = false;
	public static int bleedingWound = 0;
	static int strBuff = 0;
	static int intBuff = 0;
	static int haste = 0;
	static boolean ignite = false;
	static boolean hammer = false;
	static boolean cloakOfFire = false;
	static boolean polymorph = false;
	static int inferno = 0;
	static int blizzard = 0;
	static int avalancheDC = 0;
	static int deadlyLaharDC = 0;
	static int infernoDC = 0;
	static int blizzardDC = 0;
	static int deadlyLahar = 0;
	static boolean iceArmor = false;
	static boolean playerWasHit = false;
	static boolean poison = false;
	static boolean holdPerson = false;
	static boolean holdMonster = false;
	
	public static String lastSpell = "";
	
	static int roundCounter;
	static int roundsToEvent;
	
	static boolean encounteredShadowCreature = false;
	
	static Dice roll = new Dice();
	
	public static boolean Combat(Player player, Magic Magic, Inventory inv,
			String enemyName, boolean canEscape)
	{
		roundCounter = 1;
		roundsToEvent = 1;
		
		Scanner scan = new Scanner(System.in);
		String battleInput = "";
		String spell = "";
		int frozen = 0;
		int crushingPrison = 0;
		
		boolean mageArmor = false;
		boolean counterHit;
		boolean pinned = false;
		boolean trueStrike = false;
		boolean avalanche = false;
		boolean weakToFrost = false;
		int drowning = 0;
		
		int drownDC = 0;
		
		boolean escaped = false;
		
		lastSpell = "";
		
		Enemy enemy = new Enemy(enemyName, player);
		SpellEffects mgk = new SpellEffects();
		
		enemyName = enemyName.substring(0, 1).toUpperCase() + 
				enemyName.substring(1, enemyName.length());
		System.out.println(enemy.toString(player.getName()));
		
		if (enemyName.equals("Ripper"))
		{
			if (player.savingThrow(Player.Saves.Will, false, false) < enemy.getDC())
			{
				System.out.println(player.getName() + " inhales the pheremones from the " + enemyName + "!");
				player.poison("Wisdom", 4, 1, false);
			}
		}
		else if (enemyName.equals("Nightwalker"))
		{
			System.out.println(player.getName() + " is shocked at the sight of the " + enemy.getName() + "!");
			
			if (player.savingThrow(Player.Saves.Will, true, false) < enemy.getDC())
			{
				System.out.println(player.getName() + " is frozen in fear!");
				
				int duration = roll.d8();
				
				for (int index = 0; index < duration; index++)
				{
					player.meleeDamage(enemy.attack(player));
					player.checkDeath();
				}
			}
		}
		
		while (enemy.getHP() > 0)
		    {
				counterHit = false;
				System.out.println(player.getStats());
				System.out.println(enemyName + " HP: " + enemy.getHP());
		        System.out.print("What will you do? ");
		        battleInput = scan.nextLine();
		        if (battleInput.equalsIgnoreCase("help"))
		        	System.out.println("attack    ----- attack "
		        			+ "the enemy.\ncast      ----- cast a "
		        			+ "spell at the enemy.\nspellbook "
		        			+ "----- see your spellbook.\ninventory"
		        			+ " ----- see your inventory.\nitem "
		        			+ "     ----- use an item.\nflee      "
		        			+ "----- run away.\nequip     ----- equip a "
		        			+ "weapon\ngrapple   ----- attempt to start a grapple"
		        			+ " with the enemy");
		        else if (battleInput.equalsIgnoreCase("spellbook"))
		        {
		        	Magic.getSpells(false);
		        }
		        else if (battleInput.equalsIgnoreCase("equip"))
		        {
		        	inv.equipItem(player, true);
		        }
		        else if (battleInput.equalsIgnoreCase("inventory"))
		        {
		        	inv.getInventory(player.getGold(), 
		        			player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
		        }
		        else if (battleInput.equalsIgnoreCase("testingCheatS") && roundCounter == 1)
		        {
		        	if (!Game.cheater)
		        		Game.cheater = true;
		        	
		        	System.out.println("What will you do?");
		        	boolean loop = true;
		        	while (loop)
		        	{
		        		String cheat = scan.nextLine();
		        		if (cheat.equals("testDamageType"))
		        		{
		        			loop = false;
		        			System.out.print("Damage type: ");
		        			String type = scan.nextLine();
		        			System.out.print("Amount: ");
		        			int damageAmount;
		        			if (scan.hasNextInt())
		        			{
		        				damageAmount = scan.nextInt();
		        				enemy.damage(damageAmount, player, type);
		        				scan.nextLine();
		        			}
		        			else
		        			{
		        				loop = false;
		        				System.out.println("Invalid Input");
		        			}
		        		}
		        	}
		        }
		        else if (battleInput.equalsIgnoreCase("flee"))
		        {
		        	if (canEscape)
		        	{
		        		if (player.escape() >= enemy.blockEscape())
			        	{
			        		enemy.setHP(0);
			        	}
			        	else
			        	{
			        		System.out.println("Couldn't get away!");
			        		counterHit = true;
			        	}
		        	}
		        	else
		        	{
		        		System.out.println("You cannot escape from here.");
		        	}
		        }
		        else if (battleInput.equalsIgnoreCase("rage"))
		        {
		        	if (player.canRage())
		        	{
		        		if (player.getMP() >= 1)
		        		{
		        			if (rage)
			        		{
			        			System.out.println("It would have no effect");
			        		}
			        		else
			        		{
			        			rage = true;
				        		System.out.println(player.getName() + " has entered "
				        				+ "a rage!");
				        		player.rage();
				        		player.changeMP(-1);
				        		counterHit = true;
			        		}
		        		}
		        		else
		        		{
		        			System.out.println("You don't have enough MP.");
		        		}
		        		
		        	}
		        	else
		        	{
		        		System.out.println("You can't do this yet");
		        	}
		        }
		        else if (battleInput.equalsIgnoreCase("cast"))
		        {
		        	if (!rage)
		        	{
		        		Magic.getSpells(false);
			        	System.out.println("What will you cast? ");
			        	battleInput = scan.nextLine();
			        	
			        	// Checking to see if the player entered a spell code
			        	try
			        	{
			        		int temp = Integer.parseInt(battleInput);
			        		// Will only get here if the input is an integer
			        		
			        		battleInput = Magic.getSpellFromCode(temp);
			        	}
			        	catch (NumberFormatException e)
			        	{
			        		// Will get here if the input is not an integer
			        	}
			        	
			        	spell = Magic.useSpell(battleInput,
			        			player.getMP());
			        	int rng = player.rollSpellFailure();
			        	
			        	if (spell.equals("none") == false && (rng <= player.getArmor().getSpellFailure() && (spell.equals("Cloak of Fire") || spell.equals("Fireball") || spell.equals("Polymorph")) == false))
			        	{
			        		System.out.println("Your armor interfered with the spell!");
			        		counterHit = true;
			        	}
			        	else if (spell.equalsIgnoreCase("Burning Hands"))
			        	{
			        		if (enemy.reflexSave() >= 
			        				player.getSpellDC(
			        						Magic.findSpell("Burning Hands").
			        						getCost()))
			        		{
			        			System.out.println(enemyName + " rolled "
			        					+ "away from the spell!");
			        			enemy.damage((mgk.burningHands(player) / 2), player, "fire");
			        		}
			        		else
			        		{
			        			enemy.damage(mgk.burningHands(player), player, "fire");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equalsIgnoreCase
			        			("Magic Missile"))
			        	{
			        		enemy.damage(mgk.magicMissile(player), player, "force");
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Ray of Frost"))
			        	{
			        		if (player.touchSpellHit(enemy.getType()) >= enemy.getTouchAC())
			        		{
			        			System.out.println("The ray hit!");
			        			enemy.damage(mgk.rayOfFrost(player, weakToFrost), player, "cold");
			        		}
			        		else
			        		{
			        			System.out.println("The ray missed!");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Scorching Blast"))
			        	{
			        		if (enemy.reflexSave() >=
			        				player.getSpellDC(Magic.findSpell(
			        						"Scorching Blast").getCost()))
			        		{
			        			System.out.println(enemyName + " rolled away from "
			        					+ "the spell!");
			        			enemy.damage(mgk.scorchingBlast(player) / 2, player, "fire");
			        		}
			        		else
			        		{
			        			enemy.damage(mgk.scorchingBlast(player), player, "fire");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Spirit Sword"))
			        	{
			        		if (!hammer)
			        		{
			        			enemy.meleeDamage(mgk.spiritSword(player), forceAttack, player, true);
			        		}
			        		else
			        		{
			        			enemy.meleeDamage(mgk.spiritSword(player), forceAttack, player, true);
			        			enemy.damage(mgk.hammer(player), player, "force");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Ice Spike"))
			        	{
			        		if (player.touchSpellHit(enemy.getType()) >= enemy.getTouchAC())
			        		{
			        			System.out.println("The spike hit!");
			        			enemy.damage(mgk.iceSpike(player, weakToFrost), player, "cold");
			        		}
			        		else
			        		{
			        			System.out.println("The spike missed!");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Flash Freeze"))
			        	{
			        		if (enemy.reflexSave() >= player.getSpellDC(Magic.
			        				findSpell("Flash Freeze").getCost()))
			        		{
			        			System.out.println(enemyName + " rolled away "
			        					+ "from the spell!");
			        			enemy.damage(mgk.flashFreeze(player) / 2, player, "cold");
			        			counterHit = true;
			        		}
			        		else
			        		{
			        			enemy.damage(mgk.flashFreeze(player), player, "cold");
			        			System.out.println(enemyName + " is partially "
			        					+ "frozen! It can't attack!");
			        			counterHit = false;
			        		}
			        	}
			        	else if (spell.equals("Lightning Bolt"))
			        	{
			        		if (enemy.reflexSave() >= player.getSpellDC(Magic.
			        				findSpell("Lightning Bolt").getCost()))
			        		{
			        			System.out.println(enemyName + " rolled away "
			        					+ "from the spell!");
			        			enemy.damage(mgk.lightningBolt(player) / 2, player, "electricity");
			        		}
			        		else
			        		{
			        			enemy.damage(mgk.lightningBolt(player), player, "electricity");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Polymorph"))
			        	{
			        		if (polymorph)
			        		{
			        			System.out.println("You cannot cast this again "
			        					+ "right now");
			        			spell = null;
			        		}
			        		else
			        		{
			        			polymorph = true;
				        		player.polymorph();
				        		counterHit = true;
			        		}
			        	}
			        	else if (spell.equals("Dimension Door"))
			        	{
			        		if (canEscape)
			        		{
			        			enemy.setHP(0);
			        		}
			        		else
			        		{
			        			System.out.println("You can't escape from here!");
			        			spell = null;
			        		}
			        	}
			        	else if (spell.equals("Faerie Fire"))
			        	{
			        		enemy.changeAC(-2, true);
			        		System.out.println("The enemy is easier to hit!");
			        	}
			        	else if (spell.equals("Mage Armor"))
			        	{
			        		if (mageArmor || player.hasMageArmor)
			        		{
			        			System.out.println("You cannot cast this again "
			        					+ "right now");
			        			spell = null;
			        		}
			        		else
			        		{
			        			mageArmor = true;
			        			player.mageArmor(false);
			        			counterHit = true;
			        		}
			        	}
			        	else if (spell.equals("Extended Mage Armor"))
			        	{
			        		if (mageArmor || player.hasMageArmor)
			        		{
			        			System.out.println("You cannot cast this again right now");
			        			spell = null;
			        		}
			        		else
			        		{
			        			mageArmor = true;
			        			player.mageArmor(true);
			        			counterHit = true;
			        		}
			        	}
			        	else if(spell.equals("Cloak of Fire"))
			        	{
			        		if (cloakOfFire)
			        		{
			        			System.out.println("You cannot cast this again "
			        					+ "right now");
			        		}
			        		else
			        		{
			        			System.out.println("Flames roar up from the "
			        					+ "ground around you, but they don't hurt "
			        					+ "you");
			        			cloakOfFire = true;
			        			counterHit = true;
			        		}
			        	}
			        	else if (spell.equals("Bestow Curse"))
			        	{
			        		enemy.lowerDamage(4);
			        		enemy.changeAC(-4, true);
			        		enemy.lowerHit(4);
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Slay Living"))
			        	{
			        		if (enemy.getType().equals("Undead") || enemy.getType().equals("Construct"))
			        		{
			        			System.out.println("Nothing happened!");
			        		}
			        		else
			        		{
			        			if (enemy.fortSave() >= 
				        				player.getSpellDC(Magic.getCost(spell)))
				        		{
				        			System.out.println(enemyName + " resisted the "
				        					+ "spell!");
				        			counterHit = true;
				        		}
				        		else
				        		{
				        			enemy.setHP(0);
				        		}
			        		}
			        	}
			        	else if (spell.equals("Avasculate"))
			        	{
			        		if (player.touchSpellHit(enemy.getType()) >= enemy.getTouchAC())
			        		{
			        			System.out.println("The ray hit!");
			        			System.out.println(enemyName + " lost "
			        					+ "half its HP!");
			        			if (enemy.fortSave() >=
			        					player.getSpellDC(Magic.getCost(spell)))
			        			{
			        				counterHit = true;
			        			}
			        			else
			        			{
			        				System.out.println(enemyName + " cannot do "
			        						+ "anything!");
			        			}
			        			enemy.setHP(enemy.getHP() / 2);
			        		}
			        		else
			        		{
			        			System.out.println("The ray missed!");
			        			counterHit = true;
			        		}
			        	}
			        	else if (spell.equals("Fireball"))
			        	{
			        		if (enemy.reflexSave() >= player.getSpellDC(
			        				Magic.getCost(spell)))
			        				{
			        					System.out.println(enemyName + " rolled away "
			        							+ "from the spell!");
			        					enemy.damage(mgk.fireball(player) / 2, player, "fire");
			        				}
			        		else
			        		{
			        			enemy.damage(mgk.fireball(player), player, "fire");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Arrow of Force"))
			        	{
			        		enemy.damage(mgk.arrowOfForce(player), player, "force");
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Freezing Ray"))
			        	{
			        		if (player.touchSpellHit(enemy.getType()) >= enemy.getTouchAC())
			        		{
			        			System.out.println("The ray hit!");
			        			enemy.damage(mgk.freezingRay(player, weakToFrost), player, "cold");
			        			if (enemyName.equals("Boneclaw") == false
			        					&& enemyName.equals("Shadow") == false
			        					&& enemyName.equals("Greater Shadow") == false
			        					&& enemyName.equals("Joystealer") == false)
			        			{
			        				if (enemy.fortSave() >= player.getSpellDC(
				        					Magic.getCost(spell)))
				        			{
				        				System.out.println(enemyName + " resisted "
				        						+ "the spell");
				        			}
				        			else
				        			{
				        				System.out.println(enemyName + " is frozen "
				        						+ "solid! It can't attack!");
				        				frozen += mgk.spellDuration(3) + 1;
				        			}
			        			}
			        		}
			        		else
			        		{
			        			System.out.println("The ray missed!");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Hold Person") || spell.equals("Hold Monster"))
			        	{
			        		if (enemy.getType().equals("Undead") || (spell.equals("Hold Person") && enemy.getType().equals("Humanoid") == false))
			        		{
			        			System.out.println("There was no effect!");
			        			counterHit = true;
			        		}
			        		else
			        		{
			        			if (holdPerson || holdMonster)
				        		{
				        			System.out.println("It would have no effect.");
				        			spell = null;
				        		}
				        		else
				        		{
				        			if (enemy.willSave() < player.getSpellDC(
					        				Magic.getCost(spell)))
					        		{
					        			System.out.println(enemyName + " is paralyzed!");
					        			if (spell.equals("Hold Person"))
					        				holdPerson = true;
					        			else if (spell.equals("Hold Monster"))
					        				holdMonster = true;
					        		}
					        		else
					        		{
					        			System.out.println(enemyName + " resisted the "
					        					+ "spell!");
					        			counterHit = true;
					        		}
				        		}
			        		}
			        	}
			        	else if (spell.equals("Ignite"))
			        	{
			        		if (ignite == false)
			        		{
			        			if (enemy.reflexSave() < player.getSpellDC(
				        				Magic.getCost(spell)))
				        		{
				        			System.out.println(enemyName + " has caught fire!");
				        			ignite = true;
				        		}
				        		else
				        		{
				        			System.out.println(enemyName + " dodged the flames!");
				        		}
			        			counterHit = true;
			        		}
			        		else
			        		{
			        			System.out.println("You can't cast this again.");
			        			spell = null;
			        		}
			        	}
			        	else if (spell.equals("Fist of Force"))
			        	{
			        		enemy.damage(mgk.fistOfForce(player), player, "force");
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Ice Armor"))
			        	{
			        		if (iceArmor)
			        		{
			        			System.out.println("You can't cast that again right now.");
			        			spell = null;
			        		}
			        		else
			        		{
			        			System.out.println("Thick armor made of ice forms "
				        				+ "around you.");
				        		player.iceArmor();
				        		iceArmor = true;
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Sonic Echo"))
			        	{
			        		enemy.damage(mgk.sonicEcho(player, lastSpell), player, "sonic");
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Sonic Boom"))
			        	{
			        		if (player.touchSpellHit(enemy.getType()) >= enemy.getTouchAC())
			        		{
			        			enemy.damage(mgk.sonicBoom(player), player, "sonic");
			        		}
			        		else
			        		{
			        			System.out.println("The sonic boom misses the "
			        					+ enemyName);
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Shadow Slash"))
			        	{
			        		if (player.touchSpellHit(enemy.getType()) >= enemy.getTouchAC())
			        		{
			        			if (!hammer)
			        			{
			        				enemy.meleeDamage(mgk.shadowSlash(player), forceAttack, player, false);
			        			}
			        			else
			        			{
			        				enemy.meleeDamage(mgk.shadowSlash(player), forceAttack, player, false);
			        				enemy.damage(mgk.hammer(player), player, "force");
			        			}
			        			if (enemy.reflexSave() < 
			        					player.getSpellDC(Magic.getCost(spell)))
			        			{
			        				System.out.println("Some of the shadows have "
			        						+ "stuck to " + enemyName + "s face!");
			        				enemy.hitPenalty(roll.d3() + roll.d3());
			        			}
			        		}
			        		else
			        		{
			        			System.out.println("The " + spell + " missed!");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Mend"))
			        	{
			        		player.changeHP(mgk.mend(player));
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Cure"))
			        	{
			        		player.changeHP(mgk.cure(player));
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Heal"))
			        	{
			        		player.changeHP(mgk.heal(player));
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Incendiary Cloud"))
			        	{
			        		if (enemy.reflexSave() < 
			        				player.getSpellDC(Magic.getCost(spell)))
			        		{
			        			enemy.damage(mgk.incendiaryCloud(player), player, "fire");
			        			System.out.println("The " + enemyName + " has had their "
			        					+ "eyes burnt!");
			        			if (player.getCha() > 14)
			        			{
			        				enemy.lowerHit((player.getCha() - 10) / 2);
			        			}
			        			else
			        			{
			        				enemy.lowerHit(2);
			        			}
			        		}
			        		else
			        		{
			        			enemy.damage(mgk.incendiaryCloud(player) / 2, player, "fire");
			        			System.out.println("The " + enemyName + " shielded its "
			        					+ "eyes from the smoke!");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Force Hand"))
			        	{
			        		boolean done = false;
			        		while (!done)
			        		{
			        			System.out.print("What will the hand do (pin/punch)? ");
			        			String choice = scan.nextLine();
			        			
			        			if (choice.equalsIgnoreCase("pin"))
			        			{
			        				if (enemy.grappleRoll() > mgk.forceHand(player, false))
			        				{
			        					System.out.println("The " + enemyName + " fought off the hand!");
			        				}
			        				else
			        				{
			        					System.out.println("The " + enemyName + " is pinned down!");
			        					pinned = true;
			        				}
			        				done = true;
			        			}
			        			else if (choice.equalsIgnoreCase("punch"))
			        			{
			        				if (enemy.getTouchAC() < player.touchSpellHit(enemy.getType()))
			        				{
			        					System.out.println("The fist hit!");
			        					enemy.damage(mgk.forceHand(player, true), player, "force");
			        				}
			        				else
			        				{
			        					System.out.println("The " + enemyName + " dodged "
			        							+ "the fist!");
			        				}
			        				counterHit = true;
			        				done = true;
			        			}
			        			else
			        			{
			        				System.out.println("Invalid Input");
			        			}
			        		}
			        	}
			        	else if (spell.equals("Cone of Cold"))
			        	{
			        		if (enemy.reflexSave() < 
			        				player.getSpellDC(Magic.getCost(spell)))
			        		{
			        			enemy.damage(mgk.coneOfCold(player, weakToFrost), player, "cold");
			        		}
			        		else
			        		{
			        			System.out.println("The " + enemyName + " rolled out "
			        					+ "of the way!");
			        			enemy.damage(mgk.coneOfCold(player, weakToFrost) / 2, player, "cold");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Conflagration"))
			        	{
			        		if (enemy.reflexSave() < player.getSpellDC(Magic.getCost(spell)))
			        		{
			        			enemy.damage(mgk.conflagration(player), player, "fire");
			        		}
			        		else
			        		{
			        			System.out.println("The " + enemyName + " rolled out "
			        					+ "of the way!");
			        			enemy.damage(mgk.conflagration(player), player, "fire");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Hammer"))
			        	{
			        		if (hammer)
			        		{
			        			System.out.println("It would have no effect.");
			        			spell = null;
			        		}
			        		else
			        		{
			        			System.out.println("Your attacks have been imbued with sledghammer force!");
				        		hammer = true;
				        		counterHit = true;
			        		}
			        	}
			        	else if (spell.equals("Deep Freeze"))
			        	{
			        		if (enemy.reflexSave() < player.getSpellDC(Magic.getCost(spell)))
			        		{
			        			if (enemyName.equals("Boneclaw") == false)
			        			{
			        				if (enemy.fortSave() < player.getSpellDC(Magic.getCost(spell)))
				        			{
				        				enemy.lowerHit(2);
				        				enemy.changeAC(-2, false);
				        			}
				        			else
				        			{
				        				System.out.println("The " + enemyName + " resisted "
				        						+ "the cold!");
				        			}
			        			}
			        			enemy.damage(mgk.deepFreeze(player, weakToFrost), player, "cold");
			        		}
			        		else
			        		{
			        			System.out.println("The " + enemyName + " dodged the blast!");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("True Strike"))
			        	{
			        		System.out.println("You focus on the targets movements, watching for a point to strike!");
			        		trueStrike = true;
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Confuse"))
			        	{
			        		if (enemy.willSave() < player.getSpellDC(Magic.getCost(spell)) && (enemy.getType().equals("Undead")) == false)
			        		{
			        			System.out.println("The " + enemyName + " attacks itself!");
			        			enemy.attackSelf(player);
			        		}
			        		else if (enemy.getType().equals("Undead"))
			        		{
			        			System.out.println("There was no effect!");
			        		}
			        		else
			        		{
			        			System.out.println("The " + enemyName + " resisted the spell!");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Magma Spray"))
			        	{
			        		boolean save = false;
			        		if (enemy.reflexSave() >= player.getSpellDC(Magic.getCost(spell)))
			        		{
			        			System.out.println("It only grazed the " + enemyName);
			        			save = true;
			        		}
			        		enemy.damage(mgk.magmaSpray(player, save), player, "fire");
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Force Rupture"))
			        	{
			        		enemy.damage(mgk.forceRupture(player), player, "force");
			        	}
			        	else if (spell.equals("Force Scythe"))
			        	{
			        		if (!hammer)
			        		{
			        			enemy.meleeDamage(mgk.forceScythe(player), forceAttack, player, true);
			        		}
			        		else
			        		{
			        			enemy.meleeDamage(mgk.forceScythe(player), forceAttack, player, true);
			        			enemy.damage(mgk.hammer(player), player, "force");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Avalanche"))
			        	{
			        		if (!avalanche)
			        		{
			        			avalancheDC = player.getSpellDC(Magic.getCost(spell));
			        			if (enemy.reflexSave() < avalancheDC)
				        		{
				        			if (enemyName.equals("Shadow") == false && enemyName.equals("Greater Shadow") == false
				        					&& enemyName.equals("Joystealer") == false)
				        			{
				        				System.out.println("The " + enemyName + " is buried in snow!");
					        			avalanche = true;
				        			}
				        			enemy.damage(mgk.avalanche(player, weakToFrost), player, "cold");
				        		}
				        		else
				        		{
				        			System.out.println("The " + enemyName + " barely avoided the avalanche!");
				        			enemy.damage(mgk.avalanche(player, weakToFrost) / 2, player, "cold");
				        		}
				        		counterHit = true;
			        		}
			        		else
			        		{
			        			System.out.println("It would have no effect.");
			        			spell = null;
			        		}
			        	}
			        	else if (spell.equals("Hypothermia"))
			        	{
			        		if (!weakToFrost)
			        		{
			        			if (enemy.fortSave() < player.getSpellDC(Magic.getCost(spell)) && (enemyName.equals("Boneclaw") == false
			        					&& enemyName.equals("Skeleton") == false))
				        		{
				        			System.out.println("The " + enemyName + " is now weak to cold damage!");
				        			weakToFrost = true;
				        			enemy.damage(mgk.hypothermia(player), player, "cold");
				        		}
				        		else
				        		{
				        			System.out.println("The " + enemyName + " resisted the spell!");
				        			enemy.damage(mgk.hypothermia(player), player, "cold");
				        		}
			        			counterHit = true;
			        		}
			        		else
			        		{
			        			System.out.println("It would have no effect.");
			        			spell = null;
			        		}
			        	}
			        	else if (spell.equals("Deadly Lahar"))
			        	{
			        		if (deadlyLahar == 0)
			        		{
			        			System.out.println("A cloud of hot ash forms and moves toward the " + enemyName);
			        			deadlyLahar = roll.d4();
			        			deadlyLaharDC = player.getSpellDC(Magic.getCost(spell));
			        			counterHit = true;
			        		}
			        		else
			        		{
			        			System.out.println("It would have no effect.");
			        			spell = null;
			        		}
			        	}
			        	else if (spell.equals("Crushing Prison"))
			        	{
			        		if (crushingPrison == 0)
			        		{
			        			if (enemy.reflexSave() < player.getSpellDC(Magic.getCost(spell)))
			        			{
			        				System.out.println("The " + enemyName + " was surrounded in a crushing prison!");
			        				crushingPrison = roll.dX(2);
			        				if (player.getCha() >= 13)
			        				{
			        					crushingPrison += (player.getCha() - 10) / 2;
			        				}
			        				else
			        				{
			        					crushingPrison += 1;
			        				}
			        			}
			        			else
			        			{
			        				System.out.println("The " + enemyName + " escaped the prison as it formed!");
			        			}
			        			counterHit = true;
			        		}
			        		else
			        		{
			        			System.out.println("It would have no effect.");
			        			spell = null;
			        		}
			        	}
			        	else if (spell.equals("Inferno"))
			        	{
			        		boolean done = false;
			        		
			        		while (!done)
			        		{
			        			System.out.print("How will you cast this (blast/ongoing)? ");
			        			String choice = scan.nextLine();
			        			
			        			if (choice.equalsIgnoreCase("blast"))
			        			{
			        				if (enemy.reflexSave() < player.getSpellDC(Magic.getCost(spell)))
			        				{
			        					enemy.damage(mgk.inferno(player, true), player, "fire");
			        				}
			        				else
			        				{
			        					System.out.println("The " + enemyName + " rolled away from the blast!");
			        					enemy.damage(mgk.inferno(player, true) / 2, player, "fire");
			        				}
			        				done = true;
			        				counterHit = true;
			        			}
			        			else if (choice.equalsIgnoreCase("ongoing"))
			        			{
			        				infernoDC = player.getSpellDC(Magic.getCost(spell));
			        				
			        				if (inferno == 0)
			        				{
			        					System.out.println("The air itself catches fire!");
			        					inferno = roll.d3() + 1;
			        					done = true;
			        					counterHit = true;
			        				}
			        				else
			        				{
			        					System.out.println("It would have no effect.");
			        					spell = null;
			        				}
			        				done = true;
			        			}
			        			else
			        			{
			        				System.out.println("Invalid Input.");
			        			}
			        		}
			        	}
			        	else if (spell.equals("Force Spear"))
			        	{
			        		if (!hammer)
			        		{
			        			enemy.meleeDamage(mgk.forceSpear(player), forceAttack, player, true);
			        		}
			        		else
			        		{
			        			enemy.meleeDamage(mgk.forceSpear(player), forceAttack, player, true);
			        			enemy.damage(mgk.hammer(player), player, "force");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Blizzard"))
			        	{
			        		if (blizzard == 0)
			        		{
			        			System.out.println("A raging blizzard picks up!");
			        			blizzard = roll.d4() + ((player.getCha() - 10) / 2);
			        			blizzardDC = player.getSpellDC(Magic.getCost(spell)) - 10;
			        			counterHit = true;
			        		}
			        		else
			        		{
			        			System.out.println("It would have no effect.");
			        			spell = null;
			        		}
			        	}
			        	else if (spell.equals("Haste"))
			        	{
			        		if (haste > 0)
			        		{
			        			System.out.println("It would have no effect.");
			        			spell = null;
			        		}
			        		else
			        		{
			        			haste = 2;
			        			System.out.println("Time seems to slow down.");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Sonic Hurricane"))
			        	{
			        		if (enemy.reflexSave() < player.getSpellDC(Magic.getCost(spell)))
			        		{
			        			enemy.damage(mgk.sonicHurricane(player), player, "sonic");
			        		}
			        		else
			        		{
			        			System.out.println("The " + enemyName + " rolled away from the spell!");
			        			enemy.damage(mgk.sonicHurricane(player) / 2, player, "sonic");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Drown"))
			        	{
			        		if (drowning > 0)
			        		{
			        			System.out.println("It would have no effect.");
			        			spell = null;
			        		}
			        		else
			        		{
			        			if (enemy.getType().equals("Undead"))
				        		{
				        			System.out.println("Some water leaks out of the "
				        					+ enemyName + "'s body!");
				        		}
				        		else
				        		{
				        			if (enemy.fortSave() < player.getSpellDC(Magic.getCost(spell)))
					        		{
					        			System.out.println(player.getName() + " has conjured some water into the "
					        					+ enemyName + "'s lungs!");
					        			drownDC = Magic.getCost(spell);
					        			drowning = 1;
					        		}
				        			else
				        			{
				        				System.out.println("The " + enemyName + " has resisted the spell!");
				        			}
				        		}
			        			counterHit = true;
			        		}
			        	}
			        	else if (spell.equals("Drain Life")) 
			        	{
			        		int stolen = mgk.drainLife(player);
			        		if (enemy.fortSave() >= player.getSpellDC(Magic.getCost(spell)))
			        		{
			        			System.out.println("The " + enemyName + " resisted the spell!");
			        			stolen /= 2;
			        		}
			        		enemy.damage(stolen, player, "entropy");
			        		player.changeHP(stolen);
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Searing Light"))
			        	{
			        		int damage = mgk.searingLight(player);
			        		if (enemy.getTouchAC() < player.touchSpellHit(enemy.getType()))
			        		{
			        			enemy.damage(damage, player, "light");
			        			if (enemy.fortSave() < player.getSpellDC(Magic.getCost(spell)))
			        			{
			        				System.out.println("The " + enemyName + " was partially blinded!");
			        				enemy.hitPenalty(4);
			        			}
			        		}
			        		else
			        		{
			        			System.out.println("The ray missed!");
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Light Burst"))
			        	{
			        		int damage = mgk.lightBurst(player);
			        		boolean blindRisk = true;
			        		if (enemy.reflexSave() >= player.getSpellDC(Magic.getCost(spell)))
			        		{
			        			System.out.println("The " + enemyName + " rolled away from the spell!");
			        			blindRisk = false;
			        			damage /= 2;
			        		}
			        		enemy.damage(damage, player, "light");
			        		
			        		if (blindRisk)
			        		{
			        			if (enemy.fortSave() < player.getSpellDC(Magic.getCost(spell)))
			        			{
			        				System.out.println("The " + enemyName + " has been partially blinded!");
			        				enemy.hitPenalty(4);
			        			}
			        		}
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Spark"))
			        	{
			        		int damage = mgk.spark(player);
			        		
			        		if (enemy.reflexSave() >= player.getSpellDC(Magic.getCost(spell)))
			        		{
			        			System.out.println("The " + enemyName + " rolled away from the spell!");
			        			damage /= 2;
			        		}
			        		
			        		enemy.damage(damage, player, "electricity");
			        		counterHit = true;
			        	}
			        	else if (spell.equals("Shadow Blast"))
			        	{
			        		int damage = mgk.shadowBlast(player);
			        		
			        		if (player.touchSpellHit(enemy.getType()) >= enemy.getTouchAC())
			        		{
			        			enemy.damage(damage, player, "entropy");
			        			
			        			if (enemy.reflexSave() < player.getSpellDC(Magic.getCost(spell)))
			        			{
			        				System.out.println("The shadows stick to the " + enemyName + "'s face!");
			        				enemy.lowerHit(roll.dX(player.getCasterLevel() / 2));
			        			}
			        		}
			        		else
			        		{
			        			System.out.println("The blast missed!");
			        		}
			        		counterHit = true;
			        	}


			        	if (spell != null)
			        	{
			        		player.changeMP(-Magic.getCost(spell));
			        		lastSpell = spell;
			        	}
			        }
		        	else
		        	{
		        		System.out.println("You can't cast spells while raging.");
		        	}
		        }
		        else if (battleInput.equalsIgnoreCase("item"))
		        {
		        	inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
		        	System.out.println("What item will you use?");
		        	battleInput = scan.nextLine();
		        	if (battleInput.equalsIgnoreCase("Smoke Bomb"))
		        	{
		        		if (canEscape)
		        		{
		        			inv.useItem(battleInput);
		        			inv.remItem(battleInput);
				        	battleInput = "flee";
				        	enemy.setHP(0);
		        		}
		        		else
		        		{
		        			System.out.println("You cannot escape from here.");
		        		}
		        	}
		        	else
		        	{
		        		String item = inv.useItem(battleInput);
		        		boolean usable = true;
		        		
			        	if (item.equalsIgnoreCase("Potion"))
			        	{
			        		player.potion(1);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Greater Potion"))
			        	{
			        		player.potion(2);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Full Potion"))
			        	{
			        		player.fullPotion();
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Mana Potion"))
			        	{
			        		player.manaPotion(1);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Greater mana potion"))
			        	{
			        		player.manaPotion(2);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Major mana potion"))
			        	{
			        		player.manaPotion(3);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Bandages"))
			        	{
			        		if (bleedingWound > 0)
			        			{
			        				bleedingWound -= 4;
			        				if (bleedingWound < 0)
			        					bleedingWound = 0;
			        			}
			        		player.changeHP(2);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Oil of Recovery"))
			        	{
			        		if (bleedingWound > 0)
			        			bleedingWound = 0;
			        		player.changeHP(player.getLevel());
			        		player.recoverPoison();
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Potion of Str"))
			        	{
			        		player.changeStat("Strength", 4);
			        		strBuff += 4;
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Potion of Int"))
			        	{
			        		player.changeStat("Intelligence", 4);
			        		intBuff += 4;
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Holy Water"))
			        	{
			        		GeneralEffects.holyWater(player);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Lightning Javelin"))
			        	{
			        		GeneralEffects.lightningJavelin(player, enemy);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Revitalising Potion"))
			        	{
			        		player.revitalisingPotion();
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Mead"))
			        	{
			        		GeneralEffects.alcohol(player, 1);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Wine"))
			        	{
			        		GeneralEffects.alcohol(player, 2);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Dwarven Rum"))
			        	{
			        		GeneralEffects.alcohol(player, 3);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Orc Killer"))
			        	{
			        		GeneralEffects.alcohol(player, 4);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Dwarf Killer"))
			        	{
			        		GeneralEffects.alcohol(player, 5);
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("rock"))
			        	{
			        		if (player.touchSpellHit(enemy.getType()) >= enemy.getAC())
			        		{
			        			System.out.println("The rock hit the " + enemyName + "!");
			        			int damage = roll.d3();
			        			enemy.meleeDamage(damage, null, player, false);
			        		}
			        		else
			        		{
			        			System.out.println("The rock missed the " + enemyName + "!");
			        		}
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("book of burning"))
			        	{
			        		usable = false;
			        		System.out.println("A ball of fire shoots from the book!");
			        		if (player.touchSpellHit(enemy.getType()) >= enemy.getTouchAC())
			        		{
			        			System.out.println("The ball of fire hit the " + enemy.getName() + "!");
			        			int dmg = roll.XdY(2, 6);
			        			enemy.damage(dmg, player, "fire");
			        		}
			        		else
			        		{
			        			System.out.println("The ball of fire missed the " + enemy.getName() + "!");
			        		}
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Rod of Lightning"))
			        	{
			        		usable = false;
			        		System.out.println("A bolt of lightning shoots from the rod!");
			        		int damage = GeneralEffects.rodOfLightning();
			        		
			        		if (enemy.reflexSave() >= 15)
			        		{
			        			System.out.println("The " + enemyName + " rolled away from the bolt!");
			        			damage /= 2;
			        		}
			        		
			        		enemy.damage(damage, player, "lightning");
			        		
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Potion of Haste"))
			        	{
			        		if (haste > 0)
			        		{
			        			System.out.println("It would have no effect.");
			        			usable = false;
			        		}
			        		else
			        		{
			        			haste = 2;
			        			System.out.println("Time seems to slow down.");
			        		}
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Antidote"))
			        	{
			        		if (!Game.antidote)
			        		{
			        			System.out.println("You feel more resistant to poison!");
			            		Game.antidote = true;
			        		}
			        		else
			        		{
			        			System.out.println("There would be no point.");
			        			usable = false;
			        		}
			        		counterHit = true;
			        	}
			        	else if (item.equalsIgnoreCase("Potion of Ironskin"))
			        	{
			        		if (!Game.ironSkin)
			        		{
			        			System.out.println("Your skin becomes living iron!");
			        			Game.ironSkin = true;
			        		}
			        		else
			        		{
			        			System.out.println("There would be no point.");
			        			usable = false;
			        		}
			        		counterHit = true;
			        	}
			        	else
			        	{
			        		System.out.println("That item is " +
			        				"unavailable.");
			        		usable = false;
			        	}
			        	
			        	if (usable)
			        	{
			        		inv.remItem(battleInput);
			        	}
		        	}
		        }
		        else if (battleInput.equalsIgnoreCase("attack"))
		        {
		        	int attackRoll = player.attackRoll(enemy.getType());
		        	if (trueStrike)
		        	{
		        		attackRoll += 20;
		        		trueStrike = false;
		        	}
		        	if (attackRoll >= enemy.getAC())
		        	{
		        		System.out.println(player.getName()
		        				+ " hit the enemy!");
		        		if (!hammer)
		        		{
		        			player.getWeapon().damage(player, enemy, 0);
		        		}
		        		else
		        		{
		        			player.getWeapon().damage(player, enemy, 0);
		        			enemy.damage(mgk.hammer(player), player, "force");
		        		}
		        		
		        		String temp[] = player.getWeapon().getProperties();
		        		
		        		if (temp.length > 0)
		        		{
		        			for (int i = 0; i < temp.length; i++)
		        			{
		        				if (temp[i].equals("Agrinon"))
		        				{
		        					if ((enemy.getType().equals("Undead") == false) && roll.d100() <= 2)
		        					{
		        						System.out.println("The " + enemyName + "'s life force was drawn into your blade!");
		        						enemy.setHP(0);
		        					}
		        				}
		        			}
		        		}
		        		
		        		if (player.getWeapon().getName().equals("Screaming Warhammer"))
		        		{
		        			lastSpell = "Sonic Echo";
		        		}
		        		else
		        		{
		        			lastSpell = "";
		        		}
		        	}
		        	else
		        	{
		        		System.out.println(player.getName()
		        				+ " missed the " + enemy.getName() + "!");
		        	}
		        	if (enemy.getHP() > 0)
		        	{
		        		counterHit = true;
		        	}
		        }
		        else if (battleInput.equalsIgnoreCase("Arcane Strike"))
		        {
		        	boolean loop = true;
		        	
		        	System.out.print("How much mana will you spend on the attack? ");
		        	int amount;
		        	while (loop)
		        	{
		        		if (scan.hasNextInt())
		        		{
		        			amount = scan.nextInt();
		        			if (amount == 0)
		        			{
		        				loop = false;
		        			}
		        			else if (amount < 0)
		        			{
		        				System.out.println("You cannot spend less than 0 mana on this attack!");
		        			}
		        			else if (amount > player.getMP())
		        			{
		        				System.out.println("You cannot spend more mana than you have on this attack!");
		        			}
		        			else
		        			{
		        				loop = false;
		        				player.changeMP(-amount);
		        				
		        				int dmg = roll.XdY(amount, 4);
		        				
		        				if (player.attackRoll(enemy.getType()) + amount >= enemy.getAC())
		        				{
		        					System.out.println(player.getName() + " hit the " + enemyName + "!");
		        					player.getWeapon().damage(player, enemy, 0);
		        					enemy.damage(dmg, player, "magic");
		        				}
		        				else
		        				{
		        					System.out.println(player.getName() + " missed the " + enemyName + "!");
		        				}
		        				counterHit = true;
		        			}
		        			
		        			scan.nextLine();
		        		}
		        		else
		        		{
		        			System.out.println("You must enter an integer.");
		        		}
		        	}
		        }
		        else if (battleInput.equalsIgnoreCase("expertise"))
		        {
		        	int attackRoll = player.expertiseAttackRoll(enemy.getType());
		        	if (trueStrike)
		        	{
		        		attackRoll += 20;
		        		trueStrike = false;
		        	}
		        	if (attackRoll >= enemy.getAC())
		        	{
		        		System.out.println(player.getName() + " hit the " + enemy.getName() + "!");
		        		if (!hammer)
		        		{
		        			player.getWeapon().damage(player, enemy, 0);
		        		}
		        		else
		        		{
		        			player.getWeapon().damage(player, enemy, 0);
		        			enemy.damage(mgk.hammer(player), player, "force");
		        		}
		        		if (player.getWeapon().equals("Flame Sword"))
		        			enemy.damage(roll.d6(), player, "fire");
		        	}
		        	else
		        	{
		        		System.out.println(player.getName() + " missed the " + enemyName + "!");
		        	}
		        	counterHit = true;
		        }
		        else if (battleInput.equalsIgnoreCase("power attack"))
		        {
		        	if (player.hasPowerAttack())
		        	{
		        		int attackRoll = player.powerAttackRoll(enemy.getType());
		        		if (trueStrike)
		        		{
		        			attackRoll += 20;
		        			trueStrike = false;
		        		}
		        		if (attackRoll >= enemy.getAC())
			        	{
			        		System.out.println(player.getName()
			        				+ " hit the enemy!");
			        		if (!hammer)
			        		{
			        			player.getWeapon().damage(player, enemy, 0);
			        		}
			        		else
			        		{
			        			player.getWeapon().damage(player, enemy, 0);
			        			enemy.damage(mgk.hammer(player), player, "force");
			        		}
			        	}
			        	else
			        	{
			        		System.out.println(player.getName()
			        				+ " missed the enemy!");
			        	}
			        	if (enemy.getHP() > 0)
			        	{
			        		counterHit = true;
			        	}
		        	}
		        	else
		        	{
		        		System.out.println("You can't do that yet.");
		        	}
		        }
		        else if (battleInput.equalsIgnoreCase("smite"))
		        {
		        	if (player.hasSmite())
		        	{
		        		if (player.getMP() > 0)
		        		{
		        			player.changeMP(-1);
		        			int attackRoll = player.attackRoll(enemy.getType()) + ((player.getCha() - 10) / 2);
			        		if (attackRoll >= enemy.getAC())
			        		{
			        			System.out.println(player.getName()
				        				+ " hit the enemy!");
				        		if (!hammer)
				        		{
				        			player.getWeapon().damage(player, enemy, player.getLevel());
				        		}
				        		else
				        		{
				        			player.getWeapon().damage(player, enemy, player.getLevel());
				        			enemy.damage(mgk.hammer(player), player, "force");
				        		}
			        		}
			        		else
			        		{
			        			System.out.println(player.getName() + " missed the " + enemy.getName() + "!");
			        		}
			        		counterHit = true;
		        		}
		        		else
		        		{
		        			System.out.println("You don't have enough MP.");
		        		}
		        	}
		        	else
		        	{
		        		System.out.println("You can't do that yet.");
		        	}
		        }
		        else if (battleInput.equalsIgnoreCase("grapple"))
		        {
		        	System.out.println(player.getName() + " tries to grab the " + enemy.getName() + "!");
		        	if (player.attackRoll(enemy.getType()) >= enemy.getTouchAC() && enemy.isIncorporeal() == false)
		        	{
		        		System.out.println(player.getName() + " has grabbed hold of the " + enemy.getName() + "!");
		        		int playerGrapple = player.grappleRoll();
		        		int enemyGrapple = enemy.grappleRoll();
		        		if (playerGrapple > enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() >= enemy.getGrappleMod()))
		        		{
		        			System.out.println(player.getName() + " has started a grapple!");
		        			grapple(player, enemy, Magic, inv);
		        		}
		        		else
		        		{
		        			System.out.println("The " + enemy.getName() + " fought its way out of the hold!");
		        			counterHit = true;
		        		}
		        	}
		        	else if (enemy.isIncorporeal())
		        	{
		        		System.out.println(player.getName() + "s hands passed through the " + enemy.getName() + "!");
		        		counterHit = true;
		        	}
		        	else
		        	{
		        		System.out.println(player.getName() + " missed the " + enemy.getName() + "!");
		        		counterHit = true;
		        	}
		        }
		        if (counterHit && haste == 0)
		        {
		        	if (ignite)
		        	{
		        		System.out.println(enemyName + " is hurt by the flames!");
		        		enemy.damage(mgk.ignite(player), player, "fire");
		        	}
		        	if (enemy.getHP() > 0)
		        	{
		        		if (frozen <= 0)
		        		{
		        			if (!holdPerson && !holdMonster)
		        			{
		        				if (drowning == 0)
		        				{
		        					if (crushingPrison == 0)
			        				{
			        					if (!avalanche)
				        				{
				        					if (!pinned)
					        				{
					        					if (enemyName.equals("Dark Lord"))
						        				{
						        					int tactic = roll.d100();
						        					if (tactic <= 15)
						        					{
						        						if (enemy.attackRoll() >= player.getAC())
										        		{
						        							playerWasHit = true;
										        			System.out.println("The " + enemyName + " hit "
										        					+ player.getName() + "!");
										        			player.meleeDamage(
										        				enemy.attack(player));
										        			if (cloakOfFire)
										        			{
										        				System.out.println("The " + enemyName + " is hurt "
										        						+ "by the flames!");
											        			enemy.damage(mgk.cloakOfFire(player), player, "fire");
										        			}
										        			if (player.getHP() < 1)
											        		{
											        			System.out.println(player.getName()
											        					+ " has died!");
											        			System.exit(0);
											        		}
										        		}
						        						else
						        						{
						        							System.out.println("The " + enemyName
						        									+ " missed " + player.getName() + "!");
						        						}
						        					}
						        					else if (tactic <= 30)
						        					{
						        						System.out.println("Illusionary duplicates "
						        								+ "step out from the " + enemyName + "!");
						        						enemy.changeAC(3, true);
						        					}
						        					else if (tactic <= 60)
						        					{
						        						System.out.println("The " + enemyName + " stares at "
						        								+ player.getName() + "!");
						        						if (player.savingThrow(Player.Saves.Will, true, false) < enemy.getDC())
						        						{
						        							System.out.println("Your mind shatters!");
						        							int damage = roll.XdY(15, 10) + 5;
						        							player.elementDamage(damage, "Mental");
						        							System.out.println(player.getName() + " took "
						        									+ damage + " mental damage!");
						        							player.poison("Intelligence", 1, 6, false);
						        							player.poison("Wisdom", 1, 6, false);
						        							player.poison("Charisma", 1, 6, false);
						        						}
						        						else
						        						{
						        							System.out.println("The mental attack "
						        									+ "does not phase " + player.getName() + "!");
						        						}
						        					}
						        					else if (tactic <= 70)
						        					{
						        						System.out.println("The " + enemyName + " says something under "
						        								+ "his breath and his muscles grow much larger!");
						        						enemy.lucienBuff();
						        					}
						        					else if (tactic <= 99)
						        					{
						        						System.out.println("The " + enemyName + " squeezes with his "
						        								+ "outstretched hand!");

						        						int damage = 0;
					        							for (int index = 0; index < 18; index++)
					        							{
					        								damage += roll.d4();
					        							}
					        							System.out.println("You feel an intense "
					        									+ "pressure squeezing your heart!");
					        							if (player.savingThrow(Player.Saves.Fortitude, true, false) < enemy.getDC())
						        						{
						        							player.poison("Strength", 2, 3, false);
						        						}
						        						else
						        						{
						        							System.out.println(player.getName()
						        									+ " resisted the spell!");
						        							damage = damage / 2;
						        						}
					        							System.out.println(player.getName() + " took "
					        									+ damage + " damage!");
					        							player.meleeDamage(damage);
						        					}
						        					else if (tactic == 100)
						        					{
						        						System.out.println("The " + enemyName + " incants some strange syllables and "
						        								+ "dark hands covered in roiling green flames reach greedily"
						        								+ "for " + player.getName() + "!");
						        						
						        						if (player.savingThrow(Player.Saves.Reflex, true, false) < 19)
						        						{
						        							System.out.println("The hands caught " + player.getName() + "!");
						        							System.out.println(player.getName() + " has been dragged to the "
						        									+ "underworld!");
						        							player.setHP(0);
						        						}
						        						else
						        						{
						        							System.out.println(player.getName() + " dodged the hands!");
						        						}
						        					}
						        				}
						        				else if (enemyName.equals("Witch"))
						        				{
						        					int tactic = roll.d100();
						        					if (tactic < 50)
						        					{
						        						System.out.println("The " + enemyName + " fires a "
							        							+ "magic beam at " + player.getName());
						        						int damage;
							        					
						        						if (player.savingThrow(Player.Saves.Reflex, true, false) >= 15)
							        					{
							        						System.out.println("The beam grazes "
							        								+ player.getName());
							        						damage = roll.d6() + roll.d6() + 2;
							        						
							        						System.out.println("The beam did "
							        								+ damage + " magic damage to "
							        								+ player.getName());
							        						player.elementDamage(damage, "Magic");
							        					}
							        					else
							        					{
							        						System.out.println("The beam hit!");
							        						int dmg = 0;
							        						for (int index = 0; index < 6; index++)
							        						{
							        							dmg += roll.d6() + 1;
							        						}
							        						System.out.println("The beam did "
							        								+ dmg + " magic damage to "
							        								+ player.getName());
							        						player.elementDamage(dmg, "Magic");
							        					}
						        					}
						        					else if (tactic < 80)
						        					{
						        						System.out.println("The " + enemyName
						        								+ "es form begins to blur!");
						        						
						        						enemy.changeAC(3, true);
						        					}
						        					else
						        					{
						        						System.out.println("The " + enemyName
						        								+ " begins to cackle as its "
						        								+ " wounds start to heal!");
						        						
						        						enemy.changeHP(roll.d6() + roll.d6()
						        								+ roll.d6() + 6);
						        					}
						        				}
						        				else if (enemyName.equals("Nightcrawlers stomache"))
						        				{
						        					if (player.savingThrow(Player.Saves.Fortitude, false, false) >= 22)
						        					{
						        						System.out.println(player.getName() + " resisted the life-sapping effect!");
						        					}
						        					else
						        					{
						        						System.out.println(player.getName() + " was sapped of life!");
						        						player.poison("Strength", 1, 3, false);
						        						player.poison("Dexterity", 1, 3, false);
						        						player.poison("Constitution", 1, 3, false);
						        						player.poison("Intelligence", 1, 3, false);
						        						player.poison("Wisdom", 1, 3, false);
						        						player.poison("Charisma", 1, 3, false);
						        					}
						        					player.checkDeath();
						        				}
						        				else if (enemyName.equals("Mohrg"))
						        				{
						        					int tactic = roll.d100();
						        					if (tactic <= 75)
						        					{
						        						if (enemy.attackRoll() >= player.getAC())
						        						{
						        							playerWasHit = true;
						        							System.out.println("The " + enemyName + " hit "
						        									+ player.getName() + "!");
						        							player.meleeDamage(enemy.attack(player));
						        							if (cloakOfFire)
										        			{
										        				System.out.println("The " + enemyName + " is hurt "
										        						+ "by the flames!");
											        			enemy.damage(mgk.cloakOfFire(player), player, "fire");
										        			}
										        			if (player.getHP() < 1)
											        		{
											        			System.out.println(player.getName()
											        					+ " has died!");
											        			System.exit(0);
											        		}
										        			else
										        			{
										        				if (enemy.getGrappleThreat(player).equals("pushover") == false && enemy.getGrappleThreat(player).equals("mild") == false)
										        				{
										        					System.out.println("The " + enemyName + " attempts to grab hold of " + player.getName() + "!");
											        				int playerGrapple = player.grappleRoll();
											        				int enemyGrapple = enemy.grappleRoll();
										        					if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
											        				{
											        					System.out.println("The " + enemyName + " has started a grapple!");
											        					grapple(player, enemy, Magic, inv);
											        				}
											        				else
											        				{
											        					System.out.println(player.getName() + " fought off the " + enemyName + "!");
											        				}
										        				}
										        			}
						        						}
						        						else
						        						{
						        							System.out.println("The " + enemyName + " missed " + player.getName());
						        						}
						        					}
						        					else
						        					{
						        						System.out.println("The " + enemyName + " lashes at " + player.getName()
						        								+ " with its tongue!");
						        						if (enemy.attackRoll() >= player.getTouch())
						        						{
						        							System.out.println("The " + enemyName + "s tongue hits " + player.getName() + "!");
						        							if (player.savingThrow(Player.Saves.Fortitude, true, false) < enemy.getDC())
						        							{
						        								System.out.println(player.getName() + " is paralyzed!");
						        								int duration = roll.d3() + roll.d3();
						        								for (int index = 0; index < duration; index++)
						        								{
						        									
						        									System.out.println("The " + enemyName + " hits you!");
						        									player.meleeDamage(enemy.attack(player));
						        									if (player.getHP() < 1)
													        		{
													        			System.out.println(player.getName()
													        					+ " has died!");
													        			System.exit(0);
													        		}
						        									System.out.println("--------------------------------");
						        								}
						        								System.out.println("The paralysis has worn off!");
						        							}
						        							else
						        							{
						        								System.out.println(player.getName() + " resisted the paralysis!");
						        							}
						        						}
						        					}
						        				}
						        				else if (enemyName.equals("Shadow") || enemyName.equals("Greater Shadow"))
						        				{
						        					if (enemy.attackRoll() >= player.getTouch())
						        					{
						        						System.out.println("The " + enemyName + " hit " + player.getName() + "!");
						        						int diceSides;
						        						if (enemyName.equals("Shadow"))
						        						{
						        							diceSides = 6;
						        						}
						        						else
						        						{
						        							diceSides = 8;
						        						}
						        						player.poison("Strength", 1, diceSides, false);
						        					}
						        					else
						        						System.out.println("The " + enemyName + " missed " + player.getName() + "!");
						        				}
						        				else if (enemyName.equals("Joystealer"))
						        				{
						        					if (enemy.attackRoll() >= player.getTouch())
						        					{
						        						System.out.println("The " + enemyName + " hit " + player.getName() + "!");
						        						player.poison("Charisma", 1, 4, false);
						        					}
						        					else
						        						System.out.println("The " + enemyName + " missed " + player.getName() + "!");
						        				}
						        				else if (enemyName.equals("Necronaut"))
						        				{
						        					int tactic = roll.d100();
						        					
						        					if (tactic > 80)
						        					{
						        						System.out.println("The " + enemyName + " attempts to trample " + player.getName() + "!");
						        						int damage = roll.XdY(4, 6) + 22;
						        						if (player.savingThrow(Player.Saves.Reflex, false, false) >= enemy.getDC())
						        						{
						        							damage = damage / 2;
						        							System.out.println(player.getName() + " rolled away from the " + enemyName + "!");
						        						}
						        						player.meleeDamage(damage);
						        						if (cloakOfFire)
						        						{
						        							System.out.println("The " + enemyName + " is hurt by the flames!");
						        							enemy.damage(mgk.cloakOfFire(player), player, "fire");
						        						}
						        					}
						        					else
						        					{
						        						System.out.println("The " + enemyName + " attacks " + player.getName() + "!");
						        						if (enemy.attackRoll() >= player.getAC())
						        						{
						        							playerWasHit = true;
						        							System.out.println("The " + enemyName + " hit "
						        									+ player.getName() + "!");
						        							player.meleeDamage(enemy.attack(player));
						        							if (cloakOfFire)
										        			{
										        				System.out.println("The " + enemyName + " is hurt "
										        						+ "by the flames!");
											        			enemy.damage(mgk.cloakOfFire(player), player, "fire");
										        			}										        			
						        						}
						        					}
						        					if (player.getHP() < 1)
									        		{
									        			System.out.println(player.getName()
									        					+ " has died!");
									        			System.exit(0);
									        		}
						        				}
						        				else if (enemyName.equals("Warlock"))
						        				{
						        					System.out.println("The " + enemyName + " shoots a blast of magic at " + player.getName() + "!");
						        					if (enemy.attackRoll() >= player.getTouch())
						        					{
						        						System.out.println("The blast hit " + player.getName() + "!");
						        						int damage = roll.XdY(6, 6);
						        						System.out.println(enemyName + " did " + damage + " magic damage to " + player.getName() + "!");
						        						player.elementDamage(damage, "Magic");
						        					}
						        					else
						        					{
						        						System.out.println("The blast missed " + player.getName() + "!");
						        					}
						        				}
						        				else if (enemyName.equals("Chraal"))
						        				{
						        					if (roundsToEvent == roundCounter)
						        					{
						        						System.out.println("The " + enemyName + " breathes frost at " + player.getName() + "!");
						        						int frostDamage = roll.XdY(6, 6);
						        						if (player.savingThrow(Player.Saves.Reflex, false, false) >= enemy.getDC())
						        						{
						        							System.out.println(player.getName() + " rolled away from the cold!");
						        							frostDamage = frostDamage / 2;
						        						}
						        						player.elementDamage(frostDamage, "cold");
						        						System.out.println("The " + enemyName + " did " + frostDamage + " cold damage to " + player.getName() + "!");
						        						roundsToEvent += roll.d4();
						        					}
						        					else
						        					{
						        						if (enemy.attackRoll() >= player.getAC())
						        						{
						        							playerWasHit = true;
										        			System.out.println(enemyName + " hit "
										        					+ player.getName() + "!");
										        			player.meleeDamage(
										        				enemy.attack(player));
										        			if (cloakOfFire)
										        			{
										        				System.out.println(enemyName + " is hurt "
										        						+ "by the flames!");
											        			enemy.damage(mgk.cloakOfFire(player), player, "fire");
										        			}
										        			if (player.getHP() < 1)
											        		{
											        			System.out.println(player.getName()
											        					+ " has died!");
											        			System.exit(0);
											        		}
						        						}
						        					}
						        				}
						        				else if (enemyName.equals("Necromancer") && roll.d100() > 50)
						        				{
						        					System.out.println("The " + enemyName + " shoots a black beam from its finger!");
						        					if (enemy.attackRoll() >= player.getTouch())
						        					{
						        						System.out.println(player.getName() + " was hit by the beam!");
						        						if (player.savingThrow(Player.Saves.Fortitude, true, false) < enemy.getDC())
						        						{
						        							System.out.println(player.getName() + " was sapped of some energy!");
						        							player.poison("Constitution", 1, 6, false);
						        						}
						        						else
						        						{
						        							System.out.println(player.getName() + " resisted the spell!");
						        						}
						        					}
						        					else
						        					{
						        						System.out.println(player.getName() + " dodged the beam!");
						        					}
						        				}
						        				else if (enemyName.equals("Bison") && roll.d100() > 60)
						        				{
						        					GeneralEffects.enemyCharge(player, enemy);
						        				}
						        				else if (enemyName.contains("Vampire"))
						        				{
						        					boolean energyDrain = false;
						        					boolean dominate = false;
						        					int rng = roll.d100();
						        					
						        					if (!player.getAccessory().equalsIgnoreCase("Amulet of Holy Protection") && rng > 80)
						        					{
						        						dominate = true;
						        					}
						        					else if (rng > 50)
						        					{
						        						energyDrain = true;
						        					}
						        					
						        					if (dominate)
					        						{
					        							if (player.savingThrow(Player.Saves.Will, true, false) < enemy.getDC())
					        							{
					        								System.out.println(player.getName() + " was dominated!");
					        								System.out.println(player.getName() + " has become the " + enemyName + "'s"
					        										+ " slave!");
					        								System.out.println(player.getName() + " has died!");
					        								System.exit(0);
					        							}
					        							else
					        							{
					        								System.out.println(player.getName() + " resisted domination!");
					        							}
					        						}
						        					else if (enemy.attackRoll() >= player.getAC())
						        					{
						        						if (energyDrain)
						        						{
						        							playerWasHit = true;
										        			System.out.println(enemyName + " hit "
										        					+ player.getName() + "!");
										        			player.meleeDamage(
										        				enemy.unarmedAttack(player));
										        			if (cloakOfFire)
										        			{
										        				System.out.println(enemyName + " is hurt "
										        						+ "by the flames!");
											        			enemy.damage(mgk.cloakOfFire(player), player, "fire");
										        			}
										        			if (player.getHP() < 1)
											        		{
											        			System.out.println(player.getName()
											        					+ " has died!");
											        			System.exit(0);
											        		}
										        			else
										        			{
										        				System.out.println(player.getName() + " was partially drained of life!");
										        				player.poison("Strength", 1, 1, false);
										        				player.poison("Dexterity", 1, 1, false);
										        				player.poison("Constitution", 1, 1, false);
										        				player.poison("Intelligence", 1, 1, false);
										        				player.poison("Wisdom", 1, 1, false);
										        				player.poison("Charisma", 1, 1, false);
										        			}
						        						}
						        						else
						        						{
						        							playerWasHit = true;
										        			System.out.println(enemyName + " hit "
										        					+ player.getName() + "!");
										        			player.meleeDamage(
										        				enemy.attack(player));
										        			if (cloakOfFire)
										        			{
										        				System.out.println(enemyName + " is hurt "
										        						+ "by the flames!");
											        			enemy.damage(mgk.cloakOfFire(player), player, "fire");
										        			}
										        			if (player.getHP() < 1)
											        		{
											        			System.out.println(player.getName()
											        					+ " has died!");
											        			System.exit(0);
											        		}
						        						}
						        					}
						        					else
						        					{
						        						System.out.println(enemyName + " missed "
									        					+ player.getName());
						        					}
						        				}
						        				else if (enemyName.equals("Lich"))
						        				{
						        					int tactic = roll.d100();
						        					if (tactic <= 25)
						        					{
						        						System.out.println("The " + enemyName + " reaches to touch " + player.getName() + "!");
						        						if (enemy.attackRoll() >= player.getTouch())
						        						{
						        							System.out.println("The " + enemyName + " touched " + player.getName() + "!");
						        							player.elementDamage(roll.d8() + 5, "negative energy");
						        							
						        							if (player.savingThrow(Player.Saves.Fortitude, true, false) < 16)
						        							{
						        								System.out.println(player.getName() + " has been paralyzed!");
						        								player.setHP(0);
						        								player.checkDeath();
						        							}
						        							else
						        							{
						        								System.out.println(player.getName() + " resisted paralysis!");
						        							}
						        						}
						        						else
						        						{
						        							System.out.println("The " + enemyName + " missed " + player.getName() + "!");
						        						}
						        					}
						        					else if (tactic <= 50)
						        					{
						        						System.out.println("The " + enemyName + " fires a lightning bolt at " + player.getName() + "!");
						        						int damage = roll.XdY(10, 6);
						        						
						        						if (player.savingThrow(Player.Saves.Reflex, true, false) >= 17)
						        						{
						        							System.out.println(player.getName() + " rolled away from the spell!");
						        							damage /= 2;
						        						}
						        						
						        						player.elementDamage(damage, "electricity");
						        					}
						        					else if (tactic <= 75)
						        					{
						        						System.out.println("The " + enemyName + " shoots a spear of ice at " + player.getName() + "!");
						        						
						        						if (enemy.attackRoll() >= player.getTouch())
						        						{
						        							System.out.println("The icy spear hit " + player.getName() + "!");
						        							player.elementDamage(roll.XdY(11, 8) + 4, "cold");
						        						}
						        						else
						        						{
						        							System.out.println("The icy spear missed " + player.getName() + "!");
						        						}
						        					}
						        					else
						        					{
						        						System.out.println("The " + enemyName + " releases five magic missiles!");
						        						player.elementDamage(roll.XdY(5, 4) + 9, "force");
						        					}
						        				}
						        				else if (enemyName.equals("Shadow Elemental"))
						        				{
						        					if (enemy.attackRoll() >= player.getTouch())
						        					{
						        						System.out.println("The " + enemyName + " touched " + player.getName() + "!");
						        						player.elementDamage(roll.d8(), null);
						        						player.elementDamage(roll.d8(), "cold");
						        					}
						        					else
						        					{
						        						System.out.println("The " + enemyName + " missed " + player.getName() + "!");
						        					}
						        				}
						        				else
						        				{
						        					if (enemy.attackRoll() >= player.getAC())
									        		{
						        						playerWasHit = true;
									        			System.out.println(enemyName + " hit "
									        					+ player.getName() + "!");
									        			player.meleeDamage(
									        				enemy.attack(player));
									        			if (cloakOfFire)
									        			{
									        				System.out.println(enemyName + " is hurt "
									        						+ "by the flames!");
										        			enemy.damage(mgk.cloakOfFire(player), player, "fire");
									        			}
									        			if (player.getHP() < 1)
										        		{
										        			System.out.println(player.getName()
										        					+ " has died!");
										        			System.exit(0);
										        		}
									        			else
									        			{
									        				if (enemyName.equals("Bear"))
									        				{
									        					if (player.savingThrow(Player.Saves.Fortitude, false, false) < 15)
									        					{
									        						System.out.println(player.
									        								getName() + " has "
									        								+ "suffered a bleeding "
									        								+ "wound!");
									        						bleedingWound += 2;
									        					}
									        				}
									        				else if (enemyName.equals("Wolf"))
									        				{
									        					if (player.getStr() > player.getDex())
									        					{
									        						if (player.strCheck() < enemy.dexRoll())
									        						{
									        							System.out.println("The wolf trips "
									        									+ player.getName() + "!");
									        							if (enemy.attackRoll() >= (player.getAC() - 2))
									        							{
									        								System.out.println(enemyName + " hit "
														        					+ player.getName() + "!");
									        								player.meleeDamage(
									    					        				enemy.attack(player));
									        							}
									        							else
									        							{
									        								System.out.println("The wolf missed "
									        										+ player.getName());
									        							}
									        						}
									        					}
									        					else
									        					{
									        						if (player.dexCheck() < enemy.dexRoll())
									        						{
									        							System.out.println("The wolf trips " 
									        									+ player.getName() + "!");
									        							if (enemy.attackRoll() >= (player.getAC() - 2))
									        							{
									        								System.out.println(enemyName + " hit "
														        					+ player.getName() + "!");
									        								player.meleeDamage(
									    					        				enemy.attack(player));
									        							}
									        							else
									        							{
									        								System.out.println("The wolf missed " 
									        										+ player.getName());
									        							}
									        						}
									        					}
									        					if (player.getHP() < 1)
												        		{
												        			System.out.println(player.getName()
												        					+ " has died!");
												        			System.exit(0);
												        		}
									        				}
									        				else if (enemyName.equals("Giant Crocodile"))
									        				{
									        					if (player.attackRoll(enemy.getType()) < enemy.attackRoll())
									        					{
									        						int damage = roll.d8() + roll.d8() + 5;
									        						System.out.println("The " + enemyName + " crushes one of " + player.getName()
									        								+ "s limbs between its monstrous teeth, \ndealing " + damage + " damage!");
									        						player.meleeDamage(damage);
									        					}
 									        				}
									        				else if (enemyName.length() >= 5 && enemyName.substring(0, 5).equals("Mummy"))
									        				{
									        					if (player.savingThrow(Player.Saves.Fortitude, true, false) < enemy.getDC() && player.hasMummyRot() == false)
									        					{
									        						System.out.println("You have contracted mummy rot!");
									        						player.setMummyRot(true);
									        					}
									        				}
									        				else if (enemyName.equals("Dire Bear"))
									        				{
									        					if (player.savingThrow(Player.Saves.Fortitude, false, false) < enemy.getDC())
									        					{
									        						System.out.println(player.getName() + " has been mauled!");
									        						player.poison("Strength", 1, 3, false);
									        						player.poison("Dexterity", 1, 3, false);
									        						player.poison("Constitution", 1, 3, false);
									        					}
									        				}
									        			}
									        			if (enemy.startsGrapples() && (enemy.getGrappleThreat(player).equals("pushover") == false && enemy.getGrappleThreat(player).equals("mild") == false))
								        				{
								        					System.out.println("The " + enemyName + " attempts to grab hold of " + player.getName() + "!");
									        				int playerGrapple = player.grappleRoll();
									        				int enemyGrapple = enemy.grappleRoll();
								        					if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
									        				{
									        					System.out.println("The " + enemyName + " has started a grapple!");
									        					grapple(player, enemy, Magic, inv);
									        				}
									        				else
									        				{
									        					System.out.println(player.getName() + " fought off the " + enemyName + "!");
									        				}
								        				}
									        		}
									        		else
									        		{
									        			System.out.println(enemyName + " missed "
									        					+ player.getName());
									        		}
						        				}
					        				}
					        				else
					        				{
					        					if (enemy.grappleRoll() > mgk.forceHand(player, false))
					        					{
					        						pinned = false;
					        						System.out.println("The " + enemyName
					        								+ " has broken out of the pin!");
					        					}
					        					else
					        					{
					        						System.out.println(enemyName + " is still pinned!");
					        					}
					        				}
				        				}
				        				else
				        				{
				        					if (enemy.attackRoll() >= avalancheDC)
				        					{
				        						System.out.println("The " + enemyName + " has dug itself out!");
				        						avalanche = false;
				        					}
				        					else
				        					{
				        						System.out.println("The " + enemyName + " is still buried!");
				        						enemy.damage(mgk.avalanche(player, weakToFrost), player, "cold");
				        					}
				        				}
			        				}
			        				else
			        				{
			        					System.out.println("The " + enemyName + " is being crushed!");
			        					enemy.damage(mgk.crushingPrison(player), player, "force");
			        					if (crushingPrison == 1)
			        					{
			        						System.out.println("The crushing prison vanishes!");
			        					}
			        					crushingPrison -= 1;
			        				}
		        				}
		        				else
		        				{
		        					if (mgk.isDrowning(player, enemy, drownDC))
		        					{
		        						if (drowning == 4)
		        						{
		        							System.out.println("The " + enemyName + " has drowned!");
		        							enemy.setHP(0);
		        						}
		        						else
		        						{
		        							System.out.println("The " + enemyName + " is still drowning!");
		        							enemy.setHP((enemy.getHP() / 2));
			        						drowning++;
		        						}
		        					}
		        					else
		        					{
		        						System.out.println("The " + enemyName + " coughs up some water!");
		        						drowning = 0;
		        					}
		        				}
		        			}
		        			else
		        			{
		        				if (holdPerson)
		        				{
		        					if (enemy.willSave() < player.getSpellDC(
			        						Magic.getCost("Hold Person")))
			        						{
			        							System.out.println(enemyName
			        									+ " is still paralyzed!");
			        						}
			        				else
			        				{
			        					System.out.println(enemyName + " is no "
			        							+ "longer paralyzed!");
			        					holdPerson = false;
			        				}
		        				}
		        				else if (holdMonster)
		        				{
		        					if (enemy.willSave() < player.getSpellDC(
			        						Magic.getCost("Hold Monster")))
			        						{
			        							System.out.println(enemyName
			        									+ " is still paralyzed!");
			        						}
			        				else
			        				{
			        					System.out.println(enemyName + " is no "
			        							+ "longer paralyzed!");
			        					holdMonster = false;
			        				}
		        				}
		        			}
		        		}
		        		else
		        		{
		        			if (frozen == 1)
		        			{
		        				System.out.println(enemyName + " has "
		        						+ "thawed out!");
		        				frozen--;
		        			}
		        			else
		        			{
		        				System.out.println(enemyName + " is "
			        					+ " still frozen!");
			        			frozen--;
		        			}
		        		}
		        		if (iceArmor)
		        		{
		        			player.iceArmorMelt();
		        			if (player.getIceArmor() == 0)
		        				iceArmor = false;
		        		}
		        	}
			        endOfTurnEffects(player, enemy);
		        }
		        if (haste > 0)
		        {
		        	if (counterHit)
		        	{
		        		haste -= 1;
		        	}
		        }
		        if (player.getHP() < 1)
        		{
        			System.out.println(player.getName()
        					+ " has died!");
        			System.exit(0);
        		}
		    }
		if (battleInput.equalsIgnoreCase("flee") ||
				spell.equalsIgnoreCase("Dimension Door"))
			{
			System.out.println("Successfully escaped!");
			if (polymorph)
			{
				System.out.println("Polymorph has worn off!");
				player.reversePolymorph();
				polymorph = false;
			}
			if (mageArmor)
			{
				player.reverseMageArmor(false);
				mageArmor = false;
			}
			if (cloakOfFire)
			{
				System.out.println("Cloak of Fire has worn off!");
				cloakOfFire = false;
			}
			if (iceArmor)
			{
				player.reverseIceArmor();
				iceArmor = false;
			}
			
			if (rage)
			{
				System.out.println(player.getName() + " has calmed down.");
				player.reverseRage();
				rage = false;
			}
			escaped = true;
			player.minHP();
		}
		else
		{
			if (enemy.getName().equals("Chraal"))
			{
				System.out.println("The " + enemy.getName() + " explodes, sending shards of ice everywhere!");
				int totalDamage = 10;
				if (player.savingThrow(Player.Saves.Reflex, false, false) >= enemy.getDC())
				{
					System.out.println(player.getName() + " dodged some of the shards!");
					totalDamage = 5;
				}
				System.out.println("The " + enemy.getName() + " did " + totalDamage + " damage to " + player.getName() + "!");
				player.meleeDamage(totalDamage);
				System.out.println("The " + enemy.getName() + " did " + totalDamage + " cold damage to " + player.getName() + "!");
				player.elementDamage(totalDamage, "cold");
			}
			
			System.out.println(enemyName + " was defeated!");
			
			player.changeGold(enemy.getGPValue());
			
			if (player.getAccessory().equals("Lucky Amulet"))
			{
				int rng = roll.d100();
				
				if (rng <= 50)
				{
					rng = roll.d100();
					System.out.println("Found an extra " + rng + " gold!");
					
					player.setGold(player.getGold() + rng);
				}
			}
			
			player.addXP(enemy.getXPValue());
			
			
			
			if (enemyName.equals("Skeleton") || enemyName.equals("Zombie"))
			{
				try
				{
					if (player.getQuestID().equals("Skeleton"))
					{
						player.questOneDown();
					}
				}
				catch (java.lang.NullPointerException error)
				{
					
				}
			}
			
			try
			{
				if (player.getQuestID().equals(enemyName) && enemyName.equals("Skeleton") == false)
				{
					player.questOneDown();
				}
			}
			catch (java.lang.NullPointerException error)
			{
				
			}
			
			try
			{
				if (player.getQuestID().equals("Shadow"))
				{
					if (enemyName.contains("Shadow") || enemyName.equals("Dusk Beast")
							|| enemyName.equals("Nightcrawler") || enemyName.equals("Nightwalker")
							|| enemyName.equals("Dark Lion"))
					{
						player.questOneDown();
					}
				}
			}
			catch (java.lang.NullPointerException error)
			{
				
			}
			
			enemy.loot(inv, player);
			
			if (player.getAccessory().equals("Lucky Amulet"))
			{
				enemy.loot(inv, player);
			}
			
			if (polymorph)
			{
				System.out.println("Polymorph has worn off!");
				player.reversePolymorph();
				polymorph = false;
			}
			if (mageArmor && (player.hasMageArmor == false))
			{
				System.out.println("Mage Armor has worn off!");
				player.reverseMageArmor(false);
				mageArmor = false;
			}
			if (cloakOfFire)
			{
				System.out.println("Cloak of Fire has worn off!");
				cloakOfFire = false;
			}
			if (iceArmor)
			{
				player.reverseIceArmor();
				iceArmor = false;
			}
			
			if (rage)
			{
				System.out.println(player.getName() + " has calmed down.");
				player.reverseRage();
				rage = false;
			}
			player.minHP();
		}
		if (poison)
		{
			if (enemyName.equals("Dire Rat"))
			{
				if (player.savingThrow(Player.Saves.Fortitude, false, true) < enemy.getDC())
				{
					player.poison("Dexterity", 1, 3, true);
					player.poison("Constitution", 1, 3, true);
				}
			}
			if (enemyName.equals("Small Viper Snake") || enemyName.equals("Large Viper Snake")
					|| enemyName.equals("Huge Viper Snake") || enemyName.equals("Yuan-Ti Abomination"))
			{
				if (player.savingThrow(Player.Saves.Fortitude, false, true) < enemy.getDC())
				{
					player.poison("Constitution", 1, 6, true);
				}
			}
			if (enemyName.equals("Gravecrawler"))
			{
				if (player.savingThrow(Player.Saves.Fortitude, false, true) < enemy.getDC())
				{
					player.poison("Constitution", 1, 4, true);
				}
			}
		}
		if (strBuff > 0)
		{
			player.changeStat("Strength", -strBuff);
		}
		if (intBuff > 0)
		{
			player.changeStat("Intelligence", -intBuff);
		}
		System.out.println(player.getStats());
		
		if (!encounteredShadowCreature && (enemyName.equals("Shadow Centaur")))
		{
			encounteredShadowCreature = true;
		}
		
		rage = false;
		bleedingWound = 0;
		strBuff = 0;
		intBuff = 0;
		haste = 0;
		ignite = false;
		hammer = false;
		cloakOfFire = false;
		polymorph = false;
		inferno = 0;
		blizzard = 0;
		avalancheDC = 0;
		deadlyLaharDC = 0;
		infernoDC = 0;
		blizzardDC = 0;
		deadlyLahar = 0;
		iceArmor = false;
		holdPerson = false;
		poison = false;
		
		return escaped;
	}
	
	
	
	private static boolean grapple(Player player, Enemy enemy, Magic Magic, Inventory inv)
	{
		Scanner scan = new Scanner(System.in);
		Dice roll = new Dice();
		SpellEffects mgk = new SpellEffects();
		
		String command;
		boolean done = false;
		boolean playerWon = true;
		
		boolean pinned = false;
		
		while (!done)
		{
			boolean constrict = false;
			boolean counterHit = false;
			boolean turnEnd = false;
			String threatLevel = enemy.getGrappleThreat(player);
			
			if (enemy.getName().equals("Nightcrawler"))
			{
				System.out.println("\nLocation: Nightcrawlers mouth\n");
			}
			
			if (!pinned)
			{
				System.out.println("GRAPPLING");
				System.out.println(player.getStats());
				System.out.println("---------------------------------------------");
				System.out.println(enemy.getName() + "s HP: " + enemy.getHP());
				System.out.println("Threat Level: " + threatLevel);
				System.out.println("---------------------------------------------");
				System.out.print("\nWhat will you do? ");
				command = scan.nextLine();
				if (command.equalsIgnoreCase("help"))
				{
					System.out.println("attack              ----- attack the enemy unarmed");
					System.out.println("pin                 ----- pin the enemy, preventing them from doing anything for a turn. Also deals half unarmed damage");
					System.out.println("cast                ----- cast a spell, not all spells can be cast in a grapple");
					System.out.println("inventory           ----- see your inventory");
					System.out.println("item                ----- use an item, very few items can be used in a grapple");
					System.out.println("escape              ----- attempt to escape the grapple");
					System.out.println("spellbook           ----- see your spellbook, and which spells can be cast in a grapple");
				}
				else if (command.equalsIgnoreCase("spellbook"))
				{
					Magic.getSpells(true);
				}
				else if (command.equalsIgnoreCase("escape"))
				{
					int playerGrapple = player.grappleRoll();
					int enemyGrapple = enemy.grappleRoll();
					
					if (player.hasSlippery())
					{
						if (enemy.getSize() == Enemy.Size.Large)
							playerGrapple += 2;
						else if (enemy.getSize() == Enemy.Size.Huge)
							playerGrapple += 4;
						else if (enemy.getSize() == Enemy.Size.Gargantuan)
							playerGrapple += 6;
						else if (enemy.getSize() == Enemy.Size.Colossal)
							playerGrapple += 8;
					}
					
					if (holdPerson || holdMonster || playerGrapple > enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() >= enemy.getGrappleMod()))
					{
						System.out.println(player.getName() + " has escaped the grapple!");
						playerWon = false;
						done = true;
					}
					else
					{
						System.out.println(player.getName() + " could not escape the grapple!");
						counterHit = true;
						constrict = true;
						turnEnd = true;
					}
				}
				else if (command.equalsIgnoreCase("rage"))
		        {
		        	if (player.canRage())
		        	{
		        		if (player.getMP() >= 1)
		        		{
		        			if (rage)
			        		{
			        			System.out.println("It would have no effect");
			        		}
			        		else
			        		{
			        			rage = true;
				        		System.out.println(player.getName() + " has entered "
				        				+ "a rage!");
				        		player.rage();
				        		player.changeMP(-1);
				        		counterHit = true;
				        		turnEnd = true;
			        		}
		        		}
		        		else
		        		{
		        			System.out.println("You don't have enough MP.");
		        		}
		        		
		        	}
		        	else
		        	{
		        		System.out.println("You can't do this yet");
		        	}
		        }
				else if (command.equalsIgnoreCase("inventory"))
				{
					inv.getInventory(player.getGold(), 
		        			player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
				}
				else if (command.equalsIgnoreCase("item"))
				{
					System.out.println("What item will you use?");
		        	String battleInput = scan.nextLine();
		        	
		        	String item = inv.useItem(battleInput);
		        	inv.remItem(battleInput);
		        	if (item.equalsIgnoreCase("Blink Token"))
		        	{
		        		done = true;
		        	}
		        	else
		        	{
		        		System.out.println("That item is " +
		        				"unavailable.");
		        	}
				}
				else if (command.equalsIgnoreCase("cast"))
				{
					if (!rage)
					{
						Magic.getSpells(true);
						String spell;
						System.out.println("What will you cast? ");
			        	command = scan.nextLine();
			        	spell = Magic.useSpell(command,
			        			player.getMP());
						if (Magic.hasSpell(spell))
						{
							if (spell.equalsIgnoreCase("Cloak of fire"))
							{
								if (cloakOfFire)
				        		{
				        			System.out.println("You cannot cast this again "
				        					+ "right now");
				        		}
				        		else
				        		{
				        			System.out.println("Flames roar up from the "
				        					+ "ground around you, but they don't hurt "
				        					+ "you");
				        			cloakOfFire = true;
				        			counterHit = true;
				        			turnEnd = true;
				        		}
							}
							else if (spell.equalsIgnoreCase("Polymorph"))
							{
								if (polymorph)
				        		{
				        			System.out.println("You cannot cast this again "
				        					+ "right now");
				        			spell = null;
				        		}
				        		else
				        		{
				        			polymorph = true;
					        		player.polymorph();
				        		}
							}
							else if (spell.equalsIgnoreCase("Fireball"))
							{
								int totalDamage = mgk.fireball(player);
								enemy.damage(totalDamage, player, "fire");
								System.out.println("The explosion deals " + totalDamage + " damage to you!");
								player.elementDamage(totalDamage, "Fire");
							}
							else if (spell.equalsIgnoreCase("Light Burst"))
							{
								int damage = mgk.lightBurst(player);
				        		enemy.damage(damage, player, "light");
				        		
				        		if (enemy.fortSave() < player.getSpellDC(Magic.getCost(spell)))
			        			{
			        				System.out.println("The " + enemy.getName() + " has been partially blinded!");
			        				enemy.hitPenalty(4);
			        			}
							}
							else
							{
								System.out.println("You can't cast that spell while grappling.");
								spell = "none";
							}
							if (spell.equals("none") == false)
							{
								counterHit = true;
								turnEnd = true;
								player.changeMP(-Magic.getCost(spell));
							}
						}
						else
						{
							System.out.println("You don't know that spell.");
						}
					}
					else
					{
						System.out.println("You can't cast spells while raging.");
					}
				}
				else if (command.equalsIgnoreCase("attack"))
				{
					int playerGrapple = player.grappleRoll();
					int enemyGrapple = enemy.grappleRoll();
					
					if (holdPerson || holdMonster || playerGrapple > enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() >= enemy.getGrappleMod()))
					{
						System.out.println(player.getName() + " hit the " + enemy.getName() + "!");
						if (!hammer)
							enemy.meleeDamage(player.unarmedAttack(), unarmedAttack, player, false);
						else
						{
							enemy.meleeDamage(player.unarmedAttack(), unarmedAttack, player, false);
							enemy.damage(mgk.hammer(player), player, "force");
						}
					}
					else
					{
						System.out.println(player.getName() + " missed the " + enemy.getName() + "!");
						constrict = true;
					}
					counterHit = true;
					turnEnd = true;
				}
				else if (command.equalsIgnoreCase("pin"))
				{
					int playerGrapple = player.grappleRoll();
					int enemyGrapple = enemy.grappleRoll();
					
					if (holdPerson || holdMonster || playerGrapple > enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() >= enemy.getGrappleMod()))
					{
						System.out.println(player.getName() + " has pinned the " + enemy.getName() + "!");
						enemy.meleeDamage(player.unarmedAttack() / 2, unarmedAttack, player, false);
					}
					else
					{
						System.out.println(player.getName() + " couldn't pin the " + enemy.getName() + "!");
						counterHit = true;
						constrict = true;
					}
					turnEnd = true;
				}
			}
			else
			{
				System.out.println(player.getName() + " cannot act this turn!");
				pinned = false;
				counterHit = true;
				turnEnd = true;
			}
			if (constrict && enemy.canConstrict())
			{
				constrict = false;
				player.meleeDamage(enemy.constrictDamage(player));
			}
			
			
			if (counterHit && !holdPerson && !holdMonster)
			{
				if (!done)
				{
					if (enemy.getName().equals("Nightcrawler"))
					{
						System.out.println("The Nightcrawler attempts to swallow " + player.getName() + "!");
						
						if (enemy.grappleRoll() > player.grappleRoll())
						{
							System.out.println(player.getName() + " was swallowed!");
							int dmg = roll.XdY(2, 10);
							System.out.println(player.getName() + " took " + dmg + " damage!");
							player.meleeDamage(dmg);
							done = true;
							
							Combat(player, Magic, inv, "Nightcrawlers stomache", false);
							
							System.out.println(player.getName() + " ripped open the Nightcrawlers stomache!");
							dmg = roll.XdY(3, 8) + 15;
							enemy.damage(dmg, player, null);
						}
						else
						{
							System.out.println(player.getName() + " fought off the " + enemy.getName() + "!");
						}
					}
					else if (threatLevel.equals("pushover"))
					{
						if (enemy.getName().equals("Dark Lord"))
						{
							System.out.println("The " + enemy.getName() + " says something under "
    								+ "his breath and his muscles grow much larger!");
    						enemy.lucienBuff();
						}
						else
						{
							System.out.println("The " + enemy.getName() + " tries to escape the grapple!");
							int playerGrapple = player.grappleRoll();
							int enemyGrapple = enemy.grappleRoll();
							if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
							{
								System.out.println("The " + enemy.getName() + " has freed itself of the grapple!");
								constrict = true;
								done = true;
							}
							else
							{
								System.out.println("The " + enemy.getName() + " couldn't get away!");
							}
						}
					}
					else if (threatLevel.equals("mild"))
					{
						int tactic = roll.d100();
						if (tactic <= 60)
						{
							System.out.println("The " + enemy.getName() + " tries to escape the grapple!");
							int playerGrapple = player.grappleRoll();
							int enemyGrapple = enemy.grappleRoll();
							if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
							{
								System.out.println("The " + enemy.getName() + " has freed itself of the grapple!");
								done = true;
								constrict = true;
							}
							else
							{
								System.out.println("The " + enemy.getName() + " couldn't get away!");
							}
						}
						else
						{
							if (enemy.getName().equals("Dark Lord"))
							{
								System.out.println("The " + enemy.getName() + " says something under "
	    								+ "his breath and his muscles grow much larger!");
	    						enemy.lucienBuff();
							}
							else
							{
								System.out.println("The " + enemy.getName() + " attacks " + player.getName() + "!");
								int playerGrapple = player.grappleRoll();
								int enemyGrapple = enemy.grappleRoll();
								if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
								{
									playerWasHit = true;
									System.out.println("The " + enemy.getName() + " hits " + player.getName() + "!");
									player.meleeDamage(enemy.attack(player));
									constrict = true;
								}
								else
								{
									System.out.println("The " + enemy.getName() + " misses " + player.getName() + "!");
								}
							}
						}
					}
					else if (threatLevel.equals("moderate"))
					{
						int tactic = roll.d100();
						if (tactic <= 60)
						{
							System.out.println("The " + enemy.getName() + " attacks " + player.getName() + "!");
							int playerGrapple = player.grappleRoll();
							int enemyGrapple = enemy.grappleRoll();
							if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
							{
								playerWasHit = true;
								System.out.println("The " + enemy.getName() + " hits " + player.getName() + "!");
								player.meleeDamage(enemy.attack(player));
								constrict = true;
							}
							else
							{
								System.out.println("The " + enemy.getName() + " misses " + player.getName() + "!");
							}
						}
						else if (tactic <= 80)
						{
							if (enemy.getName().equals("Dark Lord"))
							{
								System.out.println("The " + enemy.getName() + " says something under "
	    								+ "his breath and his muscles grow much larger!");
	    						enemy.lucienBuff();
							}
							else
							{
								System.out.println("The " + enemy.getName() + " tries to escape the grapple!");
								int playerGrapple = player.grappleRoll();
								int enemyGrapple = enemy.grappleRoll();
								if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
								{
									System.out.println("The " + enemy.getName() + " has freed itself of the grapple!");
									done = true;
									constrict = true;
								}
								else
								{
									System.out.println("The " + enemy.getName() + " couldn't get away!");
								}
							}
						}
						else
						{
							System.out.println("The " + enemy.getName() + " tries to pin " + player.getName() + "!");
							int playerGrapple = player.grappleRoll();
							int enemyGrapple = enemy.grappleRoll();
							if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
							{
								System.out.println("The " + enemy.getName() + " has pinned " + player.getName() + "!");
								player.meleeDamage(enemy.attack(player) / 2);
								pinned = true;
								constrict = true;
							}
							else
							{
								System.out.println("The " + enemy.getName() + " couldn't pin " + player.getName() + "!");
							}
						}
					}
					else if (threatLevel.equals("dangerous"))
					{
						int tactic = roll.d100();
						if (tactic <= 55)
						{
							System.out.println("The " + enemy.getName() + " attacks " + player.getName() + "!");
							int playerGrapple = player.grappleRoll();
							int enemyGrapple = enemy.grappleRoll();
							if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
							{
								playerWasHit = true;
								System.out.println("The " + enemy.getName() + " hits " + player.getName() + "!");
								player.meleeDamage(enemy.attack(player));
								constrict = true;
							}
							else
							{
								System.out.println("The " + enemy.getName() + " misses " + player.getName() + "!");
							}
						}
						else if (tactic <= 60)
						{
							if (enemy.getName().equals("Dark Lord"))
							{
								System.out.println("The " + enemy.getName() + " says something under "
	    								+ "his breath and his muscles grow much larger!");
	    						enemy.lucienBuff();
							}
							else
							{
								System.out.println("The " + enemy.getName() + " tries to escape the grapple!");
								int playerGrapple = player.grappleRoll();
								int enemyGrapple = enemy.grappleRoll();
								if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
								{
									System.out.println("The " + enemy.getName() + " has freed itself of the grapple!");
									done = true;
									constrict = true;
								}
								else
								{
									System.out.println("The " + enemy.getName() + " couldn't get away!");
								}
							}
						}
						else
						{
							System.out.println("The " + enemy.getName() + " tries to pin " + player.getName() + "!");
							int playerGrapple = player.grappleRoll();
							int enemyGrapple = enemy.grappleRoll();
							if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
							{
								System.out.println("The " + enemy.getName() + " has pinned " + player.getName() + "!");
								player.meleeDamage(enemy.attack(player) / 2);
								pinned = true;
								constrict = true;
							}
							else
							{
								System.out.println("The " + enemy.getName() + " couldn't pin " + player.getName() + "!");
							}
						}
					}
					else if (threatLevel.equals("severe"))
					{
						int tactic = roll.d100();
						if (tactic <= 64)
						{
							System.out.println("The " + enemy.getName() + " attacks " + player.getName() + "!");
							int playerGrapple = player.grappleRoll();
							int enemyGrapple = enemy.grappleRoll();
							if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
							{
								playerWasHit = true;
								System.out.println("The " + enemy.getName() + " hits " + player.getName() + "!");
								player.meleeDamage(enemy.attack(player));
								constrict = true;
							}
							else
							{
								System.out.println("The " + enemy.getName() + " misses " + player.getName() + "!");
							}
						}
						else if (tactic == 65)
						{
							System.out.println("The " + enemy.getName() + " tries to escape the grapple!");
							int playerGrapple = player.grappleRoll();
							int enemyGrapple = enemy.grappleRoll();
							if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
							{
								System.out.println("The " + enemy.getName() + " has freed itself of the grapple!");
								done = true;
								constrict = true;
							}
							else
							{
								System.out.println("The " + enemy.getName() + " couldn't get away!");
							}
						}
						else
						{
							System.out.println("The " + enemy.getName() + " tries to pin " + player.getName() + "!");
							int playerGrapple = player.grappleRoll();
							int enemyGrapple = enemy.grappleRoll();
							if (playerGrapple < enemyGrapple || ((playerGrapple == enemyGrapple) && player.getGrappleMod() <= enemy.getGrappleMod()))
							{
								System.out.println("The " + enemy.getName() + " has pinned " + player.getName() + "!");
								player.meleeDamage(enemy.attack(player) / 2);
								pinned = true;
								constrict = true;
							}
							else
							{
								System.out.println("The " + enemy.getName() + " couldn't pin " + player.getName() + "!");
							}
						}
					}
				}
			}
			else if (holdPerson || holdMonster)
			{
				if ((holdPerson && enemy.willSave() < player.getSpellDC(3)) || ((holdMonster && enemy.willSave() < player.getSpellDC(5))))
				{
					System.out.println("The " + enemy.getName() + " is still paralyzed!");
				}
				else
				{
					System.out.println("The " + enemy.getName() + " is no longer paralyzed!");
					holdPerson = false;
					holdMonster = false;
				}
			}
			if (constrict && enemy.canConstrict())
			{
				player.meleeDamage(enemy.constrictDamage(player));
			}
			if (turnEnd)
			{
				if (iceArmor)
        		{
        			player.iceArmorMelt();
        			if (player.getIceArmor() == 0)
        				iceArmor = false;
        		}
				if (cloakOfFire)
    			{
    				System.out.println("The " + enemy.getName() + " is hurt "
    						+ "by the cloak of fire!");
        			enemy.damage(mgk.cloakOfFire(player), player, "fire");
    			}
				if (ignite)
	        	{
	        		System.out.println(player.getName() + " and the " + enemy.getName() + " are hurt by the flames!");
	        		int totalDamage = mgk.ignite(player);
	        		enemy.damage(totalDamage, player, "fire");
	        		player.elementDamage(totalDamage, "Fire");
	        	}
				if (enemy.getHP() == 0)
				{
					done = true;
				}
				endOfTurnEffects(player, enemy);
			}
		}
		return playerWon;
	}
	
	private static void endOfTurnEffects(Player player, Enemy enemy)
	{
		roundCounter++;
		SpellEffects mgk = new SpellEffects();
		Dice roll = new Dice();
		
		if (playerWasHit)
		{
			playerWasHit = false;
			if (enemy.getName().equals("Small Viper Snake") || enemy.getName().equals("Large Viper Snake")
					|| enemy.getName().equals("Huge Viper Snake") || enemy.getName().equals("Yuan-Ti Abomination"))
			{
				if (player.savingThrow(Player.Saves.Fortitude, false, true) < enemy.getDC())
				{
					poison = true;
					System.out.println(player.getName() + " has been poisoned!");
					player.poison("Constitution", 1, 6, true);
				}
			}
			else if (enemy.getName().equals("Dire Rat"))
			{
				if (player.savingThrow(Player.Saves.Fortitude, false, true) < enemy.getDC())
				{
					poison = true;
					System.out.println(player.getName() + " has been poisoned!");
					player.poison("Dexterity", 1, 3, true);
					player.poison("Constitution", 1, 3, true);
				}
			}
			else if (enemy.getName().equals("Gravecrawler"))
			{
				if (player.savingThrow(Player.Saves.Fortitude, false, true) < enemy.getDC())
				{
					poison = true;
					System.out.println(player.getName() + " has been poisoned!");
					player.poison("Constitution", 1, 4, true);
				}
			}
		}
		
		enemy.fastHealing();
		
		if (bleedingWound > 0)
        {
			int total = bleedingWound - player.getDR();
			if (total < 0)
				total = 0;
        	System.out.println(player.getName() + " took "
        			+ total + " damage from the " 
        			+ "bleeding wound!");
        	player.elementDamage(bleedingWound, "Bleeding");
        }
    	if (deadlyLahar > 0)
    	{
    		if (enemy.reflexSave() < deadlyLaharDC)
    		{
    			enemy.damage(mgk.deadlyLahar(player), player, "fire");
    		}
    		else
    		{
    			System.out.println("The " + enemy.getName() + " shielded itself from the cloud!");
    			enemy.damage(mgk.deadlyLahar(player) / 2, player, "fire");
    		}
    		if (deadlyLahar == 1)
    			System.out.println("The Deadly Lahar has dissipated!");
    		deadlyLahar -= 1;
    	}
    	if (inferno > 0)
    	{
    		if (enemy.reflexSave() < infernoDC)
    		{
    			System.out.println("The inferno burns the " + enemy.getName() + "!");
    			enemy.damage(mgk.inferno(player, false), player, "fire");
    		}
    		else
    		{
    			System.out.println("The " + enemy.getName() + " shielded itself from the inferno!");
    			enemy.damage(mgk.inferno(player, false) / 2, player, "fire");
    		}
    		if (inferno == 1)
    			System.out.println("The inferno has vanished!");
    		inferno -= 1;
    	}
    	if (blizzard > 0)
    	{
    		int numShards = roll.d4();
    		for (int index = 0; index < numShards; index++)
    		{
    			if (roll.d20() + blizzardDC >= enemy.getAC())
    			{
    				System.out.println("A shard of ice hit the " + enemy.getName() + "!");
    				enemy.damage(mgk.blizzard(player), player, "cold");
    			}
    		}
    		if (blizzard == 1)
    			System.out.println("The blizzard vanishes!");
    		blizzard -= 1;
    	}
    	if (enemy.getName().equals("Gravecrawler"))
    	{
    		if (player.savingThrow(Player.Saves.Fortitude, true, false) < enemy.getDC())
    		{
    			System.out.println(player.getName() + "s skin slightly calcifies!");
    			player.poison("Constitution", 1, 2, false);
    		}
    	}
        player.checkDeath();
        
        player.revertExpertise();
	}
}
