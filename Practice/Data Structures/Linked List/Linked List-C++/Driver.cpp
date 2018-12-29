/*
*	Driver.cpp by Randyll Bearer 2018
* 	This file was written in order to test its corresponding Node.h implementation
*	in order to test a doubly-linked list data structure.
*/

//Library Files
#include <iostream>

//Header Files
#include "Node.h"

int main(){
	
	std::cout << "Initializing nodes and linked list\n";
	
	//Initialize Three Nodes
	Node first = Node(1);
	Node second = Node(2);
	Node third = Node(3);
	
	//Combine the Nodes into a linked list
	first.next = &second;
	second.next = &third;
	second.previous = &first;
	third.previous = &second;
	
	//Print all nodes
	std::cout << "Printing from first node...\n";
	first.printFromHere(&first);
	
}
//End of File