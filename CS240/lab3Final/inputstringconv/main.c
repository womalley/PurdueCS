/*
	PROBLEM 2
	CS 240
	07/05/2017

	Write a program, main() in main.c, that reads from standard input a string and writes another string to standard output.
	The string that it outputs is the input string whose lower/upper cases are toggled. 
	For example, 'A' becomes 'a' and 'B' is changed to 'b'. Numeric characters '0', '1', ..., '9' are changed 
	to 'A', 'B', ..., 'J', respectively. All other characters are replaced by a space. 
*/

#include <stdio.h>
#define MAXSIZE 500
#define TRUE 1

void main() {

	
	char str[MAXSIZE];
	char c;
	int i, strLen = 0;
	char newStr[MAXSIZE];

	// Input prompt
	printf("\nInput character string below:\n");

	// Parses input characters into character array (string)
	for (i = 0; i < (MAXSIZE - 1) && (c = getchar()) != EOF && c != '\n'; ++i) {
		str[i] = c;
		++strLen;
	}	
	
	//Accounts for new lines
	if (c == '\n') {
		str[i] = c;
		++i;
	}
	str[i++] = '\0'; //End delimiter

	//Change values
	for (i = 0; i < (strLen); i++) {
		//Lower to upper case
		if (str[i] >= 97 && str[i] <= 122) {
			newStr[i] = str[i] - 32;
		}
		//Upper to lower case
		else if (str[i] >= 65 && str[i] <= 90) {
			newStr[i] = str[i] + 32;
		} 
		//Numbers 0-9 changed to A-J
		else if (str[i] >= 48 && str[i] <= 57) {
			newStr[i] = str[i] + 17;
		}
		//All other values set to space
		else {
			newStr[i] = 32;
		}
	        printf("loops: %d\n\n", i);

	}
	newStr[i++] = '\0'; //End delimiter for new array (Without it, there will be garbage values)

	//Prints the entire char array as string
	printf("\n\n%s\n\n", newStr);
}
