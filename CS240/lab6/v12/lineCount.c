/*

	LINECOUNT FOR ESH.C
	Line count turns on or off (depending on what state it currently is in)
	and numbers the line prior to the prompt symbol.

*/

#include <stdio.h>
#include <string.h>
#include "myheader.h"

int lineCount(char countSymbol[MAXSIZE], char str[MAXSIZE], int lineCounter, int *switchFlag) {

	int countUsed = 0; //determines if function was used
	char callOn[9] = {'c', 'o', 'u', 'n', 't', ' ', 'o', 'n', '\0'}; //char array holds string to check if user calls function to turn on
	char callOff[10] = {'c', 'o', 'u', 'n', 't', ' ', 'o', 'f', 'f', '\0'}; //if user calls function to turn off
//	int switchFlag = 0; //zero is off, one is on. when off, doesnt display numbers

	//sets flag to show and increment line count
	if (!strcmp(str, callOn)) {
		*switchFlag = 1;
		countUsed = 1;
	}
	//turns off the line count display
	else if (!strcmp(str, callOff)) {
		memset(&countSymbol[0], 0, sizeof(&countSymbol));
		countUsed = 1;
		*switchFlag = 0;
	}
	//updates the line count
	if (*switchFlag == 1) {
		sprintf(countSymbol, "%03d ", lineCounter);
	}
	
	return (countUsed);
}
