package net.megx.wod05;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;

public class WOA05StepDefs 
{
	protected WebDriver driver;

	@Before
	public void setUp() 
	{
		driver = new FirefoxDriver();
	}

	@Given("the user is on WOD05 Extractor Page")
	public void the_user_is_on_woa05_extractor_page() 
	{
	    driver.get("http://www.megx.net/gms/tools/wod.html");
	}

	@When("he enters \"([^\"]*)\" as latitude")
	public void he_enters_latitude(String latitude) {
		driver.findElement(By.id("latitude")).sendKeys(latitude);
	}

	@And("he enters \"([^\"]*)\" as longitude")
	public void he_enters_longitude(String longitude) 
	{
		driver.findElement(By.id("longitude")).sendKeys(longitude);
	}
	
	@And("he enters \"([^\"]*)\" as depth")
	public void he_enters_depth(String depth) 
	{
		driver.findElement(By.id("depth")).sendKeys(depth);
	}
	
	@And("he submits request by clicking the Calculate button")
	public void he_submits_request_by_clicking_the_caculate_button()
	{
	    driver.findElement(By.name("submit")).click();	    
	}	
	
	@Then("ensure the result is complete")
	public void ensure_the_result_is_complete() 
	{
	    //WebElement message = driver.findElement(By.id("message"));
		//WebElement message = driver.findElement(By.id("message"));
		//assertEquals(message.getText(),"msg");		
	}

	@After
	public void tearDown() 
	{
	    driver.close();
	}
}
