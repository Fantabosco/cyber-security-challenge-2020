package it.reply.challenge.tamangoteam.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HashUtils {
	
	private static final Logger log = LogManager.getLogger();

	private HashUtils() {
	}
	
	public static String generateFlag(String solution) {
		String flag = "{FLG:" + getSHA256(solution).toLowerCase() + "}";
		log.info("Soluzione: {}", flag);
		return flag;
	}
	
	public static String getSHA256(String input) {
		return getHash(input, "SHA-256");
	}
		
	public static String getSHA1(String input) {
		return getHash(input, "SHA-1");
	}
	
	public static String getMD5(String input) {
		return getHash(input, "MD5");
	}
	
	private static String getHash(String input, String algorithm) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
		byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
		return bytesToHex(hash);
	}
	
	public static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder();
	    for (int i = 0; i < hash.length; i++) {
		    String hex = Integer.toHexString(0xff & hash[i]);
		    if(hex.length() == 1) {
		    	hexString.append('0');
		    }
		    hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	private static String hexToCharset(String hex, Charset charset) {
		ByteBuffer buff = ByteBuffer.allocate(hex.length()/2);
		for (int i = 0; i < hex.length(); i+=2) {
		    buff.put((byte)Integer.parseInt(hex.substring(i, i+2), 16));
		}
		buff.rewind();
		return Charset.forName(charset.name()).decode(buff).toString();
	}
}
