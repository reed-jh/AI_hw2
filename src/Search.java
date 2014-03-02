/*
 * 
 * This class contains the search algorithms used to define a path
 * 
 */
import java.util.Arrays;


public class Search
{
	// Fields
	static int searchStrategy = 0; // Determines which search algorithm us used

	// Primary search method
	public static void perform(int strategy, Grid g)
	{
		// Set strategy
		searchStrategy = strategy;
		boolean goalNotFound = true;

		while (goalNotFound)
		{
			// Debugging
			//System.out.println(g.fringe.toString());
			System.out.println("(" + g.current.point[0] + ", " + g.current.point[1] + ")");
			System.out.println("SIZE OF FRINGE: " + g.fringe.size());
			System.out.println("Curr Eval: " + g.current.evaluation);

			// Pull from fringe to make current cell
			Cell temp = g.fringe.remove();
			// Mark as visited
			g.grid[temp.point[0]][temp.point[1]].isVisited = true;
			g.current = temp;

			// Add adjacent cells to fringe from 'current' cell
			goalNotFound = buildFringe(g); // if goal is found in fringe, we're done

			// Search failed
			if (g.fringe.isEmpty() && goalNotFound)
			{
				System.out.println("The search failed to find the goal.");
				break;
			}
		}

		// Fill in grid with path data for final printout
		g.buildPath();

	}

	// Adds cells to fringe from current cell
	private static boolean buildFringe(Grid g)
	{
		Cell curr = g.current;
		int[]currPoint = curr.point;
		boolean goalNotFound = true;

		// Check cell above
		goalNotFound = checkCell(g, currPoint, -1, 0);
		// Check cell below
		goalNotFound &= checkCell(g, currPoint, 1, 0);
		// Check cell to left
		goalNotFound &= checkCell(g, currPoint, 0, -1);
		// Check cell to right
		goalNotFound &= checkCell(g, currPoint, 0, 1);

		return goalNotFound;
	}

	// Check adjacent cells for clearness
	// Returns true if the cell is not the goal, false if it is
	private static boolean checkCell(Grid g, int[] currPoint, int xOffset, int yOffset)
	{
		boolean ret = true;

		Cell temp = g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset];
		
		if (temp.isGoal)
		{
			//debugging
			System.out.println("GOT HERE!");
			ret = false;
		}
		else if (temp.isClear && !temp.isVisited)
		{
			// Perform evaluation
			g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset].pathCost = (g.grid[currPoint[0]][currPoint[1]].pathCost + 1);
			double oldEval = g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset].evaluation;
			g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset].evaluation = evaluateCell(g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset], g.grid[g.goal[0]][g.goal[1]]);
			// Add cell
			if (!g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset].isInFringe){
				g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset].previousCell = currPoint;
				g.fringe.add(g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset]);
				g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset].isInFringe = true;
				
				// debugging
				System.out.println("ADDED TO FRINGE: (" + (currPoint[0] + xOffset) + ", " + (currPoint[1] + yOffset) + ")");
			}
			else // is already in fringe
			{
				// See if this new evaluation is cheaper (the new path is shorter)
				if (g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset].evaluation < oldEval)
				{
					// remove old from fringe and add new
					g.fringe.remove(currPoint);
					g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset].previousCell = currPoint;
					g.fringe.add(g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset]);
					g.grid[currPoint[0] + xOffset][currPoint[1] + yOffset].isInFringe = true;
				}
			}
		}
		
		return ret;
	}

	// Calculates Manhattan Distance between two cells
	private static int calculateManhattanDistance(Cell a, Cell b)
	{
		return Math.abs(a.point[0] - b.point[0]) + Math.abs(a.point[1] - b.point[1]);
	}

	private static double calculateEuclideanDistance(Cell a, Cell b)
	{
		return Math.sqrt( Math.pow( (a.point[0] - b.point[0]) , 2) + Math.pow( (a.point[1] - b.point[1]) , 2) );
	}

	// Computes evaluation of cells to be added to the fringe
	private static double evaluateCell(Cell n, Cell goal)
	{
		double ret = 0;

		switch (searchStrategy)
		{
		case 1:
			ret = strategy1(n, goal);
			break;
		case 2:
			ret = strategy2(n, goal);
			break;
		case 3:
			ret = strategy3(n, goal);
			break;
		case 4:
			ret = strategy4(n, goal);
		}

		return ret;
	}

	// Stategies for evaluation function

	// f(n) = euclidean distance to goal from n
	private static double strategy1(Cell c, Cell goal)
	{
		return calculateEuclideanDistance(c, goal);
	}

	// f(n) = manhattan distance to goal from n
	private static double strategy2(Cell c, Cell goal)
	{
		return calculateManhattanDistance(c, goal);
	}	

	// f(n) = g(n) + h(n)
	// g(n) = distance to node n from start
	// h(n) = euclidean distance to goal from n
	private static double strategy3(Cell c, Cell goal)
	{
		return c.pathCost + calculateEuclideanDistance(c, goal);
	}

	// f(n) = g(n) + h(n)
	// g(n) = distance to node n from start
	// h(n) = manhattan distance to goal from n
	private static double strategy4(Cell c, Cell goal)
	{
		return c.pathCost + calculateManhattanDistance(c, goal);
	}
}
