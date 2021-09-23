import java.util.ArrayList;
import java.util.Scanner;

public class Inventory {

	ArrayList<Item> inventory = new ArrayList<Item>();
	Scanner scan = new Scanner(System.in);
	int location;
	
	public void getInventory(int gold, String weapon, String accessory, Armor armor)
	{
		System.out.println("----------------------------------------");
		System.out.println("Inventory: ");
		for (int index = 0; index < inventory.size(); index++)
		{
			System.out.println("\t" + inventory.get(index).toString());
		}
		System.out.println();
		System.out.println("Weapon: " + weapon);
		System.out.println("Armor: " + armor.getName());
		System.out.println("Accessory: " + accessory);
		System.out.println("Gold: " + gold + "GP");
		System.out.println("----------------------------------------");
	}
	public void addItem(String name, int number, int cost)
	{
		Item item = new Item(name, number, cost);
		inventory.add(item);
	}
	public void addItem(Item newItem)
	{
		inventory.add(newItem);
	}
	public String equip(String weapon)
	{
		boolean match = false;
		int location = 0;
		for (int index = 0; index < inventory.size(); index++)
		{
			if (weapon.equalsIgnoreCase(inventory.get(index).getName()))
			{
				match = true;
				location = index;
			}
		}
		if (match == true)
		{
			return inventory.get(location).getName();
		}
		else
		{
			return "none";
		}
	}
	public String useItem(String item)
	{
		boolean match = false;
		location = 0;
		
		for (int index = 0; index < inventory.size(); index++)
		{
			if (item.equalsIgnoreCase(inventory.get(index).getName()))
			{
				match = true;
				location = index;
			}
		}
		if (match == true)
		{
			return inventory.get(location).getName();
		}
		else
		{
			return "none";
		}
	}
	public void remItem(String item)
	{
		if (inventory.size() >= 1)
		{
			inventory.get(location).useItem();
			if (inventory.get(location).getQuantity() < 1)
			{
				inventory.remove(location);
			}
		}
	}
	public void findAndRemove(String item)
	{
		int found = -1;
		for (int index = 0; index < inventory.size(); index++)
		{
			if (item.equalsIgnoreCase(inventory.get(index).getName()))
			{
				found = index;
			}
		}
		if (found == -1)
			System.out.println("Programmer error: " + item + " does not exist.");
		else
		{
			inventory.get(found).useItem();
			if (inventory.get(found).getQuantity() < 1)
			{
				inventory.remove(found);
			}
		}
	}
	public void questRemoval(String item, int amount)
	{
		for (int index = 0; index < inventory.size(); index++)
		{
			if (item.equalsIgnoreCase(inventory.get(index).getName()))
			{
				inventory.get(index).changeQuantity(-amount);
				if (inventory.get(index).getQuantity() <= 0)
				{
					inventory.remove(index);
				}
			}
		}
	}
	public void addItem(String item, int price)
	{
		boolean match = false;
		int location = 0;
		for (int index = 0; index < inventory.size(); index++)
		{
			if (item.equals(inventory.get(index).getName()))
			{
				match = true;
				location = index;
			}
		}
		if (match == true)
		{
			// Remove the "price" from add() to revert
			inventory.get(location).add(price);
		}
		else
		{
			Item ITEM = new Item(item, 1, price);
			inventory.add(ITEM);
		}
		System.out.println(item + " received!");
	}
	public void sellItem(Player player, String name)
	{
		int location = -1;
		int numSell = -1;
		String input;
		for (int index = 0; index < inventory.size(); index++)
		{
			if (name.equalsIgnoreCase(inventory.get(index).getName()))
			{
				location = index;
			}
		}
		if (location >= 0)
		{
			if (inventory.get(location).getQuantity() > 1)
			{
				do
				{
					System.out.print("How many would you like to sell? ");
					try
					{
						numSell = scan.nextInt();
						if (numSell > inventory.get(location).getQuantity())
						{
							System.out.println("You don't have that many.");
							numSell = -1;
							scan.nextLine();
						}
					}
					catch (java.util.InputMismatchException error)
					{
						System.out.println("Only integer values allowed.");
						numSell = -1;
						scan.nextLine();
					}
				}
				while (numSell == -1);
				scan.nextLine();
				int pay = 0;
				for (int index = 0; index < numSell; index++)
				{
					pay += inventory.get(location).getPrice() / 2;
				}
				do
				{
					System.out.print("The merchant will pay " + (
							pay) + "GP "
							+ "for them.\nDo you accept? (yes/no)");
					input = scan.nextLine();
					if (input.equalsIgnoreCase("yes") ||
							input.equalsIgnoreCase("no"))
					{
						
					}
					else
					{
						System.out.println("Invalid input");
						input = "";
					}
				}
				while (input.equals(""));
				
				if (input.equalsIgnoreCase("yes"))
				{
					player.changeGold(pay);
					inventory.get(location).changeQuantity(-numSell);
					if (inventory.get(location).getQuantity() < 1)
					{
						if (player.getWeapon().getName().equalsIgnoreCase(
								inventory.get(location).getName()))
						{
							player.setWeapon("unarmed", 0);
						}
						else if (player.getAccessory().equalsIgnoreCase(inventory.get(location).getName()))
						{
							player.setAccessory("none");
						}
						inventory.remove(inventory.get(location));
					}
				}
			}
			else
			{
				do
				{
					System.out.print("The merchant will pay " + (
							inventory.get(location).getPrice() / 2) + "GP "
							+ "for it.\nDo you accept? (yes/no)");
					input = scan.nextLine();
					if (input.equalsIgnoreCase("yes") ||
							input.equalsIgnoreCase("no"))
					{
						
					}
					else
					{
						System.out.println("Invalid input");
						input = "";
					}
				}
				while (input.equals(""));
				
				if (input.equalsIgnoreCase("yes"))
				{
					player.changeGold(inventory.get(location).getPrice() / 2);
					if (player.getWeapon().getName().equalsIgnoreCase(
							inventory.get(location).getName()))
					{
						player.setWeapon("unarmed", 0);
					}
					else if (player.getAccessory().equalsIgnoreCase(inventory.get(location).getName()))
					{
						player.setAccessory("none");
					}
					else if (player.getArmorName().equalsIgnoreCase(inventory.get(location).getName()))
					{
						player.setArmor("none");
					}
					inventory.remove(inventory.get(location));
				}
			}
		}
		else
		{
			System.out.println("That item does not exist.");
		}
	}
	public boolean hasItem(String itemName)
	{
		boolean match = false;
		for (int index = 0; index < inventory.size(); index++)
		{
			if (itemName.equalsIgnoreCase(inventory.get(index).getName()))
			{
				match = true;
			}
		}
		return match;
	}
	
