/*
	Driver.c by Randyll Bearer 2019
	This file consists of practice bit manipulation, some common patterns/functions etc.
	Most of these are lifted from Chapter 5. of "Cracking the Coding Interview v3"
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(){
	
	printf("Beginning Bit Manipulation Practice...\n------------------------\n");
		
	//Useful/Good to Know tricks/properties
	printf("Now printing useful bit properties...\n");
		//^ XOR operator
	int x = 0b01010101;	//Binary for 1
	int y = 0b00000000; //Binary for 0
	int z = x^y;
	printf("x  ^ 0s = x\n");
	printf("%d ^ %d = %d\n", x,y,z);
	
	y = 0b11111111;
	z = x^y;
	//so z should be 0b10101010; which is 2^1 + 2^3 + 2^5 + 2^7 = 2 + 8 + 32 + 128 = 170
	printf("x  ^ 1s = ~x\n");
	printf("%d ^ %d = %d\n", x,y,z);
	
	y = x;
	z = x^y;
	printf("x  ^ x = 0\n");
	printf("%d ^ %d = %d\n", x,y,z);
		//& AND operator
	y = 0b00000000;
	z = x&y;
	printf("\nx  & 0s = 0\n");
	printf("%d & %d = %d\n", x,y,z);
	
	y = 0b11111111;
	z = x&y;
	printf("x  & 1s = x\n");
	printf("%d & %d = %d\n", x,y,z);
	
	y = x;
	z = x&y;
	printf("x  & x = x\n");
	printf("%d & %d = %d\n", x,y,z);
		//| OR operator
	y = 0b00000000;
	z = x|y;
	printf("\nx  | 0s = x\n");
	printf("%d | %d = %d\n", x,y,z);
	
	y = 0b11111111;
	z = x|y;
	printf("x  | 1s = 1s\n");
	printf("%d | %d = %d\n", x,y,z);
	
	y = x;
	z = x|y;
	printf("x  | x = x\n");
	printf("%d | %d = %d\n", x,y,z);
	
	//Testing Shifting
	printf("\nNow printing some tests of shifting...\n");
	x = 0b00000001; 
	z  = x << 2; //x should now = 4
	printf("x << y = (x * 2^y)\n");
	printf("%d << 2 = %d\n", x,z);
	
	x = 0b00000100;
	z = x >> 2;
	printf("x >> y = (x/2^y)\n");
	printf("%d >> 2 = %d\n",x,z);
	
	//Common functions for bit maniplation
	printf("\nNow printing results of some useful functions...\n");
		//Get a bit at location i
	int i = 5;
	x = 		0b10101010;
	int mask =  0b00010000;	//ith element of x should be a 0
	z = x & mask;
	printf("%d\n", z);
		//set a bit at location i
	x =    0b00000010;
	mask = 0b00010000;
	z = x | mask; //should be 2 + 16
	printf("%d\n",z);
		//clear a single bit
	x =    0b00001010;
	mask = 0b11110111;
	z = x & mask;
	printf("%d\n",z);
		//clear leftmost bit - i bit (put 1 at i, subtract 1 from i, & mask)
	x =    0b11110001;
	mask = 0b00010000;
	mask = mask = 1;
	z = x & mask;	//Should be 1
	printf("%d\n",z);
		//clear i bit - rightmost bit (get all 1s, shift left i times, & mask)
	i = 4;
	x =    0b00011111;
	mask = 0b11111111;
	mask = mask << i;
	z = x & mask;
	printf("%d\n",z);
	
}
//End of File