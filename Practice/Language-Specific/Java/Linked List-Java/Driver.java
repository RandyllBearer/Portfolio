/*
	Driver.java by Randyll Bearer 2018
	Initializes a basic linked list to test the Node.java implementation
*/

public class Driver{
	
	//Driver Method
	public static void main(String args[]){
		
		//Initialize Nodes
		System.out.println("Initializing Nodes...");
		
		Node first = new Node(1);
		Node second = new Node(2);
		Node third = new Node(3);
		
		//Link Nodes together in a list;
		System.out.println("Linking Nodes...");
		
		first.setNext(second);
		second.setNext(third);
		second.setPrevious(first);
		third.setPrevious(second);
		
		//Print out all elements in the Linked List
		first.printFromHere(first);
	
	}
	
}
//End of File