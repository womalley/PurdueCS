/*

	domainError
	DOMAIN ERROR CHECK FOR MAIN.C

*/

#include <stdio.h>
#include <stdlib.h>
#include "myHeader.h"

//Checks if domain is .org or .gov
int domainError(char *finalStr) {

        //Determine string length so that we can start at the end index - 4
        int length = 0;
        int size = 5; //size of domain arrays
        char org[5] = {'.', 'o', 'r', 'g', '\0'};
        char gov[5] = {'.', 'g', 'o', 'v', '\0'};

        while (finalStr[length] != '\0') {
                length++;
        }

        int orgVal, govVal = 0; //holds the value that says if the final string is of that type domain (0 = yes, -1 = no)
        int domainIndex = 0;
        int strIndex = length - size + 1;

        //Check if domain is .org
        while (finalStr[strIndex] != '\0') {
                if (finalStr[strIndex] != org[domainIndex]) {
                        orgVal = -1;
                }

                strIndex++;
                domainIndex++;
        }

        //reset reused indeces
        domainIndex = 0;
        strIndex = length - size + 1;

        //Check if domain is .gov
        while (finalStr[strIndex] != '\0') {
                if (finalStr[strIndex] != gov[domainIndex]) {
                        govVal = -1;
                }

                strIndex++;
                domainIndex++;
        }

        //Determines if domain is not .org or .gov
        int retError = orgVal + govVal;
        if (retError == -2) {
                return(-1);
        }
        else {
                return(0);
        }
}


