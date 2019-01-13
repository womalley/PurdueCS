/*
	PROBLEM 3 LAB 4
	CS240
	07/12/2017
	
	PART A: 
	Write a program that takes a string from stdin. The input will be a URL or IP address.
	Parse out the IP address or URL. Check to make sure it starts with "http://" and the domain 
	is of type .org or .gov. Lastly, convert the dotted decimal format of the IP address to an integer.

	PART B: 
	Use legacy app to print the IP address of a URL and vice versa.		
*/

#include <stdio.h>
#include <stdlib.h>
#define MAXSIZE 500 //Max string length for input

//int httpError(char *);   //returns error if Hypertext Transfer Protocol not included
//int domainError(char *); //returns error if domain type not .org or .gov
//int isURL(char *);    //returns value that determines if input string is IP address or URL

void main() {

	char str[MAXSIZE];	//Input string
	char strLower[MAXSIZE]; //Input string set to lowercase
	char c;
	int index, strLen = 0;
	int errorVal = 0;

/*********************************************************************************************
*			    USER INPUT AND CONVERTS TO LOWERCASE			     *
*********************************************************************************************/
	//User input
	printf("Enter URL or IP address below:\n");
	
        //Parses input characters into character array (string)
        for (index = 0; index < (MAXSIZE - 1) && (c = getchar()) != EOF && c != '\n'; ++index) {
                str[index] = c;
                ++strLen;
        }

        //Accounts for new lines
        if (c == '\n') {
                str[index] = c;
                ++index;
        }
        str[index++] = '\0'; //End delimiter

	//Input string to lower case
	for (index = 0; index < strLen; index++) {
		//Sets uppercase letter to lowercase
		if (str[index] >= 65 && str[index] <= 90) {
			strLower[index] = str[index] + 32;
		}
		else {
			strLower[index] = str[index];
		}
	}
	strLower[index++] = '\0'; //end delimiter for lower case string

/*********************************************************************************************
*				END INPUT/CONVERTER					     *
*********************************************************************************************/

	
/*********************************************************************************************
*				  ERROR CHECK HTTP://					     *
*********************************************************************************************/
	//Error check for HTTP://
	errorVal = httpError(strLower);
	if (errorVal == -1) {
		printf("\n\nERROR: Invalid Hypertext Transfer Protocol!!!\n");
		exit(1);
	}

/*********************************************************************************************
*				  END ERROR CHECK					     *
*********************************************************************************************/


/*********************************************************************************************
*				IP address or URL Extractor				     *
*********************************************************************************************/
	//Parse off uneeded characters
        int strIndex = 7; //starting after HTTP://
        int newIndex = 0;
	char parseStr[strLen - strIndex];
	char finalStr[strLen - strIndex];	

        //Parses off the HTTP://
        while (str[strIndex] != '\0') {
                parseStr[newIndex] = str[strIndex];
                strIndex++;
                newIndex++;
        }
	parseStr[newIndex++] = '\0'; //end delimiter
	
	//Parses off everything after domain
	for (strIndex = 0; strIndex < newIndex; strIndex++) {
		if (parseStr[strIndex] == '/') { //break at first '/' so all that is left is our final string (i.e. www.google.com/maps -> www.google.com)
			break;
		}
		finalStr[strIndex] = parseStr[strIndex]; //add to final form of string
	}	
	finalStr[strIndex++] = '\0'; //end delimiter

	printf("FINAL STRING: %s\n\n", finalStr);

/*********************************************************************************************
*					END EXTRACTOR					     *
*********************************************************************************************/


/*********************************************************************************************
*				   CHECKS IF URL OR IP ADDRESS				     *
*********************************************************************************************/

	int strType = 0;
	strType = isURL(finalStr);
	printf("\n\nSTR TYPE: %d\n\n", strType);
	if (strType == 1) { //1 means type is URL

	        //Error check domain type
        	errorVal = domainError(finalStr);
	        if (errorVal == -1) {
        	        printf("\n\nERROR: Invalid domain type!!!\n");
                	exit(1);
       		}
	}
	else if (strType == -1) { //-1 means type is IP Address
		printf("\n\nIP ADDRESS CONVERSION COMING SOON TO A THEATER NEAR YOU!!!\n\n");
		
	}
	else {
		printf("\n\nERROR: Something went wrong!!!\n\n");
	}


/*********************************************************************************************
*				   END CHECKS IF URL OR IP 				     *
*********************************************************************************************/




}

/*

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
*/


