/*
	PROBLEM 1 LAB 4
	calc_diff function for main.c
	Takes the difference of the indeces of two vectors
*/

#include <stdio.h>
#include "dotheader.h"

void calc_diff(int vecsize, float *x, float *y, float *u) {

	//loops through the entire array
	int index;
	
	for (index = 0; index <= vecsize; index++) {
		//sets the difference of the two arrays to a new array
		u[index] = y[index] - x[index];
	}
}

