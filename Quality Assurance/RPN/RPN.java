import java.math.BigInteger;	//https://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html
import java.util.*;	
import java.io.*;
import java.nio.charset.StandardCharsets;

/*
*
*/
public class RPN {
	//implementation of global variables
	//https://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html
	private static HashMap<String, BigInteger> variables = new HashMap<String, BigInteger>();	
	private static int lineNum = 0;
	private static boolean errorFlag = false;
	private static int errorCode = 0;
	
	//------------ functional methods ----------------
		
	/*
	* Loop through files, evaluating each line
	* read() / eval() each string
	* Mode = 0
	* returns true on successful evaluation
	* returns false on unsuccessful evaluation
	*/
	public static boolean readFiles(String[] args) {
		String line, resultString;
		String[] resultTokens;
		
		for(String uments:args){
			BufferedReader inFile = openFile(uments);
			
			if(inFile == null){
				errorCode = 5;
				return false;
			}
			
			try{
				while((line = inFile.readLine()) != null){
					resultTokens = read(line,0);
					if (resultTokens.length > 0 && !resultTokens[0].equals("")) {
						resultString = eval(resultTokens, 0);
						
						if (resultString.equals("QUIT")) {
							errorCode = 0;
							return true;
						}
						if (!resultString.equals("")) {
							print(resultString, 1);
						}
						if(errorFlag){
							errorCode = 5;
							return false;
						}
						
					}
				}
			}catch(IOException ioe){
				System.out.println("Error occurred while trying to read " + uments);
				errorCode = 5;
				return false;
			}
		}
		
		errorCode = 0;
		return true;
	}

	/*
	* Read(), Eval(), Print()
	* Break out of loop if "QUIT" is consumed
	* Mode = 1
	*/
	public static void runLoop() {
		while (true) {
			
			String[] resultTokens = read("",1);
			
			String resultString = "";
			if (resultTokens.length > 0 && !resultTokens[0].equals("")) {
				resultString = eval(resultTokens, 1);
			}else{
				System.out.print("Line " + lineNum + ": No Input");
			}
			
			if (resultString.equals("QUIT")) {
				break;
			}
			
			print(resultString, 1);
				
		}
	}
	
	/*
	* Method for reading file path and checking for errors
	* BufferedReader is returned if file is valid
	* If any file is invalid, the program exits
	*/
	public static BufferedReader openFile(String path){
		BufferedReader inFile = null;
		try{
			File newFile = new File(path);
			InputStream inputStream = new FileInputStream(newFile);
			Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
			inFile = new BufferedReader(reader);
			
			//inFile = new BufferedReader(new FileReader(path));
		}catch(FileNotFoundException fne){
			System.err.println("File " + path + " could not be read.  Exiting...");
			return null;
		}
		return inFile;
	}
	
	/*
	* Reads in a line of input from the user
	* Splits it by a whitespace delimiter
	* returns the array of split strings
	*/
	public static String[] read(String inputStr, int mode) {
		//variables
		String[] tokens = null;
		
		//logic
		if(mode==0){
			String fileString = inputStr.toUpperCase(Locale.ENGLISH);
			tokens = fileString.split(" ");
		}
		else if(mode==1){
			Scanner scanner = new Scanner(System.in, "UTF-8");
			System.out.print("> ");
			
			String userString = scanner.nextLine();
			userString = userString.toUpperCase(Locale.ENGLISH);	//allow for case-insenstivity
			tokens = userString.split(" ");
		}
		
		//return
		lineNum = lineNum + 1;
		return tokens;
	}

