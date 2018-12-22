import java.util.ArrayList;

/*
* Accepts a dynamic number of arguments and then displays a full coverage
* pairwise combination truth table.
*/
public class Pairwise {
	
	/**
	* Concatenates the raw strings to length <= 10 passed as input into the program.
	* @param arguments String[] holding the raw strings passed to the program as arguments
	* @return The Concatenated (length <= 10) String Arguments passed to the program.
	*/
	public static ArrayList<String> parseInput(String[] arguments) {
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.clear();
		
		if (arguments.length < 2) {
			return parameters;
		} else {
			int numParameters = arguments.length;
			int i = 0;
			while (i < numParameters) {
				if (arguments[i].length() > 10) {
					arguments[i] = arguments[i].substring(0, 10);
				}
				parameters.add(arguments[i]);
				i = i + 1;
			}
		}
		
		return parameters;
	}
	
	/**
	* Creates an exhaustive truth table to then be shrunk down by findToDisplay()
	* @param l number of rows in the truth table, numParams^2
	* @param w number of parameters
	* @return An exhaustive truth table in the form of a 2D array
	*/
	public static int[][] buildOverallTruthTable(int l, int w) {
		if ( w > l ) {
			return null;
		}
		
		int[][] table = new int[l][w];
		
		for (int i = 0; i < l; i++) {
			if (i == 0) {
				for (int j = 0; j < w; j++) {
					table[i][j] = 0;
				}
			} else {
				for (int j = w - 1; j >= 0; j--) {
					if (j == w - 1) {
						if (table[i - 1][j] == 0) {
							table[i][j] = 1;
						} else {
							table[i][j] = 0;
						}
					} else {
						//have to do this to cut down on line count
						int a = i - 1;
						int b = j + 1;
						int c = table[a][b];
						if (c == table[i][b] || (c == 0 && table[i][b] == 1)) {
							table[i][j] = table[i - 1][j];
						} else {
							if (table[i - 1][j] == 0) {
								table[i][j] = 1;
							} else {
								table[i][j] = 0;
							}
						}
					}
				}
			}
		}
		
		return table;
	}
	
	/**
	*Recieves an exhaustive truth table and returns an ArrayList containing the
	*indices of the exhaustive truth table which should be displayed to the user to provide
	*full pairwise test coverage.
	*@param table an exhaustive truth table
	*@param numRows number of rows in the exhaustive truth table
	*@param numParameters number of parameters/to test
	*@return contains the indices to display to user
	*@throws RuntimeException when findToDisplay fails to find full coverage [shouldn't happen]
	*/
	public static ArrayList<Integer> findToDisplay(int[][] table, int numRows, int numParameters) {
		ArrayList<Integer> toDisplay = new ArrayList<Integer>();
		int first = 0;	//index of parameters<>
		int second = 1;	//index of parameters<>
		
		while (second < numParameters) {
			
			boolean ff = false;
			boolean ft = false;
			boolean tf = false;
			boolean tt = false;
			int row = 0;	//which row we're on in table
			if (first == 0 && second == 1) {
				ff = true;
				tt = true;
				toDisplay.add(0);
				toDisplay.add(numRows - 1);
				while (!ff || !ft || !tf || !tt) {
					if (row >= numRows) {
						throw new RuntimeException("ERROR: Could not find pair coverage");
					}
					
					//Exhaustive Checking
					if (table[row][first] == 0 && table[row][second] == 1 && ft == false) {
						ft = true;
						toDisplay.add(row);
						
					} else if (table[row][first] == 1 && table[row][second] == 0 && tf == false) {
						tf = true;
						toDisplay.add(row);
						
					}
					row = row + 1;
				}
				
			} else {
				int flag = 0;
				while (!ff || !ft || !tf || !tt) {
					if (row > numRows) {
						throw new RuntimeException("ERROR: Could not find pair coverage");
					}
					
					if (flag == 0) {
						//Check toDisplay First
						int i = 0;
						while (i < toDisplay.size()) {
							int a = table[toDisplay.get(i)][first];
							int b = table[toDisplay.get(i)][second];
							if ( a == 0 && b == 0 && ff == false) {
								ff = true;
							
							} else if (a == 0 && b == 1 && ft == false) {
								ft = true;
								
							} else if (a == 1 && b == 0 && tf == false) {
								tf = true;
								
							} else if (a == 1 && b == 1 && tt == false) {
								tt = true;
								
							}
							
							i = i + 1;
						}
						
						flag = 1;
						
					} else {
						//Exhaustive Checking
						int a = table[row][first];
						int b = table[row][second];
						if (a == 0 && b == 0 && ff == false) {
							ff = true;
							toDisplay.add(row);
						
						} else if (a == 0 && b == 1 && ft == false) {
							ft = true;
							toDisplay.add(row);
							
						} else if (a == 1 && b == 0 && tf == false) {
							tf = true;
							toDisplay.add(row);
							
						} else if (a == 1 && b == 1 && tt == false) {
							tt = true;
							toDisplay.add(row);
							
						}
						
						row = row + 1;
						
					}
					
				}
					
			}
			
			//update the indices we are comparing
			second = second + 1;
			if (second == numParameters) {
				first = first + 1;
				second = first + 1;
			}
		
		}
		
		return toDisplay;
	}
	
	/**
	* Builds the string which should then be displayed to the user
	* @param cats The concatenated list of parameters passed to the program
	* @param rows the indices which should be displayed from the exhaustive truth table
	* @param table the exhaustive truth table
	* @return A String which should be displayed to the user
	*/
	public static String buildOutput(ArrayList<String> cats, ArrayList<Integer> rows, int[][] table) {
		StringBuilder out = new StringBuilder("");
		ArrayList<Integer> toTabTwice = new ArrayList<Integer>();
		
		int j = 0;
		while (j < cats.size() ) {
			String temp = cats.get(j);
			if (temp.length() == 10 ) {
				toTabTwice.add(j);
			}
			out.append(temp + "\t");
			j = j + 1;
		}
		out.append("\n");

		for (int r:rows) {
			for (int i = 0; i < table[r].length; i++) {
				if (toTabTwice.contains(i) ) {
					out.append(Integer.toString(table[r][i]) + "\t\t" );
				} else {
					out.append(Integer.toString(table[r][i]) + "\t");
				}
			}
			out.append("\n");
		}


		return out.toString();
	}
	
	/**
	* Accepts a dynamic amount of string categories/parameters from the user
	* Displays a full pairwise test coverage truth table of those inputs
	*/
	public static void main(String[] args) {
		//Parse Inputs, concatenate if > 10
		ArrayList<String> parameters = new ArrayList<String>();
		parameters = parseInput(args);
		
		if (!parameters.isEmpty()) {
			//Build Exhaustive Truth Table, #param^2 by #param
			int w = parameters.size();
			int l = (int) Math.pow(2, w);
			
			int[][] table;
			table = buildOverallTruthTable(l, w);
			if ( table == null ) {
				throw new RuntimeException("ERROR: Truth table cannot have more parames than rows!");
			}
			
			//Find all toDisplay Tests
			ArrayList<Integer> toDisplay = new ArrayList<Integer>();
			toDisplay = findToDisplay(table, l, w);
			
			//Build Program Output
			String printMe;
			printMe = buildOutput(parameters, toDisplay, table);
			System.out.println(printMe);
		} else {
			System.out.println("ERROR: Invalid Arguments. \nAt least two arguments are required.");
			System.exit(1);
		}
		
		//Exit Main
		System.exit(0);
	}	
}