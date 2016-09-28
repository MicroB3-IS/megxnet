package net.megx.selenium.tests;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AbstractPage {
	
	protected WebDriver driver;
	
	public AbstractPage(WebDriver driver){
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	/**
	 * Click button
	 *
	 * @param  id the id selector for button
	 */
	public void click(String id){
		WebElement button = driver.findElement(By.id(id));
		if(button != null){
	        button.click();
		}
	}
	
	/**
	 * Click button
	 *
	 * @param  selector the css selector for button
	 */
	public void submitByCssSelector(String selector){
		WebElement button = driver.findElement(By.cssSelector(selector));
		if(button != null){
	        button.click();
		}
	}
	
	/**
	 * Find input by name and send some value
	 *
	 * @param  name the name selector for input
	 * @param  keysValue the value for input
	 */
	public void findElementByName(String name, String keysValue){
		WebElement input = driver.findElement(By.name(name));
		if(input != null){
			input.sendKeys(keysValue);
		}
	}
	
	/**
	 * Find input by id and send some value
	 *
	 * @param  id the id selector for input
	 * @param  keysValue the value for input
	 */
	public void findElementById(String name, String keysValue){
		WebElement input = driver.findElement(By.id(name));
		if(input != null){
			input.sendKeys(keysValue);
		}
	}
	
	/**
	 * Click login button on megx site
	 *
	 * @param  provider the provider external login name
	 */
	public void externalLogin(String provider){
		WebElement social = driver.findElement(By.xpath("//a[@class='btn btn-block btn-social btn-" + provider + "']" ));
		Assert.assertTrue(social != null);
		social.click();
	}
}
