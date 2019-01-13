/*
	PROBLEM 3
	CS 240
	07/05/2017

	Write a program, myencrypt, that encrypts a file using a simple substitution cipher. myencrypt reads from stdin three values 
	N filename1 filename2

	NOTE: The wrap-around was implemented by using modulus. If the value becomes greater than the max value of 7-bit ASCII (127),
	then it does the value modulus 128.
	
*/

#include <stdio.h>
#include <stdlib.h>

void main() {

	int n = 0; //number used to encrypt file
	char filename1[100]; //original file
	char filename2[100]; //encrypted file
	int maxSize = 100;
	int i = 0;
	char c;
	char info[1000];
	size_t k = 0;
	int counter = 0;
	
	FILE *f;
	FILE *fNew;
	
	//Scan in n, original file, and encrypted file

	printf("Enter n, file name, and encrypted name separated by spaces:\n");
	scanf("%d %s %s", &n, filename1, filename2);

	//Open original file to encrypt
	if (n > 0 || n < 0) {
		f = fopen(filename1, "a+"); //creates empty file if it does not exist
		if (f == NULL) {
			fprintf(f,"ERROR: FILE DID NOT OPEN!");
			exit(1);
		}
		else {
			//sets contents to new array to be manipulated and saved into new file
			while ((c = getc(f)) != EOF) {
				info[k++] = c;	
				counter++;	
			}
			//create new file (filename2)
			fNew = fopen(filename2, "w");
	
			//Loop through array 'info' and change values by n
			for (i = 0; i < counter-1; i++) {

				//Wraps the 7-bit ASCII table if the value is greater than 127
				if (info[i] + n > 127) {
					info[i] = (info[i] + n) % 128; //using mod to wrap the text
				}
				//Does not wrap the text (not needed)
				else {
                                	info[i] = info[i] + n;
				}
				//Places the encrypted text in new file
				fputc(info[i], fNew);
			} 
			
			//close files
			fclose(f);	
			fclose(fNew);
		}
	}
}
