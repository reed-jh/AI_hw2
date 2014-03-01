/*
 * 
 * This is the User Interface for the program. This file also contains the main method
 * 
 */
import java.util.Scanner;


public class UI 
{
	// Fields
	static Grid g = null; // Grid, loaded from file

	// Main method
	public static void main(String[] args)
	{
		// Check to make sure arguments are good
		if (args.length != 1)
		{	
			// Error in command line argument
			System.out.println("There was an error in your arguments.");
			System.out.println("Please include one argument.");
		}
		else
		{
			try
			{
				// Load file contents into grid
				g = new Grid();
				if(!g.loadGrid(args[0]))
				{
					// If error exists in file
					throw new Exception("Error in file.");
				}
				
				
				
				// Primary loop
				while (true)
				{
					// Give user options for grid
					int strategy = promptWithStrategies();
					// Check for exit condition
					if (strategy == 5){
						break;
					}

					// Execute search
					Search.perform(strategy, g);
					
					// Print results
					g.printGrid();

					// Ask user if they want to continue
					if(!promptContinue())
						break;
					
					// Clear for new search
					g.clearPath();
				}
			}
			catch(Exception e)
			{
				System.out.println("Main loop");
				e.printStackTrace();
			}
		}
	}

	private static int promptWithStrategies(){
		int pick = 0;
		Scanner scanner = new Scanner(System.in);

		// Loop until user has given valid input
		while(pick < 1 || pick > 5)
		{
			try 
			{
				g.printGrid();
				System.out.println('\n' + "Please choose a search option 1-4 and press ENTER:");
				System.out.println("1. f(n) = Euclidean Distance");
				System.out.println("2. f(n) = Manhattan Distance");
				System.out.println("3. f(n) = g(n) + h(n) where h(n) = Euclidean Distance");
				System.out.println("4. f(n) = g(n) + h(n) where h(n) = Manhattan Distance");
				System.out.println("5. Exit");

				// Read in input
				String inputStr = scanner.nextLine();

				// Convert to int
				pick = Integer.parseInt(inputStr);

			} 
			catch (Exception e) 
			{
				// Error message for bad inputs
				System.out.println("There was an error in your input. Please try again." + '\n');
			}
		}
		return pick;
	}

	// Ask user if they want to continue
	private static boolean promptContinue()
	{
		boolean ret = true; // return value
		try 
		{
			Scanner scanner = new Scanner(System.in);
			String answer = ""; // string given as yes/no answer by user

			//Display question
			System.out.println("Would you like to search this grid again?");
			
			// Loop until answer is valid
			while (!(answer.toLowerCase()).equals("y") && !(answer.toLowerCase()).equals("n"))
			{
				// Give answer options
				System.out.println("(Answer: 'y' or 'n')");
				// Read in input
				answer = scanner.nextLine();
			}
			
			// Change to false if answered No. Default is true, so we don't need to set ret to true for Yes.
			if ((answer.toLowerCase()).equals("n"))
				ret = false;

		} 
		catch (Exception e) {
			// Print any error messages
			System.out.println(e);
		}
		// Return true if user wants to continue, else if they don't
		return ret;
	}
}
