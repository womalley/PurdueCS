/*
	Problem #5 part 1 CS182 
	
	Take array A of size n integers and split them into two sets (s1, s2)
	such that the difference is the maximum.


*/

#include <stdio.h>

void main() {
	
	int A[10] = {3, 8, 5, 6, 1, 4, 9, 7, 2, 10}; //array A with test numbers
	int n = (sizeof(A)/4); //number of elements in array A
	int swap; //for bubble sort swap
	int i;
	int s1[n/2]; //set 1
	int s2[n/2]; //set 2

	printf("\nn variables: %d\n\n", n); //test to show how many elements in array A

	//Sorting method for array A (bubble sort)
	for (i = 0; i < (n - 1); i++) {
		for (int k = 0; k < n - i - 1; k++) {
			
			if (A[k] > A[k + 1]) {
				
				swap = A[k];
				A[k] = A[k + 1];
				A[k + 1] = swap;
			}
		}
	}
	
	//Print sorted array in ascending order as a check
	printf("Sorted array A in ascending order:\n");

	for (i = 0; i < n; i++) {
		printf("%d  ", A[i]);
	}
	printf("\n\n"); //spacing

	//Place into sets
	for (i = 0; i < (n/2); i++) {
		
		s1[i] = A[i];
		s2[i] = A[(n/2)+i];
	}

	//check sets print s1
	printf("SET 1: ");
	for (i = 0; i < (n/2); i++) {
		printf("%d ", s1[i]);
	}
	
	//check sets print s2
	printf("\n\nSET 2: ");
	for (i = 0; i < (n/2); i++) {
		printf("%d ", s2[i]);
	}
	printf("\n\n");

	//Get sum of s1, s2 and find the difference (will be maximum)
	int sumS1 = 0;
	int sumS2 = 0;
	int diff = 0;

	for (int i = 0; i < (n / 2); i++) {
		sumS1 = sumS1 + s1[i];
		sumS2 = sumS2 + s2[i];
	}		
	diff = sumS2 - sumS1;	

	//Print difference in set 1 and set 2
	printf("The maximum difference of sumS2: %d, and sumS1: %d is diff: %d\n\n", sumS2, sumS1, diff);
}

