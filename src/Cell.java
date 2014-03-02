/*
 * 
 * This class stores information provided within each Cell (which compose the grid)
 * 
 */

public class Cell implements Comparable<Cell>
{
	// Fields
	boolean isClear = true;
	int pathCost = 0; // stores the cost of the path to reach cell
	double evaluation = 0; // result of evaluation function
	int[] point = {0,0}; // x and y hold the location value of the cell
	int[] previousCell = {0,0}; // Points to the cell that was just before this cell in the path
	boolean isGoal = false; // used in toString() method to make sure goal is correctly output
	boolean isStart = false;// used in toString() method to make sure start is correctly output
	boolean isPath = false; // set at the end, when final path is built
	boolean isVisited = false; // Whether or not cell has been added to fringe
	boolean isInFringe = false;
	
	// Methods
	public String toString()
	{
		String ret = ".";
		if (isPath)
			ret = "o";
		else if (!isClear)
			ret = "+";
		else if (isGoal)
			ret = "g";
		else if (isStart)
			ret = "i";
		
		return ret;
	}

	// Determines position in priority queue
	@Override
	public int compareTo(Cell c) {
		int val = 0;
		if (evaluation - c.evaluation < 0.0)
		{
			val = -1;
		}
		else if (evaluation - c.evaluation > 0.0)
		{
			val = 1;
		}
		else
		{
			val = 0;
		}
		return val;
	}

}
