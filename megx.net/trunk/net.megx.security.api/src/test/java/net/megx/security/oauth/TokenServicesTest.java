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
import net.megx.security.auth.services.TokenService;
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
	
	private KeySecretProvider keySecretProvider;
	private Cache cache;
	private TokenService tokenServiceMockup;
	
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
	
	@Before
	public void setup() throws Exception{
		tokenService = new OAuthTokenServices();
		userService = EasyMock.createMock(UserService.class);
		consumerService = EasyMock.createMock(ConsumerService.class);
		keySecretProvider = new DefaultKeySecretProvider();
		tokenServiceMockup = EasyMock.createMock(TokenService.class);
		cache = EasyMock.createMock(Cache.class);
		((OAuthTokenServices)tokenService).setKeySecretProvider(keySecretProvider);
		((OAuthTokenServices)tokenService).setCache(cache);
		((OAuthTokenServices)tokenService).setTokenService(tokenServiceMockup);
	}
	
	@Test
	public void testAuthorizeRequestToken() throws Exception{
		expect(userService.getUserByUserId(USERNAME)).andReturn(createUser());
		expect(consumerService.getConsumer(CONSUMER_NAME)).andReturn(createConsumer());
		//expect(keySecretProvider.createKeySecretPair()).andReturn(createKeySecretPair());
		
		replay(userService);
		replay(consumerService);
		//replay(keySecretProvider);
		
		User user = userService.getUserByUserId(USERNAME);
		Consumer consumer = consumerService.getConsumer(CONSUMER_NAME);
		
		Token requestToken = tokenService.generateRequestToken(consumer.getKey());
		expect(cache.getObject(requestToken.getToken())).andReturn(requestToken);
		replay(cache);
		
		Token authorizedToken = tokenService.authorizeRequestToken(requestToken.getToken(), user.getLogin());
		Assert.assertNotNull("Token is null", authorizedToken);
	}
	
	@Test
	public void testGenerateRequestToken() throws Exception{
		expect(consumerService.getConsumer(CONSUMER_NAME)).andReturn(createConsumer());
		//expect(keySecretProvider.createKeySecretPair()).andReturn(createKeySecretPair());
		
		replay(consumerService);
		//replay(keySecretProvider);
		
		Consumer consumer = consumerService.getConsumer(CONSUMER_NAME);
		
		Token requestToken = tokenService.generateRequestToken(consumer.getKey());
		Assert.assertNotNull("Token is null", requestToken);
	}
	
	@Test
	public void testGenerateAccessToken() throws Exception{
		expect(userService.getUserByUserId(USERNAME)).andReturn(createUser());
		expect(consumerService.getConsumer(CONSUMER_NAME)).andReturn(createConsumer());
		//expect(keySecretProvider.createKeySecretPair()).andReturn(createKeySecretPair());
		//expect(keySecretProvider.createKeySecretPair()).andReturn(createKeySecretPair());
		
		replay(userService);
		replay(consumerService);
		//replay(keySecretProvider);
		
		User user = userService.getUserByUserId(USERNAME);
		Consumer consumer = consumerService.getConsumer(CONSUMER_NAME);
		
		Token requestToken = tokenService.generateRequestToken(consumer.getKey());
		expect(cache.getObject(requestToken.getToken())).andReturn(requestToken);
		expect(cache.getObject(requestToken.getToken())).andReturn(requestToken);
		expect(cache.removeObject(requestToken.getToken())).andReturn(requestToken);
		replay(cache);
		
		Token authorizedRequestToken = tokenService.authorizeRequestToken(requestToken.getToken(), user.getLogin());
		
		Token accessToken = tokenService.generateAccessToken(consumer.getKey(), authorizedRequestToken.getToken());
		Assert.assertNotNull("Token is null", accessToken);
	}
	
	@Test
	public void testGetAccessToken() throws Exception{
		expect(userService.getUserByUserId(USERNAME)).andReturn(createUser());
		expect(consumerService.getConsumer(CONSUMER_NAME)).andReturn(createConsumer());
		//expect(keySecretProvider.createKeySecretPair()).andReturn(createKeySecretPair());
		//expect(keySecretProvider.createKeySecretPair()).andReturn(createKeySecretPair());
		
		//expect(tokenServiceMockup.saveToken(value, token))
		
		
		
		replay(userService);
		replay(consumerService);
		//replay(keySecretProvider);
		
		User user = userService.getUserByUserId(USERNAME);
		Consumer consumer = consumerService.getConsumer(CONSUMER_NAME);
		
		Token requestToken = tokenService.generateRequestToken(consumer.getKey());
		expect(cache.getObject(requestToken.getToken())).andReturn(requestToken);
		expect(cache.getObject(requestToken.getToken())).andReturn(requestToken);
		expect(cache.removeObject(requestToken.getToken())).andReturn(requestToken);
		replay(cache);
		
		Token authorizedRequestToken = tokenService.authorizeRequestToken(requestToken.getToken(), user.getLogin());
		
		//expect(tokenService.saveToken(token.getToken(), token));
		
		KeySecretProvider mockedKeysecretProvider = EasyMock.createMock(KeySecretProvider.class);
		KeySecret keySecret = new KeySecret("test-key", "test-secret");
		expect(mockedKeysecretProvider.createKeySecretPair()).andReturn(keySecret);
		
		Token mockToken = new Token();
		mockToken.setAccessToken(true);
		mockToken.setConsumerKey(consumer.getKey());
		mockToken.setTimestamp(new Date());
		mockToken.setToken(keySecret.getKey());
		mockToken.setSecret(keySecret.getSecret());
		mockToken.setUser(user);
		
		expect(tokenServiceMockup.saveToken(mockToken.getToken(), mockToken)).andReturn(mockToken);
		expect(tokenServiceMockup.getToken(mockToken.getToken())).andReturn(mockToken);
		replay(mockedKeysecretProvider);
		replay(tokenServiceMockup);
		
		((OAuthTokenServices)tokenService).setKeySecretProvider(mockedKeysecretProvider);
		
		Token generatedAccessToken = tokenService.generateAccessToken(consumer.getKey(), authorizedRequestToken.getToken());
		Assert.assertNotNull("The generated access token is null", generatedAccessToken);
		
		Token retrievedAccessToken = tokenService.getAccessToken(generatedAccessToken.getToken());
		Assert.assertEquals("Tokens are not identical", generatedAccessToken, retrievedAccessToken);
		Assert.assertTrue("Should be access token", retrievedAccessToken.isAccessToken());
	}
	
}
