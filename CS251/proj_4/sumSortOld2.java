/*
*
*	sumSort.java
*	Project 4
*	Last modified 11/06/17
*
*/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
//import java.util.AbstractList;
//import java.util.AbstractCollection;

public class sumSortOld2 {

	public static int index0;
	public static int index1;
	public static int index2;
	public static int index3;

	//cunstructor
	sumSortOld2(int index0, int index1, int index2, int index3) {
		this.index0 = index0;
		this.index1 = index1;
		this.index2 = index2;
		this.index3 = index3;
	}

	//Global variables
	public static int[] arr;	
	public static int i_0 = 0; 
	public static int i_1 = 1; 
	public static int i_2 = i_0 + 1;
	public static int i_3 = i_2 + 1;
	public static int leftSum = 0; //holds the current left side sum
	public static int N = 0; //size of inputs
	public static int size = 0; //size of outputs
	private static List<sumSortOld2> sets = new ArrayList<sumSortOld2>();	


	/*
	*
	*	Main
	*	Reads in size and all values
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

		leftSum = (arr[i_0] + arr[i_1]);
		//call to equal sums
		sums();
//		printList();
	}
	
	
	/*
	*
	*	sums
	*	prints the equal sums based on the left sides indices and sum
	*
	*/
	public static boolean firstPass = true;
	public static void sums () {
		
//		boolean firstPass = true;
	
//		StdOut.println("i_0: " + i_0 + " i_1: " + i_1);
//		StdOut.println("LeftSum: " + leftSum);


		//sets next left side indice pair and sum for use below
		if (i_1 != N - 1) {
			if (firstPass == false) {
				i_1++;
			}
		}
		else {
			//reset i_1 to one more than i_0
			i_0++;
			i_1 = i_0 + 1;
		}

/*                //if i_2 equals i_1 iterate
                if (i_2 == i_1) {
                        i_2++;
                }

                //if i_3 equals i_2 iterate
                if (i_3 == i_2) {
                        i_3++;
                }
*/
                //StdOut.println("i_0: " + i_0 + " i_1: " + i_1);

                //set new left side sum
                leftSum = (arr[i_0] + arr[i_1]);

		//compare sums to leftSum
		for (i_2 = (i_0 + 1); i_2 < N - 1; i_2++) {
			for (i_3 = (i_2 + 1); i_3 < N; i_3++) {
				int rightSum = (arr[i_2] + arr[i_3]);
			
				if (i_1 == i_2 || i_1 == i_3) {
					//skip, indices cannot be duplicated
				}	
				//if sums equal and lexicographically correct, store
				else if (leftSum == rightSum) {
//					sumSort newSet = new sumSort(index0, index1, index2, index3);
//					newSet.index0 = i_0;
//                                        newSet.index1 = i_1;
//                                        newSet.index2 = i_2;
//                                        newSet.index3 = i_3;
//					sets.add(newSet);
			
//					StdOut.println(i_0 + " " + i_1 + " " + i_2 + " " + i_3);
					size++;
				}	
			}
		}

		//first pass over
		firstPass = false;		

//		if (size == 2362) {
//			System.exit(0);
//		}

		//recursive call till i_1 meets end of list
		//if (i_1 <= N - 1) {
		if (i_0 < N - 2) {
			//firstPass = true;
			sums();
		}
		else {
			StdOut.println(size);
		}
	}


	/*
	*
	*	printList	
	*	prints the indice list of sets that equal each other
	*
	*/
//	Iterator iterate = sets.iterator(); //used to iterate the arraylist (sets)
/*	public static void printList() {
		
		Iterator iterate = sets.iterator(); //used to iterate the arraylist (sets)

//		StdOut.println(size); //prints the size of outputs

		while(iterate.hasNext()) {
			sumSort sets = (sumSort)iterate.next();
//			StdOut.println(sets.index0 + " " + sets.index1 + " " + sets.index2 + " " + sets.index3);
		}	

	}
*/
}




