/*

	httpError
	HTTP ERROR CHECKS INPUT STRING FOR MAIN.C

*/

#include <stdio.h>
#include <stdlib.h>
#include "myHeader.h"


//Checks if the input string has HTTP://
int httpError(char str[MAXSIZE]) {

        int index = 0;
        char error[8] = {'h', 't', 't', 'p', ':', '/', '/', '\0'}; //char array to compare to input string
        while (error[index] != '\0') {
                if (error[index] == '\0') {
                        return (0);
                }
                else if (error[index] != str[index]) {
                        return (-1);
                }
                index++;
        }
}

