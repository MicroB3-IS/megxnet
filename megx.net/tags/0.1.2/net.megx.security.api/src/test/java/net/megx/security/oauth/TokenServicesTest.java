package net.megx.security.oauth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.megx.model.auth.Consumer;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.Token;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.ConsumerService;
import net.megx.security.auth.services.UserService;
import net.megx.security.crypto.KeySecret;
import net.megx.security.crypto.KeySecretProvider;
import net.megx.security.crypto.impl.DefaultKeySecretProvider;
import net.megx.security.oauth.impl.OAuthTokenServices;
import net.megx.security.utils.Cache;
import static org.easymock.EasyMock.*;

public class TokenServicesTest {
	
	private TokenServices tokenService;
	private UserService userService;
	private ConsumerService consumerService;
	
	private Token defaultToken;
	private User defaultUser;
	private Consumer defaultConsumer;
	private KeySecretProvider keySecretProvider;
	private Cache cache;
	
	public static String TOKEN = "TOKEN";
	public static String SECRET = "NIaWgKMJOkAYm6vdFpgGHpPOfF4mXFmvqL-pb698hsiD3y-bZ5FyGGMD7z0pqFk0w7Ol2qD7n-GdJ-HeW3Srqa";
	public static String VERIFIER = "VERIFIER";
	public static String CALLBACK_URL = "Call/BackURL";
	public static String CONSUMER_KEY = "NzA1NDZhMTktZmMwOC00NmI2LTg0ZTUtNDg4ZWRmODE0ZjYyA";
	public static boolean ACCESS_TOKEN = true;
	public static Date TIME_STAMP = new Date();
	public static User USER;
	public static boolean AUTHORIZED = true; // only for request tokens...
	
	public static String USERNAME = "testUserForTokenTest";
	public static String DESCRIPTION = "Description for the new user";
	public static String FIRST_NAME = "Test";
	public static String LAST_NAME = "User";
	public static String INITIALS = "TU";
	public static String EMAIL = "testUser@testsite.com";
	public static boolean DISABLED = false;
	public static boolean EXTERNAL = false;
	public static Date JOINED_ON = new Date();
	public static Date LAST_LOGIN = new Date();
	public static String PASSWORD = "password";
	public static String PROVIDER = "external-provider.com";
	public static Role ROLE_USER = new Role();
	public static Role ROLE_ADMIN = new Role();
	public static Role ROLE_CMS_ADMIN = new Role();
	
	static{
		ROLE_USER.setLabel("user");
		ROLE_ADMIN.setLabel("admin");
		ROLE_CMS_ADMIN.setLabel("cmsAdmin");
	}
	
	public static String CONSUMER_NAME = "ConsumerName";
	public static String CONSUMER_DESCRIPTION = "Description for the consumer";
	public static String CONSUMERKEY = "NzA1NDZhMTktZmMwOC00NmI2LTg0ZTUtNDg4ZWRmODE0ZjYy";
	public static String CONSUMER_SECRET = "NIaWgKMJOkAYm6vdFpgGHpPOfF4mXFmvqL-pb698hsiD3y-bZ5FyGGMD7z0pqFk0w7Ol2qD7n-GdJ-HeW3Srqd";
	public static boolean CONSUMER_OOB = false;
	public static Date CONSUMER_EXPIRATION_DATE = new Date();
	public static boolean CONSUMER_TRUSTED = false;
	public static String CONSUMER_RESOURCE = "Consumer resource";
	public static String CONSUMER_ROLES = "ConsumerRoles";
	public static String CONSUMER_LOG_NAME = "testUser";
	public static String CONSUMER_CALLBACK_URL = "Call/BackURL";
	
	private User createUser(){
		User user = new User();
		user.setDescription(DESCRIPTION);
		user.setDisabled(DISABLED);
		user.setEmail(EMAIL);
		user.setExternal(EXTERNAL);
		user.setFirstName(FIRST_NAME);
		user.setInitials(INITIALS);
		user.setLastName(LAST_NAME);
		user.setJoinedOn(JOINED_ON);
		user.setLastlogin(LAST_LOGIN);
		user.setLastName(LAST_NAME);
		user.setLogin(USERNAME);
		user.setPassword(PASSWORD);
		user.setProvider(PROVIDER);
		List<Role> roles = new ArrayList<Role>(1);
		roles.add(ROLE_USER);
		user.setRoles(roles);
		return user;
	}
	
	private Token createToken(User user, String consumerKey){
		Token token = new Token();
		token.setAccessToken(ACCESS_TOKEN);
		token.setAuthorized(AUTHORIZED);
		token.setCallbackUrl(CALLBACK_URL);
		token.setConsumerKey(consumerKey);
		token.setUser(user);
		token.setSecret(SECRET);
		token.setTimestamp(TIME_STAMP);
		token.setVerifier(VERIFIER);
		token.setToken(TOKEN);
		return token;
	}
	
	private Consumer createConsumer(){
		Consumer consumer = new Consumer();
		consumer.setCallbackUrl(CONSUMER_CALLBACK_URL);
		consumer.setDescription(CONSUMER_DESCRIPTION);
		consumer.setExpirationDate(CONSUMER_EXPIRATION_DATE);
		consumer.setKey(CONSUMERKEY);
		consumer.setLogname(USERNAME);
		consumer.setName(CONSUMER_NAME);
		consumer.setOob(CONSUMER_OOB);
		consumer.setSecret(CONSUMER_SECRET);
		consumer.setTrusted(CONSUMER_TRUSTED);
		return consumer;
	}
	
	private KeySecret createKeySecretPair() throws GeneralSecurityException, IOException{
		DefaultKeySecretProvider defKeySecretProvider = new DefaultKeySecretProvider();
		return defKeySecretProvider.createKeySecretPair();
	}
	
	private String getTokenKey(KeySecret key){
		return key.getKey();
	}
	
	private Token getToken(String consumerKey, KeySecret keySecret){
		Token token = new Token();
		token.setAccessToken(false);
		token.setAuthorized(false);
		token.setConsumerKey(consumerKey);
		token.setTimestamp(new Date());
		token.setToken(keySecret.getKey());
		token.setSecret(keySecret.getSecret());
		return token;
	}
	
	@Before
	public void setup() throws Exception{
		tokenService = new OAuthTokenServices();
		userService = EasyMock.createMock(UserService.class);
		consumerService = EasyMock.createMock(ConsumerService.class);
		keySecretProvider = EasyMock.createMock(KeySecretProvider.class);
		cache = EasyMock.createMock(Cache.class);
		((OAuthTokenServices)tokenService).setKeySecretProvider(keySecretProvider);
		((OAuthTokenServices)tokenService).setCache(cache);
	}
	
	@Test
	public void testAuthorizeRequestToken() throws Exception{
		expect(userService.getUserByUserId(USERNAME)).andReturn(createUser());
		expect(consumerService.getConsumer(CONSUMER_NAME)).andReturn(createConsumer());
		expect(keySecretProvider.createKeySecretPair()).andReturn(createKeySecretPair());
		
		replay(userService);
		replay(consumerService);
		replay(keySecretProvider);
		
		User user = userService.getUserByUserId(USERNAME);
		Consumer consumer = consumerService.getConsumer(CONSUMER_NAME);
		
		Token requestToken = tokenService.generateRequestToken(consumer.getKey());
		expect(cache.getObject(requestToken.getToken())).andReturn(requestToken);
		replay(cache);
		
		Token authorizedToken = tokenService.authorizeRequestToken(requestToken.getToken(), user.getLogin());
		Assert.assertNotNull("Token is null", authorizedToken);
	}	
}
