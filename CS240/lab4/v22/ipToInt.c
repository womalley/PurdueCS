/*

	ipToInt 
	IP TO INT FOR MAIN.C
	This function converts the user input IP address (in dot decimal) into unsigned integer
	and prints the value to the terminal

*/


#include <stdio.h>
#include "myHeader.h"

void ipToInt(char *str) {

	int index = 0;
	int dots = 0; //number of dots 
	//Count number of dots in string
	while (str[index] != '\0') {
		if (str[index] == '.') {
			dots++;
		}
		index++;
	}
	
}
