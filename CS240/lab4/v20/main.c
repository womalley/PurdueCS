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
#include "dotheader.h"

void main() {

	int vecsize = 0; //User input, number of variables per vector
	float x[MAXSIZE];
	float y[MAXSIZE];
	float xMag[MAXSIZE];
	float yMag[MAXSIZE];
	float u[MAXSIZE];
	int ret = 0; //return value for read_vector function (error check)	

	//Call read_vector to scan in components and check for faults
	ret = read_vectors(&vecsize, x, y);
	
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
		
		//Print statements of calculations
		printf("\nThe dot product of the two vectors is: %.2f\n", dotProd);
		printf("The magnitude of the x-vector is: %.2f\nThe magnitude of the y-vector is: %.2f\n", *xMag, *yMag);
		
		printf("\nThe difference between the x and y-vectors:\n");
		for (int index = 0; index < vecsize; index++) {
			printf("At index %d: %.2f\n", index, u[index]);
		}
	}
        printf("-------------------END----------------------\n\n");

}
