/*
	PercolationSlow
	Proj_1
	Last modified date: 09-06-2017
*/

import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.StdIn;

public class PercolationSlow {

	//Global Variable
	int[][] grid;
	int gridTop;
	int gridBase;
	int gridSize; //contains the size of n (used for clarity, n is a poor variable name)
	QuickFindUF uf;


	/*
	*
	*	Create a new n by n grid where all cells are initially blocked
	*
	*/
	public PercolationSlow(int n) {
		
		//error check
		if (n < 1) {
			System.out.println("ERROR: n cannot be less than 1!");
		}
		
		//initializing
		grid = new int[n][n];
		gridSize = n;
		gridTop = ((gridSize * gridSize) + 1);
		gridBase = (gridSize * gridSize);
		uf = new QuickFindUF((gridSize * gridSize) + 2);
	}	
	

	/*
	*
	*	Open the site at coordinate (x, y), where x represents the horizontal axis and y the vertical one.
	*	For consistency purposes, (0, 0) will be the bottom-left cell of the grid and (n-1, n-1) will be
	*	on the top-right.
	*
	*/
	public void open(int x, int y) {

		int cellSpot = ((x * gridSize) + y);

		//makes the specified cell in the grid open
		grid[x][y] = 1;

		//checks if first cell of top or bottom of the grid (special cases)
		if ((x == 0) || (x == (gridSize - 1))) {
			connectTopBase(x, y);
                        System.out.println("VIRTUAL CHECK");
		}		

		//checks if top cell is open
		if (isOpen(x + 1, y)) {
			uf.union(cellSpot, (((x + 1) * gridSize) + y));
                        System.out.println("TOP CHECK");	
		}
		
		//checks if below cell is open
		if (isOpen(x - 1, y)) {
			uf.union(cellSpot, (((x - 1) * gridSize) + y));
                        System.out.println("BELOW CHECK");
		}

		//checks if left cell is open
		if (isOpen(x, y - 1)) {
			uf.union(cellSpot, ((x * gridSize) + (y - 1)));
                        System.out.println("LEFT CHECK");
		}

		//checks if right cell is open
		if (isOpen(x, y + 1)) {
			uf.union(cellSpot, ((x * gridSize) + (y + 1)));
                        System.out.println("RIGHT CHECK");
		}
		System.out.println("");
	}


	/*
	*
	*	Returns true if cell (x, y) is open due to a previous call to open(int x, int y).
	*
	*/
	public boolean isOpen(int x, int y) {
		
		//bound check to ensure cell is within the correct bounds
		if (boundChecker(x, y) == false) {
                        System.out.println("OUT OF BOUNDS");	
			return (false);
		}		

		//returns if specified cell is closed or open
		if (grid[x][y] != 1) {
                        System.out.println("CELL CLOSED");
			return (false); //cell closed
		}
		else {
                        System.out.println("CELL OPEN");
			return (true); //cell open
		}
	}


	/*
	*
	*	Returns true if there is a path from cell (x, y) to the surface.
	*
	*/
	public boolean isFull(int x, int y) {

		int spotNum = ((gridSize * (--x)) + y); //obtains spot where the cell is located

		//bound check to ensure cell is within the correct bounds
		boundChecker(x, y); //MIGHT NOT NEED, CALLED BY isOpen BELOW. PUT IN ELSE STATEMENT BELOW

		//can be full because cell is open
		if (isOpen(x, y) == true) {
			return (uf.connected(spotNum, gridTop));
		}
		//cannot be full because cell is closed
		else {
			return (false);
		}

	}

	
	/*
	*
	*	Analyzes the entire grid and returns true if the whole system percolates.
	*
	*/
	public boolean percolates() {
		
		//returns true if connected, false if not connected
		return (uf.connected(gridTop, gridBase));
	}


	/*
	*
	*	Main method that reads a description of a grid from standard input (using StdIn.java)
	*	and validates if the system described percolates or not, printing to standard output
	*	a simple "Yes" or "No" answer (using StdOut.java).
	*
	*/
	public static void main(String[] args) {

		//variables
		int x;
		int y;
		int n = StdIn.readInt();
		PercolationSlow percSlow = new PercolationSlow(n);

		//loop for reading from standard input
		while (!StdIn.isEmpty()) {
			
			x = StdIn.readInt();
			y = StdIn.readInt();
			
			//checks surrounding cells
			percSlow.open(x,y);
		}

		//prints to terminal whether the grid percolates or not
		if (percSlow.percolates() == true) {
			System.out.println("Yes");
		}
		else {
			System.out.println("No");
		}
	}
	
	
	//ADDED FUNCTIONS BELOW
	/*
	*
	*	checks to see if cell is out of bounds. If so, the function will throw an Index Out Of Bounds Error.
	*
	*/
	public boolean boundChecker(int x, int y) {
	
                if ((x >= 0) && (y >= 0) && (x < (gridSize)) && (y < (gridSize))) {
                	//in bounds, return true
			return (true);       
                }		
		else {
			//out of bounds, return false
			return (false);	
		}
	}

	/*
	*
	*	sets the top column as gridBase and
	*	sets the bottom column as the gridTop.
	*
	*/
	public void connectTopBase(int x, int y) {

/*		//USES OPPOSING WALLS FROM WORKING CODE BEING USED BELOW THIS IF ELSE IF STATEMENT
		//connects the cells in the first column
		if (x == 0) {
			uf.union((y), gridBase);
                        System.out.println("PART OF VIRT BASE");
		}
		//connects the cells in the last column
		else if (x == (gridSize - 1)) {
			uf.union(((x * gridSize) + y), gridTop);
                        System.out.println("PART OF VIRT TOP");
		}
*/

                //connects the cells in the first column
                if (y == 0) {
                        uf.union((x + 1), gridBase);
                        System.out.println("PART OF VIRT BASE");
                }
                //connects the cells in the last column
                else if (y == (gridSize - 1)) {
                        uf.union(((x * gridSize) + y + 1), gridTop);
                        System.out.println("PART OF VIRT TOP");
                }


	}
}
