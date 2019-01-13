/*

	calc_power
	CALC POWER FOR MAIN.C
	LAB 5 PROBLEM 1
	
	Function finds the power of the difference of the x and y-vectors.
	The difference is obtained by the calc_diff function that returns to the main.

*/

#include <stdio.h>
#include <math.h>
#include "dotheader.h"

void calc_power(int vecsize, float *u, float *sqDiff) {

	int power = 2; 
	int index = 0;
	for (index = 0; index < vecsize; index++) {
		sqDiff[index] = pow(u[index], power);
	}
}
