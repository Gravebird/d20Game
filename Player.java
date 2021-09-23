import java.util.Scanner;
import java.util.ArrayList;

/**
 * <p>This class represents the player, it holds all the values
 * used by the player during the game.</p>
 * 
 * @author Bryan Rainbow
 *
 */
public class Player {

	enum Saves {Fortitude, Reflex, Will};
	enum Race {Human, Elf, Dwarf, Gnome, Halfling, HalfOrc, Arcania};
	enum Stat {Str, Dex, Con, Int, Wis, Cha};
	
	public static boolean beatenTodd = false;
	
	private String name;
	public String type;
	private String qualities = "Qualities: ";
	private Weapon weapon = new Weapon("unarmed", 0);
	private String accessory = "none";
	private int damage;
	public int Strength;
	public int Dexterity;
	public int Constitution;
	public int Intelligence;
	public int Wisdom;
	public int Charisma;
	public int Gold;
	public int HPTotal = 0;
	private int HP = 0;
	public int MPTotal;
	private int MP;
	private int MPmod = 0;
	private int level = 1;
	private int XP = 0;
	private int NextLevel = 1000;
	private final int baseAC = 10;
	private int baseAttack = 0;
	private int atkRoll;
	private int ACmod = 0;
	private int ATKmod = 0;
	private int attackMod;
	private int DCmod = 0;
	private int escapeMod = 0;
	private String feats = "";
	private int strTrain = 1;
	private int dexTrain = 1;
	private int conTrain = 1;
	private int intTrain = 1;
	private int wisTrain = 1;
	private int chaTrain = 1;
	private int iceArmor = 0;
	private int grappleBonus = 0;
	private int unarmedCombatNumDice = 1;
	private int unarmedCombatDamageDice = 3;
	private boolean dieHardUsed = false;
	
	private int StrPoison = 0;
	private int DexPoison = 0;
	private int ConPoison = 0;
	private int IntPoison = 0;
	private int WisPoison = 0;
	private int ChaPoison = 0;
	private boolean abilityDamaged = false;
	
	private int StrTempBonus = 0;
	private int DexTempBonus = 0;
	private int ConTempBonus = 0;
	private int IntTempBonus = 0;
	private int WisTempBonus = 0;
	private int ChaTempBonus = 0;
	
	// Feats
	public boolean powerAttack = false;
	public boolean forceOfPersonality = false;
	public boolean expertise = false;
	public boolean improvedExpertise = false;
	public int expertiseMod = 0;
	public boolean insightfulReflexes = false;
	public boolean improvedToughness = false;
	public boolean slippery = false;
	public int greatFortitude = 0;
	public int lightningReflexes = 0;
	public int ironWill = 0;
	public boolean dieHard = false;
	public boolean strengthOfBody = false;
	public boolean arcaneInsight = false;
	public int spellFailureMod = 0;
	public boolean lightArmorProf = false;
	public boolean mediumArmorProf = false;
	public boolean heavyArmorProf = false;
	public boolean forcefulMagic = false;
	public boolean alcoholic = false;
	public boolean drunkenBrawler = false;
	public boolean drunkenNumbness = false;
	public boolean drunkenPotency = false;
	public int magicalSupremacy = 0;
	static ArrayList<String> spellFocusTypes = new ArrayList<String>();
	public double talented = 0.0;
	public double observant = 0.0;
	public boolean lightArmorMastery = false;
	
	
	// Racial Feats
	public boolean elvenGrace = false;
	public boolean dwarvenHardiness = false;
	public boolean gnomeIngenuity = false;
	public boolean halflingCunning = false;
	public boolean orcishBlood = false;
	public boolean arcaniaPotency = false;
	
	// Fighter Qualities
	public int DR = 0;
	public int swordProf = 0;
	public int axeProf = 0;
	public int shivProf = 0;
	public int quartProf = 0;
	public int rapProf = 0;
	public int maceProf = 0;
	public int warHammerProf = 0;
	public int unarmedProf = 0;
	public int animalFocus = 0;
	public int humanFocus = 0;
	public int undeadFocus = 0;
	public int feyFocus = 0;
	public int elementalFocus = 0;
	public int magicBeastFocus = 0;
	public boolean smite = false;
	public boolean drunkenMaster = false;
	public boolean arcaneStrike = false;
	
	// Races
	public int racialMana = 0;
	
	public boolean rage = false;
	
	private boolean mummyRot = false;
	
	private Race race;
	
	// Long-lasting spell effects
	public boolean hasMageArmor = false;
	
	Dice roll = new Dice();
	Armor armor = new Armor("none");
	Quest quest;
	Scanner scan = new Scanner(System.in);
	
	public Player(String Name)
	{
		name = Name.substring(0, 1).toUpperCase() + 
				Name.substring(1, Name.length());
		boolean reRoll = false;
		do
		{
			Strength = roll.statRoll();
			Dexterity = roll.statRoll();
			Constitution = roll.statRoll();
			Intelligence = roll.statRoll();
			Wisdom = roll.statRoll();
			Charisma = roll.statRoll();
			if ((Strength + Dexterity + Constitution + Intelligence + Wisdom + Charisma) / 6 <= 12)
			{
				reRoll = true;
			}
			else
			{
				reRoll = false;
			}
		}
		while (reRoll);
		Gold = 100;
	}
	
