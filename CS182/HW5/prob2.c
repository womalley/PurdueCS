/*

	ASSIGNMENT 5 PROBLEM 1 PART 2
	CODE SEGMENT 2
	07-20-2017

*/

#include <stdio.h>
#include <math.h>

void main() {
	
	double k = 3; //test number
	double n = pow(2, k);
	int i = 1;
	int count = 0;

	while (n > 10) {
		i = 1;
		while (i < n) {
			printf("\nFUNCTION CALLED\n");
			i = i * k;
			count++;
		}
		n = n / 2;
	}
	printf("CALLS: %d\n", count);
}
