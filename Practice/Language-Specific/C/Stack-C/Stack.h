/*
	Stack.h by Randyll Bearer 2018
	Simple implementation of a dynamically resizing Stack data structure in C using an Array
	as well as act as a refresher on pointers/passing by reference
	This Stack.c is meant to be tested by its corresponding Driver.c implementation.
*/

#include <stdlib.h>
#include <stdio.h>

//Create an int Stack struct with capacity 50
struct Stack{
	int capacity;	//How many items this Stack CAN hold, should always match size of currentStack[]
	int numItems;	//How many items are currently in the Stack.
	int *currentStack;		//should be initialized as an int array
};

/*
* If we try to push an element onto the stack when the stack's numItems == capacity, we need to resize the Stack's array.
* We will do this by simply creating a new array of twice the size of the old array, and copying the old elements into it
*/
void resizeStack(struct Stack *stack){
	int numToCopy = stack->numItems;	//How many items we need to copy to the new Stack
	int newCapacity = stack->capacity * 2;	//this is how many items the new array can old.
	stack->capacity = newCapacity;
	//modified below
	stack->currentStack = realloc(stack->currentStack, newCapacity * sizeof(int));
	
	/*
	int i = 0;
	while(i < numToCopy){	//Copy all the old elements over
		newArray[i] = stack->currentStack[i];
		i = i+1;	//make sure to increment so we do not get stuck in an infinite loop
	}
	
	stack->currentStack = newArray;
	*/
}

/*
* Pushes a new element onto the top of the Stack
*/
void push(struct Stack *stack, int toPush){
	if(stack->numItems == stack->capacity){		//We need to resize.
		resizeStack(stack);
		stack->currentStack[stack->numItems] = toPush; //add new element onto the stack
		stack->numItems = stack->numItems + 1;	//Increment # of items in Stack
		
	}else{	//We do not need to resize
		stack->currentStack[stack->numItems] = toPush;
		stack->numItems = stack->numItems + 1;	//Increment # of items in Stack
	}
}

/*
* Pops an element off the top of the Stack
*/
int pop(struct Stack *stack){
	int toReturn = 0;
	
	if(stack->numItems == 0){
		printf("Hey Bozo, no pop'ing an empty stack, check your numItems next time.");
		exit(-1);	//whoopsie, can't pop off an empty stack
	}else{ //We can safely pop from this stack
		toReturn = stack->currentStack[stack->numItems - 1];
		stack->numItems = stack->numItems - 1;	//Decrement # of items in Stack
	}
	
	return toReturn;
}

int getNumItems(struct Stack *stack){
	int toReturn = stack->numItems;
	
	return toReturn;
}











//End of File