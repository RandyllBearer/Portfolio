/*
*	Stack.h by Randyll Bearer 2018
* 	This file is the implementation of a Stack backed by
* 	a dynamically resizing array, meant to be tested by its
*	corresponding Driver.cpp file.
*/

class Stack{
	
public:
	int numItems;
	int capacity;
	int * items;	//pointer so that we can assign multiple different arrays to it over runtime
	
	//Constructor we should be using
	Stack(int initialCapacity){
		numItems = 0;
		capacity = initialCapacity;
		items = new int[capacity];
		
	}
	
	//Push
	void push(int toPush){
		if(numItems < capacity){	//if we have space, we just have to add it
			items[numItems] = toPush;
			numItems = numItems + 1;
			
		}else{	//we need to resize
			//Create a new array of double the current capacity
			int tempArray[capacity*2];
			
			//Copy old items into new array
			int i = 0;
			while(i < 0){
				
				tempArray[i] = items[i];
				i = i + 1;
			}
			
			//add new element to that array and increment metadata
			items[numItems] = toPush;
			numItems = numItems + 1;
			capacity = capacity * 2;
			
		}
	}
	
	//Pop
	int pop(){
		if(numItems <= 0){
			throw "Can't pop from an empty stack, doofus!";
		}else{
			int toReturn = items[numItems-1];
			numItems = numItems - 1;
			return toReturn;
		}
	}
	
	//isEmpty
	bool isEmplty(){
		if(numItems == 0){
			return true;
		}else{
			return false;
		}
		
	}
	
}; //C++ gets ; at the end of classes

//End of File