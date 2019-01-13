/*

	QUICK FOR ESH.C
        The quick Set command saves a specified string to later be called by "qk"
	CAUSES GARBAGE VALUES BEFORE PROMPT SYMBOL UNTIL COUNT IS TOGGLED ON OR OFF!!!!!!!!

*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "myheader.h"

int quickSet(char str[MAXSIZE], char quick[MAXSIZE], int strLen) {

	char quickCheck[7] =  {'q', 'u', 'i', 'c', 'k', ' ', '\0'};
	char qkCall[3] = {'q', 'k', '\0'};
	int qFlag = 0; //determines if user is setting new string to quick command	
	int index = 0;
		
	//check if user is calling quick command to set new string to quick
        for (index = 0; index < strlen(quickCheck) - 1; index++) {
                if (quickCheck[index] != str[index]) {
			qFlag = 0; //dont change
                        break;
                }
                else {
                        qFlag = 1; //change
                }
        }

	//change saved string to new user string
	if (qFlag == 1) {
		int quickIndex = 0; //used as index iterator and holds the length of the input string
		memset(&quick[0], 0, sizeof(&quick)); //reset array for new string
		int chkLen = strlen(quickCheck); //length of quickCheck array
		for (chkLen = strlen(quickCheck); chkLen < strLen - 1; chkLen++) {
			quick[quickIndex] = str[chkLen];
			quickIndex++;
		}
		quick[quickIndex] = '\0'; //end delimiter
	}

	//if user inputs "qk" to use saved string
	index = 0; //reset for use in for-loop below
	if (!strcmp(str, qkCall)) {
                memset(&str[0], 0, sizeof(&str)); //clear string contents, so saved string can be placed inside
		int quickLen = strlen(quick); //length of stored string

                //set string for call with "qk"
                for (index = 0; index < quickLen - 1; index++) {
                        str[index] = quick[index];
                }
                str[index] = '\0';
        }
	return (qFlag);

}
