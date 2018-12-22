/**This program will determine a valid assignment of coupons if there is one*/
import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.Stack;
import java.lang.NullPointerException;

/**
*
*/
public class FriendsCoupon {
	private static int count;				// How many users are there
	private static int numberOfCoupons;		// How many different types of coupons can be distributed
	private static int numberOfEntries;		// # of entries in Partial Solution
	private static int[][] matrixArray;		// This is our "chess board"
	
	
	/**
	*
	*/
	public static void main(String[] args) {
		//TEST METHODS
		testIsFullSolution();	// test methods feel free to comment out
		testReject();
		testExtend();
		testNext();
		
		//CREATE MATRIX FROM USER FILE
		final String userString = args[0];				// The filename to load the matrix in from
		numberOfCoupons = Integer.parseInt(args[1]);	// How many different types of coupons will we be allowed to use?
		matrixArray = create2DArray(userString);		// matrixArray now holds the created array
		displayCreatedMatrix(matrixArray);				// Mostly for testing purposes, feel free to comment out this line of code
		if(verify2DArray( matrixArray) == false){		// Checks to see if no user is friends with himself, friendship is reciprocal, symmetric.
			System.exit(0);
		}
		
		//CREATE PARTIALSOLUTION ARRAY
		int [] solution = new int[count];	// Create the stack for our partial solution [capacity of stack]
		numberOfEntries = 0;				// Our partialSolution holds nothing at this time
		
		//RUN THE RECURSIVE BACKTRACKING METHOD
		extend(solution);		// Need a starting point
		backtrace(solution);
	}
	
	/**
	* Main recursive method
	*1. check to see if a valid full solution
	*2. check to see if a valid partial solution [if no, go to next possible choice / if yes, extend the solution]
	* 
	*/
	public static void backtrace(int[] partialSolution){
		if(isFullSolution(partialSolution) == true){	// current partial is a valid final
			displayFinalSolution(partialSolution);
		}else if(reject(partialSolution) == true){		//rejecting current partial
			if(partialSolution[numberOfEntries-1] == numberOfCoupons){	//Was this the last type of coupon we could distribute? if so, we need to next(previous partial)
				partialSolution = removeStep(partialSolution);  		// we have exhausted all options for this step, previous might be in error
				backtrace(next(partialSolution));			// since the previous step didn't allow for the current step to work, we need to change the previous one
			}else{
				backtrace(next(partialSolution));			// The current option didn't work, try another one
			}
		}else if(reject(partialSolution) == false){		// The current partial solution is valid,
			backtrace(extend(partialSolution));			// if the current step works, then add a new one
		}
		
	}
	
	/**
	* Reads in from userFile and creates / fills out the two-dimensional array
	*/
	public static int[][] create2DArray(String filename){
		try{
			// Discover how large we have to make the arrays
			File matrixFile = new File(filename);
			Scanner scanner = new Scanner(matrixFile).useDelimiter("\\s* \\s*");
			count=0;	//What is the size of our arrays to make
			String temp; 		// holds most recently scanned-in value
			while(scanner.hasNext()){
				temp = scanner.next();
				if(temp.length() > 1){
					count += 1;
					break;
				}
				count +=1;
			}
			
			// Create the Arrays
			int[][] matrix = new int[count][count];
			
			//Fill out the arrays
			scanner.close();
			scanner = new Scanner(matrixFile).useDelimiter("\\s* \\s*");	// reset our scanner to the beginning of the file
			for(int i=0; i<=count-1; i++){ 
				int j;
				int symmetryCount;	// Is compared to count to make sure the array we are creating is symmetric
				if(i==0){
					j = 0;
					symmetryCount = 0;
				}else{	//Not our first iteration
					j = 1;
					symmetryCount = 1;
				}
				while(scanner.hasNext()){
					temp = scanner.next();
					symmetryCount += 1;
					if((temp.length() > 1) && (i < count-1)){	// Have we hit the end of the line?
						if(symmetryCount != (count)){
							System.out.println("ERROR: 2D-Array held in given file is not symmetric");
							System.exit(0);
						}
						matrix[i][j] = Integer.parseInt(temp.substring(0, 1));
						matrix[i+1][0] = Integer.parseInt(temp.substring(2,3));
						break;					
					}else if((i == count-1) && !(scanner.hasNext())){ // Is this the very last number to read in?
						if(symmetryCount != (count)){
							System.out.println("ERROR: 2D-Array held in given file is not symmetric");
							System.exit(0);
						}
						matrix[i][j] = Integer.parseInt(temp.substring(0,1));
						break;
					}
					matrix[i][j] = Integer.parseInt(temp);
					j += 1;
				}
			}
			scanner.close();
			
			return matrix;
			
		}catch(FileNotFoundException fnfe){
			System.out.println("ERROR: That file could not be read from.");
		}catch(IndexOutOfBoundsException iobe){
			System.out.println("ERROR: The 2D-Array held in given file is not symmetric");
			System.exit(0);
		}
		int[][] matrix2 = new int[1][1];	// Compiler was throwing an error so I guess I need a second return statement here
		return matrix2;
	}
	