	public void equipItem(Player player, boolean inBattle)
	{
		System.out.print("What will you equip? ");
		String equipment = scan.nextLine();
		String equipName = equip(equipment);
		
		// The following code is for weapons only
		String weaponName = equipName;
		int weaponEnhancement = 0;
		
		if (weaponName.substring(weaponName.length() - 2, weaponName.length() - 1).equals("+"))
		{
			int index = weaponName.indexOf('+');
			weaponEnhancement = Integer.parseInt("" + weaponName.charAt(index + 1));
			weaponName = weaponName.substring(0, weaponName.length() - 3);
		}
		// End of weapon-only code
		
		if (equipName.equals("none"))
		{
			System.out.println("That item does not exist.");
		}
		else if (Weapon.isAWeapon(equipName))
		{
			player.setWeapon(weaponName, weaponEnhancement);
		}
		else if (!inBattle)
		{
			if (equipName.equals("Amulet of Hate")
					|| equipName.substring(0, equipName.length() - 3).equals("Ring of Protection")
					|| equipName.equals("Crown of Thought")
					|| equipName.substring(0, equipName.length() - 3).equals("Cloak of Resistance")
					|| equipName.equals("Jade ring") || equipName.equals("Jeweled silver crown")
					|| equipName.equals("Gold necklace")
					|| equipName.substring(0, equipName.length() - 3).equals("Gauntlets of Strength")
					|| equipName.substring(0, equipName.length() - 3).equals("Gloves of Dexterity")
					|| equipName.substring(0, equipName.length() - 3).equals("Amulet of Health")
					|| equipName.substring(0, equipName.length() - 3).equals("Headband of Intellect")
					|| equipName.substring(0, equipName.length() - 3).equals("Periapt of Wisdom")
					|| equipName.substring(0, equipName.length() - 3).equals("Cloak of Charisma")
					|| equipName.equals("Necklace of Purity")
					|| equipName.equals("Brawlers Gloves")
					|| equipName.equals("Shadow Warding Charm")
					|| equipName.equals("Ring of Frost Resistance")
					|| equipName.equals("Lucky Amulet"))
			{
				player.setAccessory(equipName);
			}
			else if (Armor.isAnArmor(equipName))
			{
				player.setArmor(equipName);
			}
		}
		else if (inBattle)
		{
			System.out.println("You cannot equip that in battle!");
		}
		else
		{
			System.out.println("You can't equip a " + equipName);
		}
	}
}
