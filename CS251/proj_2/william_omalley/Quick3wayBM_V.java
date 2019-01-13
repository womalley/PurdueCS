/*
*
*       Quick3wayBM_V
*       Proj_2
*       last modified: 09-28-2017
*       Quick3wayBM implements several sorting methods and picks the best sorting
*       method depending on what the value of "n" is. Prints each iteration of the
*	sorted array. (all elements printed after each sort)
*
*/

import java.lang.Comparable;
import edu.princeton.cs.algs4.StdIn;

public class Quick3wayBM_V {

        //Global and global constant variables
        public static final int median3Cutoff = 40;
        public static int p, i, j, q, indexP, indexQ;
        public static int key = 0;
        public static int flag = 0;
        public static int sorted = 0;

        public static void sort(Comparable[] a) {
                sort(a, 0, a.length - 1);
        }

        public static void sort (Comparable[] a, int lo, int hi) {

                int chosenSort = 0; //determines what sort is used and prints the needed statement
                int N = ((hi - lo) + 1); //number count
                //int midNum = ((N - 1) / 2);

                /*
                *
                *       Insertion sort
                *       implementation for size equal to or less than 8
                *
                */
                if ((hi - lo) < 8) { //cut off point, compute if 8 or less

                        //variables
                        int loIndex = 0; //set lo to loIndex for manipulation
                        int loVal = 0; //set loIndex to loVal for manipulation in inner loop
                        Comparable tmp = 0; //holds the value being swapped

                        //Insertion sort method
                        for (loIndex = lo; loIndex < hi; loIndex++) {
                                for (loVal = loIndex; loVal > lo; loVal--) {
                                        if (a[loVal].compareTo(a[loVal - 1]) < 0) {
                                                swapper(a, loVal, loVal - 1);
                                        }
                                        else {
                                                //no swap needed, return to outer loop
                                                break;
                                        }
                                }
                        }

                        //print statement for insertion values
//                      StdOut.println("(Insertion Sort, " + lo + ", " + hi + ")");
                        return;
                }

                /*
                *
                *       Median-of-three partitioning
                *
                */
                else {
//                      if ((hi - median3Cutoff) < 0) { //cut off point, compute if 40 or less
                        if (hi <= ((lo + 40) - 1)) {
                                int med = 0;
                                int medStep = ((N - 1) / 2);
                                chosenSort = 1;

                                //compare lo, hi, and median
                                med = median(a, lo, (lo + medStep), (lo + medStep + medStep));
                                swapper(a, lo, med);
                        }

                        /*
                        *
                        *
                        *       Tukey Ninther
                        *       calculates tukey by taking the median of all the medians of the three partitions
                        *
                        */
                        else {
                                int m0, m1, m2 = 0; //partitioned medians
                                int medianFinal = 0;
                                chosenSort = 2;

                                int step = ((N - 1) / 8);

                                //Partition Start
                                m0 = median(a, lo, (lo + step), (lo + (2 * step)));

                                //Partition Mid
                                m1 = median(a, (lo + (3 * step)), (lo + (4 * step)), (lo + (5 * step)));

                                //Partition Last
                                m2 = median(a, (lo + (6 * step)), (lo + (7 * step)), (lo + (8 * step)));


                                medianFinal = median(a, m0, m1, m2);
                                swapper(a, medianFinal, lo);
                        }
                }


                /*
                *
                *
                *       Quicksort using 3 way partitioning
                *
                *
                */

                //variables
                p = lo;
                i = lo;
                j = (hi + 1);
                q = (hi + 1);
                Comparable v = a[lo];

                //quick3wayBM sort
                while (true) {

                        while (a[++i].compareTo(v) < 0) {
                                //increment "i"
                        }

                        while (a[--j].compareTo(v) > 0) {
                                //decrement "j"
                        }

                        //special case
                        if (i == j) {
                                j--;
                                break;
                        }

                        if (i >= j) {
                                break;
                        }

                        //comparison swap
                        if (j > i) {
                                swapper(a , i, j);
                        }

                        if (a[i].compareTo(v) == 0) {
                                swapper(a, i, ++p);
                        }

                        if (a[j].compareTo(v) == 0) {
                                swapper(a, j, --q);
                        }
                }

                //print sort type
/*                if (chosenSort == 1) {
                        StdOut.print("(Median of 3, ");
                }
                else if(chosenSort == 2) {
                        StdOut.print("(Tukey Ninther, ");
                }

                StdOut.println(lo + ", " + v + ", " + p + ", " + i + ", " + j + ", " + q + ", " + hi + ")");
*/

                int k = lo;
                int w = hi;
                int index = 0;


                //swapping
                while (k <= p) {
                        swapper(a, k++, j--);
                }
                while (w >= q) {
                        swapper(a, w--, i++);
                }

                //for Quick3wayBM_V
                int indexer = 0;
                while (indexer < a.length) {
                        StdOut.print(a[indexer] + " ");
                        indexer++;
                }
                StdOut.print("\n");

                //recursively call for left and right side
                if (lo > j) {
                        //do nothing
                }
                else {
                        sort(a, lo, j);
                }

                if (i > hi) {
                        //do nothing
                }
                else {
                        sort(a, i, hi);
                }

        }


