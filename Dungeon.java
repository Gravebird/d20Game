
import java.util.Scanner;

public abstract class Dungeon {
	
	Scanner scan = new Scanner(System.in);
	Dice roll = new Dice();
	
	public void template(Player player, Magic Magic, Inventory inv)
	{
		// Levels X - Y
		
		boolean done = false;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("------------------------------------");
		System.out.println("You have entered the BLANK.");
		System.out.println("Suggested Level: X - Y");
		System.out.println("------------------------------------");
		
		while (!done)
		{
			System.out.println("DESCRIBE THE ROOM");
			System.out.println("What will you do?");
			String command = scan.nextLine();
			
			if (command.equalsIgnoreCase("help"))
			{
				help();
			}
			else if (command.equalsIgnoreCase("leave"))
			{
				done = true;
			}
			else if (command.equalsIgnoreCase("look"))
			{
				System.out.println("DESCRIBE THE ROOM");
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
			else if (command.equalsIgnoreCase("go"))
			{
				
			}
		}
	}
	
	public void help()
	{
		System.out.println("go        ----- go somewhere");
		System.out.println("look      ----- look around");
		System.out.println("cast      ----- cast a spell (noncombat spells only");
		System.out.println("item      ----- use an item");
		System.out.println("stats     ----- see your stats");
		System.out.println("equip     ----- equip an item");
		System.out.println("inventory ----- see your inventory");
		System.out.println("spellbook ----- see your spellbook");
	}
	public void useSpell(Player player, Magic Magic)
	{
		Magic.getSpells(false);
		SpellEffects mgk = new SpellEffects();
		String spell;
		String spellChoice;
		System.out.println("What will you cast? ");
    	spellChoice = scan.nextLine();
    	spell = Magic.useSpell(spellChoice,
    			player.getMP());
    	if (spell.equals("Mend"))
    	{
    		player.changeHP(mgk.mend(player));
    	}
    	else if (spell.equals("Cure"))
    	{
    		player.changeHP(mgk.cure(player));
    	}
    	else if (spell.equals("Heal"))
    	{
    		player.changeHP(mgk.heal(player));
    	}
    	else
    	{
    		System.out.println("There would be no point.");
    		spell = null;
    	}
    	
    	if (spell != null)
    	{
    		player.changeMP(-Magic.getCost(spell));
    	}
	}
}
