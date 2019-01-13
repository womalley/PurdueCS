/*
*
*	PercolationStats
*	last modified: 09-09-2017
*
*/

import edu.princeton.cs.algs4.StdStats; 
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

	//global variables
	int[] opens; //holds number of opens for each repetition
	double[] pStar; //holds the p* estimate, (opened cells) / (N * N) for each repetition
	double[] repTime; //holds run times for each repetition	
	PercolationQuick percStatsQuick;
	
	public static void main(String[] args) {
		
		//declaring variables
		int N; //N by N is the grid size
		int T; //the number of repetitions of the previous process for the grid
		String type; //holds either slow or fast, which determines what method to use
		int openCount; //holds the number of opened cells
		int x;
		int y;	
		int repCount; //repetition counter
		double mean; //the mean of pStar
		double stdDev; //standard deviation
		double totalTime; //total time all repetitions take
		double meanTime; //the mean of the total time
		double stdDevTime; //standard deviation of the time
		double pStarVal; //the pStar calculated value (opened cells) / (N * )

		//initializing variables
		N = Integer.parseInt(args[0]);
		T = Integer.parseInt(args[1]);
		type = args[2];
		repCount = 0; //repetition counter
		openCount = 0; //opened cells counter
		totalTime = 0; //holds total run time
		
		PercolationStats percStats = new PercolationStats();

                percStats.pStar = new double[T];
                percStats.opens = new int[T];
                percStats.repTime = new double[T];

		//throw error if N or T is not a positive number
		if (N < 1) {
			System.out.println("ERROR: N must be a positive number!");
		}
		
		if (T < 1) {
			System.out.println("Error: T must be a positive number!");
		}
		
		//throw error if type is not slow or fast
		if (!(type.equals("slow"))) {
			if (!(type.equals("fast"))) {
				System.out.println("ERROR: type must be either slow or fast!");
			}
		}
		
		/*
		*
		*	PercolationSlow (QuickFindUF)
		*
		*/
		if (type.equals("slow")) {
			
			int index = 0;
			for (index = 0; index <= T - 1; index++) {
				
		                PercolationSlow percStatsSlow = new PercolationSlow(N);

				//reset count				
				openCount = 0;
				
				//reset clock timer (initialize)
				Stopwatch timer = new Stopwatch();

				//open cells until percolation
				while(!percStatsSlow.percolates()) {
				
					x = StdRandom.uniform(N);
					y = StdRandom.uniform(N);

					//if the cell isnt open or full, open it
					if (!percStatsSlow.isOpen(x, y)) {
						percStatsSlow.open(x, y);
	
						//record number of open cells
						openCount++;
					}
				}

                                //record repetition time
                                percStats.repTime[index] = timer.elapsedTime();
                                totalTime += timer.elapsedTime();

				//compute pStar for each repetition
				pStarVal = ((double)(openCount) / (double)(N * N));
				percStats.pStar[index] = pStarVal; //places pStarVal into array for later use

			}	
		
			
			//computes mean threshold
			mean = StdStats.mean(percStats.pStar);
			System.out.println("mean threshold=" + mean);

			//computes std dev
			stdDev = StdStats.stddev(percStats.pStar);
			System.out.println("std dev=" + stdDev);

			//computes total time
			System.out.println("time=" + totalTime);
			
			//computes mean time (per repetition)
			meanTime = StdStats.mean(percStats.repTime);
			System.out.println("mean time=" + meanTime);

			//computes std dev time
			stdDevTime = StdStats.stddev(percStats.repTime);
			System.out.println("stddev time=" + stdDevTime);


		}


		/*
		*
		*	Percolation (WeightedQuickUnionUF) (fast version)
		*
		*/
		if (type.equals("fast")) {

			int index;
                        for (index = 0; index <= T - 1; index++) {
				
				Percolation percStatsQuick = new Percolation(N);

                                //reset count
                                openCount = 0;

                                //reset clock timer (initialize)
                                Stopwatch timer = new Stopwatch();

                                //open cells until percolation
                                while(!percStatsQuick.percolates()) {

                                        x = StdRandom.uniform(N);
                                        y = StdRandom.uniform(N);

                                        //if the cell isnt open or full, open it
                                        if (!percStatsQuick.isOpen(x, y)) {
                                                percStatsQuick.open(x, y);

                                                //record number of open cells
                                                openCount++;
                                        }
                                }

                                //record repetition time
                                percStats.repTime[index] = timer.elapsedTime();
                                totalTime += timer.elapsedTime();

                                //compute pStar for each repetition
                                pStarVal = ((double)(openCount) / (double)(N * N));
                                percStats.pStar[index] = pStarVal; //stores pStarVal for computation below
                        }


                        //computes mean threshold
                        mean = StdStats.mean(percStats.pStar);
                        System.out.println("mean threshold=" + mean);

                        //computes std dev
                        stdDev = StdStats.stddev(percStats.pStar);
                        System.out.println("std dev=" + stdDev);

                        //computes total time
                        System.out.println("time=" + totalTime);
                        
			//computes mean time (per repetition)
                        meanTime = StdStats.mean(percStats.repTime);
                        System.out.println("mean time=" + meanTime);

                        //computes std dev time
                        stdDevTime = StdStats.stddev(percStats.repTime);
                        System.out.println("stddev time=" + stdDevTime);
		}
	}
}
