package com.revature.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

//reference: https://www.javatpoint.com/how-to-encrypt-password-in-java

public class CryptoUtils {
	
	private final static String algorithm = "SHA-256";
	
	public static byte[] getSHA(String input) throws NoSuchAlgorithmException
	{
		//MessageDigest instance for hashing using SHA256
		MessageDigest md = MessageDigest.getInstance(algorithm);
		
		//digest() method called to calculate message digest of an input and return array of byte
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}
	
	public static String Encrypt(byte[] hash)
	{
		//Convert byte array of hash into digest
		BigInteger number = new BigInteger(1, hash);
		
		//Convert the digest into hex value
		StringBuilder hexString = new StringBuilder(number.toString(16));
		
		//Pad with leading zeros
		while(hexString.length() < 32)
		{
			hexString.insert(0, '0');
		}	
		return hexString.toString();
	}
	
}
