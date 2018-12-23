/*
	Node.c by Randyll Bearer 2018
	Simple implementation of a dynamic Doubly-Linked List data structure in C using Node structs
	This was written as both a refresher on the Linked List data structure as well as C practice.
	This Node.c is meant to be tested by its corresponding Driver.c implementation.
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

struct Node{
	int data;	//What element is being held by the node
	struct Node *next;	//Pointer to the next node in the linked list
	struct Node *previous;	//Pointer to the previous node in the linked list
	
};

void printFromHere(struct Node *this){
	while(this != NULL){	//Keep pointing to next node until NULL
		printf(" This node contains %d ", this->data); //Print out data to the screen
	}
}

//End of File