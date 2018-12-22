import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
/*
* Author: Randyll Bearer
* Email: rlb97@pitt.edu
*
**/


public class pw_check {
	public static void main(String[] args){
		Scanner keyboard = new Scanner(System.in);
		DLBTrie allBad = new DLBTrie();				// BAD DLB
		
		if(args.length == 1 && (args[0].equals("-g") || args[0].equals("-G"))){	// Okay so we are going to need to read-in the provided dictionary.				
			//GENERATE BAD PASSWORDS
			pw_check instance = new pw_check();
			allBad = instance.generateBadPasswords(allBad);				//Non-static method / static BS
			
		}
	}
	
	
	//Will read in from the dictionary.txt and produce my_dictionary.txt (If file location not found return boolean / false)
	public DLBTrie generateBadPasswords(DLBTrie dlbToComplete){	//accepts allBad DLB
		//Where will we create my_dictionary.txt at?
		boolean generatedFlag = false;
		Scanner keyboard = new Scanner(System.in);	//Input from user
		Scanner dictionaryIn = null;				//Input from dictionary.txt
		PrintWriter dictionaryOut = null;			//Write to my_dictionary.txt
		StringBuilder[] allOptions = null;			//Holds all possible illegal words to be printed to my_dictionary.txt
		
		
		//Find out where dictionary.txt is located and where my_dictionary.txt is to be written to
		while(generatedFlag == false){
			System.out.println("Please enter the path for dictionary.txt: ");
			String filename = keyboard.nextLine();
			File dictionaryFile = new File(filename);
			
			try{
				dictionaryIn = new Scanner(dictionaryFile);
				generatedFlag = true;
			}catch(FileNotFoundException fnfe){
				System.out.println("\nSorry but nothing could be found at that file location...\n");
			}
			
			if(generatedFlag == true){	//Did we get the first file?
				boolean generatedFlag2 = false;
				while(generatedFlag2 == false){
					System.out.println("Please enter the desired file where my_dictionary.txt should be stored: ");
					filename = keyboard.nextLine() + "\\my_dictionary.txt";
					
					try{
						File myDictionaryLocation = new File(filename);
						dictionaryOut = new PrintWriter(myDictionaryLocation);
						generatedFlag2 = true;
					}catch(IOException ioe){
						System.out.println("\nA file could not be created there.\n");
					}
				}
			}
			
		}
		
		//Generate the BadPasswords DLB + my_dictionary.txt from dictionary.txt
		while(dictionaryIn.hasNextLine()){
			//Read-in word and convert it to characters in an array.
			String tempWord = dictionaryIn.nextLine();
			if(tempWord.equals(null)){				//End of txtfile
				break;
			}
			tempWord = tempWord.toLowerCase();
			int wordLength = tempWord.length();		//Used in future for loops.
			if(tempWord.length() <= 5){				//Only ones of importance.
				char[] characterArray = new char[tempWord.length()];
				StringBuilder baseWord = new StringBuilder(tempWord);
				baseWord.getChars(0,tempWord.length()-1,characterArray,0);					// Puts the characters from the string into the characterArray array.
				allOptions = findIllegalAlternatives(baseWord, wordLength, dictionaryOut, dlbToComplete);	// allOptions now holds all possible alternatives to the word from dictionary
			}
		}
		dictionaryOut.flush();
		dictionaryOut.close();
		return dlbToComplete;
	}
	

	
	//Takes in a dictionary word and creates an array of illegalAlternates + prints those to my_dictionary.txt
	public static StringBuilder[] findIllegalAlternatives(StringBuilder wordToCheck, int stringLength, PrintWriter toDictionary, DLBTrie badDLB){	//Will find all illegal alternatives due to substitutions, must be converted from StringBuilder to String before returning.
		StringBuilder baseWord = new StringBuilder().append(wordToCheck);
		StringBuilder[] wordArray = new StringBuilder[32];
		wordArray[0] = baseWord;
		int size = 1;
		
		//baseWord = our stringBuilder word from dictionary
		//stringLength = the length of our word from dictionary
		//wordArray = array holding all possible words
		//size = number of words in array
		
		for(int i = 0; i<stringLength; i++){
			for(int j = 0; j<size; j++){
				StringBuilder tempWord = new StringBuilder (wordArray[j]); //Now we have the first word in the array
				char tempChar = tempWord.charAt(i);
				
				if(tempChar == 'o'){
					tempWord.deleteCharAt(i);
					tempWord.insert(i, '0');
					wordArray[size] = tempWord;
					size +=1;
				}else if(tempChar == 'l'){
					tempWord.deleteCharAt(i);
					tempWord.insert(i, '1');
					wordArray[size] = tempWord;
					size +=1;
				}else if(tempChar == 't'){
					tempWord.deleteCharAt(i);
					tempWord.insert(i, '7');
					wordArray[size] = tempWord;
					size +=1;
				}else if(tempChar == 'a'){
					tempWord.deleteCharAt(i);
					tempWord.insert(i, '4');
					wordArray[size] = tempWord;
					size +=1;
				}else if(tempChar == 'e'){
					tempWord.deleteCharAt(i);
					tempWord.insert(i, '3');
					wordArray[size] = tempWord;
					size +=1;
				}else if(tempChar == 'i'){
					tempWord.deleteCharAt(i);
					tempWord.insert(i, '1');
					wordArray[size] = tempWord;
					size +=1;
				}else if(tempChar == 's'){
					tempWord.deleteCharAt(i);
					tempWord.insert(i, '$');
					wordArray[size] = tempWord;
					size +=1;
				}
			}
		}
		for(int i=0; i<size; i++){
			System.out.println(wordArray[i]);	//TEST
			toDictionary.println(wordArray[i]);
		}
		
		//CREATE DLB
		for(int i=0; i<size; i++){
			StringBuilder wordOption = new StringBuilder(wordArray[i]);	//Take the first option
			char[] characters = new char[stringLength];			//
			wordOption.getChars(0,stringLength,characters,0);	//Split it into characters [characters now holds each character in string, stringLength determines how many characters there are]
			
			for(int j = 0; j<=stringLength; j++){				//Maybe <= is not correct?
				DLBNode current = badDLB.getRoot();
				char currentChar = characters[j];
				if(current.getKey() == 0){	//If this is the very first time
					badDLB.setRootKey(currentChar);
				}
			}
			
		}
		
		return wordArray;
	}
	
	
	public class DLBNode{
		private char key =']';				// The letter being stored.
		private boolean endFlag = false;	// Is this the last letter in a word?
		private DLBNode next = null;		// Next horizontal option in LinkedList.
		private DLBNode child = null;		// Next vertical option in LinkedList.
		
