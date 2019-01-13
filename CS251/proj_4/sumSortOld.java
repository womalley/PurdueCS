/*
*
*       sumSort.java
*       Project 4
*       Last modified 11/06/17
*
*/


import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
import edu.princeton.cs.algs4.QuickX;
import java.io.*;
import java.util.*;
import java.util.Comparator;
//import java.util.AbstractList;
//import java.util.AbstractCollection;

public class sumSortOld {

/*        public static int index0;
        public static int index1;
	public static int sum;

        //cunstructor
        sumSort(int index0, int index1, int sum) {
                this.index0 = index0;
                this.index1 = index1;
		this.sum = sum;
        }
*/

        //Global variables
        public static int[] arr;
        public static int i_0 = 0;
        public static int i_1 = 1;
        public static int i_2 = i_0 + 1;
        public static int i_3 = i_2 + 1;
        public static int leftSum = 0; //holds the current left side sum
        public static int N = 0; //size of inputs
        public static int size = 0; //size of outputs
//	public static sumSort[] allSums = new sumSort[5000];
        private static List<sumSortOld> sets = new ArrayList<sumSortOld>();
	public static sumIndex[] allSums = new sumIndex[5000];

        /*
        *
        *       Main
        *       Reads in size and all values
        *
        */
        public static void main(String[] args) {

                int index = 0;

                N = StdIn.readInt(); //number of inputs
                arr = new int[N];

                //read in list of values
                for (index = 0; index < N; index++) {
                        arr[index] = StdIn.readInt();
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
			
		int index = 0;
		//finds all possible sums
		for (int i_0 = 0; i_0 <= N - 1; i_0++) {
			for (int i_1 = (i_0 + 1); i_1 <= N - 1; i_1++) {
				int sum = (arr[i_0] + arr[i_1]); //calculated sum
				allSums[index] = new sumIndex(i_0, i_1, sum); //store some in array
				index++;
			}
		}
                for (index = 0; index < 10; index++) {
       	                StdOut.println(allSums[index].sum);
			StdOut.println(allSums[index].index0);
			StdOut.println(allSums[index].index1);
               	}

		Integer[] temp = Arrays.copyOf(allSums, allSums.length, Integer[].class);


		//sorts the sum array using quickX (quick3WayBM)
		QuickX.sort(allSums, comparisons.compareObjects);
		//Arrays.sort(allSums);
		//Collections.sort(allSums, new Comparator<sumIndex>());

		for (index = 0; index < 10; index++) {
//                        StdOut.println(temp[index].sum);
//                        StdOut.println(temp[index].index0);
//                        StdOut.println(temp[index].index1);

		}

	}

	
	/*
	*
	*	compareObjects
	*	compares each object of allSums (index0, index1, sum)
	*
	*/
/*	public static Comparator<Integer[]> compareObjects = new Comparator<Integer[]>() {
		public int compare(Integer[] a, Integer[] b) {
			int i_0a = a[0];
			int i_0b = b[0];
			
			int i_1a = a[1];
			int i_1b = b[1];

                        int i_2a = a[2];
                        int i_2b = b[2];

                        int i_3a = a[3];
                        int i_3b = b[3];

			//sort
			if (i_0a != i_0b) {
				return (i_0b - i_0a);
			}
			else {
				if (i_1a != i_1b) {
					return (i_1b - i_1a);
				}
				else {
					if (i_2a != i_2b) {
						return (i_2b - i_2a);
					}
					else {
						if (i_3a != i_3b) {
							return (i_3b - i_3a);
						}
						else {
							return (0);
						}
					}
				}
			}
		}
	};
*/
}






