/**
* Assignment 3: Distribute Coupons to Group of Friends
* @author Randyll Bearer rlb97
*/
import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.Stack;
import java.lang.NullPointerException;

/**
* Finds an assignment of coupons to a group of friends that satisfies the rules of
* 1. if I is friends with J, J must be friends with I [Reciprocal]
* 2. J cannot be Friends with J [Cannot be friends with self]
* 3. If I is friends with J, I and J cannot have the same type of coupon distributed
* This program loads in a matrix from a user-given file, verifies that it follows rules 1 & 2 and that its symmetric
* and then outputs the valid coupon assignment [Max # of coupons also given by user] if there is one, if the assignment is impossible then it reports that it
* is impossible.
*/
public class FriendsCoupon {
	private static int count;				// How many users/friends are there
	private static int numberOfCoupons;		// How many different types of coupons can be distributed
	private static int numberOfEntries;		// # of entries in partialSolution[]
	private static int[][] matrixArray;		// This is our "chess board"
	private static HashMap<Integer, Integer> userCoupon;	//[key = user#, value=typeOfCoupon]
	
	
	/**
	*	Driver Method, tests the methods, creates Matrix/HashMap/partialSolution, runs the back-tracking recursive method.
	*/
	public static void main(String[] args) {
			//TEST METHODS
			testIsFullSolution();	// test methods feel free to comment out
			testReject();
			testExtend();
			testNext();
			count = 0;
			numberOfCoupons = 0;
			numberOfEntries = 0;
			matrixArray = null;
			userCoupon = null;
			
			//CREATE MATRIX FROM USER FILE
			final String userString = args[0];				// The filename to load the matrix in from
			numberOfCoupons = Integer.parseInt(args[1]);	// How many different types of coupons will we be allowed to use
			matrixArray = create2DArray(userString);		// matrixArray now holds the created array
			if(verify2DArray( matrixArray) == false){		// Checks to see if no user is friends with himself, friendship is reciprocal, symmetric.
				System.exit(0);
			}
			displayCreatedMatrix(matrixArray);				// Mostly for testing purposes, feel free to comment out this line of code
			
			//CREATE HASHMAP AND PARTIALSOLUTION ARRAY
			userCoupon = createHashMap(count);	// returns a hashmap with keys 0-(n-1) representing the user number with values set to 0 (Will be changed to distributed coupon)
			int [] solution = new int[count];	// Create the stack for our partial solution [capacity of stack]
			numberOfEntries = 0;				// Our partialSolution holds nothing at this time
			
			//RUN THE RECURSIVE BACKTRACKING METHOD
			solution = extend(solution);		// Need a starting point
			backtrace(solution);
	}
	
	/**
	* Main recursive method
	*1. check to see if a valid full solution
	*2. check to see if a valid partial solution [if no, go to next possible choice / if yes, extend the solution]
	* @param partialSolution: an array holding the steps we've made up to this point.  
	*/
	public static void backtrace(int[] partialSolution){
		if(isFullSolution(partialSolution) == true){	// if the current partialSolution is actually a valid finalSolution, we're done
			displayFinalSolution(partialSolution);
		}else if(reject(partialSolution) == true){		//if the current partialSolution is invalid
			if(partialSolution[numberOfEntries-1] == numberOfCoupons){	//Was this the last type of coupon we could distribute? if so, we need to next(previous partial)
				partialSolution = removeStep(partialSolution);  		// we have exhausted all options for this step, regress to previous step
				backtrace(next(partialSolution));			// since the previous step didn't allow for the current step to work, we need to change the previous one
			}else{
				backtrace(next(partialSolution));			// The current option didn't work, try the next coupon type
			}
		}else if(reject(partialSolution) == false){		// The current partial solution is valid,
			backtrace(extend(partialSolution));			// if the current step works, then add a new one
		}
		
	}
	