		//CONSTRUCTORS
		public DLBNode(){
			endFlag = true;
		}
		
		public DLBNode(char newKey){		// This will be the most often used constructor.
			key = newKey;
		}
		
		public DLBNode(char newKey, boolean newEndFlag, DLBNode newNext, DLBNode newChild){
			key = newKey;
			endFlag = newEndFlag;
			next = newNext;
			child = newChild;
		}
		
		//GETTERS + SETTERS
		public void setKey(char newKey){
			key = newKey;
		}
		
		public void setEndFlag(boolean newEndFlag){
			endFlag = newEndFlag;
		}
		
		public void setNext(DLBNode newNext){
			next = newNext;
		}
		
		public void setChild(DLBNode newChild){
			child = newChild;
		}
		
		public char getKey(){
			return key;
		}
		
		public boolean getEndFlag(){
			return endFlag;
		}
		
		public DLBNode getNext(){
			return next;
		}
		
		public DLBNode getChild(){
			return child;
		}
	}
	
	/*
	* So the idea is, we are just navigating the "DLBNode current" down-and-to-the-right of the tree, until we find what we need or find a place we need to add to.
	*
	**/
	public static class DLBTrie{
		public DLBNode root;			// This is where we will start our inserts/searches.
		public DLBNode current = root;	// This is the node we are currently viewing in the DLBTrie
		public DLBNode next;			// This is the next node in the HORIZONTAL row.
		public DLBNode child;			// This is the next node in the VERTICAL column.
		public int nodeCount = 0;
		
		//CONSTRUCTORS
		public DLBTrie(){						// Should only really ever use this one.
			DLBNode root = null;
		}
		
		public DLBTrie(DLBNode newNode){
			root = newNode;
		}
		
		//METHODS
		public void setRootKey(char newRootKey){// Should only ever be ran once at the first insert of a new DLBTrie.
			root.setKey(newRootKey);
		}
		
		public void setNext(DLBNode newNext){	// Should only be called at the end of a HORIZONTAL linked list / Otherwise, you have not checked all possible existing nodes. Adds the new node to the end of the HORIZONTAL linked list.
			if(this.nodeCount == 0){
				root = newNext;
				nodeCount += 1;
			}else{
				current.setNext(newNext);
				nodeCount += 1;
			}
		}
		
		public void setChild(DLBNode newChild){	// Should only be called when a node has NO VERTICAL child / Otherwise, you should navigate to lower level and insert a new next node in linked list.  newChild then becomes the root of the next VERTICAL linked list.
			current.setChild(newChild);
			nodeCount += 1;
		}
		
		public void setEndFlag(boolean newFlag){// Should only be called when the node is the final character of the string.
			current.setEndFlag(newFlag);
		}
		
		public DLBNode getNext(){				// Returns the next HORIZONTAL node in the linked list.  If null, reached end of HORIZONTAL LinkedList.
			return current.getNext();
		}
		
		public DLBNode getChild(){				// Returns the root VERTICAL node in the linked list. If null, current node has no children.
			return current.getChild();
		}
		
		public boolean getEndFlag(){			// Returnes whether a node is or is not the final node in a word (NOTE: Does not necessarily mean that it has no children/next).
			return current.getEndFlag();
		
		}
		
		public DLBNode getRoot(){
			return root;
		}
		
		
	}
	
	
	
	
}