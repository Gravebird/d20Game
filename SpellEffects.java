
public class SpellEffects {

	private int damage;
	
	Dice roll = new Dice();
	
	public int spellDuration(int num)
	{
		return roll.dX(num);
	}
	
	//Fire school
	public int burningHands(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() <= 4)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d4();
			}
		}
		else
		{
			for (int index = 0; index < 5; index++)
			{
				damage += roll.d4();
			}
		}
		if (damage <= 0)
			damage = 1;
		return damage;
	}
	public int scorchingBlast(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() <= 4)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d8();
			}
		}
		else
		{
			for (int index = 0; index < 5; index++)
			{
				damage += roll.d8();
			}
		}
		if (damage <= 0)
			damage = 1;
		return damage;
	}
	public int fireball(Player player)
	{
		int damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 10)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d6();
			}
		}
		else
		{
			for (int index = 0; index < 10; index++)
			{
				damage += roll.d6();
			}
		}
		return damage;
	}
	public int ignite(Player player)
	{
		return roll.d6() + roll.d6() + player.getMagicBonusDamage();
	}
	public int incendiaryCloud(Player player)
	{
		damage = player.getMagicBonusDamage();
		for (int index = 0; index < 4; index++)
		{
			damage += roll.d6();
		}
		return damage;
	}
	public int conflagration(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 15)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d8();
			}
		}
		else
		{
			for (int index = 0; index < 15; index++)
			{
				damage += roll.d8();
			}
		}
		return damage;
	}
	public int magmaSpray(Player player, boolean save)
	{
		damage = player.getMagicBonusDamage();
		if (save)
		{
			damage =+ roll.d6() + roll.d6();
		}
		else
		{
			if (player.getCasterLevel() < 20)
			{
				for (int index = 0; index < player.getCasterLevel(); index++)
				{
					damage += roll.d12();
				}
			}
			else
			{
				for (int index = 0; index < 20; index++)
				{
					damage += roll.d12();
				}
			}
		}
		return damage;
	}
	public int deadlyLahar(Player player)
	{
		int damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 15)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d6();
			}
		}
		else
		{
			for (int index = 0; index < 15; index++)
			{
				damage += roll.d6();
			}
		}
		return damage;
	}
	public int inferno(Player player, boolean blast)
	{
		int damage = player.getMagicBonusDamage();
		int numDice = 0;
		
		if (player.getCasterLevel() < 25)
			numDice = player.getCasterLevel();
		else
			numDice = 25;
		
		if (blast)
		{
			for (int index = 0; index < numDice; index++)
			{
				damage += roll.d8();
			}
		}
		else
		{
			for (int index = 0; index < numDice / 2; index++)
			{
				damage += roll.d8();
			}
		}
		return damage;
	}
	
	// Force school
	public int magicMissile(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() == 1)
		{
			damage += roll.d4() + 1;
		}
		else if (player.getCasterLevel() <= 8)
		{
			for (int index = 0; index < ((player.getCasterLevel() + 1) / 2); index++)
			{
				damage += roll.d4() + 1;
			}
		}
		else
		{
			for (int index = 0; index < 5; index++)
			{
				damage += roll.d4() + 1;
			}
		}
		if (damage <= 0)
			damage = 1;
		return damage;
	}
	public int spiritSword(Player player)
	{
		damage = player.getMagicBonusDamage();
		int rng = roll.d20();
		if (player.getCasterLevel() <= 4)
		{
			for (int index = 0; index < (player.getCasterLevel() / 2); index++)
			{
				damage += roll.d6() + 1;
			}
		}
		else
		{
			for (int index = 0; index < 3; index++)
			{
				damage += roll.d6() + 1;
			}
		}
		if (rng > 18)
		{
			System.out.println("A critical hit!");
			damage = damage * 2;
		}
		if (damage <= 0)
			damage = 1;
		return damage;
	}
	public int arrowOfForce(Player player)
	{
		int damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 10)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d4();
			}
		}
		else
		{
			for (int index = 0; index < 10; index++)
			{
				damage += roll.d4();
			}
		}
		return damage;
	}
	public int fistOfForce(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 15)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d6();
			}
		}
		else
		{
			for (int index = 0; index < 15; index++)
			{
				damage += roll.d6();
			}
		}
		return damage;
	}
	public int forceHand(Player player, boolean attack)
	{
		int damage = player.getMagicBonusDamage();
		if (attack)
		{
			if (player.getCasterLevel() < 15)
			{
				for (int index = 0; index < player.getCasterLevel(); index++)
				{
					damage += roll.d8();
				}
			}
			else
			{
				for (int index = 0; index < 15; index++)
				{
					damage += roll.d8();
				}
			}
		}
		else
		{
			damage += roll.d20() + (player.getCasterLevel() / 2);
		}
		return damage;
	}
	public int forceRupture(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 15)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d4();
			}
		}
		else
		{
			for (int index = 0; index < 15; index++)
			{
				damage += roll.d4();
			}
		}
		return damage;
	}
	public int forceScythe(Player player)
	{
		damage = player.getMagicBonusDamage();
		int critical = roll.d100();
		
		if (player.getCasterLevel() < 20)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d4();
			}
		}
		else
		{
			for (int index = 0; index < 20; index++)
			{
				damage += roll.d4();
			}
		}
		if (critical >= 76)
		{
			System.out.println("A critical hit!");
			damage = damage * 2;
		}
		return damage;
	}
	public int crushingPrison(Player player)
	{
		int damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() / 4 < 5)
		{
			for (int index = 0; index < (player.getCasterLevel() / 4); index++)
			{
				damage += roll.d4();
			}
		}
		else
		{
			for (int index = 0; index < 5; index++)
			{
				damage += roll.d4();
			}
		}
		return damage;
	}
	public int forceSpear(Player player)
	{
		int damage = player.getMagicBonusDamage();
		int numDice = 0;
		int critical = roll.d100();
		
		if (player.getCasterLevel() < 25)
			numDice = player.getCasterLevel();
		else
			numDice = 25;
		
		for (int index = 0; index < numDice; index++)
		{
			damage += roll.d6();
		}
		if (critical >= 61)
		{
			System.out.println("A critical hit!");
			damage = damage * 2;
		}
		
		return damage;
	}
	
	// Ice school
	public int rayOfFrost(Player player, boolean weakToFrost)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() <= 4)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d6();
			}
		}
		else
		{
			for (int index = 0; index < 5; index++)
			{
				damage += roll.d6();
			}
		}
		if (damage <= 0)
			damage = 1;
		if (weakToFrost)
		{
			damage += hypothermia(player);
		}
		return damage;
	}
	public int iceSpike(Player player, boolean weakToFrost)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() <= 4)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d8();
			}
		}
		else
		{
			for (int index = 0; index < 5; index++)
			{
				damage += roll.d8();
			}
		}
		if (damage <= 0)
			damage = 1;
		if (weakToFrost)
		{
			damage += hypothermia(player);
		}
		return damage;
	}
	public int freezingRay(Player player, boolean weakToFrost)
	{
		int damage = player.getMagicBonusDamage();
		for (int index = 0; index < (player.getCasterLevel() / 2) 
				&& index < 5; index++)
		{
			damage += roll.d6();
		}
		if (weakToFrost)
		{
			damage += hypothermia(player);
		}
		return damage;
	}
	public int coneOfCold(Player player, boolean weakToFrost)
	{
		int damage = (player.getInt() - 10) / 2;
		if (player.getCasterLevel() < 15)
		{
			for (int index  = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d6();
			}
		}
		else
		{
			for (int index = 0; index < 15; index++)
			{
				damage += roll.d6();
			}
		}
		if (weakToFrost)
		{
			damage += hypothermia(player);
		}
		return damage;
	}
	public int deepFreeze(Player player, boolean weakToFrost)
	{
		int damage = player.getMagicBonusDamage();
		for (int index = 0; index < 8; index++)
		{
			damage += roll.d4();
		}
		if (weakToFrost)
		{
			damage += hypothermia(player);
		}
		return damage;
	}
	public int avalanche(Player player, boolean weakToFrost)
	{
		int damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 20)
		{
			for (int index = 0; index < player.getCasterLevel() / 2; index++)
			{
				damage += roll.d6();
			}
		}
		else
		{
			for (int index = 0; index < 10; index++)
			{
				damage = roll.d6();
			}
		}
		if (weakToFrost)
		{
			damage += hypothermia(player);
		}
		return damage;
	}
	public int hypothermia(Player player)
	{
		int damage = 0;
		for (int index = 0; index < (player.getCha() - 10) / 2; index++)
		{
			damage += roll.d6();
		}
		if (damage == 0)
			damage += roll.d6();
		
		damage += player.getMagicBonusDamage();
		
		return damage;
	}
	public int blizzard(Player player)
	{
		int damage = player.getMagicBonusDamage();
		int numDice = 0;
		
		if (player.getCasterLevel() < 25)
			numDice = player.getCasterLevel() / 5;
		else
			numDice = 5;
		
		for (int index = 0; index < numDice / 4; index++)
		{
			damage += roll.d6();
		}
		return damage;
	}
	
	// Bonus spells
	public int flashFreeze(Player player)
	{
		damage = player.getMagicBonusDamage();
		damage += roll.d6() + roll.d6() + roll.d6();
		return damage;
	}
	public int spark(Player player)
	{
		damage = player.getMagicBonusDamage();
		int maximum = 5;
		if (player.getCasterLevel() < 5)
		{
			maximum = player.getCasterLevel();
		}
		
		for (int index = 0; index < maximum; index++)
		{
			damage += roll.d3();
		}
		return damage;
	}
	public int lightningBolt(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 10)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d6();
			}
		}
		else
		{
			for (int index = 0; index < 10; index++)
			{
				damage += roll.d6();
			}
		}
		return damage;
	}
	public int cloakOfFire(Player player)
	{
		return roll.d6() + player.getMagicBonusDamage();
	}
	public int sonicEcho(Player player, String lastSpell)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 10)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d4();
			}
		}
		else
		{
			for (int index = 0; index < 10; index++)
			{
				damage += roll.d4();
			}
		}
		if (lastSpell.contains("Sonic"))
		{
			System.out.println("The blast draws strength from the reverberating sound "
					+ "in the air.");
			damage = damage * 2;
		}
		return damage;
	}
	public int sonicBoom(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 15)
		{
			for (int index = 0; index < player.getCasterLevel(); index++)
			{
				damage += roll.d6();
			}
		}
		else
		{
			for (int index = 0; index < 15; index++)
			{
				damage += roll.d6();
			}
		}
		return damage;
	}
	public int shadowSlash(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() / 2 < 5)
		{
			for (int index = 0; index < (player.getCasterLevel() / 2); index++)
			{
				damage += roll.d6();
			}
		}
		else
		{
			for (int index = 0; index < 5; index++)
			{
				damage += roll.d6();
			}
		}
		return damage;
	}
	
	// Healing
	public int mend(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() <= 5)
		{
			damage += player.getCasterLevel();
		}
		else
		{
			damage += 5;
		}
		
		if (Battle.bleedingWound > 0)
		{
			Battle.bleedingWound -= 1;
			if (Battle.bleedingWound < 0)
			{
				Battle.bleedingWound = 0;
			}
		}
		
		damage += roll.d8();
		return damage;
	}
	public int cure(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() <= 10)
		{
			damage += player.getCasterLevel();
		}
		else
		{
			damage += 10;
		}
		
		if (Battle.bleedingWound > 0)
		{
			Battle.bleedingWound -= 2;
			if (Battle.bleedingWound < 0)
			{
				Battle.bleedingWound = 0;
			}
		}
		
		damage += roll.d8() + roll.d8();
		return damage;
	}
	public int heal(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() <= 15)
		{
			damage += player.getCasterLevel();
		}
		else
		{
			damage += 15;
		}
		
		if (Battle.bleedingWound > 0)
		{
			Battle.bleedingWound -= 3;
			if (Battle.bleedingWound < 0)
			{
				Battle.bleedingWound = 0;
			}
		}
		
		damage += roll.d8() + roll.d8() + roll.d8();
		return damage;
	}
	
	
	public int sonicHurricane(Player player)
	{
		int damage = player.getMagicBonusDamage();
		int numDice;
		
		if (player.getCasterLevel() < 20)
		{
			numDice = player.getCasterLevel();
		}
		else
		{
			numDice = 20;
		}
		
		for (int index = 0; index < numDice; index++)
		{
			damage += roll.d8();
		}
		return damage;
	}
	public boolean isDrowning(Player player, Enemy enemy, int DCmod)
	{
		boolean drowning;
		
		if (enemy.fortSave() < player.getSpellDC(DCmod))
		{
			drowning = true;
		}
		else
		{
			drowning = false;
		}
		return drowning;
	}
	public int drainLife(Player player) {
		
		int damage = roll.XdY((player.getCasterLevel() / 2), 4);
		damage += player.getMagicBonusDamage();
		
		return damage;
	}
	public int searingLight(Player player)
	{
		int damage = player.getMagicBonusDamage();
		
		if (player.getCasterLevel() < 10)
		{
			damage += roll.XdY(player.getCasterLevel(), 6);
		}
		else
		{
			damage += roll.XdY(10, 6);
		}
		
		return damage;
	}
	public int lightBurst(Player player)
	{
		int damage = player.getMagicBonusDamage();
		
		if (player.getCasterLevel() < 15)
		{
			damage += roll.XdY(player.getCasterLevel(), 8);
		}
		else
		{
			damage += roll.XdY(15, 8);
		}
		
		return damage;
	}
	public int rayOfFatigue(Player player)
	{
		// This spell is not in the game yet
		int damage = roll.d6();
		damage += player.getMagicBonusDamage();
		
		return damage;
	}
	
	public int hammer(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 15)
		{
			for (int index = 3; index < player.getCasterLevel(); index += 3)
			{
				damage += roll.d8();
			}
		}
		else
		{
			for (int index = 0; index < 5; index++)
			{
				damage += roll.d8();
			}
		}
		return damage;
	}
	
	public int shadowBlast(Player player)
	{
		damage = player.getMagicBonusDamage();
		if (player.getCasterLevel() < 10)
		{
			damage += roll.XdY(player.getLevel(), 6);
		}
		else
		{
			damage += roll.XdY(10, 6);
		}
		
		return damage;
	}
}
