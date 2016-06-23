package com.spencergang.fem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Cryptographer {

	public void encrypt(File inputFile, File outputFile, String password, File saltFile, File ivFile) throws Exception{
		
		//Input + Output Stream
		FileInputStream inputStream = new FileInputStream(inputFile);
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		
		//Create our salt file
		byte[] salt = new byte[8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		FileOutputStream saltOutput = new FileOutputStream(saltFile);
		saltOutput.write(salt);
		saltOutput.close();
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKey secretKey = factory.generateSecret(keySpec);
		SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		
		//Creates our iv file
		FileOutputStream ivOutput = new FileOutputStream(ivFile);
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		ivOutput.write(iv);
		ivOutput.close();
		
		//Encryption
		byte[] input = new byte[64];
		int bytesRead;
		
		while((bytesRead = inputStream.read(input)) != -1){
			byte[] output = cipher.update(input, 0, bytesRead);
			if(output != null){
				outputStream.write(output);
			}
		}
		
		byte[] output = cipher.doFinal();
		if(output != null){
			outputStream.write(output);
		}
		
		inputStream.close();
		outputStream.flush();
		outputStream.close();
	}

	public void decrypt(File inputFile, File outputFile, String password, File saltFile, File ivFile) throws Exception {
		
		//Grab our salt file
		FileInputStream saltInput = new FileInputStream(saltFile);
		byte[] salt = new byte[8];
		saltInput.read(salt);
		saltInput.close();
		
		//Grab our iv file
		FileInputStream ivInput = new FileInputStream(ivFile);
		byte[] iv = new byte[16];
		ivInput.read(iv);
		ivInput.close();
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKey tmp = factory.generateSecret(keySpec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		
		//Decryption
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		FileInputStream inputStream = new FileInputStream(inputFile);
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		byte[] in = new byte[64];
		int read;
		while((read = inputStream.read(in)) != -1){
			byte[] output = cipher.update(in, 0, read);
			if(output != null){
				outputStream.write(output);
			}
		}
		
		byte[] output = cipher.doFinal();
		if(output != null){
			outputStream.write(output);
		}
		
		inputStream.close();
		outputStream.flush();
		outputStream.close();
	}
}