        /*
        *
        *       median
        *       used in tukey ninther, determines the median of each partitioning
        *
        */
        private static int median(Comparable[] a, int low, int median, int high) {

                int medianVal = 0;

/*              StdOut.println("low: " + a[low] + " med: " + a[median] + " high: " + a[high]); //value
                StdOut.println("low: " + low + " med: " + median + " high: " + high); //index
*/

                //case: small num, then two equal large numbers
                if ((a[median].compareTo(a[high]) == 0) && (a[median].compareTo(a[low]) > 0)) {
                        medianVal = median;
                }

                //case: lo equal to hi, both larger than median
                else if ((a[low].compareTo(a[high]) == 0) && (a[low].compareTo(a[median]) > 0)) {
                        medianVal = low;
                }

                //case: lo equal to median, both larger than high
                else if ((a[low].compareTo(a[median]) == 0) && (a[low].compareTo(a[high]) > 0)) {
                        medianVal = low;
                }

                //case: lo equal to median, both less than high
                else if ((a[low].compareTo(a[median]) == 0) && (a[median].compareTo(a[high]) < 0)) {
                        medianVal = median;
                }

                //case: lo equal to hi, both less than median
                else if ((a[low].compareTo(a[high]) == 0) && (a[high].compareTo(a[median]) < 0)) {
                        medianVal = high;
                }

                //case: median equal to hi, both less than low
                else if ((a[median].compareTo(a[high]) == 0) && (a[high].compareTo(a[low]) < 0)) {
                        medianVal = high;
                }

                //case: they are all equal to each other
                else if ((a[low].compareTo(a[median]) == 0) && (a[median].compareTo(a[high]) == 0)) {
                        medianVal = median;
                }

                //REGULAR CASES

                //low is smallest
                else if ((a[low].compareTo(a[median]) < 0) && (a[low].compareTo(a[high]) < 0)) {
                        if (a[median].compareTo(a[high]) < 0) {
                                medianVal = median;
                        }
                        else {
                                medianVal = high;
                        }
                }

                //median is smallest
                else if ((a[median].compareTo(a[low]) < 0) && (a[median].compareTo(a[high]) < 0)) {
                        if (a[low].compareTo(a[high]) < 0) {
                                medianVal = low;
                        }
                        else {
                                medianVal = high;
                        }
                }

                //high is smallest
                else if ((a[high].compareTo(a[low]) < 0) && (a[high].compareTo(a[median]) < 0)) {
                        if (a[low].compareTo(a[median]) < 0) {
                                medianVal = low;
                        }
                        else {
                                medianVal = median;
                        }
                }

//              StdOut.println("index: " + medianVal);

                return(medianVal);
        }


        /*
        *
        *       swapper
        *       swapping function that swaps two integers in the array
        *
        */
        private static void swapper(Comparable[] a, int i, int k) {

                Comparable swap = a[i];
                a[i] = a[k];
                a[k] = swap;
        }


        /*
        *
        *       MAIN METHOD
        *       Reads in the numbers from a text file and places them in an array
        *
        */
        public static void main(String[] args) {
                int elementCount = 0; //first number in text file contains the number of elements in the file
                int spot = 0; //spot in array

                elementCount = StdIn.readInt(); //sets first number as the total number of elements in the text file
                Comparable[] arr = new Comparable[elementCount]; //holds all of the numbers in the text file (minus the first number that is the element count)
                while (!StdIn.isEmpty()) { //keep reading in until end of text file
                        int input = StdIn.readInt();
                        arr[spot] = input;
                        spot++;
                }

                //original array read in
                int arrSpot = 0;
                while (arrSpot < arr.length-1) {
                        StdOut.print(arr[arrSpot] + " ");
                        arrSpot++;
                }
                StdOut.print("\n");


                //call method to sort array
                sort(arr);
        }
}

