/*
	Driver.java by Randyll Bearer 2018
	Node to be used in a basic doubly-linked list and tested by
	Driver.java
*/

public class Node{
	
	private Node next = null;
	private Node previous = null;
	private int value = 0;
	
	//Constructor to be used
	Node(int desiredValue){
		value = desiredValue;
	}
	
	/*
	* Prints the values of all nodes starting from this one
	*/
	public void printFromHere(Node current){
		
		System.out.println("Printing values of all nodes starting from node containing " + value);
		
		int i = 0;
		while(current != null){
			System.out.println("Node " + i + " contains value " + current.value);
			current = current.next;
			i = i + 1;
		}
		
	}
	
	/*
	* Returns the next Node
	*/
	public Node getNext(){
		return next;
	}
	
	/*
	* Sets the next Node
	*/
	public void setNext(Node toSet){
		next = toSet;
	}
	
	/*
	* Returns the previous Node
	*/
	public Node getPrevious(){
		return previous;
	}
	
	/*
	* Sets the previous Node
	*/
	public void setPrevious(Node toSet){
		previous = toSet;
	}
	
	/*
	* Returns the value of current node
	*/
	public int getValue(){
		return value;
	}
	
	/*
	* Sets the value
	*/
	public void setValue(int toSet){
		value = value;
	}
	
}
//End of File