	public String getName()
	{
		return name;
	}
	public Race getRace()
	{
		return race;
	}
	public void setRace(Race newRace, Magic mgk)
	{
		race = newRace;
		if (race == Race.Human)
		{
			chooseFeat(mgk, true, true);
		}
		else if (race == Race.Elf)
		{
			Dexterity += 2;
			Constitution -= 2;
			racialMana +=2;
		}
		else if (race == Race.Dwarf)
		{
			Constitution += 2;
			Charisma -= 2;
		}
		else if (race == Race.Gnome)
		{
			Constitution += 2;
			Strength -= 2;
			spellFailureMod += 5;
		}
		else if (race == Race.Halfling)
		{
			Dexterity += 2;
			Strength -= 2;
			ACmod++;
		}
		else if (race == Race.HalfOrc)
		{
			Strength += 2;
			Constitution += 2;
			Intelligence -= 2;
		}
		else if (race == Race.Arcania)
		{
			Strength -= 2;
			Dexterity -= 2;
			Constitution -= 2;
			Intelligence += 2;
			mgk.rngSpell(5);
		}
		
		System.out.println("Str: " + getStr() + "\nDex: " + getDex() + "\nCon: " + getCon()
				+ "\nInt: " + getInt() + "\nWis: " + getWis() + "\nCha: " + getCha());
	}
	public String getRaceName()
	{
		String raceName;
		if (race == Race.Human)
			raceName = "Human";
		else if (race == Race.Elf)
			raceName = "Elf";
		else if (race == Race.Dwarf)
			raceName = "Dwarf";
		else if (race == Race.Gnome)
			raceName = "Gnome";
		else if (race == Race.Halfling)
			raceName = "Halfling";
		else if (race == Race.HalfOrc)
			raceName = "Half-Orc";
		else if (race == Race.Arcania)
			raceName = "Arcania";
		else
		{
			System.out.println("\nERROR: undefined raceName in Player.getRaceName()\n");
			raceName = "NULL";
		}
		
		return raceName;
	}
	public int getLevel()
	{
		return level;
	}
	public int getCasterLevel()
	{
		return level + magicalSupremacy;
	}
	public boolean fullHealth()
	{
		boolean check = false;
		if (HP == HPTotal)
			check = true;
		return check;
	}
	public String getStats()
	{
		if (Game.cheater)
			System.out.println("----------------------------------------\n\t\tCHEATER");
		
		String output;
		
		if (type.equals("Fighter"))
		{
			output = "----------------------------------------\n"
					+ "\t" + name + ", level " + level + " " + getRaceName() + " " + type
					+ "\n\t\tBAB: " + baseAttack + "\n\t\tGrapple: " + getGrappleMod() + "\n\t"
					+ getStat("Str") + getStat("Int")
					+ "\n\t" + getStat("Dex") + getStat("Wis")
					+ "\n\t" + getStat("Con") + getStat("Cha")
					+ "\n\tAC: " + getAC()
					+ "\t\tBDC: " + (10 + ((getCha() - 10) / 2) + DCmod)
					+ "\n    **********************************"
					+ "\n\t\tFortitude: " + fortSaveMod()
					+ "\n\t\tReflex:\t   " + reflexSaveMod() + "\n\t\tWill:\t   " + willSaveMod()
					+ "\n    **********************************"
					+ "\n    HP: " + HP + "/" + HPTotal + "    XP: "
					+ XP + "/" + NextLevel + "    MP: " + MP + "/" + getMPTotal()
					+ "\n----------------------------------------";
		}
		else
		{
			output = "----------------------------------------\n"
					+ "\t" + name + ", level " + level + " " + getRaceName() + " " + type
					+ "\n\t\tBDC: " + (10 + ((getCha() - 10) / 2) + DCmod)
					+ "\n\t\tGrapple: " + getGrappleMod()
					+ "\n\t" + getStat("Int") + getStat("Str")
					+ "\n\t" + getStat("Wis") + getStat("Dex")
					+ "\n\t" + getStat("Cha") + getStat("Con")
					+ "\n\tAC: " + getAC()
					+ "\t\tBAB: " + baseAttack
					+ "\n    **********************************"
					+ "\n\t\tFortitude: " + fortSaveMod()
					+ "\n\t\tReflex:\t   " + reflexSaveMod() + "\n\t\tWill:\t   " + willSaveMod()
					+ "\n    **********************************"
					+ "\n    HP: " + HP + "/" + HPTotal + "    XP: "
					+ XP + "/" + NextLevel + "    MP: " + MP + "/" + getMPTotal()
					+ "\n----------------------------------------";
		}
		return output;
	}
	/**
	 * Sets the players health die to the entered number.
	 * Also sets the type (class) of the player to
	 * either "Fighter" or "Caster"
	 * 
	 * @param Die the number of sides on the die.
	 */
	public void healthDie(int Die)
	{
		HPTotal += Die + ((getCon() - 10) / 2);
		HP = HPTotal;
		if (Die == 10) {
			type = "Fighter";
			MP = getMPTotal();
			baseAttack += level;
			lightArmorProf = true;
			mediumArmorProf = true;
			heavyArmorProf = true;
		}
		else {
			type = "Caster";
			MPTotal = level + 1 + ((getWis() - 10) / 2) + MPmod;
			MP = getMPTotal();
			baseAttack += level / 2;
		}
	}
	public void setStat(String stat, int newStat)
	{
		if (stat.equals("Str"))
			Strength = newStat;
		else if (stat.equals("Dex"))
			Dexterity = newStat;
		else if (stat.equals("Con"))
		{
			int temp = (Constitution - 10) / 2;
			HPTotal -= temp * level;
			Constitution = newStat;
			temp = (Constitution - 10) / 2;
			HPTotal += temp * level;
			HP = HPTotal;
		}
		else if (stat.equals("Int"))
			Intelligence = newStat;
		else if (stat.equals("Wis"))
			Wisdom = newStat;
		else if (stat.equals("Cha"))
			Charisma = newStat;
	}
	public String getType()
	{
		return type;
	}
	private String getStat(String ability)
	{
		String totalStat = ability;
		
		if (ability.equals("Str"))
		{
			if (StrPoison > 0 || StrTempBonus != 0)
			{
				totalStat += ": " + getStr() + "(" + (getStr() + StrPoison - StrTempBonus) + ")\t";
			}
			else
			{
				totalStat += ": " + getStr() + "\t\t";
			}
		}
		else if (ability.equals("Dex"))
		{
			if (DexPoison > 0 || DexTempBonus != 0)
			{
				totalStat += ": " + getDex() + "(" + (getDex() + DexPoison - DexTempBonus) + ")\t";
			}
			else
			{
				totalStat += ": " + getDex() + "\t\t";
			}
		}
		else if (ability.equals("Con"))
		{
			if (ConPoison > 0 || ConTempBonus != 0)
			{
				totalStat += ": " + getCon() + "(" + (getCon() + ConPoison - ConTempBonus) + ")\t";
			}
			else
			{
				totalStat += ": " + getCon() + "\t\t";
			}
		}
		else if (ability.equals("Int"))
		{
			if (IntPoison > 0 || IntTempBonus != 0)
			{
				totalStat += ": " + getInt() + "(" + (getInt() + IntPoison - IntTempBonus) + ")\t";
			}
			else
			{
				totalStat += ": " + getInt() + "\t\t";
			}
		}
		else if (ability.equals("Wis"))
		{
			if (WisPoison > 0 || WisTempBonus != 0)
			{
				totalStat += ": " + getWis() + "(" + (getWis() + WisPoison - WisTempBonus) + ")\t";
			}
			else
			{
				totalStat += ": " + getWis() + "\t\t";
			}
		}
		else if (ability.equals("Cha"))
		{
			if (ChaPoison > 0 || ChaTempBonus != 0)
			{
				totalStat += ": " + getCha() + "(" + (getCha() + ChaPoison - ChaTempBonus) + ")\t";
			}
			else
			{
				totalStat += ": " + getCha() + "\t\t";
			}
		}
		return totalStat;
	}
	public int getStr()
	{
		int totalStat = Strength;
		if (getAccessory().substring(0, getAccessory().length() - 3).equals("Gauntlets of Strength"))
		{
			int index = getAccessory().indexOf('+');
			totalStat += Integer.parseInt("" + getAccessory().charAt(index + 1));
		}
		return totalStat;
	}
	public int getDex()
	{
		int totalStat = Dexterity;
		if (getAccessory().substring(0, getAccessory().length() - 3).equals("Gloves of Dexterity"))
		{
			int index = getAccessory().indexOf('+');
			totalStat += Integer.parseInt("" + getAccessory().charAt(index + 1));
		}
		return totalStat;
	}
	public int getCon()
	{
		int totalStat = Constitution;
		if (getAccessory().substring(0, getAccessory().length() - 3).equals("Amulet of Health"))
		{
			int index = getAccessory().indexOf('+');
			totalStat += Integer.parseInt("" + getAccessory().charAt(index + 1));
		}
		return totalStat;
	}
	public int getInt()
	{
		int totalStat = Intelligence;
		if (getAccessory().substring(0, getAccessory().length() - 3).equals("Headband of Intellect"))
		{
			int index = getAccessory().indexOf('+');
			totalStat += Integer.parseInt("" + getAccessory().charAt(index + 1));
		}
		return totalStat;
	}
	public int getWis()
	{
		int totalStat = Wisdom;
		if (getAccessory().substring(0, getAccessory().length() - 3).equals("Periapt of Wisdom"))
		{
			int index = getAccessory().indexOf('+');
			totalStat += Integer.parseInt("" + getAccessory().charAt(index + 1));
		}
		return totalStat;
	}
	public int getCha()
	{
		int totalStat = Charisma;
		if (getAccessory().substring(0, getAccessory().length() - 3).equals("Cloak of Charisma"))
		{
			int index = getAccessory().indexOf('+');
			totalStat += Integer.parseInt("" + getAccessory().charAt(index + 1));
		}
		return totalStat;
	}
	public void resetBonuses()
	{
		Strength -= StrTempBonus;
		StrTempBonus = 0;
		Dexterity -= DexTempBonus; 
		DexTempBonus = 0;
		Constitution -= ConTempBonus;
		ConTempBonus = 0;
		Intelligence -= IntTempBonus;
		IntTempBonus = 0;
		Wisdom -= WisTempBonus;
		WisTempBonus = 0;
		Charisma -= ChaTempBonus;
		ChaTempBonus = 0;
	}
	public void addAlcoholBonus(Stat stat, int amount)
	{
		if (stat == Stat.Str)
		{
			StrTempBonus += amount;
			Strength += amount;
		}
		else if (stat == Stat.Dex)
		{
			DexTempBonus += amount;
			Dexterity += amount;
		}
		else if (stat == Stat.Con)
		{
			ConTempBonus += amount;
			Constitution += amount;
			if (Constitution % 2 == 0)
			{
				HP += level;
			}
			if (amount > 2)
			{
				amount -= 2;
				int tempHP = amount / 2;
				HP += level * tempHP;
			}
		}
		else if (stat == Stat.Int)
		{
			IntTempBonus += amount;
			Intelligence += amount;
		}
		else if (stat == Stat.Wis)
		{
			WisTempBonus += amount;
			Wisdom += amount;
			if (Wisdom % 2 != 0)
			{
				MP -= 1;
			}
			if (Math.abs(amount) > 2)
			{
				int lostMP = Math.abs(amount) - 2;
				lostMP /= 2;
				MP -= lostMP;
			}
		}
		else if (stat == Stat.Cha)
		{
			ChaTempBonus += amount;
			Charisma += amount;
		}
		
		checkDeath();
	}
	public boolean isDrunk()
	{
		boolean val = false;
		
		if (StrTempBonus != 0 || DexTempBonus != 0 || ConTempBonus != 0
				|| IntTempBonus != 0 || WisTempBonus != 0 || ChaTempBonus != 0)
		{
			val = true;
		}
		
		return val;
	}
	public boolean isAlcoholic()
	{
		return alcoholic;
	}
	public boolean hasDrunkenMaster()
	{
		return drunkenMaster;
	}
	public int getGold()
	{
		return Gold;
	}
	public int getHP()
	{
		return HP;
	}
	public int getHPTotal()
	{
		return HPTotal;
	}
	public int getMP()
	{
		return MP;
	}
	public int getMPTotal()
	{
		int totalMP = MPTotal + racialMana;
		if (getAccessory().equals("Crown of Thought"))
		{
			totalMP += 4;
		}
		if (weapon.getName().equals("Staff of Storms"))
		{
			totalMP += 2;
		}
		return totalMP;
	}
	public int getAC()
	{
		int dexMod = (getDex() - 10) / 2;
		if (dexMod > armor.getMaxDex())
			dexMod = armor.getMaxDex();
		int totalAC = baseAC + dexMod + ACmod + expertiseMod;
		
		// The following if statement is for the Light Armor Mastery feat
		if (armor.getType() == Armor.armorType.Light && lightArmorMastery)
		{
			totalAC += armor.getACbonus() * 2;
		}
		else
		{
			totalAC += armor.getACbonus();
		}
		
		if (getAccessory().substring(0, getAccessory().length() - 3).equals("Ring of Protection"))
		{
			int index = getAccessory().indexOf('+');
			int num = Integer.parseInt("" + getAccessory().charAt(index + 1));
			totalAC += num;
		}
		if (type.equals("Caster"))
		{
			if (arcaneInsight)
				totalAC += (getInt() - 10) / 2;
			else
				totalAC += (getInt() - 10) / 4;
		}
		if (drunkenBrawler && isDrunk())
		{
			totalAC += 2;
		}
		if (Game.ironSkin)
		{
			totalAC += 4;
		}
		
		return totalAC;
	}
	public void revertExpertise()
	{
		expertiseMod = 0;
	}
	public int getTouch()
	{
		int totalAC = baseAC + ((getDex() - 10) / 2) + ACmod;
		if (type.equals("caster"))
		{
			if (arcaneInsight)
			{
				totalAC += ((getInt() - 10) / 2);
			}
			else
			{
				totalAC += ((getInt() - 10) / 4);
			}
		}
		if (getAccessory().substring(0, getAccessory().length() - 3).equals("Ring of Protection"))
		{
			int index = getAccessory().indexOf('+');
			int num = Integer.parseInt("" + getAccessory().charAt(index + 1));
			totalAC += num;
		}
		if (drunkenBrawler && isDrunk())
		{
			totalAC += 2;
		}
		return totalAC;
	}
	public int rollSpellFailure()
	{
		int rng = roll.d100();
		rng += spellFailureMod;
		return rng;
	}
	public boolean canRage()
	{
		return rage;
	}
	public void rage()
	{
		Strength += 4;
		Constitution += 4;
		HPTotal += level * 2;
		HP += level * 2;
		System.out.println("Strength increased by 4!\nConstitution increased by 4!");
	}
	public void reverseRage()
	{
		
		Strength -= 4;
		Constitution -= 4;
		HPTotal -= level * 2;
		HP -= level * 2;
		System.out.println("Strength decreased by 4!\nConstitution decreased by 4!");
	}
	public String getQualities()
	{
		String output;
		if (qualities.equals("Qualities: "))
		{
			output = qualities + "none";
		}
		else
		{
			output = qualities;
		}
		return output;
	}
	public int fortSaveMod()
	{
		int mod;
		if (type.equals("Fighter"))
		{
			mod = level / 2;
			mod += 2;
		}
		else
		{
			mod = level / 4;
		}
		int save = mod;
		
		if (strengthOfBody)
			save += (getStr() - 10) / 2;
		else
			save += (getCon() - 10) / 2;
		
		save += greatFortitude;
		
		if (getAccessory().substring(0, getAccessory().length() - 3).equals("Cloak of Resistance"))
		{
			int index = getAccessory().indexOf("+");
			save += Integer.parseInt("" + getAccessory().charAt(index + 1));
		}
		
		return save;
	}
	public int reflexSaveMod()
	{
		int save = (level / 3);
		if (insightfulReflexes)
		{
			save += (getInt() - 10) / 2;
		}
		else
		{
			save += (getDex() - 10) / 2;
		}
		save += lightningReflexes;
		
		if (getAccessory().substring(0, getAccessory().length() - 3).equals("Cloak of Resistance"))
		{
			int index = getAccessory().indexOf("+");
			save += Integer.parseInt("" + getAccessory().charAt(index + 1));
		}
		return save;
	}
	public int willSaveMod()
	{
		int mod;
		if (type.equals("Fighter"))
		{
			mod = level / 4;
		}
		else
		{
			mod = level / 2;
			mod += 2;
		}
		int save;
		if (forceOfPersonality)
		{
			save = mod + ((getCha() - 10) / 2);
		}
		else
		{
			save = mod + ((getWis() - 10) / 2);
		}
		save += ironWill;
		
		if (getAccessory().substring(0, getAccessory().length() - 3).equals("Cloak of Resistance"))
		{
			int index = getAccessory().indexOf("+");
			save += Integer.parseInt("" + getAccessory().charAt(index + 1));
		}
		
		return save;
	}
	public int savingThrow(Saves saveType, boolean magical, boolean poison)
	{
		int mod = 0;
		if (saveType == Saves.Fortitude)
			mod = fortSaveMod();
		else if (saveType == Saves.Reflex)
			mod = reflexSaveMod();
		else if (saveType == Saves.Will)
			mod = willSaveMod();
		else
			System.out.println("Programmer error: " + saveType + " does not exist");
		
		if (race == Race.Dwarf && (magical || poison))
		{
			mod += 2;
			if (magical && poison)
				mod += 2;
		}
		
		if (poison && Game.antidote)
		{
			mod += 4;
		}
		
		return roll.d20() + mod;
	}
	public int strCheck()
	{
		return roll.d20() + ((getStr() - 10) / 2);
	}
	public int dexCheck()
	{
		return roll.d20() + ((getDex() - 10) / 2);
	}
	public String getFeats()
	{
		return feats;
	}
	public int getNumberOfSpellFocus(String type)
	{
		int count = 0;
		for (int index = 0; index < spellFocusTypes.size(); index++)
		{
			if (type.equals(spellFocusTypes.get(index)))
			{
				count++;
			}
		}
		return count;
	}
	public int getSpellDC(int spellLevel)
	{
		int DC = 10 + ((getCha() - 10) / 2) + spellLevel + DCmod;
		
		if (drunkenPotency)
		{
			DC += 2;
		}
		
		return DC;
	}
	public int getMagicBonusDamage()
	{
		int amount;
		if (forcefulMagic)
			amount = (getCha() - 10) / 2;
		else
			amount = (getInt() - 10) / 2;
		
		return amount;
	}
	public int getStrTrain(int add)
	{
		strTrain += add;
		return strTrain;
	}
	public int getDexTrain(int add)
	{
		dexTrain += add;
		return dexTrain;
	}
	public int getConTrain(int add)
	{
		conTrain += add;
		return conTrain;
	}
	public int getIntTrain(int add)
	{
		intTrain += add;
		return intTrain;
	}
	public int getWisTrain(int add)
	{
		wisTrain += add;
		return wisTrain;
	}
	public int getChaTrain(int add)
	{
		chaTrain += add;
		return chaTrain;
	}
	public void changeStat(String stat, int amount)
	{
		if (stat.equals("Strength"))
		{
			Strength += amount;
			if (amount < 0)
				System.out.println("Strength decreased by " + Math.abs(amount) + "!");
			else
				System.out.println("Strength increased by " + amount + "!");
		}
		else if (stat.equals("Dexterity"))
		{
			Dexterity += amount;
			if (amount < 0)
				System.out.println("Dexterity decreased by " + Math.abs(amount) + "!");
			else
				System.out.println("Dexterity increased by " + amount + "!");
		}
		else if (stat.equals("Constitution"))
		{
			Constitution += amount;
			if (amount < 0)
				System.out.println("Constitution decreased by " + Math.abs(amount) + "!");
			else
				System.out.println("Constitution increased by " + amount + "!");
			if (getCon() % 2 == 0)
			{
				if (amount > 0)
				{
					HPTotal += level;
					HP += level;
				}
				else if (amount < 0)
				{
					HPTotal -= level;
					HP -= level;
				}
			}
		}
		else if (stat.equals("Intelligence"))
		{
			Intelligence+= amount;
			if (amount < 0)
				System.out.println("Intelligence decreased by " + Math.abs(amount) + "!");
			else
				System.out.println("Intelligence increased by " + amount + "!");
		}
		else if (stat.equals("Wisdom"))
		{
			Wisdom+= amount;
			if (amount < 0)
				System.out.println("Wisdom decreased by " + Math.abs(amount) + "!");
			else
				System.out.println("Wisdom increased by " + amount + "!");
		}
		else if (stat.equals("Charisma"))
		{
			Charisma+= amount;
			if (amount < 0)
				System.out.println("Charisma decreased by " + Math.abs(amount) + "!");
			else
				System.out.println("Charisma increased by " + amount + "!");
		}
		else
		{
			System.out.println("Programmer error: " + stat + " does not "
					+ "exist.");
		}
		
		if (type.equals("Fighter"))
		{
			baseAttack = level + ATKmod;
		}
		else
		{
			baseAttack = (level / 2) + ATKmod;
			MPTotal = level + 1 + ((getWis() - 10) / 2) + MPmod;
			MP = getMPTotal();
		}
	}
	public void setWeapon(String Equip, int magicBonus)
	{
		weapon = new Weapon(Equip, magicBonus);
		
		System.out.println(weapon.getFullName() + " has been equipped!");
		
		if (MP > getMPTotal())
		{
			MP = getMPTotal();
		}
	}
	public void setAccessory(String equip)
	{
		if (accessory.substring(0, accessory.length() - 3).equals("Amulet of Health"))
		{
			int index = accessory.indexOf('+');
			int change = Integer.parseInt("" + accessory.charAt(index + 1)) / 2;
			HPTotal -= level * change;
			HP -= level * change;
		}
		accessory = equip;
		System.out.println(accessory + " has been equipped!");
		if (accessory.substring(0, accessory.length() - 3).equals("Amulet of Health"))
		{
			int index = accessory.indexOf('+');
			int change = Integer.parseInt("" + accessory.charAt(index + 1)) / 2;
			HPTotal += level * change;
			HP += level * change;
		}
		if (MP > getMPTotal())
		{
			MP = getMPTotal();
		}
		if (HP < 1)
		{
			System.out.println(name + " has died!");
			System.exit(0);
		}
	}
	public String getAccessory()
	{
		return accessory;
	}
	public void setArmor(String equip)
	{
		armor = new Armor(equip);
		System.out.println(armor.getName() + " has been equipped!");
	}
	public Armor getArmor()
	{
		return armor;
	}
	public String getArmorName()
	{
		return armor.getName();
	}
	public int getInit()
	{
		return roll.d20() + ((getDex() - 10) / 2);
	}
	public int attack(String enemyType, Enemy enemy)
	{
		if (weapon.getName().length() >= 5 && weapon.getName().substring(weapon.getName().length() - 5, weapon.getName().length()).equalsIgnoreCase("Sword"))
		{
			damage = ((getStr() - 10) / 2) + attackMod + swordProf;
		}
		else if (weapon.getName().length() >= 3 && weapon.getName().substring(weapon.getName().length() - 3, weapon.getName().length()).equalsIgnoreCase("Axe")
				&& weapon.getName().equals("Power Axe") == false)
		{
			damage = ((((getStr() - 10) / 2) * 3) / 2) 
					+ (attackMod * 2) + axeProf;
		}
		else if (weapon.getName().equalsIgnoreCase("Power Axe"))
		{
			damage = (getStr() - 10) + (attackMod * 2) + axeProf;
		}
		else if (weapon.getName().toLowerCase().contains("staff"))
		{
			damage = attackMod + quartProf;
		}
		else if (weapon.getName().length() >= 4 && weapon.getName().substring(weapon.getName().length() - 4, weapon.getName().length()).equalsIgnoreCase("Shiv"))
		{
			damage = attackMod + ((getStr() - 10) / 2) + shivProf;
		}
		else if (weapon.getName().length() >= 6 && weapon.getName().substring(weapon.getName().length() - 6, weapon.getName().length()).equalsIgnoreCase("Rapier"))
		{
			damage = attackMod + ((getStr() - 10) / 2) + rapProf;
		}
		else if (weapon.getName().equalsIgnoreCase("Ruby Scepter") 
				|| (weapon.getName().length() >= 4 && weapon.getName().substring(weapon.getName().length() - 4, weapon.getName().length()).equalsIgnoreCase("Mace")))
		{
			damage = attackMod + ((getStr() - 10) / 2) + maceProf;
		}
		else if (weapon.getName().length() >= 9 && weapon.getName().substring(weapon.getName().length() - 9, weapon.getName().length()).equalsIgnoreCase("Warhammer"))
		{
			damage = attackMod + ((((getStr() - 10) / 2) * 3) / 2) + warHammerProf;
		}
		else if (weapon.getName().equalsIgnoreCase("unarmed"))
		{
			damage = unarmedAttack();
		}
		else
		{
			System.out.println("/nERROR: undefined weapon in class player.attack/n");
			damage = 100 + ((getStr() - 10) / 2) + attackMod;
		}
		
		damage += favoredEnemyDamageRoll(enemyType);
		
		return damage;
	}
	public int unarmedAttack()
	{
		damage = roll.XdY(unarmedCombatNumDice, unarmedCombatDamageDice) + ((getStr() - 10) / 2) + unarmedProf;
		if (getAccessory().equals("Brawlers Gloves"))
		{
			damage += roll.d6();
		}
		return damage;
	}
	private int favoredEnemyDamageRoll(String enemyType)
	{
		int bonus = 0;
		if (enemyType.equals("Animal"))
			bonus += animalFocus;
		else if (enemyType.equals("Humanoid"))
			bonus += humanFocus;
		else if (enemyType.equals("Undead"))
			bonus += undeadFocus;
		else if (enemyType.equals("Fey"))
			bonus += feyFocus;
		else if (enemyType.equals("Elemental"))
			bonus += elementalFocus;
		
		return bonus;
	}
	private int favoredEnemyAttackRoll(String enemyType)
	{
		int bonus = 0;
		if (enemyType.equals("Animal"))
			bonus += (animalFocus / 2);
		else if (enemyType.equals("Humanoid"))
			bonus += (humanFocus / 2);
		else if (enemyType.equals("Undead"))
			bonus += (undeadFocus / 2);
		else if (enemyType.equals("Fey"))
			bonus += (feyFocus / 2);
		else if (enemyType.equals("Elemental"))
			bonus += (elementalFocus / 2);
		
		return bonus;
	}
	private int armorCheckPenalty()
	{
		int penalty = 0;
		if ((armor.getType() == Armor.armorType.Light && !lightArmorProf) || (armor.getType() == Armor.armorType.Medium && !mediumArmorProf)
				|| (armor.getType() == Armor.armorType.Heavy && !heavyArmorProf))
		{
			penalty = armor.getArmorCheck();
		}
		return penalty;
	}
	public int attackRoll(String enemyType)
	{
		attackMod = 0;
		if (weapon.getName().length() >= 6 && weapon.getName().substring(weapon.getName().length() - 6, weapon.getName().length()).equals("Rapier"))
		{
			atkRoll = roll.d20() + baseAttack + ((getDex() - 10) / 2);
		}
		else
		{
			atkRoll = roll.d20() + baseAttack + ((getStr() - 10) / 2);
		}
		
		atkRoll += weapon.getEnhancement();
		
		if (weapon.getName().equalsIgnoreCase("Undead Bane Mace") && enemyType.equals("Undead"))
		{
			atkRoll += 2;
		}
		else if (weapon.getName().equalsIgnoreCase("Animal Bane Warhammer") && enemyType.equals("Animal"))
		{
			atkRoll += 2;
		}
		atkRoll -= armorCheckPenalty();
		
		atkRoll += favoredEnemyAttackRoll(enemyType);
		
		return atkRoll;
	}
	public int powerAttackRoll(String enemyType)
	{
		int amount;
		boolean loop = true;
		
		attackMod = 0;
		
		while (loop)
		{
			while (loop)
			{
				System.out.println("How much extra damage would you "
						+ "like to deal?");
				if (scan.hasNextInt())
				{
					attackMod = scan.nextInt();
					loop = false;
				}
				else
				{
					System.out.println("You must enter an integer value.");
					scan.next();
				}
			}
			loop = true;
			if (attackMod <= baseAttack)
			{
				loop = false;
			}
			else
				System.out.println("You cannot deal more damage than your "
						+ "BAB");
		}
		if (weapon.getName().length() >= 6 && weapon.getName().substring(weapon.getName().length() - 6, weapon.getName().length()).equals("Rapier"))
		{
			amount = roll.d20() + baseAttack + ((getDex() - 10) / 2) - attackMod;
		}
		else
		{
			amount = roll.d20() + baseAttack + ((getStr() - 10) / 2) - attackMod;
		}
		amount -= armorCheckPenalty();

		amount += favoredEnemyAttackRoll(enemyType);
		
		return amount;
	}
	public int expertiseAttackRoll(String enemyType)
	{
		int amount;
		boolean loop = true;
		
		attackMod = 0;
		
		while (loop)
		{
			while (loop)
			{
				System.out.print("Enter the amount to transfer from "
						+ "attack to defense");
				if (scan.hasNextInt())
				{
					attackMod = scan.nextInt();
					loop = false;
				}
				else
				{
					System.out.println("You must enter an integer value.");
					scan.nextLine();
				}
			}
			if (attackMod > 5 && !improvedExpertise)
				loop = true;
			if (attackMod > baseAttack)
				loop = true;
			
			if (loop)
			{
				System.out.print("You cannot enter a number that is greater than your BAB");
				if (improvedExpertise)
					System.out.println(".");
				else
					System.out.println(", up to 5.");
			}	
		}
		if (weapon.getName().length() >= 6 && weapon.getName().substring(weapon.getName().length() - 6, weapon.getName().length()).equals("Rapier"))
		{
			amount = roll.d20() + baseAttack + ((getDex() - 10) / 2) - attackMod;
		}
		else
		{
			amount = roll.d20() + baseAttack + ((getStr() - 10) / 2) - attackMod;
		}
		
		amount -= armorCheckPenalty();

		amount += favoredEnemyAttackRoll(enemyType);
		
		expertiseMod = attackMod;
		
		System.out.println("TESTING RESULTS: [Attack Roll: " + amount + "], "
				+ "[AC: " + getAC() + "]");
		
		return amount;
	}
	public int getGrappleMod()
	{
		return baseAttack + ((getStr() - 10) / 2) + grappleBonus;
	}
	public int grappleRoll()
	{
		return roll.d20() + getGrappleMod();
	}
	public Weapon getWeapon()
	{
		return weapon;
	}
	public void meleeDamage(int dmg)
	{
		HP -= dmg;
		if (HP < 1)
			HP = 0;
		if (HP == 0 && dieHard)
		{
			if (!dieHardUsed)
			{
				dieHardUsed = true;
				HP = 1;
				System.out.println(name + " withstood the blow!");
			}
		}
		else if (HP < 1)
		{
			checkDeath();
		}
	}
	public void elementDamage(int dmg, String type)
	{
		int totalDamage = dmg;
		
		if (getAccessory().equals("Ring of Frost Resistance") && type.equals("cold"))
		{
			dmg -= 10;
			if (dmg < 0)
				dmg = 0;
		}
		
		if (getArmor().hasQuality("Absorbtion"))
		{
			dmg -= 10;
			if (dmg < 0)
				dmg = 0;
		}
		
		HP -= totalDamage;
		if (HP < 1)
			HP = 0;
		if (HP == 0 && dieHard)
		{
			if (!dieHardUsed)
			{
				dieHardUsed = true;
				HP = 1;
				System.out.println(name + " withstood the blow!");
			}
		}
		else if (HP < 1)
		{
			checkDeath();
		}
	}
	public int getDR()
	{
		int totalDR = DR;
		
		if (drunkenNumbness && isDrunk())
		{
			totalDR += 2;
		}
		if (Game.ironSkin)
		{
			totalDR += 5;
		}
		
		return totalDR;
	}
	public void addXP(int exp)
	{
		if (talented > 0.0)
		{
			System.out.println("TESTING\n{ Orig XP: " + exp + " }\n{ Extra XP: " + (exp * talented) + " }");
			exp += (exp * talented);
		}
		XP += exp;
		System.out.println("Gained " + exp + " XP!");
	}
	public void changeACmod(int value)
	{
		ACmod += value;
	}
	public void setGold(int value)
	{
		Gold = value;
	}
	public void changeGold(int num)
	{
		Gold += num;
		
		if (num >= 0)
		{
			if (observant > 0.0)
			{
				System.out.println("TESTING\n{ Orig gold: " + num + " }\n{ Extra gold: " + (num * observant) + " }");
				num += (num * observant);
			}
			System.out.println("Gained " + num + " gold!");
		}
		else
		{
			System.out.println("lost " + Math.abs(num) + " gold!");
		}
	}
	public void changeHP(int value)
	{
		if (value > 0)
		{
			System.out.println("Gained " + value + " HP!");
			HP += value;
		}
		else
		{
			System.out.println("Lost " + value + " HP!");
			HP += value;
		}
		if (HP > HPTotal)
		{
			HP = HPTotal;
		}
	}
	public void setHP(int value)
	{
		HP = value;
	}
	public int escape()
	{
		return (roll.d20() + ((getDex() - 10) / 2)) + escapeMod;
	}
	public boolean hasPowerAttack()
	{
		return powerAttack;
	}
	public boolean hasSmite()
	{
		return smite;
	}
	public void poorRest()
	{
		System.out.println("You rest the night in the poor room.");
		System.out.println("Recovered " + level + " health!");
		HP = HP + level;
		MP = getMPTotal();
		recoverPoison();
		dieHardUsed = false;
		resetBonuses();
		if (HP > HPTotal)
			HP = HPTotal;
	}
	public void goodRest()
	{
		System.out.println("You rest the night in the good room.");
		System.out.println("Recovered " + (level * 2) + " health!");
		HP = HP + (level * 2);
		MP = getMPTotal();
		recoverPoison();
		dieHardUsed = false;
		resetBonuses();
		if (HP > HPTotal)
			HP = HPTotal;
	}
	public void bestRest()
	{
		System.out.println("You rest the night in the best room.");
		System.out.println("Recovered " + (level * 4) + " health!");
		HP = HP + (level * 4);
		MP = getMPTotal();
		recoverPoison();
		dieHardUsed = false;
		resetBonuses();
		if (HP > HPTotal)
			HP = HPTotal;
	}
	public boolean hasMummyRot()
	{
		return mummyRot;
	}
	public void setMummyRot(boolean value)
	{
		mummyRot = value;
	}
	public void mummyRotDamage()
	{
		if (savingThrow(Saves.Fortitude, true, false) < 16)
		{
			System.out.println("Mummy rot has affected " + getName());
			poison("Constitution", 1, 6, false);
			poison("Charisma", 1, 6, false);
		}
	}
	public boolean isDiseased()
	{
		boolean output = false;
		output = mummyRot;
		return output;
	}
	public void recoverPoison()
	{
		if (StrPoison > 0)
		{
			Strength++;
			StrPoison--;
		}
		if (DexPoison > 0)
		{
			Dexterity++;
			DexPoison--;
		}
		if (ConPoison > 0)
		{
			Constitution++;
			ConPoison--;
		}
		if (IntPoison > 0)
		{
			Intelligence++;
			IntPoison--;
		}
		if (WisPoison > 0)
		{
			Wisdom++;
			WisPoison--;
		}
		if (ChaPoison > 0)
		{
			Charisma++;
			ChaPoison--;
		}
		if (StrPoison == 0 && DexPoison == 0 && ConPoison == 0
				&& IntPoison == 0 && WisPoison == 0 && ChaPoison == 0)
		{
			abilityDamaged = false;
		}
	}
	public String getPoisonedStats()
	{
		String output = "";
		if (StrPoison > 0)
			output += "Str " + getStr() + "(" + (getStr() + StrPoison) + ")\n";
		if (DexPoison > 0)
			output += "Dex " + getDex() + "(" + (getDex() + DexPoison) + ")\n";
		if (ConPoison > 0)
			output += "Con " + getCon() + "(" + (getCon() + ConPoison) + ")\n";
		if (IntPoison > 0)
			output += "Int " + getInt() + "(" + (getInt() + IntPoison) + ")\n";
		if (WisPoison > 0)
			output += "Wis " + getWis() + "(" + (getWis() + WisPoison) + ")\n";
		if (ChaPoison > 0)
			output += "Cha " + getCha() + "(" + (getCha() + ChaPoison) + ")\n";
		
		return output;
	}
	public boolean isAbilityDamaged()
	{
		return abilityDamaged;
	}
	public void cureAbilityDamage(String stat, int amount)
	{
		int total = 0;
		if (stat.equals("Strength"))
		{
			for (int index = 0; index < amount; index++)
			{
				if (StrPoison > 0)
				{
					StrPoison--;
					Strength++;
					total++;
				}
				else
					break;
			}
		}
		else if (stat.equals("Dexterity"))
		{
			for (int index = 0; index < amount; index++)
			{
				if (DexPoison > 0)
				{
					DexPoison--;
					Dexterity++;
					total++;
				}
				else
					break;
			}
		}
		else if (stat.equals("Constitution"))
		{
			for (int index = 0; index < amount; index++)
			{
				if (ConPoison > 0)
				{
					ConPoison--;
					Constitution++;
					total++;
				}
				else
					break;
			}
		}
		else if (stat.equals("Intelligence"))
		{
			for (int index = 0; index < amount; index++)
			{
				if (IntPoison > 0)
				{
					IntPoison--;
					Intelligence++;
					total++;
				}
				else
					break;
			}
		}
		else if (stat.equals("Wisdom"))
		{
			for (int index = 0; index < amount; index++)
			{
				if (WisPoison > 0)
				{
					WisPoison--;
					Wisdom++;
					total++;
				}
				else
					break;
			}
		}
		else if (stat.equals("Charisma"))
		{
			for (int index = 0; index < amount; index++)
			{
				if (ChaPoison > 0)
				{
					ChaPoison--;
					Charisma++;
					total++;
				}
				else
					break;
			}
		}
		else
			System.out.println("Programmer error: " + stat + " is not an ability score");
		System.out.println(stat + " restored by " + total + " points!");
	}
	public void poison(String stat, int numDice, int diceSides, boolean poison)
	{
		if (getAccessory().equals("Necklace of Purity") && poison)
		{
			System.out.println("The poison has no effect!");
		}
		else
		{
			int poisonAmount = roll.XdY(numDice, diceSides);
			if (stat.equalsIgnoreCase("Strength"))
			{
				Strength -= poisonAmount;
				StrPoison += poisonAmount;
			}
			else if (stat.equalsIgnoreCase("Dexterity"))
			{
				Dexterity -= poisonAmount;
				DexPoison += poisonAmount;
			}
			else if (stat.equalsIgnoreCase("Constitution"))
			{
				int hpLoss = 0;
				for (int index = 0; index <= poisonAmount; index += 2);
				{
					hpLoss++;
				}
				HP -= (level * hpLoss);
				Constitution -= poisonAmount;
				ConPoison += poisonAmount;
			}
			else if (stat.equalsIgnoreCase("Intelligence"))
			{
				Intelligence -= poisonAmount;
				IntPoison += poisonAmount;
			}
			else if (stat.equalsIgnoreCase("Wisdom"))
			{
				Wisdom -= poisonAmount;
				WisPoison += poisonAmount;
			}
			else if (stat.equalsIgnoreCase("Charisma"))
			{
				Charisma -= poisonAmount;
				ChaPoison += poisonAmount;
			}
			else
			{
				System.out.println("Programmer error: " + stat + " is not an ability score.");
			}
			abilityDamaged = true;
			System.out.println(stat + " reduced by " + poisonAmount);
			if (getStr() <= 0 || getDex() <= 0 || getCon() <= 0
					|| getInt() <= 0 || getWis() <= 0 || getCha() <= 0
					|| HP < 1)
			{
				checkDeath();
			}
		}
	}
	public void checkDeath()
	{
		boolean dead = false;
		
		if (getStr() <= 0)
		{
			System.out.println(getName() + " is too weak to move!");
			dead = true;
		}
		else if (getDex() <= 0)
		{
			System.out.println(getName() + " is unable to coordinate muscle movement!");
			dead = true;
		}
		else if (getCon() <= 0)
		{
			System.out.println(getName() + "s body stops functioning!");
			dead = true;
		}
		else if (getInt() <= 0)
		{
			System.out.println(getName() + "s brain stops functioning!");
			dead = true;
		}
		else if (getWis() <= 0)
		{
			System.out.println(getName() + " loses consciousness and begins to have a nightmare!");
			dead = true;
		}
		else if (getCha() <= 0)
		{
			System.out.println(getName() + " slips into a coma!");
			dead = true;
		}
		else if (HP < 1)
		{
			dead = true;
		}

		if (dead)
		{
			System.out.println(getName() + " has died!");
			
			System.exit(0);
		}
	}
	public boolean hasSlippery()
	{
		return slippery;
	}
	public void potion(int strength)
	{
		int heal = 0;
		if (strength == 1)
		{
			System.out.println(name + " drank a potion!");
			heal = (roll.d8() + level);
		}
		else if (strength == 2)
		{
			System.out.println(name + " drank a greater potion!");
			heal = (roll.XdY(2, 8) + (level * 2));
		}
		else
		{
			System.out.println("Programmer error: Potion strength " + strength + " does not exist");
		}
		HP += heal;
		System.out.println("Regained " + heal + " HP!");
		if (HP > HPTotal)
			HP = HPTotal;
	}
	public void fullPotion()
	{
		System.out.println(name + " drank a full potion!");
		HP = HPTotal;
		System.out.println("All HP recovered!");
	}
	public void revitalisingPotion()
	{
		System.out.println(name + " drank a revitalising potion!");
		HP = HPTotal;
		MP = MPTotal;
		for (int index = 0; index < 7; index++)
		{
			recoverPoison();
		}
		System.out.println("You feel rested!");
	}
	public void manaPotion(int strength)
	{
		int MPheal = 0;
		if (strength == 1)
		{
			System.out.println(name + " drank a mana potion!");
			MPheal = roll.d3() + 2;
		}
		else if (strength == 2)
		{
			System.out.println(name + " drank a greater mana potion!");
			MPheal = roll.d4() + 4;
		}
		else if (strength == 3)
		{
			System.out.println(name + " drank a major mana potion!");
			MPheal = roll.XdY(2, 3) + 6;
		}
		else
		{
			System.out.println("Programmer error: Potion strength " + strength + " does not exist");
		}
		MP += MPheal;
		System.out.println("Regained " + MPheal + " MP!");
		if (MP > getMPTotal())
			MP = getMPTotal();
	}
	public void minHP()
	{
		if (HP < 0)
			HP = 0;
	}
	public void changeMP(int value)
	{
		if (value > 0)
		{
			System.out.println("Gained " + value + " MP!");
			MP = MP + value;
			if (MP > getMPTotal())
				MP = getMPTotal();
		}
		else if (value == 0)
		{
			System.out.println("You don't have enough MP.");
		}
		else
		{
			System.out.println("Lost " + Math.abs(value) + " MP!");
			MP = MP + value;
		}
	}
	public int touchSpellHit(String enemyType)
	{
		int amount = roll.d20() + (baseAttack + ((getDex() - 10) / 2));
		
		amount -= armorCheckPenalty();
		amount += favoredEnemyAttackRoll(enemyType);
		
		return amount;
	}
	public void polymorph()
	{
		Strength += 4;
		Dexterity += 4;
		Constitution += 4;
		HPTotal += level * 2;
		HP += level * 2;
		System.out.println("Strength increased by 4!\nDexterity increased by "
				+ "4!\nConstitution increased by 4!");
		System.out.println(getStats());
	}
	public void reversePolymorph()
	{
		Strength -= 4;
		Dexterity -= 4;
		Constitution -= 4;
		HPTotal -= level * 2;
		HP -= level * 2;
		System.out.println("Strength decreased by 4!\nDexterity decreased by "
				+ "4!\nConstitution decreased by 4!");
		System.out.println(getStats());
		if (HP < 1)
		{
			System.out.println(name + " has died!");
			System.exit(0);
		}
	}
	public void mageArmor(boolean extended)
	{
		if (extended)
		{
			hasMageArmor = true;
		}
		ACmod += 4;
		System.out.println("AC increased by 4!");
		System.out.println(getStats());
	}
	public void reverseMageArmor(boolean endExtended)
	{
		if (!hasMageArmor || endExtended)
		{
			System.out.println("Mage Armor has worn off!");
			hasMageArmor = false;
			ACmod -= 4;
			System.out.println("AC decreased by 4!");
			System.out.println(getStats());
		}
	}
	public void iceArmor()
	{
		iceArmor = (getCasterLevel() * 2) / 3;
		ACmod += iceArmor;
		System.out.println("AC increased by " + iceArmor + "!");
	}
	public void iceArmorMelt()
	{
		System.out.println("The ice armor has melted slightly!");
		iceArmor--;
		ACmod--;
	}
	public void reverseIceArmor()
	{
		ACmod -= iceArmor;
		iceArmor = 0;
		System.out.println("Ice armor has worn off!");
	}
	public int getIceArmor()
	{
		return iceArmor;
	}
	
