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
        	return (forked); 
	}
	
	//free
	free(arrStr);

	return (0);
}
