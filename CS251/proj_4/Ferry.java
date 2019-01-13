/*
*
*	Ferry.java
*	Project 4
*	Last modified: 11/07/17
*
*/

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class Ferry {


	public static int N = 0; //number of input values
	public static int[] arr;
	public static HashMap<Integer, Integer> map = new HashMap<>();
	// key zero associated with left of ferry
	// key one associated with right of ferry	

        /*
        *
        *       Main
        *       Reads in size and all values
        *
        */
        public static void main(String[] args) {

                int index = 0;
		int carCount = 0;

                N = StdIn.readInt(); //number of inputs
                arr = new int[N];
		
		int length = Integer.valueOf(args[0]);

                //read in list of values
                for (index = 0; index < N; index++) {
                        arr[index] = StdIn.readInt();
                }

		//set values to zero
		map.put(0, 0);
		map.put(1, 0);

		//loop storage array and add to hashMap
		for (index = 0; index <= N - 1; index++) {
			int car1 = arr[index];
			int car2 = length; //set to length for edge cases, will be set to next index + 1 otherwise
			if (index != (N - 1)) {
				car2 = arr[index + 1];
			}
			int leftLength = map.get(0);
			int rightLength = map.get(1);

			if (((car1 + car2) + leftLength) == length) {
				map.put(0, (car1 + car2 + leftLength));
				carCount = carCount + 2;
				index++;
			}
			else if (((car1 + car2) + rightLength) == length) {
				map.put(1, (car1 + car2 + rightLength));
                                carCount = carCount + 2;
                                index++;
			}
			else if ((car1 + leftLength) <= length) {
				map.put(0, (car1 + leftLength));
				carCount++;
			}
			else if ((car1 + rightLength) <= length) {
				map.put(1, (car1 + rightLength));
                                carCount++;
			}
			else {
				break;
			}
		}

		StdOut.print(length + " " + carCount);
        }
}
