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
#include "myHeader.h"

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
		ipToInt(str);	
	}
	else {
		printf("\n\nERROR: Something went wrong!!!\n\n");
	}


/*********************************************************************************************
*				   END CHECKS IF URL OR IP 				     *
*********************************************************************************************/


/*********************************************************************************************
*				CONCAT AND SYSTEM CALL					     *
*********************************************************************************************/

	concatSysCall(finalStr);

/*********************************************************************************************
*                             CONCAT AND SYSTEM CALL END                                     *
*********************************************************************************************/





}





