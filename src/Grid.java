/*
 * 
 * This class stores the grid of cells based of data imported from the input file
 * 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.PriorityQueue;


public class Grid 
{
	// Fields
	Cell[][] grid; // Dynamically allocated
	int size = 0;
	int[] start = new int[2]; // store location of start cell
	int[] goal = new int[2]; // store location of goal cell
	Cell current; // Most recently expanded cell in search
	PriorityQueue<Cell> fringe = new PriorityQueue<Cell>();

	// Methods

	// Takes file as input and produces the grid
	// Returns false if there was an error in the file
	boolean loadGrid(String fileName)
	{
		boolean loaded = true;
		String line = null;
		BufferedReader buff;
		try
		{
			// Set up for file reading
			buff = new BufferedReader(new FileReader(fileName));

			// Read fist line with size of grid
			if ((line = buff.readLine()) != null)
			{
				size = Integer.parseInt(line);
			}

			// Build empty grid
			// Grid has layer of 'blocked' padding around it, to implicitly limit navigation to the bounds of the grid
			grid = new Cell[size + 2][size + 2];

			// Read contents of file
			for (int i = 0; i < size + 2; ++i) {

				// Check for empty line
				if( (i != 0) && (i != size + 1) )
				{
					line = buff.readLine();
					if ( (line == null) && !(line.length() == size))
						throw new Exception("Error in file");
				}

				// Look at each char in line and assess cell value
				for (int j = 0; j < size + 2; ++j)
				{
					// Read value of cell from file if within valid bounds
					char cellValue = 0;
					if ((j != 0) && (i != 0) && (j < size + 1) && (i < size + 1))
					{
						cellValue = line.charAt(j - 1);
					}

					// Create cell
					grid[i][j] = new Cell();

					// Set x,y values for cell
					grid[i][j].point[0] = i;
					grid[i][j].point[1] = j;

					// Set values for each cell
					switch (cellValue)
					{
					case '.':
						break;
					case 'g':
						grid[i][j].isGoal = true;
						goal = grid[i][j].point;
						break;
					case 'i':
						grid[i][j].isStart = true;
						start = grid[i][j].point;
						current = grid[i][j];
						fringe.add(grid[i][j]);
						break;
					default: // space is an obstacle
						grid[i][j].isClear = false;
					}

				}

			}
		}
		catch(Exception e)
		{	
			e.printStackTrace();
			loaded = false;
		}
		return loaded;
	}

	// Builds path that has reached goal
	public void buildPath()
	{
		int[] currentPoint = current.point;
		// Loop through cells from goal to start, marking all in path as such
		while (!Arrays.equals(currentPoint, start))
		{
			grid[currentPoint[0]][currentPoint[1]].isPath = true;
			currentPoint = grid[currentPoint[0]][currentPoint[1]].previousCell;
			
			// Debugging
			//System.out.println(currentPoint[0] + " " + currentPoint[1] + "--> " + grid[currentPoint[0]][currentPoint[1]].pathCost +  " -> " + grid[currentPoint[0]][currentPoint[1]].evaluation);
		}
	}

	// Prints out grid
	void printGrid()
	{
		for (int i = 1; i < size + 1; ++i)
		{
			String line = "";
			for (int j = 1; j < size + 1; ++j)
			{
				// Add each cell to line
				line += grid[i][j].toString();
			}
			// Print line
			System.out.print(line + '\n');
		}
	}
	
	// Delete the path created by search
	// Clears other info
	void clearPath(){
		for (int i = 1; i < size + 1; ++i)
		{
			String line = "";
			for (int j = 1; j < size + 1; ++j)
			{
				grid[i][j].isPath = false;
				grid[i][j].isVisited = false;
				int[] temp = {0,0};
				grid[i][j].previousCell = temp;
				grid[i][j].pathCost = 0;
				grid[i][j].isInFringe = false;
				
			}
		}
		fringe = new PriorityQueue<Cell>();
		current = grid[start[0]][start[1]];
		fringe.add(current);
	}

}
