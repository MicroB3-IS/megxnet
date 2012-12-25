package net.megx.security.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import net.megx.utils.PasswordHash;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class PasswordHashTest {
	
	private static String DEFAULT_USER_PASS = "megx";
	private Log log = LogFactory.getLog(getClass());
	
	@Before
	public void setUp() throws JSONException, FileNotFoundException{
		String configStr = new Scanner(new File("__config/net.megx.megdb.json")).useDelimiter("\\Z").next();
		System.out.println("Configuration" + configStr);
		JSONObject config = new JSONObject(configStr);
		PasswordHash.SALT_BYTES = config.optInt("saltBytes", PasswordHash.SALT_BYTES);
		PasswordHash.HASH_BYTES = config.optInt("hashBytes", PasswordHash.HASH_BYTES);
		PasswordHash.PBKDF2_ITERATIONS = config.optInt("PBKDF2iterations", PasswordHash.PBKDF2_ITERATIONS);
	}
	
	@Test
	public void generateDefaultUserPassword() throws NoSuchAlgorithmException, InvalidKeySpecException{
		System.out.println("GENERATED HASH: " + PasswordHash.createHash(DEFAULT_USER_PASS));
	}
	
}
