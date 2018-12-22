//Imports
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;


//Accepts a single String argument denoting the path of the desired file.
//Element.java parses that file and for every line attempts to split it
//into its corresponding abbreviation of elements. If no such split can be
//done, Element.java informs the user.
public class Element{
	
	//Initiate a FileReader and BufferedReader object using the filePath argument
	//Catches any exceptions these operations might throw.
	public static BufferedReader openFile(String path){
		BufferedReader inFile = null;
		try{
			inFile = new BufferedReader(new FileReader(path));
		}catch(FileNotFoundException fne){
			System.out.println("ERROR: Element.java could not find the following file: " + path);
			System.exit(2);
		}
		return inFile;
		
	}
	
	
	//Fills hmap with the contents of elements.txt
	//key = abbreviation, value = element name
	public static HashMap<String, String> getElementMap(BufferedReader inFile){
		HashMap<String, String> hmap = new HashMap<String, String>();
		String line;
		
		try{
			while((line = inFile.readLine()) != null){
				//Regular Expression: words[1] holds abbreviation / words[2] holds element name
				String[] words = line.split("[^\\w']+");
				words[1] = words[1].toUpperCase();	//must also make our input string toUpperCase()
				hmap.put(words[1], words[2]);
			}
		}catch(IOException ioe){
			System.out.println("ERROR: Element.java could not read elements.txt");
			System.exit(3);
		}
		
		return hmap;
	}
	
	
	/*
	Creates the array list of atom abbreviations
	Returns the list for use in unit testing
	The list is attempted to be built forward using the stepping algorithm
	If and only if this fails, the list is attempted to be built backwards using a recursive call
	The direction depends on the 'forward' boolean flag passed to this function
	By attempting the same steps forward and backward all permutations should be covered
	*/
	public static ArrayList<String> getAbbreviations(String _line, HashMap<String, String> hmap, boolean forward){
		ArrayList<String> _a = new ArrayList<String>();
		String testLine, result;
		
		// removes of all non-alphabetical characters
		// eliminates case sensitivity
		_line = _line.replaceAll("[^A-Za-z]+","");	
		_line = _line.toUpperCase();
		
		// starting point chosen based on direction
		int startIndex = 0;
		int endIndex = 2;
		if(!forward){
			startIndex = _line.length()-2;
			endIndex = _line.length();
		}
		
		// Choose to apply the algorithm in the forward or backward
		// direction.  Sometimes only one will work, so it has to be
		// possible to check both using this function
		if(forward){
			while(startIndex < _line.length()){
				// get a group of one or two letters to test
				testLine = _line.substring(startIndex, endIndex);
				result = hmap.get(testLine);
				
				if(result != null){
					// add element to list if it exists
					_a.add(testLine);
					
					// set new indices
					startIndex = endIndex;
					endIndex = startIndex + 2;
					if(endIndex > _line.length()){endIndex=_line.length();}
				}else{
					// if there is no abbreviation, return an empty list
					if(endIndex - startIndex == 1){
						_a = getAbbreviations(_line, hmap, false);
						return _a;
					}else{
						//try a 1 letter combination if the 2 letter doesn't exist
						endIndex = endIndex - 1;
					}
				}
			}
		}else{
			while(startIndex >= 0){
				// get a group of one or two letters to test
				testLine = _line.substring(startIndex, endIndex);
				result = hmap.get(testLine);
				
				if(result != null){
					// add element to list if it exists
					_a.add(0,testLine);
					
					// set new indices
					endIndex = startIndex;
					if(startIndex == 1){startIndex-=1;}
					else{startIndex-=2;}
				}else{
					// if there is no abbreviation, return an empty list
					if(endIndex - startIndex == 1){	
						_a.clear();
						return _a;
					}else{
						//try a 1 letter combination if the 2 letter doesn't exist
						startIndex = startIndex + 1;
					}
				}
			}
		}
		
		return _a;
	}
	
	
	// Method used a valid abbreviation list to build a string output for the abbreviations
	// Error checks that the element abbreviations exist using the hashmap
	public static String buildAbbreviationString(ArrayList<String> _a, HashMap<String, String> _h){
		String _ba = "";
		
		if(_a.isEmpty()){
			_ba = "";
			return _ba;
		}
		
		for(int i = 0; i < _a.size(); i++) {
			if(_h.get(_a.get(i)) == null){
				_ba = "";
				return _ba;
			}
			String toAdd = _a.get(i);
			if(i==0){
				if(toAdd.length() == 2){
					String partA = toAdd.substring(0,1);
					String partB = toAdd.substring(1,2);
					partB = partB.toLowerCase();
					_ba += partA+partB;
				}else{
					_ba += toAdd;
				}
			}else{
				toAdd = _a.get(i);
				if(toAdd.length() == 2){
					String partA = toAdd.substring(0,1);
					String partB = toAdd.substring(1,2);
					partB = partB.toLowerCase();
					_ba += " - " + partA+partB;
				}else{
					_ba += " - " + toAdd;
				}
			}
		}
		
		return _ba;
	}
	
	
	// Method uses a valid abbreviation list to build a string output for the elements
	// Error checks that the element abbreviations exist using the hashmap
	public static String buildElementString(ArrayList<String> _a, HashMap<String, String> _h){
		String _be = "";
		
		if(_a.isEmpty()){
			_be = "";
			return _be;
		}
		
		_be += "\n" + _h.get(_a.get(0));
		for (int i = 1; i < _a.size(); i++) {
			if(_h.get(_a.get(i)) == null){
				_be = "";
				return _be;
			}else{
				_be += " - " + _h.get(_a.get(i));
			}
		}
		
		return _be;
	}
	
	
	// the run method iterates over lines in the file to generate the appropriate output
	public static void run(BufferedReader inElements, BufferedReader inFile){
		HashMap<String, String> hmap = new HashMap<String, String>();
		ArrayList<String> abbreviations = new ArrayList<String>();
		String line;
		
		// uses the imported element data to create a hashmap
		hmap = getElementMap(inElements);
		
		try{
			while((line = inFile.readLine()) != null){
				System.out.println("\n");	//Just for cleaner formatting
				
				// get the abbreviations and reset elements list
				abbreviations = getAbbreviations(line, hmap, true);
				
				// If this line in the file returns valid abbreviations, print the line
				// Else, print that the line could not be built
				if(!abbreviations.isEmpty()){
					// Attempt to build the output strings.
					// If this cannot be done, print an error
					String p1 = buildAbbreviationString(abbreviations, hmap);
					String p2 = buildElementString(abbreviations, hmap);
					if(p1 == "" || p2 == ""){
						System.out.println("An error occured.  Could not create name '"+line+"'");
					}else{
						String output = p1 + "\n" + p2;
						System.out.println(output);
					}
				}else{
					System.out.println("\nCould not create name '"+line+"' out of elements.");
				}
			}
		}catch(IOException ioe){
			System.out.println("ERROR: Element.java could not read user input file");
			System.exit(2);
		}
	}
	
	
	//The main method - initializes the program
	public static void main(String[] args){
		String filePath = "";
		
		//Get Arguments and handle input errors
		if(args.length != 1){
			System.out.println("ERROR: Element.java can only accept one String argument defining file path.");
			System.exit(1);
		}
		try{
			filePath = args[0];
		}catch(Exception e){
			System.out.println("ERROR: Element.java cannot recognize that String file path");
			System.exit(2);
		}
		
		// Open both the elements file and the user specified input file
		// Error handling occurs in the openFile() method
		BufferedReader inUser = openFile(filePath);
		BufferedReader inElements = openFile("elements.txt");
		
		// runs the program using the user input
		run(inElements, inUser);
		
		//handle errors associated with closing the filereader
		//and closes any open files.
		try{
			inElements.close();
			inUser.close();
		}catch(IOException ioe){
			System.out.println("ERROR: Element.java could not close FileReader");
			System.exit(3);
		}
		
		System.exit(0);
	}
	
}