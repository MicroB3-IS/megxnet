package net.megx.security.api.test;

import net.megx.security.crypto.KeySecret;
import net.megx.security.crypto.KeySecretProvider;
import net.megx.security.crypto.impl.DefaultKeySecretProvider;

public class CryptoTest {
	public static void main(String[] args) throws Throwable{
		KeySecretProvider provider = new DefaultKeySecretProvider();
		KeySecret keySecret = provider.createKeySecretPair();
		
		
		System.out.println("Key: "+keySecret.getKey());
		System.out.println("Secret: "+keySecret.getSecret());
		
	}
}
