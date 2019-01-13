/*
	HEADER FOR MAIN.C
*/

#define MAXSIZE 500
int httpError(char *);   //returns error if Hypertext Transfer Protocol not included
int domainError(char *); //returns error if domain type not .org or .gov
int isURL(char *);    //returns value that determines if input string is IP address or URL
void concatSysCall(char *); //concats final string with "host " and uses system call
void ipToInt(char *); //converts IP to integer and prints value

