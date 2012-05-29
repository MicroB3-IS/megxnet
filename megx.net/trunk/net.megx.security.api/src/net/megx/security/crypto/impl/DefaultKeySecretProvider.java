package net.megx.security.crypto.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import net.megx.security.crypto.KeySecret;
import net.megx.security.crypto.KeySecretProvider;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DefaultKeySecretProvider implements KeySecretProvider{

	protected Log log = LogFactory.getLog(getClass());
	private static final String ALG_HMAC_SHA1 = "HmacSHA1";
	
	private SecureRandom secureRandom = new SecureRandom();
	
	public void setSecureRandom(SecureRandom secureRandom) {
		this.secureRandom = secureRandom;
	}

	public KeySecret createKeySecretPair() throws GeneralSecurityException, IOException {
		KeyGenerator keyGen = KeyGenerator.getInstance(ALG_HMAC_SHA1);
		SecretKey secretKey = keyGen.generateKey();
		
		log.debug("Generating key-secret pair...");
		String secret = base64encode(secretKey.getEncoded());
		
		
		byte [] rnd = UUID.randomUUID().toString().getBytes();
		String key = base64encode(rnd);
		KeySecret keySecret = new KeySecret(key, secret);
		log.debug("Generated key-secret: " + keySecret);
		return keySecret;
	}
	
	public static void main(String[] args) throws Exception{
		KeySecretProvider provider = new DefaultKeySecretProvider();
//		KeySecret keySecret = provider.createKeySecretPair();
//		System.out.println("Key byte-lenght: " + keySecret.getKey().getBytes().length);
//		System.out.println("Secret byte-lenght: " + keySecret.getSecret().getBytes().length);
		for(int i = 0; i < 1000; i++){
		String rnd = provider.getRandomSequence(i+1);
		System.out.println("["+rnd+"]");
		System.out.println("l="+rnd.length());
		}
	}
	
	private String base64encode(byte [] bytes){
		Base64 base64 = new Base64(128, new byte[]{}, true);
		
		return base64.encodeAsString(bytes);
	}

	public String getRandomSequence(int length) {
		if(length <= 0) return "";
		if(length == 1) length = 2;
		byte [] randomBytes = new byte[(6*length)/8];
		secureRandom.nextBytes(randomBytes);
		String base64 =base64encode(randomBytes);
		return base64.substring(0, length-1);
	}
}
