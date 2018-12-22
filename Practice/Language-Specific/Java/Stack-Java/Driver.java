/*
	Driver.java by Randyll Bearer 2018
	Practice for basic Java coding implementing a basic Stack data structure
	To be used in conjuction with Stack.java
*/


public class Driver {
	
	//Main driver method for the program, is in charge of instance'ing a Stack, and testing it's methods
	public static void main(String[] args){
		//Introductory Greeting
		System.out.println("Hello, now testing Stack.java...");
		System.out.println("\n\n\n");
		
		//Initialize a new Stack
		System.out.println("Initializing a new Stack...");
		Stack stack = new Stack();
		System.out.println("Stack Initialized");
		System.out.println("\n\n\n");
		
		//Test Pushing to Stack
		System.out.println("Pushing 1 to Stack...");
		stack.push(1);
		System.out.println("1 pushed to Stack");
		System.out.println("Pushing 2 to Stack...");
		stack.push(2);
		System.out.println("2 pushed to Stack");
		System.out.println("Pushing 3 to Stack...");
		stack.push(3);
		System.out.println("3 pushed to Stack");
		System.out.println("\n\n\n");
		
		//Retrieve data about the Stack
		System.out.println("Retrieving current capacity of Stack...");
		System.out.println("Current Stack Capacity = " + stack.getCapacity());
		System.out.println("Retrieving current numItems of Stack...");
		System.out.println("Current numItems in Stack = " + stack.getNumItems());
		System.out.println("\n\n\n");
		
		//Pop 2 items from Stack
		System.out.println("Popping item from Stack...");
		System.out.println(stack.pop() + " popped from Stack");
		System.out.println("Popping item from Stack...");
		System.out.println(stack.pop() + " popped from Stack");
		System.out.println("\n\n\n");
		
		//Retrieve data about the Stack
		System.out.println("Retrieving current capacity of Stack...");
		System.out.println("Current Stack Capacity = " + stack.getCapacity());
		System.out.println("Retrieving current numItems of Stack...");
		System.out.println("Current numItems in Stack = " + stack.getNumItems());
		System.out.println("\n\n\n");
		
	}
	
	
}
//End of File