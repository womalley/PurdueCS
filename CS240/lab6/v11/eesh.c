/*

	LAB 6 PROBLEM 3
	Customizable shell that takes commands

*/

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "myheader.h"

int main() {
	
	char promptSymbol[MAXSIZE] = "$";
	char str[MAXSIZE]; //users string
        int lineCounter = 0; //initialized for function lineCount
	char countSymbol[MAXSIZE]; //shows line count when on
	char lastStr[MAXSIZE]; //for prevStr function, holds previous used string
	char c; //for reading user string in
	int switchFlag = 0; //for lineCount function. zero is off, and one is on
	int stat; //status for forking
	pid_t forked; //fork 

	//reads the secret string for lck function from file "secretstring.txt"
	FILE *fp;
	char password[MAXSIZE];
	if ((fp = fopen("secretstring.txt", "r")) == NULL) {
		fprintf(stderr, "ERROR: File secretstring.txt does not exist!\n");
		exit(1);
	}
	
	//read file contents to password string
	fscanf(fp, "%s", password);
	
	fclose(fp);

	//start shell loop
	while(1) {
		
		lineCounter++; //iterate line count
		int strLen = 0; //resets string length for every user input
		memset(&str[0], 0, sizeof(str)); //reset input string for multiple use
	
		//prints prompt
		fprintf(stdout, "%s%s: ", countSymbol, promptSymbol);
		
		
		//takes in user entered string
		int index = 0;
	        int wordCount = 1; //counts number of arguments the user enters (start at one, since 1 for NULL space)
		for (index = 0; index < (MAXSIZE - 1) && (c = getchar()) != EOF && c != '\n'; ++index) {
			str[index] = c;
			strLen++;
			//argument count (word count)
			if (c == ' ') {
				wordCount++;
			}
		}
		str[index] = '\0';

		int cprtUsed = 0; //zero means not used, one means used
		int lckUsed = 0; //zero means not used, one means used
		int countUsed = 0; //zero means not used, one means used
		prevStr(str, lastStr, strLen);
		cprtUsed = cprt(promptSymbol, str, strLen); 
		lckUsed = lck(str, promptSymbol, password);
		countUsed = lineCount(countSymbol, str, lineCounter, &switchFlag);

		//exiting terms (exits if user enters E, e, X, or x)
		if (strLen == 1) {
			if (str[0] == 'E' || str[0] == 'e' || str[0] == 'X' || str[0] == 'x') {
				exit(0);
			}
		}
		
		//System call if not a shell command
		if ((cprtUsed == 0) && (lckUsed == 0) && (countUsed == 0)) {
			
			char **arrStr = (char**)malloc(sizeof(char*) * wordCount); //allocates enough space for all args and NULL space		
			//Parse str array by white space and add individual arguments to array of strings
			int arrSpot = 0; //index for array of strings position
			int indexStr = 0; //index of str array
			int i = 0; //index
			int arg = 0; //index
			char temp[strLen]; //holds parsed argument to be placed in array of strings
	
			//loop while index i is not equal to strLen
			while (i != strLen && i < strLen) {
				arg = 0; //argument index
				memset(&temp[0], 0, sizeof(&temp)); //reset temp memory for next argument read in
				
				//parse argument into temp	
				while (str[indexStr] != ' ') {
					//break if reached end of char array
					if (i > (strLen)) {
						break;
					}
					temp[arg] = str[indexStr]; //add to temp
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
			

			fprintf(stdout, "new process will run: %s\n", str);	
 			forked = fork();
			//child code
			if (forked == 0) {
				execvp(arrStr[0], arrStr);
				
			}
			//parent code
			else {
				waitpid(forked, &stat, 0);	
			}
			
			//free array of strings
			free(arrStr);
		}
	}

	return(0);
}
