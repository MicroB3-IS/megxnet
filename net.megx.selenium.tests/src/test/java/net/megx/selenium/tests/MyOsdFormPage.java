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

public class MyOsdFormPage extends AbstractPage {

	private String url;
	private Properties p;
	private InputStream in;

	public MyOsdFormPage(WebDriver driver) throws IOException {
		super(driver);
		this.p = new Properties();
		this.in = new FileInputStream("src/test/resources/selenium.properties");
		p.load(in);
		this.url = p.getProperty("samplesFormUrl");
	}
	
	/**
	 * Open browser
	 */
	public MyOsdFormPage open() {
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
	 * Submit required fields for myOsd form with hardcoded values
	 */
	public MyOsdFormPage SubmitSamples() {
		findElementByName("date_taken", "2016-08-02");
		findElementByName("time_taken", "13:21");
		findElementByName("latitude", "25");
		findElementByName("longitude", "24");
		findElementByName("depth", "3");
		findElementByName("sample_name", "selenium-test");
		submitByCssSelector(".alpaca-form-button-submit");
		return this;
	}
	
	/**
	 * Assert that element exist
	 */
	public void assertElementExist() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		  wait.until(
		          ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-success")));
			WebElement exist = driver
					.findElement(By.cssSelector(".toast-success"));
		if (exist == null) {
			Assert.assertTrue(false);
		}
	}
}
