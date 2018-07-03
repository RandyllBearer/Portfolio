#include <stdlib.h>
#include <stdio.h>

static void* return_arg(void* p);

int frame3 ( void )
{
  int *a = malloc(10 * sizeof(int));
  int n = a[9];
  a[5] = 42;  

  if (a[5] == 42) {	//possibly unintialized
    printf("hello from frame3().  The answer is 42.\n");
  } else {
    printf("hello from frame3().  The answer is not 42.\n");
  }

  a[0] = 0;
  n = a[  a[0] & 7  ]; //No idea what this is

  return_arg(&n);	//No need to free() just n when we later free a
  
  free(a);

  a = malloc(99 * sizeof(int)); //This allocation serves no purpose
  free(a);
  return n;
}

int frame2 ( void )
{
  return frame3() - 1;
}

int frame1 ( void )
{
  return frame2() + 1;
}

int main ( void )
{
  return frame1() - 1;
}

static void* return_arg(void* p)
{
   return p;
}

