/*
	Driver.c by Randyll Bearer 2018
	Simple implementation of a dynamically resizing Stack data structure in C
	This Driver.c is meant to test its corresponding Stack.h implementation.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Stack.h"	//Must be in local directory

//Driver method to test Stack.h
int main(){
	
	//Create and initialize the Stack struct
	struct Stack myStack;	//Initializes a new Stack struct
	
	myStack.capacity = 2;
	myStack.currentStack = malloc(myStack.capacity*sizeof(int));	//Allocates memory in stack for 2 ints.
	myStack.numItems = 0;
	
	
	//Push three elements onto the Stack
	
	printf("Current Stack capacity = %d\n", myStack.capacity);	//This should be 2
	
	push(&myStack, 1);
	
	printf("Just pushed %d, numItems in stack = %d\n", myStack.currentStack[myStack.numItems-1], myStack.numItems);
	
	push(&myStack, 2);
	
	printf("Just pushed %d, numItems in stack = %d\n", myStack.currentStack[myStack.numItems-1], myStack.numItems);
	
	push(&myStack, 3);
	
	printf("Just pushed %d, numItems in stack = %d\n", myStack.currentStack[myStack.numItems-1], myStack.numItems);
	
	
	printf("Current Stack capacity = %d\n", myStack.capacity);
	printf("There are currently %d items in the Stack\n", getNumItems(&myStack));
	
	//Pop item from stack
	
	int justRemoved = pop(&myStack);
	
	printf("Just removed %d, there are now %d items in the Stack\n", justRemoved, getNumItems(&myStack));
	
	justRemoved = pop(&myStack);

	printf("Just removed %d, there are now %d items in the Stack\n", justRemoved, getNumItems(&myStack));
	
	free(myStack.currentStack);
}
//End of File