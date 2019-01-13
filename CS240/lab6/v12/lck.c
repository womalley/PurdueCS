/*

	lck function for esh.c
	This function locks the terminal until the password entered

*/

#include <stdio.h>
#include <string.h>
#include "myheader.h"

int lck(char str[MAXSIZE], char promptSymbol[MAXSIZE], char password[MAXSIZE]) {
 
	//lck function (locks the terminal until the user inputs the password)
        char lckCheck[4] = {'l', 'c', 'k', '\0'};
        int lckFlag = 0;
        int index = 0;
        int lckLen = strlen(lckCheck);
	int strLen = strlen(str);
	int lckUsed = 0; //value determines if lck was used or not (0 false, 1 true)
	
        for (index = 0; index < strlen(lckCheck); index++) {
        	if (lckCheck[index] != str[index]) {
                	lckFlag = 0; //dont lock terminal
                        break;
                }
                else {
                        if (lckLen == strLen) {
                  		lckFlag = 1; //lock terminal
				lckUsed = 1; //lock was used
                        }
                }
	}


        //lock terminal until password entered
        index = 0;
        char pc; //password character
        char secretStr[MAXSIZE];

        while (lckFlag != 0) {
        	memset(&secretStr[0], 0, sizeof(secretStr)); //clear character array to be reused

                fprintf(stdout, "%s ENTER PASSWORD TO UNLOCK: ", promptSymbol); //prompts user
                for (index = 0; index < (MAXSIZE - 1) && (pc = getchar()) != EOF && pc != '\n'; ++index) { //obtains user password attempt
              		secretStr[index] = pc;
                }
                int len = strlen(password);
                int secretLen = strlen(secretStr);

                if (!strcmp(secretStr, password)) { //set flag to unlock terminal if user inputs correct password
                	lckFlag = 0;
                }
	}
	return (lckUsed);
 
}
