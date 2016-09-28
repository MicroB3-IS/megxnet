package net.megx.selenium.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends AbstractPage {

	private String url;
	private Properties p;
	private InputStream in;

	public LoginPage(WebDriver driver) throws IOException {
		super(driver);
		this.p = new Properties();
		this.in = new FileInputStream("src/test/resources/selenium.properties");
		p.load(in);
		this.url = p.getProperty("loginFormUrl");
	}
	
	/**
	 * Open browser
	 */
	public LoginPage open() {
		driver.navigate().to(url);
		return this;
	}
	
	/**
	 * Close browser
	 */
	public void closeDriver(){
		driver.close();
		driver.quit();
	}
	
	/**
	 * Login with  megx account
	 *
	 * @param  userInputId the id selector for user name on megx site
	 * @param  passInputId the id selector for password on megx site
	 * @param  buttonId the id selector for submit button on megx site
	 */
	public LoginPage MegxLogin(String userInputId, String passInputId, String buttonId) {
		findElementByName(userInputId, p.getProperty("loginUser"));
		findElementByName(passInputId, p.getProperty("loginPass"));
		click(buttonId);
		return this;
	}
	
	/**
	 * Login through facebook
	 *
	 * @param  userInputId the id selector for user name on facebook site
	 * @param  passInputId the id selector for password on facebook site
	 * @param  buttonId the id selector for submit button on facebook site
	 */
	public LoginPage FacebookLogin(String userInputId, String passInputId, String buttonId) {
		externalLogin("facebook");
		findElementById(userInputId, p.getProperty("loginFacebookUser"));
		findElementById(passInputId, p.getProperty("loginFacebookPass"));
		click(buttonId);
		return this;
	}
	
	/**
	 * Login through twitter
	 *
	 * @param  userInputId the id selector for user name on twitter site
	 * @param  passInputId the id selector for password on twitter site
	 * @param  buttonId the id selector for submit button on twitter site
	 */
	public LoginPage TwitterLogin(String userInputId, String passInputId, String buttonId) {
		externalLogin("twitter");
		findElementById(userInputId, p.getProperty("loginTwitterUser"));
		findElementById(passInputId, p.getProperty("loginTwitterPass"));
		click(buttonId);
		return this;
	}
	
	/**
	 * Login through gmail
	 *
	 * @param  userInputId the id selector for user name on google site
	 * @param  passInputId the id selector for password on google site
	 * @param  buttonId the id selector for submit button on google site
	 */
	public LoginPage GmailLogin(String userInputId, String passInputId, String buttonId) {
		externalLogin("google");
		findElementById(userInputId, p.getProperty("loginGoogleUser"));
		click("next");  
		WebDriverWait wait = new WebDriverWait(driver, 10);
		  wait.until(
		          ExpectedConditions.visibilityOfElementLocated(By.id("Passwd")));
		findElementById(passInputId, p.getProperty("loginGooglePass"));
		click("signIn");
		 wait.until(
		          ExpectedConditions.elementToBeClickable(By.id(buttonId)));	
		click(buttonId);
		return this;
	}
	
	/**
	 * Assert that logout button is found
	 */
	public void assertLogout() {
		WebElement href = driver.findElement(By.cssSelector("a[href*='/admin/logout.do']"));
		if(href != null){
			String navigate = href.getAttribute("href");
			driver.navigate().to(navigate);
			Assert.assertTrue(true);
		}
	}
}
