
public class Weapon {

	private int enhancement;
	private String name;
	private int basePrice;
	private String properties[];
	
	enum type {None, Slash, Pierce, Bludg}
	
	private type damageType;
	
	public Weapon(String weaponName, int magicBonus)
	{
		name = weaponName;
		enhancement = magicBonus;
		properties = new String[0];
		
		if (weaponName.equals("unarmed"))
		{
			damageType = type.Bludg;
			basePrice = 0;
		}
		else if (weaponName.equals("Shiv"))
		{
			damageType = type.Pierce;
			basePrice = 2;
		}
		else if (weaponName.equals("Sword"))
		{
			damageType = type.Slash;
			basePrice = 8;
		}
		else if (weaponName.equals("Flame Sword"))
		{
			damageType = type.Slash;
			basePrice = 2308;
		}
		else if (weaponName.equals("Sword of Agrinon"))
		{
			damageType = type.Slash;
			basePrice = 8308;
			properties = new String[1];
			properties[0] = "Agrinon";
		}
		else if (weaponName.equals("Axe"))
		{
			damageType = type.Slash;
			basePrice = 9;
		}
		else if (weaponName.equals("Power Axe"))
		{
			damageType = type.Slash;
			basePrice = 8309;
		}
		else if (weaponName.equals("Mace"))
		{
			damageType = type.Bludg;
			basePrice = 8;
		}
		else if (weaponName.equals("Undead Bane Mace"))
		{
			damageType = type.Bludg;
			basePrice = 2308;
		}
		else if (weaponName.equals("Ghost Touch Mace"))
		{
			damageType = type.Bludg;
			basePrice = 4308;
		}
		else if (weaponName.equals("Warhammer"))
		{
			damageType = type.Bludg;
			basePrice = 9;
		}
		else if (weaponName.equals("Screaming Warhammer"))
		{
			damageType = type.Bludg;
			basePrice = 8309;
		}
		else if (weaponName.equals("Disrupting Warhammer"))
		{
			damageType = type.Bludg;
			basePrice = 8309;
		}
		else if (weaponName.equals("Animal Bane Warhammer"))
		{
			damageType = type.Bludg;
			basePrice = 2309;
		}
		else if (weaponName.equals("Quarterstaff"))
		{
			damageType = type.Bludg;
			basePrice = 4;
		}
		else if (weaponName.equals("Staff of Storms"))
		{
			damageType = type.Bludg;
			basePrice = 21000;
		}
		else if (weaponName.equals("Acidic Quarterstaff"))
		{
			damageType = type.Bludg;
			basePrice = 2304;
		}
		else if (weaponName.equals("Freezing Axe"))
		{
			damageType = type.Slash;
			basePrice = 2309;
		}
		else if (weaponName.equals("Rapier"))
		{
			damageType = type.Pierce;
			basePrice = 7;
		}
		else if (weaponName.equals("Shocking Rapier"))
		{
			damageType = type.Pierce;
			basePrice = 2307;
		}
		else if (weaponName.equals("Soulstealing Rapier"))
		{
			damageType = type.Pierce;
			basePrice = 16412;
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public String[] getProperties()
	{
		return properties;
	}
	
	public String getFullName()
	{
		String output;
		
		if (enhancement > 0)
		{
			output = name + " +" + enhancement;
		}
		else
		{
			output = name;
		}
		
		return output;
	}
	
	public int getEnhancement()
	{
		return enhancement;
	}
	
	public type getType()
	{
		return damageType;
	}
	
	public int getPrice()
	{
		int totalPrice = basePrice;
		totalPrice += (int)Math.pow(enhancement, 2) * 2000;
		
		return totalPrice;
	}
	
	public int getBasePrice()
	{
		return basePrice;
	}
	
	private static String[] weaponList()
	{
		String[] weaponList = new String[21];
		weaponList[0] = "Sword";
		weaponList[1] = "Flame Sword";
		weaponList[2] = "Mace";
		weaponList[3] = "Undead Bane Mace";
		weaponList[4] = "Ghost Touch Mace";
		weaponList[5] = "Warhammer";
		weaponList[6] = "Disrupting Warhammer";
		weaponList[7] = "Screaming Warhammer";
		weaponList[8] = "Axe";
		weaponList[9] = "Power Axe";
		weaponList[10] = "Quarterstaff";
		weaponList[11] = "Acidic Quarterstaff";
		weaponList[12] = "Staff of Storms";
		weaponList[13] = "Shiv";
		weaponList[14] = "Ruby Scepter";
		weaponList[15] = "Freezing Axe";
		weaponList[16] = "Rapier";
		weaponList[17] = "Shocking Rapier";
		weaponList[18] = "Soulstealing Rapier";
		weaponList[19] = "Sword of Agrinon";
		weaponList[20] = "Animal Bane Warhammer";
		
		return weaponList;
	}
	
	public static boolean isAWeapon(String itemName)
	{
		boolean match = false;

		String magicItemName = itemName.substring(0, itemName.length() - 3);
		
		for (int index = 0; index < weaponList().length; index++)
		{
			if (itemName.equalsIgnoreCase(weaponList()[index]) || magicItemName.equalsIgnoreCase(weaponList()[index]))
			{
				match = true;
			}
		}
		return match;
	}
	
	public void damage(Player player, Enemy enemy, int bonusDamage)
	{
		Dice roll = new Dice();
		
		int dmg = bonusDamage + enhancement;
		
		if (name.equals("Sword") || name.equals("Mace")
				|| name.equals("Undead Bane Mace") || name.equals("Flame Sword")
				|| name.equals("Ruby Scepter"))
		{
			dmg += roll.d8();
		}
		else if (name.equals("Shiv"))
		{
			dmg += roll.d4();
		}
		else if (name.equals("Quarterstaff")
				|| name.equals("Rapier")
				|| name.equals("Shocking Rapier")
				|| name.equals("Acidic Quarterstaff")
				|| name.equals("Ghost Touch Mace") || name.equals("Soulstealing Rapier")
				|| name.contains("Warhammer") || name.contains("Axe")
				|| name.equals("Staff of Storms"))
		{
			dmg += roll.d6();
		}
		
		/*
		 * player.attack is still required, that is where the players proficiencies
		 * with weapons can apply to the damage dealt.
		 */
		dmg += player.attack(enemy.getType(), enemy);
		
		if (dmg < 1)
			dmg = 1;
		
		boolean ghostTouch = false;
		
		if (name.equals("Ghost Touch Mace"))
			ghostTouch = true;
		
		enemy.meleeDamage(dmg, this, player, ghostTouch);
		
		
		if (name.equals("Flame Sword"))
		{
			enemy.damage(roll.d6(), player, "fire");
		}
		else if (name.equals("Freezing Axe"))
		{
			enemy.damage(roll.d6(), player, "cold");
		}
		else if (name.equals("Shocking Rapier"))
		{
			enemy.damage(roll.d6(), player, "electricity");
		}
		else if (name.equals("Acidic Quarterstaff"))
		{
			enemy.damage(roll.d6(), player, "acid");
		}
		else if (name.equals("Screaming Warhammer"))
		{
			enemy.damage(roll.XdY(2, 4), player, "sonic");
		}
		else if (name.equals("Soulstealing Rapier"))
		{
			int steal = roll.XdY(3, 4);
			enemy.damage(steal, player, "entropy");
			player.changeHP(steal);
		}
		else if (name.equals("Staff of Storms"))
		{
			if (player.getType().equals("Caster"))
			{
				int extraDamage = 0;
				for (int i = 0; i < (player.getLevel() / 3); i++)
				{
					extraDamage += roll.d4();
				}
				
				enemy.damage(extraDamage, player, "electricity");
			}
		}
	}
}
