/*
 * 
 * This class stores information provided within each Cell (which compose the grid)
 * 
 */

public class Cell implements Comparable<Cell>
{
	// Fields
	boolean isClear = false;
	int pathCost = 0; // stores the cost of the path to reach cell
	int x; // x and y hold the location value of the cell
	int y;
	boolean isGoal = false; // used in toString() method to make sure goal is correctly output
	boolean isStart = false;// used in toString() method to make sure start is correctly output

	// Methods
	public String toString()
	{
		String ret = ".";
		if (!isClear)
			ret = "+";
		else if (isGoal)
			ret = "g";
		else if (isStart)
			ret = "i";
		return ret;
	}

	// Determine 
	@Override
	public int compareTo(Cell c) {
		int val = pathCost - c.pathCost;
		return val;
	}

}
