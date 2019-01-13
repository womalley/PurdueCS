/*
	PROBLEM 1
	LAB 3
	07/05/2017
	
	Write a program that uses uses sizeof() to print the size of all types 
	in Chapter 2 of K & R and their composition with type qualifiers. 
	Deposit main.c in a subdirectory typesize/ created under lab3/.

*/

#include <stdio.h>

void main() {
	
        //Size of type char with all possible qualifiers
        printf("char: %zu\n", sizeof(char));
        printf("const char: %zu\n", sizeof(const char));
        printf("signed char: %zu\n", sizeof(signed char));
        printf("unsigned char: %zu\n\n", sizeof(unsigned char));

	
	//Size of type int with all possible qualifiers
	printf("int: %zu\n", sizeof(int));
        printf("const int: %zu\n", sizeof(const int));
        printf("signed int: %zu\n", sizeof(signed int));
        printf("unsigned int: %zu\n", sizeof(unsigned int));
        printf("short int: %zu\n", sizeof(short int));
        printf("long int: %zu\n\n", sizeof(long int));
 

        //Size of type float with all possible qualifiers
	printf("float: %zu\n", sizeof(float));
        printf("const float: %zu\n\n", sizeof(const float));


        //Size of type double with all possible qualifiers
        printf("double: %zu\n", sizeof(double));
        printf("const double: %zu\n", sizeof(const double));
        printf("long double: %zu\n\n", sizeof(long double));

}
