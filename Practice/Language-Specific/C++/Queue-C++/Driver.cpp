/*
*	Driver.cpp by Randyll Bearer 2018
* 	This file was written in order to test its corresponding 
*	Queue.h and Node.h.
*/

//Library Files
#include <iostream>

//Header Files
#include "Queue.h"

int main(){
	
	//Initialize the Queue with values 1,2,3
	Queue myQueue = Queue();
	myQueue.enqueue(1);
	myQueue.enqueue(2);
	myQueue.enqueue(3);
	myQueue.enqueue(4);
	myQueue.enqueue(5);
	
	//Test some metadata about the Queue
	std::cout << "There are " << myQueue.numElements << " elements in this queue\n";
	myQueue.printElements();
	
	//Dequeue some stuff
	int justRemoved = myQueue.dequeue();
	std::cout << "Just removed " << justRemoved << "\n";
	
	myQueue.printElements();
	
	//Okay we allocated a lot of new nodes, time to delete them
	myQueue.deleteQueue();
	
}
//End of File