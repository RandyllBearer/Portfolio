/*
	Stack.c by Randyll Bearer 2018
	Simple implementation of a dynamically resizing Stack data structure in C as well as act as a refresher on pointers/passing by reference
	This Stack.c is meant to be tested by its corresponding Driver.c implementation.
*/

//Create an int Stack struct with capacity 50
struct Stack{
	int capacity;	//How many items this Stack CAN hold, if resize is needed simply double and copy over.
	int currentStack[];		//Dynamic memory int array
	int numItems;	//How many items are currently in the Stack.
};

void resizeStack(struct Stack *stack){
	int numToCopy = stack->numItems;	//How many items we need to copy to the new Stack
	stack->capacity = stack->capacity * 2;	//Double the capacity for the new Stack
	int = (Stack *)malloc(sizeof(Stack)+capacity*sizeof(int));	//Allocates memory in stack for 2 ints.
	
	int i = 0;
	while(i < stack->numItems){	//Copy all the old elements over
		newStack[i] = stack->currentStack[i];
		i = i+1;	//make sure to increment so we do not get stuck in an infinite loop
	}
	
	
	stack->currentStack = newStack;
	
}

void push(struct Stack *stack, int toPush){
	if(stack->numItems == stack->capacity){		//We need to resize.
		resizeStack(stack);
		stack->currentStack[stack->numItems] = toPush;
		stack->numItems = stack->numItems + 1;	//Decrement # of items in Stack
		
		
		
	}else{	//We do not need to resize
		stack->currentStack[stack->numItems] = toPush;
		stack->numItems = stack->numItems + 1;	//Decrement # of items in Stack
	}
}

int pop(struct Stack *stack){
	int toReturn = 0;
	
	if(stack->numItems == 0){
		//Throw stack is empty exception
	}else{ //We can safely pop from this stack
		int toReturn = stack->currentStack[stack->numItems - 1];
		stack->numItems = stack->numItems - 1;	//Decrement # of items in Stack
	}
	
	return toReturn;
}

int getNumItems(struct Stack *stack){
	int toReturn = stack->numItems;
	
	return toReturn;
}











//End of File