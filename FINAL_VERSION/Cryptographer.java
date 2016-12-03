package com.spencergang.fem;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/*
 * Cryptographer:
 * 		Our Cryptographer class is responsible for containing our encryption and decryption
 * 		methods and corresponding functions to carry out encryption and decryption operations.
 * 
 * 		The method of encryption used in the File Encryption Module is AES (Advanced Encryption Standard),
 * 		Our encryption relies on padding to carry out AES operations, for more info see
 * 		http://aesencryption.net/
 * 		
 */

public class Cryptographer {

	
	/*
	 * encrypt:
	 * 	The encrypt method is responsible for encrypting an input file
	 * 
	 * 	arg0 : File inputFile : our input file to be encrypted
	 * 	arg1 : File outputFile : our encrypted file
	 * 	arg2 : String password : password used to encrypt our file
	 */
	public void encrypt(File inputFile, File outputFile, String password) throws Exception{
		
		//Input + Output Stream
		FileInputStream inputStream = new FileInputStream(inputFile);
		FileOutputStream outputStream = new FileOutputStream(outputFile, true);
		
		//Create our salt file
		byte[] salt = new byte[8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		
		//FileOutputStream saltOutput = new FileOutputStream(saltFile);
		//saltOutput.write(salt);
		//saltOutput.close();
		
		//Write salt to beginning of file (first 8 bits)
		outputStream.write(salt);
		
		
		//Set our keyfactory to ("PBKDF2WithHmacSHA1")
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		//Creates specs for our key with our salt file and password passed
		//through the encrypt method
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		//Creates the first secret key
		SecretKey secretKey = factory.generateSecret(keySpec);
		//Overlap with another secret key with AES encryption and our previous
		//secret key
		SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		
		//Creates the cipher 
		//(PKCS5Padding is used to ensure that our "password" is always 
		//in plain text terms, a multiple of 16 bits
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		//Initialize our cipher in Encrypt Mode with our recently created
		//secret key with "AES" specs
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		//params is used to create our IV bytes with our given 
		//algorithm parameters
		AlgorithmParameters params = cipher.getParameters();
		
		//Creates our iv file
		//The IV file is simply for adding additional security
		//to our AES encryption. In theory this is not necessary,
		//however our application uses our IV file to decrypt.
		//FileOutputStream ivOutput = new FileOutputStream(ivFile);
		//Get our IV from params
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		
		//Write our iv bytes to IV file and close
		//ivOutput.write(iv);
		//ivOutput.close();
		
		//Write our IV bits to our file (8 bits)
		outputStream.write(iv);
		
		//Encryption
		byte[] input = new byte[64];
		int bytesRead;
		
		//First we read our input file
		while((bytesRead = inputStream.read(input)) != -1){
			//Simply updates and returns the chunks that it can encrypt
			byte[] output = cipher.update(input, 0, bytesRead);
			//If our output chunks aren't null
			if(output != null){
				//Write our chunks
				outputStream.write(output);
			}
		}
		
		//Conduct the actual cipher on the data we initialized and modified with
		//our cipher variable. The two are used in conjunction to ensure that if
		//our update method missed any bits in our buffers, that they will be pushed
		//and given to our final file.
		byte[] output = cipher.doFinal();
		//Output has data
		if(output != null){
			//Write our finally ciphered bits over top our chunks
			outputStream.write(output);
		}
		
		//Close our input stream
		inputStream.close();
		//Flush and close our output stream.
		//(Pushes all bits in buffer out)
		outputStream.flush();
		outputStream.close();
	}

	public void decrypt(File inputFile, File outputFile, String password) throws Exception {
		
		//Input and Output streams for our encrypted and decrypted output file
		FileInputStream inputStream = new FileInputStream(inputFile);
		FileOutputStream outputStream = new FileOutputStream(outputFile, true);
		
		//For our salt, iv, and our actual file data
		//Assign to length of our encrypted file (input file)
		byte[] allFile = new byte[(int) inputFile.length()];
		//Used to read from IV to EOF
		int totalBytes = inputStream.read(allFile);
		
		//Grab salt from allFile
		byte[] salt = Arrays.copyOfRange(allFile, 0, 8);
		//Grab IV from allFile
		byte[] iv = Arrays.copyOfRange(allFile, 8, 24);
		//Grab fileData from allFile
		byte[] fileContent = Arrays.copyOfRange(allFile, 24, totalBytes);
		
		//Byte input stream for fileContent decryption
		ByteArrayInputStream fileContentStream = new ByteArrayInputStream(fileContent);
		
		//Tell our key factory what key we will be working with
		//In our case we have a padded key (password can be any length)
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		//Create keyspec from our password and salt file
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		//Temporary key generated
		SecretKey tmp = factory.generateSecret(keySpec);
		//Use temporarily generated key to generate our AES key
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		
		//Set our cypher to AES with PKCS5Padding
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		//Tell our cipher that we will be decrypting, pass our password and IV
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		
		//Create an array of 64 bytes to hold our encrpted content
		byte[] in = new byte[64];
		//To keep track of EOF
		int readBytes;
		//Read our encrypted data into "in"
		while((readBytes = fileContentStream.read(in)) != -1){
			
			//Create a new byte array with decrypted blocks of data
			//NOTE: cipher.update does not account for all bytes
			//that we pass into our cipher buffer. If the data in our 
			//cipher buffer cannot fill a block or has leftovers in a 
			//later block, it will leave them in the buffer to be 
			//picked up by cipher.doFinal
			byte[] output = cipher.update(in, 0, readBytes);
			
			//If there were blocks returned
			if(output != null){
				outputStream.write(output);
			}
		}
		
		//Any bytes left in our cipher buffer are padded and returned as a whole block
		//with cipher.doFinal
		byte[] output = cipher.doFinal();
		
		//If there is a block of data
		if(output != null){
			outputStream.write(output);
		}
		
		//Close our buffers
		inputStream.close();
		outputStream.flush();
		outputStream.close();
		fileContentStream.close();
	}
}
