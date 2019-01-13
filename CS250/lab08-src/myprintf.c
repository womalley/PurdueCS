#include <stdio.h>
void printd(int);
void printx(int);

int myprintf(const char* format, ...) {
	int *spot = (int*)&format;

	//Loop till end delimiter
	while(*format != '\0') {

		//If hit % check character after it
		if(*format == '%') {
			format++;
		
			//print character 	
			if(*format == 'c') {
				spot++;
				char* c = (char*) spot;
				putchar(*c);
				format++;
			}
			//print string
			else if(*format == 's') {		
				spot++;
				char* s = (char*) *spot;
				while(*s != '\0') {
					putchar(*s);
					s++;
				}
				format++;
			}
			//print decimal
			else if(*format == 'x') {
				spot++;
				int x = *spot;
				printx(x);
				format++;
			}
			//print hexadecimal
			else if(*format == 'd') {
				spot++;
				int d = *spot;
				printd(d);
				format++;
			}
		}
		else {
			putchar(*format);
			format++;
		}
	}
 
	return (1);
}
