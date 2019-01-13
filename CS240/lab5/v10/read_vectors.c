/*

	READ_VECTORS FUNCTION FOR MAIN.C

*/

#include <stdio.h>
#include <stdlib.h>
#include "dotheader.h"
#include <string.h>

//Initialize function with parameters
int read_vectors(int *vecsize, float x[MAXSIZE], float y[MAXSIZE]) {
	
	FILE *fp; //FILE file pointer(read) and file pointer write(write)
	float  fpContent[MAXSIZE]; //Holds all of the files content to be later distributed into vecsize, x-vector, and y-vector

	//Open file to be read
	if ((fp = fopen("vecin.dat", "r")) == NULL) {
		fprintf(stderr, "ERROR: File vecin.dat does not exist!\n");
		exit(1);	
	}


	//Read data to fpContent
	int index = 0;
	while(fscanf(fp, "%f", &fpContent[index]) != EOF) {
		index++;
	}

	//Obtain vecsize from fpContent (file read in)
	*vecsize = fpContent[0];

	//Error check for improper input numbers
	if (index != ((2 * (*vecsize)) + 1)) {
		fprintf(stderr, "ERROR: Improper input, x and y-vectors not of equal length!\n");
	}


	//Obtain the x-vector
	index = 1; //reset to 1 (starting point of x-vector content)
	int vecIndex = 0; //vector index (for x and y-vectors)
	for (vecIndex = 0; vecIndex < *vecsize; vecIndex++) {
		x[vecIndex] = fpContent[index];
		index++;
	}

	//Obtain the y-vector
	index = (*vecsize + 1); //vecsize plus one, so it starts at the index the y-vector values begin
	vecIndex = 0; //reset for storing values in y-vector array 'y'
	for (vecIndex = 0; vecIndex < *vecsize; vecIndex++) {
		y[vecIndex] = fpContent[index];
		index++;
	}

	fclose(fp); //close file (done reading)


	//TESTING INPUT TO ARRAYS
/*	printf("\n\nVECSIZE: %d\n\n", *vecsize);
	for (int i = 0; i < *vecsize; i++) {
		printf("\n\nX-VECTOR: %f\n\n", x[i]);
	}
        for (int i = 0; i < *vecsize; i++) {
                printf("\n\nY-VECTOR: %f\n\n", y[i]);
        }
*/


	return(0);
}



//OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW OLD CODE BELOW
/*

	//Makes sure the vec size is between 1 and 50 as specified
	if (*vecsize > MAXSIZE || *vecsize < 1) {
		return (-1);
	}


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
	
	return(0);	
}
*/
