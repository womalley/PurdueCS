//main.c

#include <stdio.h>
#include <stdlib.h>

char *sub_string(char*, int, int);

int main() {
	int start_index, end_index;
	char *in_string;
	char*out_string;

	/*Code to receive inputs from user*/
	printf("Enter a string: ");
	scanf("%s", in_string);
	
	printf("Enterthe start index: ");
	scanf("%d", &start_index);

	printf("Enter the end index: ");
	scanf("%d", &end_index);

	/*Code to print the sub-string*/
	out_string = sub_string(in_string, start_index, end_index);

	return 0;
}
