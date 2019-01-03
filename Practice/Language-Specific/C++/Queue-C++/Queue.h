/*
*	Node.h by Randyll Bearer 2018
* 	This is a basic Queue implementation backed by a 
* 	doubly-linked list meant to be tested by Driver.cpp
*   Driver.cpp file.
*/

#include "Node.h"

class Queue{
public:
	Node* first;
	Node* last;
	int numElements;
	
	//Default Constructor
	Queue(){
		first = NULL;
		last = NULL;
		numElements = 0;
	}
	
	//Create a node with value toEnqueue and insert into queue at head
	int enqueue(int toEnqueue){
		Node *newNode = new Node(toEnqueue);
		
		if(first == NULL){	//If this is our first entry, update head
			first = newNode;
			last = newNode;
		}else{
			newNode->next = first;
			first->previous = newNode;
			first = newNode;
		}
		
		numElements = numElements + 1;
		return toEnqueue;
		
	}
	
	//Remove and return the tail-most node's value from the queue
	int dequeue(){
		int toReturn = -1;
		
		if(numElements == 0){	//If queue is empty, can't dequeue anything
			return toReturn = -1;
		}else{
			toReturn = last->value;
			
			Node* temp = last->previous;
			temp->next = NULL;
			delete(last);	//delete after new
			last = temp;
			
			numElements = numElements -1;
			return toReturn;
		}
		
	}
	
	//Returns True if numElements == 0, False otherwise
	int isEmpty(){
		if(numElements == 0){
			return true;
		}else{
			return false;
		}
	}
	
	//Print all elements in Queue
	void printElements(){
		
		std::cout << "Now printing all elements in the queue\n";
		
		Node* current = first;
		while(current != NULL){
			std::cout << "" << current->value << "\n";
			current = current->next;
		}
	
		return;

	}
	
	//We allocated new Nodes, so we have to delete them to avoid memory leaking
	void deleteQueue(){
		Node* current = first;
		Node* temp;
		while(current != NULL){
			temp = current->next;
			delete(current);
			current = temp;
		}
		
		
	}
	
	
};
//End of File