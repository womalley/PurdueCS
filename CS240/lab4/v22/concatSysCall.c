/*

	concatSysCall
	CONCAT SYSTEM CALL concatenates the URL/IP Address with "host " and an end delimiter,
	so that a system() call can be made to the terminal. Displaying the URL's IP or vice versa

*/

#include <stdio.h>


void concatSysCall(char *str) {

	int system(char *concatStr);
	int len = 0; //string length
	int size = 5; //length of host array
	char host[5] = {'h', 'o', 's', 't', ' '}; //host string to concat to URL/ IP address string
	
	//length of input string
	while (str[len] != '\0') {
		len++;
	}
	
	char concatStr[len + size]; //concat string initialized to proper size

	
	int index = 0;
	int strIndex = 0;
	//place both strings into one for system call
	for (index = 0; index < (len + size); index++) {
		//place host array in first
		if (index < size) {
			concatStr[index] = host[index];
		}
		else {
			//place input string in concat string
			concatStr[index] = str[strIndex];
			strIndex++;
		}
	}
	concatStr[index++] = '\0'; //end delimiter

	printf("CONCAT STR:%s\n\n", concatStr);

	system(concatStr);

}
