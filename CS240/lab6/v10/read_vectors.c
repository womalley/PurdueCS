/*

	READ_VECTORS FUNCTION FOR MAIN.C

*/

#include <stdio.h>
#include <stdlib.h>
#include "dotheader.h"
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

//Initialize function with parameters
int read_vectors(int *vecsize, float **x, float **y) {
	
	FILE *fp; //FILE file pointer(read) and file pointer write(write)
//	float  *fpContent = (float*)malloc(sizeof(float)); //Holds all of the files content to be later distributed into vecsize, x-vector, and y-vector
	float fpSize[1];
	//Open file to be read
	if ((fp = fopen("vecin.dat", "r")) == NULL) {
		fprintf(stderr, "ERROR: File vecin.dat does not exist!\n");
		exit(1);	
	}
	
	//scan the vector size from the file
	fscanf(fp, "%f", &fpSize[0]);
	*vecsize = fpSize[0];
	
	//File size
	char ch;
	int count = 0; //counts numbers of variables in file
	
	while ((ch = fgetc(fp)) != EOF) {
		if (ch == ' ' || ch == '\n') {
			count++;
		}
		
	}
	fclose(fp); //CLOSE FILE


	//Error check for improper input numbers
	if (count != ((2 * (*vecsize)) + 1)) {
		fprintf(stderr, "ERROR: Improper input, x and y-vectors not of equal length!\n");
	}
        
	//Read data into fpContent after getting vecsize (malloc)
	float *fpContent = (float*)malloc(sizeof(float) * ((*vecsize * 2) + 1));


        //Open file to be read entire file to temporary array
        if ((fp = fopen("vecin.dat", "r")) == NULL) {
                fprintf(stderr, "ERROR: File vecin.dat does not exist!\n");
                exit(1);
        }

	//scanning file into array
	int index = 0;
	while (fscanf(fp, "%f", &fpContent[index]) != EOF) {
		index++;
	}

	//malloc x and y vector with vecsize
	*x = (float*)malloc(sizeof(float) * (*vecsize));
	if (*x == NULL) {
		fprintf(stderr, "MALLOC FAILED!\n");
	}
	
	*y = (float*)malloc(sizeof(float) * (*vecsize));
	if (*y == NULL) {
		fprintf(stderr, "MALLOC FAILED!\n");
	}

	//Obtain the x-vector
	index = 1; //reset to 1 (starting point of x-vector content)
	int vecIndex = 0; //vector index (for x and y-vectors)
	for (vecIndex = 0; vecIndex < *vecsize; vecIndex++) {
		x[vecIndex] = &fpContent[index];
		index++;
	}

	//Obtain the y-vector
	index = (*vecsize + 1); //vecsize plus one, so it starts at the index the y-vector values begin
	vecIndex = 0; //reset for storing values in y-vector array 'y'
	for (vecIndex = 0; vecIndex < *vecsize; vecIndex++) {
		y[vecIndex] = &fpContent[index];
		index++;
	}

	fclose(fp); //close file (done reading)

	return(0);
}

