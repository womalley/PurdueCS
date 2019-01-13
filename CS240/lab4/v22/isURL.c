/*

	isURL
	ISURL CHECK FOR MAIN.C	

*/

#include <stdio.h>
#include <stdlib.h>
#include "myHeader.h"

//Determines if input is URL or IP Address
int isURL(char *str) {

        printf("STR: %s\n\n\n", str);
        int index = 0;
        while(str[index] != '\0') {
                if ((str[index] >= 48 && str[index] <= 57) || str[index] == 46) { //ASCII values of zero and nine
                        //keep looping so far it looks like an IP Address
                }
                else {
                        return(1); //returns 1 since index must contain something other than a dot or number (thus its a URL)
                }
                index++;
        }

        return(-1); //if it makes it to the end delimiter without returning, it's an IP Address
}

