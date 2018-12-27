/*
* Stack.js by Randyll Bearer 2018
* Simple implementation of a dynamically resizing Stack in Javascript for practice.
* To be tested by running its corresponding Driver.html
*/

//Declare the Stack class
class Stack{
	
	//Detail the fields for the class
	constructor(initialCapacity){
		this.numItems = 0;	//How many items are currently on the Stack
		this.capacity = initialCapacity;	//How many items can possibly fit on the Stack
		this.items = [initialCapacity];	//The actual array containing the elements of the Stack
	}
	
	//Methods
	push(toPush) {	//Push an element onto the Stack
		if(this.numItems < this.capacity){
			this.items[this.numItems] = toPush;
			this.numItems = this.numItems + 1;
			
		}else{	//We need to resize
			let tempArray = [this.capacity*2];
			
			//Copy old items into new array
			let i = 0;
			while(i < this.capacity){
				tempArray[i] = this.items[i];
				i = i + 1;
			}
			
			//Update new Capacity
			this.capacity = this.capacity * 2;
			
			//Push new item
			this.items[this.numItems] = toPush;
			this.numItems = this.numItems + 1;
			
			
		}
		return toPush;	//return the value we just pushed
	}
	
	pop(toPop){	//Remove top element from the Stack
		if(this.numItems <= 0){
			throw("Can't pop() from an empty Stack you doofus!");
			
		}else{
			let toReturn = this.items[this.numItems-1];
			this.numItems = this.numItems - 1;
			return toReturn;
			
		}
	}
	
	isEmpty(){	//Returns boolean true if this.numItems == 0, false if otherwise
		if(this.numItems == 0){
			return true;
		}else{
			return false;
		}
	}
	
}