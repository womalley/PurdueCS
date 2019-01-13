// Using pointers (i.e., variables that contain addresses) directly in
// a program.

#include <stdio.h>

int main()
{
int x;
int *y;
int **z;

  x = 5;
  printf("%d\n",x);
  printf("%p\n",&x);

  y = &x;
  printf("%d\n",*y);
  printf("%p\n",y);

  z = &y;
  printf("%d\n",**z);
//  printf("%p\n",z); //prints the address of x

}