	public void newQuest(String questName, String questDetails, String id,
			int req, String giver)
	{
		quest = new Quest(questName, questDetails, id, req, giver);
	}
	public String getQuest()
	{
		return quest.getQuest();
	}
	public Quest getCurrentQuest()
	{
		return quest;
	}
	public String getQuestID()
	{
		return quest.getID();
	}
	public void questOneDown()
	{
		quest.oneDown();
	}
	public boolean questIsComplete()
	{
		return quest.isComplete();
	}
	public void noQuest()
	{
		quest.setID("none");
	}
	public void levelUp(Magic magic)
	{
		boolean loop = true;
		
		if (XP >= NextLevel)
		{
			if (HP == HPTotal)
			{
				System.out.println("Level Up!");
				XP -= NextLevel;
				NextLevel += 1000;
				level++;
				String input = "";

				if (level % 4 == 0)
				{
					while (loop == true) {
					System.out.println("Which ability score will you "
							+ "increase? ");
					input = scan.nextLine();
					if (input.equalsIgnoreCase("Str"))
					{
						changeStat("Strength", 1);
						loop = false;
					}
					else if (input.equalsIgnoreCase("Dex"))
					{
						changeStat("Dexterity", 1);
						loop = false;
					}
					else if (input.equalsIgnoreCase("Con"))
					{
						changeStat("Constitution", 1);
						loop = false;
						
					}
					else if (input.equalsIgnoreCase("Int"))
					{
						changeStat("Intelligence", 1);
						loop = false;
					}
					else if (input.equalsIgnoreCase("Wis"))
					{
						changeStat("Wisdom", 1);
						loop = false;
					}
					else if (input.equalsIgnoreCase("Cha"))
					{
						changeStat("Charisma", 1);
						loop = false;
					}
					else
					{
						System.out.println("That is not one of the choices.");
					}
					}
				}
				if (type.equalsIgnoreCase("caster"))
				{
					if (level == 3)
					{
						System.out.println("Select a new spell to learn.");
						System.out.println("\nScorching Blast");
						System.out.println("\nSpirit Sword");
						System.out.println("\nIce Spike");
						while (loop == true) {
							
						String command = scan.nextLine();
						
						if (command.equalsIgnoreCase("Scorching Blast"))
						{
							magic.addSpell("Scorching Blast", 2, false);
							loop = false;
						}
						else if (command.equalsIgnoreCase("Spirit Sword"))
						{
							magic.addSpell("Spirit Sword", 2, false);
							loop = false;
						}
						else if (command.equalsIgnoreCase("Ice Spike"))
						{
							magic.addSpell("Ice Spike", 2, false);
							loop = false;
						}
						}
						loop = true;
					}
					if (level == 5)
					{
						System.out.println("Select a new spell to learn.");
						System.out.println("\nFireball");
						System.out.println("\nArrow of Force");
						System.out.println("\nFreezing Ray");
						while (loop == true) {
							
						String command = scan.nextLine();
						
						if (command.equalsIgnoreCase("Fireball"))
						{
							magic.addSpell("Fireball", 3, true);
							loop = false;
						}
						else if (command.equalsIgnoreCase("Arrow of Force"))
						{
							magic.addSpell("Arrow of Force", 3, false);
							loop = false;
						}
						else if (command.equalsIgnoreCase("Freezing Ray"))
						{
							magic.addSpell("Freezing Ray", 3, false);
							loop = false;
						}
						}
						loop = true;
					}
					if (level == 7)
					{
						System.out.println("Select a new spell to learn.");
						System.out.println("\nIgnite");
						System.out.println("\nFist of Force");
						System.out.println("\nIce Armor");
						while (loop)
						{
							String command = scan.nextLine();
							
							if (command.equalsIgnoreCase("Ignite"))
							{
								magic.addSpell("Ignite", 4, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("Fist of Force"))
							{
								magic.addSpell("Fist of Force", 4, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("Ice Armor"))
							{
								magic.addSpell("Ice Armor", 4, false);
								loop = false;
							}
						}
						loop = true;
					}
					if (level == 9)
					{
						System.out.println("Select a new spell to learn.");
						System.out.println("\nIncendiary Cloud");
						System.out.println("\nForce Hand");
						System.out.println("\nCone of Cold");
						while (loop)
						{
							String command = scan.nextLine();
							
							if (command.equalsIgnoreCase("Incendiary Cloud"))
							{
								magic.addSpell("Incendiary Cloud", 5, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("Force Hand"))
							{
								magic.addSpell("Force Hand", 5, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("Cone of Cold"))
							{
								magic.addSpell("Cone of Cold", 5, false);
								loop = false;
							}
						}
						loop = true;
					}
					if (level == 11)
					{
						System.out.println("Select a new spell to learn.");
						System.out.println("\nConflagration");
						System.out.println("\nForce Rupture");
						System.out.println("\nDeep Freeze");
						while (loop)
						{
							String command = scan.nextLine();
							
							if (command.equalsIgnoreCase("Conflagration"))
							{
								magic.addSpell("Conflagration", 6, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("Force Rupture"))
							{
								magic.addSpell("Force Rupture", 6, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("Deep Freeze"))
							{
								magic.addSpell("Deep Freeze", 6, false);
								loop = false;
							}
						}
						loop = true;
					}
					if (level == 13)
					{
						System.out.println("Select a new spell to learn.");
						System.out.println("\nMagma Spray");
						System.out.println("\nForce Scythe");
						System.out.println("\nAvalanche");
						while (loop)
						{
							String command = scan.nextLine();
							
							if (command.equalsIgnoreCase("Magma Spray"))
							{
								magic.addSpell("Magma Spray", 7, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("Force Scythe"))
							{
								magic.addSpell("Force Scythe", 7, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("Avalanche"))
							{
								magic.addSpell("Avalanche", 7, false);
								loop = false;
							}
						}
						loop = true;
					}
					if (level == 15)
					{
						System.out.println("Select a new spell to learn.");
						System.out.println("\nDeadly Lahar");
						System.out.println("\nCrushing Prison");
						System.out.println("\nHypothermia");
						while (loop)
						{
							String command = scan.nextLine();
							
							if (command.equalsIgnoreCase("Deadly Lahar"))
							{
								magic.addSpell("Deadly Lahar", 8, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("Crushing Prison"))
							{
								magic.addSpell("Crushing Prison", 8, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("Hypothermia"))
							{
								magic.addSpell("Hypothermia", 8, false);
								loop = false;
							}
						}
						loop = true;
					}
					if (level == 17)
					{
						System.out.println("Select a new spell to learn.");
						System.out.println("\nInferno");
						System.out.println("\nForce Spear");
						System.out.println("\nBlizzard");
						while (loop)
						{
							String command = scan.nextLine();
							
							if (command.equalsIgnoreCase("Inferno"))
							{
								magic.addSpell("Inferno", 9, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("Force Spear"))
							{
								magic.addSpell("Force Spear", 9, false);
								loop = false;
							}
							else if (command.equalsIgnoreCase("\nBlizzard"))
							{
								magic.addSpell("Blizzard", 9, false);
								loop = false;
							}
						}
					}
				}
				
				if (level % 3 == 0)
				{
					chooseFeat(magic, false, false);
				}
				if (level % 5 == 0 && type.equals("Fighter"))
				{
					MPmod++;
					MPTotal = MPmod;
					MP = getMPTotal();
					
					if (level == 5 || true)
					{
						while (loop)
						{
							System.out.println("Select a Quality\n");
							System.out.println("Damage Reduction    ----- Take 2 less damage from physical sources.");
							System.out.println("Rage                ----- Rage in combat for 1MP, gain combat boosts.");
							System.out.println("Proficiency         ----- Deal 2 more damage with a specific weapon.");
							System.out.println("Unarmed Combat      ----- Deal unarmed damage with a better dice roll.");
							System.out.println("Smite               ----- Smite in combat for 1MP, deals your level as bonus");
							System.out.println("                          damage if it hits, charisma applies to the attack roll.");
							System.out.println("Favored Enemy       ----- +1 to hit and +2 damage against a type of enemy.");
							System.out.println("Warmage             ----- Get 10% spell failure ignored from armor and +1MP.");
							System.out.println("Drunken Master      ----- Take less penalties for drinking alcohol.");
							System.out.println("Arcane Strike       ----- Spend mana on a melee attack to deal extra damage and");
							System.out.println("                          have a higher chance to hit.");
							
							String choice = scan.nextLine();
							if (choice.equalsIgnoreCase("Damage Reduction"))
							{
								DR += 2;
								System.out.println("Damage Reduction increased by 2!");
								loop = false;
								if (qualities.equals("Qualities: "))
								{
									qualities = qualities + "Damage Reduction";
								}
								else
								{
									qualities = qualities + ", " + "Damage Reduction";
								}
							}
							else if (choice.equalsIgnoreCase("Rage"))
							{
								if (rage)
								{
									System.out.println("You can't take this again");
								}
								else
								{
									rage = true;
									System.out.println("Can now type rage in combat to gain boosts");
									loop = false;
									
									if (qualities.equals("Qualities: "))
									{
										qualities = qualities +  "Rage";
									}
									else
									{
										qualities = qualities + ", " + "Rage";
									}
								}
							}
							else if (choice.equalsIgnoreCase("Proficiency"))
							{
								System.out.println("Which weapon will you train with?");
								do
								{
									choice = scan.nextLine();
									if (choice.equalsIgnoreCase("sword"))
									{
										swordProf += 2;
									}
									else if (choice.equalsIgnoreCase("axe"))
									{
										axeProf += 2;
									}
									else if (choice.equalsIgnoreCase("rapier"))
									{
										rapProf += 2;
									}
									else if (choice.equalsIgnoreCase("shiv"))
									{
										shivProf += 2;
									}
									else if (choice.equalsIgnoreCase("quarterstaff"))
									{
										quartProf += 2;
									}
									else if (choice.equalsIgnoreCase("mace"))
									{
										maceProf += 2;
									}
									else if (choice.equalsIgnoreCase("warhammer"))
									{
										warHammerProf += 2;
									}
									else if (choice.equalsIgnoreCase("unarmed"))
									{
										unarmedProf += 2;
									}
									else
									{
										System.out.println("Invalid input");
										choice = "";
									}
									if (choice.equals("") == false)
									{
										System.out.println(choice + " damage "
												+ "increased by 2!");
									}
									String temp = choice + " Proficiency";
									choice = temp.substring(0, 1).toUpperCase() + temp.substring(1, temp.length());
								}
								while (choice.equals(""));
								loop = false;
								if (qualities.equals("Qualities: "))
								{
									qualities = qualities + choice;
								}
								else
								{
									qualities = qualities + ", " + choice;
								}
							}
							else if (choice.equalsIgnoreCase("unarmed combat"))
							{
								boolean tooMuch = true;
								if (unarmedCombatDamageDice != 10)
								{
									loop = false;
									tooMuch = false;
								}
								if (unarmedCombatDamageDice == 3)
								{
									unarmedCombatDamageDice = 8;
								}
								else if (unarmedCombatDamageDice == 8)
								{
									unarmedCombatNumDice = 2;
									unarmedCombatDamageDice = 6;
								}
								else if (unarmedCombatDamageDice == 6)
								{
									unarmedCombatDamageDice = 10;
								}
								if (tooMuch)
								{
									System.out.println("You cannot take this again.");
								}
								else
								{
									System.out.println("Unarmed damage: " + unarmedCombatNumDice + "d" + unarmedCombatDamageDice);
								
									if (qualities.equals("Qualities: "))
									{
										qualities = qualities + "Unarmed Combat";
									}
									else
									{
										qualities = qualities + ", " + "Unarmed Combat";
									}
								}
							}
							else if (choice.equalsIgnoreCase("Favored Enemy"))
							{
								String type = "";
								while (loop)
								{
									System.out.println("Animal");
									System.out.println("Humanoid");
									System.out.println("Undead");
									System.out.println("Fey (only 1 creature currently)");
									System.out.println("Elemental (only 2 creatures currently)");
									System.out.println("Magical Beast");
									System.out.print("What type will you choose? ");
									type = scan.nextLine();
									
									if (type.equalsIgnoreCase("Animal"))
									{
										type = "Animal";
										animalFocus += 2;
										loop = false;
									}
									else if (type.equalsIgnoreCase("Humanoid"))
									{
										type = "Humanoid";
										humanFocus += 2;
										loop = false;
									}
									else if (type.equalsIgnoreCase("Undead"))
									{
										type = "Undead";
										undeadFocus += 2;
										loop = false;
									}
									else if (type.equalsIgnoreCase("Fey"))
									{
										type = "Fey";
										feyFocus += 2;
										loop = false;
									}
									else if (type.equalsIgnoreCase("Elemental"))
									{
										type = "Elemental";
										elementalFocus += 2;
										loop = false;
									}
									else if (type.equalsIgnoreCase("Magical Beast"))
									{
										type = "Magical Beast";
										magicBeastFocus += 2;
										loop = false;
									}
									else
										System.out.println("Invalid Input");
								}
								if (qualities.equals("Qualities: "))
								{
									qualities = qualities + "Favored Enemy: " + type;
								}
								else
								{
									qualities = qualities + ", " + "Favored Enemy: " + type;
								}
							}
							else if (choice.equalsIgnoreCase("Smite"))
							{
								if (!smite)
								{
									System.out.println("Type smite in combat to smite for 1MP");
									smite = true;
									loop = false;
									
									if (qualities.equals("Qualities: "))
									{
										qualities = qualities + "Smite";
									}
									else
									{
										qualities = qualities + ", Smite";
									}
								}
								else
									System.out.println("You can't take this again.");
							}
							else if (choice.equalsIgnoreCase("Warmage"))
							{
								if (spellFailureMod < 30)
								{
									spellFailureMod += 10;
									MPmod++;
									MPTotal = MPmod;
									MP = getMPTotal();
									loop = false;
									
									if (qualities.equals("Qualities: "))
									{
										qualities = qualities + "Warmage";
									}
									else
									{
										qualities = qualities + ", Warmage";
									}
								}
								else
								{
									System.out.println("There would be no point.");
								}
							}
							else if (choice.equalsIgnoreCase("Drunken Master"))
							{
								if (!drunkenMaster)
								{
									drunkenMaster = true;
									loop = false;
									System.out.println("Will now take less penalties for drinking alcohol!");
									
									if (qualities.equals("Qualities: "))
									{
										qualities = qualities + "Drunken Master";
									}
									else
									{
										qualities = qualities + ", Drunken Master";
									}
								}
								else
								{
									System.out.println("You cannot take this again.");
								}
							}
							else if (choice.equalsIgnoreCase("Arcane Strike"))
							{
								if (!arcaneStrike)
								{
									arcaneStrike = true;
									loop = false;
									System.out.println("Can now type arcane strike in combat to spend");
									System.out.println("mana on a melee attack!");
									
									if (qualities.equals("Qualities: "))
									{
										qualities += "Arcane Strike";
									}
									else
									{
										qualities += ", Arcane Strike";
									}
								}
								else
								{
									System.out.println("You cannot take this again.");
								}
							}
						}
						loop = true;
					}
				}
				if (type.equals("Fighter"))
				{
					baseAttack = level + ATKmod;
					int rng = roll.d10() + ((getCon() - 10) / 2);
					if (improvedToughness)
						rng++;
					if (rng < 1)
						rng = 1;
					HPTotal += rng;
					HP = HPTotal;
					MP = getMPTotal();
					System.out.println("Gained " + rng + " HP!");
					System.out.println(getStats());
				}
				else
				{
					baseAttack = (level / 2) + ATKmod;
					int rng = roll.d6() + ((getCon() - 10) / 2);
					if (improvedToughness)
						rng++;
					if (rng < 1)
						rng = 1;
					HPTotal += rng;
					HP = HPTotal;
					MPTotal = level + 1 + ((getWis() - 10) / 2) + MPmod;
					MP = getMPTotal();
					System.out.println("Gained " + rng + " HP!");
					System.out.println(getStats());
				}
				if (getAccessory().substring(0, getAccessory().length() - 3).equals("Amulet of Health"))
				{
					int index = getAccessory().indexOf('+');
					int change = Integer.parseInt("" + getAccessory().charAt(index + 1)) / 2;
					HPTotal -= (level - 1) * change;
					HPTotal += level * change;
					HP = HPTotal;
				}
			}
			else
			{
				System.out.println("You must be uninjured to level up.");
			}
		}
		else
		{
			System.out.println("You do not have enough XP to level up.");
		}
	}
	
	public void chooseFeat(Magic magic, boolean firstFeat, boolean humanFeat)
	{
		boolean loop = true;
		String input;
		
		while (loop == true)
		{
			boolean notAChoice = false;
			System.out.println("---Repeatable feats---");
			System.out.println("Toughness                ----- +3 HP");
			System.out.println("Dodge                    ----- +1 AC");
			System.out.println("Focus                    ----- +1 to hit enemies");
			if (!firstFeat)
				System.out.println("Bonus Spell              ----- Get a unique spell added to your spellbook");
			if (!humanFeat)
				System.out.println("Mana                     ----- +2 MP");
			System.out.println("Spellpower               ----- +1 to spell DCs");
			if (getInt() >= 15 || getWis() >= 15 || getCha() >= 15)
				System.out.println("Spell School Focus       ----- +4 damage from spells of a certain element");
			System.out.println("Mobility                 ----- +2 on escape rolls");
			System.out.println("Improved Grapple         ----- +2 on grapple checks");
			System.out.println("Great Fortitude          ----- +2 on fortitude saves");
			System.out.println("Lightning Reflexes       ----- +2 on reflex saves");
			System.out.println("Iron Will                ----- +2 on will saves");
			if (!firstFeat && lightArmorProf && spellFailureMod < 30)
				System.out.println("Battle Caster            ----- Ignore 5% spell Failure chance from armor");
			if (getInt() >= 17 + (magicalSupremacy * 2))
				System.out.println("Magical Supremacy        ----- Spells function as though you were 1 level\n" +
						           "                               higher");
			if (getCha() >= 17)
				System.out.println("Arcane Specialization    ----- Lower the MP cost of a spell you\n" +
						           "                               know by 1 or raise it by any number\n" +
						           "                               up to 4");
			
			System.out.println("Talented                 ----- Earn 25% more experience than normal");
			System.out.println("Observant                ----- Find 25% more gold than normal");
			
			
			String[] oneTimeFeats = new String[26];
			if (firstFeat)
				oneTimeFeats[0] = "Wealth                   ----- Get a bonus 400GP";
			if (getStr() >= 13 && powerAttack == false)
				oneTimeFeats[1] = "Power Attack             ----- Lets you power attack in combat";
			if (getInt() >= 13 && expertise == false)
				oneTimeFeats[2] = "Expertise                ----- Subtract an amount (up to 5 but no more than \n\t\t\t       your level) from your attack roll, " +
						"but add the \n\t\t\t       same amount to AC for the turn";
			if (getCha() >= 13 && forceOfPersonality == false)
				oneTimeFeats[3] = "Force of Personality     ----- Will save calculated from charisma \n\t\t\t       rather than wisdom";
			if (getInt() >= 13 && insightfulReflexes == false)
				oneTimeFeats[4] = "Insightful Reflexes      ----- Reflex save calculated from \n\t\t\t       intelligence rather than dexterity";
			if (!humanFeat && fortSaveMod() > 2 && !improvedToughness)
				oneTimeFeats[5] = "Improved Toughness       ----- Get +1 HP per level, and +1 HP \n\t\t\t       every time you level up";
			if (getDex() >= 13 && slippery == false)
				oneTimeFeats[6] = "Slippery                 ----- higher chance to escape from a \n\t\t\t       grapple with larger opponents";
			if (getCon() >= 15 && !dieHard)
				oneTimeFeats[7] = "Diehard                  ----- once per day, remain at 1 HP when \n\t\t\t       you would normally die";
			if (getStr() >= 13 && strengthOfBody == false)
				oneTimeFeats[8] = "Strength of Body         ----- Fortitude save calculated from strength \n\t\t\t       rather than constitution";
			if (!firstFeat && getInt() >= 15 && !arcaneInsight && type.equals("Fighter") == false)
				oneTimeFeats[9] = "Arcane Insight           ----- Double the bonus to AC generated from \n\t\t\t       intelligence";
			if (!lightArmorProf && !humanFeat)
				oneTimeFeats[10] = "Armor Proficiency        ----- No longer take penalty on attack roll for wearing " +
						"\t\t\t       light armor";
			if (lightArmorProf && !mediumArmorProf)
				oneTimeFeats[11] = "Armor Proficiency        ----- No longer take penalty on attack roll for wearing " +
						"\t\t\t       medium armor";
			if (mediumArmorProf && !heavyArmorProf)
				oneTimeFeats[12] = "Armor Proficiency        ----- No longer take penalty on attack roll for wearing " +
						"\t\t\t       heavy armor";
			if (getCha() >= 17 && !forcefulMagic)
				oneTimeFeats[13] = "Forceful Magic           ----- Spells deal bonus damage based off charisma " +
						"\t\t\t\t       rather than intelligence.";
			if (expertise && getInt() >= 15 && !improvedExpertise)
				oneTimeFeats[14] = "Improved Expertise       ----- As Expertise, but the amount is only restricted " +
						"\t\t\t       by your BAB";
			if (getRace() == Race.Elf && !elvenGrace)
				oneTimeFeats[15] = "Elven Grace              ----- +2 Dexterity";
			if (getRace() == Race.Dwarf && !dwarvenHardiness)
				oneTimeFeats[16] = "Dwarven Hardiness        ----- +2 Constitution";
			if (getRace() == Race.Gnome && !gnomeIngenuity)
				oneTimeFeats[17] = "Gnome Ingenuity          ----- +2 Intelligence";
			if (getRace() == Race.Halfling && !halflingCunning)
				oneTimeFeats[18] = "Halfling Cunning         ----- +2 Wisdom";
			if (getRace() == Race.HalfOrc && !orcishBlood)
				oneTimeFeats[19] = "Orcish Blood             ----- +2 Strength";
			if (getRace() == Race.Arcania && !arcaniaPotency)
				oneTimeFeats[20] = "Arcania Potency          ----- +2 Charisma";
			if (!alcoholic)
				oneTimeFeats[21] = "Alcoholic                ----- Greater bonuses and penalties for drinking " +
						"\t\t\t\t       alcohol";
			if (alcoholic && !drunkenBrawler)
				oneTimeFeats[22] = "Drunken Brawler          ----- +2 AC while drunk";
			if (alcoholic && !drunkenNumbness)
				oneTimeFeats[23] = "Drunken Numbness         ----- gain damage reduction 2 while drunk";
			if (alcoholic && !drunkenPotency)
				oneTimeFeats[24] = "Drunken Potency          ----- +2 to DCs while drunk";
			if (lightArmorProf && !lightArmorMastery)
				oneTimeFeats[25] = "Light Armor Mastery      ----- Double the AC generated from light armor";
			
			boolean oneTimeFeatsDisplay = false;
			for (int index = 0; index < oneTimeFeats.length; index++)
			{
				if (oneTimeFeats[index] != null)
					oneTimeFeatsDisplay = true;
			}
			
			if (oneTimeFeatsDisplay)
			{
				System.out.println("\n---One-time feats---");
				for (int index = 0; index < oneTimeFeats.length; index++)
				{
					if (oneTimeFeats[index] != null)
						System.out.println(oneTimeFeats[index]);
				}
			}
			
			System.out.print("Which feat do you want? ");
			input = scan.nextLine();
			if (input.equalsIgnoreCase("quit"))
			{
				System.exit(0);
			}
			else if (input.equalsIgnoreCase("toughness"))
			{
				HPTotal += 3;
				HP = HPTotal;
				System.out.println("Gained 3 HP!");
				loop = false;
			}
			else if (input.equalsIgnoreCase("Wealth"))
			{
				if (firstFeat)
				{
					System.out.println("400GP received!");
					Gold += 400;
					loop = false;
				}
			}
			else if (input.equalsIgnoreCase("dodge"))
			{
				ACmod++;
				System.out.println("AC increased by 1!");
				loop = false;
			}
			else if (input.equalsIgnoreCase("improved grapple"))
			{
				grappleBonus += 2;
				System.out.println("Grapple bonus increased by 2!");
				loop = false;
			}
			else if (input.equalsIgnoreCase("focus"))
			{
				ATKmod++;
				System.out.println("Attack roll increased by" +
						" 1!");
				if (firstFeat)
					baseAttack += ATKmod;
				loop = false;
			}
			else if (input.equalsIgnoreCase("power attack"))
			{
				if (getStr() >= 13 && powerAttack == false)
				{
					powerAttack = true;
					loop = false;
					System.out.println("Type power attack in "
							+ "combat to deal more damage at the "
							+ "cost of accuracy");
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Expertise"))
			{
				if (getInt() >= 13 && expertise == false)
				{
					expertise = true;
					loop = false;
					System.out.println("Type expertise in combat to dodge at "
							+ "the cost of accuracy");
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("bonus spell"))
			{
				if (!firstFeat)
				{
					magic.rngSpell(3);
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("mana") && !humanFeat)
			{
				System.out.println("MP increased by 2!");
				MPmod += 2;
				if (type.equals("Fighter") == false)
				{
					MPTotal = level + 1 + ((getWis() - 10) / 2) + MPmod;
				}
				else
				{
					MPTotal = (level / 5) + MPmod;
				}
				MP = getMPTotal();
				loop = false;
			}
			else if (input.equalsIgnoreCase("spellpower"))
			{
				System.out.println("BDC increased by 1!");
				DCmod++;
				loop = false;
			}
			else if (input.equalsIgnoreCase("mobility"))
			{
				System.out.println("Escape chance increased by "
						+ "10%!");
				escapeMod += 2;
				loop = false;
			}
			else if (input.equalsIgnoreCase("force of personality"))
			{
				if (getCha() >= 13 && forceOfPersonality == false)
				{
					System.out.println("Will saves now calculated from charisma rather than wisdom!");
					forceOfPersonality = true;
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Insightful Reflexes"))
			{
				if (getInt() >= 13 && insightfulReflexes == false)
				{
					System.out.println("Reflex saves now calculated from intelligence rather than dexterity");
					insightfulReflexes = true;
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Improved Toughness"))
			{
				if (!humanFeat && fortSaveMod() > 2 && !improvedToughness)
				{
					System.out.println(level + " HP gained!");
					HPTotal += level;
					HP = HPTotal;
					improvedToughness = true;
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Slippery"))
			{
				if (getDex() >= 13 && slippery == false)
				{
					System.out.println("Escape from grapples with larger opponents easier");
					slippery = true;
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Great Fortitude"))
			{
				System.out.println("Fortitude save increased by 2");
				greatFortitude += 2;
				loop = false;
			}
			else if (input.equalsIgnoreCase("Lightning Reflexes"))
			{
				System.out.println("Reflex saves increased by 2");
				lightningReflexes += 2;
				loop = false;
			}
			else if (input.equalsIgnoreCase("Iron Will"))
			{
				System.out.println("Will saves increased by 2");
				ironWill += 2;
				loop = false;
			}
			else if (input.equalsIgnoreCase("Diehard"))
			{
				if (getCon() >= 15 && !dieHard)
				{
					System.out.println("Remain at 1HP when you would normally die once per day");
					dieHard = true;
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Strength of Body"))
			{
				if (getStr() >= 13 && strengthOfBody == false)
				{
					System.out.println("Fortitude saves now calculated from strength rather than constitution");
					strengthOfBody = true;
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Arcane Insight"))
			{
				if (getInt() >= 15 && !arcaneInsight && type.equals("Fighter") == false)
				{
					System.out.println("Intelligence bonus to AC is now doubled");
					arcaneInsight = true;
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Armor Proficiency"))
			{
				if (!lightArmorProf && !humanFeat)
				{
					lightArmorProf = true;
					System.out.println("Gained proficiency with light armor!");
					loop = false;
				}
				else if (lightArmorProf && !mediumArmorProf)
				{
					mediumArmorProf = true;
					System.out.println("Gained proficiency with medium armor!");
					loop = false;
				}
				else if (mediumArmorProf && !heavyArmorProf)
				{
					heavyArmorProf = true;
					System.out.println("Gained profiency with heavy armor!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Battle Caster"))
			{
				if (!firstFeat && lightArmorProf && spellFailureMod < 30)
				{
					spellFailureMod += 5;
					System.out.println(spellFailureMod + "% spell failure chance ignored!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Forceful Magic"))
			{
				if (getCha() >= 17 && !forcefulMagic)
				{
					forcefulMagic = true;
					System.out.println("Spells now deal damage from charisma instead of intelligence!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Improved Expertise"))
			{
				if (expertise && getInt() >= 15 && !improvedExpertise)
				{
					improvedExpertise = true;
					System.out.println("Expertise is now only restricted by your BAB!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Elven Grace"))
			{
				if (!elvenGrace && getRace() == Race.Elf)
				{
					elvenGrace = true;
					Dexterity += 2;
					System.out.println("Gained 2 Dexterity!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Dwarven Hardiness"))
			{
				if (!dwarvenHardiness && getRace() == Race.Dwarf)
				{
					dwarvenHardiness = true;
					Constitution += 2;
					HPTotal += level;
					HP += level;
					System.out.println("Gained 2 Constitution!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Gnome Ingenuity"))
			{
				if (!gnomeIngenuity && getRace() == Race.Gnome)
				{
					gnomeIngenuity = true;
					Intelligence += 2;
					System.out.println("Gained 2 Intelligence!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Halfling Cunning"))
			{
				if (!halflingCunning && getRace() == Race.Halfling)
				{
					halflingCunning = true;
					Wisdom += 2;
					if (type.equalsIgnoreCase("caster"))
					{
						MPTotal++;
						MP++;
					}
					System.out.println("Gained 2 Wisdom!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Orcish Blood"))
			{
				if (!orcishBlood && getRace() == Race.HalfOrc)
				{
					orcishBlood = true;
					Strength += 2;
					System.out.println("Gained 2 Strength!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Arcania Potency"))
			{
				if (!arcaniaPotency && getRace() == Race.Arcania)
				{
					arcaniaPotency = true;
					Charisma += 2;
					System.out.println("Gained 2 Charisma!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Spell School Focus"))
			{
				if (getInt() >= 15 || getWis() >= 15 || getCha() >= 15)
				{
					String[] damageTypes = Spell.getSpellDamageTypes();
					System.out.println("-----------Spell Schools-------------");
					for (int index = 0; index < damageTypes.length; index++)
					{
						System.out.println("\t" + damageTypes[index]);
					}
					System.out.println("-------------------------------------");
					while (loop)
					{
						System.out.print("Which school will you specialize in? ");
						String tempInput = scan.nextLine();
						
						boolean match = false;
						
						for (int index = 0; index < damageTypes.length; index++)
						{
							if (tempInput.equalsIgnoreCase(damageTypes[index]))
							{
								match = true;
								break;
							}
						}
						if (match)
						{
							System.out.println("Will now deal 4 more damage from "
									+ tempInput.toLowerCase() + " spells!");
							spellFocusTypes.add(tempInput.toLowerCase());
							input += ": " + tempInput.toLowerCase();
							loop = false;
						}
						else
						{
							System.out.println("Invalid Input");
						}
					}
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Alcoholic"))
			{
				if (!alcoholic)
				{
					alcoholic = true;
					System.out.println("Can now take greater bonuses and penalties when drinking alcohol!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Drunken Brawler"))
			{
				if (alcoholic && !drunkenBrawler)
				{
					drunkenBrawler = true;
					System.out.println("+2 AC while drunk!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Drunken Numbness"))
			{
				if (alcoholic && !drunkenNumbness)
				{
					drunkenNumbness = true;
					System.out.println("Gain damage reduction 2 while drunk!");
					loop = false;
				}
				notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Drunken Potency"))
			{
				if (alcoholic && !drunkenPotency)
				{
					drunkenPotency = true;
					System.out.println("+2 to DCs while drunk!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Magical Supremacy"))
			{
				if (getInt() >= 17 + (magicalSupremacy * 2))
				{
					magicalSupremacy++;
					System.out.println("Spells now function as though you were " + magicalSupremacy + " levels");
					System.out.println("higher!");
					loop = false;
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Arcane Specialization"))
			{
				if (getCha() >= 17)
				{
					magic.getSpells(false);
					while (loop)
					{
						System.out.print("Which spell will you alter? ");
						String choice = scan.nextLine();
						if (magic.hasSpell(choice))
						{
							System.out.print("Will you raise or lower the cost? (+/-) ");
							String option = scan.nextLine();
							if (option.equals("+"))
							{
								boolean done = false;
								while (!done)
								{
									System.out.print("How much will it increase? (up to 4) ");
									int amount;
									if (scan.hasNextInt())
									{
										amount = scan.nextInt();
										if (amount < 1 || amount > 4)
										{
											System.out.println("The number must be between 1 and 4, inclusive.");
										}
										else if (amount == 0)
										{
											done = true;
										}
										else
										{
											magic.findSpell(choice).changeCost(amount);
											done = true;
											loop = false;
										}
									}
									else
									{
										System.out.println("Invalid input");
									}
								}
							}
							else if (option.equals("-"))
							{
								if (magic.findSpell(choice).getCost() <= 1)
								{
									System.out.println(choice + " cannot cost less mana!");
								}
								else
								{
									magic.findSpell(choice).changeCost(-1);
									loop = false;
								}
							}
							else
							{
								System.out.println("Invalid Input");
							}
						}
						else
						{
							System.out.println("You do not know " + choice);
						}
					}
				}
				else
					notAChoice = true;
			}
			else if (input.equalsIgnoreCase("Talented"))
			{
				talented += 0.25;
				System.out.println("Will now earn 25% extra XP!");
				loop = false;
			}
			else if (input.equalsIgnoreCase("Observant"))
			{
				observant += 0.25;
				System.out.println("Will now earn 25% extra gold!");
				loop = false;
			}
			else if (input.equalsIgnoreCase("Light Armor Mastery"))
			{
				if (lightArmorProf && !lightArmorMastery)
				{
					lightArmorMastery = true;
					System.out.println("Will now receive double the AC from light armor!");
					loop = false;
				}
			}
			else
			{
				notAChoice = true;
			}
			if (notAChoice)
			{
				System.out.println("That is not one of the " +
						"choices.");
				input = null;
			}
			if (input != null)
			{
				input = Character.toUpperCase(input.charAt(0)) + input.substring(1);
				feats += "\n\t" + input;
			}
		}
	}
}