	/**
	*Checks to See:
	*1: Friendship is reciprocal	[i][j] must be friends with [j][i] vice versa
	*2: No user is friends with himself i=/=j
	*/
	public static boolean verify2DArray(int[][] toCheck){
		System.out.println("\nVerifying that the created 2D-Array is valid [Recriprocal Friendships, No Friendships with Self, Symmetric]...");
		boolean result = true;
		for(int i=0; i<= count-1; i++){
			for(int j=0; j<= count-1; j++){			
				if(toCheck[i][j] == 1){	// i is friends with j
					if(i == j){	// i cannot be friends with i
						result = false;
						System.out.println("ERROR: user " + i + " cannot be friends with himself");
						return result;
					}else if(toCheck[j][i] == 0){	// if I is friends with J then J must be friends with I
						result = false;
						System.out.println("ERROR: If user " + i + " is friends with user " + j + ", then user " + j + " must be friends with user " + i + "");
						return result;
					}
				}
			}
		}
		System.out.println("The created 2dArray is valid");
		return result;
	}
	
	/**
	* Print out the created array
	*/
	public static void displayCreatedMatrix(int[][] matrix){
			System.out.println("\nDisplaying created 2D-Array...");
			for(int i=0; i<= count-1; i++){
				for(int j=0; j<=count-1; j++){
					System.out.print(matrix[i][j]);
				}
				System.out.println("");
			}
	}
	
	/**
	*checks to see if partialSolution is a valid final solution
	*assumes all previous values are correct, check only final user
	*/
	public static boolean isFullSolution(int[] toCheck){
		if(toCheck[count-1] == 0){	// check to make sure we've even made it to the last user
			return false;
		}
		for(int i=0; i<= count-1; i++){		// iterate through the last user and check their tags
			if(matrixArray[count-1][i] == 1){
				if(toCheck[i] == toCheck[count-1]){
				return false;
				}
			}
		}
		return true;
	}
	
