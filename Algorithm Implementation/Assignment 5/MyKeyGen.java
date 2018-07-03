import java.math.BigInteger;
import java.util.Random;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
*Generates new RSA public and private key pairs, and saves them to separate files as serialized BigInteger objects.
*Author: Randyll Bearer			RLB97@pitt.edu
**/

public class MyKeyGen{
	
	public static void main(String[] args){
		System.out.println("Generating pubkey.rsa and privkey.rsa...");
		
		Random random = new Random();
		BigInteger p = BigInteger.probablePrime(512, random);	//Creates the first sufficiently large and random prime.
		Random random2 = new Random();
		BigInteger q = BigInteger.probablePrime(512, random2);	//Creates a second sufficiently large and random prime.
		BigInteger n = p.multiply(q);							//Generates N, which will be saved to both pub and priv files.
		
		//Create necessary variables for RSA (P,Q,N,E,D)
		while(n.bitLength() != 1024){		//Ensure that our bitlength is actually 1024 (If too small it can be 1023, don't want this)
			random = new Random();
			random2 = new Random();
			p = BigInteger.probablePrime(512, random);
			q = BigInteger.probablePrime(512, random2);
			n = p.multiply(q);
		}
		BigInteger one = new BigInteger(1, random);	//I don't know how else to instantiate a BigInteger with value 1
		while(one.intValue() != 1){			//Allow it to only have 1 digit, keep trying until that digit = 1
			random = new Random();
			one = new BigInteger(1,random);
		}		
		BigInteger phiN = (p.subtract(one)).multiply(q.subtract(one));	//Phi(N) = (p-1) * (q-1)
		
		random = new Random();
		random2 = new Random();		
		BigInteger e = new BigInteger(random.nextInt(1024), random2);	//Create public key
		while(e.compareTo(phiN) >= 0 || e.compareTo(one) <= 0 || e.gcd(phiN).compareTo(one) != 0 ){ 	//Ensures that e is > 1, e < phi(n), and their GCD is 1
			random = new Random();
			random2 = new Random();
			e = new BigInteger(random.nextInt(1024), random2);
		}
		BigInteger d = e.modInverse(phiN);	//create private key
		
		//Write out to public and private key files
		try{
			//Save to privkey
			FileOutputStream fileOutputStream = new FileOutputStream("privkey.rsa");
			ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);
			output.writeObject(d);
			output.writeObject(n);
			output.flush();
			//Save to pubkey
			fileOutputStream = new FileOutputStream("pubkey.rsa");
			output = new ObjectOutputStream(fileOutputStream);
			output.writeObject(e);
			output.writeObject(n);
			output.flush();
		}catch(FileNotFoundException fnfe){
			System.out.println(fnfe);
		}catch(IOException ioe){
			System.out.println(ioe);
		}
		
		System.out.println("Keypairs Created!");
		
	}
	
	
}