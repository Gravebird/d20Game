import java.util.Random;

public class Dice {

	Random rng = new Random();
	
	public int d3()
	{
		return rng.nextInt(3) + 1;
	}
	public int d4()
	{
		return rng.nextInt(4) + 1;
	}
	public int d6()
	{
		return rng.nextInt(6) + 1;
	}
	public int d8()
	{
		return rng.nextInt(8) + 1;
	}
	public int d10()
	{
		return rng.nextInt(10) + 1;
	}
	public int d12()
	{
		return rng.nextInt(12) + 1;
	}
	public int d20()
	{
		int total =  rng.nextInt(20) + 1;
		if (total == 20)
			total = 30;
		else if (total == 1)
			total = -10;
		
		return total;
	}
	public int d100()
	{
		return rng.nextInt(100) + 1;
	}
	public int dX(int X)
	{
		return rng.nextInt(X) + 1;
	}
	
	public int XdY(int X, int Y)
	{
		int num = 0;
		for (int index = 0; index < X; index++)
		{
			num += dX(Y);
		}
		return num;
	}
	public int statRoll()
	{
		int die1 = rng.nextInt(6) + 1;
		int die2 = rng.nextInt(6) + 1;
		int die3 = rng.nextInt(6) + 1;
		int die4 = rng.nextInt(6) + 1;
		int total;
		
		if (die1 == 1)
		{
			die1 = 0;
		}
		else if (die2 == 1)
		{
			die2 = 0;
		}
		else if (die3 == 1)
		{
			die3 = 0;
		}
		else if (die4 == 1)
		{
			die4 = 0;
		}
		else if (die1 == 2)
		{
			die1 = 0;
		}
		else if (die2 == 2)
		{
			die2 = 0;
		}
		else if (die3 == 2)
		{
			die3 = 0;
		}
		else if (die4 == 2)
		{
			die4 = 0;
		}
		else if (die1 == 3)
		{
			die1 = 0;
		}
		else if (die2 == 3)
		{
			die2 = 0;
		}
		else if (die3 == 3)
		{
			die3 = 0;
		}
		else if (die4 == 3)
		{
			die4 = 0;
		}
		else if (die1 == 4)
		{
			die1 = 0;
		}
		else if (die2 == 4)
		{
			die2 = 0;
		}
		else if (die3 == 4)
		{
			die3 = 0;
		}
		else if (die4 == 4)
		{
			die4 = 0;
		}
		else if (die1 == 5)
		{
			die1 = 0;
		}
		else if (die2 == 5)
		{
			die2 = 0;
		}
		else if (die3 == 5)
		{
			die3 = 0;
		}
		else if (die4 == 5)
		{
			die4 = 0;
		}
		else
		{
			die1 = 0;
		}
		
		total = die1 + die2 + die3 + die4;
		return total;
	}
}
