/*
	Driver.c by Randyll Bearer 2018
	Simple implementation of a dynamic Linked List data structure in C using Node structs
	This Driver.c is meant to test its corresponding LinkedList.c implementation.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Node.h"

int main(){
	
	//Initialize the Linked List
	printf("Initializing Linked List...");
	struct Node* first = NULL;	//We have NULL pointers, but don't have space allocated yet for them to point to
	struct Node* second = NULL;
	struct Node* third = NULL:
	
	first = (struct Node*)malloc(sizeof(struct Node));	//Now the pointers can point to an allocated/non-random space
	second = (struct Node*)malloc(sizeof(struct Node));
	third = (struct Node*)malloc(sizeof(struct Node));
	
	struct Node* current = first;	//This is the pointer we will use to traverse the linked list
	
	//Populate the Linked List
	printf("Populating the Linked List with values...");
	
	//Link first node to second node
	current->data = 1;		//set the data value of the node pointed to by current (first) to 1.
	current->next = second;	//Link the first node to the second node
	current->previous = NULL;	//This is the first node in the list, so there is no previous
	current->next->previous = current;	//Go ahead and link the next node back to this one.
	current = current->next;	//Traverse to the next node
	
	//Link second node to third node
	current->data = 2;
	current->next = third;
	current->next->previous = current;	//Go ahead and link next node back to current node
	current = current->next;
	
	//Link third node
	current->data = 3;
	current->next = NULL;	//We only have three nodes, this is end of the linked list
	current = first;	//point current back to start of Linked List
	
	//Print out the contents of the linked list in order
	printf("Printing out the contents of the Linked List...");
	printFromHere(current);
	
	
	
	

	
}
//End of File