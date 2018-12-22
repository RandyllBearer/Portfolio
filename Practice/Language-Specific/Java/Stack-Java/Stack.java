/*
	Stack.java by Randyll Bearer 2018
	Practice for basic Java coding implementing a basic Stack data structure
	To be used in conjuction with Driver.java
*/

/*
	Dynamically-Resizing int Stack data structure, allows for Popping, Pushing, Size(), etc.
	Note that this implementation of Stack prioritizes memory optimization over time optimization 
	(i.e. resizing and copying array will sometimes slow this Stack down)
*/
public class Stack {
	
	private int capacity = 1;			//How many items can currently be fit in/on the stack.
	private int numItems = 0;			//How many items are in/on the stack
	private int[] array = new int[capacity];	//What will actually be holding the contents
	
	/*
	Pushes an item onto the stack
	Returns 0 if int cannot be pushed onto stack
	Returns int itemToPush value if success
	*/
	public int push(int itemToPush){
		if(numItems == capacity){
			resizeStack();
		}
		
		array[numItems] = itemToPush;	//Grab most recent item pushed onto the stack
		numItems = numItems + 1;		//Increment number of items on Stack
		
		return itemToPush;
	}
	
	/*
	Pops an item from the stack
	Throws EmptyStackException if no int can be popped from the stack
	Returns the last int pushed onto the stack if success
	*/
	public int pop(){
		if(numItems == 0){
			//throw new EmptyStackException;
		}
		
		int toReturn = array[numItems-1];	//Gets most recently pushed item
		numItems = numItems - 1;			//Decrement number of items on Stack
		
		return toReturn;
	}
	
	/*
	Creates new stack object of 2*capacity and copies over old values.
	Calls copyStack()
	*/
	private void resizeStack(){
		int newCapacity = 2*capacity;
		int[] newArray = new int[newCapacity];
		
		int i = 0;
		while(i < numItems){		//Copy contents of old array into new arracy
			newArray[i] = array[i];
			i =  i+1;
		}
		
		array = newArray;
		capacity = newCapacity;
	}
	
	/*
	Returns the current capacity of this Stack
	*/
	public int getCapacity(){
		return capacity;
	}
	
	/*
	Returns the current number of items in this Stack
	*/
	public int getNumItems(){
		return numItems;
	}
	
}
//End of File