	/**
	* checks to see if the partial solution so far is valid
	* assume that all values except current one are valid, 
	*/
	public static boolean reject(int[] toCheck){
		for(int i = 0; i <= count-1; i++){
			if(matrixArray[numberOfEntries-1][i] == 1){	//if current user [numberOfEntries-1] is friends with user i,
				if(toCheck[i] == toCheck[numberOfEntries-1]){ // check to see if user i and [numberOfEntries-1] have the same coupon, if so, reject
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	*returns a partial solution, or null if there are no more steps to be added
	* add 1 to the coupon type of the next index in partial Solution, add 1 to the coupon type of the next hashmap key.
	*/
	public static int[] extend(int[] toExtend){
		try{
			if(numberOfEntries == count){	// if we have as many entries as users, then we can't add any more
				return null;
			}
			else{
				numberOfEntries = numberOfEntries + 1;	// our current index is now at the next user
				toExtend[numberOfEntries-1] += 1;		// increment partial solution
				return toExtend;
			}
		}catch(NullPointerException npe){	//Theoretically should never occur, but just to be safe...
			return null;
		}
	}
	
	/**
	* returns a partial solution, or null if nothing left to next to
	* add 1 to the coupon type of the current user in hashmap, add 1 to the current index in partialSolution
	*/
	public static int[] next(int[] toNext){
		try{
			if(toNext[numberOfEntries-1] == numberOfCoupons){		// if we are on our last option for coupon, return null
				return null;
			}else{
				toNext[numberOfEntries-1] = toNext[numberOfEntries-1] + 1;	// Increment our coupon type by 1
				return toNext;
			}
		}catch(NullPointerException npe){	//Theoretically should never occur but just to be safe...
			return null;
		}
	}
	
	/**
	* Removes the current step in the program so we can change the previous step, if the previous step is at its maximum option, we must remove it as well
	*/
	public static int[] removeStep(int[] toRemove){
		if(numberOfEntries == 1){ 	// if we are on our first step and have to remove it, the matrix is unsolvable
			System.out.println("It is impossible to properly distribute just " + numberOfCoupons + " coupon(s) to this group of " + count +" friends.");
			System.exit(0);
			return toRemove;
		}else{
			while(toRemove[numberOfEntries-1] == numberOfCoupons){	//while our steps are on their last possible option
				toRemove[numberOfEntries-1] = 0;
				numberOfEntries = numberOfEntries-1;
				if((numberOfEntries == 1) && (toRemove[numberOfEntries-1] == numberOfCoupons)){
					System.out.println("It is impossible to properly distribute just " + numberOfCoupons + " coupon(s) to this group of " + count + " friends.");
					System.exit(0);
				}
			}
			return toRemove;
		}
	}
	
	/**
	* Will display a valid finalSolution
	*/
	public static void displayFinalSolution(int[] finalSolution){
		System.out.println("\nDisplaying Valid Distribution of Coupons...");
		for(int i=0; i<=count-1; i++){
			System.out.println("User " + (i+1) + " should receive coupon " + getCoupon(finalSolution[i]) + ".");
		}
	}
	
	/**
	*Takes an int value and convert it to its corresponding String letter
	*/
	public static String getCoupon(int intToConvert){
		if (intToConvert == 1){
			return "A";}
		if (intToConvert == 2){
			return "B";}
		if (intToConvert == 3){
			return "C";}
		if (intToConvert == 4){
			return "D";}
		if (intToConvert == 5){
			return "E";}
		if (intToConvert == 6){
			return "F";}
		if (intToConvert == 7){
			return "G";}
		if (intToConvert == 8){
			return "H";}
		if (intToConvert == 9){
			return "I";}
		if (intToConvert == 10){
			return "J";}
		if (intToConvert == 11){
			return "K";}
		if (intToConvert == 12){
			return "L";}
		if (intToConvert == 13){
			return "M";}
		if (intToConvert == 14){
			return "N";}
		if (intToConvert == 15){
			return "O";}
		if (intToConvert == 16){
			return "P";}
		if (intToConvert == 17){
			return "Q";}
		if (intToConvert == 18){	
			return "R";}
		if (intToConvert == 19){
			return "S";}
		if (intToConvert == 20){
			return "T";}
		if (intToConvert == 21){
			return "U";}
		if (intToConvert == 22){
			return "V";}
		if (intToConvert == 23){
			return "W";}
		if (intToConvert == 24){
			return "X";}
		if (intToConvert == 25){
			return "Y";}
		if (intToConvert == 26){
			return "Z";}
		return "Not enough letters";
	}
	
	/**
	* tests the isFullSolution() method. [creates some matrixes and hashmaps with known valid/invalid values to test]
	*/
	public static void testIsFullSolution(){
		//TEST 1
		System.out.println("\nTesting the method isFullSolution with a full but unsolvable solution of size 8...");
		count = 8;
		matrixArray = new int[count][count];						//only concerned with last row as that is all isFullSolution() accesses
		matrixArray[count-1][0] = 1; matrixArray[count-1][1] = 1;matrixArray[count-1][2] = 1;matrixArray[count-1][3] = 1;matrixArray[count=1][4] = 1;matrixArray[count-1][5] = 1;matrixArray[count-1][6] = 1;matrixArray[count-1][7] = 1;
		count=8;
		int[] solution = new int[count];
		solution[0] = 1; solution[1] = 2; solution[2] = 2; solution[3] = 3; solution[4] = 1; solution[5] = 2; solution[6] = 3; solution[7] = 3;
		if(isFullSolution(solution) == true){
			System.out.println("	-isFullSolution has deemed the partialSolution VALID, the test has FAILED.");	// Should be unsolvable
		}else{
			System.out.println("	-isFullSolution has deemed the partialSolution INVALID, the test has PASSED.");	// Desired
		}
		//TEST 2
		System.out.println("\nTesting the method isFullSolution with a full and correct solution of size 4...");
		count = 4;
		matrixArray = new int[count][count];						//only concerned with last row as that is all isFullSolution() accesses
		matrixArray[count-1][0] = 0; matrixArray[count-1][1] = 0;matrixArray[count-1][2] = 1;matrixArray[count-1][3] = 0;
		count=4;
		solution = new int[count];
		solution[0] = 1; solution[1] = 2; solution[2] = 2; solution[3] = 1;
		if(isFullSolution(solution) == true){
			System.out.println("	-isFullSolution has deemed the partialSolution VALID, the test has PASSED.");	// Desired
		}else{
			System.out.println("	-isFullSolution has deemed the partialSolution INVALID, the test has FAILED.");	// Solution should be correct
		}
	}
	
	/**
	* tests the reject() method. [creates both a valid and invalid matrix/hashmap combo to test]
	*/
	public static void testReject(){
		//TEST 1
		System.out.println("\nTesting the method reject with an incorrect size 7 partialSolution of a solution of size 8...");
		count = 8;
		numberOfEntries = 7;
		matrixArray = new int[count][count];	//only concerned with last row as that is all isFullSolution() accesses
		matrixArray[count-2][0] = 1; matrixArray[count-2][1] = 1;matrixArray[count-2][2] = 1;matrixArray[count-2][3] = 1;matrixArray[count-2][4] = 1;matrixArray[count-2][5] = 1;matrixArray[count-2][6] = 1;matrixArray[count-2][7] = 1;
		count=8;
		numberOfEntries = 7;
		int[] solution = new int[count];
		solution[0] = 1; solution[1] = 2; solution[2] = 2; solution[3] = 3; solution[4] = 1; solution[5] = 2; solution[6] = 3; solution[7] = 0;
		if(reject(solution) == true){
			System.out.println("	-reject has deemed the partialSolution INVALID, the test has PASSED.");	// Desirable
		}else{
			System.out.println("	-reject has deemed the partialSolution VALID, the test has FAILED.");	// should fail to reject
		}
		//TEST 2
		System.out.println("\nTesting the method reject with a valid partialSolution of size 3 of a solution of size 4...");
		count = 4;
		numberOfEntries = 3;
		matrixArray = new int[count][count];						//only concerned with last row as that is all isFullSolution() accesses
		matrixArray[count-2][0] = 1; matrixArray[count-2][1] = 0;matrixArray[count-2][2] = 0;matrixArray[count-2][3] = 1;
		count=4;
		numberOfEntries = 3;
		solution = new int[count];
		solution[0] = 1; solution[1] = 2; solution[2] = 2; solution[3] = 0;
		if(reject(solution) == true){
			System.out.println("	-reject has deemed the partialSolution INVALID, the test has FAILED.");	// solution should be correct
		}else{
			System.out.println("	-reject has deemed the partialSolution VALID, the test has PASSED.");	// Desirable
		}
	}
	
	/**
	* tests the extend() method.
	*/
	public static void testExtend(){
		//TEST 1
		count = 8;
		numberOfEntries = 8;
		System.out.println("\nTesting the method extend with a full partialSolution of size 8...");
		int[] solution = new int[count];
		if(extend(solution) == null){
			System.out.println("	-extend has deemed that it is IMPOSSIBLE to extend another value, the test has PASSED.");	//extend should fail to add more
		}else{
			System.out.println("	-extend has deemed that it is POSSIBLE to extend another value, the test has FAILED.");		//extend should not be able to extend another value or indexoutofbounds exception
		}
		//TEST 2
		System.out.println("\nTesting the method extend with a non-full partialSolution of size 5 with count 8...");
		count = 8;
		numberOfEntries = 5;
		solution = new int[count];
		if(extend(solution) != null){
			System.out.println("	-extend has deemed that it is POSSIBLE to extend another value, the test has PASSED.");
		}else{
			System.out.println("	-extend has deemed that it is IMPOSSIBLE to extend another value, the test has FAILED.");
		}
	}
	
	/**
	* tests the next() method.
	*/
	public static void testNext(){
		//TEST 1
		count = 8;
		numberOfEntries = 7;
		numberOfCoupons = 2;
		System.out.println("\n Testing the method next with a partialSolution of the last available option...");
		int[] solution = new int[count];
		solution[numberOfEntries - 1] = 2;
		if(next(solution) == null){
			System.out.println("	-next has deemed it IMPOSSIBLE to change the distributed coupon to its next type, the test has PASSED.");	//Desirable
		}else{
			System.out.println("	-next has deemed it POSSIBLE to change the distributed coupon to its next type, the test has FAILED.");		//If the current coupon is already the same as the max coupon type, should be nothing left to change it to
		}
		//TEST 2
		count = 4;
		numberOfEntries = 2;
		numberOfCoupons = 2;
		System.out.println("\n Testing the method next with a partialSolution capable of having its most recent step changed...");
		solution = new int[count];
		solution[numberOfEntries - 1] = 1;
		if(next(solution) == null){
			System.out.println("	-next has deemed it IMPOSSIBLE to change the distributed coupon to its next type, the test has FAILED");	// We are on coupon type 1, should be able to change to maximum type 2
		}else{
			System.out.println("	-next has deemed it POSSIBLE to change the distributed coupon to its next type, the test has PASSED");
		}
	}
	
}