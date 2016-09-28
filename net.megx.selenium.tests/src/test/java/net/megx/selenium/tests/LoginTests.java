package net.megx.selenium.tests;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import net.megx.selenium.tests.LoginPage;

public class LoginTests {
	
	
	private LoginPage loginPage;
	private Properties p;
	private InputStream in;
	private boolean isChormeBrowser;
	
	@Before
	public void setUp() throws Exception{	   
		this.p = new Properties();
		this.in = new FileInputStream("src/test/resources/selenium.properties");
		p.load(in);
		isChormeBrowser = Boolean.parseBoolean(p.getProperty("isChromeBrowser"));
		
		if(isChormeBrowser){
			System.setProperty("webdriver.chrome.driver", p.getProperty("chromeDriver"));
			loginPage = new LoginPage(new ChromeDriver());
		}else{
			System.setProperty("webdriver.gecko.driver", p.getProperty("geckoDriver"));
			loginPage = new LoginPage(new FirefoxDriver());	
		}		
	}
	
	@After
	public void tearDown(){
		loginPage.closeDriver();
	}
	
	@Test
	public void testMegxLogin() throws IOException{
				loginPage.open()
						 .MegxLogin("j_username", "j_password","mx-standard-login-btn")
						 .assertLogout();
	}
	
	@Test
	public void testFacebookLogin() throws IOException{
				loginPage.open()
						 .FacebookLogin("email", "pass","loginbutton")
						 .assertLogout();
	}
	
	@Test
	public void testTwitterLogin() throws IOException{
				loginPage.open()
						 .TwitterLogin("username_or_email", "password", "allow")
						 .assertLogout();
	}
	
	@Test
	public void testGmailLogin() throws IOException{
				loginPage.open()
						 .GmailLogin("Email", "Passwd", "submit_approve_access")
						 .assertLogout();
	}
}