	/**
	* Reads in from filename and creates a 2dArray of size[count][count]. Throws exceptions if
	* the file could not be read from or if the 2dArray is not symmetric.  Will EXIT the program if
	* the created array does not meet the above rules.
	* @param filename: User-passed argument holding the file location to load the matrix in from
	* @return A two-dimensional array holding 1/0's created from user file.
	*/
	public static int[][] create2DArray(String filename){
		try{
			// Discover how large we have to make the arrays
			File matrixFile = new File(filename);
			Scanner scanner = new Scanner(matrixFile).useDelimiter("\\s* \\s*");
			count=0;		//What is the size of our arrays to make
			String temp; 	//holds most recently scanned-in value
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
						matrix[i][j] = Integer.parseInt(temp.substring(0,1));	// Add the read-in value to the matrix
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
		int[][] matrix2 = new int[1][1];	// Compiler was throwing an error so I guess I need a dummy return statement here
		return matrix2;
	}
	
	/**
	* Checks the returned 2dArray from create2DArray() to verify that it follows the above rules.
	* @param toCheck: the created matrix
	* @return result: if false: the created array does not follow the rules and is invalid / if true: the created array is valid and follows the rules
	*/
	public static boolean verify2DArray(int[][] toCheck){
		System.out.println("\nVerifying that the created 2D-Array is valid [Recriprocal Friendships, No Friendships with Self, Symmetric]...");
		boolean result = true;
		for(int i=0; i<= count-1; i++){
			for(int j=0; j<= count-1; j++){			
				if(toCheck[i][j] == 1){	// i is friends with j
					if(i == j){			// i cannot be friends with i
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
		System.out.println("The created 2dArray is valid\n");
		return result;
	}
	
	/**
	* For debugging/testing purposes, displays the created 2dArray out to the user.
	* @param matrix: the created 2DArray
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
	* Creates a hashmap to hold which users have been assigned which coupons, used in conjunction with partialSolution.
	* @param capacity: The same as count, the # of users
	* @return A created hashmap of key=user#, value=coupon type
	*/
	public static HashMap createHashMap(int capacity){
		HashMap<Integer, Integer> userCoupon = new HashMap<>(count);	// key = user#, value = distributed coupon
		for(int i=0; i<= count-1; i++){	// Create each key/value pair in the hashmap, default value = 0
			userCoupon.put(new Integer(i),new Integer(0));
		}
		return userCoupon;
	}
	
	/**
	* Accepts a partialSolution and checks to see if it is a valid finalSolution. Assumes that 
	* in all previous steps of the partialSolution has been checked by reject().  isFullSolution() only
	* checks the final step in the partialSolution.
	* assumes all previous values are correct, check only final user
	* @param toCheck: the partialSolution
	* @return True if the partialSolution is in fact a valid finalSolution. / False if otherwise.
	*/
	public static boolean isFullSolution(int[] toCheck){
		if(userCoupon.get(count-1) == 0){	// Check to make sure we've even made it to the last user
			return false;
		}
		for(int i=0; i<= count-1; i++){		// Iterate through the last user and check their tags
			if(matrixArray[count-1][i] == 1){
				if(userCoupon.get(i) == userCoupon.get(count-1)){
				return false;
				}
			}
		}
		return true;
	}
	
	/**
	* Accepts a partialSolution and checks to see if it should be rejected or not. Assumes that all previous
	* steps of the partialSolution have also been checked by reject().  reject() checks only the most recently 
	* added step.
	* @param toCheck: the partialSolution
	* @return True if the partialSolution is invalid / False if the partialSolution IS valid
	*/
	public static boolean reject(int[] toCheck){
		for(int i = 0; i <= count-1; i++){
			if(matrixArray[numberOfEntries-1][i] == 1){	//if current user [numberOfEntries-1] is friends with user i,
				if(userCoupon.get(i) == userCoupon.get(numberOfEntries-1)){ // check to see if user i and [numberOfEntries-1] have the same coupon, if so, reject
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	* Accepts a partialSolution which has previously been deemed valid and attempts to extend it by one
	* partialSolution step. Updates both the partialSolution and HashMap.
	* @param toExtend: the partialSolution
	* @return A new partialSolution with one more step added if possible / If the partialSolution is full, return null.
	*/
	public static int[] extend(int[] toExtend){
		try{
			if(numberOfEntries >= count){	// if we have as many entries as users, then we can't add any more
				return null;
			}
			else{
				numberOfEntries = numberOfEntries + 1;	// our current index is now at the next user
				toExtend[numberOfEntries-1] += 1;		// increment partial solution
				userCoupon.put(new Integer(numberOfEntries-1), 1);	// Increment the HashMap
				return toExtend;
			}
		}catch(NullPointerException npe){	//Theoretically should never occur, but just to be safe...
			return null;
		}
	}
	
	/**
	* Accepts a partialSolution which has been deemed invalid and attempts to increment its coupon distribution
	* by one.  If it is impossible to increment (coupontype = numberOfCoupons), returns null.
	* @param toNext: the partialSolution
	* @return If possible toNext, return a new partialSolution / If impossible toNext, return null.
	*/
	public static int[] next(int[] toNext){
		try{
			if(toNext[numberOfEntries-1] == numberOfCoupons){		// if we are on our last option for coupon, return null
				return null;
			}else{
				toNext[numberOfEntries-1] = toNext[numberOfEntries-1] + 1;	// Increment our coupon type by 1
				userCoupon.put(numberOfEntries-1, (userCoupon.get(numberOfEntries-1)+1));			// Increment the HashMap
				return toNext;
			}
		}catch(NullPointerException npe){	//Theoretically should never occur but just to be safe...
			return null;
		}
	}
	
	/**
	* Removes as many steps as necessary from the partialSolution to bring it back to its last valid state before reaching invalid
	* toRemove is what notices impossible arrays, if we need to remove our very first step then a solution is impossible.
	* @param toRemove: A partialSolution which needs to be rolledBack
	* @return a partialSolution which has been brought back to its next viable state
	*/
	public static int[] removeStep(int[] toRemove){
		if(numberOfEntries == 1){ 	// if we are on our first step and have to remove it, the matrix is unsolvable
			System.out.println("It is impossible to properly distribute just " + numberOfCoupons + " coupon(s) to this group of " + count +" friends.");
			System.exit(0);
			return toRemove;
		}else{
			while(toRemove[numberOfEntries-1] == numberOfCoupons){	//while our steps are on their last possible option
				toRemove[numberOfEntries-1] = 0;	//decrement the partialSolution
				userCoupon.put(numberOfEntries-1, 0);	//decrement the HashMap
				numberOfEntries = numberOfEntries-1;
				if((numberOfEntries == 1) && (toRemove[numberOfEntries-1] == numberOfCoupons)){	// If we have to remove our first step, its impossible.
					System.out.println("It is impossible to properly distribute just " + numberOfCoupons + " coupon(s) to this group of " + count + " friends.");
					System.exit(0);
				}
			}
			return toRemove;
		}
	}
	
	/**
	* Prints out the calculated distribution of coupons.
	* @param finalSolution: a partialSolution which has been deemed a valid finalSolution
	*/
	public static void displayFinalSolution(int[] finalSolution){
		System.out.println("\nDisplaying Valid Distribution of Coupons...");
		for(int i=0; i<=count-1; i++){
			System.out.println("User " + (i+1) + " should receive coupon " + getCoupon(finalSolution[i]) + ".");
		}
	}
	
	/**
	* Is used by displayFinalSolution, converts the partialSolution int steps into correspond Strings.
	* @param intToConvert: the int step from the finalSolution.
	* @return A corresponding string, 1=A, 2=B, etc...
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
	* Just a test method, passes in cases to check to see if isFullSolution() is working as desired.
	* HEAVILY DEPENDENT on and ASSUMES that other methods are working as desired. For example, isFullSolution only
	* checks the last step in a partialSolution because it assumes that reject() has been ran previously on earlier steps.  Unfair to pass
	* it corner cases which will never exist.
	* outputs PASS/FAILED
	*/
	public static void testIsFullSolution(){
		//TEST 1
		System.out.println("\nTesting the method isFullSolution with a full but unsolvable solution of size 8...");
		count = 8;
		userCoupon = new HashMap<>(count-1);	// Concerned with every index
		matrixArray = new int[count][count];						//only concerned with last row as that is all isFullSolution() accesses
		userCoupon.put(0, 1); userCoupon.put(1,2); userCoupon.put(2,2); userCoupon.put(3,3); userCoupon.put(4,1); userCoupon.put(5,2); userCoupon.put(6,3); userCoupon.put(7,3);
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
		userCoupon = new HashMap<>(count-1);	// Concerned with every index
		matrixArray = new int[count][count];						//only concerned with last row as that is all isFullSolution() accesses
		userCoupon.put(0, 1); userCoupon.put(1,2); userCoupon.put(2,2); userCoupon.put(3,1);
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
	* Just a test method, passes in cases to check to see if reject() is working as desired.
	* outputs PASS/FAILED
	*/
	public static void testReject(){
		//TEST 1
		System.out.println("\nTesting the method reject with an incorrect size 7 partialSolution of a solution of size 8...");
		count = 8;
		numberOfEntries = 7;
		userCoupon = new HashMap<>(count-1);	// Concerned with every index
		matrixArray = new int[count][count];	//only concerned with last row as that is all isFullSolution() accesses
		userCoupon.put(0, 1); userCoupon.put(1,2); userCoupon.put(2,2); userCoupon.put(3,3); userCoupon.put(4,1); userCoupon.put(5,2); userCoupon.put(6,3); userCoupon.put(7,0);
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
		userCoupon = new HashMap<>(count-1);	// Concerned with every index
		matrixArray = new int[count][count];						//only concerned with last row as that is all isFullSolution() accesses
		userCoupon.put(0, 1); userCoupon.put(1,2); userCoupon.put(2,2); userCoupon.put(3,0);
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
	* Just a test method, passes in cases to check to see if extend() is working as desired.
	* outputs PASS/FAILED
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
		userCoupon = new HashMap<Integer, Integer>(count);
		userCoupon.put(new Integer(4), new Integer(1)); userCoupon.put(new Integer(5), new Integer(1)); userCoupon.put(new Integer(6), new Integer(0));
		if(extend(solution) != null){
			System.out.println("	-extend has deemed that it is POSSIBLE to extend another value, the test has PASSED.");
		}else{
			System.out.println("	-extend has deemed that it is IMPOSSIBLE to extend another value, the test has FAILED.");
		}
	}
	
	/**
	* Just a test method, passes in cases to check to see if next() is working as desired.
	* outputs PASS/FAILED
	*/
	public static void testNext(){
		//TEST 1
		count = 8;
		numberOfEntries = 7;
		numberOfCoupons = 2;
		System.out.println("\nTesting the method next with a partialSolution of the last available option...");
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
		System.out.println("\nTesting the method next with a partialSolution capable of having its most recent step changed...");
		solution = new int[count];
		solution[numberOfEntries - 1] = 1;
		userCoupon = new HashMap<>(4);
		userCoupon.put(new Integer(1), new Integer(1)); userCoupon.put(new Integer(2), new Integer(1));
		if(next(solution) == null){
			System.out.println("	-next has deemed it IMPOSSIBLE to change the distributed coupon to its next type, the test has FAILED");	// We are on coupon type 1, should be able to change to maximum type 2
		}else{
			System.out.println("	-next has deemed it POSSIBLE to change the distributed coupon to its next type, the test has PASSED");
		}
	}
	
}