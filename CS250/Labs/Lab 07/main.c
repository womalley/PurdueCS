#include <stdio.h>
#include <stdlib.h>

char* sub_string(char*, int, int);

int main()
{
	int start, end;
	char* in = (char*)malloc(25);
	char* out = (char*)malloc(30);

	printf("Enter a string: ");
	scanf("%s", in);

	printf("Enter the start index: ");
	scanf(" %d", &start);

	printf("Enter the end index: ");
	scanf(" %d", &end);

	out = sub_string(in, start, end);

	printf("The substring of the given string is '%s'\n", out);
}
