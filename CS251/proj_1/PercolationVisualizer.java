/*
*
*	Percolation Visualizer
*	last modified: 09-11-2017
*
*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;

public class PercolationVisualizer {
	
	//Global Variables
	public static int windowSize = 600; //specified size in notes
	public static int timeInterval = 400; //amount of time used to pause the program before checking the next cell

	
	/*
	*
	*	Main class for Percolation Visualizer
	*
	*/
	public static void main(String[] args) {
		
		int x;
		int y;
		int gridSize;
		
		//sets the canvas size in pixels	
                StdDraw.setCanvasSize(windowSize, windowSize);
	
		gridSize = StdIn.readInt(); //sets first number to the n by n grid size

		StdDraw.enableDoubleBuffering();
		Percolation percVisual = new Percolation(gridSize);
		
		StdDraw.show();
	        
		//loop for reading from standard input
                while (!StdIn.isEmpty()) {

               	        x = StdIn.readInt();
                       	y = StdIn.readInt();

                        //checks surrounding cells
			percVisual.open(x, y);			

			//draw the grid
			drawGrid(percVisual, gridSize);
			StdDraw.show();

			//pause
			StdDraw.pause(timeInterval);

	                }
	}


	/*
	*
	*	drawGrid
	*	scales the grid to the given grid size and fills the grid given input
	*
	*/
	public static void drawGrid(Percolation percVisual, int n) {

		//initialize variables for static use
		int x;
		int y;
	
		//set new scale and default color (x and y scaled as so below to create lines between cells)
		StdDraw.setXscale(-0.0001*n, 1.0001*n);
		StdDraw.setYscale(-0.0001*n, 1.0001*n);
		StdDraw.setPenColor(StdDraw.BLACK);

		//set square fill size (windowSize / n) for block size and / 2.0 for half point
		StdDraw.filledSquare(((windowSize / n) / 2.0), ((windowSize / n) / 2.0), ((windowSize / n) / 2.0));

		//Create blank grid
		for (x = n - 1; x >= 0; x--) { //scans top right to bottom left
			for (y = n - 1; y >= 0; y--) {
				
				//cell is not open
				if (!percVisual.isOpen(x, y)) {
					StdDraw.setPenColor(StdDraw.BLACK);
				}
				else {
					//cell is open
					if (percVisual.isOpen(x , y)) {
						StdDraw.setPenColor(StdDraw.WHITE);
					}
					//cell can be filled (connected to virtual top)
					if (percVisual.isFull(x, y)) {
						StdDraw.setPenColor(StdDraw.BLUE);
					}
				}
	
				//fill the square with the right color (values attached to x, y, and third input create a block slightly smaller than original square, for lines)
				StdDraw.filledSquare(x + 0.5, y + 0.5, 0.49);
			}
		}
	}
}
