/*

	PREVSTR FOR ESH.C
	The prev command function repeats the last command entered

*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "myheader.h"

void prevStr(char str[MAXSIZE], char lastStr[MAXSIZE], int strLen) {

	char clone[6] = {'c', 'l', 'o', 'n', 'e', '\0'};
	int index = 0; //for string looping
	
	//if user calls function
	if (!strcmp(str, clone)) {
		memset(&str[0], 0, sizeof(&str)); //clear string contents
		int prevStrLen = strlen(lastStr); //length of the previous string
		
		//set string to previous string
		for (index = 0; index < prevStrLen; index++) {
			str[index] = lastStr[index];
		}
		str[index] = '\0';

	}
	//set str as new previous string for use next time function is called
	else {
		memset(&lastStr[0], 0, sizeof(&lastStr)); //clear string contents
		for (index = 0; index < strLen; index++) {
			lastStr[index] = str[index];
		}
		lastStr[index] = '\0';
	}
}
