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
		
		if(log.isDebugEnabled())
			log.debug("Generating key-secret pair...");
		String secret = base64encode(secretKey.getEncoded());
		
		
		byte [] rnd = UUID.randomUUID().toString().getBytes();
		String key = base64encode(rnd);
		KeySecret keySecret = new KeySecret(key, secret);
		
		if(log.isDebugEnabled())
			log.debug("Generated key-secret: " + keySecret);
		
		return keySecret;
	}
	
	private String base64encode(byte [] bytes){
		Base64 base64 = new Base64(128, new byte[]{}, true);
		
		return base64.encodeAsString(bytes);
	}

	public String getRandomSequence(int length) {
		if(length <= 0) return "";
		byte [] randomBytes = new byte[(int)Math.ceil( ((double)(6*length))/8) ];
		secureRandom.nextBytes(randomBytes);
		String base64 =base64encode(randomBytes);
		return base64.substring(0, length);
	}
	public static void main(String[] args) {
		DefaultKeySecretProvider k = new DefaultKeySecretProvider();
		for(int  i = 1; i <= 100; i++){
			String r = k.getRandomSequence(i);
			if(r.length() == i){
				System.out.println(String.format("%d. %s [%d]",i,r,r.length()));
			}else{
				System.err.println(String.format("%d. %s [%d]",i,r,r.length()));
			}
		}
	}
}
