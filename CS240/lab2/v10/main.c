// Basic string processing.

#include <stdio.h>

int main()
{
int i;
char a[6]; 
//char b[]={'a','b','c','d','e','\0'};
char *strcpy(char *a, const char *b);

  a[0] = 'a';
  a[1] = 'b';
  a[2] = '\0';
  a[3] = 'd';
  a[4] = 'e';
  a[5] = '\0';

  strcpy(a, "abcdef");

  printf("%s\n", a);
}
