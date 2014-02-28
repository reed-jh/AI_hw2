import java.io.BufferedReader;
import java.io.FileReader;

/*
 * 
 * This class stores the grid of cells based of data imported from the input file
 * 
 */

public class Grid 
{
	// Fields
	Cell[][] grid; // Dynamically allocated
	int size = 0;
	int[] start = new int[2]; // store location of start cell
	int[] goal = new int[2]; // store location of goal cell


	// Methods

	// Takes file as input and produces the grid
	// Returns false if there was an error in the file
	boolean loadGrid(String fileName)
	{
		boolean loaded = true;
		try
		{
			// Set up for file reading
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			String line = null;
			
			// Read fist line with size of grid
			if ((line = buff.readLine()) != null)
			{
				size = Integer.parseInt(line);
			}
			
			// Read contents of file
			while ((line = buff.readLine()) != null) {

			}
		}
		catch(Exception e)
		{
			loaded = false;
			System.out.println(e);
		}
		return loaded;
	}

	// Prints out grid
	void printGrid()
	{
		for (int i = 0; i < size; ++i)
		{
			String line = "";
			for (int j = 0; j < size; ++j)
			{
				// Add each cell to line
				line += grid[i][j].toString();
			}
			// Print line
			System.out.print(line + '\n');
		}
	}

}
