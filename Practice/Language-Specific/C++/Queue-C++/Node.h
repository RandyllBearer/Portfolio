/*
*	Node.h by Randyll Bearer 2019
* 	This file is the implementation of a Node to be used in
* 	a doubly-linked list data structure to be used to implementat
* 	a queue.
*/

//Library Files
#include <iostream>

class Node{
public:
	Node* next;
	Node* previous;
	int value;
	
	//Constructor to set default value
	Node(int toSet){
		value = toSet;
		next = NULL;
		previous = NULL;
	}
	
};

//End of File