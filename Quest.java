
public class Quest {

	private String questName;
	private String questDetails;
	private String questID;
	private String questGiver;
	private int numRequired;
	private int numFinished;
	private int dayToComplete;
	private boolean complete;
	
	public static int churchQuestStage = 0;
	public static int forestQuestStage = 0;
	public static int shadowQuestStage = 0;
	public static boolean muggerQuestComplete = false;
	public static boolean ripperQuestComplete = false;
	
	public Quest(String name, String details, String id, int required, String giver)
	{
		questName = name;
		questDetails = details;
		numRequired = required;
		numFinished = 0;
		questID = id;
		complete = false;
		questGiver = giver;
	}
	public Quest(String name, String details, String id, int required, String giver, 
			int dayToComplete)
	{
		questName = name;
		questDetails = details;
		numRequired = required;
		numFinished = 0;
		questID = id;
		complete = false;
		questGiver = giver;
		this.dayToComplete = dayToComplete;
	}
	
	public String getQuest()
	{
		String output;
		if (questID.equals("none"))
		{
			output = "You have no quest.";
		}
		else
		{
			output = "------------------------------------------------------------\n"
					+ questName + ":\n\n" + questDetails + "\n\n\t" + numFinished +
					" / " + numRequired + "\n-----"
					+ "-------------------------------------------------------";
		}
		return output;
	}
	
	public String getID()
	{
		return questID;
	}
	
	public String getName()
	{
		return questName;
	}
	
	public String getGiver()
	{
		return questGiver;
	}
	
	public void setID(String newID)
	{
		questID = newID;
	}
	
	public boolean isComplete()
	{
		return complete;
	}
	
	public void oneDown()
	{
		if (numFinished != numRequired)
		{
			numFinished++;
			if (numFinished == numRequired)
			{
				questName += " (COMPLETE)";
				System.out.println("\nQuest Completed!\n");
				complete = true;
			}
		}
		
	}
	
	public int getTimeLeft()
	{
		int time = dayToComplete - Shops.dayCounter;
		
		if (time < 0)
		{
			time = 0;
		}
		
		return time;
	}
	
	public boolean timeOver()
	{
		boolean over = false;
		
		if (dayToComplete <= Shops.dayCounter)
		{
			over = true;
		}
		
		return over;
	}
}
