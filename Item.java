import java.util.Scanner;

public class Item {

	private String itemName;
	private int Quantity;
	private int price;
	
	public static final int maxItemNameLength = 30;
	
	static Scanner scan = new Scanner(System.in);
	static Dice roll = new Dice();
	
	public Item(String item, int quantity, int cost)
	{
		itemName = item;
		Quantity = quantity;
		price = cost;
	}
	public Item(int cost, String name)
	{
		itemName = name;
		price = cost;
	}
	public Item(Item newItem)
	{
		itemName = newItem.getName();
		Quantity = newItem.getQuantity();
		price = newItem.getPrice();
	}
	
	public String getName()
	{
		return itemName;
	}
	public String toString()
	{
		if (Quantity < 2)
			return itemName;
		else
			return itemName + " x" + Quantity;
	}
	public void useItem()
	{
		Quantity--;
	}
	public int getQuantity()
	{
		return Quantity;
	}
	public void changeQuantity(int num)
	{
		Quantity += num;
	}
	public void add(int value)
	{
		// Remove int value from add() to revert
		Quantity++;
		// Remove the next line to revert
		price = (price + value) / 2;
	}
	public int getPrice()
	{
		return price;
	}
	public String shopStock()
	{
		String output = "\t" + itemName;
		
		for (int i = itemName.length(); i < maxItemNameLength; i++)
		{
			output += " ";
		}
		
		output += "----- " + price + "GP";
		
		return output;
	}
	
	public static void useItem(Player player, Inventory inv, Magic magic)
	{
		inv.getInventory(player.getGold(), player.getWeapon().getFullName(), player.getAccessory(), player.getArmor());
		String itemChoice;
		System.out.println("What item will you use?");
    	itemChoice = scan.nextLine();
    	String item = inv.useItem(itemChoice);
    	boolean valid = true;
    	
    	if (item.equalsIgnoreCase("Potion"))
    	{
    		player.potion(1);
    	}
    	else if (item.equalsIgnoreCase("Greater Potion"))
    	{
    		player.potion(2);
    	}
    	else if (item.equalsIgnoreCase("Full Potion"))
    	{
    		player.fullPotion();
    	}
    	else if (item.equalsIgnoreCase("Mana Potion"))
    	{
    		player.manaPotion(1);
    	}
    	else if (item.equalsIgnoreCase("Greater Mana Potion"))
    	{
    		player.manaPotion(2);
    	}
    	else if (item.equalsIgnoreCase("Major Mana Potion"))
    	{
    		player.manaPotion(3);
    	}
    	else if (item.equalsIgnoreCase("Holy Water"))
    	{
    		GeneralEffects.holyWater(player);
    	}
    	else if (item.equalsIgnoreCase("Revitalising Potion"))
    	{
    		player.revitalisingPotion();
    	}
    	else if (item.equalsIgnoreCase("Mead"))
    	{
    		GeneralEffects.alcohol(player, 1);
    	}
    	else if (item.equalsIgnoreCase("Wine"))
    	{
    		GeneralEffects.alcohol(player, 2);
    	}
    	else if (item.equalsIgnoreCase("Dwarven Rum"))
    	{
    		GeneralEffects.alcohol(player, 3);
    	}
    	else if (item.equalsIgnoreCase("Orc Killer"))
    	{
    		GeneralEffects.alcohol(player, 4);
    	}
    	else if (item.equalsIgnoreCase("Dwarf Killer"))
    	{
    		GeneralEffects.alcohol(player, 5);
    	}
    	else if (item.equalsIgnoreCase("Tome of Knowledge"))
    	{
    		player.chooseFeat(magic, false, false);
    	}
    	else if (item.equalsIgnoreCase("Potion of Enlightenment"))
    	{
    		GeneralEffects.statIncreaseByOne(player);
    	}
    	else if (item.equalsIgnoreCase("Spell Tome"))
    	{
    		magic.rngSpell(2);
    	}
    	else if (item.equalsIgnoreCase("Wizards Spellbook"))
    	{
    		magic.rngSpell(8);
    		magic.rngSpell(4);
    	}
    	else if (item.equalsIgnoreCase("Spell Scroll"))
    	{
    		magic.rngSpell(1);
    	}
    	else if (item.equalsIgnoreCase("Book of burning"))
    	{
    		System.out.println("A ball of fire shoots at you when you open it!");
    		if (player.savingThrow(Player.Saves.Reflex, true, false) < 13)
    		{
    			System.out.println(player.getName() + " dodged the ball of fire!");
    		}
    		else
    		{
    			System.out.println("The ball of fire hit " + player.getName() + "!");
    			int dmg = roll.XdY(2, 6);
    			player.elementDamage(dmg, "fire");
    		}
    		valid = false;
    	}
    	else if (item.equalsIgnoreCase("Rod of Lightning"))
    	{
    		System.out.println("A bolt of lightning fires in the direction you point the rod!");
    		
    		valid = false;
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
    			valid = false;
    		}
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
    			valid = false;
    		}
    	}
    	else
    	{
    		System.out.println("That item is " +
    				"unavailable.");
    		valid = false;
    	}
    	
    	if (valid)
    	{
    		inv.remItem(itemChoice);
    	}
	}
	
}
