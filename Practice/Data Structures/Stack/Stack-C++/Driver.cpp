/*
*	Driver.cpp by Randyll Bearer 2018
* 	This file was written in order to test its corresponding Stach.h implementation
*	of a Stack backed by a dynamically-resizing array.
*/


//Library Files
#include <iostream>

//Header Files
#include "Stack.h"

int main(){
	
	//Initialize the Stack with capacity 3
	//ERROR
	Stack myStack = Stack(3);
	//ERROR
	
	//Push 3 elements onto the stack and test capacity/numItems
	myStack.push(1);
	myStack.push(2);
	myStack.push(3);
	std::cout << "Stack has capacity of " << myStack.capacity << " and is holding " << myStack.numItems << " currently.\n";
	
	//Push 1 more element onto the stack and test again
	myStack.push(4);
	std::cout << "Stack has capacity of " << myStack.capacity << " and is holding " << myStack.numItems << " currently.\n";
	
	//Pop an item from the stack and test again
	int popped = myStack.pop();
	std::cout << "Just popped " << popped << " from the stack. " << "Stack has capacity of " << myStack.capacity << " and is holding " << myStack.numItems << " currently.\n";
	
}
//End of File