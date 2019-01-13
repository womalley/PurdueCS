/*

	CPRT FOR ESH.C
	Determines if the cprt command is being called. If command is called,
	this function changes the standard prompt '$' to whatever the user inputs.

*/

#include <stdio.h>
#include <string.h>
#include "myheader.h"

int cprt(char promptSymbol[MAXSIZE], char str[MAXSIZE], int strLen) {

	//checks if the string starts with "cprt" and then performs the prompt symbol change
        char symChange[6] = {'c', 'p', 'r', 't', ' ', '\0'};
        int symFlag = 0; //flag that determines if the prompt symbol needs to be changed
        int index = 0; //used as iterator

        for (index = 0; index < strlen(symChange); index++) {
		if (symChange[index] != str[index]) {
                	symFlag = 0; //dont change
                        break;
                }
                else {
                	symFlag = 1; //change
                }
	}

        //change symbol
      	if (symFlag == 1) {
        	memset(&promptSymbol[0], 0, sizeof(&promptSymbol)); //set prompt symbol to nothing
                int i = strlen(symChange); //length of symChange
                int k = 0;
                for (i = strlen(symChange); i < strLen; i++) {
	                promptSymbol[k] = str[i]; //set string after cprt to new symbol
                        k++;
                }
                promptSymbol[i] = '\0'; //end delimiter
	}
	return (symFlag);
}
