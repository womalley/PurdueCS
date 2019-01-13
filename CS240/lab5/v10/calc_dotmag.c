/*

	CALC_DOTMAG FUNCTION FOR MAIN.C

*/

#include <stdio.h>
#include <math.h>
#include "dotheader.h"

//Initialize function with parameters
float calc_dotmag(int vecsize, float x[MAXSIZE], float y[MAXSIZE], float *xMag, float *yMag) {

	//Dot Product calculation
	int index = 0; //used to iterate through the elements of the float arrays
	float dotProduct = 0; //stores the computed dot product value of the two float arrays
	*xMag = 0; //initialize to zero
	*yMag = 0; //initialize to zero

	for (index = 0; index < vecsize; index++) {
		dotProduct = ((x[index] * y[index]) + dotProduct);
	}

	//Magnitude (norm) of x-vector and y-vector prior to square root
	for (index = 0; index < vecsize; index++) {
		*xMag = ((x[index] * x[index]) + *xMag);	
                *yMag = ((y[index] * y[index]) + *yMag);
	}

	//square root the values to get the magnitude
	*xMag = sqrtf(*xMag);
	*yMag = sqrtf(*yMag);
	
	return(dotProduct);
}
