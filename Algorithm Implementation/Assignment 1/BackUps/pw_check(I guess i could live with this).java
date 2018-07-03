import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
/*
* Author: Randyll Bearer
* Email: rlb97@pitt.edu
*
* The purpose of this program is to simulate password generation using the De La Briandais Trie symbol table.  It can be thought of as two distinct parts:
* 
* Part 1: "java pw_check.java -g": This part is in charge of creating and filling a DLB from a given dictionary.txt which contains words which CANNOT be found anywhere in a password.
* However, the words in dictionary.txt should also be checked for possible substitution characters ['l' = '1', 'o' = '0', etc...];  With all base words and substitutions found, the
* "badDLB" should then be filled.  The "badDLB" is used as a reference for the generation of a "goodDLB", which contains all possible USABLE passwords.  
*
* Part 2: "java pw_check": The second part is in charge of recieving a potential password from a user, looking it up in "goodDLB" to see if it is a possible option.  If it is, then
* the program should end.  If the given password is illegal, then 10 alternatives should be found through the traversal of the "goodDLB".
**/
public class pw_check {
	static String[] masterArray = {"LLLNS","LLLSN","LLNLS","LLNSL","LLSLN","LLSNL","LNLLS","LNLSL","LNSLL","LSLLN","LSLNL","LSNLL","NLLLS","NLLSL","NLSLL","NSLLL","SLLLN","SLLNL","SLNLL","SNLLL","LLNNS","LLNSN","LLSNN","LNLNS","LNLSN","LNNLS","LNNSL","LNSLN","LNSNL","LSLNN","LSNLN","LSNNL","NLLNS","NLLSN","NLNLS","NLNSL","NLSLN","NLSNL","NNLLS","NNLSL","NNSLL","NSLLN","NSLNL","NSNLL","SLLNN","SLNLN","SLNNL","SNLLN","SNLNL","SNNLL","LLNSS","LLSNS","LLSSN","LNLSS","LNSLS","LNSSL","LSLNS","LSLSN","LSNLS","LSNSL","LSSLN","LSSNL","NLLSS","NLSLS","NLSSL","NSLLS","NSLSL","NSSLL","SLLNS","SLLSN","SLNLS","SLNSL","SLSLN","SLSNL","SNLLS","SNLSL","SNSLL","SSLLN","SSLNL","SSNLL","LNNSS","LNSNS","LNSSN","LSNNS","LSNSN","LSSNN","NLNSS","NLSNS","NLSSN","NNLSS","NNSLS","NNSSL","NSLNS","NSLSN","NSNLS","NSNSL","NSSLN","NSSNL","SLNNS","SLNSN","SLSNNA","SNLNS","SNLSN","SNNLS","SNNSL","SNSLN","SNSNL","SSLNN","SSNLN","SSNNL"}; //SIZE 110
	static char[] letterArray = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};	//SIZE 26
	static char[] numberArray = {'0','1','2','3','4','5','6','7','8','9'};																	//SIZE 10
	static char[] symbolArray = {'!', '@', '$', '^', '_', '*'};																				//SIZE 6
	static String[] tenAlternatives = new String[10];
	static int alternativesAmount = 0;
	
	public static void main(String[] args){
		pw_check instance = new pw_check();				//Static - Non-Static reference solution
		Scanner keyboard = new Scanner(System.in);
		DLBTrie allBad = instance.new DLBTrie();		// BAD DLB
		DLBTrie allGood = instance.new DLBTrie();		// GOOD DLB
		
		//GENERATE my_dictionary.txt + good_passwords.txt
		if(args.length == 1 && (args[0].equals("-g") || args[0].equals("-G"))){	// If the "-g" cmdLine argument is passed		
			
			//GENERATE BAD PASSWORDS
			allBad = instance.generateBadPasswords(allBad);	
			
			//FIND OUT WHERE TO STORE good_passwords.txt
			PrintWriter goodOut = null;
			while(true){
				try{
					File goodPasswords = new File(".\\good_passwords.txt");
					goodOut = new PrintWriter(goodPasswords);
					break;
				}catch(IOException ioe){
					System.out.println("ERROR: good_passwords.txt could not be created current directory");
				}
			}
			
			//GENERATE GOOD PASSWORDS
			System.out.println("GENERATION STARTED...");	//TEST
			for(int i=0; i<110; i++){						//Iterate through masterArray
				StringBuilder masterPattern = new StringBuilder(masterArray[i]);//HOLDS STRING PATTERN "LLLNS"
				char[] masterCharacters = new char[5];
				masterPattern.getChars(0,5,masterCharacters,0);					//HOLDS CHAR PATTERN 'L','L','L','N','S'
				int masterPatternIndex = 0;										// LOCATION IN CHAR PATTERN [Look up if L/N/S]
				
				instance.findPossible(masterCharacters, masterPatternIndex, "", allBad, goodOut);
			}
			goodOut.flush();
			goodOut.close();
			System.out.println("GENERATION COMPLETE!");		//TEST
			
		}else if(args.length == 0){		//Take in password and check if good / if not, provide alternatives
			File goodPW = new File(".\\good_passwords.txt");
			Scanner goodPasswordsIn = null;	
			if(goodPW.exists()){
				
				//CREATE THE GOOD DLB
				try{
					goodPasswordsIn = new Scanner(goodPW);
				}catch(FileNotFoundException fnfe){
					System.out.println("\nERROR: Could not find good_passwords.txt");
				}
				System.out.println("LOADING: PLEASE BE PATIENT...");
				allGood = generateGoodDLB(goodPasswordsIn, allGood);
				System.out.println("LOADING COMPLETE!");
				
				//ASK FOR + CHECK PASSWORDS
				while(true){
					System.out.print("\nPlease enter your desired password [Or type 'stop' to terminate]: ");
					String desiredPassword = keyboard.nextLine();
					if(desiredPassword.equals("stop") || (desiredPassword.equals("Stop") || desiredPassword.equals("STOP"))){
						System.out.println("PROGRAM CLOSING");
					}
					System.out.println("DETERMINING VALIDITY OF PASSWORD...");
					if(desiredPassword.length() == 5){
						if(checkValidity(desiredPassword, allGood) == true){
							System.out.println("\nTHIS PASSWORD IS VALID! Congratulations on your most wise choice!");
						}else{

						}
					}else{
						System.out.println("ERROR: Password must be 5 characters long...");
					}
					
				}	
			}else{
				System.out.println("\nERROR: good_passwords.txt COULD NOT BE FOUND...	[Please run 'java pw_check -g' first!]");
			}
			
		}else{
			System.out.println("\nERROR: Could not understand cmdLine arguments...");
		}
	}
	
	/**
	* Fully handles the creation of all unusable passwords, the "filling" of "badDLB", and the printing of bad passwords to file.
	* 
	* @param dlbToComplete: The "badDLB" which will contain all unusable passwords.
	* @return: dlbToComplete: The updated version which contains all unusable passwords.
	**/
	public DLBTrie generateBadPasswords(DLBTrie dlbToComplete){	
		//Where will we create my_dictionary.txt at?
		boolean generatedFlag = false;
		Scanner keyboard = new Scanner(System.in);	//Input from user
		Scanner dictionaryIn = null;				//Input from dictionary.txt
		PrintWriter dictionaryOut = null;			//Write to my_dictionary.txt
		StringBuilder[] allOptions = null;			//Holds all possible base word + alternatives to be printed to my_dictionary.txt
		
		
		//Find out where dictionary.txt is located and where my_dictionary.txt is to be written to
		while(generatedFlag == false){
			String filename = (".\\" + "dictionary.txt");
			File dictionaryFile = new File(filename);
			
			try{
				dictionaryIn = new Scanner(dictionaryFile);
				generatedFlag = true;
			}catch(FileNotFoundException fnfe){
				System.out.println("\nERROR: dictionary.txt Could not be found in directory...\n");
			}
			
			if(generatedFlag == true){
				boolean generatedFlag2 = false;
				while(generatedFlag2 == false){
					filename = ("." + "\\my_dictionary.txt");
					
					try{
						File myDictionaryLocation = new File(filename);
						dictionaryOut = new PrintWriter(myDictionaryLocation);
						generatedFlag2 = true;
					}catch(IOException ioe){
						System.out.println("\nERRORL my_dictionary.txt Could not be created in directory...\n");
					}
				}
			}
		}
		
		//Read in word from dictionary.txt, convert to lowercase, send to findIllegalAlternatives() to find alternate substitutions
		while(dictionaryIn.hasNextLine()){
			String tempWord = dictionaryIn.nextLine();
			if(tempWord.equals(null)){				//End of txtfile
				break;
			}
			tempWord = tempWord.toLowerCase();
			int wordLength = tempWord.length();		//Used in future for loops.
			if(tempWord.length() <= 5){				//Disregard any word larger than max password size.
				char[] characterArray = new char[tempWord.length()];
				StringBuilder baseWord = new StringBuilder(tempWord);
				baseWord.getChars(0,tempWord.length()-1,characterArray,0);									// Puts the characters from the string into the characterArray array.
				allOptions = findIllegalAlternatives(baseWord, wordLength, dictionaryOut, dlbToComplete);	// allOptions now holds all possible alternatives to the word from dictionary
			}
		}
		dictionaryOut.flush();
		dictionaryOut.close();
		return dlbToComplete;
	}
	
	/**
	*Takes in a dictionary word and creates an array of illegalAlternates + prints those to my_dictionary.txt
	*
	*@param wordToCheck: Word from dictionary.txt to be checked for possible alternatives and placed into "badDLB".
	*@param stringLength: How long wordToCheck is.
	*@param toDictionary: PrintWriter to print wordToCheck and its possible alternatives to my_dictionary.txt
	*@param badDLB: De La BrianDais Trie holding all illegal passwords.
	*@return: An array holding all possible alternatives to worrdToCheck.
	**/
	public static StringBuilder[] findIllegalAlternatives(StringBuilder wordToCheck, int stringLength, PrintWriter toDictionary, DLBTrie badDLB){
		StringBuilder baseWord = new StringBuilder().append(wordToCheck);
		StringBuilder[] wordArray = new StringBuilder[32];
		wordArray[0] = baseWord;
		int size = 1;
		
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
			toDictionary.println(wordArray[i]);
		}
		
		//CREATE DLB
		for(int i=0; i<size; i++){										//Iterate through wordArray which contains alternateOptions
			StringBuilder wordOption = new StringBuilder(wordArray[i]);	//Take the first option
			char[] characters = new char[stringLength];			
			wordOption.getChars(0,stringLength,characters,0);	
			
			DLBNode current = badDLB.getRoot();
			for(int j = 0; j<stringLength; j++){				///Iterate through each character in a word
				char currentChar = characters[j];
				if(badDLB.getRootKey() == ']' ){				//If this is the very first time
					badDLB.setRootKey(currentChar);				//So the root node now has a key
					if(j == stringLength - 1){					//If our word is one letter long
						badDLB.getRoot().setEndFlag(true);
						badDLB.incrementWords();			//TEST
					}else if(current.getChild() == null){
						current.newChild();
						badDLB.incrementCount();
						current = current.getChild();
					}else{
						current = current.getChild();
					}
					
				}else{
					while(true){								//Traverse the DLB
						if(current.getKey() == ']'){			//If we are starting on a fresh child node
							current.setKey(currentChar);
							if(j== stringLength -1){			//If this is our last word update and break out
								current.setEndFlag(true);
								badDLB.incrementWords();	//TEST
								break;
							}
							current.newChild();
							current = current.getChild();		//Update current and move down to next level
							badDLB.incrementCount();		//TEST
							break;
						}else if(current.getKey() == currentChar){	//So we found our character
							if(j==stringLength-1){					//We are at the end of our word
								current.setEndFlag(true);
								badDLB.incrementWords();	//TEST
								break;
							}
							if(current.getChild() == null){			//If we currently have no child
								current.newChild();
								current = current.getChild();
								badDLB.incrementCount();	//TEST
								break;
							}else{
								current = current.getChild();
								break;
							}
						}else if(current.getNext() == null){		// if our next node is null, then we have to add a new one + update it + make a child + make current = new child
							current.newNext();						// CREATE
							badDLB.incrementCount();		//TEST
							current = current.getNext();
							current.setKey(currentChar);			// UPDATE
							if(j == stringLength-1){				//If this is the final node, don't bother with making a new child
								current.setEndFlag(true);
								badDLB.incrementWords();	//TEST
								break;
							}
							current.newChild();						// MAKE CHILD
							current = current.getChild();			// MAKE CHILD CURRENT
							badDLB.incrementCount();		//TEST
							break;
						}else{
							current = current.getNext();
						}
					}
				}	
			}	
		}
		return wordArray;
	}
	
	/**
	*Fills out the "goodDLB" from the words located inside of good_passwords.txt. [Very similar to generate "badDLB"]
	*
	*@param goodIn: A scanner to read from good_passwords.txt.
	*@param goodPasswordTrie: The "goodDLB" to be filled from good_passwords.txt.
	*@return: A filled out "goodDLB".
	**/
	public static DLBTrie generateGoodDLB(Scanner goodIn, DLBTrie goodPasswordTrie){
		char[] characters = new char[5];
		
		while(goodIn.hasNextLine()){	//Hopefully this does not consume the token
			StringBuilder wordToAdd = new StringBuilder(goodIn.nextLine());
			wordToAdd.getChars(0,5,characters,0);
			
			DLBNode current = goodPasswordTrie.getRoot();
			for(int i = 0; i<5; i++){
				char currentCharacter = characters[i];
				if(goodPasswordTrie.getRootKey() == ']'){	//If this is the very first time
					goodPasswordTrie.setRootKey(currentCharacter);
					goodPasswordTrie.incrementCount();		//TEST
					if(current.getChild() == null){
						current.newChild();
						goodPasswordTrie.incrementCount();
						current = current.getChild();
					}else{
						current = current.getChild();
					}
					
				}else{
					while(true){
						if(current.getKey() == ']'){			//If we are starting on a fresh child node
							current.setKey(currentCharacter);
							if(i == 4){			//If this is our last word update and break out
								current.setEndFlag(true);
								goodPasswordTrie.incrementWords();	//TEST
								break;
							}
							current.newChild();
							current = current.getChild();		//Update current and move down to next level
							goodPasswordTrie.incrementCount();		//TEST
							break;
						}else if(current.getKey() == currentCharacter){	//So we found our character
							if(i == 4){					//We are at the end of our word
								current.setEndFlag(true);
								goodPasswordTrie.incrementWords();	//TEST
								break;
							}
							if(current.getChild() == null){			//If we currently have no child
								current.newChild();
								current = current.getChild();
								goodPasswordTrie.incrementCount();	//TEST
								break;
							}else{
								current = current.getChild();
								break;
							}
						}else if(current.getNext() == null){		// if our next node is null, then we have to add a new one + update it + make a child + make current = new child
							current.newNext();						// CREATE
							goodPasswordTrie.incrementCount();		//TEST
							current = current.getNext();
							current.setKey(currentCharacter);			// UPDATE
							if(i == 4){				//If this is the final node, don't bother with making a new child
								current.setEndFlag(true);
								goodPasswordTrie.incrementWords();	//TEST
								break;
							}
							current.newChild();						// MAKE CHILD
							current = current.getChild();			// MAKE CHILD CURRENT
							goodPasswordTrie.incrementCount();		//TEST
							break;
						}else{
							current = current.getNext();
						}
					}
				}
				
			}
			
		}
		//System.out.println("NODE COUNT: " + goodPasswordTrie.getCount());		//TEST
		//System.out.println("WORD COUNT: " + goodPasswordTrie.getWordCount());	//TEST
		return goodPasswordTrie;
	}
	
	/**
	*Takes a string and checks to see if it is a valid/possible password.
	*
	*@param password: String to be checked.
	*@param goodPasswordTrie: DLBTrie which contains all possible good passwords.
	*@return: If TRUE: password is legal		// FALSE: password is illegal.
	**/
	public static boolean checkValidity(String password, DLBTrie goodPasswordTrie){
		char characters[] = new char[5];
		StringBuilder tempWord = new StringBuilder(password);
		tempWord.getChars(0,5,characters,0);
		
		DLBNode current = goodPasswordTrie.getRoot();
		DLBNode previous = goodPasswordTrie.getRoot();
		String prefix = "";
		for(int i=0; i<5; i++){
			char currentCharacter = characters[i];
			while(true){
				if(current.getKey() == currentCharacter){
					if(i == 4){
						return true;
					}
					if(current.getChild() != null){
						current = current.getChild();
						previous = current;
						break;
					}else{
						System.out.println("PASSWORD INVALID / SHOWING ALTERNATIVES: ");
						findPrefix(current,previous, prefix,i);
						return false;
					}
				}else if(current.getKey() != currentCharacter){
					if(current.getNext() == null){
						System.out.println("PASSWORD INVALID / SHOWING ALTERNATIVES: ");
						findPrefix(current,previous, prefix,i);
						return false;
					}else{
						current = current.getNext();
					}
				}
			}
			prefix = prefix + currentCharacter;
		}
		return true;
	}
	
	/**
	*
	*
	*
	*
	**/
	public static void findPrefix(DLBNode currentNode, DLBNode levelRoot, String currentPrefix, int index){
		boolean duplicateFlag = false;	//WE HAVE NOT ENCOUNTERED THE DUPLICATE
		DLBNode safetyRoot = null;
		String safetyPrefix = null;
		
		while(alternativesAmount <= 10 && levelRoot.getNext() != null){
			if(index == 0){
				if(levelRoot.getKey() == currentNode.getKey() && duplicateFlag == false){
					levelRoot = levelRoot.getNext();
					duplicateFlag = true;
				}
				levelRoot = levelRoot.getChild();
				currentPrefix = currentPrefix + levelRoot.getKey();
				index += 1;
				System.out.println("MADE IT TO LEVEL 0");//TEST
			}else if(index == 1){
				if(levelRoot.getKey() == currentNode.getKey() && duplicateFlag == false){
					levelRoot = levelRoot.getNext();
					duplicateFlag = true;
				}
				levelRoot = levelRoot.getChild();
				currentPrefix = currentPrefix + levelRoot.getKey();
				index += 1;
				System.out.println("MADE IT TO LEVEL 1");	//TEST
			}else if(index == 2){
				if(levelRoot.getKey() == currentNode.getKey() && duplicateFlag == false){
					levelRoot = levelRoot.getNext();
					duplicateFlag = true;
				}
				levelRoot = levelRoot.getChild();
				currentPrefix = currentPrefix + levelRoot.getKey();
				index += 1; 
				System.out.println("MADE IT TO LEVEL 2"); //TEST
			}else if(index == 3){
				System.out.println("MADE IT TO LEVEL 3");	//TEST
				if(levelRoot.getKey() == currentNode.getKey() && duplicateFlag == false){
					levelRoot = levelRoot.getNext();
					duplicateFlag = true;
				}
				if(levelRoot.getNext() != null){
					safetyRoot = levelRoot.getNext();
				}
				if(safetyRoot.getKey() == currentNode.getKey() && duplicateFlag == false){
					if(safetyRoot.getNext() != null){
						safetyRoot = safetyRoot.getNext();
					}
				}
				if(safetyRoot != null && safetyRoot.getChild() != null){
					safetyRoot = safetyRoot.getChild();
					safetyPrefix = currentPrefix + safetyRoot.getKey();
				}
				levelRoot = levelRoot.getChild();
				currentPrefix = currentPrefix + levelRoot.getKey();
				index +=1;
			}else if(index == 4 && safetyRoot == null){
				System.out.println("MADE IT TO LEVEL 4 without safetyRoot");	//TEST
				if(levelRoot.getKey() == currentNode.getKey() && duplicateFlag == false){
					levelRoot = levelRoot.getNext();
				}
				while(levelRoot.getNext() != null && alternativesAmount <= 10){
					String finalPrefix = currentPrefix + levelRoot.getKey();
					System.out.println("ALTERNATIVE: " + finalPrefix);
					levelRoot = levelRoot.getNext();
					alternativesAmount +=1;
				}
			}else if(index == 4 && safetyRoot != null){
				System.out.println("MADE IT TO LEVEL 4 with safetyRoot");
				if(levelRoot.getKey() == currentNode.getKey() && duplicateFlag == false){
					levelRoot = levelRoot.getNext();
				}else if(safetyRoot.getKey() == currentNode.getKey() && duplicateFlag == false){
					safetyRoot = safetyRoot.getNext();
				}
				while(levelRoot.getNext() != null && alternativesAmount <= 10){
					String finalPrefix = currentPrefix + levelRoot.getKey();
					System.out.println("ALTERNATIVE: " + finalPrefix);
					levelRoot = levelRoot.getNext();
					alternativesAmount +=1;
				}
				while(safetyRoot.getNext() != null && alternativesAmount <= 10){
					String finalPrefix = safetyPrefix + safetyRoot.getKey();
					System.out.println("ALTERNATIVE: " + finalPrefix);
					safetyRoot = safetyRoot.getNext();
					alternativesAmount +=1;
				}
				
			}
		}

		
		
		
	}
	
	/**
	*A recursive method used to find all possible legal password permutations.  Will be called for each of the 110 masterPatterns.  
	*Works on a simple basis of adding one character at a time, iterating through all possible characters for that index (while checking if illegal)
	*then adding a new character and repeating.
	*
	*@param pattern: An array holding variations of "L,L,L,N,S" used to determine all possible passwords for a given permutation of the password pattern.
	*@param patternIndex: Which character we are currently concerned with in pattern[].
	*@param prefix: the partial recursive solution found so far, will become a full password if not illegal.
	*@param badDLBTrie: The "badDLB" used to check whether the partialSolution prefix should be continued or not.
	*@param goodPasswordsOut: Used to print good passwords out to good_passwords.txt.
	**/
	public void findPossible(char[] pattern, int patternIndex, String prefix, DLBTrie badDLBTrie, PrintWriter goodPasswordsOut){
		if(patternIndex == 5){						//Recursive Base Case
			if(lookupPossible(prefix, badDLBTrie) == false){
				return;
			}
			goodPasswordsOut.println(prefix);		//PRINT IT OUT TO good_passwords.txt
			return;
		}else if(pattern[patternIndex] == 'L'){		//Do we need to increment through the letter array?
			for(int i=0; i<26; i++){
				String newPrefix = prefix + letterArray[i];
				if(lookupPossible(newPrefix, badDLBTrie) == true){	//FALSE = BAD PASSWORD CANNOT USE
					findPossible(pattern, (patternIndex+1), newPrefix, badDLBTrie, goodPasswordsOut);
				}
			}
		}else if(pattern[patternIndex] == 'N'){		//Do we need to increment through the number array?
			for(int j=0; j<10; j++){
				String newPrefix = prefix + numberArray[j];
				if(lookupPossible(newPrefix, badDLBTrie) == true){
					findPossible(pattern, (patternIndex+1), newPrefix, badDLBTrie, goodPasswordsOut);
				}
			}
		}else if(pattern[patternIndex] == 'S'){		//Do we need to increment through the symbol array?
			for(int k=0; k<6; k++){
				String newPrefix = prefix + symbolArray[k];
				if(lookupPossible(newPrefix, badDLBTrie) == true){
					findPossible(pattern, (patternIndex+1), newPrefix, badDLBTrie, goodPasswordsOut);
				}
			}
		}
	}
	
	/**
	*Just an intermediary step to avoid readability issues in findPossible(). Handles determining whether a partialSolution prefixToCheck is valid or invalid. 
	*
	*@param prefixToCheck; partialSolution passed from findPossible(), is checked to see if it is legal or illegal.
	*@param badTrie: the "badDLB", is used to reference whether prefixToCheck is a bad password.
	*@return result: If TRUE: prefixToCheck is legal so far    // FALSE: prefixToCheck is an illegal combination
	**/
	public boolean lookupPossible(String prefixToCheck, DLBTrie badTrie){
		boolean result = true;	//DEFAULT = TRUE = GOOD PASSWORD
		int prefixLength = prefixToCheck.length();
		
		if(prefixLength == 1){
			result = lookupPossible1(prefixToCheck, badTrie);
		}else if(prefixLength == 2){
			result = lookupPossible2(prefixToCheck, badTrie);
		}else if(prefixLength == 3){
			result = lookupPossible3(prefixToCheck, badTrie);
		}else if(prefixLength == 4){
			result = lookupPossible4(prefixToCheck, badTrie);
		}else if(prefixLength == 5){
			result = lookupPossible5(prefixToCheck, badTrie);
		}
		
		return result;
	}
	
	/**
	*Is used to determine whether a 1 character long prefix is legal or illegal
	*
	*@param prefixToLookup: The partialSolution to be checked.
	*@param badPasswordTrie: The "badDLB" used to reference whether prefixToLookup is legal or illegal.
	*@return: If TRUE: prefixToLookup is legal so far 	// FALSE: prefixToLookup is illegal.
	**/
	public boolean lookupPossible1(String prefixToLookup, DLBTrie badPasswordTrie){	
		StringBuilder temp = new StringBuilder(prefixToLookup);
		char character = temp.charAt(0);
		DLBNode current = badPasswordTrie.getRoot();
		
		//Lookup 1
		while(true){	//CHECK 1
			if(current.getKey() == character && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == character && current.getEndFlag() == false){
				return true;
			}else if(current.getNext() == null){
				return true;	//If the character wasn't found in bad passwords, it must be good password
			}else{
				current = current.getNext();
			}
		}
	}
	
	/**
	*Is used to determine whether a 2 character long prefix is legal or illegal, runs through all
	*relevant permutations [In this case, 2:1,2] not already checked by previous lookupPossible#'s
	*
	*@param prefixToLookup: The partialSolution to be checked.
	*@param badPasswordTrie: The "badDLB" used to reference whether prefixToLookup is legal or illegal.
	*@return: if TRUE: prefixToLookup is legal so far	// FALSE: prefixToLookup is illegal.
	**/
	public boolean lookupPossible2(String prefixToLookup, DLBTrie badPasswordTrie){	//COMPLETE IN THEORY
		boolean result = true;
		StringBuilder temp = new StringBuilder(prefixToLookup);
		DLBNode current = badPasswordTrie.getRoot();
		
		//Lookup 2
		char currentCharacter = temp.charAt(1);
		while(true){	//CHECK 2
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				break;
			}else if(current.getNext() == null){
				break;
			}else{
				current = current.getNext();
			}
		}
		
		//LOOKUP 1,2
		currentCharacter = temp.charAt(0);
		char currentCharacter2 = temp.charAt(1);
		current = badPasswordTrie.getRoot();
		while(true){	//CHECK 1
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				if(current.getChild() == null){
					return true;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}
		while(true){	//CHECK 2
			if(current.getKey() == currentCharacter2 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter2 && current.getEndFlag() == false){
				return true;
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}
	}
	
	/**
	*Is used to determine whether a 3 character long prefix is legal or illegal, runs through all
	*relevant permutations [In this case, 3:2,3:1,2,3] not already checked by previous lookupPossible#'s
	*
	*@param prefixToLookup: The partialSolution to be checked.
	*@param badPasswordTrie: The "badDLB" used to reference whether prefixToLookup is legal or illegal.
	*@return: if TRUE: prefixToLookup is legal so far	// FALSE: prefixToLookup is illegal.
	**/
	public boolean lookupPossible3(String prefixToLookup, DLBTrie badPasswordTrie){	
		boolean result = true;
		StringBuilder temp = new StringBuilder(prefixToLookup);
		DLBNode current = badPasswordTrie.getRoot();
		boolean flag = true;
		
		//LOOKUP 3
		char currentCharacter = temp.charAt(2);
		while(true){	//CHECK 3
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				break;
			}else if(current.getNext() == null){
				break;
			}else{
				current = current.getNext();
			}
		}
		
		//LOOKUP 2,3
		currentCharacter = temp.charAt(1);
		char currentCharacter2 = temp.charAt(2);
		current = badPasswordTrie.getRoot();
		while(true){	// CHECK 2
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				if(current.getChild() == null){
					flag = false;
					break;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				flag = false;
				break;
			}else{
				current = current.getNext();
			}
		}
		while(flag == true){	//CHECK 3
			if(current.getKey() == currentCharacter2 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter2 && current.getEndFlag() == false){
				break;
			}else if(current.getNext() == null){
				break;
			}else{
				current = current.getNext();
			}
		}
		
		//LOOKUP 1,2,3
		currentCharacter = temp.charAt(0);
		currentCharacter2 = temp.charAt(1);
		char currentCharacter3 = temp.charAt(2);
		current = badPasswordTrie.getRoot();
		while(true){	//CHECK 1
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				if(current.getChild() == null){
					return true;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}
		while(true){	//CHECK 2
			if(current.getKey() == currentCharacter2 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter2 && current.getEndFlag() == false){
				if(current.getChild() == null){
					return true;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}while(true){	//CHECK 3
			if(current.getKey() == currentCharacter3 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter3 && current.getEndFlag() == false){
				return true;
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}
	}
	
	/**
	*Is used to determine whether a 4 character long prefix is legal or illegal, runs through all
	*relevant permutations [In this case, 4:3,4:2,3,4:1,2,3,4] not already checked by previous lookupPossible#'s
	*
	*@param prefixToLookup: The partialSolution to be checked.
	*@param badPasswordTrie: The "badDLB" used to reference whether prefixToLookup is legal or illegal.
	*@return: if TRUE: prefixToLookup is legal so far	// FALSE: prefixToLookup is illegal.
	**/
	public boolean lookupPossible4(String prefixToLookup, DLBTrie badPasswordTrie){	
		boolean result = true;
		StringBuilder temp = new StringBuilder(prefixToLookup);
		DLBNode current = badPasswordTrie.getRoot();
		boolean flag = true;
		
		//LOOKUP 4
		char currentCharacter = temp.charAt(3);
		while(true){	//CHECK 4
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				break;
			}else if(current.getNext() == null){
				break;
			}else{
				current = current.getNext();
			}
		}
		
		//LOOKUP 3,4
		currentCharacter = temp.charAt(2);
		char currentCharacter2 = temp.charAt(3);
		current = badPasswordTrie.getRoot();
		while(true){	// CHECK 3
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				if(current.getChild() == null){
					flag = false;	//So 3,4 cannot be a bad password, but somewhere down the line may be
					break;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				flag = false; 		//If 3 cannot be found, then 3/4 cannot be a bad password, move onto next check
				break;
			}else{
				current = current.getNext();
			}
		}
		while(flag = true){	//CHECK 4
			if(current.getKey() == currentCharacter2 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter2 && current.getEndFlag() == false){
				break;
			}else if(current.getNext() == null){
				break;
			}else{
				current = current.getNext();
			}
		}
		
		//LOOKUP 2,3,4
		currentCharacter = temp.charAt(1);
		currentCharacter2 = temp.charAt(2);
		char currentCharacter3 = temp.charAt(3);
		current = badPasswordTrie.getRoot();
		flag = true;
		boolean flag2 = true;
		while(true){	//CHECK 2
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				if(current.getChild() == null){
					flag = false;
					break;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				flag = false;
				break;
			}else{
				current = current.getNext();
			}
		}
		while(flag == true){	//CHECK 3
			if(current.getKey() == currentCharacter2 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter2 && current.getEndFlag() == false){
				if(current.getChild() == null){
					flag2 = false;
					break;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				flag2 = false;
				break;
			}else{
				current = current.getNext();
			}
		}while(flag == true && flag2 == true){	//CHECK 4
			if(current.getKey() == currentCharacter3 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter3 && current.getEndFlag() == false){
				break;
			}else if(current.getNext() == null){
				break;
			}else{
				current = current.getNext();
			}
		}
		
		//LOOKUP 1,2,3,4
		currentCharacter = temp.charAt(0);
		currentCharacter2 = temp.charAt(1);
		currentCharacter3 = temp.charAt(2);
		char currentCharacter4 = temp.charAt(3);
		current = badPasswordTrie.getRoot();
		while(true){	//CHECK 1
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				if(current.getChild() == null){
					return true;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}
		while(true){	//CHECK 2
			if(current.getKey() == currentCharacter2 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter2 && current.getEndFlag() == false){
				if(current.getChild() == null){
					return true;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}while(true){	//CHECK 3
			if(current.getKey() == currentCharacter3 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter3 && current.getEndFlag() == false){
				if(current.getChild() == null){
					return true;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}
		while(true){	//CHECK 4
			if(current.getKey() == currentCharacter4 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter4 && current.getEndFlag() == false){
				return true;
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}
	}
	
	/**
	*Is used to determine whether a 5 character long prefix is legal or illegal, runs through all
	*relevant permutations [In this case, 5:4,5:3,4,5:2,3,4,5:1,2,3,4,5] not already checked by previous lookupPossible#'s
	*
	* I wouldn't claim that this is fully optimized to the maximum, but it seems to work quickly enough for my needs.
	*
	*@param prefixToLookup: The partialSolution to be checked.
	*@param badPasswordTrie: The "badDLB" used to reference whether prefixToLookup is legal or illegal.
	*@return: if TRUE: prefixToLookup is legal so far	// FALSE: prefixToLookup is illegal.
	**/
	public boolean lookupPossible5(String prefixToLookup, DLBTrie badPasswordTrie){	//COMPLETE IN THEORY
		boolean result = true;
		StringBuilder temp = new StringBuilder(prefixToLookup);
		DLBNode current = badPasswordTrie.getRoot();
		boolean flag = true;
		
		//LOOKUP 5
		char currentCharacter = temp.charAt(4);
		while(true){	//CHECK 5
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				break;
			}else if(current.getNext() == null){
				break;
			}else{
				current = current.getNext();
			}
		}
		
		//LOOKUP 4,5
		currentCharacter = temp.charAt(3);
		char currentCharacter2 = temp.charAt(4);
		current = badPasswordTrie.getRoot();
		while(true){	// CHECK 4
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				if(current.getChild() == null){
					flag = false;	//So 3,4 cannot be a bad password, but somewhere down the line may be
					break;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				flag = false; 		//If 3 cannot be found, then 3/4 cannot be a bad password, move onto next check
				break;
			}else{
				current = current.getNext();
			}
		}
		while(flag = true){	//CHECK 5
			if(current.getKey() == currentCharacter2 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter2 && current.getEndFlag() == false){
				break;
			}else if(current.getNext() == null){
				break;
			}else{
				current = current.getNext();
			}
		}
		
		//LOOKUP 3,4,5
		currentCharacter = temp.charAt(2);
		currentCharacter2 = temp.charAt(3);
		char currentCharacter3 = temp.charAt(4);
		current = badPasswordTrie.getRoot();
		flag = true;
		boolean flag2 = true;
		while(true){	//CHECK 3
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				if(current.getChild() == null){
					flag = false;
					break;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				flag = false;
				break;
			}else{
				current = current.getNext();
			}
		}
		while(flag == true){	//CHECK 4
			if(current.getKey() == currentCharacter2 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter2 && current.getEndFlag() == false){
				if(current.getChild() == null){
					flag2 = false;
					break;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				flag2 = false;
				break;
			}else{
				current = current.getNext();
			}
		}while(flag == true && flag2 == true){	//CHECK 5
			if(current.getKey() == currentCharacter3 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter3 && current.getEndFlag() == false){
				break;
			}else if(current.getNext() == null){
				break;
			}else{
				current = current.getNext();
			}
		}
		
		//LOOKUP 2,3,4,5
		currentCharacter = temp.charAt(1);
		currentCharacter2 = temp.charAt(2);
		currentCharacter3 = temp.charAt(3);
		char currentCharacter4 = temp.charAt(4);
		current = badPasswordTrie.getRoot();
		flag = true;
		flag2 = true;
		boolean flag3 = true;
		while(true){	//CHECK 2
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				if(current.getChild() == null){
					flag = false;
					break;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				flag = false;
				break;
			}else{
				current = current.getNext();
			}
		}
		while(flag == true){	//CHECK 3
			if(current.getKey() == currentCharacter2 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter2 && current.getEndFlag() == false){
				if(current.getChild() == null){
					flag2 = false;
					break;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				flag2 = false;
				break;
			}else{
				current = current.getNext();
			}
		}while(flag == true && flag2 == true){	//CHECK 4
			if(current.getKey() == currentCharacter3 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter3 && current.getEndFlag() == false){
				if(current.getChild() == null){
					flag3 = false;
					break;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				flag3 = false;
				break;
			}else{
				current = current.getNext();
			}
		}
		while(flag == true && (flag2 == true && flag3 == true)){	//CHECK 5
			if(current.getKey() == currentCharacter4 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter4 && current.getEndFlag() == false){
				break;
			}else if(current.getNext() == null){
				break;
			}else{
				current = current.getNext();
			}
		}
		
		//LOOKUP 1,2,3,4,5 *Oh bby here we go*
		currentCharacter = temp.charAt(0);
		currentCharacter2 = temp.charAt(1);
		currentCharacter3 = temp.charAt(2);
		currentCharacter4 = temp.charAt(3);
		char currentCharacter5 = temp.charAt(4);
		current = badPasswordTrie.getRoot();
		while(true){	//CHECK 1
			if(current.getKey() == currentCharacter && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter && current.getEndFlag() == false){
				if(current.getChild() == null){
					return true;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}
		while(true){	//CHECK 2
			if(current.getKey() == currentCharacter2 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter2 && current.getEndFlag() == false){
				if(current.getChild() == null){
					return true;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}while(true){	//CHECK 3
			if(current.getKey() == currentCharacter3 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter3 && current.getEndFlag() == false){
				if(current.getChild() == null){
					return true;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}
		while(true){	//CHECK 4
			if(current.getKey() == currentCharacter4 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter4 && current.getEndFlag() == false){
				if(current.getChild() == null){
					return true;
				}else{
					current = current.getChild();
					break;
				}
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}while(true){	//CHECK 5
			if(current.getKey() == currentCharacter5 && current.getEndFlag() == true){
				return false;
			}else if(current.getKey() == currentCharacter5 && current.getEndFlag() == false){
				return true;
			}else if(current.getNext() == null){
				return true;
			}else{
				current = current.getNext();
			}
		}
	}
	
	/**
	*A representation of a DLBNode. Is used to create the Horizontal/Vertical LinkedLists required by the DLB Trie symbol table
	*as well as its traversal.
	**/
	public class DLBNode{
		private char key =']';				// The letter being stored.  DEFAULT = ']'
		private boolean endFlag = false;	// Is this the last letter in a word?
		private DLBNode next = null;		// Next horizontal option in LinkedList.
		private DLBNode child = null;		// Next vertical option in LinkedList.
		
		//CONSTRUCTORS
		public DLBNode(){		//Most commonly used.
			endFlag = false;
		}
		
		public DLBNode(char newKey){
			key = newKey;
		}
		
		/**
		*Sets the key of the node to the desired value.
		*
		*@param: newKey: Value to be set as key
		**/
		public void setKey(char newKey){
			key = newKey;
		}
		
		/**
		*Sets the endFlag of the node to the desired boolean. Used to represent a lastCharacter node.
		*
		*@param newEndFlag: If TRUE: This node is the last character of a vertical LinkedList string	// FALSE: this node is NOT the last character.
		**/
		public void setEndFlag(boolean newEndFlag){
			endFlag = newEndFlag;
		}
		
		/**
		*Sets the next node (Reference to the next node in LinkedList of the DLB)
		*
		*@param newNext: Node whose reference is about to be set as the current Node's next Node.
		**/
		public void setNext(DLBNode newNext){
			next = newNext;
		}
		
		/**
		*Creates a new node to be referenced as the next in the Linked List of the DLB.
		**/
		public void newNext(){
			next = new DLBNode();
		}
	
		/**
		*Creates a new node to be referenced as the child in the vertical Linked List of the DLB
		**/
		public void newChild(){
			child = new DLBNode();
		}
		
		/**
		*Returns the key of the current node
		*
		*@return: The key value of the current node.
		**/
		public char getKey(){
			return key;
		}
		
		/**
		*Returns the endFlag of the current node, which determines whether it is/isn't the final node in a vertical string.
		*
		*@return: If TRUE: this node is the final node in a vertical Linked List		// FALSE: This node is NOT the final node.
		**/
		public boolean getEndFlag(){
			return endFlag;
		}
		
		/**
		*Returns the current node's next referenced node.
		*
		*@return: The next node in the horizontal chain.
		**/
		public DLBNode getNext(){
			return next;
		}
		
		/**
		*Returns the current node's child node.
		*
		*@return: The next node in the vertical chain.
		**/
		public DLBNode getChild(){
			return child;
		}
	}
	
	/**
	* Implementation of the De La Briandais Trie symbol table (Even though the DLBNode holds most of the relevant data);
	**/
	public class DLBTrie{
		private DLBNode root;			// This is where we will start our inserts/searches.
		private int nodeCount = 0;		// How many nodes are in the DLB?
		private int words = 0;			// How many endFlags have been set to True?
		
		//CONSTRUCTORS
		public DLBTrie(){						// Should only really ever use this one.
			root = new DLBNode();
		}
		
		public DLBTrie(DLBNode newNode){
			root = newNode;
		}
		
		/**
		*Sets the key of the root node of the DLBTrie. Should only be used at the very first insert of a new DLBTrie.
		*
		*@param newRootKey: The value to be set as the root's key.
		**/
		public void setRootKey(char newRootKey){
			root.setKey(newRootKey);
		}
		
		/**
		*Returns the root node of the DLB
		*
		*@return: The root node of the DLB
		**/
		public DLBNode getRoot(){
			return root;
		}
		
		/**
		*Returns the key of the root node of the DLB
		*
		*@return: The root node's key.
		**/
		public char getRootKey(){
			return root.getKey();
		}
		
		/**
		*Used to keep track of how many nodes are in the DLB for debugging / curiosity. Each call adds one to count.
		**/
		public void incrementCount(){
			nodeCount +=1;
		}
		
		/**
		*Used to keep track of how many words are in the DLB for debugging / curiosity. Each call adds one to count.
		**/
		public void incrementWords(){
			words += 1;
		}
		
		/**
		*Returns how many nodes are in the DLB.
		*
		*@return: # of nodes in DLB.
		**/
		public int getCount(){
			return nodeCount;
		}
		
		/**
		*Returns how many words are in the DLB.
		*
		*@return: # of words in the DLB.
		**/
		public int getWordCount(){
			return words;
		}
		
		
	}
	
}