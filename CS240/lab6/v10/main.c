/*
	PROBLEM 4
	CS240
	07/05/2017

	Create a program that obtains the dot product of two vectors
	and computes the magnitude of each vector. Adhere to the constraints
	listed in the Lab3 document.

*/

#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include "dotheader.h"

void main() {

	int vecsize = 0; //User input, number of variables per vector
	int ret = 0; //return value for read_vector function (error check) 
        int maxsize = 0;
	float *x = (float*)malloc(sizeof(float)); //x-vector entered by user
        float *y = (float*)malloc(sizeof(float)); //y-vector entereed by user	

	//Call read_vector to scan in components and check for faults
        ret = read_vectors(&vecsize, &x, &y);
	int size = sizeof(maxsize);
	float *xMag = (float*)malloc(sizeof(float)*vecsize); //magnitude
	float *yMag = (float*)malloc(sizeof(float)*vecsize); //magnitude
	float *u = (float*)malloc(sizeof(float)*vecsize); //difference of x and y-vectors
	float *sqDiff = (float*)malloc(sizeof(float)*vecsize); //holds u indices squared
	
	//Error print if read_vectors returns -1
	if (ret == -1) {
		printf("ERROR! IMPROPER INPUT\n");
	}
	else{
			
		//Call calc_dotmag to compute the dot product and magnitudes of the vectors
		float dotProd = 0; //Holds the return value of the dot product calculation
		dotProd = calc_dotmag(vecsize, x, y, xMag, yMag); //sets the return value of the function to the dotProd
		
		//Call calc_diff to compute the difference between x and y-vectors
		calc_diff(vecsize, x, y, u);
		
		//Call calc_power to determine the square of each indice in float array u
		calc_power(vecsize, u , sqDiff);

                //Outputs calculations and information to file "vecout.dat"
		write_vectors(vecsize, x, y, xMag, yMag, dotProd, u, sqDiff);	
	
	}
	printf("Calculations placed in file \"vecout.dat\"\n\n");

	//free all mallocs
	free(xMag);
	free(yMag);
	free(u);
	free(sqDiff);
}
