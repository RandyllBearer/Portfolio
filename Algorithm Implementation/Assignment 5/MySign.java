import java.math.BigInteger;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
/**
* Signs files with an RSA encrypted hash function or Verifies an already signed file.
* Assumes certain files exist within current working director, privkey.rsa and pubkey.rsa, will terminate without these
* Utilizes BigInteger and MessageDigest
* reads from privkey.rsa and pubkey.rsa throught ObjectInputStream
* Author: Randyll Bearer			rlb97
**/


public class MySign{
	
	public static void main(String[] args){
		String flag = args[0];		//Either 'V' or 'S'
		String filename = args[1];	//Will create a file object out of this
		boolean flag2 = false;
		boolean flag3 = false;
		Scanner systemIn = new Scanner(System.in);		//read in user input
		MessageDigest md;								//Creates hash value
		byte[] digest;
		byte[] data;
		
		try{
			md = MessageDigest.getInstance("SHA-256");	//using the SHA-256 algorithm
			
			//Verify correct flag
			do{
				if(flag.equalsIgnoreCase("V") || flag.equalsIgnoreCase("S")){
					flag3 = true;
				}else{
					System.out.println("\nERROR: Valid flag argument not passed, please enter 'V' if you would like to verify or 'S' if you would like to sign... ");
					System.out.print("Desired Flag: ");
					flag = systemIn.nextLine();
					flag3 = false;
				}
			}while(flag3 == false);
			//Guarantee that the file we are reading from exists and establish a digest
			do{
				try{
					File loadFile = new File(filename);
					Scanner loadIn = new Scanner(loadFile);	//Create a scanner just because I know it throws exceptions if it doesn't exist, why not use it to check?
					flag2 = true;
				}catch(IOException ioe){
					System.out.println("\nERROR: That file location could not be read from, please verify file location...");
					System.out.print("Please enter file location: ");
					filename = systemIn.nextLine();
					flag2 = false;
				}
			}while(flag2 == false);
			
			//Verify and Sign
			if(flag.equalsIgnoreCase("V")){	//VERIFY
			
				System.out.println("Verifying File...");
				try{
					//Read in the signature from the start of the file
					File file = new File(filename);
					Scanner fileIn = new Scanner(file);
					String toEncrypt = fileIn.nextLine();	//toEncrypt SHOULD be our signature in HexaDecimal
					
					//Read in everything from file and using correct offset (257 bytes) update the digest
					Path filepath = new File(filename).toPath();
					data = Files.readAllBytes(filepath);	//Byte array holding everything from file
					int size = (int) Files.size(filepath);
					md.update(data, 257, size-257);	//Should be correct offset, 256 characters + newline
					digest = md.digest();
					BigInteger hashValue = new BigInteger(1,digest); //Truncates 0's, this really shouldn't matter though
					
					//Get E and N and decrypted from pubkey.rsa
					FileInputStream fileInputStream = new FileInputStream(new File("pubkey.rsa"));
					ObjectInputStream input = new ObjectInputStream(fileInputStream);
					BigInteger e = (BigInteger) input.readObject();	//E
					BigInteger n = (BigInteger) input.readObject(); //N
					BigInteger encrypt = new BigInteger(toEncrypt, 16);	//turns our hexadecimal string into an integer
					BigInteger result = encrypt.modPow(e,n);	//When converted to string, this should = toEncrypt
					
					//Display the two values
					System.out.println("\nGiven = " + result.toString(16));		//TEST
					System.out.println("\nResult = " + hashValue.toString(16));	//TEST
					
					//Verify if the two keys are the same
					if(result.toString(16).equals(hashValue.toString(16)) ){
						System.out.println("\nVALID: The Generated Hash Value and decrypted Signature ARE the same...");
					}else{
						System.out.println("\nINVALID: The Generated Hash Value and Decrypted Signature are NOT the same...");
					}
					System.exit(0);
					
				}catch(IOException ioe){
					System.out.println(ioe);
					System.exit(0);
				}catch(ClassNotFoundException cnfe){
					System.out.println(cnfe);
					System.exit(0);
				}
				
			}else if(flag.equalsIgnoreCase("S")){	//SIGN
				
				System.out.println("Signing File...");
				try{
					//Read in everything from unsigned file
					Path filepath = new File(filename).toPath();
					data = Files.readAllBytes(filepath);	//data = byte array holding everything from file.
					md.update(data);	//Update message digest with file contents
					digest = md.digest();	//Hash of file, will be 256 bits = 64 bytes
					BigInteger hashValue = new BigInteger(1, digest );	//THIS TRUNCATES THE ZEROS, but it shouldn't impact anything
					
					//Get D and N from privkey.rsa and decrpyt the hash value
					FileInputStream fileInputStream = new FileInputStream(new File("privkey.rsa"));
					ObjectInputStream input = new ObjectInputStream(fileInputStream);
					BigInteger d = (BigInteger) input.readObject();	//D
					BigInteger n = (BigInteger) input.readObject();	//N
					BigInteger result = hashValue.modPow(d,n);	//Here is our decrypted hash, what we will be writing to file in hexadecimal format as our signature	
					
					//Ensure proper formatting of result (WE WANT TO BE ABLE TO ASSUME THE LENGTH OF SIGNATURE)
					String stringResult = result.toString(16);	//Save it in hexadecimal
					while(stringResult.length() != 256){
						stringResult = "0" + stringResult;
					}
					stringResult = stringResult + "\n";	//THIS SHOULD BE 257 BYTES in a file
					byte[] decryptedString = stringResult.getBytes();
					
					//Start writing to file
					filepath = new File(filename + ".signed").toPath();
					Files.write(filepath, decryptedString);	//Will create/overwrite/whatever
					Files.write(filepath, data, StandardOpenOption.APPEND);	//Appends afterwards
					
				}catch(IOException ioe){
					System.out.println(ioe);
					System.exit(0);
				}catch(ClassNotFoundException cnfe){
					System.out.println(cnfe);
					System.exit(0);
				}
			}
			
			System.out.println("Signature complete!");
			
		}catch(NoSuchAlgorithmException nsae){
			System.out.println("ERROR: THIS SHOULD NOT HAPPEN....");	//If SHA-256 doesn't exist, we're gonna have a bad time
		}
		
	}
	
}