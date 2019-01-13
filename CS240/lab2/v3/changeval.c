/*	Function changeval(int *a)
	changes the value of an integer
	by passing by reference */

#include <stdio.h>

void changeval(int *a)
{
  printf("%p\n", a);
  *a = 3;
}
