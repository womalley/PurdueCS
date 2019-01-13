/*
*
*	Comparisons
*	used in sumSort
*	allows generic (object) array to be compared
*
*/

import java.util.Comparator;

public class comparisons {

        /*
        *
        *       compareObjects
        *       compares each object of allSums (index0, index1, sum)
        *
        */
        public static Comparator<Integer[]> compareObjects = new Comparator<Integer[]>() {
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


}
