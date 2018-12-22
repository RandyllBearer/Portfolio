/*
	Driver.c by Randyll Bearer 2018
	Simple implementation of a dynamically resizing Stack data structure in C
	This Driver.c is meant to test its corresponding Stack.c implementation.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include Stack.h	//Must be in local directory

//Driver method to test Stack.c
int main(){
	char z[100] = "Initializing Stack...";
	
	//Create and initialize the Stack struct
	struct Stack myStack;
	myStack.capacity = 2;
	myStack.currentStack = (Stack *)malloc(sizeof(Stack)+capacity*sizeof(int));	//Allocates memory in stack for 2 ints.
	myStack.numItems = 0;
	
	
	push(&myStack, 1);
	push(&myStack, 2);
	push(&myStack, 3);
	
	int numItemsInStack = getNumItems(&myStack);
	printf("There are currently %d items in the Stack");
	
	pop(&myStack);
	pop(&myStack);
	printf("There are currently %d items in the Stack");
	
	free(myStack.currentStack);
}
//End of File