/*
	Node.java by Randyll Bearer 2018
	Node to be used in an implementation of a binary search tree
*/

public class Node{
	
	protected Node leftChild = null;
	protected Node rightChild = null;
	protected Node parent = null;
	protected int value = 0;
	
	//Constructor to be used
	Node(int desiredValue){
		value = desiredValue;
	}
	
	/*
	* Returns the leftChild
	*/
	public Node getLeftChild(){
		return leftChild;
	}
	
	/*
	* Returns the rightChild
	*/
	public Node getRightChild(){
		return rightChild;
	}
	
	/*
	* Returns the parent node
	*/
	public Node getParent(){
		return parent;
	}
	
	/*
	* Sets the parent node
	*/
	public void setParent(Node toSet){
		parent = toSet;
	}
	
	/*
	* Sets the leftChild
	*/
	public void setLeftChild(Node toSet){
		leftChild = toSet;
	}
	
	/*
	* Sets the rightChild
	*/
	public void setRightChild(Node toSet){
		rightChild = toSet;
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