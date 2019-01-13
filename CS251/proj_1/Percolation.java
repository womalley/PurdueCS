	
/*
*
*	Percolation
*       Proj_1
*       last modified: 09-11-2017
*	Percolation handles the fast method implemented (WeigtedQuickUnionUF)
*
*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {

        //Global Variable
        int[][] grid; //2D array that holds the x and y values for the cell positions
        int gridTop; //a virtual row for the top bound (where the water originates)
        int gridBase; //a virtual row for the lower bound
        int gridSize; //contains the size of n (used for clarity, n is a poor variable name)
        WeightedQuickUnionUF uf; //WeigtedQuickUnionUF object


        /*
        *
        *       Create a new n by n grid where all cells are initially blocked
        *
        */
        public Percolation(int n) {

                //error check
                if (n < 1) {
                        System.out.println("ERROR: n cannot be less than 1!");
                }

                //initializing
                grid = new int[n][n];
                gridSize = n; //size of the grid dimensions
                gridTop = ((gridSize * gridSize) + 0); //virtual top value (must be outside of the max cell value)
                gridBase = ((gridSize * gridSize) + 1); //virtual base value (must be outside of the max cell value and not the same as gridTop)
                uf = new WeightedQuickUnionUF((gridSize * gridSize) + 2); //cannot have the same value as any of the cell indices
        }


        /*
        *
        *       Open the site at coordinate (x, y), where x represents the horizontal axis and y the vertical one.
        *       For consistency purposes, (0, 0) will be the bottom-left cell of the grid and (n-1, n-1) will be
        *       on the top-right.
        *
        */
        public void open(int x, int y) {

		//cell position on the grid
                int cellSpot = ((x * gridSize) + y);

                //makes the specified cell in the grid open
                grid[x][y] = 1;


                //checks if first cell of top or bottom of the grid (special cases)
                if ((y == 0) || (y == (gridSize - 1))) {
                        connectTopBase(x, y);
                }

                //checks if top cell is open
                if (isOpen(x + 1, y)) {
                        uf.union(cellSpot, (((x + 1) * gridSize) + y));
                }

                //checks if below cell is open
                if (isOpen(x - 1, y)) {
                        uf.union(cellSpot, (((x - 1) * gridSize) + y));
                }

                //checks if left cell is open
                if (isOpen(x, y - 1)) {
                        uf.union(cellSpot, ((x * gridSize) + (y - 1)));
                }

                //checks if right cell is open
                if (isOpen(x, y + 1)) {
                        uf.union(cellSpot, ((x * gridSize) + (y + 1)));
                }
        }


        /*
        *
        *       Returns true if cell (x, y) is open due to a previous call to open(int x, int y).
        *
        */
        public boolean isOpen(int x, int y) {

                //bound check to ensure cell is within the correct bounds
                if (boundChecker(x, y) == false) {
                        return (false);
                }

                //returns if specified cell is closed or open
                if (grid[x][y] != 1) {
                        return (false); //cell closed
                }
                else {
                        return (true); //cell open
                }
        }


        /*
        *
        *       Returns true if there is a path from cell (x, y) to the surface.
        *
        */
        public boolean isFull(int x, int y) {

                int spotNum = ((gridSize * (x)) + y); //obtains spot where the cell is located
		
		//returns true if connected, false if not connected
                return (uf.connected(spotNum, gridTop));
        }


        /*
        *
        *       Analyzes the entire grid and returns true if the whole system percolates.
        *
        */
        public boolean percolates() {

                //returns true if connected, false if not connected
                return (uf.connected(gridTop, gridBase));
        }


        /*
        *
        *       Main method that reads a description of a grid from standard input (using StdIn.java)
        *       and validates if the system described percolates or not, printing to standard output
        *       a simple "Yes" or "No" answer (using StdOut.java).
        *
        */
        public static void main(String[] args) {

                //variables
                int x;
                int y;
                int n = StdIn.readInt();
                Percolation percQuick = new Percolation(n);

                //loop for reading from standard input
                while (!StdIn.isEmpty()) {

                        x = StdIn.readInt();
                        y = StdIn.readInt();

                        //checks surrounding cells
                        percQuick.open(x,y);
                }

                //prints to terminal whether the grid percolates or not
                if (percQuick.percolates() == true) {
                        System.out.println("Yes");
                }
                else {
                        System.out.println("No");
                }
        }


        //ADDED FUNCTIONS BELOW
        /*
        *
        *       checks to see if cell is out of bounds. If so, the function will throw an Index Out Of Bounds Error.
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
        *       sets the top column as gridBase and
        *       sets the bottom column as the gridTop.
        *
        */
        public void connectTopBase(int x, int y) {

                //connects the cells in the first column
                if (y == 0) {
                        uf.union(x * gridSize + y, gridBase);
                }

                //connects the cells in the last column
                else if (y == (gridSize - 1)) {
                        uf.union((x * gridSize) + y, gridTop);
                }
       }
}

