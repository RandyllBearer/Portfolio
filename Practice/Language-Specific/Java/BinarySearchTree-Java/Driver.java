/*
	Driver.java by Randyll Bearer 2018
	Initializes a basic binary search tree and performs insertions, removals, and traversals
	NOTE: This bst assumes distinct keys/values
*/

public class Driver{
	
	//Inserts a new node into the BST (follows ordering scheme)
	public static Node insert(Node root, Node toInsert){
		if(root == null){	//Check for null root (This means we're making a new leaf)
			root = toInsert;
			return toInsert;
		}
		if(toInsert.getValue() < root.getValue()){			//Branch Left
			root.leftChild = insert(root.getLeftChild(), toInsert);
		}else if(toInsert.getValue() > root.getValue()){	//Branch Right
			root.rightChild = insert(root.getRightChild(), toInsert);
		}
		
		return root;
		
		
	}
	
	//Finds the node containing the desired value, removes it from the tree and returns it
	//3 possibilities
	//1. Node is leaf (simply delete it)
	//2. Node has one child (copy child to node and delete)
	//3. Node has two children (find inorder successor of the node and copy it to node and delete)
	public static Node remove(Node root, int valueToRemove){
		if(root == null){
			return root;
		}
		if(root.value == valueToRemove){	//Delete the node in one of the above 3 cases
			//Case 1 & 2
			if(root.leftChild == null){
				return root.rightChild;
			}else if(root.rightChild == null){
				return root.leftChild;
			}
			//Case 3
			int minimum = root.value;
			while(root.leftChild != null){
				minimum = root.leftChild.value;
				root = root.leftChild;
			}
			root.value = minimum;
			root.rightChild = remove(root.rightChild, root.value);
			
			
			
		}else if(valueToRemove < root.getValue()){
			root.leftChild = remove(root.leftChild, valueToRemove);
		}else{
			root.rightChild = remove(root.rightChild, valueToRemove);
		}
		
		return root;
		
	}
	
	//Prints in Pre-order traversal from argument node
	public static void printPreOrder(Node root){
		if(root != null){
			System.out.println(root.value);
			printPreOrder(root.leftChild);
			printPreOrder(root.rightChild);
		}
	}
	
	//Prints in in-order traversal from argument node
	public static void printInOrder(Node root){
		if(root != null){
			printInOrder(root.leftChild);
			System.out.println(root.value);
			printInOrder(root.rightChild);
		}
	}
	
	//Prints in post-order traversal from argument node
	public static void printPostOrder(Node root){
		if(root != null){
			printPostOrder(root.leftChild);
			printPostOrder(root.rightChild);
			System.out.println(root.value);
		}
	}
	
	//Main driver method
	public static void main(String args[]){
		
		//Initialize beginning tree structure found in treeDiagram.png
		System.out.println("Initializing default tree structure...");
		Node head = new Node(10);
		
		insert(head, new Node(6));
		insert(head, new Node(20));
		insert(head, new Node(4));
		insert(head, new Node(7));
		insert(head, new Node(15));
		insert(head, new Node(25));
		
		System.out.println("Tree structure initialized.");
		
		System.out.println("Initial Print Test...");
		Node current = head;
		while(current != null){
			System.out.println(current.value);
			current = current.leftChild;
		}
		
		//Test traversal methods
		System.out.println("\nTesting In Order Print from Root");
		printInOrder(head);
		
		System.out.println("\nTesting Preorder Print from Root");
		printPreOrder(head);
		
		System.out.println("\nTesting Postorder Print from Root");
		printPostOrder(head);
		
	}
	
}
//End of File