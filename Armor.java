
public class Armor {

	enum armorType {Light, Medium, Heavy, None}
	
	private int ACbonus;
	private int spellFailure;
	private int maxDex;
	private int armorCheck;
	private String name;
	private armorType type;
	private int basePrice;
	int enhancement;
	String qualities[] = new String[0];
	
	public Armor(String armorName)
	{
		int change = 0;
		
		try
		{
			int location = armorName.indexOf('+');
			try
			{
				change = Integer.parseInt("" + armorName.charAt(location + 1));
			}
			catch (java.lang.NumberFormatException error)
			{
				change = 0;
			}
		}
		catch (java.lang.NullPointerException error)
		{
			
		}
		
		enhancement = change;
		name = armorName;
		
		if (name.equalsIgnoreCase("none"))
		{
			ACbonus = 0;
			spellFailure = 0;
			maxDex = 100;
			armorCheck = 0;
			type = armorType.None;
		}
		else if (name.length() >= 19 && name.substring(0, name.length() - 3).equalsIgnoreCase("Bracers of Armor"))
		{
			ACbonus = 0;
			spellFailure = 0;
			maxDex = 100;
			armorCheck = 0;
			type = armorType.None;
		}
		else if (name.length() >= 6 && name.substring(0, 6).equalsIgnoreCase("Padded"))
		{
			ACbonus = 1;
			spellFailure = 5;
			maxDex = 8;
			armorCheck = 0;
			type = armorType.Light;
			basePrice = 5;
		}
		else if (name.length() >= 7 && name.substring(0, 7).equalsIgnoreCase("Leather"))
		{
			ACbonus = 2;
			spellFailure = 10;
			maxDex = 6;
			armorCheck = 0;
			type = armorType.Light;
			basePrice = 10;
			
		}
		else if (name.length() >= 15 && name.substring(0, 15).equalsIgnoreCase("Studded Leather"))
		{
			ACbonus = 3;
			spellFailure = 15;
			maxDex = 5;
			armorCheck = 1;
			type = armorType.Light;
			basePrice = 25;
		}
		else if (name.length() >= 11 && name.substring(0, 11).equalsIgnoreCase("Chain Shirt"))
		{
			ACbonus = 4;
			spellFailure = 20;
			maxDex = 4;
			armorCheck = 2;
			type = armorType.Light;
			basePrice = 100;
		}
		else if (name.length() >= 9 && name.substring(0, 9).equalsIgnoreCase("Chainmail"))
		{
			ACbonus = 5;
			spellFailure = 30;
			maxDex = 2;
			armorCheck = 5;
			type = armorType.Medium;
			basePrice = 150;
		}
		else if (name.length() >= 11 && name.substring(0, 11).equalsIgnoreCase("Breastplate"))
		{
			ACbonus = 5;
			spellFailure = 25;
			maxDex = 3;
			armorCheck = 4;
			type = armorType.Medium;
			basePrice = 200;
		}
		else if (name.length() >= 11 && name.substring(0, 11).equalsIgnoreCase("Splint mail"))
		{
			ACbonus = 6;
			spellFailure = 40;
			maxDex = 0;
			armorCheck = 7;
			type = armorType.Heavy;
			basePrice = 200;
		}
		else if (name.length() >= 11 && name.substring(0, 11).equalsIgnoreCase("Banded mail"))
		{
			ACbonus = 6;
			spellFailure = 35;
			maxDex = 1;
			armorCheck = 6;
			type = armorType.Heavy;
			basePrice = 250;
		}
		else if (name.length() >= 10 && name.substring(0, 10).equalsIgnoreCase("Half-plate"))
		{
			ACbonus = 7;
			spellFailure = 40;
			maxDex = 0;
			armorCheck = 7;
			type = armorType.Heavy;
			basePrice = 600;
		}
		else if (name.length() >= 10 && name.substring(0, 10).equalsIgnoreCase("Full plate"))
		{
			ACbonus = 8;
			spellFailure = 35;
			maxDex = 1;
			armorCheck = 6;
			type = armorType.Heavy;
			basePrice = 1500;
		}
		else if (name.length() >= 24 && name.substring(0, 24).equalsIgnoreCase("Full plate of Absorbtion"))
		{
			ACbonus = 8;
			spellFailure = 35;
			maxDex = 1;
			armorCheck = 5;
			type = armorType.Heavy;
			basePrice = 17605;
			qualities = new String[1];
			qualities[0] = "Absorbtion";
		}
		else if (name.length() >= 27 && name.substring(0, 27).equalsIgnoreCase("Breastplate of Spellcasting"))
		{
			ACbonus = 5;
			spellFailure = 0;
			maxDex = 3;
			armorCheck = 3;
			type = armorType.Medium;
			basePrice = 4350;
		}
		else if (name.length() >= 26 && name.substring(0, 26).equalsIgnoreCase("Breastplate of Fire Resist"))
		{
			// Need to implement fire resistance
		}
		ACbonus += enhancement;
	}
	private static String[] armorList()
	{
		String[] list = new String[13];
		list[0] = "Padded";
		list[1] = "Leather";
		list[2] = "Studded Leather";
		list[3] = "Chain Shirt";
		list[4] = "Chainmail";
		list[5] = "Breastplate";
		list[6] = "Splint mail";
		list[7] = "Banded mail";
		list[8] = "Half-plate";
		list[9] = "Full plate";
		list[10] = "Bracers of Armor";
		list[11] = "Full plate of Absorbtion";
		list[12] = "Breastplate of Spellcasting";
		
		return list;
	}
	public static boolean isAnArmor(String itemName)
	{
		boolean match = false;
		String magicItemName = itemName.substring(0, itemName.length() - 3);
		
		for (int index = 0; index < armorList().length; index++)
		{
			if (itemName.equalsIgnoreCase(armorList()[index]) || magicItemName.equalsIgnoreCase(armorList()[index]))
			{
				match = true;
			}
		}
		return match;
	}
	public int getEnhancement()
	{
		return enhancement;
	}
	
	public int getBasePrice()
	{
		return basePrice;
	}
	
	public int getACbonus()
	{
		return ACbonus;
	}
	public int getSpellFailure()
	{
		return spellFailure;
	}
	public int getMaxDex()
	{
		return maxDex;
	}
	public int getArmorCheck()
	{
		return armorCheck;
	}
	public String getName()
	{
		return name;
	}
	public armorType getType()
	{
		return type;
	}
	
	public boolean hasQuality(String check)
	{
		boolean found = false;
		
		if (qualities.length > 0)
		{
			for (int i = 0; i < qualities.length; i++)
			{
				if (qualities[i].equalsIgnoreCase(check))
				{
					found = true;
					break;
				}
			}
		}
		
		return found;
	}
}
