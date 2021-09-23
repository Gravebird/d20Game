
public class Enemy {

	enum Size {Small, Medium, Large, Huge, Gargantuan, Colossal}
	
	private String name;
	private String weapon;
	private int HP;
	private int damage;
	private int damageRoll;
	private int atkRoll;
	private int dex;
	private int toHitPenalty = 0;
	private final int baseAC = 10;
	private int AC = baseAC;
	private int XPValue;
	private int GPValue;
	private int reflex;
	private int fortitude;
	private int will;
	private int initiative;		// initiative is currently unused
	private int reqLevel;
	private int touchAC;
	private int DC = 10;
	private String type;
	private String subType = "none";
	
	private Size size;
	
	private boolean incorporeal = false;
	
	private boolean canConstrict = false;
	private int constrictNumDice = 0;
	private int constrictDamageDice = 0;
	private int constrictBonus = 0;
	
	private int fastHealingAmount = 0;
	
	private int grappleRoll;
	private boolean startsGrapples = false;
	
	private boolean nextHitCrit = false;
	
	Dice roll = new Dice();
	
	public Enemy(String Name, Player player)
	{
		name = Name;
		if (name.equals("Mugger"))
		{
			HP = roll.d6() + 2;
			damage = 0;
			AC = 12;
			touchAC = 10;
			XPValue = roll.d100() + roll.d100() + roll.d100();
			GPValue = roll.XdY(2, 20);
			atkRoll = 2;
			reflex = 4;
			fortitude = 3;
			will = 1;
			weapon = "shiv";
			initiative = 1;
			reqLevel = 1;
			grappleRoll = 2;
			size = Size.Medium;
			type = "Humanoid";
			subType = "Human";
		}
		else if (name.equals("Genkakku"))
		{
			HP = 0;
			for (int index = 0; index < 8; index++)
			{
				HP += roll.d8() + 2;
			}
			for (int index = 0; index < 7; index++)
			{
				HP += roll.d12() + 2;
			}
			damage = 8;
			AC = 19;
			touchAC = 12;
			XPValue = 0;
			GPValue = 0;
			reflex = 11;
			fortitude = 13;
			will = 5;
			atkRoll = 15;
			initiative = 0;
			weapon = "spikedBracers";
			for (int index = 0; index < 30; index++)
			{
				XPValue += roll.d100();
				GPValue += roll.dX(20);
			}
			reqLevel = 15;
			grappleRoll = 21;
			size = Size.Large;
			type = "Monstrous Humanoid";
			subType = "Giant";
		}
		else if (name.equals("Raging Guard"))
		{
			HP = 0;
			XPValue = 0;
			GPValue = 0;
			for (int index = 0; index < 4; index++)
			{
				HP += roll.d10() + 2;
			}
			damage = 2;
			AC = 16;
			touchAC = 11;
			reflex = 4;
			fortitude = 6;
			will = 5;
			atkRoll = 5;
			initiative = 2;
			weapon = "longsword";
			for (int index = 0; index < 6; index++)
			{
				XPValue += (roll.d100() + roll.d100()) / 2;
				GPValue += (roll.XdY(2, 20)) / 2;
			}
			reqLevel = 4;
			grappleRoll = 7;
			size = Size.Medium;
			type = "Humanoid";
			subType = "Human";
		}
		else if (name.equals("Todd"))
		{
			HP = 0;
			XPValue = 0;
			GPValue = 0;
			for (int index = 0; index < player.getLevel(); index++)
			{
				HP += roll.d8() + 2;
				GPValue += roll.XdY(2, 20);
				XPValue += roll.d100() + roll.d100();
			}
			damage = player.getLevel() / 2;
			AC = 10 + ((player.getLevel() * 3) / 2);
			touchAC = 10 + ((player.getLevel() / 2));
			reflex = player.getLevel() / 2;
			fortitude = player.getLevel() / 2;
			will = player.getLevel() / 2;
			atkRoll = player.getLevel();
			initiative = player.getLevel() / 2;
			weapon = "longsword";
			reqLevel = player.getLevel();
			grappleRoll = atkRoll;
			size = Size.Medium;
			type = "Humanoid";
			subType = "Human";
		}
		else if (name.equals("Wolf"))
		{
			HP = roll.d8() + roll.d8() + 4;
			XPValue = 0;
			for (int index = 0; index < 5; index++)
			{
				XPValue += roll.d100();
			}
			GPValue = 0;
			damage = 1;
			AC = 14;
			touchAC = 12;
			reflex = 5;
			fortitude = 5;
			will = 1;
			atkRoll = 3;
			initiative = 2;
			weapon = "medBite";
			dex = 13;
			reqLevel = 4;
			grappleRoll = 2;
			size = Size.Medium;
			type = "Animal";
		}
		else if (name.equals("Bear"))
		{
			HP = roll.d8() + roll.d8() + roll.d8() + 6;
			XPValue = 0;
			for (int index = 0; index < 8; index++)
			{
				XPValue += roll.d100();
			}
			GPValue = 0;
			damage = 4;
			AC = 13;
			touchAC = 11;
			reflex = 4;
			fortitude = 5;
			will = 2;
			atkRoll = 6;
			initiative = 1;
			weapon = "medBite";
			reqLevel = 5;
			grappleRoll = 6;
			size = Size.Medium;
			type = "Animal";
		}
		else if (name.equals("Witch"))
		{
			HP = 0;
			XPValue = 0;
			GPValue = roll.XdY(10, 20);
			for (int index = 0; index < 10; index++)
			{
				HP += roll.d6() + 2;
				XPValue += roll.d100();
			}
			atkRoll = 5;
			AC = 12;
			touchAC = 12;
			reflex = 5;
			fortitude = 4;
			will = 8;
			reqLevel = 8;
			grappleRoll = atkRoll;
			size = Size.Medium;
			weapon = "shiv";
			type = "Humanoid";
			subType = "Human";
		}
		else if (name.equals("Dark Lord"))
		{
			HP = roll.XdY(20, 6) + 60;
			XPValue = roll.XdY(15, 100);
			GPValue = roll.XdY(15, 20);
			damage = 7;
			atkRoll = 17;
			AC = 20;
			touchAC = 20;
			reflex = 13;
			fortitude = 13;
			will = 17;
			reqLevel = 20;
			weapon = "mace";
			grappleRoll = atkRoll - 5;
			size = Size.Medium;
			type = "Humanoid";
			subType = "Human";
			DC = 24;
		}
		else if (name.equals("Skeleton"))
		{
			HP = roll.d12();
			XPValue = roll.XdY(10, 20);
			GPValue = roll.dX(20);
			damage = 1;
			atkRoll = 1;
			AC = 15;
			touchAC = 11;
			reflex = 1;
			fortitude = 0;
			will = 2;
			reqLevel = 2;
			weapon = "medBite";
			grappleRoll = 1;
			size = Size.Medium;
			type = "Undead";
		}
		else if (name.equals("Zombie"))
		{
			HP = roll.d12() + roll.d12() + 3;
			XPValue = roll.d10() + roll.XdY(14, 20);
			GPValue = roll.dX(20);
			damage = 1;
			atkRoll = 2;
			AC = 11;
			touchAC = 9;
			reflex = -1;
			fortitude = 0;
			will = 3;
			reqLevel = 3;
			weapon = "medBite";
			grappleRoll = 2;
			size = Size.Medium;
			type = "Undead";
		}
		else if (name.equals("Owlbear Skeleton"))
		{
			HP = 0;
			for (int index = 0; index < 5; index++)
			{
				HP += roll.d12();
			}
			XPValue = 0;
			GPValue = roll.XdY(10, 20);
			for (int index = 0; index < 10; index++)
			{
				XPValue += roll.d100();
			}
			damage = 5;
			atkRoll = 6;
			AC = 13;
			touchAC = 11;
			reflex = 3;
			fortitude = 1;
			will = 4;
			reqLevel = 4;
			weapon = "medBite";
			grappleRoll = 11;
			size = Size.Large;
			type = "Undead";
		}
		else if (name.equals("Dire Rat"))
		{
			HP = roll.d8() + 1;
			XPValue = roll.XdY(5, 20);
			GPValue = roll.d10();
			damage = 0;
			atkRoll = 4;
			AC = 15;
			touchAC = 14;
			reflex = 5;
			fortitude = 3;
			will = 3;
			reqLevel = 2;
			weapon = "smallBite";
			DC = 11;
			grappleRoll = -4;
			size = Size.Small;
			type = "Animal";
		}
		else if (name.equals("Crocodile"))
		{
			HP = 0;
			for (int index = 0; index < 3; index++)
			{
				HP += roll.d8() + 3;
			}
			XPValue = roll.XdY(8, 100);
			GPValue = roll.dX(20);
			damage = 6;
			atkRoll = 6;
			AC = 15;
			touchAC = 11;
			reflex = 4;
			fortitude = 6;
			will = 2;
			reqLevel = 5;
			grappleRoll = 10;
			weapon = "largeBite";
			size = Size.Medium;
			type = "Animal";
			startsGrapples = true;
		}
		else if (name.equals("Giant Crocodile"))
		{
			HP = 28;
			for (int index = 0; index < 7; index++)
			{
				HP += roll.d8();
			}
			XPValue = roll.XdY(16, 100);
			GPValue = roll.XdY(20, 20);
			damage = 12;
			atkRoll = 11;
			AC = 16;
			touchAC = 9;
			reflex = 6;
			fortitude = 9;
			will = 3;
			reqLevel = 10;
			weapon = "hugeBite";
			grappleRoll = 21;
			size = Size.Huge;
			type = "Animal";
			startsGrapples = true;
		}
		else if (name.equals("Large Viper Snake"))
		{
			HP = roll.d8() + roll.d8() + roll.d8();
			XPValue = roll.XdY(6, 100);
			GPValue = 0;
			damage = 0;
			atkRoll = 4;
			AC = 15;
			touchAC = 12;
			reflex = 6;
			fortitude = 3;
			will = 2;
			reqLevel = 3;
			weapon = "snakeBite";
			DC = 11;
			grappleRoll = 6;
			size = Size.Large;
			type = "Animal";
		}
		else if (name.equals("Small Viper Snake"))
		{
			HP = roll.d8();
			XPValue = roll.d100() + roll.d100();
			GPValue = 0;
			damage = -2;
			atkRoll = 4;
			AC = 17;
			touchAC = 14;
			reflex = 5;
			fortitude = 2;
			will = 1;
			reqLevel = 1;
			weapon = "smallSnakeBite";
			DC = 10;
			grappleRoll = -6;
			size = Size.Small;
			type = "Animal";
		}
		else if (name.equals("Huge Viper Snake"))
		{
			HP = 6 + roll.XdY(6, 8);
			XPValue = roll.XdY(12, 100);
			GPValue = 0;
			damage = 4;
			atkRoll = 6;
			AC = 15;
			touchAC = 10;
			reflex = 7;
			fortitude = 6;
			will = 3;
			reqLevel = 5;
			weapon = "medBite";
			DC = 14;
			grappleRoll = 15; 
			size = Size.Huge;
			type = "Animal";
		}
		else if (name.equals("Yuan-Ti Abomination"))
		{
			HP = roll.XdY(9, 8) + 27;
			XPValue = roll.XdY(22, 100);
			GPValue = roll.XdY(14, 100);
			damage = 3;
			atkRoll = 12;
			AC = 22;
			touchAC = 10;
			reflex = 7;
			fortitude = 6;
			will = 11;
			reqLevel = 9;
			weapon = "yuan-ti bite";
			DC = 17;
			grappleRoll = 17;
			canConstrict = true;
			constrictNumDice = 1;
			constrictDamageDice = 6;
			constrictBonus = 6;
			size = Size.Large;
			type = "Monstrous Humanoid";
			startsGrapples = true;
		}
		else if (name.equals("Mohrg"))
		{
			HP = roll.XdY(14, 12);
			XPValue = roll.XdY(30, 100);
			GPValue = 0;
			damage = 7;
			atkRoll = 12;
			AC = 23;
			touchAC = 14;
			reflex = 10;
			fortitude = 4;
			will = 9;
			reqLevel = 10;
			weapon = "medSlam";
			DC = 17;
			grappleRoll = 12;
			size = Size.Medium;
			type = "Undead";
		}
		else if (name.equals("Mummy"))
		{
			HP = roll.XdY(8, 12) + 3;
			XPValue = roll.XdY(15, 100);
			GPValue = roll.XdY(15, 20);
			damage = 10;
			atkRoll = 11;
			AC = 20;
			touchAC = 10;
			reflex = 2;
			fortitude = 4;
			will = 8;
			reqLevel = 7;
			weapon = "medSlam";
			DC = 16;
			grappleRoll = 11;
			size = Size.Medium;
			type = "Undead";
		}
		else if (name.equals("Boneclaw"))
		{
			HP = roll.XdY(10, 12) + 40;
			XPValue = roll.XdY(16, 100);
			GPValue = 0;
			damage = 7;
			atkRoll = 7;
			AC = 16;
			touchAC = 13;
			reflex = 7;
			fortitude = 3;
			will = 9;
			reqLevel = 7;
			weapon = "piercing claw";
			grappleRoll = 14;
			size = Size.Large;
			type = "Undead";
		}
		else if (name.equals("Shadow"))
		{
			HP = roll.XdY(3, 12);
			XPValue = roll.XdY(9, 100);
			GPValue = 0;
			damage = 0;
			atkRoll = 3;
			AC = 13;
			touchAC = 13;
			reflex = 3;
			fortitude = 1;
			will = 4;
			reqLevel = 4;
			weapon = "incorporeal touch";
			size = Size.Medium;
			type = "Undead";
			incorporeal = true;
		}
		else if (name.equals("Greater Shadow"))
		{
			HP = roll.XdY(9, 12);
			XPValue = roll.XdY(24, 100);
			GPValue = 0;
			damage = 0;
			atkRoll = 6;
			AC = 14;
			touchAC = 14;
			reflex = 5;
			fortitude = 3;
			will = 7;
			reqLevel = 9;
			weapon = "incorporeal touch";
			size = Size.Medium;
			type = "Undead";
			incorporeal = true;
		}
		else if (name.equals("Dire Bear"))
		{
			HP = roll.XdY(12, 8) + 51;
			XPValue = roll.XdY(22, 100);
			GPValue = roll.XdY(21, 100);
			damage = 10;
			atkRoll = 19;
			AC = 17;
			touchAC = 10;
			reflex = 9;
			fortitude = 12;
			will = 9;
			reqLevel = 13;
			weapon = "lrgClaw";
			DC = 20;
			grappleRoll = 23;
			size = Size.Large;
			type = "Animal";
		}
		else if (name.equals("Gravecrawler"))
		{
			HP = roll.XdY(25, 12);
			XPValue = roll.XdY(34, 100);
			GPValue = roll.XdY(25, 20);
			damage = 0;
			atkRoll = 13;
			AC = 17;
			touchAC = 13;
			reflex = 10;
			fortitude = 8;
			will = 16;
			reqLevel = 14;
			weapon = "smallBite";
			DC = 22;
			grappleRoll = 9;
			size = Size.Small;
			type = "Undead";
		}
		else if (name.equals("Necronaut"))
		{
			HP = roll.XdY(32, 12) + 96;
			XPValue = roll.XdY(24, 100);
			GPValue = 0;
			damage = 20;
			atkRoll = 22;
			AC = 25;
			touchAC = 5;
			reflex = 9;
			fortitude = 10;
			will = 20;
			reqLevel = 12;
			weapon = "gargSlam";
			DC = 41;
			grappleRoll = 43;
			size = Size.Gargantuan;
			type = "Undead";
		}
		else if (name.equals("Joystealer"))
		{
			HP = roll.XdY(6, 6) + 6;
			XPValue = roll.XdY(12, 100);
			GPValue = roll.XdY(6, 20);
			damage = 0;
			atkRoll = 6;
			AC = 17;
			touchAC = 17;
			reflex = 8;
			fortitude = 3;
			will = 6;
			reqLevel = 9;
			weapon = "incorporeal touch";
			DC = 0;
			size = Size.Medium;
			type = "Fey";
			subType = "Incorporeal";
			incorporeal = true;
		}
		else if (name.equals("Gladiator"))
		{
			HP = roll.XdY(5, 10) + 10;
			XPValue = roll.XdY(8, 100);
			GPValue = roll.XdY(5, 20);
			damage = 5;
			atkRoll = 9;
			AC = 19;
			touchAC = 12;
			reflex = 4;
			fortitude = 5;
			will = 2;
			reqLevel = 6;
			grappleRoll = atkRoll;
			weapon = "longsword";
			size = Size.Medium;
			type = "Humanoid";
			subType = "Human";
		}
		else if (name.equals("Warlock"))
		{
			HP = roll.XdY(13, 6) + 26;
			XPValue = roll.XdY(16, 100);
			GPValue = roll.XdY(40, 20);
			damage = 0;
			atkRoll = 11;
			AC = 33;
			touchAC = 23;
			reflex = 8;
			fortitude = 8;
			will = 10;
			reqLevel = 14;
			grappleRoll = 8;
			weapon = "unarmed";
			size = Size.Medium;
			type = "Humanoid";
			subType = "Human";
		}
		else if (name.equals("Chraal"))
		{
			HP = roll.XdY(9, 8) + 45;
			XPValue = roll.XdY(12, 100);
			GPValue = 0;
			damage = 5;
			atkRoll = 10;
			AC = 21;
			touchAC = 13;
			reflex = 3;
			fortitude = 11;
			will = 5;
			reqLevel = 9;
			weapon = "ChraalClaw";
			grappleRoll = 14;
			DC = 19;
			size = Size.Large;
			type = "Elemental";
			subType = "Cold";
		}
		else if (name.equals("Champion"))
		{
			HP = roll.XdY(20, 10) + 80;
			XPValue = roll.XdY(30, 100);
			GPValue = roll.XdY(25, 20);
			damage = 18;
			atkRoll = 27;
			AC = 23;
			touchAC = 13;
			reflex = 10;
			fortitude = 16;
			will = 7;
			reqLevel = 20;
			grappleRoll = 25;
			weapon = "medGreatsword";
			size = Size.Medium;
			type = "Humanoid";
			subType = "Human";
		}
		else if (name.equals("Bison"))
		{
			HP = roll.XdY(5, 8) + 15;
			XPValue = roll.XdY(7, 100);
			GPValue = 0;
			damage = 9;
			atkRoll = 8;
			AC = 17;
			touchAC = 12;
			reflex = 3;
			fortitude = 5;
			will = 3;
			reqLevel = 7;
			grappleRoll = 8;
			weapon = "medGore";
			size = Size.Medium;
			type = "Animal";
		}
		else if (name.equals("Vampire"))
		{
			HP = roll.XdY(13, 12);
			XPValue = roll.XdY(100, 100);
			GPValue = roll.XdY(100, 20);
			damage = 7;
			atkRoll = 16;
			AC = 32;
			touchAC = 23;
			reflex = 16;
			fortitude = 7;
			will = 13;
			reqLevel = 16;
			grappleRoll = 14;
			weapon = "medKama";
			size = Size.Medium;
			type = "Undead";
			DC = 17;
		}
		else if (name.equals("Shadow Centaur"))
		{
			HP = roll.XdY(8, 12) + 24;
			XPValue = roll.XdY(50, 100);
			GPValue = roll.XdY(50, 20);
			damage = 6;
			atkRoll = 5;
			AC = 16;
			touchAC = 11;
			reflex = 8;
			fortitude = 5;
			will = 7;
			reqLevel = 7;
			grappleRoll = 8;
			weapon = "lrgLongsword";
			size = Size.Large;
			type = "Magical Beast";
			subType = "Shadow";
			fastHealingAmount = 2;
		}
		else if (name.equals("Ripper"))
		{
			HP = roll.XdY(8, 8) + 24;
			XPValue = roll.XdY(40, 100);
			GPValue = roll.XdY(40, 20);
			damage = 2;
			atkRoll = 9;
			AC = 20;
			touchAC = 13;
			reflex = 5;
			fortitude = 5;
			will = 9;
			reqLevel = 7;
			grappleRoll = 8;
			weapon = "medClaw";
			size = Size.Medium;
			type = "Magical  Beast";
			DC = 16;
		}
		else if (name.equals("Nightcrawler"))
		{
			HP = roll.XdY(25,12) + 50;
			XPValue = roll.XdY(180, 100);
			GPValue = roll.XdY(180, 20);
			damage = 21;
			atkRoll = 29;
			AC = 35;
			touchAC = 6;
			reflex = 10;
			fortitude = 12;
			will = 23;
			reqLevel = 20;
			grappleRoll = 45;
			weapon = "gargBite";
			size = Size.Gargantuan;
			type = "Undead";
			subType = "Shadow";
			DC = 22;
			startsGrapples = true;
		}
		else if (name.equals("Nightcrawlers stomache"))
		{
			HP = 35;
			AC = 9;
			touchAC = 0;
			XPValue = 0;
			GPValue = 0;
			size = Size.Large;
			type = "Undead";
			subType = "Shadow";
		}
		else if (name.equals("Necromancer"))
		{
			HP = roll.XdY(10, 4) + 20;
			XPValue = roll.XdY(50, 100);
			GPValue = roll.XdY(50, 20);
			damage = 0;
			atkRoll = 8;
			AC = 15;
			touchAC = 14;
			reflex = 7;
			fortitude = 6;
			will = 9;
			reqLevel = 10;
			grappleRoll = 5;
			weapon = "mace";
			size = Size.Medium;
			type = "Humanoid";
			subType = "Human";
			DC = 18;
		}
		else if (name.equals("Nightwalker"))
		{
			HP = roll.XdY(21, 12) + 42;
			XPValue = roll.XdY(150, 100);
			GPValue = roll.XdY(150, 20);
			damage = 16;
			atkRoll = 24;
			AC = 32;
			touchAC = 10;
			reflex = 11;
			fortitude = 11;
			will = 19;
			reqLevel = 18;
			grappleRoll = 34;
			weapon = "hugeSlam";
			size = Size.Huge;
			type = "Undead";
			subType = "Shadow";
			DC = 24;
		}
		else if (name.equals("Dusk Beast"))
		{
			HP = roll.XdY(8, 8) + 8;
			XPValue = roll.XdY(50, 10);
			GPValue = roll.XdY(5, 20);
			damage = 3;
			atkRoll = 10;
			AC = 15;
			touchAC = 12;
			reflex = 8;
			fortitude = 7;
			will = 8;
			reqLevel = 5;
			grappleRoll = 10;
			weapon = "medBite";
			size = Size.Medium;
			type = "Magical Beast";
			subType = "Shadow";
		}
		else if (name.equals("Lich"))
		{
			HP = roll.XdY(11, 12) + 3;
			XPValue = roll.XdY(100, 20);
			GPValue = roll.XdY(15, 20);
			damage = 0;
			atkRoll = 5;
			AC = 23;
			touchAC = 14;
			reflex = 7;
			fortitude = 4;
			will = 10;
			reqLevel = 15;
			grappleRoll = 5;
			weapon = "medQuarterstaff";
			size = Size.Medium;
			type = "Undead";
		}
		else if (name.equals("Dark Lion"))
		{
			HP = roll.XdY(5, 8) + 10;
			XPValue = roll.XdY(60, 10);
			GPValue = 0;
			damage = 5;
			atkRoll = 7;
			AC = 15;
			touchAC = 12;
			reflex = 7;
			fortitude = 6;
			will = 2;
			reqLevel = 5;
			grappleRoll = 12;
			weapon = "smallNails";
			size = Size.Large;
			type = "Magical Beast";
			subType = "Shadow";
			startsGrapples = true;
		}
		else if (name.equals("Shadow Elemental"))
		{
			HP = roll.XdY(8, 8) + 24;
			XPValue = roll.XdY(100, 10);
			GPValue = 0;
			damage = 0;
			atkRoll = 7;
			AC = 12;
			touchAC = 12;
			reflex = 8;
			fortitude = 5;
			will = 2;
			reqLevel = 10;
			grappleRoll = 10;
			weapon = "incorporeal touch";
			size = Size.Large;
			type = "Elemental";
			subType = "Shadow";
			incorporeal = true;
		}
		else if (name.equals("Troll"))
		{
			HP = roll.XdY(6, 8) + 36;
			XPValue = roll.XdY(80, 10);
			GPValue = roll.XdY(80, 20);
			damage = 6;
			atkRoll = 9;
			AC = 16;
			touchAC = 11;
			reflex = 4;
			fortitude = 11;
			will = 3;
			reqLevel = 6;
			grappleRoll = 14;
			weapon = "medClaw";
			size = Size.Large;
			type = "Humanoid";
			subType = "Giant";
			fastHealingAmount = 5;
		}
		
		// 43 total enemies
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public boolean startsGrapples()
	{
		return startsGrapples;
	}
	
	public int getAtkMod()
	{
		return atkRoll;
	}
	
	public int getGrappleMod()
	{
		return grappleRoll;
	}
	
	public void fastHealing()
	{
		if (fastHealingAmount > 0 && HP > 0)
		{
			HP += fastHealingAmount;
			System.out.println("The " + name + " recovers " + fastHealingAmount + " HP.");
		}
	}
	
	public int grappleRoll()
	{
		return roll.d20() + grappleRoll;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Size getSize()
	{
		return size;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void nextHitWillCrit()
	{
		nextHitCrit = true;
	}
	
	public String getGrappleThreat(Player player)
	{
		String threatLevel;
		int advantage = player.getGrappleMod() + - getGrappleMod();
		if (canConstrict())
		{
			advantage -= 2;
		}
		
		if (advantage > 5)
			threatLevel = "pushover";
		else if (advantage > 2)
			threatLevel = "mild";
		else if (advantage > -2)
			threatLevel = "moderate";
		else if (advantage >= -5)
			threatLevel = "dangerous";
		else
			threatLevel = "severe";
		
		return threatLevel;
	}
	
	public int attackRoll()
	{
		return roll.d20() + atkRoll - toHitPenalty;
	}
	public int attack(Player player)
	{
		if (weapon.equals("shiv") || weapon.equals("snakeBite")
				|| weapon.equals("smallNails"))
		{
			damageRoll = roll.d4();
		}
		else if (weapon.equals("smallSnakeBite"))
		{
			damageRoll = roll.dX(2);
		}
		else if (weapon.equals("mace"))
		{
			damageRoll = roll.d8();
		}
		else if (weapon.equals("spikedBracers"))
		{
			damageRoll = roll.d12();
		}
		else if (weapon.equals("medBite") || weapon.equals("medSlam")
				|| weapon.equals("medKama") || weapon.equals("medClaw")
				|| weapon.equals("medQuarterstaff"))
		{
			damageRoll = roll.d6();
		}
		else if (weapon.equals("lrgClaw") || weapon.equals("medGore"))
		{
			damageRoll = roll.XdY(2, 4);
		}
		else if (weapon.equals("smallBite"))
		{
			damageRoll = roll.d4();
		}
		else if (weapon.equals("largeBite") || weapon.equals("ChraalClaw"))
		{
			damageRoll = roll.d8();
		}
		else if (weapon.equals("yuan-ti bite"))
		{
			damageRoll = roll.XdY(5, 6);
		}
		else if (weapon.equals("hugeBite"))
		{
			damageRoll = roll.XdY(2, 8);
		}
		else if (weapon.equals("longsword"))
		{
			damageRoll = roll.d8();
		}
		else if (weapon.equals("piercing claw") || weapon.equals("medGreatsword")
				|| weapon.equals("lrgSlam"))
		{
			damageRoll = roll.XdY(2, 6);
		}
		else if (weapon.equals("incorporeal touch"))
		{
			damageRoll = 0;
		}
		else if (weapon.equals("gargSlam") || weapon.equals("gargBite"))
		{
			damageRoll = roll.XdY(4, 6);
		}
		else
		{
			damageRoll = roll.d3();
		}
		
		if (name.equals("Ripper"))
		{
			damageRoll += roll.d6();
		}
		
		int totalDamage = damageRoll + damage;
		if (totalDamage < 1)
			totalDamage = 1;
		totalDamage -= player.getDR();
		if (totalDamage < 0)
			totalDamage = 0;
		if (nextHitCrit)
		{
			totalDamage *= 2;
			nextHitCrit = false;
		}
		System.out.println(name + " did " + totalDamage + " damage "
				+ "to " + player.getName() + "!");
		
		if (name == "Chraal")
		{
			int coldDamage = roll.d6();
			player.elementDamage(coldDamage, "cold");
			System.out.println(name + " did " + coldDamage + " cold damage to "
				+ player.getName() + "!");
		}
		
		return totalDamage;
	}
	public int unarmedAttack(Player player)
	{
		int totalDamage = roll.d4() + damage;
		
		if (totalDamage < 1)
			totalDamage = 1;
		totalDamage -= player.getDR();
		if (totalDamage < 0)
			totalDamage = 0;
		if (nextHitCrit)
		{
			totalDamage *= 2;
			nextHitCrit = false;
		}
		System.out.println(name + " did " + totalDamage + " damage "
				+ "to " + player.getName() + "!");
		
		return totalDamage;
	}
	public boolean canConstrict()
	{
		return canConstrict;
	}
	public int constrictDamage(Player player)
	{
		int totalDamage = roll.XdY(constrictNumDice, constrictDamageDice) + constrictBonus;
		System.out.println("The " + name + " constricts " + player.getName() + " for " + totalDamage + " damage!");
		return totalDamage;
	}
	public boolean isIncorporeal()
	{
		return incorporeal;
	}
	public int blockEscape()
	{
		int escape = roll.d20();
		if (name.equals("Mugger") || name.equals("Bear") || name.equals("Crocodile")
				|| name.equals("Giant Crocodile") || name.equals("Dire Bear"))
		{
			escape  += 1;
		}
		else if (name.equals("Necronaut"))
		{
			escape -= 1;
		}
		else if (name.equals("Raging Guard") || name.equals("Wolf") || name.equals("Gravecrawler")
				|| name.equals("Shadow Centaur") || name.equals("Huge Viper Snake")
				|| name.equals("Nightwalker") || name.equals("Dusk Beast")
				|| name.equals("Shadow Elemental")
				|| name.equals("Troll"))
		{
			escape += 2;
		}
		else if (name.equals("Dark Lion"))
		{
			escape += 3;
		}
		else if (name.equals("Todd"))
		{
			escape += reflex;
		}
		else if (name.equals("Witch") || name.equals("Dark Lord")
				|| name.equals("Mohrg"))
		{
			escape += 4;
		}
		else if (name.equals("Lich"))
		{
			escape += 5;
		}
		else if (name.equals("Small Viper Snake") || name.equals("Large Viper Snake")
				|| name.equals("Ripper") || name.equals("Necromancer"))
		{
			escape += 3;
		}
		return escape;
	}
	public int reflexSave()
	{
		return roll.d20() + reflex;
	}
	public int fortSave()
	{
		return roll.d20() + fortitude;
	}
	public int willSave()
	{
		return roll.d20() + will;
	}
	public int getInit()
	{
		return roll.d20() + initiative;
	}
	public void setHP(int value)
	{
		HP = value;
	}
	public int getHP()
	{
		return HP;
	}
	public int getAC()
	{
		return AC;
	}
	public int getTouchAC()
	{
		return touchAC;
	}
	public void damage(int dmg, Player player, String type)
	{
		if (type != null)
		{
			int tempBonus = player.getNumberOfSpellFocus(type) * 4;
			dmg += tempBonus;
			
			if (type.equals("sonic"))
			{
				Battle.lastSpell = "Sonic Damage";
			}
		}
		else
		{
			type = "none";
		}
		
		if (player.getAccessory().equals("Amulet of Hate"))
		{
			dmg += 2;
		}
		
		if (name.equals("Chraal") && type.equals("fire"))
		{
			dmg += dmg / 2;
		}
		
		if ((name.equals("Boneclaw") || name.equals("Skeleton") || name.equals("Chraal")
				|| name.equals("Owlbear Skeleton") || name.equals("Nightcrawler")
				|| name.equals("Nightwalker")) 
				&& type.equals("cold"))
		{
			dmg = 0;
		}
		
		if (subType.equals("Shadow") && type.equals("light"))
		{
			dmg *= 2;
		}
		
		if (name.equals("Shadow Centaur") && type.equals("cold"))
		{
			dmg -= 13;
			if (dmg < 0)
				dmg = 0;
		}
		else if (name.equals("Dark Lion") && type.equals("cold"))
		{
			dmg -= 10;
			if (dmg < 0)
				dmg = 0;
		}
		else if (name.equals("Lich"))
		{
			if (type.equals("cold") || type.equals("electricity"))
			{
				dmg = 0;
			}
		}
		else if (name.equals("Troll"))
		{
			if (type.equals("fire") || type.equals("acid"))
			{
				dmg = (dmg * 3) / 2;
			}
		}
		else if (incorporeal)
		{
			if (type.equals("force") == false)
			{
				// This only applies to element damage
				int rng = roll.d100();
				if (rng <= 50)
				{
					dmg = 0;
				}
			}
		}
		HP -= dmg;
		System.out.print("Did " + dmg);
		if (type.equals("none") == false)
		{
			System.out.print(" " + type);
		}
		System.out.println(" damage to the " + name + "!");
		
		if (HP < 0)
			HP = 0;
	}
	public void meleeDamage(int dmg, Weapon weapon, Player player, boolean ghostTouch)
	{
		if (weapon == null)
		{
			weapon = new Weapon("unarmed", 0);
		}
		if (player.getAccessory().equals("Amulet of Hate"))
		{
			dmg += 2;
		}
		if (name.equals("Skeleton") || name.equals("Owlbear Skeleton")
				|| name.equals("Boneclaw"))
		{
			if (weapon.getType() == Weapon.type.Bludg)
			{
				
			}
			else
			{
				System.out.println("Your weapon is ineffective.");
				dmg -= 5;
				if (dmg < 0)
					dmg = 0;
			}
		}
		else if (name.equals("Lich"))
		{
			if ((weapon.getType() == Weapon.type.Bludg && weapon.getEnhancement() > 0) == false)
			{
				System.out.println("Your weapon is ineffective");
				dmg -= 15;
				if (dmg < 0)
					dmg = 0;
			}
		}
		else if (name.equals("Mummy"))
		{
			System.out.println("Your weapon is ineffective.");
			dmg -= 5;
			if (dmg < 0)
				dmg = 0;
		}
		else if (name.equals("Zombie"))
		{
			if (weapon.getType() == Weapon.type.Slash)
			{
				
			}
			else
			{
				System.out.println("Your weapon is ineffective.");
				dmg -= 5;
				if (dmg < 0)
					dmg = 0;
			}
		}
		else if (name.equals("Necronaut"))
		{
			if (weapon.getType() == Weapon.type.Bludg && weapon.getEnhancement() > 0)
			{
				
			}
			else
			{
				System.out.println("Your weapon is inneffective.");
				dmg -= 15;
				if (dmg < 0)
					dmg = 0;
			}
		}
		else if (name.equals("Shadow Centaur"))
		{
			if (weapon.getEnhancement() < 1)
			{
				System.out.println("Your weapon is inneffective.");
				dmg -= 5;
				if (dmg < 0)
					dmg = 0;
			}
		}
		else if (name.equals("Nightcrawler"))
		{
			if (player.getWeapon().getEnhancement() < 6)
			{
				System.out.println("Your weapon is inneffective.");
				dmg -= 15;
				if (dmg < 0)
					dmg = 0;
			}
		}
		else if (incorporeal)
		{
			if (ghostTouch)
			{
				
			}
			else if (weapon.getEnhancement() > 0)
			{
				int rng = roll.d100();
				if (rng <= 50)
				{
					dmg = 0;
				}
			}
			else
			{
				dmg = 0;
			}
		}
		if (type.equals("Undead") && weapon.getName().equals("Undead Bane Mace"))
		{
			System.out.println("The " + name + " crumbles slightly from the impact!");
			dmg += 2 + roll.XdY(2, 6);
		}
		else if (type.equals("Animal") && weapon.getName().equals("Animal Bane Warhammer"))
		{
			System.out.println("The " + name + " bleeds more than usual!");
			dmg += 2 + roll.XdY(2, 6);
		}
		HP -= dmg;
		System.out.println("Did " + dmg + " damage to the " + name + "!");
		if (HP < 0)
			HP = 0;
		if (type.equals("Undead") && weapon.getName().equals("Disrupting Warhammer"))
		{
			if (willSave() < 14)
			{
				System.out.println("The " + name + " crumbles to dust!");
				HP = 0;
			}
		}
	}
	public int getXPValue()
	{
		return XPValue;
	}
	public int getGPValue()
	{
		return GPValue;
	}
	public void changeAC(int value, boolean touch)
	{
		if (value > 0)
			AC += value;
		else
			AC -= Math.abs(value);
		if (touch)
		{
			touchAC += value;
		}
	}
	public void changeHP(int value)
	{
		if (value > 0)
		{
			HP += value;
		}
		else
		{
			HP -= Math.abs(value);
		}
	}
	public void lowerDamage(int value)
	{
		System.out.println("Weakness overcomes the " + name);
		damage -= value;
	}
	public void lowerHit(int value)
	{
		atkRoll -= value;
	}
	public int dexRoll()
	{
		return roll.d20() + ((dex - 10) / 2);
	}
	public void hitPenalty(int amount)
	{
		toHitPenalty += amount;
	}
	
	public String toString(String pName)
	{
		return "********************************\n\t" + name + " attacks "
				+ pName + "!\n\nHP: " + HP + "\nSuggested Level: "
				+ reqLevel + "\n********************************";
	}
	
	public void attackSelf(Player player)
	{
		System.out.println("The " + name + " attacks itself!");
		int attack = attack(player);
		System.out.println("The " + name + " did " + attack + " damage to itself!");
		HP -= attack;
	}
	
	public void lucienBuff()
	{
		damage += 4;
		atkRoll += 4;
		grappleRoll += 4;
	}
	public int resistDispel(int value)
	{
		return roll.d20() + value + (will / 4);
	}
	public int getDC()
	{
		return DC;
	}
	
	public void loot(Inventory inv, Player player)
	{
		
		if (name.equals("Mugger"))
		{
			int rng = roll.d100();
			int rng2 = roll.d100();
			if (rng >= 60)
			{
				inv.addItem("Shiv", 3);
			}
			if (rng2 >= 90)
			{
				inv.addItem("Potion", 50);
			}
		}
		else if (name.equals("Wolf"))
		{
			int rng1 = roll.d100();
			int rng2 = roll.d100();
			
			if (rng1 >= 60)
			{
				inv.addItem("Wolf pelt", rng1);
			}
			if (rng2 < 40)
			{
				inv.addItem("Wolf tooth", rng2);
			}
		}
		else if (name.equals("Bear"))
		{
			int rng1 = roll.d100();
			int rng2 = roll.d100();
			if (rng1 >= 60)
			{
				rng1 = rng1 * 2;
				inv.addItem("Bear pelt", rng1);
			}
			if (10 < rng2 && rng2 < 50)
			{
				rng2 = rng2 * 2;
				inv.addItem("Bear tooth", rng2);
			}
		}
		else if (name.equals("Todd"))
		{
			if (!Player.beatenTodd)
				Player.beatenTodd = true;
			
			int rng = roll.XdY(player.getLevel(), 100);
			if (rng < 50)
			{
				inv.addItem("Cracked jewel", rng);
			}
			else if (rng < 150)
			{
				inv.addItem("Gold necklace", rng);
			}
			else if (rng < 300)
			{
				inv.addItem("Jeweled silver crown", rng);
			}
			else if (rng < 500)
			{
				inv.addItem("Ruby scepter", rng);
			}
			else if (rng < 700)
			{
				inv.addItem("Platinum mug", rng);
			}
			else if (rng < 900)
			{
				inv.addItem("Jade ring", rng);
			}
			else if (rng < 1150)
			{
				inv.addItem("Black pearl", rng);
			}
			else if (rng < 1400)
			{
				inv.addItem("Book of burning", rng);
			}
			else
			{
				inv.addItem("Old masterpiece painting", rng);
			}
		}
		else if (name.equals("Witch"))
		{
			int rng = roll.dX(2000);
			int rng2 = roll.d100();
			int rng3 = roll.d100();
			if (rng >= 1500)
			{
				inv.addItem("Witches toe", rng);
			}
			if (rng2 >= 51)
			{
				inv.addItem("Quarterstaff +1", 2304);
			}
			
			if (rng3 <= 50)
			{
				inv.addItem("Mana Potion", 120);
			}
		}
		else if (name.equals("Small Viper Snake"))
		{
			int rng = roll.d100();
			if (rng >= 50)
			{
				inv.addItem("Small Snake Skin", (rng * 2));
			}
		}
		else if (name.equals("Large Viper Snake"))
		{
			int rng = roll.d100();
			if (rng >= 50)
			{
				inv.addItem("Large Snake Skin", (rng * 3));
			}
		}
		else if (name.equals("Huge Viper Snake"))
		{
			int rng = roll.d100();
			if (rng >= 50)
			{
				inv.addItem("Huge Snake Skin", (rng * 4));
			}
		}
		else if (name.equals("Huge Viper Snake"))
		{
			int rng = roll.d100();
			if (rng >= 50)
			{
				inv.addItem("Huge Snake Skin", (rng * 4));
			}
		}
		else if (name.equals("Yuan-Ti Abomination"))
		{
			int rng = roll.d100();
			if (rng >= 50)
			{
				inv.addItem("Yuan-Ti Abomination Skin", (rng * 8));
			}
		}
		else if (name.equals("Dark Lord"))
		{
			
			int rng1 = roll.d100();
			int rng2 = roll.d100();
			int rng3 = roll.d100();
			
			if (rng1 >= 50)
			{
				int rng = 0;
				for (int index = 0; index < 6; index++)
				{
					rng += roll.dX(2000);
				}
				
				if (rng < 500)
				{
					inv.addItem("Chipped Emerald", rng);
				}
				else if (rng < 3000)
				{
					inv.addItem("Emerald", rng);
				}
				else if (rng < 8000)
				{
					inv.addItem("Polished Emerald", rng);
				}
				else
				{
					inv.addItem("Pure Emerald", rng);
				}
			}
			
			if (rng2 >= 60)
			{
				inv.addItem("Mace +5", 20008);
			}
			
			if (rng3 >= 40)
			{
				inv.addItem("Ring of Protection +3", 18000);
			}
		}
		else if (name.equals("Mohrg"))
		{
			int rng;
			do
			{
				rng = roll.XdY(2, 900);
			}
			while (rng < 900);
			if (rng >= 1000)
			{
				inv.addItem("Mohrg Tissue", rng * 2);
			}
		}
		else if (name.equals("Mummy"))
		{
			int rng = roll.d100();
			if (rng >= 50)
			{
				do
				{
					rng = roll.XdY(10, 100);
				}
				while (rng < 600);
				inv.addItem("Mummy Wrap", rng);
			}
		}
		else if (name.equals("Boneclaw"))
		{
			int rng = roll.d100();
			if (rng >= 40)
			{
				do
				{
					rng = roll.XdY(20, 60);
				}
				while (rng < 500);
				inv.addItem("Boneclaw nail", rng);
			}
		}
		else if (name.equals("Dire Bear"))
		{
			int rng1 = roll.d100();
			int rng2 = roll.d100();
			
			if (rng1 < 50)
			{
				do
				{
					rng1 = roll.XdY(30, 50);
				}
				while (rng1 < 750);
				inv.addItem("Dire Bear pelt", rng1);
			}
			if (rng2 < 50)
			{
				do
				{
					rng2 = roll.XdY(20, 40);
				}
				while (rng2 < 400);
				inv.addItem("Dire Bear tooth", rng2);
			}
		}
		else if (name.equals("Gravecrawler"))
		{
			int rng = roll.d100();
			
			if (rng > 25)
			{
				rng = roll.XdY(25, 30) * 5;
				inv.addItem("Stone Skull", rng);
			}
		}
		else if (name.equals("Necronaut"))
		{
			int rng = roll.d100();
			
			if (rng > 40)
			{
				rng = roll.XdY(28, 60);
				inv.addItem("Necronaut claw", rng);
			}
		}
		else if (name.equals("Chraal"))
		{
			int rng = roll.d100();
			
			if (rng > 30)
			{
				rng = roll.XdY(29, 50);
				inv.addItem("Essence of Cold", rng);
			}
		}
		else if (name.contains("Shadow") || name.equals("Dusk Beast")
				|| name.equals("Dark Lion") || name.equals("Nightcrawler")
				|| name.equals("Nightwalker"))
		{
			int rng = roll.d100();
			
			if (rng <= 35)
			{
				rng = roll.XdY(20, 20);
				inv.addItem("Shadow Essence", rng);
			}
		}
		else if (name.equals("Necromancer"))
		{
			int rng = roll.d100();
			
			if (rng <= 60)
			{
				inv.addItem("Mana Potion", 120);
			}
			
			rng = roll.d100();
			
			if (rng <= 25)
			{
				inv.addItem("Greater Mana Potion", 500);
			}
		}
		else if (name.equals("Ripper"))
		{
			int rng = roll.d100();
			
			if (rng <= 40)
			{
				rng = roll.XdY(15, 20);
				inv.addItem("Ripper Claws", rng);
			}
		}
		else if (name.equals("Lich"))
		{
			int rng = roll.d100();
			
			if (rng <= 50)
			{
				rng = roll.XdY(80, 50);
				inv.addItem("Enchanted Cloth", rng);
			}
			
			rng = roll.d100();
			
			if (rng <= 75)
			{
				inv.addItem("Major Mana Potion", 800);
			}
			
			rng = roll.d100();
			
			if (rng < 33)
			{
				inv.addItem("Potion of Ironskin", 1400);
			}
		}
		else if (name.equals("Gladiator"))
		{
			int rng = roll.d100();
			String drop;
			int value;
			
			if (rng <= 50)
			{
				rng = roll.d100();
				
				if (rng <= 25)
				{
					drop = "Sword";
					value = 8;
					
					rng = roll.dX(3) - 1;
					
					if (rng > 0)
					{
						value += 300 + ((rng * rng) * 1000);
						
						drop += " +" + rng;
					}
				}
				else if (rng <= 50)
				{
					drop = "Warhammer";
					value = 9;
					
					rng = roll.dX(3) - 1;
					
					if (rng > 0)
					{
						value += 300 + ((rng * rng) * 1000);
						
						drop += " +" + rng;
					}
				}
				else if (rng <= 75)
				{
					drop = "Breastplate";
					value = 200;
					
					rng = roll.dX(5) - 1;
					
					if (rng > 0)
					{
						value += 150 + ((rng * rng) * 1000);
						
						drop += " +" + rng;
					}
				}
				else
				{
					drop = "Full plate";
					value = 1500;
					
					rng = roll.dX(5) - 1;
					
					if (rng > 0)
					{
						value += 150 + ((rng * rng) * 1000);
						
						drop += " +" + rng;
					}
				}
				
				inv.addItem(drop, value);
			}
		}
		else if (name.equals("Champion"))
		{
			inv.addItem("Breastplate +5", 25200);
			inv.addItem("Power Axe +1", 10309);
		}
	}
}
