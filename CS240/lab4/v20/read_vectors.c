/*

	READ_VECTORS FUNCTION FOR MAIN.C

*/

#include <stdio.h>
#include <stdlib.h>
#include "dotheader.h"
#include <string.h>

//Initialize function with parameters
int read_vectors(int *vecsize, float x[MAXSIZE], float y[MAXSIZE]) {
	
	int numScanx = 0; //number of numbers entered into x-vector

/*******************************************************************
*			Scan in vecsize                            *
********************************************************************/
	//Scans vecsize input
	printf("Enter Vector Size: ");
	scanf("%d", vecsize);
	fflush(stdin);
	
	//Makes sure the vec size is between 1 and 50 as specified
	if (*vecsize > MAXSIZE || *vecsize < 1) {
		return (-1);
	}
/*******************************************************************
*                    End scan in vecsize                           *
********************************************************************/


/*******************************************************************
*                       Scan in x-vector                           *
********************************************************************/
	char numVec[1024]; //temp char* to read in the values as a string
	printf("Enter x-vector values: \n");
	scanf(" %1023[0-9*.0-9* ]", numVec); //reads up to 1023 chars. Expects any number a "." and any number.
	

	char * pch;
 	pch = strtok (numVec," "); //used to split at white space
  	while (pch != NULL)
  	{
		x[numScanx] =  atof(pch); //sets char as float and saves into 'x'
    		pch = strtok (NULL, " ");
		numScanx++;
  	}	

/*	
	int k = 0; //looping counter
	//Printing prompt for x-vector print
	for (k = 0; k < *vecsize; k++) {
		printf("%f\n", x[k]);
	}
*/
/*******************************************************************
*                     End scan in x-vector                         *
********************************************************************/


/*******************************************************************
*                       Scan in y-vector                           *
********************************************************************/
	char numVec2[1024];
	printf("\nEnter y-vector values: \n");
	scanf(" %1023[0-9*.0-9* ]", numVec2);
	
	int numScany = 0;
	pch = strtok (numVec2," ");
  	while (pch != NULL)
  	{
		y[numScany] =  atof(pch);
    		pch = strtok (NULL, " ");
		numScany++;
  	}	
	
/*
	k = 0; //looping counter
	//Printing prompt for x-vector print
	for (k = 0; k < *vecsize; k++) {
		printf("%f\n", y[k]);
	}
*/
/*******************************************************************
*                     End scan in x-vector                         *
********************************************************************/


/*******************************************************************
*              Return error if improper user input                 *
********************************************************************/
	if ((*vecsize != numScanx) || (*vecsize != numScany)) {
		return (-1);
	}

	return(0);	
}
