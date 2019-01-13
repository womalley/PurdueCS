/*
*
*       sumSort.java
*       Project 4
*       Last modified 11/06/17
*
*/


import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class sumSort {

        public static int index0;
        public static int index1;
	public static int sum;

        //cunstructor
        sumSort(int index0, int index1, int sum) {
                this.index0 = index0;
                this.index1 = index1;
		this.sum = sum;
        }


        //Global variables
        public static int[] arr;
	public static int[] vals;
        public static int N = 0; //size of inputs
        public static int size = 0; //size of outputs
	//public static int setSize = (int)Math.floor(((N / 2.0) * (N)) - (N / 2.0));
	public static SumCompare[] allSums = new SumCompare[5000];

        /*
        *
        *       Main
        *       Reads in size and all values
        *
        */
        public static void main(String[] args) {

                int index = 0;

                N = StdIn.readInt(); //number of inputs
                vals = new int[N];

                //read in list of values
                for (index = 0; index <= N - 1; index++) {
                        vals[index] = StdIn.readInt();
                }

                //create and sort array for all sums
		sumArray();
			
	}

	/*
	*
	*	sumArray
	*	creates a list of all possible sums
	*	based on the input array	
	*
	*/
	public static void sumArray () {

		arr = new int[N];
		int step = 0;
		int i = 1;
		//offset values
	        for(step = 0; step < N - 1; step++) {
            		arr[step] = vals[i];
			i++;
        	}

		SumCompare[] allSums = new SumCompare[5000];

		int index = 0;
		//finds all possible sums and stores them in allSums
		for (i = 0; i <= N - 1; i++) {
			for (int k = (i + 1); k <= N - 1; k++) {
				int sum = (arr[i] + arr[k]); //calculated sum
				allSums[index] = new SumCompare(i, k, sum);
				index++;
			}
		}

		//sorts the sum array using quickX (quick3WayBM)
		QuickXEdit.sort(allSums, Comparators.comparison);

	}
}

