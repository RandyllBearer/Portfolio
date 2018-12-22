public class test{
	static String[] masterArray311 = {"LLLNS","LLLSN", "LLNLS", "LLNSL", "LLSLN", "LLSNL", "LNLLS", "LNLSL", "LNSLL", "LSLLN", "LSLNL", "LSNLL", "NLLLS", "NLLSL", "NLSLL", "NSLLL", "SLLLN", "SLLNL", "SLNLL", "SNLLL" };
	static char[] letterArray = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};	//SIZE 26
	static char[] numberArray = {'0','1','2','3','4','5','6','7','8','9'};																	//SIZE 10
	static char[] symbolArray = {'!', '@', '$', '^', '_', '*'};																				//SIZE 6
	
	public static void main (String[] args){
	test instance = new test();
		
		for(int i=0; i<20; i++){	//String in masterArray311
			StringBuilder masterPattern = new StringBuilder(masterArray311[i]);	//HOLDS STRING PATTERN "LLLNS"
			char[] masterCharacters = new char[5];
			masterPattern.getChars(0,4,masterCharacters,0);						//HOLDS CHAR PATTERN 'L','L','L','N','S'
			int masterPatternIndex = 0;		// Where am I at in the master pattern [Look up if L/N/S]
			
			instance.findPossible(masterCharacters, masterPatternIndex, "");
		}
		
	}
	
	public void findPossible(char[] pattern, int patternIndex, String prefix){
		if(patternIndex == 4){				//If we are on our last index
			System.out.println(prefix);		//WILL CHANGE TO PRINT OUT TO good_dictionary.txt
			return;
		}
		if(pattern[patternIndex] == 'L'){			//Do we need to increment a letter?
			for(int i=0; i<26; i++){
				String newPrefix = prefix + letterArray[i];
				//lookupPossible newPrefix; //if return true, good password, if return false, cannot use
				findPossible(pattern, (patternIndex+1), newPrefix);
			}
			
		}else if(pattern[patternIndex] == 'N'){		//Do we need to increment a number?
			for(int i=0; i<10; i++){
				String newPrefix = prefix + numberArray[i];
				findPossible(pattern, (patternIndex+1), newPrefix);
			}
			
		}else if(pattern[patternIndex] == 'S'){		//Do we need to increment a symbol?
			for(int i=0; i<6; i++){
				String newPrefix = prefix + numberArray[i];
				findPossible(pattern, (patternIndex+1), newPrefix);
			}
			
		}
	}
	
	//This method is really just an intermediary step
	public boolean lookupPossible(String prefixToCheck){
		boolean result = true;	//DEFAULT = TRUE = GOOD PASSWORD
		int prefixLength = prefixToCheck.length();
		
		if(prefixLength == 1){
			result = lookupPossible1(prefixToCheck, prefixLength);
		}else if(prefixLength == 2){
			result = lookupPossible2(prefixToCheck, prefixLength);
		}else if(prefixLength == 3){
			result = lookupPossible3(prefixToCheck, prefixLength);
		}else if(prefixLength == 4){
			result = lookupPossible4(prefixToCheck, prefixLength);
		}else if(prefixLength == 5){
			result = lookupPossible5(prefixToCheck, prefixLength);
		}
		
		return result;
	}
	
	
	public boolean lookupPossible1(String prefixToLookup, int length){
		boolean result = true;	//DEFAULT = TRUE = GOOD PASSWORD
		StringBuilder temp = new StringBuilder(prefixToLookup);
		char character = temp.charAt(0);
		
		
		
		
		return result;
	}
	
	public boolean lookupPossible2(String prefixToLookup, int length){
		boolean result = true;
		
		
		
		return result;
	}
	
	public boolean lookupPossible3(String prefixToLookup, int length){
		boolean result = true;
		
		
		return result;
	}
	
	public boolean lookupPossible4(String prefixToLookup, int length){
		boolean result = true;
		
		
		return result;
	}
	
	public boolean lookupPossible5(String prefixToLookup, int length){
		boolean result = true;
		
		
		return result;
	}
}














