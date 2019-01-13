/*

	write_vector
	WRITE VECTOR FOR MAIN.C
	Write vector takes in the x and y-vector, the magnitudes, the difference, and the square of the difference.
	Write vector then writes the information to a dat file called "vecout.dat"

*/

#include <stdio.h>
#include "dotheader.h"
#include <stdlib.h>

void write_vectors(int vecsize, float x[MAXSIZE], float y[MAXSIZE], float *xMag, float *yMag, float dotProd, float u[MAXSIZE], float sqDiff[MAXSIZE]) {

	FILE *fpw; //File pointer to write to

	//Open file to write
	fpw = fopen("vecout.dat", "w");
	if (fpw == NULL) {
		fprintf(stderr, "ERROR: Creating write file failed!\n");
		exit(1);
	}

	//Write the magnitudes
	fprintf(fpw, "MAGNITUDES:\n");
	fprintf(fpw, "x-vector magnitude: %f\n", *xMag);
	fprintf(fpw, "y-vector magnitude: %f\n", *yMag);

	//Write the dot product
	fprintf(fpw, "\nThe dot product of x and y-vector: %f\n\n", dotProd);

	//Write the difference of the two vectors
	fprintf(fpw, "The difference of x and y-vector:\n");
	int index = 0;
	for (index = 0; index < vecsize; index++) {
		fprintf(fpw, "At index %d: %.2f\n", index, u[index]);
	}

	//Write the square of the difference of the two vectors
	fprintf(fpw, "\nThe square of the difference:\n");
	index = 0; //reset for looping
	for (index = 0; index < vecsize; index++) {
		fprintf(fpw, "At index: %d: %.2f\n", index, sqDiff[index]);
	}

}
