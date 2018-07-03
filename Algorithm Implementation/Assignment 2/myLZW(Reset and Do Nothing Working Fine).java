import java.util.Scanner;

/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/
 //STRINGBUILDER.REPLACE(0,5,"") WILL BE FASTER THAN STRING.SUBSTRING(5);
public class myLZW {
    private static final int R = 256;        				// number of input chars
    private static int W = 9;         				// codeword width
	private static int L = (int)Math.pow(2,W);      	// number of codewords = 2^W [DEFAULT 512]
	private static char mode =']';							// do nothing, reset, or monitor? [DEFAULT = ']' ]

    public static void compress() { 
        String input = BinaryStdIn.readString();	//Read in the entire file + system.out prints to the std.out not through command prompt
        TST<Integer> st = new TST<Integer>();		// Each Node will hold an Integer value, 
		
		System.out.print(mode);
		
        for (int i = 0; i < R; i++)			//This loop goes through and adds R nodes to support our basic ASCII encoding.
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF = reserve the R codeword for the end of the file in decompression. [WILL BE STARTING AT 257]		
		
        while (input.length() > 0) {	//Iterate through the file
            String s = st.longestPrefixOf(input);  // Find max prefix match s.	RETURNS A STRING
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding. [ST.get would return the i in the previous for loop]
            int t = s.length();					//length of the longest found prefix
			
			if(t<input.length() && code >= L && W == 16 && (mode == 'r' || mode == 'R')){	//If we are in reset mode, then reset
				//if(true) throw new IllegalArgumentException ("We made it to RESET point: code = " + code + " W = " + W + " L = " + L);	//TEST
				W = 9;
				L = (int)Math.pow(2,W);
				st = new TST<Integer>();
				
				for(int i=0; i<R; i++){
					st.put("" + (char)i,i);
				}
				code = R+1;				
			}else if(t<input.length() && code >= L && W < 16){	//Upgrade our codeword length [We are about to add to 512 [so 512 will be filled with a w+1 bitlength codeword]]
				W = W+1;
				L = (int)Math.pow(2,W);		//Don't need to expand an array or anything else since the TST will exand regardless of codewordBitLength
			}
			
            if (t < input.length() && code < L){	// Add s to symbol table as long as it isn't longer than the remaining characters and we still have room in our array.
				st.put(input.substring(0, t + 1), code++); //Take this prefix and add it to the TST 
            }
			   
            input = input.substring(t);            // Scan past s in input. [STRINGBUILDER.REPLACE(0,T+1,"") WOULD BE FASTER]
        }
        BinaryStdOut.write(R, W);	//prints out our end of file codeword with the appropriate amount of bit length
        BinaryStdOut.close();
    } 


    public static void expand() {
		
		mode = BinaryStdIn.readChar(8);		//Reads in what our mode of encoding was and progresses the scanner/pointer

        String[] st = new String[L];		//An array holding our values where index=key	[if the encoded is 322, then we would go to index 322 and write its value]
        int i; // next available codeword value [where we are about to store]

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF	RESERVE A SPOT FOR END OF FILE	[st[] holds 256 currently in index 0-255 [ABOUT TO STORE IN 257]]
		
        int codeword = BinaryStdIn.readInt(W);	//Read in # of bits = codeword bit length + move the pointer forward that many
        if (codeword == R) return;           // expanded message is empty string {TERMINATE EXAPNSION}
        String val = st[codeword];			//val is the String at index codeword 	[if not added previous, null]

        while (true) {
           
			if(i == L && W == 16 && (mode == 'r' || mode == 'R')){	//If we are about to reset our array
				W = 9;
				L = (int)Math.pow(2,W);
				st = new String[L];
				i = 257;
				for(int j=0; j<R; j++){
					st[j] = "" + (char) j;
				}
			}else if(i >= L && W < 16){		//When i = 512/4096, so when we are about to store in array index 512 [512 is holding null at the end of this if/statement]
				//if(i >= L) throw new IllegalArgumentException("HEY WE MADE IT HERE:   i = " + i + " W = " + W + " L = " + L);	//TEST
				W = W + 1;
				L = (int)Math.pow(2,W);
				String[] tempArray = new String[L];
				for(int j =0; j< (int)Math.pow(2,W-1); j++){
					tempArray[j] = st[j];
				}
				st = tempArray;
				//if(true) throw new IllegalArgumentException("HEY WE MADE IT HERE:   i = " + i + " W = " + W + " L = " + L + "WHAT IS HELD IN INDEX I-1 = " + st[i-1] + "WHAT IS HELD IN INDEX I = " + st[i]);	//TEST
			}
			
			BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);	//Read in # of bits again 

			
            if (codeword == R) break;			//if this is our end of file signal terminae
            String s = st[codeword];			//s = st.index[codeword] [s is basically our [next] string]
            if (i == codeword) s = val + val.charAt(0);   // special case hack [if the codeword is == to the location we are about to store it to [i.e. the codeword does not yet exist] do the hack
            if (i < L) st[i++] = val + s.charAt(0);		  // otherwise just add the new codeword as long as we have space
			if (i == L){
				
			}
            val = s;							// set our current to = our next
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
        if (args[0].equals("-")){
			if(args[1].equals(null)){
				throw new IllegalArgumentException("You must specify which mode you want to compress with [Do Nothing // Reset // Monitor");
			}
			if(args[1].equals("n") || args[1].equals("N")){
				mode = 'n';
			}else if(args[1].equals("r") || args[1].equals("R")){
				mode = 'r';
			}else if(args[1].equals("m") || args[1].equals("M")){
				mode = 'm';
			}else{
				throw new IllegalArgumentException("A valid mode must be entered as argument [n/r/m]");
			}
			compress();
		}
        else if (args[0].equals("+")){
			expand();
			
		}
        else throw new IllegalArgumentException("Illegal command line argument");
	}

}