	/*
	* Accepts an array of split strings, interpret one at a time
	* if num, push to stack / variable, act accordingly / keyword, act accordingly
	* return the String of what should be printed
	*/
	public static String eval(String[] toEval, int mode) {
		//variables
		String result = "";
		Stack stack = new Stack();
		String currentToken = "";
		BigInteger number = null;
		boolean letFlag = false;	//set true if "LET" keyword was parsed
		String toChange = "";
		errorFlag = false;			//set true if need to print to stderr
		
		//logic
		int i = 0;
		while (i < toEval.length) {
			currentToken = toEval[i];
		
			try {
				//if we can cast it as a number, it is a number
				number = new BigInteger(toEval[i]);
				stack.push(number);
				
			} catch (NumberFormatException nfe) {
				int toCheck = (int)currentToken.charAt(0);
				
				if (currentToken.equals("LET") && i == 0) {					
					//let must be followed by variable + values
					i = i + 1;
					if (i + 1 >= toEval.length) {
						result = "Line " + lineNum + ": Operator LET applied to empty stack";
						errorFlag = true;
						errorCode = 2;
						return result;
					}
					
					toChange = toEval[i];
					
					if (toChange.length() != 1 || ( toCheck < 65 || toCheck > 90 ) ) {
						result = "Line " + lineNum + ": Operator LET applied to empty stack";
						errorFlag = true;
						errorCode = 2;
						return result;
					}
					letFlag = true;
					
				} else if (currentToken.equals("PRINT") && i == 0 ) {
					//do nothing
					
				} else if (currentToken.equals("QUIT") && i == 0 ) {
					return "QUIT";
					
				} else if (currentToken.equals("LET") && i != 0 ) {
					result = "Line " + lineNum + ": Could not evaluate expression";
					return result;
					
				} else if (currentToken.equals("PRINT") && i != 0 ) {
					result = "Line " + lineNum + ": Could not evaluate expression";
					return result;
					
				} else if (currentToken.equals("QUIT") && i != 0 ) {
					result = "Line " + lineNum + ": Could not evaluate expression";
					return result;
					
				} else if (currentToken.equals("+") ) {
					try {
						BigInteger second = (BigInteger)stack.pop();
						BigInteger first = (BigInteger)stack.pop();
						stack.push(first.add(second) );
						
					} catch (EmptyStackException ese) {
						result = "Line " + lineNum + ": Operator + applied to empty stack";
						errorFlag = true;
						errorCode = 2;
						return result;
					}
					
				} else if (currentToken.equals("-") ) {
					try {
						BigInteger second = (BigInteger)stack.pop();
						BigInteger first = (BigInteger)stack.pop();
						stack.push(first.subtract(second) );
						
					} catch (EmptyStackException ese) {
						result = "Line " + lineNum + ": Operator - applied to empty stack";
						errorFlag = true;
						errorCode = 2;
						return result;
					}
					
				} else if (currentToken.equals("/") ) {
					try {
						BigInteger second = (BigInteger)stack.pop();
						BigInteger first = (BigInteger)stack.pop();
						stack.push(first.divide(second) );
						
					} catch (EmptyStackException ese) {
						result = "Line " + lineNum + ": Operator / applied to empty stack";
						errorFlag = true;
						errorCode = 2;
						return result;
					}
					
				} else if (currentToken.equals("*") ) {
					try {
						BigInteger second = (BigInteger)stack.pop();
						BigInteger first = (BigInteger)stack.pop();
						stack.push(first.multiply(second) );
						
					} catch (EmptyStackException ese) {
						result = "Line " + lineNum + ": Operator * applied to empty stack";
						errorFlag = true;
						errorCode = 2;
						return result;
					}
					
				} else if ( currentToken.length() == 1 && toCheck > 64 && toCheck < 91 ) {
					BigInteger variable = variables.get(currentToken);
					if (variable == null) {
						result = "Line " + lineNum + ": Variable ";
						result = result + currentToken + " is not initialized";
						errorFlag = true;
						errorCode = 1;
						return result;
					}
					stack.push(variable);
					
				} else {
					result = "Line " + lineNum + ": Unknown keyword " + currentToken;
					errorFlag = true;
					errorCode = 4;
					return result;
				}
				
			}
			
			
			i = i + 1;
		}
		
		//return
		if (stack.size() > 1) {
			result = "Line " + lineNum + ": " + stack.size() + " elements in stack after evaluation";
			errorFlag = true;
			errorCode = 3;
			return result;
		}
		
		BigInteger resultant = (BigInteger)stack.pop();
		
		if (letFlag == true) {
			//write to variable
			variables.put(toChange, resultant);
			if(mode==0){
				return "";
			}
		}
		
		result = resultant.toString();
		return result;
	}

	/*
	* Accepts a string and prints it back to user
	*/
	public static void print(String toPrint, int mode) {
		
		if (errorFlag == true) {
			System.err.println(toPrint);
			
		} else {
			System.out.println(toPrint);
		}
		
	}

	/*
	* Main method
	* Determine which mode to run the program in by checking if arguments exist
	* If no arguments, run REPL
	* If arguments, read in from files
	*/
	public static void main(String[] args) {
		try {
			if (args.length == 0) {
			runLoop();
			
			} else {
				readFiles(args);
			}
			
			System.exit(errorCode);
			
		}catch (Exception e) {
			System.err.println("ERROR: Unexpected error occured, program will now exit...");
			System.exit(5);
		}
		
	}
	
	//------------ debug code ---------------------
	
	/*
	* To be used as a way to test the runLoop() function
	*/
	public static ArrayList<String> debugRunLoop(int i) {
		//will have to have eval() consume a 'QUIT' keyword to break this loop'
		ArrayList<String> debugOutput = new ArrayList<String>();
		lineNum = 0;
		int j = 0;
		while (j < i) {
			
			//https://docs.oracle.com/javase/7/docs/api/java/util/Stack.html
			String[] resultTokens = read("", 1);
			
			String resultString = "";
			if (resultTokens.length > 0) {
				resultString = eval(resultTokens, 1);
			}
			if (resultString.equals("QUIT")) {
				break;
			}
			debugOutput.add(resultString);
			
			print(resultString, 1);
			
			j = j + 1;
		}
		
		return debugOutput;
	}
	
	/*
	* To be used for testing different modes between repl / files
	*/
	public static void setErrorFlag (boolean toSet) {
		errorFlag = toSet;
	}
	
	/*
	* To be used for setting lineNum to predict error output messages
	*/
	public static void setLineNum (int num) {
		lineNum = num;
	}
	
	/*
	* To be used for resetting variable initializations;
	*/
	public static void resetHashmap () {
		variables = new HashMap<String, BigInteger>();
	}
	
	//end of program
}

