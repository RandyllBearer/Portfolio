/*
*	Node.h by Randyll Bearer 2018
* 	This file is the implementation of a Node to be used in
* 	a doubly-linked list data structure with the corresponding
*   Driver.cpp file.
*/

//Library Files
#include <iostream>

class Node{
public:
	Node* next;
	Node* previous;
	int element;
	
	//Constructor to set default value
	Node(int toSet){
		element = toSet;
		next = NULL;
		previous = NULL;
	}
	
	//Print elements from here
	void printFromHere(Node* current){
		
		std::cout << "Printing from node with value " << current->element << "\n";
		
		int i = 0;
		while(current != NULL){
			std::cout << "Node " << i << " value is " << current->element << "\n";
			current = current->next;
			i = i + 1;
		}

	}
	
	
};

//End of File