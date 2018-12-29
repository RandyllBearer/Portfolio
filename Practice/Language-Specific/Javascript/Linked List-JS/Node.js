/*
* Node.js by Randyll Bearer 2018
* Simple implementation of a node to be used in a doubly-linked list tested
* by Driver.html
*/

//Declare the Node Class
class Node{
	
	//Constructor we will mainly be using, initializes with value
	constructor(initialValue){
		this.element = initialValue;
		this.next = null;
		this.previous = null;
		
	}
	
	//Print the contents of all nodes starting from here
	printFromHere(toPrintFrom){
		console.log("Printing the values of all nodes from this one");
		
		//Traverse to end of linked list
		let i = 0;
		while(toPrintFrom != null){
			console.log(`Value of node ${i} = ${toPrintFrom.element}`);
			
			toPrintFrom = toPrintFrom.next;
			i = i + 1;
		}
		
	}
	
	
	
	
}
//End of File