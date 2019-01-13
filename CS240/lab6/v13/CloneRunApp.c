/*

	CLONE RUN APP FOR EESH.C
	LAB6 PROBLEM 3
	07-23-2017
	CloneRunApp creates a child process to execute command line arguments

*/

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "myheader.h"

int CloneRunApp(char *binary, int strLen, int wordCount) {


	//Parse str array by white space and add individual arguments to array of strings
        int arrSpot = 0; //index for array of strings position
        int indexStr = 0; //index of str array
        int i = 0; //index
        int arg = 0; //index
        char temp[strLen]; //holds parsed argument to be placed in array of strings
	char **arrStr = (char**)malloc(sizeof(char*) * wordCount); //array of strings
	int stat; //status for forking
	pid_t forked; //fork

	//sleep time check and parse
	char sleepCheck[3] = {'d', 'd', '\0'}; //dd is prompt to sleep
	int sleepFlag = 0; //determines if sleep is called
	char sleepTime[3]; //holds time to sleep (up to 99 seconds)	
	int iterate = 0;

	for (iterate = 0; iterate < strlen(sleepCheck); iterate++) {
		if (sleepCheck[iterate] != binary[iterate]) {
			break; //dont sleep
		}
		else {
			sleepFlag = 1; //call sleep below
		}
	}

	//obtain sleep time
	int sleepIndex = 0;
	char strTemp[strLen];
	if (sleepFlag == 1) {
		//placing sleeptime in an array
		while (binary[iterate] != ' ' && sleepIndex <= 2) {
			sleepTime[sleepIndex] = binary[iterate];
			sleepIndex++;
			iterate++;
		}

		//parse off dd and numbers assumes two numbers in input, (i.e. 09, 10, ...)
		//char strTemp[strLen-5]; //temp
		int index = 0;
		int parseOff = 5; //starts after dd##Space (i.e. dd13 )
		
		//places parsed string in temp
		//while (binary[parseOff] != '\0') {
		while (index != strLen-5 && index < strLen-5) {

			strTemp[index] = binary[parseOff];
			index++;
			parseOff++;
		}
		strTemp[index] = '\0'; //end delimiter
		
		memset(&binary[0], 0, sizeof(&binary)); //reset space for parsed string

		index = 0; //reset for loop
		//while (strTemp[index] != '\0') {
		while (index != strLen-4 && index < strLen-4) {
			binary[index] = strTemp[index];
			index++;
		}
		
		//set strLen to proper length
		strLen = strLen - 5;
	}

	//convert string sleep time to unsigned integer
	unsigned int sleepTimeInt;
	sscanf(sleepTime, "%u", &sleepTimeInt);

        //loop while index i is not equal to strLen
        while (i != strLen && i < strLen) {
	        arg = 0; //argument index
                memset(&temp[0], 0, sizeof(&temp)); //reset temp memory for next argument read in

                //parse argument into temp
                while (binary[indexStr] != ' ') {
	                //break if reached end of char array
                        if (i >= (strLen)) {
   	                     break;
                        }
                        temp[arg] = binary[indexStr]; //add to temp
                        arg++;
                        indexStr++;
                        i++;
		}
                temp[arg] = '\0'; //end delimeter
                arrStr[arrSpot] = (char*)malloc(sizeof(char)*(arg + 1)); //set enough space for incoming arg
                strcpy(arrStr[arrSpot], temp); //place string into array of strings

                arrSpot++;
                i++;
                indexStr++;
	}
        arrStr[arrSpot] = NULL; //NULL delimiter

	fprintf(stdout, "new process will run: %s\n", binary);
	if (sleepFlag == 1) {
		fprintf(stdout, "Sleeping for %u seconds...\n", sleepTimeInt);
		sleep(sleepTimeInt);
	}

        forked = fork();
        //child code
        if (forked == 0) {

	        if((execvp(arrStr[0], arrStr)) == -1) {
			exit(1);
		}
	}
        //fork failed, return -1
        else if (forked < 0) {
		return (-1);
        }
	//parent code
	else {
		//returns PID
                if (sleepFlag == 1) {
		}
		return (forked);	
	}
	
	//free
	free(arrStr);

	return (0);
}
