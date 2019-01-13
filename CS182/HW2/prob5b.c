/*

	Problem 5 part 2 of HW2 CS182

	Find the minimum pairwise difference of the compared elements of an array A

*/

#include <stdio.h>
#include <stdlib.h>

void main() {

	int A[10] = {32, 87, 65, 13, 54, 1, 18, 108, 23, 75};
//	int A[10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//	int A[10] = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};

	int minDiff = 2147483647; //ceiling for an integer
	int i, j;
	int n = 0;
	
	n = (sizeof(A)/4);

	for (i = 0; i < n; i++) {
		for (j = 0; j < n; j++) {

			if (i == j) {
				//do nothing, don't want to compare to itself
				printf("\ni is: %d\n\n", i); //used to check by hand
			}
			else {
				if (minDiff > abs(A[i] - A[j])) {
					minDiff = abs(A[i] - A[j]);
					
					printf("\nNEW MIN DIFF: %d\n\n", minDiff); //prints new min diff of array
				}
			}
		}
	}
}
